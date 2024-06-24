package mitch.prisonscore.modules.pickaxe.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.modules.pickaxe.MitchPickaxeModule;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.utils.ItemCreator;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UpgradeGUI extends ChestGui {

    private final Player player;
    private final ProfilePlayer profilePlayer;

    public UpgradeGUI(Player player) {
        this.setInventory(Bukkit.createInventory(null, 45, MessageUtils.colorize(LangConf.get().getPickaxeMainGuiTitle())));
        this.player = player;
        this.profilePlayer = ProfilePlayer.get(player.getUniqueId());
        add();
        createInventory();
        setSoundOpen(null);
        setSoundClose(null);
    }

    public void createInventory(){
        PickaxePlayer pPickaxe = PickaxePlayer.get(player.getUniqueId());
        MitchPickaxeModule.get().getEnchants().forEach((enchant, e) -> {
            int slot = e.getConfig().getDisplayItem().getSlot();
            getInventory().setItem(slot, e.getEnchantGuiItem(pPickaxe));
            if(e.getConfig().getMaxLevel() == pPickaxe.getEnchants().get(enchant)){
                this.setAction(slot, event -> {
                    if(e.getConfig().getMaxPrestige() == pPickaxe.getEnchantPrestiges().get(enchant)) {
                        MessageUtils.sendMessages(player, LangConf.get().getPickaxeUpgradeAlreadyMaxed());
                    }else{
                        new PrestigeEnchantGUI(e, player).open();
                    }
                    return true;
                });
                return;
            }
            this.setAction(slot, event -> {
                if(e.getConfig().getLevelRequired() > profilePlayer.getRank()){
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
