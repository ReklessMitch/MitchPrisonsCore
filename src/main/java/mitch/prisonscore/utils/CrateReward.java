
package mitch.prisonscore.utils;

import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class CrateReward {

    @Getter private double chance;
    private List<String> commands;
    private String message;

    public CrateReward(double chance, List<String> commands, String message) {
        this.chance = chance;
        this.commands = commands;
        this.message = message;
    }

    public void runCommands(Player player) {
        commands.forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                PlaceholderAPI.setPlaceholders(player, command)));
    }

    public List<String> getCommands(Player player) {
        commands.forEach(command -> PlaceholderAPI.setPlaceholders(player, command));
        return commands;
    }

    public List<String> getCommands(Player player, int amount) {
        commands.forEach(command -> PlaceholderAPI.setPlaceholders(player, command));
        commands.replaceAll(command -> command.replace("%amount%", String.valueOf(amount)));
        return commands;
    }

    public String getMessage(Player player) {
        message = PlaceholderAPI.setPlaceholders(player, message);
        return message;
    }


}
