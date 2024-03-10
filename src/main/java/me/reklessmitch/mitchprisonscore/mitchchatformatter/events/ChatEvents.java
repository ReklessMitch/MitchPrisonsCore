package me.reklessmitch.mitchprisonscore.mitchchatformatter.events;

import com.massivecraft.massivecore.Engine;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilesConf;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
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

public class ChatEvents  extends Engine {

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
        final ProfilesConf profilesConf = ProfilesConf.get();
        final ProfilePlayer profilePlayer = ProfilePlayer.get(event.getPlayer().getUniqueId());
        final TagResolver prefixResolver = Placeholder.parsed("prefix", profilesConf.getRankToPrefix().get(primaryGroup));
        final String currentChatPerm = profilePlayer.getCurrentChatColour();
        final String currentNamePerm = profilePlayer.getCurrentNameColour();
        final TagResolver chatColourResolver = Placeholder.parsed("chatcolour", profilesConf.getChatColoursPermToColour().get(currentChatPerm));
        final TagResolver nameColourResolver = Placeholder.parsed("namecolour", profilesConf.getNameColoursPermToColour().get(currentNamePerm));
        final String format = PlaceholderAPI.setPlaceholders(event.getPlayer(), profilesConf.getChatFormat()) + " " + event.getMessage();
        Bukkit.broadcast(MessageUtils.colorize(format, nameResolver, prefixResolver, chatColourResolver, nameColourResolver));
        event.setCancelled(true);
    }
}
