package commands.botcommands;

import commands.CommandContext;
import commands.CommandManager;
import commands.ICommand;
import configs.Config;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HelpCommand implements ICommand {
    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();
        if (args.isEmpty()){
            StringBuilder builder = new StringBuilder();
            builder.append("List of commands\n");
            manager.getAllCommands().stream().map(ICommand::getName).forEach(
                    (it) -> builder.append('`').append(Config.get("PREFIX")).append(it).append("`\n")
            );
            channel.sendMessage(builder.toString()).queue();
            return;
        }
        String search = args.get(0);
        ICommand command = manager.getCommand(search);
        if (command == null){
            channel.sendMessage("Nothing found for " + search).queue();
            return;
        }
        channel.sendMessage(command.getHelp()).queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Shows the list of commands for the bot\n" + "Usage: !help [command]";
    }

    @Override
    public  List<String> getAliases(){
        return  Stream.of("commands", "cmds", "commandlist").collect(Collectors.toList());
    }
}
