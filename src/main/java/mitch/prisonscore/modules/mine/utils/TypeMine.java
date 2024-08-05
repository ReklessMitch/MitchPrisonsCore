package mitch.prisonscore.modules.mine.utils;

import com.google.common.collect.Lists;
import com.massivecraft.massivecore.command.type.TypeAbstract;
import org.bukkit.command.CommandSender;

import java.util.Collection;

public class TypeMine extends TypeAbstract<MineType> {

    private static final TypeMine i = new TypeMine();
    public static TypeMine get() {
        return i;
    }

    public TypeMine() {
        super(MineType.class);
    }

    @Override
    public MineType read(String s, CommandSender commandSender) {
        return MineType.valueOf(s.toUpperCase());
    }

    @Override
    public Collection<String> getTabList(CommandSender commandSender, String s) {
        return Lists.newArrayList("TOKEN", "MONEY", "BEACON");
    }
}