package mitch.prisonscore.modules.profile.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.modules.profile.ProfileModule;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.object.ShopItem;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class StoreShop extends ChestGui {

    private final Player player;
    private final ProfilePlayer profilePlayer;
    List<ShopItem> shopItems;

    public StoreShop(Player player, String store) {
        this.shopItems = ProfileModule.get().getStoreItems().get(store);
        this.player = player;
        this.profilePlayer = ProfilePlayer.get(player.getUniqueId());
        setInventory(Bukkit.createInventory(null, 18, store));
        setUpInventory();
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        add();
    }

    private void setUpInventory() {

        shopItems.forEach(shopItem -> {
            this.getInventory().setItem(shopItem.getSlot(), shopItem.getGuiItem());
            int cost = shopItem.getCost();
            this.setAction(shopItem.getSlot(), event -> {
                if (profilePlayer.getCurrencyAmount(Currency.CREDIT).compareTo(BigInteger.valueOf(cost)) >= 0) {
                    profilePlayer.take(Currency.CREDIT, BigInteger.valueOf(cost));
                    TagResolver shopItemName = Placeholder.parsed("item", shopItem.getName());
                    TagResolver shopItemCost = Placeholder.parsed("cost", String.valueOf(cost));
                    MessageUtils.sendMessages(player, LangConf.get().getStorePurchaseSuccess(), shopItemName, shopItemCost);
                    List<String> commands = new ArrayList<>(shopItem.getCommands());
                    commands.replaceAll(c -> c.replace("%player%", player.getName()));
                    commands.forEach(c -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c));
                } else {
                    MessageUtils.sendMessages(player, LangConf.get().getStoreNotEnoughCredits());}
                return true;
            });
        });
    }

    public void open() {
        player.openInventory(getInventory());
    }
}
