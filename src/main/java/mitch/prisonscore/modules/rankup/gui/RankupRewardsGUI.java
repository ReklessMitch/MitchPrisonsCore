package mitch.prisonscore.modules.rankup.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.rankup.RankupModule;
import mitch.prisonscore.modules.rankup.object.RankupReward;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RankupRewardsGUI extends ChestGui {

    private final Player player;

    public RankupRewardsGUI(Player player){
        this.player = player;
        setInventory(Bukkit.createInventory(null, 54, "Rankup Rewards"));
        add();
        RankupModule.get().getRankupRewards().forEach(this::setup);
        setAutoclosing(false);
        setAutoremoving(true);
    }

    private void setup(int levelRequired, RankupReward item){
        ProfilePlayer pp = ProfilePlayer.get(player.getUniqueId());
        if(pp.getClaimedRewards().contains(levelRequired)){
            getInventory().setItem(item.getSlot(), item.getRewardItem(Material.RED_WOOL));
            setAction(item.getSlot(), event -> {
                MessageUtils.sendMessage(player, "<red>You have already claimed this reward!");
                return true;
            });
        } else if (pp.getRank() < levelRequired) {
            getInventory().setItem(item.getSlot(), item.getRewardItem(Material.RED_STAINED_GLASS_PANE));
            setAction(item.getSlot(), event -> {
                MessageUtils.sendMessage(player, "<red>You have not unlocked this reward yet!");
                return true;
            });
        }else {
            getInventory().setItem(item.getSlot(), item.getRewardItem());
            setAction(item.getSlot(), event -> {
                item.getCommands().forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName())));
                pp.getClaimedRewards().add(levelRequired);
                pp.changed();
                setup(levelRequired, item);
                return true;
            });
        }
    }

    public void open(){
        player.openInventory(getInventory());
    }
}
