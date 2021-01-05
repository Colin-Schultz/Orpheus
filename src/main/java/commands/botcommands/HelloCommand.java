package commands.botcommands;

import commands.CommandContext;
import commands.ICommand;

public class HelloCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        ctx.getChannel().sendMessage("World!").queue();
    }

    @Override
    public String getName() {
        return "hello";
    }

    @Override
    public String getHelp() {
        return "Returns a message";
    }
}
