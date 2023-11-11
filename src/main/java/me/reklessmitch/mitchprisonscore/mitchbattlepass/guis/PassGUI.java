package me.reklessmitch.mitchprisonscore.mitchbattlepass.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import me.reklessmitch.mitchprisonscore.mitchbattlepass.configs.PassConf;
import me.reklessmitch.mitchprisonscore.mitchbattlepass.configs.PassPlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.Currency;
import me.reklessmitch.mitchprisonscore.utils.ItemCreator;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;

public class PassGUI extends ChestGui {

    private final Player player;

    public PassGUI(Player player){
        this.player = player;
        setInventory(Bukkit.createInventory(null, 27, MessageUtils.colorize(LangConf.get().getPassGuiTitle())));
        setupInventory();
        add();
    }

    private void setUpUnpaid(ProfilePlayer profilePlayer, PassPlayer pp, int cost) {
        final ItemStack item = ItemCreator.createItem(Material.BARRIER, 1, 0, "<green>Buy Battle Pass", "<grey>Click to buy the battle pass", "", "<green>Cost: <white>" + cost);
        getInventory().setItem(15, item);
        setAction(15, event -> {
            if (profilePlayer.getCurrencyAmount(Currency.CREDIT).compareTo(BigInteger.valueOf(cost)) < 0) {
                MessageUtils.sendMessages(player, LangConf.get().getBattlePassNotEnoughCredits());
                return true;
            }
            pp.setPremium(true);
            profilePlayer.take(Currency.CREDIT, BigInteger.valueOf(cost));
            MessageUtils.sendMessages(player, LangConf.get().getBattlePassPurchaseSuccess());
            return true;
        });
    }

    private void setUpPaid() {
        final ItemStack item = ItemCreator.createItem(Material.BARRIER, 1, 0, "<green>Battle Pass", "<grey>You already have the battle pass", "", "<green>Click to claim rewards");
        getInventory().setItem(15, item);
        setAction(15, event -> {
            PassPlayer.get(player.getUniqueId()).claimPaidRewards();
            return true;
        });
        getInventory().setItem(26, ItemCreator.createItem(Material.BARRIER, 1, 0, "<green>Battle Pass", "<green>Click to claim all rewards"));
        setAction(26, event -> {
            PassPlayer.get(player.getUniqueId()).claimAllRewards();
            return true;
        });
    }

    private void setupInventory() {
        int cost = PassConf.get().getCreditsToBuyPremium();
        ProfilePlayer profile = ProfilePlayer.get(player.getUniqueId());
        PassPlayer pp = PassPlayer.get(player.getUniqueId());
        if(!pp.isPremium()) {
            setUpUnpaid(profile, pp, cost);
        } else {
            setUpPaid();
        }
        getInventory().setItem(11, ItemCreator.createItem(Material.IRON_INGOT, 1, 0, "<green>Free Rewards", "<grey>Click to claim free rewards"));
        setAction(11, event -> {
            PassPlayer.get(player.getUniqueId()).claimFreeRewards();
            return true;
        });

    }

    public void open() {
        player.openInventory(getInventory());
    }
}
