package mitch.prisonscore.modules.publicmines.utils;

import com.google.common.collect.Lists;
import com.massivecraft.massivecore.command.type.TypeAbstract;
import org.bukkit.command.CommandSender;

import java.util.Collection;

public class TypeMineTier extends TypeAbstract<MineTier> {

    private static final TypeMineTier i = new TypeMineTier();
    public static TypeMineTier get() {
        return i;
    }

    public TypeMineTier() {
        super(MineTier.class);
    }

    @Override
    public MineTier read(String s, CommandSender commandSender) {
        return MineTier.valueOf(s.toUpperCase());
    }

    @Override
    public Collection<String> getTabList(CommandSender commandSender, String s) {
        return Lists.newArrayList("TIER1", "TIER2", "TIER3", "TIER4");
    }
}