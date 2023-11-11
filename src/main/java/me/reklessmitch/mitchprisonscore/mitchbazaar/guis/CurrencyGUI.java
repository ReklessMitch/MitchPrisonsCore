package me.reklessmitch.mitchprisonscore.mitchbazaar.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.Currency;
import me.reklessmitch.mitchprisonscore.utils.ItemCreator;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CurrencyGUI extends ChestGui {

    Player player;

    public CurrencyGUI(Player player) {
        this.player = player;
        setInventory(Bukkit.createInventory(null, 27, MessageUtils.colorize(LangConf.get().getBazaarGuiTitle())));
        setup();
        add();
    }

    private void setUpAction(int slot, ItemStack item, Currency itemToBeBrought){
        getInventory().setItem(slot, item);
        setAction(slot, event -> {
            new PurchaseGUI(item, itemToBeBrought).open(player);
            return true;
        });
    }
    private void setup() {
        setUpAction(10, ItemCreator.createItem(Material.BEACON, 1, 0, "<green>Beacon",
                "<grey>Click to buy/sell beacons"), Currency.BEACON);
        setUpAction(12, ItemCreator.createItem(Material.EMERALD, 1, 0, "<green>Token",
                "<grey>Click to buy/sell tokens"), Currency.TOKEN);
        setUpAction(14, ItemCreator.createItem(Material.GOLD_INGOT, 1, 0, "<green>Money",
                "<grey>Click to buy/sell money"), Currency.MONEY);
        setUpAction(16, ItemCreator.createItem(Material.DIAMOND, 1, 0, "<green>Credits",
                "<grey>Click to buy/sell credits"), Currency.CREDIT);

    }

    public void open() {
        player.openInventory(getInventory());
    }

}
