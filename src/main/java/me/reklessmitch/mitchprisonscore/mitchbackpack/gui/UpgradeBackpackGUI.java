package me.reklessmitch.mitchprisonscore.mitchbackpack.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;

import me.reklessmitch.mitchprisonscore.mitchbackpack.config.BackpackConf;
import me.reklessmitch.mitchprisonscore.mitchbackpack.config.BackpackPlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.Currency;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.CurrencyUtils;
import me.reklessmitch.mitchprisonscore.utils.ItemCreator;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;

public class UpgradeBackpackGUI extends ChestGui {

    private final Player player;
    private final BackpackPlayer backpackPlayer;
    private final ProfilePlayer profilePlayer;

    public UpgradeBackpackGUI(Player player) {
        this.player = player;
        this.backpackPlayer = BackpackPlayer.get(player.getUniqueId());
        this.profilePlayer = ProfilePlayer.get(player.getUniqueId());
        setInventory(Bukkit.createInventory(null, 54, MessageUtils.colorize(LangConf.get().getBackpackGuiTitle())));
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        refresh();
        add();
    }

    private void getUpgradeItem(int[] slots, BigInteger amount, BigInteger cost){
        final LangConf conf = LangConf.get();
        final ItemStack item = ItemCreator.createItem(Material.PAPER, 1,  conf.getInvisibleCustomData(),
                "<green>Upgrade <red><bold>" + amount + " <green>slots", "<red>Cost: <white>" + CurrencyUtils.format(cost));
        for(int slot : slots) {
            getInventory().setItem(slot, item);
            this.setAction(slot, event -> {
                if (profilePlayer.getCurrencyAmount(Currency.TOKEN).compareTo(cost) >= 0) {
                    backpackPlayer.addSlot(amount);
                    profilePlayer.take(Currency.TOKEN, cost);
                    refresh();
                    return false;
                } else {
                    MessageUtils.sendMessages(player, conf.getBackpackUpgradeNotEnoughTokens());
                }
                return true;
            });
        }
    }

    public void refresh(){
        getUpgradeItem(new int[]{0,1,2,9,10,11}, BigInteger.valueOf(1), backpackPlayer.getCost(BigInteger.valueOf(1)));
        getUpgradeItem(new int[]{3,4,5,12,13,14}, BigInteger.valueOf(5), backpackPlayer.getCost(BigInteger.valueOf(10)));
        getUpgradeItem(new int[]{6,7,8,15,16,17}, BigInteger.valueOf(50), backpackPlayer.getCost(BigInteger.valueOf(50)));
        getUpgradeItem(new int[]{18,19,20,27,28,29}, BigInteger.valueOf(500), backpackPlayer.getCost(BigInteger.valueOf(500)));
        getUpgradeItem(new int[]{21,22,23,30,31,32}, BigInteger.valueOf(1000), backpackPlayer.getCost(BigInteger.valueOf(5000)));
        BigInteger maxPurchasable = backpackPlayer.getMaxPurchasable();
        getUpgradeItem(new int[]{24,25,26,33,34,35}, maxPurchasable, backpackPlayer.getCost(maxPurchasable));
        getBackpackSkinItem();
        getAutoSellItem();
        togglesButton();
    }

    private void getBackpackSkinItem() {
        int[] slots = new int[]{36,37,38,45,46,47};
        final ItemStack item = ItemCreator.createItem(Material.PAPER, 1, LangConf.get().getInvisibleCustomData(), "<red>Backpack Skin", "<grey>Click to edit your backpack skin");
        for(int slot: slots){
            getInventory().setItem(slot, item);
            this.setAction(slot, event -> {
                new BackpackSkins(player).open();
                return true;
            });
        }
    }

    private void togglesButton(){
        int[] slots = new int[]{41,42,43,44,50, 51, 52, 53};
        final ItemStack item = ItemCreator.createItem(Material.PAPER, 1, LangConf.get().getInvisibleCustomData(), "<red>Toggles", "<grey>Click to toggle backpack messages!");
        for(int slot: slots){
            getInventory().setItem(slot, item);
            this.setAction(slot, event -> {
                backpackPlayer.setMessages(!backpackPlayer.isMessages());
                TagResolver isMessages = Placeholder.parsed("toggle", backpackPlayer.isMessages() ? "<green<on" : "<red>off");
                MessageUtils.sendMessage(player, LangConf.get().getBackpackMessagesToggled(), isMessages);
                event.setCancelled(true);
                return true;
            });
        }
    }

    private void getAutoSellItem() {
        int[] slots = new int[]{39,40,48,49};
        final LangConf conf = LangConf.get();
        long cost = BackpackConf.get().getAutoSellCost();
        final ItemStack item = ItemCreator.createItem(Material.PAPER, 1, conf.getInvisibleCustomData(), "<red>Auto Sell", backpackPlayer.isAutoSell() ?
                "<green>Enabled" : "<red>Disabled", "<grey> ", "<red>Cost: <yellow>" + cost);
        for(int slot: slots){
            getInventory().setItem(slot, item);
            this.setAction(slot, event -> {
                event.setCancelled(true);
                if(backpackPlayer.isAutoSell()){
                    MessageUtils.sendMessages(player, conf.getAutoSellAlreadyPurchased());
                    return false;
                }
                if (profilePlayer.getCurrencyAmount(Currency.TOKEN).compareTo(BigInteger.valueOf(cost)) < 0) {
                    MessageUtils.sendMessages(player, conf.getAutoSellNotEnoughTokens());
                    return false;
                }
                profilePlayer.take(Currency.TOKEN, BigInteger.valueOf(cost));
                backpackPlayer.setAutoSell(true);
                final TagResolver tokenAmountResolver = Placeholder.parsed("cost", String.valueOf(cost));
                MessageUtils.sendMessages(player, conf.getAutoSellPurchased(), tokenAmountResolver);
                refresh();
                return true;
            });
        }
    }

    public void open() {
        player.openInventory(getInventory());
    }
}
