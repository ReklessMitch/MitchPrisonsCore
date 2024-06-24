package mitch.prisonscore.colls;

import com.massivecraft.massivecore.store.Coll;
import mitch.prisonscore.utils.MessagesConfig;

public class MessageConfColl extends Coll<MessagesConfig> {

    private static final MessageConfColl i = new MessageConfColl();
    public static MessageConfColl get() { return i; }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        if(!active){
            return;
        }
        MessagesConfig.i = this.get("messages", true);
    }
}

