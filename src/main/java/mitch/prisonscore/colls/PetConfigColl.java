package mitch.prisonscore.colls;

import com.massivecraft.massivecore.store.Coll;
import com.massivecraft.massivecore.store.MStore;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.pet.newpets.Pet;
import mitch.prisonscore.modules.pet.newpets.PetConfig;

public class PetConfigColl extends Coll<PetConfig> {

    private static final PetConfigColl i = new PetConfigColl();
    public static PetConfigColl get() {
        return i;
    }

    private PetConfigColl() {
        super("mpc_pets", PetConfig.class, MStore.getDb(), MitchPrisonsCore.get());

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
        if (oid instanceof Pet e) return e.getName();

        return null;
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
    }

}
