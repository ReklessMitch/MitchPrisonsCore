package mitch.prisonscore.modules.pickaxe.enchants.enchantments.custom;

import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import mitch.prisonscore.modules.mine.utils.BlockInPmineBrokeEvent;
import mitch.prisonscore.modules.pickaxe.enchants.Enchant;
import mitch.prisonscore.modules.pickaxe.enchants.EnchantmentConfig;
import mitch.prisonscore.utils.configurable.DisplayItem;
import mitch.prisonscore.modules.pickaxe.utils.EnchantType;
import mitch.prisonscore.utils.CrateReward;
import mitch.prisonscore.utils.configurable.FormatItem;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

public class SupplyDrop extends Enchant<SupplyDrop.Config> {

    public SupplyDrop() {
        super(EnchantType.SUPPLY_DROP, SupplyDrop.Config.class);
    }

    @Override
    public void activate(BlockInPmineBrokeEvent e, int level, int prestigeLevel) {
        Player player = e.getPlayer();
        int prestigeBonus = 1 + prestigeLevel;
        for(int pb = 0; pb < prestigeBonus; pb++){
            CrateReward reward = getReward();
            if(reward == null) return;
            reward.runCommands(player);
            MessageUtils.sendMessage(player, reward.getMessage(player));
        }
        player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_SHOOT, 1, 1);
        //sendEnchantMessage(e.getPlayer());
    }

    private CrateReward getReward(){
        double totalChance = 0;
        for(CrateReward reward : this.getConfig().getRewards()){
            totalChance += reward.getChance();
        }
        double random = Math.random() * totalChance;
        double current = 0;
        for(CrateReward reward : this.getConfig().getRewards()){
            current += reward.getChance();
            if(random <= current){
                return reward;
            }
        }
        return null;
    }

    public static class Config extends EnchantmentConfig {
        @Getter private List<CrateReward> rewards = List.of(new CrateReward(0.2, List.of("addc %player% token 1000"), "Tokens 1"));

        public Config() {
            super(new DisplayItem(Material.DIAMOND, "base", List.of("test"), 0, 0),
                    0, 0, 0, 0, "enchantMessage", 0,
                    0, 0, 0, 0, new FormatItem(Material.DIAMOND, "base", List.of("test"), 0));
        }


    }
}
