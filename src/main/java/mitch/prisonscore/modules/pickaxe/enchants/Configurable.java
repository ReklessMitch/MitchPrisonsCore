package mitch.prisonscore.modules.pickaxe.enchants;

import mitch.prisonscore.colls.EnchantmentConfigColl;

import java.util.Arrays;

public interface Configurable<T extends EnchantmentConfig> {

    String getName();

    default Class<? extends EnchantmentConfig> getConfigClass() {
        return Arrays.stream(getClass().getDeclaredClasses()).filter(EnchantmentConfig.class::isAssignableFrom).map(cls ->
                (Class<? extends EnchantmentConfig>)cls).findAny().orElse(null);
    }

    default T getConfig() {
        return (T) EnchantmentConfigColl.get().get(getName(), true);
    }

}