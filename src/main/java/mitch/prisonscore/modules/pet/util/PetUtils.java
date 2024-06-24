package mitch.prisonscore.modules.pet.util;

import mitch.prisonscore.utils.MathUtils;
import org.bukkit.Location;

import java.util.List;

import static mitch.prisonscore.modules.pet.util.PetType.*;

public class PetUtils {

    public static List<PetType> getNormalPets(){
        return List.of(JACKHAMMER_BOOST, MONEY, TOKEN, CRATE, SUPPLY_DROP);
    }

    public static List<PetType> getAdvancedPets(){
        return List.of(MINER, REAPER);
    }

    public static Location getRelativePetLocation(Location location, int petPosition, int petSlots) {
        Location eyeLocation = location.clone().subtract(0.0D, 0.0075D, 0.0D);

        double xLocation = eyeLocation.getX() + 0.5D * MathUtils.cos(MathUtils.toRadians((double) eyeLocation.getYaw() + -90.0D));
        double yLocation = eyeLocation.getY();
        double zLocation = eyeLocation.getZ() + 0.5D * MathUtils.sin(MathUtils.toRadians((double) eyeLocation.getYaw() + -90.0D));

        eyeLocation = new Location(eyeLocation.getWorld(), xLocation, yLocation, zLocation, eyeLocation.getYaw(), eyeLocation.getPitch());

        double offset;
        double petRotation;
        if (petSlots < 6) {
            petRotation = switch (petPosition) {
                case 0 -> -45.0D;
                case 1 -> -135.0D;
                case 2 -> -90.0D;
                case 3 -> 0.0D;
                default -> 180.0D;
            };

        } else {
            petRotation = switch (petPosition) {
                case 0 -> -36.0D;
                case 1 -> -108.0D;
                case 2 -> -72.0D;
                case 3 -> 0.0D;
                case 4 -> -144.0D;
                default -> -180.0D;
            };

        }
        offset = petRotation;

        float yaw = eyeLocation.getYaw();
        double cX = eyeLocation.getX();
        double cY = eyeLocation.getZ();

        double rX = 3.5D;
        double rY = 2.0D;
        double alpha = MathUtils.toRadians(offset);
        double theta = MathUtils.toRadians((double) yaw);

        xLocation = rX * MathUtils.cos((float) alpha) * MathUtils.cos((float) theta) - rY * MathUtils.sin((float) alpha) * MathUtils.sin((float) theta) + cX;
        yLocation = eyeLocation.getY();
        zLocation = rX * MathUtils.cos((float) alpha) * MathUtils.sin((float) theta) + rY * MathUtils.sin((float) alpha) * MathUtils.cos((float) theta) + cY;

        return new Location(eyeLocation.getWorld(), xLocation, yLocation - 0.3, zLocation, eyeLocation.getYaw(), 0.0F);
    }
}