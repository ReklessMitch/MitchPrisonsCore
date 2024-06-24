package mitch.prisonscore.modules.bazaar.cmd;

import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.command.Visibility;

public class BazaarCommands extends MassiveCommand {

    public BazaarCommands(){
        this.addAliases("bazaar");
        this.setVisibility(Visibility.VISIBLE);
    }
}
