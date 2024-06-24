package mitch.prisonscore.modules.profile;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import lombok.Getter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.Module;
import mitch.prisonscore.modules.pickaxe.utils.DisplayItem;
import mitch.prisonscore.modules.profile.cmds.currency.*;
import mitch.prisonscore.modules.profile.cmds.decoration.CmdChatColourGUI;
import mitch.prisonscore.modules.profile.cmds.decoration.CmdNameColourGUI;
import mitch.prisonscore.modules.profile.cmds.decoration.CmdRankColourGUI;
import mitch.prisonscore.modules.profile.cmds.joinmessage.CmdChangeJoinMessage;
import mitch.prisonscore.modules.profile.cmds.misc.CmdToggleSC;
import mitch.prisonscore.modules.profile.cmds.wardrobe.CmdWardrobe;
import mitch.prisonscore.modules.profile.configs.ProfilePlayerColl;
import mitch.prisonscore.modules.profile.engines.ChatEvents;
import mitch.prisonscore.modules.profile.engines.PlayerEvents;
import mitch.prisonscore.modules.profile.object.ShopItem;
import mitch.prisonscore.modules.profile.placeholders.CurrencyPlaceholders;
import mitch.prisonscore.modules.profile.placeholders.ProfilePlaceholders;
import mitch.prisonscore.modules.type.ModuleType;
import org.bukkit.Material;

import java.util.List;
import java.util.Map;

@Getter
@SuppressWarnings("FieldMayBeFinal")
@EditorName("config")
public class ProfileModule extends Module {

    private List<String> joinMessages = List.of("%player% joined", "%player% is here");

    private Map<String, String> nameColoursPermToColour = Map.of(
            "mpc.namecolour.red", "<red>",
            "mpc.namecolour.green", "<green>",
            "mpc.namecolour.blue", "<blue>",
            "mpc.namecolour.yellow", "<yellow>",
            "mpc.namecolour.purple", "<purple>",
            "mpc.namecolour.white", "<white>",
            "mpc.namecolour.black", "<black>",
            "mpc.namecolour.gray", "<gray>");

    private Map<String, String> chatColoursPermToColour = Map.of(
            "mpc.chatcolour.red", "<red>",
            "mpc.chatcolour.green", "<green>",
            "mpc.chatcolour.blue", "<blue>",
            "mpc.chatcolour.yellow", "<yellow>",
            "mpc.chatcolour.purple", "<purple>",
            "mpc.chatcolour.white", "<white>",
            "mpc.chatcolour.black", "<black>",
            "mpc.chatcolour.gray", "<gray>");

    private Map<String, String> rankColoursPermToColour = Map.of(
            "mpc.rankcolour.red", "<red>",
            "mpc.rankcolour.green", "<green>",
            "mpc.rankcolour.blue", "<blue>",
            "mpc.rankcolour.yellow", "<yellow>",
            "mpc.rankcolour.purple", "<purple>",
            "mpc.rankcolour.white", "<white>",
            "mpc.rankcolour.black", "<black>",
            "mpc.rankcolour.gray", "<gray>");

    private final transient List<ShopItem> defaultShopItem = List.of(new ShopItem(1, 10, Material.DIAMOND, "<green>Rank1",
            "<green>Rank1", List.of("aa %player% 1"), List.of("<green>Cost: %cost%")));
    private final transient DisplayItem defaultDisplayItem =
            new DisplayItem(Material.DIAMOND,
                    "<green>Cosmetic",
                    List.of("Runs X command"),
                    0,
                    1);

    private Map<String, DisplayItem> storeCategories = Map.of("ranks", defaultDisplayItem,
            "gkits", defaultDisplayItem,
            "crates", defaultDisplayItem);
    private Map<String, List<ShopItem>> storeItems = Map.of("ranks", defaultShopItem,
            "gkits", defaultShopItem,
            "crates", defaultShopItem);

    private String wardrobeTitle = "<green>Wardrobe";

    private DisplayItem backpackItem = defaultDisplayItem;
    private DisplayItem wingsItem = defaultDisplayItem;
    private DisplayItem armourItem = defaultDisplayItem;
    private DisplayItem pickaxeItem = defaultDisplayItem;
    private DisplayItem nameColourItem = defaultDisplayItem;
    private DisplayItem chatColourItem = defaultDisplayItem;
    private DisplayItem rankColourItem = defaultDisplayItem;

    private Map<String, String> rankToPrefix = Map.of(
            "default", "default", "guardian", "guardian", "hero", "hero",
            "mythic", "mythic", "celestial", "celestial", "emperor", "emperor",
            "helper", "helper", "moderator", "moderator", "senior-moderator", "senior-moderator");

    private String chatFormat = "<prefix> <red><name>: <white>";

    public static ProfileModule get() {
        return MitchPrisonsCore.get().getModuleByType(ModuleType.PROFILE);
    }

    public ProfileModule() {
        super(true);
    }

    @Override
    public ModuleType getType() {
        return ModuleType.PROFILE;
    }

    @Override
    public void enable() {
        if (!isEnabled()) return;
        MitchPrisonsCore.get().activate(
                // colls
                ProfilePlayerColl.class,

                // cmds
                CmdChangeJoinMessage.class, CmdCurrency.class, CmdBal.class,
                CmdChatColourGUI.class, CmdNameColourGUI.class, CmdRankColourGUI.class,
                CmdCurrencyAddAmount.class, CmdWardrobe.class, CmdToggleSC.class, CmdResetCurrencies.class,

                // engines
                ChatEvents.class,
                PlayerEvents.class

        );
        new ProfilePlaceholders().register();
        new CurrencyPlaceholders().register();
    }

    @Override
    public void disable() {

    }

}
