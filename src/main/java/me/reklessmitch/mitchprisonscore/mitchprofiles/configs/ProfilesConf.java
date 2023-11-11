package me.reklessmitch.mitchprisonscore.mitchprofiles.configs;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchpets.util.DisplayItem;
import me.reklessmitch.mitchprisonscore.mitchprofiles.object.ShopItem;
import me.reklessmitch.mitchprisonscore.mitchprofiles.object.WardrobeItem;
import org.bukkit.Material;

import java.util.List;
import java.util.Map;

@Getter
@EditorName("config")
public class ProfilesConf extends Entity<ProfilesConf> {
    public static ProfilesConf i;
    public static ProfilesConf get() { return i; }

    private List<String> joinMessages = List.of("%player% joined", "%player% is here");

    private Map<String, String> nameColoursPermToColour = Map.of(
            "mpc.chatcolour.red", "<red>",
            "mpc.chatcolour.green", "<green>",
            "mpc.chatcolour.blue", "<blue>",
            "mpc.chatcolour.yellow", "<yellow>",
            "mpc.chatcolour.purple", "<purple>",
            "mpc.chatcolour.white", "<white>",
            "mpc.chatcolour.black", "<black>",
            "mpc.chatcolour.gray", "<gray>");

    private Map<String, String> chatColoursPermToColour = Map.of(
            "mpc.chatcolour.red", "<red>",
            "mpc.chatcolour.green", "<green>",
            "mpc.chatcolour.blue", "<blue>",
            "mpc.chatcolour.yellow", "<yellow>",
            "mpc.chatcolour.purple", "<purple>",
            "mpc.chatcolour.white", "<white>",
            "mpc.chatcolour.black", "<black>",
            "mpc.chatcolour.gray", "<gray>");

    private final transient List<ShopItem> defaultShopItem = List.of(new ShopItem(1, 10, Material.DIAMOND, "<green>Rank1",
            "<green>Rank1", List.of("aa %player% 1"), List.of("<green>Cost: %cost%")));
    private final transient DisplayItem defaultDisplayItem =
            new DisplayItem(Material.DIAMOND,
                    "<green>Rank1",
                    List.of("aa %player% 1"),
                    0,
                    1);
    private final transient WardrobeItem defaultWardobeItem = new WardrobeItem(Material.DIAMOND, "<green>Penguin Backpack",
            List.of("Penguin Backpack"), 0, "cosmetics apply penguin", "mitch.backpack.penguin");

    Map<String, DisplayItem> storeCategories = Map.of("ranks", defaultDisplayItem,
            "gkits", defaultDisplayItem,
            "crates", defaultDisplayItem);
    Map<String, List<ShopItem>> storeItems = Map.of("ranks", defaultShopItem,
            "gkits", defaultShopItem,
            "crates", defaultShopItem);

    private String wardrobeTitle = "<green>Wardrobe";
    private DisplayItem backpackItem = defaultDisplayItem;
    private DisplayItem wingsItem = defaultDisplayItem;
    private DisplayItem armourItem = defaultDisplayItem;

    private Map<String, List<WardrobeItem>> wardrobeCategories = Map.of
            ("wings", List.of(defaultWardobeItem),
            "backpack", List.of(defaultWardobeItem),
            "armour", List.of(defaultWardobeItem));

    private Map<String, String> rankToPrefix = Map.of(
            "default", "default", "guardian", "guardian", "hero", "hero",
            "mythic", "mythic", "celestial", "celestial", "emperor", "emperor",
            "helper", "helper", "moderator", "moderator", "senior-moderator", "senior-moderator");

    private String chatFormat = "<prefix> <red><name>%deluxetags_tag%: <white>";

}
