package me.reklessmitch.mitchprisonscore.mitchbazaar.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.mitchbazaar.config.BazaarConf;
import me.reklessmitch.mitchprisonscore.mitchbazaar.object.ShopValue;
import me.reklessmitch.mitchprisonscore.mitchbazaar.runnables.SignOverGUI;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilesConf;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.Currency;
import me.reklessmitch.mitchprisonscore.utils.ItemCreator;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class PurchaseGUI extends ChestGui {

    private final ItemStack item; // item being sold
    private final Map<Currency, List<ShopValue>> sellPrices; // prices for item being sold
    private final Currency currencyToBeBrought; // item being brought

    public PurchaseGUI(ItemStack item, Currency itemToBeBrought) {
        setInventory(Bukkit.createInventory(null, 27, MessageUtils.colorize(LangConf.get().getBazaarGuiTitle())));
        this.currencyToBeBrought = itemToBeBrought;
        this.item = item;
        this.sellPrices = BazaarConf.get().getSellPrices().get(itemToBeBrought);
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        setup();
        add();
    }


    private void setup() {
        getInventory().setItem(4, ItemCreator.createItem(item.getType(), 1, 0, "<yellow>"
                + item.getType().name().toLowerCase(), "<grey>Click the currency", "<grey>you want to exchange!"));
        final List<Currency> currencies = Arrays.stream(Currency.values()).toList();
        for(int i = 0; i < currencies.size(); i++){
            final Currency currency = currencies.get(i);
            final List<ShopValue> sorted = sellPrices.get(currency);
            if(sorted == null) {
                getInventory().setItem(10 + i * 2, new ItemStack(Material.BARRIER));
                continue;
            }
            sorted.sort(Comparator.comparing(ShopValue::getPricePerItem));
            final BigInteger totalStock = sorted.stream()
                    .map(ShopValue::getAmount)
                    .map(BigInteger::valueOf) // Convert long to BigInteger
                    .reduce(BigInteger.ZERO, BigInteger::add); // Sum all values
            final ItemStack currencyItem = ItemCreator.createItem(currency.getMaterial(), 1, 0, "<yellow>"
                            + currencies.get(i).getName(), "<grey>Total Stock: <red>" + totalStock,
                            "<grey>Lowest Price" + (sorted.isEmpty() ? ": <red>N/A" :
                            "<green>: " + sorted.get(0).getAmount() + " @ " +
                            MitchPrisonsCore.get().getDecimalFormat().format(sorted.get(0).getPricePerItem()) + "per"));

            getInventory().setItem(10 + i * 2, currencyItem);

            setAction(10 + i * 2, event -> {
                event.getWhoClicked().closeInventory();
                new SignOverGUI((Player) event.getWhoClicked(), currencyToBeBrought, currency, totalStock).runTask(MitchPrisonsCore.get());
                return true;
            });
        }
    }

    public void open(Player player) {
        player.openInventory(getInventory());
    }
}
