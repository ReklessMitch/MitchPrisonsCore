package mitch.prisonscore.modules.pet.cmd;

import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import mitch.prisonscore.modules.pet.gui.PetGUI;
import org.bukkit.entity.Player;

public class CmdPetGUI extends PetCommand{

    public CmdPetGUI() {
        this.addAliases("gui");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() {
        Player player = (Player) sender;
        new PetGUI(player.getUniqueId()).open();
    }
}
