package mitch.prisonscore.modules.publicmines.cmds;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import mitch.prisonscore.modules.publicmines.PublicMinesModule;

public class CmdResetPublicMine extends PublicMineCommands{

    private static final CmdResetPublicMine i = new CmdResetPublicMine();
    public static CmdResetPublicMine get() { return i; }

    public CmdResetPublicMine() {
        this.addAliases("publicreset");
        this.addParameter(TypeString.get(), "mineName");
    }

    @Override
    public void perform() throws MassiveException {
        String mineName = this.readArg();
        PublicMinesModule.get().getPublicMines().values().stream().filter(mine ->
                mine.getName().equalsIgnoreCase(mineName)).findFirst()
                .ifPresentOrElse(mine -> {
                    mine.reset();
                    msg("<g>Reset mine <h>%s<g>.", mine.getName());
                }, () -> msg("<red>Could not find mine <h>%s<red>.", mineName));
    }


}
