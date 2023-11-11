package me.reklessmitch.mitchprisonscore.mitchpickaxe.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;

import me.reklessmitch.mitchprisonscore.mitchpickaxe.enchants.Enchant;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.Currency;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.CurrencyUtils;
import me.reklessmitch.mitchprisonscore.utils.ItemCreator;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;


public class UpgradeEnchantGUI extends ChestGui {

    private final Enchant enchant;
    private final Player player;
    private final PPickaxe p;
    private final ProfilePlayer profilePlayer;

    public UpgradeEnchantGUI(@NotNull Enchant enchant, Player player) {
        this.enchant = enchant;
        this.player = player;
        this.p = PPickaxe.get(player);
        this.profilePlayer = ProfilePlayer.get(player.getUniqueId());
        setInventory(Bukkit.createInventory(null, 54, MessageUtils.colorize(LangConf.get().getPickaxeGuiTitle())));
        refresh();
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        setAutoremoving(true);
        add();
    }

    private void getPane(int[] slots, int amount){
        int level = p.getEnchants().get(enchant.getType());
        int prestige = p.getEnchantPrestiges().get(enchant.getType());
        long cost = enchant.getCost(level, amount, prestige);

        for (int slot : slots) {
            ItemStack item = ItemCreator.createItem(Material.PAPER, 1, LangConf.get().getInvisibleCustomData(), "<red>Upgrade: <white>" + amount, "<red>Cost: <white>" + CurrencyUtils.format(BigInteger.valueOf(cost)));
            getInventory().setItem(slot, item);
            setAction(slot, event -> {
                event.setCancelled(true);
                if(level + amount > enchant.getMaxLevel()){
                    MessageUtils.sendMessage(player, "<red>You would exceed the max level of this enchantment");
                    return true;
                }

                if (profilePlayer.getCurrencyAmount(Currency.TOKEN).subtract(BigInteger.valueOf(cost)).compareTo(BigInteger.ZERO) > 0) {
                    profilePlayer.take(Currency.TOKEN, cost);
                    profilePlayer.changed();
                    p.getEnchants().replace(enchant.getType(), amount + p.getEnchants().get(enchant.getType()));
                    MessageUtils.sendMessage(player, "<green>You have upgraded " + enchant.getType() + " by " + amount + " levels");
                    p.updatePickaxe();
                    refresh();
                }else{
                    MessageUtils.sendMessage(player, "<red>You do not have enough tokens to upgrade this enchantment");
                }
                return true;
            });
        }
    }


    public void refresh(){
        getInventory().setItem(4, enchant.getEnchantGuiItem(p));
        getPane(new int[]{9, 10, 11, 18, 19, 20}, 1);
        getPane(new int[]{12, 13, 14, 21, 22, 23},5);
        getPane(new int[]{15, 16, 17, 24, 25, 26}, 50);
        getPane(new int[]{27, 28, 29, 36, 37, 38}, 500);
        getPane(new int[]{30, 31, 32, 39, 40, 41}, 5000);
        int currentLevel = p.getEnchants().get(enchant.getType());
        int maxLevels = enchant.getMaxAmount(currentLevel,
                profilePlayer.getCurrencyAmount(Currency.TOKEN).longValue(), enchant.getMaxLevel(), p.getEnchantPrestiges().get(enchant.getType()));
        getPane(new int[]{33, 34, 35, 42, 43, 44}, maxLevels);
    }

    public void open(){
        player.openInventory(getInventory());
    }

}
