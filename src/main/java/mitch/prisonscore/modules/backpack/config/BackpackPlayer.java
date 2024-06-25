package mitch.prisonscore.modules.backpack.config;

import com.massivecraft.massivecore.mixin.MixinTitle;
import com.massivecraft.massivecore.store.SenderEntity;
import lombok.Getter;
import lombok.Setter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.backpack.BackpackModule;
import mitch.prisonscore.modules.booster.configs.BoosterPlayer;
import mitch.prisonscore.modules.booster.objects.Booster;
import mitch.prisonscore.modules.pet.entity.PetPlayer;
import mitch.prisonscore.modules.pet.util.PetType;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.modules.profile.utils.CurrencyUtils;
import mitch.prisonscore.utils.ItemCreator;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

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
        ItemStack item = ItemCreator.createItem(Material.PAPER, 1, skinID, "<red>Backpack",
                "<grey>Size: <yellow>" + capacity, "<grey>Autosell: " + (autoSell ? "<green>Enabled" : "<red>Disabled"));
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(MitchPrisonsCore.get().getNoMove(), PersistentDataType.BOOLEAN, true);
        item.setItemMeta(meta);
        return item;
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
        final PetPlayer petPlayer = PetPlayer.get(getId());
        return petPlayer.getActivePet() == PetType.MONEY ? petPlayer.getPetBooster(PetType.MONEY) : 0;
    }

    public void sell() {
        BigInteger startAmount = currentLoad; // Convert currentLoad to a BigInteger
        PickaxePlayer ppickaxe = PickaxePlayer.get(getId());
//        double greedMulti = ppickaxe.getEnchants().get(EnchantType.GREED) / 1000.0;
//        if(greedMulti != 0) {
//            int greedPrestigeMulti = ppickaxe.getEnchantPrestiges().get(EnchantType.GREED);
//            startAmount = startAmount.multiply(BigInteger.valueOf((long)((1 + greedMulti) * greedPrestigeMulti))); // Use multiply for BigInteger
//        }
//        int boostLevel = ppickaxe.getEnchants().get(EnchantType.BOOST);
//        if(boostLevel != 0 &&
//                new SecureRandom().nextDouble() < MitchPickaxeModule.get().getEnchantByType(EnchantType.BOOST).getProcChance(boostLevel)){
//            startAmount = startAmount.multiply(BigInteger.valueOf(2)); // Use multiply for BigInteger
//            boostActivated = true;
//        }
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
//                    (boostActivated ? "\n<green>Boost Multiplier (2x)" : "") +
//                    (greedMulti > 0 ? "\n<green>Greed Multiplier (+" + greedMulti / 1000.0 + ")" : "") +
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
        return amountToBuy.multiply(BigInteger.valueOf(BackpackModule.get().getSlotPriceIncreasePerSize()));
    }

    public BigInteger getMaxPurchasable() {
        final BigInteger tokens = ProfilePlayer.get(getId()).getCurrencyAmount(Currency.TOKEN);
        BackpackModule conf = BackpackModule.get();
        int costPerSlot = conf.getSlotPriceIncreasePerSize();
        return tokens.divide(BigInteger.valueOf(costPerSlot));
    }

    public void setSkin(int customDataModel) {
        skinID = customDataModel;
        set();
        changed();
    }
}
