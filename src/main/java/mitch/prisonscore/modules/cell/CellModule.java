package mitch.prisonscore.modules.cell;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import lombok.Getter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.cell.cmds.cellcmds.CmdCell;
import mitch.prisonscore.modules.cell.object.Cell;
import mitch.prisonscore.modules.cell.object.DisplayItemCommand;
import mitch.prisonscore.modules.Module;
import mitch.prisonscore.modules.type.ModuleType;
import org.bukkit.Material;

import java.util.*;

@Getter
@EditorName("config")
@SuppressWarnings("FieldMayBeFinal")
public class CellModule extends Module {

    private int maxCellSize = 10;
    private Map<String, Cell> cells = new HashMap<>();
    private int size = 36;
    private List<DisplayItemCommand> guiItems = List.of(new DisplayItemCommand(
            Material.BEACON, "&aCreate Cell", List.of("&7Click to create a cell using /cell create {name}"), 1, 0,
            ""
    ));


    public List<String> getCellNames(){
        return new ArrayList<>(cells.keySet());
    }

    public Cell getCellByMember(UUID playerID){
        for(Cell cell : cells.values()){
            if(cell.getMembers().contains(playerID) || cell.getOwner().equals(playerID)){
                return cell;
            }
        }
        return null;
    }

    public Set<UUID> getAllPlayersInCells(){
        Set<UUID> players = new HashSet<>();
        cells.values().forEach(cell -> players.addAll(cell.getAllMembers()));
        return players;
    }

    public Cell getCellByName(String cellName) {
        return cells.get(cellName.toUpperCase());
    }

    public static CellModule get() {
        return MitchPrisonsCore.get().getModuleByType(ModuleType.CELLS);
    }

    public CellModule() {
        super(true);
    }

    @Override
    public ModuleType getType() {
        return ModuleType.CELLS;
    }

    @Override
    public void enable() {
        if (!isEnabled()) return;
        MitchPrisonsCore.get().activate(
                // cmds
                CmdCell.class
        );
    }

    @Override
    public void disable() {}

}
