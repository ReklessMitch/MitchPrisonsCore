package mitch.prisonscore.modules.mine.guis;

import com.google.common.collect.Lists;
import com.massivecraft.massivecore.chestgui.ChestGui;
import com.sk89q.worldedit.world.block.BlockTypes;
import mitch.prisonscore.modules.mine.MineModule;
import mitch.prisonscore.modules.mine.configs.MinePlayer;
import mitch.prisonscore.utils.MessageUtils;
import mitch.prisonscore.utils.configurable.FormatItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class BlockGUI extends ChestGui {

    private final Player player;
    private MinePlayer minePlayer;

    public BlockGUI(Player player){
        this.player = player;
        this.minePlayer = MinePlayer.get(player.getUniqueId());
        setInventory(Bukkit.createInventory(null, 45, "Set Block"));
        init();
        add();
    }

    private ItemStack getBlockItem(Material material){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(MessageUtils.colorize("<green>" + material.name().toUpperCase() + " BLOCK"));
        List<String> loreToAdd = Lists.newArrayList("" +
                        "<gray>Left Click to add block",
                        "<gray>Right click to remove block");
        if(!player.hasPermission("mpc.mineblock." + material.name().toLowerCase())){
            loreToAdd.add("<red>Locked");
        }
        meta.lore(MessageUtils.colorize(loreToAdd));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        if(minePlayer.getBlocks().contains(material)){
            item.addUnsafeEnchantment(Enchantment.THORNS, 1);
        }
        return item;
    }

    public void init() {
        List<Material> blocks = Lists.newArrayList(MineModule.get().getBlockMap());
        for(int i = 0; i < blocks.size(); i++){
            Material block = blocks.get(i);
            getInventory().setItem(i, getBlockItem(blocks.get(i)));
            setAction(i, event -> {
                if(event.getClick().isLeftClick()) {
                    minePlayer.addBlock(block);
                }
                else if(event.getClick().isRightClick()) {
                    minePlayer.removeBlock(block);
                }
                return true;
            });
        }
    }

    public void open() {
        player.openInventory(getInventory());
    }
}
