package me.reklessmitch.mitchprisonscore.colls;

import com.massivecraft.massivecore.store.Coll;
import me.reklessmitch.mitchprisonscore.utils.MessagesConfig;

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

