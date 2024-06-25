package mitch.prisonscore.modules.cell.cmds.cellcmds;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeLong;
import mitch.prisonscore.modules.cell.cmds.CellCommands;
import mitch.prisonscore.modules.cell.CellModule;
import mitch.prisonscore.modules.cell.object.Cell;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.math.BigInteger;

public class CmdAddBeacons extends CellCommands {

    public CmdAddBeacons(){
        this.addAliases("addbeacons");
        this.addParameter(TypeLong.get(), "amount");
    }

    @Override
    public void perform() throws MassiveException {
        Cell cell = CellModule.get().getCellByMember(me.getUniqueId());
        LangConf lang = LangConf.get();
        if(cell == null){
            MessageUtils.sendMessage(me, lang.getNotInACell());
            return;
        }
        BigInteger amount = BigInteger.valueOf(this.readArg());
        ProfilePlayer profile = ProfilePlayer.get(me.getUniqueId());

        BigInteger beaconAmount = profile.getCurrencyAmount(Currency.BEACON);

        if (amount.compareTo(BigInteger.ZERO) > 0 && beaconAmount.compareTo(amount) > 0) {
            profile.take(Currency.BEACON, amount);
            cell.addBeacons(amount);
            TagResolver amountResolver = Placeholder.parsed("amount", String.valueOf(amount));
            MessageUtils.sendMessage(me, lang.getAddedBeaconsToCell(), amountResolver);
        } else {
            MessageUtils.sendMessage(me, lang.getNotEnoughBeacons());
        }
    }
}
