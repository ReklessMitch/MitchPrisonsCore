package mitch.prisonscore.modules.pet.newpets;

import lombok.Getter;
import mitch.prisonscore.modules.mine.utils.BlockInPmineBrokeEvent;
import mitch.prisonscore.modules.pet.entity.PetPlayer;
import mitch.prisonscore.modules.pet.util.PetType;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;

@Getter
public abstract class Pet<T extends PetConfig> implements PetConfigurable<T> {

    private PetType type;

    private Class<T> configClass;

    protected Pet(PetType type, Class<T> configClass) {
        this.type = type;
        this.configClass = configClass;
    }

    @Override
    public String getName() {
        return type.name();
    }

    protected void sendPetMessage(Player player, TagResolver... tagResolver){
        if(!PetPlayer.get(player.getUniqueId()).isPetMessageActive(type)) return;
        final String message = this.getConfig().getPetActivateMessage();
        if(message == null || message.isEmpty()) return;
        MessageUtils.sendMessage(player, message, tagResolver);
    }


    public abstract void activate(BlockInPmineBrokeEvent event, int level);
}
