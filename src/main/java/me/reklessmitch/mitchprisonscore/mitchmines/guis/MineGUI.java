package me.reklessmitch.mitchprisonscore.mitchmines.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import me.clip.placeholderapi.PlaceholderAPI;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.MineConf;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.PlayerMine;
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

public class MineGUI extends ChestGui {

    private final Player player;
    private final PlayerMine playerMine;
    private final LangConf langConf = LangConf.get();

    public MineGUI(Player player){
        this.player = player;
        this.playerMine = PlayerMine.get(player.getUniqueId());
        setInventory(Bukkit.createInventory(null, 54, MessageUtils.colorize(langConf.getMineGuiTitle())));
        init();
        setAutoclosing(false);
        add();
    }

    private void addMineInformationItem(){
        int[] slots = {0, 1, 2, 9, 10, 11, 18, 19, 20, 27, 28, 29, 36, 37, 38, 45, 46, 47};
        for(int slot: slots) {
            final ItemStack item = ItemCreator.createItem(Material.PAPER, 1, langConf.getInvisibleCustomData(), "<gold>Mine GO", PlaceholderAPI.setPlaceholders(player, langConf.getMineGoGUIItem()));
            getInventory().setItem(slot, item);
            this.setAction(slot, event -> {
                playerMine.teleport();
                return true;
            });
        }

    }

    private void addUpgradeBoosterItem(){
        int[] slots = {6, 7, 8, 15, 16, 17, 24, 25, 26, 33, 34, 35, 42, 43, 44, 51, 52, 53};
        MineConf conf = MineConf.get();
        int cost = conf.getMineBoosterCost();
        int maxLevel = conf.getMineBoosterMax();

        boolean maxed = playerMine.getBooster() >= maxLevel;
        final ItemStack item = maxed ?
                ItemCreator.createItem(Material.PAPER, 1, langConf.getInvisibleCustomData(), "<gold>Upgrade Booster",
                        PlaceholderAPI.setPlaceholders(player, langConf.getMineBoosterMaxed())) :
                ItemCreator.createItem(Material.PAPER, 1, langConf.getInvisibleCustomData(), "<gold>Upgrade Booster",
                        PlaceholderAPI.setPlaceholders(player, langConf.getMineBoosterNotMaxed()));
        for(int slot: slots) {
            getInventory().setItem(slot, item);
            this.setAction(slot, event -> {
                if(maxed) {
                    event.getWhoClicked().sendMessage(PlaceholderAPI.setPlaceholders(player, langConf.getMineMaxMineBooster()));
                    return true;
                }
                final ProfilePlayer profile = ProfilePlayer.get(player.getUniqueId());
                if (profile.getCurrencyAmount(Currency.CREDIT).intValue() >= cost) {
                    profile.take(Currency.CREDIT, BigInteger.valueOf(cost));
                    playerMine.addBooster(1);
                    addUpgradeBoosterItem();
                } else {
                    MessageUtils.sendMessage(player, langConf.getMineNotEnoughCredits());
                }
                return true;
            });
        }
    }

    private void addResetItem(){
        int[] slots = {3, 4 ,5, 12, 13, 14, 21, 22, 23, 30, 31, 32, 39, 40, 41, 48, 49, 50};
        for(int slot: slots) {
            final ItemStack item = ItemCreator.createItem(Material.PAPER, 1, LangConf.get().getInvisibleCustomData(), "<gold>Reset Mine", "<red>Reset your mine");
            getInventory().setItem(slot, item);
            this.setAction(slot, event -> {
                playerMine.reset();
                return true;
            });
        }
    }

    private void init() {
        addMineInformationItem();
        addUpgradeBoosterItem();
        addResetItem();
    }

    public void open() {
        player.openInventory(getInventory());
    }
}
