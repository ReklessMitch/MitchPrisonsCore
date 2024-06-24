package mitch.prisonscore.colls;

import com.massivecraft.massivecore.store.Coll;
import com.massivecraft.massivecore.store.MStore;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.pickaxe.enchants.Enchant;
import mitch.prisonscore.modules.pickaxe.enchants.EnchantmentConfig;

public class EnchantmentConfigColl extends Coll<EnchantmentConfig> {

    private static final EnchantmentConfigColl i = new EnchantmentConfigColl();
    public static EnchantmentConfigColl get() {
        return i;
    }

    private EnchantmentConfigColl() {
        super("mpc_enchantments", EnchantmentConfig.class, MStore.getDb(), MitchPrisonsCore.get());

        setCreative(false);
    }

    @Override
    public void onTick() {
        super.onTick();
    }

    @Override
    public boolean getClassFromRemote() {
        return true;
    }

    @Override
    public String fixId(Object oid) {
        if (oid == null) return null;
        if (oid instanceof String s) return s;
        if (oid instanceof Enchant e) return e.getName();

        return null;
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
    }

}
