package mitch.prisonscore.modules.type;


import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.TypeAbstract;
import com.massivecraft.massivecore.comparator.ComparatorCaseInsensitive;
import mitch.prisonscore.modules.Module;
import mitch.prisonscore.modules.ModuleColl;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class TypeModule extends TypeAbstract<Module> {

   private static TypeModule i = new TypeModule();
   public static TypeModule get() {
      return i;
   }

   public TypeModule() {
      super(Module.class);
   }

   @Override
   public Module read(String s, CommandSender commandSender) throws MassiveException {
      Module module = ModuleColl.get().getAll().stream().filter(m -> m.getName().replace(" ", "_").equalsIgnoreCase(s)).findFirst().orElse(null);

      if (module != null) return module;

      throw new MassiveException().addMsg("<b>No custom set could be found matching \"<p>%s<b>\".", s);
   }

   @Override
   public Collection<String> getTabList(CommandSender commandSender, String s) {
      Set<String> ret = new TreeSet<>(ComparatorCaseInsensitive.get());
      for (Module module: ModuleColl.get().getAll()) {
         ret.add(ChatColor.stripColor(module.getName().replace(" ", "_")));
      }
      return ret;
   }
}
