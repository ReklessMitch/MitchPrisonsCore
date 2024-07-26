package mitch.prisonscore.modules.pickaxe.utils;

import mitch.prisonscore.modules.booster.configs.BoosterPlayer;
import mitch.prisonscore.modules.booster.objects.Booster;
import mitch.prisonscore.modules.mine.configs.MinePlayer;
import mitch.prisonscore.modules.pet.entity.PetPlayer;
import mitch.prisonscore.modules.pet.util.PetType;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.utils.Currency;
import org.bukkit.entity.Player;

import java.util.UUID;

public class EnchantUtils {

    public static void addTokens(int blocks, Player player) {
        UUID uuid = player.getUniqueId();
        PickaxePlayer pickaxe = PickaxePlayer.get(uuid);
        ProfilePlayer profilePlayer = ProfilePlayer.get(uuid);
        int fortuneLevel = pickaxe.getEnchants().get(EnchantType.FORTUNE);
        if(fortuneLevel > 0) {
            blocks *= 1 + ((double) fortuneLevel / 1000);
        }
        int tokensToAdd = blocks;
        PetPlayer petPlayer = PetPlayer.get(uuid);
        PetType petType = petPlayer.getActivePet();
        if(petType == PetType.TOKEN) {
            tokensToAdd = (int) (blocks * petPlayer.getPetBooster(PetType.TOKEN));
        }
        profilePlayer.addCurrency(Currency.TOKEN, tokensToAdd);
    }
}
