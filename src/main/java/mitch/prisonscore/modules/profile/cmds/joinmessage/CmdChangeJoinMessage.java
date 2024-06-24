package mitch.prisonscore.modules.profile.cmds.joinmessage;


import mitch.prisonscore.modules.profile.cmds.MiscCommands;
import mitch.prisonscore.modules.profile.guis.SelectJoinMessageGUI;

public class CmdChangeJoinMessage extends MiscCommands {

    private static final CmdChangeJoinMessage i = new CmdChangeJoinMessage();
    public static CmdChangeJoinMessage get() { return i; }

    public CmdChangeJoinMessage() {
        this.setAliases("changejoinmessage");
        this.setDesc("Change your join message");
    }

    @Override
    public void perform(){
        new SelectJoinMessageGUI(me).open();
    }
}
