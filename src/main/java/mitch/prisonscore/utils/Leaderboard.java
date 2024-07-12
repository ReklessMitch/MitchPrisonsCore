package mitch.prisonscore.utils;

import lombok.Getter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayerColl;
import mitch.prisonscore.modules.profile.configs.ProfilePlayerColl;
import mitch.prisonscore.modules.cell.CellModule;
import mitch.prisonscore.modules.cell.object.Cell;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.modules.profile.utils.CurrencyUtils;
import org.bukkit.Bukkit;
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
    private List<String> blockTop = new ArrayList<>();
    private Map<mitch.prisonscore.modules.profile.utils.Currency, List<String>> currencyTop = new HashMap<>();

    private List<String> getCellTopLeaderboard(){
        List<String> messages = new ArrayList<>();
        messages.add("<bluegrad2>+--- Top Cells ---+");
        List<Cell> cells = CellModule.get().getCells().values().stream()
                .sorted(Comparator.comparing(Cell::getBeacons, Comparator.reverseOrder()))
                .toList();
        int top = Math.min(cells.size(), 10);
        for(int i = 0; i < top; i++){
            Cell cell = cells.get(i);
            messages.add("<white>" + (i + 1) + ". <lightbluegrad>" + cell.getName() + " <grey>- <bluegrad2>" + cell.getBeacons() + " Beacons");
        }
        return messages;
    }

    private List<String> getRankTopLeaderboard(){
        List<ProfilePlayer> sortedPlayers = ProfilePlayerColl.get().getAll().stream()
                .sorted(Comparator.comparingInt(ProfilePlayer::getRank))
                .toList();
        List<String> messages = new ArrayList<>();
        Map<String, Integer> topRankPlayers = new HashMap<>();
        sortedPlayers.forEach(player -> topRankPlayers.put(player.getName(), player.getRank()));
        messages.add("<yellow>Top 10 Ranks:");
        int top = Math.min(topRankPlayers.size(), 10);

        for(int i = 0; i < top; i++){
            messages.add("<yellow>" + sortedPlayers.get(i).getName() + "<gray>: <yellow>" + sortedPlayers.get(i).getRank());
        }
        for (Map.Entry<String, Integer> entry : topRankPlayers.entrySet()) {
            messages.add("<yellow>" + entry.getKey() + "<gray>: <yellow>" + entry.getValue());
        }
        return messages;
    }


    private List<String> getCurrencyTopLeaderboard(mitch.prisonscore.modules.profile.utils.Currency currency){
        List<String> messages = new ArrayList<>();
        messages.add("<green><bold>>+--- " + currency.name() + " TOP ---+");
        List<ProfilePlayer> profiles = ProfilePlayerColl.get().getAll().stream()
                .sorted(Comparator.comparing(profile -> profile.getCurrencyAmount(currency), Comparator.reverseOrder()))
                .toList();
        int top = Math.min(profiles.size(), 10);
        for(int i = 0; i < top; i++){
            ProfilePlayer profile = profiles.get(i);
            messages.add("<green>" + (i + 1) + ". <white>" + profile.getName() + " <grey>- <green>" + CurrencyUtils.format(profile.getCurrencyAmount(currency)));
        }
        return messages;
    }

    private List<String> getBlockTopLeaderboard(){
        List<String> messages = new ArrayList<>();
        messages.add("<redgrad><bold>>+--- Top Blocks ---+");
        List<PickaxePlayer> profiles = PickaxePlayerColl.get().getAll().stream()
                .sorted(Comparator.comparing(PickaxePlayer::getBlocksBroken, Comparator.reverseOrder()))
                .toList();
        int top = Math.min(profiles.size(), 10);
        for(int i = 0; i < top; i++){
            PickaxePlayer profile = profiles.get(i);
            messages.add("<white>" + (i + 1) + ". <redgrad>" + profile.getName() + " <grey>- <redgrad>" + CurrencyUtils.format(profile.getBlocksBroken()) + " Blocks");
        }
        return messages;
    }


    private BukkitRunnable updateLeaderboards = new BukkitRunnable() {
        @Override
        public void run() {
            Bukkit.broadcast(MessageUtils.colorize("<redgrad><bold>Updating Leaderboards..."));
            cellTop = getCellTopLeaderboard();
            rankTop = getRankTopLeaderboard();
            blockTop = getBlockTopLeaderboard();
            for(mitch.prisonscore.modules.profile.utils.Currency currency : Currency.values()){
                currencyTop.put(currency, getCurrencyTopLeaderboard(currency));
            }
        }
    };
}
