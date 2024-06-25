package mitch.prisonscore.modules.cell.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.modules.cell.CellModule;
import mitch.prisonscore.modules.cell.object.Cell;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.UUID;

public class CellKick extends ChestGui {

    private Cell cell;
    private List<UUID> members;
    private final Player player;

    public CellKick(Player player) {
        setInventory(Bukkit.createInventory(null, 36, "Kick a member"));
        LangConf lang = LangConf.get();
        this.player = player;
        Cell c = CellModule.get().getCellByMember(player.getUniqueId());
        if(c == null) {
            MessageUtils.sendMessage(player, lang.getNotInACell());
            return;
        }
        this.cell = c;
        this.members = cell.getAllMembersBelowUser(player.getUniqueId());
        if(members.isEmpty()){
            MessageUtils.sendMessage(player, lang.getNotAnyoneKickableFromCell());
            return;
        }
        init();
        add();
        open();
    }

    private void init() {
        for(int i = 0; i < members.size(); i++){
            UUID member = members.get(i);
            getInventory().setItem(i, getMemberItem(member));
            this.setAction(i, event -> {
                cell.removePlayer(member, player);
                return true;
            });
        }
    }

    private ItemStack getMemberItem(UUID member){
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) playerHead.getItemMeta();

        // Set the owner of the skull (which player's head it represents)
        assert meta != null;
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(member));
        meta.setDisplayName("<red>" + Bukkit.getOfflinePlayer(member).getName());
        meta.setLore(List.of("<green>Click to kick member"));
        // Set the SkullMeta back to the ItemStack
        playerHead.setItemMeta(meta);
        return playerHead;
    }

    public void open() {
        player.openInventory(getInventory());
    }
}
