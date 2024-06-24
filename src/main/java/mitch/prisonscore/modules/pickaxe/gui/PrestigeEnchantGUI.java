package mitch.prisonscore.modules.pickaxe.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;

import mitch.prisonscore.modules.pickaxe.enchants.Enchant;
import mitch.prisonscore.utils.ItemCreator;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class PrestigeEnchantGUI extends ChestGui {


    private final Enchant enchant;
    private final Player player;
    private final PickaxePlayer p;

    public PrestigeEnchantGUI(@NotNull Enchant enchant, Player player) {
        this.enchant = enchant;
        this.player = player;
        this.p = PickaxePlayer.get(player);
        setInventory(Bukkit.createInventory(null, 9, MessageUtils.colorize("<green>Confirm prestige")));
        refresh();
        setAutoclosing(true);
        setSoundOpen(null);
        setSoundClose(null);
        setAutoremoving(true);
        add();
    }

    public void refresh(){
        getInventory().setItem(4, enchant.getEnchantGuiItem(p));
        for(int i = 0; i < 4; i++) {
            TagResolver enchantNameResolver = Placeholder.parsed("enchant", enchant.getType().name());
            getInventory().setItem(i, ItemCreator.createItem(Material.GREEN_STAINED_GLASS_PANE, 1,
                    0, "<green>Confirm Prestige", "<green>Click to confirm the prestige of " + enchant.getType().name()));
            setAction(i, event -> {
                MessageUtils.sendMessage(player, LangConf.get().getPrestigeEnchantSuccess(), enchantNameResolver);
                p.getEnchants().replace(enchant.getType(), 0);
                p.getEnchantPrestiges().replace(enchant.getType(), p.getEnchantPrestiges().get(enchant.getType()) + 1);
                p.updatePickaxe();
                return true;
            });
        }

        for(int i = 5; i < 9; i++) {
            getInventory().setItem(i, ItemCreator.createItem(Material.RED_STAINED_GLASS_PANE, 1,
                    0, "<red>Cancel Prestige", "<red>Click to cancel the prestige of " + enchant.getType().name()));
            setAction(i, event -> {
                MessageUtils.sendMessage(player, LangConf.get().getPrestigeEnchantCancelled());
                return true;
            });
        }
    }

    public void open(){
        player.openInventory(getInventory());
    }

}
