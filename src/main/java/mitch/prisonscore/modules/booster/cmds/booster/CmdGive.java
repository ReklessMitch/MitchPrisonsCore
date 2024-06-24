package mitch.prisonscore.modules.booster.cmds.booster;

import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeDouble;
import com.massivecraft.massivecore.command.type.primitive.TypeLong;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import mitch.prisonscore.Perm;
import mitch.prisonscore.modules.booster.cmds.BoosterCommands;
import mitch.prisonscore.modules.booster.objects.Booster;
import mitch.prisonscore.modules.booster.utils.BoosterType;
import org.bukkit.entity.Player;


public class CmdGive extends BoosterCommands {

    public CmdGive() {
        this.addAliases("give");
        this.addParameter(TypePlayer.get(), "player", "ReklessMitch");
        this.addParameter(TypeString.get(), "type", "token/money/beacon");
        this.addParameter(TypeDouble.get(), "multiplier", "2");
        this.addParameter(TypeLong.get(), "duration", "60");
        this.addRequirements(RequirementHasPerm.get(Perm.ADMIN));
    }

    @Override
    public void perform(){
        try {
            Player player = this.readArg();
            String sType = this.readArg();
            BoosterType type = BoosterType.valueOf(sType.toUpperCase());
            double multiplier = this.readArg();
            long duration = this.readArg();
            Booster booster = new Booster(type, multiplier, duration);
            player.getInventory().addItem(booster.getBoosterItem());
        } catch (Exception typeError) {
            me.sendMessage("Invalid arguments");
        }
    }
}
