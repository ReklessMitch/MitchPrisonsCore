package mitch.prisonscore.modules.type;

import lombok.Getter;

public enum ModuleType {
   RANKUP("Rankup"),
   BOOSTERS("Boosters"),
   BACKPACK("Backpack"),
   BATTLEPASS("Battlepass"),
   BAZAAR("Bazaar"),
   CELLS("Cells"),
   PETS("Pets"),
   MINES("Mines"),
   PROFILE("Profile"),
   PICKAXE("Pickaxe"),
   CRATES("Crates"),
   CRYSTALS("Crystals");

   @Getter private String name;

   ModuleType(String name) {
      this.name = name;
   }
}
