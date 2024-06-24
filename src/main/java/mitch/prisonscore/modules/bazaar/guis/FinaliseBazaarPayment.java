package mitch.prisonscore.modules.bazaar.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.IdUtil;
import mitch.prisonscore.modules.bazaar.BazaarModule;
import mitch.prisonscore.modules.bazaar.object.ShopValue;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.modules.profile.utils.CurrencyUtils;
import mitch.prisonscore.utils.ItemCreator;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.*;

public class FinaliseBazaarPayment extends ChestGui {

    private final BigInteger amount;
    private final mitch.prisonscore.modules.profile.utils.Currency currencyToBeBrought;
    private final mitch.prisonscore.modules.profile.utils.Currency currencyToBuyWith;
    private Map<ShopValue, Long> cheapestItems = new HashMap<>();
    private final Player player;


    public FinaliseBazaarPayment(Player player, BigInteger amount, mitch.prisonscore.modules.profile.utils.Currency currencyToBeBrought, Currency currencyToBuyWith) {
        this.player = player;
        this.currencyToBeBrought = currencyToBeBrought;
        this.currencyToBuyWith = currencyToBuyWith;
        this.amount = amount;
        setInventory(Bukkit.createInventory(null, 9, "Finalise Payment"));
        perform();
        add();
        setAutoclosing(true);
    }

    public void perform() {
        BigInteger cost = getCost();
        for(int i = 0; i < 4; i++) {
            ItemStack item = ItemCreator.createItem(Material.GREEN_STAINED_GLASS_PANE, 1, 0, "<green>Confirm Payment", "<grey>Click to confirm payment");
            getInventory().setItem(i, item);
            setAction(i, event -> {
                ProfilePlayer.get(player.getUniqueId()).take(currencyToBuyWith, cost);
                MessageUtils.sendMessage(player, "<green>You have brought " + amount + " " + currencyToBeBrought.getName() + " for " + CurrencyUtils.format(cost) + "<yellow>" + currencyToBuyWith + "/s<yellow>.");
                confirmPayment();
                return true;
            });
        }
        getInventory().setItem(4, getGuiItem(cost));
        for(int i = 5; i < 9; i++) {
            ItemStack item = ItemCreator.createItem(Material.RED_STAINED_GLASS_PANE, 1, 0, "<red>Cancel Payment", "<grey>Click to cancel payment");
            getInventory().setItem(i, item);
            setAction(i, event -> {
                MessageUtils.sendMessage(player, "<red>You have cancelled the payment.");
                return true;
            });
        }
    }

    private void confirmPayment() {
        cheapestItems.forEach((shopValue, a) -> {
            OfflinePlayer p = IdUtil.getOfflinePlayer(shopValue.getOwner());
            long costOfItem = (long) (a * shopValue.getPricePerItem());

            ProfilePlayer.get(shopValue.getOwner()).addCurrency(currencyToBuyWith, costOfItem);
            if(p.isOnline()){
                MessageUtils.sendMessage(p.getPlayer(), "<green>Your item has been brought from the bazaar! ( "+ costOfItem + " " + currencyToBuyWith + " )");
            }
            long newAmount = shopValue.getAmount() - a;
            BazaarModule conf = BazaarModule.get();
            if(newAmount <= 0){

                conf.getSellPrices().get(currencyToBeBrought).get(currencyToBuyWith).remove(shopValue);
            }else {
                shopValue.setAmount(newAmount);
            }
            conf.changed();
        });

    }

    private ItemStack getGuiItem(BigInteger cost) {
        return ItemCreator.createItem(Material.PAPER, 1, 0, "<yellow>Finalise Payment",
                "<gray>You are about to buy/sell <yellow>" + amount + " " + currencyToBeBrought.getName() +
                        "<grey> for " + CurrencyUtils.format(cost) + "<yellow>" + currencyToBuyWith.getName() +
                        "/s<grey>.", "<grey>Click the <green>Green <grey>to confirm or the <red>Red <grey>to cancel.");
    }

    private BigInteger getCost(){
        cheapestItems = new HashMap<>();
        List<ShopValue> shopPrices = BazaarModule.get().getSellPrices().get(currencyToBeBrought).get(currencyToBuyWith);
        shopPrices.sort(Comparator.comparing(ShopValue::getPricePerItem));
        BigInteger x = amount;
        BigInteger cost = BigInteger.ZERO;

        for (ShopValue item : shopPrices) {
            BigInteger quantityToAdd = x.min(BigInteger.valueOf(item.getAmount())); // Use min with BigIntegers
            BigInteger itemCost = quantityToAdd.multiply(BigInteger.valueOf(item.getPricePerItem().longValue())); // Use multiply for BigInteger

            cost = cost.add(itemCost); // Use add for BigInteger

            cheapestItems.put(item, quantityToAdd.longValue()); // Assuming cheapestItems is a Map<ShopValue, Long>
            x = x.subtract(quantityToAdd);

            if (x.compareTo(BigInteger.ZERO) <= 0) {
                break;
            }
        }

        return cost;
    }

    public void open() {
        player.openInventory(getInventory());
    }
}
