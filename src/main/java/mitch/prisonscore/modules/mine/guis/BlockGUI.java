package mitch.prisonscore.modules.mine.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.modules.mine.MineModule;
import mitch.prisonscore.modules.mine.configs.MinePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BlockGUI extends ChestGui {

    private final Player player;

    public BlockGUI(Player player){
        this.player = player;
        setInventory(Bukkit.createInventory(null, 45, "Set Block"));
        init();
        add();
    }

    public void init() {
        int i = 0;
        for(Material block : MineModule.get().getBlockMap()){
            getInventory().setItem(i, new ItemStack(block));
            setAction(i, event -> {
                MinePlayer.get(player.getUniqueId()).setBlock(block);
                return true;
            });
            i++;
        }
    }

    public void open() {
        player.openInventory(getInventory());
    }
}
