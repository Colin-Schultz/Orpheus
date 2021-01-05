package commands.botcommands;

import commands.CommandContext;
import commands.ICommand;

public class ColinCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        ctx.getChannel().sendMessage("He is my creator!").queue();
    }
    @Override
    public String getHelp() {
        return "Returns a message";
    }
    @Override
    public String getName() {
        return "colin";
    }
}
