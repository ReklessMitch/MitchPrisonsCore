package mitch.prisonscore.modules.booster.utils;

import com.google.common.collect.Lists;

import com.massivecraft.massivecore.command.type.TypeAbstract;
import org.bukkit.command.CommandSender;

import java.util.Collection;

public class TypeBooster extends TypeAbstract<BoosterType> {

    private static final TypeBooster i = new TypeBooster();
    public static TypeBooster get() {
        return i;
    }

    public TypeBooster() {
        super(BoosterType.class);
    }

    @Override
    public BoosterType read(String s, CommandSender commandSender) {
        return BoosterType.valueOf(s.toUpperCase());
    }

    @Override
    public Collection<String> getTabList(CommandSender commandSender, String s) {
        return Lists.newArrayList("TOKEN", "MONEY", "BEACON");
    }
}