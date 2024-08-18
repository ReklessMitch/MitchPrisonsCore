package mitch.prisonscore.modules.profile.cmds.wardrobe;

import com.massivecraft.massivecore.MassiveException;
import mitch.prisonscore.modules.profile.cmds.CurrencyCommands;
import mitch.prisonscore.utils.ItemCreator;
import mitch.prisonscore.utils.PacketUtils;
import org.bukkit.Material;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class CmdWings extends CurrencyCommands {

    private static CmdWings i = new CmdWings();
    public static CmdWings get() { return i; }

    public CmdWings() {
        this.addAliases("wings");
    }

    @Override
    public void perform() throws MassiveException {
        ArmorStand stand = me.getWorld().spawn(me.getLocation(), ArmorStand.class);
        ItemStack book = ItemCreator.createItem(Material.BOOK, 1, 3);
        stand.setArms(true);
        stand.setItem(EquipmentSlot.OFF_HAND, book);
        stand.setInvisible(true);
        PacketUtils.sendMountPacket(
                stand.getEntityId(),
                new int[]{me.getEntityId()},
                me.getWorld().getPlayers());
    }
}
