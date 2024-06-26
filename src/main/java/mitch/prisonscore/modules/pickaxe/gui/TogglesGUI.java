package mitch.prisonscore.modules.pickaxe.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.modules.pickaxe.MitchPickaxeModule;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.modules.pickaxe.enchants.Enchant;
import mitch.prisonscore.modules.pickaxe.utils.EnchantType;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class TogglesGUI extends ChestGui {

    private final Player player;
    private final int toggle;// true = Enchant Toggles, false = Enchant Message Toggles

    //- Message Toggles :red_circle: - 0
    //- Enchant Toggles :red_circle: - 1
    //- Enchant Sounds :red_circle: - 2

    public TogglesGUI(@NotNull Player player, int toggle) {
        this.toggle = toggle;
        this.player = player;
        setInventory(Bukkit.createInventory(null, 27, MessageUtils.colorize(LangConf.get().getPickaxeTogglesGuiTitle())));
        refresh();
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        add();
    }

    public void refresh(){
        int startSlot = 0;
        for (Map.Entry<EnchantType, Enchant> entry : MitchPickaxeModule.get().getEnchants().entrySet()) {
            EnchantType type = entry.getKey();
            PickaxePlayer pickaxe = PickaxePlayer.get(player.getUniqueId());
            getInventory().setItem(startSlot, entry.getValue().getEnchantGuiToggleItem(pickaxe));

            this.setAction(startSlot, event -> {
                event.setCancelled(true);

                //- Message Toggles :red_circle: - 0
                //- Enchant Toggles :red_circle: - 1
                //- Enchant Sounds :red_circle: - 2

                switch (toggle) {
                    case 1 -> pickaxe.toggleEnchant(type);
                    case 0 -> pickaxe.toggleEnchantMessage(type);
                    case 2 -> pickaxe.toggleEnchantSound(type);
                    default -> {
                    }
                }

                refresh();
                return true;
            });

            startSlot++;
        }
    }

    public void open(){
        player.openInventory(getInventory());
    }
}
