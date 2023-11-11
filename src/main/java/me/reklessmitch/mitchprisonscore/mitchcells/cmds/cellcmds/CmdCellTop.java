package me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds;

import me.reklessmitch.mitchprisonscore.mitchcells.cmds.CellCommands;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;
import me.reklessmitch.mitchprisonscore.mitchcells.object.Cell;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;

import java.util.Comparator;
import java.util.List;

public class CmdCellTop extends CellCommands {

    public CmdCellTop(){
        this.addAliases("top");
    }

    @Override
    public void perform() {
        MessageUtils.sendMessage(sender, "<light_blue>+--- Top Cells ---+");
        List<Cell> cells = CellConf.get().getCells().values().stream()
                .limit(10)
                .sorted(Comparator.comparing(Cell::getBeacons, Comparator.reverseOrder()))
                .toList();
        for(int i = 0; i < cells.size(); i++){
            Cell cell = cells.get(i);
            MessageUtils.sendMessage(sender, "<light_blue>" + (i + 1) + ". <grey>" + cell.getName() + " <grey>- <light_blue>" + cell.getBeacons() + " Beacons");
        }
    }
}
