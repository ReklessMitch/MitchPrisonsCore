package mitch.prisonscore.modules.booster.configs;

import com.massivecraft.massivecore.store.SenderEntity;
import lombok.Getter;
import lombok.Setter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.booster.objects.Booster;
import mitch.prisonscore.modules.booster.utils.BoosterActiveTask;
import mitch.prisonscore.modules.booster.utils.BoosterType;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Setter @Getter
public class BoosterPlayer extends SenderEntity<BoosterPlayer> {

    private Booster activeTokenBooster = null;
    private Booster activeMoneyBooster = null;
    private Booster activeBeaconBooster = null;
    private List<Booster> boosters = new ArrayList<>();

    public static BoosterPlayer get(Object oid) {
        return BoosterPlayerColl.get().get(oid);
    }

    @Override
    public BoosterPlayer load(@NotNull BoosterPlayer that) {
        super.load(that);
        return this;
    }

    public boolean combineBoosters() {
        int changes = 0;
        Map<BoosterType, Map<Double, Long>> multipliersToTime = new EnumMap<>(BoosterType.class);
        for(Booster booster : boosters){
            if(multipliersToTime.containsKey(booster.getType())){
                Map<Double, Long> multipliers = multipliersToTime.get(booster.getType());
                if(multipliers.containsKey(booster.getMultiplier())){
                    multipliers.replace(booster.getMultiplier(), multipliers.get(booster.getMultiplier()) + booster.getTimeInSeconds());
                    changes++;
                }else{
                    multipliers.put(booster.getMultiplier(), booster.getTimeInSeconds());
                }
            }else{
                Map<Double, Long> multipliers = new HashMap<>();
                multipliers.put(booster.getMultiplier(), booster.getTimeInSeconds());
                multipliersToTime.put(booster.getType(), multipliers);
            }
        }
        if(changes > 0){
            boosters.clear();
            multipliersToTime.forEach((type, multipliers) ->
                    multipliers.forEach((multiplier, time) ->
                            boosters.add(new Booster(type, multiplier, time))));
            MessageUtils.sendMessage(getPlayer(), LangConf.get().getBoostersCombined());
            activeBeaconBooster = null;
            activeMoneyBooster = null;
            activeTokenBooster = null;
            return true;
        }else{
            MessageUtils.sendMessage(getPlayer(), LangConf.get().getNoBoostersToCombine());
            return false;
        }
    }

    public void activateBooster(Booster booster) {
        switch (booster.getType()){
            case BEACON -> {
                if(activeBeaconBooster != null){
                    activeBeaconBooster.setActive(false);
                }
                activeBeaconBooster = booster;
            }
            case TOKEN -> {
                if(activeTokenBooster != null){
                    activeTokenBooster.setActive(false);
                }
                activeTokenBooster = booster;
            }
            case MONEY -> {
                if(activeMoneyBooster != null){
                    activeMoneyBooster.setActive(false);
                }
                activeMoneyBooster = booster;
            }
        }
        booster.setActive(true);
        new BoosterActiveTask(booster, this).runTaskTimer(MitchPrisonsCore.get(), 0, 20);

    }

    public void deactivateBooster(Booster booster) {
        switch (booster.getType()){
            case BEACON -> activeBeaconBooster = null;
            case TOKEN -> activeTokenBooster = null;
            case MONEY -> activeMoneyBooster = null;
        }
        booster.setActive(false);
    }
}