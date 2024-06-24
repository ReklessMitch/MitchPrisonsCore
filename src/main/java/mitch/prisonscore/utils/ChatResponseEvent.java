package mitch.prisonscore.utils;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class ChatResponseEvent extends Event {

    private final String message;
    private final Player player;
    private static final HandlerList handlers = new HandlerList();


    public ChatResponseEvent(Player player, String message) {
        this.player = player;
        this.message = message;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
