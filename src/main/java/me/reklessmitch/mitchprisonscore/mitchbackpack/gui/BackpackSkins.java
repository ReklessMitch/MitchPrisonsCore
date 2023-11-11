package me.reklessmitch.mitchprisonscore.mitchbackpack.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import me.reklessmitch.mitchprisonscore.mitchbackpack.config.BackpackConf;
import me.reklessmitch.mitchprisonscore.mitchbackpack.config.BackpackPlayer;
import me.reklessmitch.mitchprisonscore.utils.ItemCreator;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BackpackSkins extends ChestGui {

    private final Player player;

    public BackpackSkins(Player player){
        setInventory(Bukkit.createInventory(null, 27, MessageUtils.colorize(LangConf.get().getPickaxeSkinsGuiTitle())));
        this.player = player;
        BackpackConf.get().getBackpackSkins().forEach((pickaxeName, displayItem) -> getBackpackSkinItem(displayItem.getItemName(), displayItem.getItemLore(), displayItem.getCustomModelData(), displayItem.getSlot()));
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        add();
    }

    private void getBackpackSkinItem(String name, List<String> lore, int customDataModel, int slot){
        final ItemStack item = ItemCreator.createItem(Material.PAPER, 1, customDataModel, name, lore);
        getInventory().setItem(slot, item);
        this.setAction(slot, event -> {
            event.setCancelled(true);
            if(!player.hasPermission("mitchprisonscore.backpack." + customDataModel)){
                MessageUtils.sendMessages(player, LangConf.get().getBackpackSkinNoPerm());
                return false;
            }
            BackpackPlayer bp = BackpackPlayer.get(player.getUniqueId());
            if(bp.getSkinID() == customDataModel){
                MessageUtils.sendMessages(player, LangConf.get().getBackpackSkinAlreadyEquipped());
                return false;
            }
            final TagResolver skinNameResolver = Placeholder.parsed("skin", name);
            MessageUtils.sendMessages(player, LangConf.get().getBackpackSkinEquipped(), skinNameResolver);
            bp.setSkin(customDataModel);
            player.closeInventory();
            return true;
        });
    }


    public void open(){
        player.openInventory(getInventory());
    }

}
