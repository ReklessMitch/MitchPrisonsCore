package mitch.prisonscore.modules;


import com.massivecraft.massivecore.store.Coll;
import com.massivecraft.massivecore.store.MStore;
import mitch.prisonscore.MitchPrisonsCore;

public class ModuleColl extends Coll<Module> {

    private static ModuleColl i = new ModuleColl();
    public static ModuleColl get() {return i;}

    public ModuleColl() {
        super("vamp", Module.class, MStore.getDb(), MitchPrisonsCore.get());
        this.setCreative(false);
    }

    @Override
    public boolean getClassFromRemote() {return true;}

    @Override
    public void onTick() {
        super.onTick();
    }

    @Override
    public String fixId(Object oid) {
        if (oid == null) {return null;}
        if (oid instanceof String o) {return o;}
        if (oid instanceof Module module){
            System.out.println("Returning module: {FUCKKKKK}" + module.getName());
            return module.getName().replace(" ", "");}
        return null;
    }
}

