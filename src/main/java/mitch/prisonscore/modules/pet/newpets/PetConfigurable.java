package mitch.prisonscore.modules.pet.newpets;

import mitch.prisonscore.colls.EnchantmentConfigColl;
import mitch.prisonscore.colls.PetConfigColl;
import mitch.prisonscore.modules.pickaxe.enchants.EnchantmentConfig;

import java.util.Arrays;

public interface PetConfigurable <T extends PetConfig> {

    String getName();

    default Class<? extends PetConfig> getConfigClass() {
        return Arrays.stream(getClass().getDeclaredClasses()).filter(PetConfig.class::isAssignableFrom).map(cls ->
                (Class<? extends PetConfig>)cls).findAny().orElse(null);
    }

    default T getConfig() {
        return (T) PetConfigColl.get().get(getName(), true);
    }

}