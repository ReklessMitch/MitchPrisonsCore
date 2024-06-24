package mitch.prisonscore.modules.pet.cmd;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import mitch.prisonscore.Perm;
import mitch.prisonscore.modules.pet.entity.PetPlayer;
import mitch.prisonscore.modules.pet.util.PetType;
import org.bukkit.entity.Player;

public class CmdPetRemoveLevel extends PetCommand{

    public CmdPetRemoveLevel() {
        this.addAliases("removeLevel");
        this.addParameter(TypePlayer.get(), "player");
        this.addParameter(TypeString.get(), "petType");
        this.addParameter(TypeInteger.get(), "level");
        this.addRequirements(RequirementHasPerm.get(Perm.ADMIN));
    }

    @Override
    public void perform() throws MassiveException {
        final Player player = this.readArg();
        final String petType = this.readArg();
        PetType petTypeEnum = PetType.valueOf(petType.toUpperCase());
        int level = this.readArg();
        final PetPlayer petPlayer = PetPlayer.get(player.getUniqueId());
        petPlayer.removePetLevel(petTypeEnum, level);
    }
}