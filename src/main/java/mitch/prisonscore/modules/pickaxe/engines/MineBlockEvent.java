package mitch.prisonscore.modules.pickaxe.engines;

import com.massivecraft.massivecore.Engine;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.mine.configs.MinePlayer;
import mitch.prisonscore.modules.mine.utils.BlockInPmineBrokeEvent;
import mitch.prisonscore.modules.pet.PetModule;
import mitch.prisonscore.modules.pet.entity.PetPlayer;
import mitch.prisonscore.modules.pet.newpets.Pet;
import mitch.prisonscore.modules.pet.util.PetType;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.modules.pickaxe.MitchPickaxeModule;
import mitch.prisonscore.modules.pickaxe.enchants.Enchant;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.utils.Currency;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockDamageEvent;

import static mitch.prisonscore.modules.pickaxe.utils.EnchantUtils.addTokens;

public class MineBlockEvent extends Engine {

    private static MineBlockEvent i = new MineBlockEvent();

    public static MineBlockEvent get() {
        return i;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockDamage(BlockDamageEvent event) {
        final Block block = event.getBlock();
        final ProfilePlayer profilePlayer = ProfilePlayer.get(event.getPlayer().getUniqueId());
        if (block.getType().equals(Material.BEACON)) {
            event.getBlock().setType(Material.AIR);
            profilePlayer.addCurrency(Currency.BEACON, 1);
        }
        Bukkit.getScheduler().runTaskLater(MitchPrisonsCore.get(),
                () -> event.getPlayer().sendBlockChange(block.getLocation(), block.getType(), block.getData()), 3);
    }


    private void addRawBlock(Player player, PickaxePlayer pickaxe) {
        pickaxe.addRawBlockBroken();
        addTokens(1, player);
        pickaxe.addBlockBroken(1);
    }

    private void activatePet(BlockInPmineBrokeEvent e){
        PetPlayer petPlayer = PetPlayer.get(e.getPlayer().getUniqueId());
        PetType petType = petPlayer.getActivePet();
        Pet pet = PetModule.get().getPets().get(petType);
        if(pet == null) return;
        if(pet.getConfig().canProc(petPlayer.getPetLevel(petType))) {
            pet.activate(e, petPlayer.getPetLevel(petType));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void mineBlock(BlockInPmineBrokeEvent e) {
        PickaxePlayer pickaxe = PickaxePlayer.get(e.getPlayer().getUniqueId());
        addRawBlock(e.getPlayer(), pickaxe);
        activatePet(e);
        pickaxe.getEnchants().forEach((type, level) -> {
            Enchant playerEnchant = MitchPickaxeModule.get().getEnchantByType(type);
            if(level == 0) return;
            final int prestige = pickaxe.getEnchantPrestiges().get(type);
            if(!pickaxe.getEnchantToggle().get(type)) return;
            if(playerEnchant.getConfig().canProc(level, prestige, e.getPlayer().getUniqueId())){
                playerEnchant.activate(e, level, prestige);
            }
//            if (playerEnchant.getConfig().getClass().isAssignableFrom(ProccableEnchantConfig.class)) {
//                final int prestige = pickaxe.getEnchantPrestiges().get(type);
//                System.out.println("Proccable enchant: " + type.getDisplayName() +
//                        ((ProccableEnchantConfig) playerEnchant.getConfig()).getProcChance(level) + " level: " + level + " prestige: " + prestige);
//                if (((ProccableEnchantConfig) playerEnchant.getConfig()).canProc(level, prestige)) {
//                    playerEnchant.activate(e, level, prestige);
//                }
//            }
        });
    }
}
