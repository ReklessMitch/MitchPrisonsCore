package mitch.prisonscore.modules.crates.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.modules.crates.CratesModule;
import mitch.prisonscore.modules.crates.configs.CratePlayer;
import mitch.prisonscore.modules.crates.objects.Crate;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CrateOpen extends ChestGui {

    private final Player player;

    public CrateOpen(Player player) {
        this.player = player;
        setInventory(Bukkit.createInventory(null, 27, "Crate"));
        setup();
        add();

    }

    private void checkAmount(int amountOfKeysPlayerHas, Crate crate, String crateName, int amountOfKeysNeeded){
        LangConf lang = LangConf.get();
        if(amountOfKeysPlayerHas == 0){
            MessageUtils.sendMessage(player, lang.getNoCrateKeyForCrate());
            return;
        }
        if(amountOfKeysPlayerHas < amountOfKeysNeeded){
            TagResolver crateNameResolver = Placeholder.parsed("crate", crateName);
            TagResolver amountResolver = Placeholder.parsed("amount", String.valueOf(amountOfKeysNeeded));
            MessageUtils.sendMessage(player, lang.getCrateKeyNotEnoughAmount(), crateNameResolver, amountResolver);
            return;
        }
        crate.getMultipleRewards(amountOfKeysNeeded).forEach(reward -> reward.executeReward(player, crateName));
    }


    private void setup() {
        CratesModule.get().getCrateGUIItems().forEach((crateName, displayItem) -> {
            final int slot = displayItem.getSlot();
            final Crate crate = CratesModule.get().getCrate(crateName);
            final CratePlayer cratePlayer = CratePlayer.get(player);
            int amount = 0;
            try{
                amount = cratePlayer.getCrateKeys().get(crateName);
            } catch (NullPointerException e){
                cratePlayer.getCrateKeys().put(crateName, 0);
            }
            final TagResolver crateAmountResolver = Placeholder.parsed("amount", String.valueOf(amount));
            getInventory().setItem(slot, displayItem.getGuiItem(crateAmountResolver));

            int finalAmount = amount;
            setAction(slot, event -> {
                switch (event.getClick()) {
                    case LEFT -> checkAmount(finalAmount, crate, crateName, 1);
                    case RIGHT -> new CratePreview(player, crate).open();
                    case DROP -> checkAmount(finalAmount, crate, crateName, 10);
                    case MIDDLE -> checkAmount(finalAmount, crate, crateName, finalAmount);
                    case NUMBER_KEY -> checkAmount(finalAmount, crate, crateName, event.getHotbarButton() + 1);
                    default -> {}
                }
                return true;
            });
        });
    }

    public void open() {
        player.openInventory(getInventory());
    }




}
