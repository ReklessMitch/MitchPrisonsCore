package mitch.prisonscore.modules.profile.engines;

import com.massivecraft.massivecore.Engine;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import mitch.prisonscore.modules.profile.ProfileModule;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatEvents extends Engine {

    private static ChatEvents i = new ChatEvents();
    public static ChatEvents get() { return i; }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void playerChatEvent(PlayerChatEvent event){
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void playerAsyncChatEvent(AsyncChatEvent event){
        event.renderer((source, sourceDisplayName, message, viewer) -> {
            String name = MiniMessage.miniMessage().serialize(sourceDisplayName);
            String msg = MiniMessage.miniMessage().serialize(message);
            return MessageUtils.colorize(name + " <arrow> " + msg);
        });
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void set(AsyncPlayerChatEvent event){
        final TagResolver nameResolver = Placeholder.parsed("name", event.getPlayer().getName());
        User lpu = LuckPermsProvider.get().getUserManager().getUser(event.getPlayer().getUniqueId());
        if(lpu == null){
            return;
        }
        final String primaryGroup = lpu.getPrimaryGroup();
        final ProfileModule profilesConf = ProfileModule.get();
        final ProfilePlayer profilePlayer = ProfilePlayer.get(event.getPlayer().getUniqueId());
        final TagResolver prefixResolver = Placeholder.parsed("prefix", profilesConf.getRankToPrefix().get(primaryGroup));
        final String currentChatPerm = profilePlayer.getCurrentChatColour();
        final String currentNamePerm = profilePlayer.getCurrentNameColour();
        final TagResolver staffChatResolver = Placeholder.parsed("sc", profilePlayer.isStaffChat() ? "<red>[<gold><bold>SC<red>] " : "");
        final TagResolver chatColourResolver = Placeholder.parsed("chatcolour", profilesConf.getChatColoursPermToColour().get(currentChatPerm));
        final TagResolver nameColourResolver = Placeholder.parsed("namecolour", profilesConf.getNameColoursPermToColour().get(currentNamePerm));
        final TagResolver levelResolver = Placeholder.parsed("rank", String.valueOf(profilePlayer.getRank()));
        final TagResolver rankColour = Placeholder.parsed("rankcolour", profilePlayer.getCurrentRankColour());
        final String format = PlaceholderAPI.setPlaceholders(event.getPlayer(), profilesConf.getChatFormat()) + " " + event.getMessage();
        event.setCancelled(true);
        if(profilePlayer.isStaffChat()){
            Bukkit.broadcast(MessageUtils.colorize(format, nameResolver, prefixResolver, chatColourResolver, nameColourResolver,
                    levelResolver, rankColour, staffChatResolver), "mpc.staffchat");
            return;
        }
        Bukkit.broadcast(MessageUtils.colorize(format, nameResolver, prefixResolver, chatColourResolver, nameColourResolver,
                                                        levelResolver, rankColour, staffChatResolver));
    }
}
