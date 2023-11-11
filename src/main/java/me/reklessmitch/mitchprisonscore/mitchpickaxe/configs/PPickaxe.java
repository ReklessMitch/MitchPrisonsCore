package me.reklessmitch.mitchprisonscore.mitchpickaxe.configs;

import com.massivecraft.massivecore.store.SenderEntity;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.colls.PPickaxeColl;
import me.reklessmitch.mitchprisonscore.mitchbattlepass.events.BlocksMinedEvent;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.PlayerMine;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.enchants.Enchant;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.DisplayItem;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.EnchantType;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Getter
public class PPickaxe extends SenderEntity<PPickaxe> {

    private DisplayItem pickaxe = new DisplayItem(Material.DIAMOND_PICKAXE, "<gold>Pickaxe",
            List.of("<yellow>Enchants"), 0, 4);
    private long rawBlocksBroken = 0;
    private long blocksBroken = 0;
    private Map<EnchantType, Integer> enchants = setEnchants(false);
    private Map<EnchantType, Integer> enchantPrestiges = setEnchants(true);
    private Map<EnchantType, Boolean> enchantToggle = setEnchantToggle();
    private Map<EnchantType, Boolean> enchantMessages = setEnchantToggle();
    private Map<EnchantType, Boolean> enchantSoundToggles = setEnchantToggle();
    private boolean virtualKey = false;
    private boolean autoRankup = false;

    private Map<EnchantType, Boolean> setEnchantToggle() {
        Map<EnchantType, Boolean> enchantTogglesList = new EnumMap<>(EnchantType.class);
        PickaxeConf.get().enchants.keySet().forEach(enchant -> enchantTogglesList.put(enchant, true));
        return enchantTogglesList;
    }

    public void addRawBlockBroken(){
        rawBlocksBroken++;
    }

    public void addBlockBroken(long amount){
        PlayerMine mine = PlayerMine.get(getPlayer());
        int booster = mine.getBooster();
        if(booster > 0){
            amount = amount * booster;
        }
        blocksBroken += amount;
        BlocksMinedEvent e = new BlocksMinedEvent(getPlayer(), amount);
        Bukkit.getPluginManager().callEvent(e);

    }

    public static PPickaxe get(Object oid) {
        return PPickaxeColl.get().get(oid);
    }

    @Override
    public PPickaxe load(@NotNull PPickaxe that)
    {
        super.load(that);
        return this;
    }

    public void updatePickaxe(){
        List<String> lore = new ArrayList<>(List.of("<yellow><bold>Enchants"));
        lore.add("<grey> ");
        enchants.forEach((enchantType, level) -> {
            if(level == 0) return;
            Enchant e = PickaxeConf.get().getEnchantByType(enchantType);
            int prestige = enchantPrestiges.get(enchantType);
            if (e == null) return;
            String enchantmentLore = "<aqua>| " + e.getDisplayItem().getItemName() + "<white>: " + level;
            if(prestige > 0){
                enchantmentLore += " <grey>( P" + prestige + " )";
            }
            lore.add(enchantmentLore);
        });
        pickaxe.setItemLore(lore);
        givePickaxe();
        this.changed();
    }

    public ItemStack getPickaxeGuiItem(){
        return pickaxe.getGuiItem(enchants.get(EnchantType.EFFICIENCY));
    }

    /**
     *
     * @param prestige, pass through true if u want to set up prestige enchants
     * @return Map<EnchantType, Integer>
     */
    private Map<EnchantType, Integer> setEnchants(boolean prestige){
        Map<EnchantType, Integer> enchantList = new EnumMap<>(EnchantType.class);
        PickaxeConf.get().enchants.keySet().forEach(enchant -> {
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
