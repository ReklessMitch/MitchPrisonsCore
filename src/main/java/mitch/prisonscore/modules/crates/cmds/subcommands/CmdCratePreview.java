package mitch.prisonscore.modules.crates.cmds.subcommands;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import mitch.prisonscore.modules.crates.CratesModule;
import mitch.prisonscore.modules.crates.cmds.CrateCommands;
import mitch.prisonscore.modules.crates.guis.CratePreview;

public class CmdCratePreview extends CrateCommands {

    public CmdCratePreview(){
        this.addAliases("preview");
        this.addParameter(TypeString.get(), "crate");
    }

    @Override
    public void perform() throws MassiveException {
        final String crateName = this.readArg();
        CratesModule.get().getCrates().stream().filter(crate -> crate.getName().equalsIgnoreCase(crateName)).findFirst()
                .ifPresentOrElse(crate -> new CratePreview(me, crate).open(), // if true
                                () -> msg("<red>Invalid crate")); // if false
    }

}
