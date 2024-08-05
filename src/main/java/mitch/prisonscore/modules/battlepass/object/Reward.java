package mitch.prisonscore.modules.battlepass.object;

import lombok.Getter;

import java.util.List;

@Getter
public class Reward {
    String name;
    List<String> commands;
    List<String> messages;

    public Reward(String name, List<String> commands, List<String> messages) {
        this.name = name;
        this.commands = commands;
        this.messages = messages;
    }


}
