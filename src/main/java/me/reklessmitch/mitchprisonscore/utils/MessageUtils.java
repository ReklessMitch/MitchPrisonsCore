package me.reklessmitch.mitchprisonscore.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageUtils {

    private static List<TagResolver> cachedGlobalTags;


    static {
        cachedGlobalTags = new ArrayList<>();
        cacheTags();
    }

    public static Component colorize(String message, boolean resolved, TagResolver... resolvers) {
        if (!resolved) {
            final TagResolver[] globalResolvers = new TagResolver[cachedGlobalTags.size() + resolvers.length];
            System.arraycopy(cachedGlobalTags.toArray(), 0, globalResolvers, 0, cachedGlobalTags.size());
            System.arraycopy(resolvers, 0, globalResolvers, cachedGlobalTags.size(), resolvers.length);

            return MiniMessage.miniMessage().deserialize(message, globalResolvers).decoration(TextDecoration.ITALIC, false);
        }

        return MiniMessage.miniMessage().deserialize(message, resolvers).decoration(TextDecoration.ITALIC, false);
    }

    public static Component colorize(String message, TagResolver... resolvers) {
        return colorize(message, false, resolvers);
    }

    public static List<Component> colorize(List<String> messages, TagResolver... resolvers) {
        final List<Component> components = new ArrayList<>();
        for (final String message: messages) {
            components.add(colorize(message, resolvers).decoration(TextDecoration.ITALIC, false));
        }
        return components;
    }

    public static void sendMessage(CommandSender sender, String message, TagResolver... resolvers) {
        sendMessage(sender, message, false, resolvers);
    }

    public static void sendMessage(CommandSender sender, String message, boolean resolved, TagResolver... resolvers) {
        if (!resolved) {
            final TagResolver[] globalResolvers = new TagResolver[cachedGlobalTags.size() + resolvers.length];
            System.arraycopy(cachedGlobalTags.toArray(), 0, globalResolvers, 0, cachedGlobalTags.size());
            System.arraycopy(resolvers, 0, globalResolvers, cachedGlobalTags.size(), resolvers.length);

            sender.sendMessage(MiniMessage.miniMessage().deserialize(message, globalResolvers).decoration(TextDecoration.ITALIC, false));
            return;
        }

        sender.sendMessage(MiniMessage.miniMessage().deserialize(message, resolvers).decoration(TextDecoration.ITALIC, false));
    }

    public static void sendMessages(CommandSender sender, List<String> messages, TagResolver... resolvers) {
        final TagResolver[] globalResolvers = new TagResolver[cachedGlobalTags.size() + resolvers.length];
        System.arraycopy(cachedGlobalTags.toArray(), 0, globalResolvers, 0, cachedGlobalTags.size());
        System.arraycopy(resolvers, 0, globalResolvers, cachedGlobalTags.size(), resolvers.length);

        for (final String message: messages) {
            sendMessage(sender, message, true, globalResolvers);
        }
    }

    public static void sendMessage(UUID entity, String message, TagResolver... resolvers) {
        sendMessage(entity, message, false, resolvers);
    }

    public static void sendMessage(UUID entity, String message, boolean resolved, TagResolver... resolvers) {
        final Player player = Bukkit.getPlayer(entity);

        if (player == null) {
            return;
        }

        if (!resolved) {
            final TagResolver[] globalResolvers = new TagResolver[cachedGlobalTags.size() + resolvers.length];
            System.arraycopy(cachedGlobalTags.toArray(), 0, globalResolvers, 0, cachedGlobalTags.size());
            System.arraycopy(resolvers, 0, globalResolvers, cachedGlobalTags.size(), resolvers.length);
            player.sendMessage(MiniMessage.miniMessage().deserialize(message, globalResolvers).decoration(TextDecoration.ITALIC, false));
            return;
        }

        player.sendMessage(MiniMessage.miniMessage().deserialize(message, resolvers).decoration(TextDecoration.ITALIC, false));
    }

    public static void sendMessages(UUID entity, List<String> messages, TagResolver... resolvers) {
        final TagResolver[] globalResolvers = new TagResolver[cachedGlobalTags.size() + resolvers.length];
        System.arraycopy(cachedGlobalTags.toArray(), 0, globalResolvers, 0, cachedGlobalTags.size());
        System.arraycopy(resolvers, 0, globalResolvers, cachedGlobalTags.size(), resolvers.length);

        for (final String message: messages) {
            sendMessage(entity, message, true, globalResolvers);
        }
    }

    public static void cacheTags() {
        cachedGlobalTags.clear();
        MessagesConfig.get().getGlobalPlaceholders().forEach((key, value) -> cachedGlobalTags.add(Placeholder.parsed(key, value)));
    }
}
