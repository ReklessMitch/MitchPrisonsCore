package mitch.prisonscore.utils;

import com.google.common.collect.Lists;
import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;

import java.util.List;

@Getter
@SuppressWarnings("FieldMayBeFinal")
@EditorName("config")
public class LangConf extends Entity<LangConf> {

    public static LangConf i;
    public static LangConf get() {
        return i;
    }

    // Announcements

    private int invisibleCustomData = 100;

    private static List<String> defaultAnnouncement = Lists.newArrayList(
            "<gray>Visit our website at <yellow>https://example.com",
            "<gray>Join our discord at <yellow>https://discord.gg/example"
    );

    private List<List<String>> announcements = List.of(defaultAnnouncement);

    // BattlePass
    private String battlePassNoRewards = "<red>You have no rewards to claim!";
    private String battlePassClaimed = "<red>You have claimed your rewards!";
    private String battlePassLevelUp = "<green>You have leveled up to level <level>!";

    // Booster
    private String boostersCombined = "<green>Your boosters have been combined! (Reactivate your boosters!)";
    private String noBoostersToCombine = "<red>You do not have any boosters to combine!";
    private String boostersTooMany = "<red>You can't have more than 35 boosters! (Combine them!)";
    private String boosterAdded = "<green>You have added a <type> booster with <multi>x multiplier and <time> time!";

    // GUI's
    private String pickaxeGuiTitle = ":offset_-28::pickaxe:";
    private String pickaxeMainGuiTitle = ":offset_-28::mainpickaxe:";
    private String pickaxeSkinsGuiTitle = ":offset_-28::skins:";
    private String pickaxeTogglesGuiTitle = ":offset_-28::toggles:";
    private String mineGuiTitle = ":offset_-28::mine:";
    private String bazaarGuiTitle = ":offset_-28::bazaar:";
    private String petGuiTitle = ":offset_-28::pets:";
    private String boosterGuiTitle = ":offset_-28::boosters:";
    private String passGuiTitle = ":offset_-28::pass:";
    private String backpackGuiTitle = ":offset_-28::backpack:";
    private String chatColourTitle = ":offset_-28::chatcolour:";
    private String nameColourTitle = ":offset_-28::namecolour:";

    // Mine
    private List<String> mineGoGUIItem = Lists.newArrayList("<red>Mine Size: <white>%mitchmines_size%",
            "<red>Mine Block: <white>%mitchmines_block%",
            "<red>Mine Booster: <white>%mitchmines_booster%",
            "<grey>",
            "<green>Click here to teleport to your mine!");
    private List<String> mineBoosterNotMaxed = Lists.newArrayList("<red>Mine Booster: <white>%mitchmines_booster%",
            "<grey>",
            "<green>Click here to buy a mine booster!");
    private List<String> mineBoosterMaxed = Lists.newArrayList("<red>Mine Booster: <white>%mitchmines_booster%");
    private String mineMaxMineBooster = "<red>You have reached the max level for your mine booster";
    private String mineNotEnoughCredits = "<red>You do not have enough credits to upgrade your mine booster";
    private String mineReset = "<green>You have reset your mine!";
    private String mineBoosterAdded = "<green>You have added a mine booster!";

    // Rankup
    private String rankUp = "<green>You have ranked up to %mitchprofiles_rank%!";

    private List<String> rankUpNotEnoughMoney = Lists.newArrayList(
            "<red>You do not have enough money to rank up!",
            "<red>You need <amount> more to rank up!"
    );

    private List<String> rankUpSuccess = Lists.newArrayList(
            "<green>You have ranked up to <rank>!"
    );
    // Pickaxe
    private List<String> pickaxeUpgradeNotHighEnoughRank = Lists.newArrayList(
            "<red>You do not have a high enough rank to upgrade your pickaxe!"
    );

    private List<String> pickaxeUpgradeAlreadyMaxed = Lists.newArrayList(
            "<red>Your pickaxe is already maxed out!"
    );

    private List<String> pickaxeSkinSelected = Lists.newArrayList(
            "<green>You have selected the <skin> pickaxe skin!"
    );

    private List<String> pickaxeSkinAlreadySelected = Lists.newArrayList(
            "<red>You already have this pickaxe skin selected!"
    );

    private List<String> pickaxeSkinNotUnlocked = Lists.newArrayList(
            "<red>You have not unlocked this pickaxe skin!"
    );

    // Backpack
    private List<String> backpackUpgradeNotEnoughTokens = Lists.newArrayList(
            "<red>You do not have enough tokens to upgrade your backpack!"
    );

    // Autosell
    private List<String> autoSellAlreadyPurchased = Lists.newArrayList(
            "<red>You already have autosell!"
    );

    private List<String> autoSellNotEnoughTokens = Lists.newArrayList(
            "<red>You do not have enough tokens to purchase autosell!"
    );

    private List<String> autoSellPurchased = Lists.newArrayList(
            "<green>You have purchased autosell for <cost> tokens!"
    );

    // Backpack Skin
    private List<String> backpackSkinNoPerm = Lists.newArrayList(
            "<red>You do not have permission to use this backpack skin!"
    );

    private List<String> backpackSkinAlreadyEquipped = Lists.newArrayList(
            "<red>You already have this backpack skin equipped!"
    );

    private List<String> backpackSkinEquipped = Lists.newArrayList(
            "<green>You have equipped the <skin> backpack skin!"
    );

    // Battlepass
    private List<String> battlePassNotEnoughCredits = Lists.newArrayList(
            "<red>You do not have enough credits to purchase the battlepass!"
    );

    private List<String> battlePassPurchaseSuccess = Lists.newArrayList(
            "<red>You have purchased the battlepass!"
    );

    // Store
    private List<String> storeNotEnoughCredits = Lists.newArrayList(
            "<red>You do not have enough credits to purchase this item!"
    );

    private List<String> storePurchaseSuccess = Lists.newArrayList(
            "<green>You have purchased <item> for <cost> credits!"
    );

    // Transfers
    private List<String> transferCannotPayYourself = Lists.newArrayList(
            "<red>You cannot pay yourself!"
    );

    private List<String> transferPlayerNotJoinedServer = Lists.newArrayList(
            "<red>Player has not joined the server yet!"
    );

    private List<String> transferInvalidAmount = Lists.newArrayList(
            "<red>Invalid amount / character (k, m, b)!"
    );

    private List<String> transferNotEnoughCurrency = Lists.newArrayList(
            "<red>You do not have enough <currency>(s) to transfer!"
    );

    private List<String> transferSuccessSend = Lists.newArrayList(
            "<green>You have transferred <amount> <currency> to <receiever>!"
    );

    private List<String> transferSuccessReceive = Lists.newArrayList(
            "<green><sender> has transferred <amount> <currency> to you!"
    );

    private String wardrobeNoPermission = "<red> You do not have permission to use this item!";

    private String prestigeEnchantSuccess = "<green> You have prestiged the <enchant> enchantment!";

    private String prestigeEnchantCancelled = "<red> You have cancelled the prestige";

    private String backpackMessagesToggled = "<green> You have toggled backpack messages <toggle>!";

    // Bazaar
    private String bazaarNotEnoughInStock = "<grey>This item only has <red><amount> in stock!";

    private String bazaarNotEnoughToSell = "<red>You do not have enough <cts> to sell!";

    private String bazaarSellSuccess = "<green>You have added <amount> <cts> to the sell shop for <price> <ctb>";

    private List<String> addPercentOfRankup = Lists.newArrayList(
            "<grey>You have been given <green><percent> <grey>of <green><cost> <grey>of your next rankup <green><amount><grey>.!"
    );

    private String addSlotToBackpack = "<green>You have added <amount> slots to your backpack!";


    // cell / team / gang
    private String notInACell = "<red>You are not in a cell!";
    private String cellDoesNotExist = "<red>That cell does not exist!";
    private String cellCreatedSuccess = "<green>You have created a cell!";
    private String cellNameTaken = "<red>That cell name is already taken!";
    private String alreadyInCell = "<red>You are already in a cell!";
    private String cellCreated = "<green>You have created the cell <cellname>";
    private String cellDisbanded = "<red>You have disbanded your cell!";
    private String addedBeaconsToCell = "<green>You have added <amount> beacons to your cell!";
    private String notEnoughBeacons = "<red>You do not have enough beacons!";
    private String notOwnerOfCell = "<red>You are not the owner of this cell!";
    private List<String> cellInfo = Lists.newArrayList("<grey>------------<reset> <gold><bold>Cell Info <grey>------------",
                "<grey>Cell Name: <gold><cellname>",
                "<grey>Cell Owner: <gold><cellowner>",
                "<grey>Cell Members: <gold><cellmembers>",
                "<grey>Beacons: <gold><cellbeacons>",
                "<grey>----------------------------------");
    private String notHighEnoughCellRank = "<red>You are not a high enough rank in your cell!";
    private String cellInviteSent = "<green>You have sent an invite to <player>!";
    private String cellInviteReceived = "<green>You have received an invite to <cellname> from <inviter>!";
    private String playerAlreadyInvitedToCell = "<red>You have already invited this player!";
    private String cellIsFull = "<red>This cell is full!";
    private String cellJoinedSuccess = "<green>You have joined the cell <cellname>!";
    private String notInvitedToCell = "<red>You have not been invited to this cell!";
    private String mustDisbandCellAsOwner = "<red>You are the owner of this cell, you must disband it to leave!";
    private String cellLeftSuccess = "<red>You have left the cell!";
    private String notAnyoneKickableFromCell = "<red>You do not have anyone in ur cell you can kick";

    // Crate

    private String noCrateKeyForCrate = "<red>You do not have any crate keys for this crate!";
    private String crateKeyNotEnoughAmount = "<red>You do not have enough crate keys for this amount of crates!";


    // Profile / Currency
    private List<String> getPlayerBalances = Lists.newArrayList(
            "<grey>------------<reset> <gold><bold>Balance <grey>------------",
            "<grey>Money: <gold><money>",
            "<grey>Tokens: <gold><tokens>",
            "<grey>Credits: <gold><credits>",
            "<grey>Beacons: <gold><beacons>",
            "<grey>----------------------------------"
    );

    private String addedCurrencyToPlayer = "<green>You have added <red><amount> <currency> <green>to <red> <player>";
    private String removedCurrencyFromPlayer = "<green>You have removed <red><amount> <currency> <green>from <red> <player>";
    private String setCurrencyToPlayer = "<green>You have set <red><player> <green> <currency> to <red><amount>";




}