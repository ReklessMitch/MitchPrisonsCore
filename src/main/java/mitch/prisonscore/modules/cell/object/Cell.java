package mitch.prisonscore.modules.cell.object;

import com.massivecraft.massivecore.util.IdUtil;
import lombok.Getter;
import mitch.prisonscore.modules.cell.CellModule;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.util.*;

@Getter
public class Cell {

    private String name;
    private UUID owner;
    private Set<UUID> officers;
    private Set<UUID> members;
    private BigInteger beacons;
    private Set<UUID> invites;

    public Cell(String name, UUID owner){
        this.name = name;
        this.owner = owner;
        this.beacons = BigInteger.ZERO;
        this.members = new HashSet<>();
        this.invites = new HashSet<>();
        this.officers = new HashSet<>();
    }

    public void addBeacons(BigInteger amount) {
        beacons = beacons.add(amount);
        getAllMembers().forEach(uuid -> {
            OfflinePlayer player = IdUtil.getOfflinePlayer(uuid);
            if(player.isOnline() && player.getPlayer() != null){
                MessageUtils.sendMessage(player.getPlayer(), "<gold>" + amount + " <gray>beacons deposited into cell from <gold>" + player.getName());
            }
        });
    }

    public void disband() {
        getAllMembers().forEach(uuid -> {
            OfflinePlayer player = IdUtil.getOfflinePlayer(uuid);
            if(player.isOnline() && player.getPlayer() != null){
                MessageUtils.sendMessage(player.getPlayer(), "<gray>Your cell has been disbanded");
            }
        });
        CellModule cellConf = CellModule.get();
        cellConf.getCells().remove(name);
        cellConf.changed();

    }

    public Set<UUID> getAllMembers(){
        Set<UUID> m = new HashSet<>();
        m.addAll(officers);
        m.addAll(members);
        m.add(owner);
        return m;
    }

    public Set<UUID> getAllHigherUps(){
        Set<UUID> m = new HashSet<>(officers);
        m.add(owner);
        return m;
    }

    public void removePlayer(UUID uniqueId, Player remover) {
        members.remove(uniqueId);
        officers.remove(uniqueId);
        invites.remove(uniqueId);
        OfflinePlayer player = IdUtil.getOfflinePlayer(uniqueId);
        MessageUtils.sendMessage(remover, "<green>You have kicked <red>" + player.getName() + "<green> from your cell");
        if(player.isOnline() && player.getPlayer() != null){
            MessageUtils.sendMessage(player.getPlayer(), "<red>You have been kicked from your cell");
        }
    }

    public List<UUID> getAllMembersBelowUser(UUID uniqueId) {
        List<UUID> m = new ArrayList<>(members);
        if(owner.equals(uniqueId)) {
            m.addAll(officers);
        }
        return m;
    }
}
