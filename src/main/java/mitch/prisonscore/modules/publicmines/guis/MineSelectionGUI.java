package mitch.prisonscore.modules.publicmines.guis;

import com.google.common.collect.Lists;
import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.modules.mine.configs.MinePlayer;
import mitch.prisonscore.modules.publicmines.utils.MineTier;
import mitch.prisonscore.utils.ItemCreator;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MineSelectionGUI extends ChestGui {

    private final Player player;

    public MineSelectionGUI(Player player) {
        this.player = player;
        setInventory(Bukkit.createInventory(null, 27, "Public Mines"));
        addItems();
        add();
    }

    public void addItems() {
        ItemStack publicMineItem = ItemCreator.createItem(Material.STONE, 1, "public mines", Lists.newArrayList("" +
                "<red>Teleport to the public mine."));

        ItemStack privateMineItem = ItemCreator.createItem(Material.DIAMOND_BLOCK, 1, "private mines", Lists.newArrayList("" +
                "<red>Teleport to your private mine."));

        getInventory().setItem(12, publicMineItem);
        setAction(12, event -> {
            new PublicMineGUI(player).open();
            return true;
        });
        getInventory().setItem(14, privateMineItem);
        setAction(14, event -> {
            MinePlayer minePlayer = MinePlayer.get(player.getUniqueId());
            if(!minePlayer.isHasPrivateMine()){
                MessageUtils.sendMessage(player, "<red>You do not have a private mine.");
                return false;
            }
            minePlayer.getHighestMine().teleportToMine(player);
            return true;
        });

    }

    public void open() {
        player.openInventory(getInventory());
    }



}
