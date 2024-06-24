package mitch.prisonscore.modules.crates.cmds;

import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import mitch.prisonscore.modules.crates.cmds.subcommands.CmdCrateGive;
import mitch.prisonscore.modules.crates.cmds.subcommands.CmdCratePreview;
import mitch.prisonscore.modules.crates.guis.CrateOpen;

public class CmdCrates extends CrateCommands {

    private static CmdCrates i = new CmdCrates();
    public static CmdCrates get() { return i; }

    protected CmdCratePreview preview = new CmdCratePreview();
    protected CmdCrateGive give = new CmdCrateGive();


    public CmdCrates(){
        this.addRequirements(RequirementIsPlayer.get());
        this.addAliases("crate", "mpcrate");
        this.addChild(preview);
        this.addChild(give);
    }

    @Override
    public void perform(){
        new CrateOpen(me).open();
    }



}
