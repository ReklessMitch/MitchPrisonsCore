package mitch.prisonscore.utils;

import lombok.Getter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.profile.configs.ProfilePlayerColl;
import mitch.prisonscore.modules.cell.CellModule;
import mitch.prisonscore.modules.cell.object.Cell;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.modules.profile.utils.CurrencyUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

@Getter
public class Leaderboard {

    public Leaderboard(){
        // update leaderboards every 5 minutes
        updateLeaderboards.runTaskTimerAsynchronously(MitchPrisonsCore.get(), 0, 6000);
    }
    // leaderboards
    private List<String> cellTop = new ArrayList<>();
    private List<String> rankTop = new ArrayList<>();
    private Map<mitch.prisonscore.modules.profile.utils.Currency, List<String>> currencyTop = new HashMap<>();

    private List<String> getCellTopLeaderboard(){
        List<String> messages = new ArrayList<>();
        messages.add("<light_blue>+--- Top Cells ---+");
        List<Cell> cells = CellModule.get().getCells().values().stream()
                .limit(10)
                .sorted(Comparator.comparing(Cell::getBeacons, Comparator.reverseOrder()))
                .toList();
        final int size = cells.size();
        for(int iter = 0; iter < size; iter++){
            Cell cell = cells.get(iter);
            messages.add("<light_blue>" + (iter + 1) + ". <grey>" + cell.getName() + " <grey>- <light_blue>" + cell.getBeacons() + " Beacons");
        }
        return messages;
    }

    private List<String> getRankTopLeaderboard(){
        final List<ProfilePlayer> sortedPlayers = ProfilePlayerColl.get().getAll().stream()
                .sorted(Comparator.comparingInt(ProfilePlayer::getRank))
                .limit(10).toList();

        List<String> messages = new ArrayList<>();
        Map<String, Integer> topRankPlayers = new HashMap<>();
        sortedPlayers.forEach(player -> topRankPlayers.put(player.getName(), player.getRank()));
        messages.add("<yellow>Top 10 Ranks:");
        for (Map.Entry<String, Integer> entry : topRankPlayers.entrySet()) {
            messages.add("<yellow>" + entry.getKey() + "<gray>: <yellow>" + entry.getValue());
        }
        return messages;
    }


    private List<String> getCurrencyTopLeaderboard(mitch.prisonscore.modules.profile.utils.Currency currency){
        List<String> messages = new ArrayList<>();
        messages.add("<green><bold>>+--- " + currency.name() + " TOP ---+");
        List<ProfilePlayer> profiles = ProfilePlayerColl.get().getAll().stream()
                .limit(10)
                .sorted(Comparator.comparing(profile -> profile.getCurrencyAmount(currency), Comparator.reverseOrder()))
                .toList();
        for(int i = 0; i < profiles.size(); i++){
            ProfilePlayer profile = profiles.get(i);
            messages.add("<green>" + (i + 1) + ". <white>" + profile.getName() + " <grey>- <green>" + CurrencyUtils.format(profile.getCurrencyAmount(currency)));
        }
        return messages;
    }


    private BukkitRunnable updateLeaderboards = new BukkitRunnable() {
        @Override
        public void run() {
            cellTop = getCellTopLeaderboard();
            rankTop = getRankTopLeaderboard();
            for(mitch.prisonscore.modules.profile.utils.Currency currency : Currency.values()){
                currencyTop.put(currency, getCurrencyTopLeaderboard(currency));
            }
        }
    };
}
