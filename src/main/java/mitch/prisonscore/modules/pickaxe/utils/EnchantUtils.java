package mitch.prisonscore.modules.pickaxe.utils;

import mitch.prisonscore.modules.booster.configs.BoosterPlayer;
import mitch.prisonscore.modules.booster.objects.Booster;
import mitch.prisonscore.modules.mine.configs.MinePlayer;
import mitch.prisonscore.modules.pet.entity.PetPlayer;
import mitch.prisonscore.modules.pet.util.PetType;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.utils.Currency;

public class EnchantUtils {

    public static void addTokens(int blocks, MinePlayer mine) {
        PickaxePlayer pickaxe = PickaxePlayer.get(mine.getUuid());
        ProfilePlayer profilePlayer = ProfilePlayer.get(mine.getUuid());
        int fortuneLevel = pickaxe.getEnchants().get(EnchantType.FORTUNE);
        if(fortuneLevel > 0) {
            blocks *= 1 + ((double) fortuneLevel / 1000);
        }
        int tokensToAdd = blocks;
        PetPlayer petPlayer = PetPlayer.get(mine.getUuid());
        PetType petType = petPlayer.getActivePet();
        if(petType == PetType.TOKEN) {
            tokensToAdd = (int) (blocks * petPlayer.getPetBooster(PetType.TOKEN));
        }
        tokensToAdd *= mine.getBooster();
        Booster booster = BoosterPlayer.get(mine.getUuid()).getActiveTokenBooster();
        if(booster != null){
            tokensToAdd *= booster.getMultiplier();
        }
        profilePlayer.addCurrency(Currency.TOKEN, tokensToAdd);
    }
}
