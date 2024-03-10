package me.reklessmitch.mitchprisonscore.mitchpets.cmd;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetPlayer;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetType;
import org.bukkit.entity.Player;

// @TODO change param to take PetType enum
public class CmdPetAddLevel extends PetCommand{

    public CmdPetAddLevel() {
        this.addAliases("addLevel");
        this.addParameter(TypePlayer.get(), "player");
        this.addParameter(TypeString.get(), "petType");
        this.addParameter(TypeInteger.get(), "level");
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        String petType = this.readArg();
        PetType petTypeEnum = PetType.valueOf(petType.toUpperCase());
        if(petTypeEnum == null){
            msg("<red>Invalid pet type");
            return;
        }
        int level = this.readArg();
        final PetPlayer petPlayer = PetPlayer.get(player.getUniqueId());
        petPlayer.addPetLevel(petTypeEnum, level);
    }
}
