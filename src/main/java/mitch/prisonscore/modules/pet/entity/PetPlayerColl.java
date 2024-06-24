package mitch.prisonscore.modules.pet.entity;

import com.massivecraft.massivecore.store.SenderColl;

public class PetPlayerColl extends SenderColl<PetPlayer> {
    private static final PetPlayerColl i = new PetPlayerColl();
    public static PetPlayerColl get() { return i; }
}
