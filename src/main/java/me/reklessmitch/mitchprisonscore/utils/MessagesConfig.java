package me.reklessmitch.mitchprisonscore.utils;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import java.util.Map;

@Getter
@EditorName("config")
public class MessagesConfig extends Entity<MessagesConfig> {

    public static MessagesConfig i;
    public static MessagesConfig get() { return i; }

    private Map<String, String> globalPlaceholders = Map.of(
            "server_prefix", "<red>ServerName <gray>» "
    );
}

