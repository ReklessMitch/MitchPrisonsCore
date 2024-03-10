package me.reklessmitch.mitchprisonscore.mitchbackpack.config;

import com.massivecraft.massivecore.mixin.MixinTitle;
import com.massivecraft.massivecore.store.SenderEntity;
import lombok.Getter;
import lombok.Setter;
import me.reklessmitch.mitchprisonscore.colls.BackPackPlayerColl;
import me.reklessmitch.mitchprisonscore.mitchboosters.configs.BoosterPlayer;
import me.reklessmitch.mitchprisonscore.mitchboosters.objects.Booster;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetPlayer;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetType;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PickaxeConf;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.EnchantType;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.Currency;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.CurrencyUtils;
import me.reklessmitch.mitchprisonscore.utils.ItemCreator;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.security.SecureRandom;

@Getter
@Setter
public class BackpackPlayer extends SenderEntity<BackpackPlayer> {

    public static BackpackPlayer get(Object oid) {
        return BackPackPlayerColl.get().get(oid);
    }

    @Override
    public BackpackPlayer load(@NotNull BackpackPlayer that) {
        super.load(that);
        return this;
    }

    private boolean messages = true;
    private int skinID = 0;
    private BigInteger currentLoad = BigInteger.ZERO;
    private BigInteger capacity = BigInteger.valueOf(100);
    private boolean autoSell = false;

    public void set() {
        getPlayer().getInventory().setItem(40, getBackpackItem());
    }

    public ItemStack getBackpackItem() {
        return ItemCreator.createItem(Material.PAPER, 1, skinID, "<red>Backpack",
                "<grey>Size: <yellow>" + capacity, "<grey>Autosell: " + (autoSell ? "<green>Enabled" : "<red>Disabled"));
    }

    public void setMessages(boolean messages) {
        this.messages = messages;
        this.changed();
    }

    public void add(BigInteger amount) {
        if (capacity.compareTo(amount.add(currentLoad)) <= 0) { // Use compareTo for BigInteger comparison
            currentLoad = capacity;
            if (autoSell) {
                sell();
                return;
            }
            MixinTitle.get().sendTitleMsg(getPlayer(), 0, 20, 0, "<red>Backpack is full!", "<grey>Sell your items with <yellow>/sell");
        } else {
            currentLoad = currentLoad.add(amount); // Use add for BigInteger
        }
        changed();
    }

    public void addSlot(BigInteger amount){
        capacity = capacity.add(amount);
        final TagResolver amountResolver = Placeholder.parsed("amount", amount.toString());
        MessageUtils.sendMessage(getPlayer(), LangConf.get().getAddSlotToBackpack(), amountResolver);
        changed();
        set();
    }

    public double getPetBooster(){
        PetPlayer petPlayer = PetPlayer.get(getId());
        return petPlayer.getActivePet() == PetType.MONEY ? petPlayer.getPetBooster(PetType.MONEY) : 0;
    }

    public void sell() {
        boolean boostActivated = false;
        BigInteger startAmount = currentLoad; // Convert currentLoad to a BigInteger
        PPickaxe ppickaxe = PPickaxe.get(getId());
        double greedMulti = ppickaxe.getEnchants().get(EnchantType.GREED) / 1000.0;
        if(greedMulti != 0) {
            int greedPrestigeMulti = ppickaxe.getEnchantPrestiges().get(EnchantType.GREED);
            startAmount = startAmount.multiply(BigInteger.valueOf((long)((1 + greedMulti) * greedPrestigeMulti))); // Use multiply for BigInteger
        }
        int boostLevel = ppickaxe.getEnchants().get(EnchantType.BOOST);
        if(boostLevel != 0 &&
                new SecureRandom().nextDouble() < PickaxeConf.get().getEnchantByType(EnchantType.BOOST).getProcChance(boostLevel)){
            startAmount = startAmount.multiply(BigInteger.valueOf(2)); // Use multiply for BigInteger
            boostActivated = true;
        }
        double petBooster = getPetBooster();
        if(petBooster > 0){
            startAmount = startAmount.multiply(BigInteger.valueOf((long)(1 + petBooster))); // Use multiply for BigInteger
        }

        Booster booster = BoosterPlayer.get(getId()).getActiveMoneyBooster();
        if(booster != null){
            startAmount = startAmount.multiply(BigInteger.valueOf((long)(booster.getMultiplier()))); // Use multiply for BigInteger
        }
        int rank = ProfilePlayer.get(getId()).getRank() + 1;
        startAmount = startAmount.multiply(BigInteger.valueOf(rank)); // Use multiply for BigInteger
        ProfilePlayer profilePlayer = ProfilePlayer.get(getId());
        profilePlayer.addCurrency(Currency.MONEY, startAmount);
        if(messages) {
            MessageUtils.sendMessage(getPlayer(), "<green>-------------------------" +
                    "\n<green>You have sold <yellow>" + currentLoad + " <green>items for <yellow>" + CurrencyUtils.format(startAmount) + " <green>money" +
                    "\n<green>Rank Multiplier (+" + rank + ")" +
                    (booster != null ? "\n<green>Booster Multiplier (+" + booster.getMultiplier() + ")" : "") +
                    (boostActivated ? "\n<green>Boost Multiplier (2x)" : "") +
                    (greedMulti > 0 ? "\n<green>Greed Multiplier (+" + greedMulti / 1000.0 + ")" : "") +
                    (petBooster > 0 ? "\n<green>Pet Multiplier (+" + petBooster + ")" : "") +
                    "\n<green>-------------------------");
        }

        if(ppickaxe.isAutoRankup()){
            ProfilePlayer.get(getId()).rankUpMax();
        }
        currentLoad = BigInteger.ZERO;
        changed();
        set();
    }

    public BigInteger getCost(BigInteger amountToBuy) {
        return amountToBuy.multiply(BigInteger.valueOf(BackpackConf.get().getSlotPriceIncreasePerSize()));
    }

    public BigInteger getMaxPurchasable() {
        BigInteger tokens = ProfilePlayer.get(getId()).getCurrencyAmount(Currency.TOKEN);
        BackpackConf conf = BackpackConf.get();
        int costPerSlot = conf.getSlotPriceIncreasePerSize();
        return tokens.divide(BigInteger.valueOf(costPerSlot));
    }

    public void setSkin(int customDataModel) {
        skinID = customDataModel;
        set();
        changed();
    }
}
