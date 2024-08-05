package mitch.prisonscore.modules.battlepass.config;

import com.massivecraft.massivecore.store.SenderEntity;
import lombok.Getter;
import lombok.Setter;
import mitch.prisonscore.modules.battlepass.BattlePassModule;
import mitch.prisonscore.modules.battlepass.object.Reward;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Getter
public class PassPlayer extends SenderEntity<PassPlayer> {

    public static PassPlayer get(Object oid) {
        return PassPlayerColl.get().get(oid);
    }

    @Override
    public PassPlayer load(@NotNull PassPlayer that) {
        super.load(that);
        return this;
    }

    private int level = 0;
    private int lastClaimedPaidLevel = 0;
    private int lastClaimedFreeLevel = 0;
    @Setter private boolean premium = false;


    private void claimRewards(Map<Integer, List<Reward>> rewards, boolean isFree) {
        TreeMap<Integer, List<Reward>> sortedMap = new TreeMap<>(rewards);
        SortedMap<Integer, List<Reward>> subMap = sortedMap.subMap(
                isFree ? lastClaimedFreeLevel + 1 : lastClaimedPaidLevel + 1,
                level + 1
        );
        if(subMap.isEmpty()) {
            MessageUtils.sendMessage(getPlayer(), LangConf.get().getBattlePassNoRewards());
        }else{
            MessageUtils.sendMessage(getPlayer(), LangConf.get().getBattlePassClaimed());
            subMap.forEach((l, r) -> r.forEach(reward -> {
                reward.getCommands().forEach(
                    command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", getPlayer().getName())));
                reward.getMessages().forEach(
                    message -> MessageUtils.sendMessage(getPlayer(), message.replace("%player%", getPlayer().getName())));
            }));

            if(isFree){lastClaimedFreeLevel = level;}
            else{lastClaimedPaidLevel = level;}
            changed();
        }
    }
    public void claimFreeRewards() {
        claimRewards(BattlePassModule.get().getFreeRewards(), true);
    }

    public void claimAllRewards() {
        claimPaidRewards();
        claimFreeRewards();
    }

    public void claimPaidRewards() {
        claimRewards(BattlePassModule.get().getPaidRewards(), false);
    }

    public void addLevel() {
        level++;
        changed();
    }
}
