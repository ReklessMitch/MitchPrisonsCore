package me.reklessmitch.mitchprisonscore.mitchrankup.cmds;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import me.reklessmitch.mitchprisonscore.Perm;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
import org.bukkit.entity.Player;

public class CmdSetRank extends RankupCommands{

    private static final CmdSetRank i = new CmdSetRank();
    public static CmdSetRank get() { return i; }

    public CmdSetRank() {
        this.setVisibility(Visibility.SECRET);
        this.setAliases("setrank");
        this.setDesc("Set a players rank");
        this.addParameter(TypePlayer.get(), "player");
        this.addParameter(TypeInteger.get(), "rank");
        this.addRequirements(RequirementHasPerm.get(Perm.ADMIN));
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        int rank = this.readArg();
        MessageUtils.sendMessage(me, "<green>You have set " + player.getName() + "'s rank to " + rank);
        MessageUtils.sendMessage(player, "<green>Your rank has been set to " + rank);
        ProfilePlayer pp = ProfilePlayer.get(player.getUniqueId());
        pp.setRank(rank);
        pp.changed();
    }
}
