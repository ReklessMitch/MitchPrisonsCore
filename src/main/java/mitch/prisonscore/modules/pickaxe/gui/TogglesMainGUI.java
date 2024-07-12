package mitch.prisonscore.modules.pickaxe.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.modules.pet.entity.PetPlayer;
import mitch.prisonscore.modules.pet.gui.TogglePetGUI;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.utils.ItemCreator;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TogglesMainGUI extends ChestGui {

    private final Player player;

    public TogglesMainGUI(Player player){
        this.player = player;
        setInventory(Bukkit.createInventory(null, 27, MessageUtils.colorize(LangConf.get().getPickaxeTogglesGuiTitle())));
        add();
        addItems();
    }

    private void addItems(){
        // - Loot and Key finder for Virtualkey or in inventory  :red_circle:
        //- Message Toggles :red_circle: - 0
        //- Enchant Toggles :red_circle: - 1
        //- Enchant Sounds :red_circle: - 2

        final ItemStack petShowToggle =
                ItemCreator.createItem(Material.PAPER, 1, 68, "<red>Pet Toggle",
                        "<gray>Toggle for showing pets");

        getInventory().setItem(15, petShowToggle);
        setAction(15, event -> {
            final PetPlayer petPlayer = PetPlayer.get(player.getUniqueId());
            boolean showPet = petPlayer.isShowPet();
            petPlayer.setShowPet(!showPet);
            if(showPet){
                petPlayer.despawnPet();
            }else{
                if(petPlayer.getActivePet() != null){
                    petPlayer.spawnPet(petPlayer.getActivePet());
                }
            }
            MessageUtils.sendMessage(player, "<green>You have " + (petPlayer.isShowPet() ? "<green>enabled" : "<red>disabled") + " <green>showing pets");
            return true;
        });

        final ItemStack toggleVirtualKey = ItemCreator.createItem(Material.TRIPWIRE_HOOK,1, 0, "<red>Toggle Virtual Key", "<gray>Toggle the virtual key");
        getInventory().setItem(10, toggleVirtualKey);
        setAction(10, event -> {
            PickaxePlayer.get(player.getUniqueId()).toggleVirtualKey();
            return true;
        });

        final ItemStack toggleMessage = ItemCreator.createItem(Material.PAPER, 1, 0, "<red>Message Toggles", "<gray>Toggle the messages");
        getInventory().setItem(11, toggleMessage);
        setAction(11, event -> {
            new TogglesGUI(player, 0).open();
            return true;
        });

        final ItemStack toggleEnchant = ItemCreator.createItem(Material.COMPARATOR, 1, 0, "<red>Enchant Toggles", "<gray>Toggle the enchants");
        getInventory().setItem(12, toggleEnchant);
        setAction(12, event -> {
            new TogglesGUI(player, 1).open();
            return true;
        });

        final ItemStack toggleEnchantSound = ItemCreator.createItem(Material.JUKEBOX, 1, 0, "<red>Enchant Sound Toggles", "<gray>Toggle the enchant sounds");
        getInventory().setItem(13, toggleEnchantSound);
        setAction(13, event -> {
            new TogglesGUI(player, 2).open();
            return true;
        });

        final ItemStack toggleAutoRankup = ItemCreator.createItem(Material.DIAMOND, 1, 0, "<red>Auto Rankup", "<gray>Toggle the auto rankup");
        getInventory().setItem(14, toggleAutoRankup);
        setAction(14, event -> {
            PickaxePlayer.get(player.getUniqueId()).toggleAutoRankup();
            return true;
        });

        final ItemStack togglePetMessages = ItemCreator.createItem(Material.BONE, 1, 0, "<red>Pet Messages", "<gray>Toggle the pet messages");
        getInventory().setItem(16, togglePetMessages);
        setAction(16, event -> {
            new TogglePetGUI(player).open();
            return true;
        });
    }

    public void open() {
        player.openInventory(getInventory());
    }
}
