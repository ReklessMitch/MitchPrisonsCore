package mitch.prisonscore.utils;

import com.massivecraft.massivecore.Engine;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class MitchAntiCheat extends Engine {

    private static MitchAntiCheat i = new MitchAntiCheat();
    public static MitchAntiCheat get() { return i; }

    private Map<UUID, Integer> blocksPer10s = new HashMap<>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        final UUID player = event.getPlayer().getUniqueId();
        if (blocksPer10s.containsKey(player)) {
            blocksPer10s.put(player, blocksPer10s.get(player) + 1);
        } else {
            blocksPer10s.put(player, 1);
        }
    }

    public BukkitRunnable checkPlayersTask = new BukkitRunnable() {
        @Override
        public void run() {
            for(Map.Entry<UUID, Integer> entry : blocksPer10s.entrySet()) {
                Player player = Bukkit.getPlayer(entry.getKey());
                if(entry.getValue() > 300) {
                    if(player == null) continue;
                    player.kick(MessageUtils.colorize("<red>You were kicked for breaking too many blocks in a short period of time!"));
                }else{
                    MessageUtils.sendMessage(player, "<green>You have broken <gold>" + entry.getValue() + " <green>raw blocks in the last 10 seconds.");

                }
            }
            blocksPer10s.clear();
        }
    };

}
