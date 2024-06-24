package mitch.prisonscore.modules.profile.cmds.misc;

import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import mitch.prisonscore.Perm;
import mitch.prisonscore.modules.profile.cmds.MiscCommands;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.utils.MessageUtils;

public class CmdToggleSC extends MiscCommands {

    private static CmdToggleSC i = new CmdToggleSC();
    public static CmdToggleSC get() { return i; }

    public CmdToggleSC() {
        this.addAliases("sc");
        this.addRequirements(RequirementHasPerm.get(Perm.ADMIN));
    }

    @Override
    public void perform() {
        ProfilePlayer profilePlayer = ProfilePlayer.get(me.getUniqueId());
        profilePlayer.toggleStaffChat();
        MessageUtils.sendMessage(me, "<green>Staff chat is now " + (profilePlayer.isStaffChat() ? "enabled" : "disabled") + "<green>.");
    }

}
