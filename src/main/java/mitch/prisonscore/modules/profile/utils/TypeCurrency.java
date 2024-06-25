package mitch.prisonscore.modules.profile.utils;

import com.google.common.collect.Lists;

import com.massivecraft.massivecore.command.type.TypeAbstract;
import org.bukkit.command.CommandSender;

import java.util.Collection;

public class TypeCurrency extends TypeAbstract<Currency> {

    private static final TypeCurrency i = new TypeCurrency();
    public static TypeCurrency get() {
        return i;
    }

    public TypeCurrency() {
        super(Currency.class);
    }

    @Override
    public Currency read(String s, CommandSender commandSender) {
        return Currency.valueOf(s.toUpperCase());
    }

    @Override
    public Collection<String> getTabList(CommandSender commandSender, String s) {
        return Lists.newArrayList("TOKEN", "MONEY", "CREDIT", "BEACON");
    }
}
