package mitch.prisonscore.modules.crates.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.modules.crates.CratesModule;
import mitch.prisonscore.modules.crates.configs.CratePlayer;
import mitch.prisonscore.modules.crates.objects.Crate;
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
        if(amountOfKeysPlayerHas == 0){
            player.sendMessage("You do not have any keys for this crate!");
            return;
        }
        if(amountOfKeysPlayerHas < amountOfKeysNeeded){
            final String message = "You do not have %d %s keys to open, try a different option!".formatted(amountOfKeysNeeded, crateName);
            player.sendMessage(message);
            return;
        }
        crate.getMultipleRewards(amountOfKeysNeeded).forEach(reward -> reward.executeReward(player, crateName));
    }


    private void setup() {
        CratesModule.get().getCrateGUIItems().forEach((crateName, displayItem) -> {
            final int slot = displayItem.getSlot();
            final Crate crate = CratesModule.get().getCrate(crateName);
            final CratePlayer cratePlayer = CratePlayer.get(player);
            final int amount = cratePlayer.getCrateKeys().get(crateName);
            final TagResolver crateAmountResolver = Placeholder.parsed("amount", String.valueOf(amount));
            getInventory().setItem(slot, displayItem.getGuiItem(crateAmountResolver));
            setAction(slot, event -> {
                switch (event.getClick()) {
                    case LEFT -> checkAmount(amount, crate, crateName, 1);
                    case RIGHT -> new CratePreview(player, crate).open();
                    case DROP -> checkAmount(amount, crate, crateName, 10);
                    case MIDDLE -> checkAmount(amount, crate, crateName, amount);
                    case NUMBER_KEY -> checkAmount(amount, crate, crateName, event.getHotbarButton() + 1);
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
