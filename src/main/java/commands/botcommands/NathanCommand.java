package commands.botcommands;

import commands.CommandContext;
import commands.ICommand;

public class NathanCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        ctx.getChannel().sendMessage("He is the host of all things!").queue();
    }
    @Override
    public String getHelp() {
        return "Returns a message";
    }
    @Override
    public String getName() {
        return "nathan";
    }
}
