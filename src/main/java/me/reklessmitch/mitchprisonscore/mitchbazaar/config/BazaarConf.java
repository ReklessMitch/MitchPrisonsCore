package me.reklessmitch.mitchprisonscore.mitchbazaar.config;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchbazaar.object.ShopValue;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.Currency;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@EditorName("config")
public class BazaarConf extends Entity<BazaarConf> {

    public static BazaarConf i;
    public static BazaarConf get() {return i;}

    @Getter
    private Map<Currency, Map<Currency, List<ShopValue>>> sellPrices = Map.of(
            Currency.BEACON, Map.of(
                    Currency.TOKEN, new ArrayList<>(),
                    Currency.MONEY, new ArrayList<>(),
                    Currency.CREDIT, new ArrayList<>()
            ),
            Currency.TOKEN, Map.of(
                    Currency.BEACON, new ArrayList<>(),
                    Currency.MONEY, new ArrayList<>(),
                    Currency.CREDIT, new ArrayList<>()
            ),
            Currency.MONEY, Map.of(
                    Currency.BEACON, new ArrayList<>(),
                    Currency.TOKEN, new ArrayList<>(),
                    Currency.CREDIT, new ArrayList<>()
            ),
            Currency.CREDIT, Map.of(
                    Currency.BEACON, new ArrayList<>(),
                    Currency.TOKEN, new ArrayList<>(),
                    Currency.MONEY, new ArrayList<>()
            )
    );


}
