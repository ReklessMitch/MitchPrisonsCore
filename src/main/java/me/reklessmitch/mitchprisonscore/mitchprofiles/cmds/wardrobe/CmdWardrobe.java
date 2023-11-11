package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.wardrobe;

import com.massivecraft.massivecore.MassiveException;
import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.CurrencyCommands;
import me.reklessmitch.mitchprisonscore.mitchprofiles.guis.WardrobeSelectionGUI;
import org.bukkit.entity.Player;

public class CmdWardrobe extends CurrencyCommands {

    private static CmdWardrobe i = new CmdWardrobe();
    public static CmdWardrobe get() { return i; }

    public CmdWardrobe() {
        this.addAliases("wardrobe");
    }

    @Override
    public void perform() throws MassiveException {
        Player player = (Player) this.sender;
        WardrobeSelectionGUI gui = new WardrobeSelectionGUI(player);
        gui.open();
    }


}
