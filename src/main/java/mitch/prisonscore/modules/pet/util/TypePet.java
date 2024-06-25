package mitch.prisonscore.modules.pet.util;

import com.massivecraft.massivecore.command.type.TypeAbstract;
import org.bukkit.command.CommandSender;

import java.util.Collection;

public class TypePet extends TypeAbstract<PetType> {

    private static final TypePet i = new TypePet();
    public static TypePet get() {
        return i;
    }

    public TypePet() {
        super(PetType.class);
    }

    @Override
    public PetType read(String s, CommandSender commandSender) {
        return PetType.valueOf(s.toUpperCase());
    }

    @Override
    public Collection<String> getTabList(CommandSender commandSender, String s) {
        return PetType.getPetTypes();
    }
}