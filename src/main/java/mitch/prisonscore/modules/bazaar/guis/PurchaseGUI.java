package mitch.prisonscore.modules.bazaar.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.bazaar.BazaarModule;
import mitch.prisonscore.modules.bazaar.object.ShopValue;
import mitch.prisonscore.modules.bazaar.events.GetAmountResponse;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.utils.ItemCreator;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.*;

public class PurchaseGUI extends ChestGui {

    private final ItemStack item; // item being sold
    private final Map<Currency, List<ShopValue>> sellPrices; // prices for item being sold
    private final Currency currencyToBeBrought; // item being brought

    public PurchaseGUI(ItemStack item, Currency itemToBeBrought) {
        setInventory(Bukkit.createInventory(null, 27, MessageUtils.colorize(LangConf.get().getBazaarGuiTitle())));
        this.currencyToBeBrought = itemToBeBrought;
        this.item = item;
        this.sellPrices = BazaarModule.get().getSellPrices().get(itemToBeBrought);
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        setup();
        add();
    }


    private void setup() {
        getInventory().setItem(4, ItemCreator.createItem(item.getType(), 1, 0, "<yellow>"
                + item.getType().name().toLowerCase(), "<grey>Click the currency", "<grey>you want to exchange!"));
        final List<Currency> currencies = List.of(Currency.BEACON, Currency.TOKEN, Currency.MONEY, Currency.CREDIT);
        int start = 10;
        for(Currency currency: currencies){
            Bukkit.broadcastMessage("Currency: " + currency +  " - slot: " + start);
            final List<ShopValue> sorted = sellPrices.get(currency);
            if(sorted == null) {
                getInventory().setItem(start, new ItemStack(Material.BARRIER));
                start += 2;
                continue;
            }
            sorted.sort(Comparator.comparing(ShopValue::getPricePerItem));
            final BigInteger totalStock = sorted.stream()
                    .map(ShopValue::getAmount)
                    .map(BigInteger::valueOf) // Convert long to BigInteger
                    .reduce(BigInteger.ZERO, BigInteger::add); // Sum all values

            final ItemStack currencyItem = ItemCreator.createItem(currency.getMaterial(), 1, 0, "<yellow>"
                            + currency.getName(), "<grey>Total Stock: <red>" + totalStock,
                            "<grey>Lowest Price" + (sorted.isEmpty() ? ": <red>N/A" :
                            "<green>: " + sorted.get(0).getAmount() + " @ " +
                            MitchPrisonsCore.get().getDecimalFormat().format(sorted.get(0).getPricePerItem()) + " each"));


            getInventory().setItem(start, currencyItem);
            setAction(start, event -> {
                new GetAmountResponse((Player) event.getWhoClicked(), currencyToBeBrought, currency, totalStock);
                return true;
            });
            start += 2;
        }
    }

    public void open(Player player) {
        player.openInventory(getInventory());
    }
}
