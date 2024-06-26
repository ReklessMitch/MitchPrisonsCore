package mitch.prisonscore.modules.pickaxe.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.modules.pickaxe.MitchPickaxeModule;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.utils.ItemCreator;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PickaxeSkinsGUI extends ChestGui {

    private final Player player;

    public PickaxeSkinsGUI(Player player){
        setInventory(Bukkit.createInventory(null, 27, MessageUtils.colorize(LangConf.get().getPickaxeSkinsGuiTitle())));
        this.player = player;
        MitchPickaxeModule.get().getPickaxeSkins().forEach((pickaxeName, displayItem) -> getPickaxeSkinItem(displayItem.getItemName(), displayItem.getItemLore(), displayItem.getCustomModelData(), displayItem.getSlot()));
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        add();
    }

    private void getPickaxeSkinItem(String name, List<String> lore, int customDataModel, int slot){
        final ItemStack item = ItemCreator.createItem(Material.DIAMOND_PICKAXE, 1, customDataModel, name, lore);
        getInventory().setItem(slot, item);
        this.setAction(slot, event -> {
            event.setCancelled(true);
            PickaxePlayer pick = PickaxePlayer.get(player.getUniqueId());
            if(!player.hasPermission("mitchprisonscore.pickaxe." + customDataModel)){
                MessageUtils.sendMessages(player, LangConf.get().getPickaxeSkinNotUnlocked());
                return false;
            }
            if(pick.getPickaxe().getCustomModelData() == customDataModel){
                MessageUtils.sendMessages(player, LangConf.get().getPickaxeSkinAlreadySelected());
                return false;
            }
            final TagResolver skinNameResolver = Placeholder.parsed("skin", name);
            MessageUtils.sendMessages(player, LangConf.get().getPickaxeSkinSelected(), skinNameResolver);
            pick.setSkin(customDataModel);
            player.closeInventory();
            return true;
        });
    }

    public void open(){
        player.openInventory(getInventory());
    }

}
