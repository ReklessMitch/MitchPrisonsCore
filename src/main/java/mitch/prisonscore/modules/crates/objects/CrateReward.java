package mitch.prisonscore.modules.crates.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mitch.prisonscore.modules.crates.configs.CratePlayer;
import mitch.prisonscore.utils.configurable.DisplayItem;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

@AllArgsConstructor
@Getter
@SuppressWarnings("FieldMayBeFinal")
public class CrateReward {

    private DisplayItem displayItem;
    private double chance;
    private List<String> commands;
    private List<String> broadcastMessages;
    private List<String> messageToPlayer;

    public void executeReward(Player player, String crateName){
        commands.forEach(command -> {
            command = command.replace("%player%", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        });
        broadcastMessages.forEach(message -> {
            message = message.replace("%player%", player.getName());
            player.getServer().broadcast(MessageUtils.colorize(message));
        });
        messageToPlayer.forEach(message -> MessageUtils.sendMessage(player, message));
        CratePlayer.get(player).removeCrateKey(crateName, 1);
    }
}
