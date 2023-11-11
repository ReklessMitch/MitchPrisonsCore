package me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeLong;
import me.reklessmitch.mitchprisonscore.mitchcells.cmds.CellCommands;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;
import me.reklessmitch.mitchprisonscore.mitchcells.object.Cell;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.Currency;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;

import java.math.BigInteger;

public class CmdAddBeacons extends CellCommands {

    public CmdAddBeacons(){
        this.addAliases("addbeacons");
        this.addParameter(TypeLong.get(), "amount");
    }

    @Override
    public void perform() throws MassiveException {
        Cell cell = CellConf.get().getCellByMember(me.getUniqueId());
        if(cell == null){
            MessageUtils.sendMessage(me, "<red>You are not in a cell");
            return;
        }
        BigInteger amount = BigInteger.valueOf(this.readArg());
        ProfilePlayer profile = ProfilePlayer.get(me.getUniqueId());

        BigInteger beaconAmount = profile.getCurrencyAmount(Currency.BEACON);

        if (amount.compareTo(BigInteger.ZERO) > 0 && beaconAmount.compareTo(amount) > 0) {
            profile.take(Currency.BEACON, amount);
            cell.addBeacons(amount);
            MessageUtils.sendMessage(me, "Added " + amount + " beacons to your cell");
        } else {
            MessageUtils.sendMessage(me, "<red>You do not have enough beacons");
        }
    }
}
