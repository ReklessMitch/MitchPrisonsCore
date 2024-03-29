package me.reklessmitch.mitchprisonscore.mitchpickaxe.engines;

import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.util.MUtil;
import com.sk89q.worldedit.math.BlockVector3;
import me.clip.placeholderapi.PlaceholderAPI;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.mitchboosters.configs.BoosterPlayer;
import me.reklessmitch.mitchprisonscore.mitchboosters.objects.Booster;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.PlayerMine;
import me.reklessmitch.mitchprisonscore.mitchmines.utils.BlockInPmineBrokeEvent;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetPlayer;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetType;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PickaxeConf;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.EnchantType;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.Currency;
import me.reklessmitch.mitchprisonscore.utils.CrateReward;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MineBlockEvent extends Engine {

    /*
    See if it is possible to have global variables for this class
     */
    private static MineBlockEvent i = new MineBlockEvent();
    public static MineBlockEvent get() { return i; }

    private double getProcChance(Player player, EnchantType type, int level){
        double procChance = PickaxeConf.get().getEnchantByType(type).getProcChance(level);
        PetPlayer petPlayer = PetPlayer.get(player);
        PetType petType = petPlayer.getActivePet();
        if(petType == PetType.CRATE && type == EnchantType.KEY_FINDER ||
                petType == PetType.SUPPLY_DROP && type == EnchantType.SUPPLY_DROP ||
                petType == PetType.JACKHAMMER_BOOST && type == EnchantType.JACKHAMMER) {
            procChance *= petPlayer.getPetBooster(petType);
        }
        return procChance;

    }

    @EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockDamage(BlockDamageEvent event){
        final Block block = event.getBlock();
        final ProfilePlayer profilePlayer = ProfilePlayer.get(event.getPlayer().getUniqueId());
        if(block.getType().equals(Material.BEACON)) {
            event.getBlock().setType(Material.AIR);
            profilePlayer.addCurrency(Currency.BEACON, 1);
        }
        Bukkit.getScheduler().runTaskLater(MitchPrisonsCore.get(),
                () -> event.getPlayer().sendBlockChange(block.getLocation(), block.getType(), block.getData()), 3);
    }


    @EventHandler(ignoreCancelled = true)
    public void mineBlock(BlockInPmineBrokeEvent e) {
        Player player = e.getPlayer();
        PPickaxe pickaxe = PPickaxe.get(e.getPlayer().getUniqueId());
        ProfilePlayer currency = ProfilePlayer.get(e.getPlayer().getUniqueId());
        addRawBlock(currency, e.getPlayerMine(), pickaxe);


        pickaxe.getEnchants().forEach((type, level) -> {
            if(!pickaxe.getEnchantToggle().get(type)) return;
            double procChance = getProcChance(player, type, level);
            if(type == EnchantType.NUKE){
                procChance *= 1 + pickaxe.getEnchantPrestiges().get(type);
            }
            if(level == 0 || procChance < MitchPrisonsCore.get().getRandom().nextDouble(1)) return;
            boolean enchantMessage = pickaxe.getEnchantMessages().get(type);
            int prestige = pickaxe.getEnchantPrestiges().get(type);
            switch (type) {
                case APOCALYPSE -> apocalypse(e, currency, enchantMessage);
                case BEACON -> beacon(e, enchantMessage);
                case HASTE -> haste(e.getPlayer(), level);
                case JACKHAMMER -> jackhammer(e, currency, enchantMessage, prestige);
                case KEY_FINDER -> keyFinder(e.getPlayer(), enchantMessage);
                case LOOT_FINDER -> lootFinder(e.getPlayer(), enchantMessage);
                case SCAVENGER -> scavenger(e.getPlayer(), enchantMessage);
                case SPEED -> speed(e.getPlayer(), level);
                case SUPPLY_DROP -> supplyDrop(e, enchantMessage);
                case TOKEN_POUCH -> tokenPouch(level, currency, enchantMessage);
                case NUKE -> nuke(e, currency, enchantMessage);
                case EXPLOSIVE -> explosive(e, currency, level, enchantMessage);
                default -> {}
            }
        });
        currency.changed();
    }

    private void addRawBlock(ProfilePlayer currency, PlayerMine mine, PPickaxe pickaxe) {
        pickaxe.addRawBlockBroken();
        addTokens(1, currency, mine);
        addBlocksToBackpack(pickaxe.getPlayer(), 1);
    }

    private void addTokens(int blocks, ProfilePlayer profilePlayer, PlayerMine mine) {
        PPickaxe pickaxe = PPickaxe.get(mine.getPlayer().getUniqueId());
        int fortuneLevel = pickaxe.getEnchants().get(EnchantType.FORTUNE);
        if(fortuneLevel > 0) {
            blocks *= 1 + ((double) fortuneLevel / 1000);
        }
        int tokensToAdd = blocks;
        PetPlayer petPlayer = PetPlayer.get(mine.getPlayer().getUniqueId());
        PetType petType = petPlayer.getActivePet();
        if(petType == PetType.TOKEN) {
            tokensToAdd = (int) (blocks * petPlayer.getPetBooster(PetType.TOKEN));
        }
        tokensToAdd *= mine.getBooster();
        Booster booster = BoosterPlayer.get(mine.getPlayer().getUniqueId()).getActiveTokenBooster();
        if(booster != null){
            tokensToAdd *= booster.getMultiplier();
        }
        profilePlayer.addCurrency(Currency.TOKEN, tokensToAdd);
    }

    private void addBlocksToBackpack(Player player, int blocks){
        PPickaxe.get(player.getUniqueId()).addBlockBroken(blocks);
    }

    private void sendEnchantMessage(Player player, EnchantType type, TagResolver... tagResolver){
        final String message = PickaxeConf.get().getEnchantByType(type).getEnchantMessage();
        if(message.equals("")) return;
        MessageUtils.sendMessage(player, message, tagResolver);
    }

    private void explosive(BlockInPmineBrokeEvent e, ProfilePlayer currency, int level, boolean enchantMessage) {
        PlayerMine mine = e.getPlayerMine();
        int radiusIncrease = level / PickaxeConf.get().getExplosiveLevelsPerIncrease();
        int radius = PickaxeConf.get().getExplosiveStartRadius() + radiusIncrease;
        int blocks = mine.getExplosiveBlocks(e.getBlock().getLocation(), radius);
        addTokens(blocks, currency, mine);
        addBlocksToBackpack(e.getPlayer(), blocks);
        if(enchantMessage) {
            sendEnchantMessage(e.getPlayer(), EnchantType.EXPLOSIVE);
        }
    }

    private void nuke(BlockInPmineBrokeEvent e, ProfilePlayer currency, boolean enchantMessage) {
        PlayerMine mine = e.getPlayerMine();
        int blocks = (int) (mine.getVolume() - mine.getVolumeMined());
        addTokens(blocks, currency, mine);
        addBlocksToBackpack(e.getPlayer(), blocks);
        mine.reset();
        if(enchantMessage) {
            sendEnchantMessage(e.getPlayer(), EnchantType.NUKE);
        }
    }

    private void apocalypse(BlockInPmineBrokeEvent e, ProfilePlayer currency, boolean enchantMessage) {
        int blocks = e.getPlayerMine().apocalypse();
        addTokens(blocks, currency, e.getPlayerMine());
        addBlocksToBackpack(e.getPlayer(), blocks);
        if(enchantMessage) {
            sendEnchantMessage(e.getPlayer(), EnchantType.APOCALYPSE);
        }
    }

    private void beacon(BlockInPmineBrokeEvent e, boolean enchantMessage) {
        Location centerLocation = e.getBlock().getLocation();
        PPickaxe pickaxe = PPickaxe.get(e.getPlayer().getUniqueId());
        int prestigeMulti = 1 + pickaxe.getEnchantPrestiges().get(EnchantType.BEACON);
        List<Block> surroundingBlocks = new ArrayList<>();
        for (int xOffset = -5; xOffset <= 5; xOffset++) {
            for (int zOffset = -5; zOffset <= 5; zOffset++) {
                if (xOffset == 0 && zOffset == 0) continue;
                Location location = centerLocation.clone().add(xOffset, 0, zOffset);
                Block surroundingBlock = location.getBlock();
                if (surroundingBlock.getType() == Material.AIR ||
                        !e.getPlayerMine().isInMine(BlockVector3.at(surroundingBlock.getX(), surroundingBlock.getY(), surroundingBlock.getZ())))
                    continue;
                surroundingBlocks.add(surroundingBlock);
            }
        }


        int rand = MitchPrisonsCore.get().getRandom().nextInt(1, 10);
        List<Block> beaconBlocks = MUtil.randomSubset(surroundingBlocks, rand * prestigeMulti);
        beaconBlocks.forEach(block -> block.setType(Material.BEACON));
        final TagResolver beaconResolver = Placeholder.parsed("blocks", String.valueOf(beaconBlocks.size()));
        if(enchantMessage) {
            sendEnchantMessage(e.getPlayer(), EnchantType.BEACON, beaconResolver);
        }
    }

    private void haste(Player player, int level) {
        if(player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) return;
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, level));
    }

    private void jackhammer(BlockInPmineBrokeEvent e, ProfilePlayer currency, boolean enchantMessage, int prestige) {
        for(int y = 0; y < prestige + 1; y++) {
            int blocks = e.getPlayerMine().getBlocksOnYLayer(e.getBlock().getY() - y);
            addTokens(blocks, currency, e.getPlayerMine());
            addBlocksToBackpack(e.getPlayer(), blocks);
        }
        if(enchantMessage) {
            sendEnchantMessage(e.getPlayer(), EnchantType.JACKHAMMER);
        }
    }

    private void keyFinder(Player player, boolean enchantMessage) {
        PPickaxe pickaxe = PPickaxe.get(player);
        String key = pickaxe.isVirtualKey() ? "virtualkey" : "key";
        int amount = 1 + pickaxe.getEnchantPrestiges().get(EnchantType.KEY_FINDER);
        final TagResolver keyResolver = Placeholder.parsed("keyamount", String.valueOf(amount));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format("crate %s give %s Mine %s", key, player.getName(), amount));
        if(enchantMessage) {
            sendEnchantMessage(player, EnchantType.KEY_FINDER, keyResolver);
        }
    }

    private void lootFinder(Player player, boolean enchantMessage) {
        PPickaxe pickaxe = PPickaxe.get(player);
        String key = pickaxe.isVirtualKey() ? "virtualkey" : "key";
        int amount = 1 + pickaxe.getEnchantPrestiges().get(EnchantType.KEY_FINDER);
        final TagResolver lootResolver = Placeholder.parsed("lootamount", String.valueOf(amount));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format("crate %s give %s Loot %s", key, player.getName(), amount));
        if(enchantMessage) {
            sendEnchantMessage(player, EnchantType.LOOT_FINDER, lootResolver);
        }
    }

    private void addLevelToEnchant(EnchantType type, PickaxeConf conf, PPickaxe pickaxe){
        if(pickaxe.getEnchants().get(type) < conf.getEnchantByType(type).getMaxLevel()) {
            pickaxe.getEnchants().replace(type, pickaxe.getEnchants().get(type) + 1);
        }
    }

    private void scavenger(Player player, boolean enchantMessage) {
        PPickaxe pickaxe = PPickaxe.get(player.getUniqueId());
        PickaxeConf conf = PickaxeConf.get();

        addLevelToEnchant(EnchantType.EFFICIENCY, conf, pickaxe);
        addLevelToEnchant(EnchantType.FORTUNE, conf, pickaxe);
        int prestigeLevel = pickaxe.getEnchants().get(EnchantType.SCAVENGER);
        if (prestigeLevel == 1) {
            addLevelToEnchant(EnchantType.JACKHAMMER, conf, pickaxe);
        } else if (prestigeLevel == 2) {
            addLevelToEnchant(EnchantType.NUKE, conf, pickaxe);
        }
        pickaxe.changed();
        pickaxe.updatePickaxe();
        if(enchantMessage) {
            sendEnchantMessage(player, EnchantType.SCAVENGER);
        }
    }

    private void speed(Player player, int level) {
        if(player.hasPotionEffect(PotionEffectType.SPEED)) return;
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, level));
    }

    private void supplyDrop(BlockInPmineBrokeEvent e, boolean enchantMessage) {
        Player player = e.getPlayer();
        PPickaxe pickaxe = PPickaxe.get(player);
        int prestigeBonus = 1 + pickaxe.getEnchantPrestiges().get(EnchantType.SUPPLY_DROP);
        for(int pb = 0; pb < prestigeBonus; pb++){
            CrateReward reward = PickaxeConf.get().getReward();
            reward.getCommands(player).forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    PlaceholderAPI.setPlaceholders(player, command)));

            MessageUtils.sendMessage(player, PlaceholderAPI.setPlaceholders(player, reward.getMessage(player)));
        }
        player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_SHOOT, 1, 1);
        if(enchantMessage) {
            sendEnchantMessage(player, EnchantType.SUPPLY_DROP);
        }
    }

    private void tokenPouch(int level, ProfilePlayer profilePlayer, boolean enchantMessage) {
        PickaxeConf conf = PickaxeConf.get();
        int baseAmount = conf.getTokenPouchBaseAmount();
        final Player player = profilePlayer.getPlayer();
        int prestigeMulti = 1 + PPickaxe.get(player).getEnchants().get(EnchantType.TOKEN_POUCH);
        double petMultiplier = PetPlayer.get(player).getPetBooster(PetType.TOKEN);
        BigInteger tokensToGive = BigInteger.valueOf((long) (baseAmount +
                ((long) conf.getTokenPouchIncreasePerLevel() * level) * petMultiplier * prestigeMulti));

        profilePlayer.addCurrency(Currency.TOKEN, tokensToGive);
        final TagResolver tokenResolver = Placeholder.parsed("tokens", String.valueOf(tokensToGive));
        if(enchantMessage) {
            sendEnchantMessage(player, EnchantType.TOKEN_POUCH, tokenResolver);
        }
    }
}
