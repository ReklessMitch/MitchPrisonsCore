package mitch.prisonscore.modules.booster.engines;

import com.massivecraft.massivecore.Engine;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.booster.BoosterModule;
import mitch.prisonscore.modules.booster.configs.BoosterPlayer;
import mitch.prisonscore.modules.booster.objects.Booster;
import mitch.prisonscore.modules.booster.utils.BoosterType;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BoosterInteract extends Engine {

    private static BoosterInteract i = new BoosterInteract();
    public static BoosterInteract get() { return i; }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        PlayerInventory inv = event.getPlayer().getInventory();
        ItemMeta meta = inv.getItemInMainHand().getItemMeta();
        if(meta == null){
            return;
        }

        if(!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        if(pdc.isEmpty()) return;
        BoosterModule module = BoosterModule.get();
        if(pdc.has(module.getTypeKey(), PersistentDataType.STRING)
            && pdc.has(module.getMultiKey(), PersistentDataType.DOUBLE)
            && pdc.has(module.getDurationKey(), PersistentDataType.LONG)) {
            String id = pdc.get(module.getTypeKey(), PersistentDataType.STRING);

            BoosterType type = BoosterType.valueOf(id);
            double multiplier = pdc.get(module.getMultiKey(), PersistentDataType.DOUBLE);
            long time = pdc.get(module.getDurationKey(), PersistentDataType.LONG);
            BoosterPlayer boosterPlayer = BoosterPlayer.get(event.getPlayer().getUniqueId());
            if(boosterPlayer.getBoosters().size() >= 35){
                MessageUtils.sendMessage(event.getPlayer(), LangConf.get().getBoostersTooMany());
                return;
            }
            final TagResolver multiResolver = Placeholder.parsed("multiplier", String.valueOf(multiplier));
            final TagResolver typeResolver = Placeholder.parsed("type", type.getName());
            final TagResolver timeResolver = Placeholder.parsed("time", String.valueOf(time));

            MessageUtils.sendMessage(event.getPlayer(), LangConf.get().getBoosterAdded(), multiResolver, typeResolver, timeResolver);
            boosterPlayer.getBoosters().add(new Booster(type, multiplier, time));
            inv.getItemInMainHand().setAmount(inv.getItemInMainHand().getAmount() - 1);
        }
    }
}
