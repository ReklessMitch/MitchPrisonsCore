package mitch.prisonscore.modules.pet.cmd;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import mitch.prisonscore.Perm;
import mitch.prisonscore.modules.pet.entity.PetPlayer;
import mitch.prisonscore.modules.pet.util.PetType;
import mitch.prisonscore.modules.pet.util.TypePet;
import org.bukkit.entity.Player;

// @TODO change param to take PetType enum
public class CmdPetAddLevel extends PetCommand{

    public CmdPetAddLevel() {
        this.addAliases("addLevel");
        this.addParameter(TypePlayer.get(), "player");
        this.addParameter(TypePet.get(), "petType");
        this.addParameter(TypeInteger.get(), "level");
        this.addRequirements(RequirementHasPerm.get(Perm.ADMIN));
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        PetType petType = this.readArg();
        int level = this.readArg();
        final PetPlayer petPlayer = PetPlayer.get(player.getUniqueId());
        petPlayer.addPetLevel(petType, level);
    }
}
