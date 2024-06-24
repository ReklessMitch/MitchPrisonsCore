package mitch.prisonscore.modules;

import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import lombok.Setter;
import mitch.prisonscore.modules.type.ModuleType;
@SuppressWarnings("FieldMayBeFinal")
public abstract class Module extends Entity<Module> {

    @Getter @Setter private boolean enabled;

    protected Module(boolean enabled) {
        this.enabled = enabled;
    }

    public abstract ModuleType getType();

    public String getName() {
        return this.getType().getName();
    }

    public abstract void enable();

    public abstract void disable();

}