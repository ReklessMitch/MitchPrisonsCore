package mitch.prisonscore.modules.profile.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.modules.profile.ProfileModule;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ColourGUI extends ChestGui {

    private final int type;
    private final ProfileModule profileModule;
    private final LangConf langConf;
    private final Map<String, String> colourToGradient;
    private final ProfilePlayer profilePlayer;
    private final Player player;

    /*
    * Constructor for the ColourGUI's class
    * - ChatColour - 0
    * - NameColour - 1
    * - RankColour - 2
     */
    public ColourGUI(@NotNull Player player, int type) {
        this.langConf = LangConf.get();
        this.profileModule = ProfileModule.get();
        this.profilePlayer = ProfilePlayer.get(player.getUniqueId());
        this.type = type;
        this.colourToGradient = getPermToColour();
        this.player = player;
        this.setInventory(Bukkit.createInventory(null, 27, getTitle()));
        add();
        setupGUI();
    }

    private Component getTitle() {
        return switch (type) {
            case 0 -> MessageUtils.colorize(langConf.getChatColourTitle());
            case 1 -> MessageUtils.colorize(langConf.getNameColourTitle());
            case 2 -> MessageUtils.colorize(langConf.getRankColourTitle());
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    private String getPerm() {
        return switch (type) {
            case 0 -> "chat";
            case 1 -> "name";
            case 2 -> "rank";
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    private Map<String, String> getPermToColour() {
        return switch (type) {
            case 0 -> profileModule.getChatColoursPermToColour();
            case 1 -> profileModule.getNameColoursPermToColour();
            case 2 -> profileModule.getRankColoursPermToColour();
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    private void setColour(String colour) {
        switch (type) {
            case 0 -> profilePlayer.setCurrentChatColour(colour);
            case 1 -> profilePlayer.setCurrentNameColour(colour);
            case 2 -> profilePlayer.setCurrentRankColour(colour);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private void setupGUI(){
        final List<String> perms = new ArrayList<>(colourToGradient.keySet());
        for (int i = 0; i < perms.size(); i++) {
            String colour = perms.get(i);
            String gradient = colourToGradient.get(colour);
            TagResolver colourResolver = Placeholder.parsed("colour", colour);
            TagResolver gradientResolver = Placeholder.parsed("gradient", gradient);
            boolean hasPerm = profilePlayer.getPlayer().hasPermission("mpc."+ getPerm() + "." + colour);
            TagResolver hasPermResolver = Placeholder.parsed("hasperm",
                    hasPerm ? "" : profileModule.getRequiresPermissionExtraText());
            TagResolver activeResolver = Placeholder.parsed("active",
                    profilePlayer.getCurrentChatColour().equals(colour) ? profileModule.getActiveColourExtraText() : "");
            ItemStack item = profileModule.getPermToColourItem().getFormatItem(colourResolver, gradientResolver, hasPermResolver, activeResolver);

            this.getInventory().setItem(i, item);
            if(!hasPerm) return;
            setAction(i, event -> {
                MessageUtils.sendMessage(player, "<green>" + getPerm() + " colour set to " + gradient);
                setColour(colour);
                event.setCancelled(true);
                return true;
            });
        }
    }

    public void open(){
        player.openInventory(this.getInventory());
    }
}
