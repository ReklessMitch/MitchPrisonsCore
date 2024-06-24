package mitch.prisonscore.modules.bazaar;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import lombok.Getter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.bazaar.cmd.CmdBazaar;
import mitch.prisonscore.modules.bazaar.object.ShopValue;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.modules.Module;
import mitch.prisonscore.modules.type.ModuleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@EditorName("config")
public class BazaarModule extends Module {

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

    public static BazaarModule get() {
        return MitchPrisonsCore.get().getModuleByType(ModuleType.BAZAAR);
    }

    public BazaarModule() {
        super(true);
    }

    @Override
    public ModuleType getType() {
        return ModuleType.BAZAAR;
    }

    @Override
    public void enable() {
        if (!isEnabled()) return;
        MitchPrisonsCore.get().activate(
                // cmds
                CmdBazaar.class

                // events
                // Bazaar Get Amount Response here

        );
    }

    @Override
    public void disable() {

    }

}
