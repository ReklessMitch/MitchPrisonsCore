package mitch.prisonscore.modules.booster.cmds.booster;

import mitch.prisonscore.modules.booster.cmds.BoosterCommands;
import mitch.prisonscore.modules.booster.guis.BoosterGUI;
import org.bukkit.entity.Player;

public class CmdBooster extends BoosterCommands {

    private static final CmdBooster i = new CmdBooster();
    public static CmdBooster get() { return i; }

    private final CmdGive cmdGive = new CmdGive();
    private final CmdBoosterGUI cmdGUI = new CmdBoosterGUI();

    public CmdBooster(){
        this.addAliases("booster", "boost", "boosters");
        this.addChild(this.cmdGive);
        this.addChild(this.cmdGUI);
    }

    @Override
    public void perform() {
        Player player = (Player) sender;
        new BoosterGUI(player.getUniqueId()).open(player);
    }

}
