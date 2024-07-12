package mitch.prisonscore.modules.crystals.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.modules.crystals.CrystalModule;
import mitch.prisonscore.modules.crystals.configs.CrystalPlayer;
import mitch.prisonscore.modules.crystals.objects.Crystal;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.utils.Currency;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.util.List;

public class CrystalGUI extends ChestGui {

    private Player player;

    public CrystalGUI(Player player) {
        this.player = player;
        this.setInventory(Bukkit.createInventory(null, 45, "Crystals"));
        guiSetup();
        setAutoclosing(false);
        add();
    }

    public void guiSetup() {
        // Add items to the inventory
        TagResolver dustResolver = Placeholder.parsed("dust", ProfilePlayer.get(player.getUniqueId()).getCurrencyAmount(Currency.DUST).toString());
        getInventory().setItem(4, CrystalModule.get().getCrystalFormat().getFormatItem(dustResolver));
        CrystalPlayer crystalPlayer = CrystalPlayer.get(player.getUniqueId());
        List<Crystal> crystals = crystalPlayer.getCrystals();
        int moveDown = 9;
        for(int i = moveDown; i < crystals.size() + moveDown ; i++) {
            Crystal crystal = crystals.get(i - moveDown);
            getInventory().setItem(i, crystal.getCrystalItem());
            setAction(i, event -> {
                switch(event.getClick()) {
                    case LEFT -> player.sendMessage("Selected crystal");
                    case DROP -> {
                        crystalPlayer.tinker(crystal);
                        getInventory().close();
                    }
                    case RIGHT -> addDustToCrystal(crystal, 1);
                    case MIDDLE -> addDustToCrystal(crystal, 100 - crystal.getBoostAmount());
                    case NUMBER_KEY -> addDustToCrystal(crystal, event.getHotbarButton() + 1);
                    default -> {}
                }
                event.setCancelled(true);
                return false;
            });
        }
    }

    private void addDustToCrystal(Crystal crystal, int amount) {
        ProfilePlayer profilePlayer = ProfilePlayer.get(player.getUniqueId());
        BigInteger currencyBal = profilePlayer.getCurrencyAmount(Currency.DUST);
        if(currencyBal.compareTo(BigInteger.valueOf(amount)) < 0) {
            player.sendMessage("You do not have enough dust to add to the crystal");
            return;
        }
        if(crystal.getBoostAmount() + amount > 100) {
            player.sendMessage("You cannot add more than 100 dust to a crystal");
            int removeAmount = 100 - crystal.getBoostAmount();
            profilePlayer.take(Currency.DUST, removeAmount);
            crystal.setBoostAmount(100);
        }else{
            crystal.addBoostAmount(amount);
            profilePlayer.take(Currency.DUST, amount);
        }
        CrystalPlayer crystalPlayer = CrystalPlayer.get(player.getUniqueId());
        crystalPlayer.changed();
        player.closeInventory();

    }

    public void open() {
        player.openInventory(getInventory());
    }
}
