package mitch.prisonscore.modules.pickaxe.configs;

import com.massivecraft.massivecore.store.SenderEntity;
import lombok.Getter;
import mitch.prisonscore.modules.battlepass.events.BlocksMinedEvent;
import mitch.prisonscore.modules.mine.configs.MinePlayer;
import mitch.prisonscore.modules.pickaxe.MitchPickaxeModule;
import mitch.prisonscore.modules.pickaxe.enchants.Enchant;
import mitch.prisonscore.utils.configurable.DisplayItem;
import mitch.prisonscore.modules.pickaxe.utils.EnchantType;
import mitch.prisonscore.utils.MessageUtils;
import mitch.prisonscore.utils.configurable.FormatItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Getter
public class PickaxePlayer extends SenderEntity<PickaxePlayer> {

    private FormatItem pickaxe = new FormatItem(Material.DIAMOND_PICKAXE, "<gold>Pickaxe",
            List.of("<yellow>Enchants"), 0);
    private long rawBlocksBroken = 0;
    private long blocksBroken = 0;
    private Map<EnchantType, Integer> enchants = setEnchants(false);
    private Map<EnchantType, Integer> enchantPrestiges = setEnchants(true);
    private Map<EnchantType, Boolean> enchantToggle = setEnchantToggle();
    private Map<EnchantType, Boolean> enchantMessages = setEnchantToggle();
    private Map<EnchantType, Boolean> enchantSoundToggles = setEnchantToggle();
    private boolean virtualKey = false;
    private boolean autoRankup = false;

    public boolean isMessageEnabled(EnchantType enchant){
        return enchantMessages.getOrDefault(enchant, true);
    }

    public void addLevelToEnchant(EnchantType enchant, int amount){
        enchants.put(enchant, enchants.get(enchant) + amount);
        updatePickaxe();
    }

    private Map<EnchantType, Boolean> setEnchantToggle() {
        Map<EnchantType, Boolean> enchantTogglesList = new EnumMap<>(EnchantType.class);
        MitchPickaxeModule.get().getEnchants().keySet().forEach(enchant -> enchantTogglesList.put(enchant, true));
        return enchantTogglesList;
    }

    public void addRawBlockBroken(){
        rawBlocksBroken++;
    }

    public void addBlockBroken(long amount){
        blocksBroken += amount;
        BlocksMinedEvent e = new BlocksMinedEvent(getPlayer(), amount);
        Bukkit.getPluginManager().callEvent(e);
    }

    public static PickaxePlayer get(Object oid) {
        return PickaxePlayerColl.get().get(oid);
    }

    @Override
    public PickaxePlayer load(@NotNull PickaxePlayer that)
    {
        super.load(that);
        return this;
    }

    public void updatePickaxe(){
        List<String> lore = new ArrayList<>(List.of("<yellow><bold>Enchants"));
        lore.add("<grey> ");
        enchants.forEach((enchantType, level) -> {
            if(level == 0) return;
            Enchant<?> e = MitchPickaxeModule.get().getEnchantByType(enchantType);
            int prestige = enchantPrestiges.get(enchantType);
            if (e == null) return;
            String enchantmentLore = "<aqua>| " + e.getConfig().getDisplayItem().getItemName() + "<white>: " + level;
            if(prestige > 0){
                enchantmentLore += " <grey>( P" + prestige + " )";
            }
            lore.add(enchantmentLore);
        });
        pickaxe.setItemLore(lore);
        givePickaxe();
        this.changed();
        givePotionEffectEnchants();
    }

    public void givePotionEffectEnchants(){
        getPlayer().clearActivePotionEffects();
        if(enchants.get(EnchantType.HASTE) >= 1 && enchantToggle.get(EnchantType.HASTE)){
            getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, PotionEffect.INFINITE_DURATION, enchants.get(EnchantType.HASTE)));
        }
        // if speed >= 1 and not toggled, give speed
        if(enchants.get(EnchantType.SPEED) >= 1 && enchantToggle.get(EnchantType.SPEED)){
            getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, enchants.get(EnchantType.SPEED)));
        }
    }

    public ItemStack getPickaxeGuiItem(){
        ItemStack item = pickaxe.getFormatItem();
        item.addUnsafeEnchantment(Enchantment.DIG_SPEED, getEnchants().get(EnchantType.EFFICIENCY));
        return item;
    }

    /**
     *
     * @param prestige, pass through true if u want to set up prestige enchants
     * @return Map<EnchantType, Integer>
     */
    private Map<EnchantType, Integer> setEnchants(boolean prestige){
        Map<EnchantType, Integer> enchantList = new EnumMap<>(EnchantType.class);
        MitchPickaxeModule.get().getEnchants().keySet().forEach(enchant -> {
            if(enchant == EnchantType.EFFICIENCY && !prestige){
                enchantList.put(enchant, 10);
                return;
            }
            enchantList.put(enchant, 0);
        });
        return enchantList;
    }

    public int getEnchantLevel(EnchantType enchantType){
        return enchants.get(enchantType);
    }

    public void givePickaxe() {
        getPlayer().getInventory().setItem(0, getPickaxeGuiItem());
    }

    public void setSkin(int customDataModel) {
        pickaxe.setCustomModelData(customDataModel);
        // do the popup listener for the pickaxe
        changed();
        givePickaxe();
    }

    public void toggleEnchant(EnchantType enchantType){
        boolean toggle = enchantToggle.get(enchantType);
        MessageUtils.sendMessage(getPlayer(), "<green>Toggled " + enchantType + ": " + (toggle ? "<red>DISABLED" : "<green>ENABLED"));
        enchantToggle.replace(enchantType, !toggle);
        changed();
        givePotionEffectEnchants();
    }

    public void toggleEnchantMessage(EnchantType type) {
        boolean toggle = enchantMessages.get(type);
        MessageUtils.sendMessage(getPlayer(), "<green>Toggled " + type + " messages : " + (toggle ? "<red>DISABLED" : "<green>ENABLED"));
        enchantMessages.replace(type, !toggle);
        changed();
    }

    public void toggleVirtualKey() {
        virtualKey = !virtualKey;
        MessageUtils.sendMessage(getPlayer(), "<green>Toggled virtual key: " + (virtualKey ? "<green>ENABLED" : "<red>DISABLED"));
        changed();
    }

    public void toggleEnchantSound(EnchantType type) {
        boolean toggle = enchantSoundToggles.get(type);
        MessageUtils.sendMessage(getPlayer(), "<green>Toggled " + type + " sound : " + (toggle ? "<red>DISABLED" : "<green>ENABLED"));
        enchantSoundToggles.replace(type, !toggle);
        changed();
    }


    public void toggleAutoRankup() {
        autoRankup = !autoRankup;
        MessageUtils.sendMessage(getPlayer(), "<green>Toggled auto rankup: " + (autoRankup ? "<green>ENABLED" : "<red>DISABLED"));
        changed();
    }
}
