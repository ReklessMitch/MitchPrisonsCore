package me.reklessmitch.mitchprisonscore.mitchpickaxe.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PickaxeConf;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.utils.ItemCreator;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UpgradeGUI extends ChestGui {

    private final Player player;

    public UpgradeGUI(Player player) {
        this.setInventory(Bukkit.createInventory(null, 45, MessageUtils.colorize(LangConf.get().getPickaxeMainGuiTitle())));
        this.player = player;
        add();
        createInventory();
        setSoundOpen(null);
        setSoundClose(null);
    }

    public void createInventory(){
        PPickaxe pPickaxe = PPickaxe.get(player.getUniqueId());
        PickaxeConf.get().getEnchants().forEach((enchant, e) -> {
            getInventory().setItem(e.getDisplayItem().getSlot(), e.getEnchantGuiItem(pPickaxe));
            if(e.getMaxLevel() == pPickaxe.getEnchants().get(enchant)){
                this.setAction(e.getDisplayItem().getSlot(), event -> {
                    if(e.getMaxPrestige() == pPickaxe.getEnchants().get(enchant)) {
                        MessageUtils.sendMessages(player, LangConf.get().getPickaxeUpgradeAlreadyMaxed());
                    }else{
                        new PrestigeEnchantGUI(e, player).open();
                    }
                    return true;
                });
                return;
            }
            this.setAction(e.getDisplayItem().getSlot(), event -> {
                if(e.getLevelRequired() > ProfilePlayer.get(player.getUniqueId()).getRank()){
                    MessageUtils.sendMessages(player, LangConf.get().getPickaxeUpgradeNotHighEnoughRank());
                    return true;
                }
                new UpgradeEnchantGUI(e, player).open();
                return true;
            });
        });
        setupPickaxeSkinButton();
        setupTogglesButton();
    }

    private void setupTogglesButton() {
        int[] toggleSlots = {36, 37, 38, 39, 27, 28, 29, 30};
        final ItemStack toggleGuiItem = ItemCreator.createItem(Material.PAPER, 1, LangConf.get().getInvisibleCustomData(),
                "<green>Toggles", "<grey>Click to change your toggles");
        for(int slot : toggleSlots){
            getInventory().setItem(slot, toggleGuiItem);
            this.setAction(slot, event -> {
                new TogglesMainGUI((Player) event.getWhoClicked()).open();
                return true;
            });
        }
    }

    private void setupPickaxeSkinButton(){
        int[] pickaxeSkinSlots = {41, 42, 43, 44, 32, 33, 34, 35};
        final ItemStack skinGuiItem = ItemCreator.createItem(Material.PAPER, 1, LangConf.get().getInvisibleCustomData(), "<green>Pickaxe Skins", "<grey>Click to change your pickaxe skin");
        for(int slot : pickaxeSkinSlots){
            getInventory().setItem(slot, skinGuiItem);
            this.setAction(slot, event -> {
                new PickaxeSkins(player).open();
                return true;
            });
        }
    }

    public void open(){
        player.openInventory(getInventory());
    }

}
