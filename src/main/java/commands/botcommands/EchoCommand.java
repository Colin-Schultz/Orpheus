package commands.botcommands;

import AudioHandlers.EchoHandler;
import commands.CommandContext;
import commands.ICommand;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.List;

public class EchoCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        GuildMessageReceivedEvent event = ctx.getEvent();
        List<String> args = ctx.getArgs();
        VoiceChannel connectedChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();
        if (connectedChannel == null) {
            event.getChannel().sendMessage("I am currently not connected to any voice channels").queue();
            return;
        }
        if(args.size()==0){
            event.getChannel().sendMessage("This command requires 1 argument (start/stop)").queue();
            return;
        }
        if (args.get(0).equalsIgnoreCase("start")) {
            EchoHandler handler = new EchoHandler();
            AudioManager audioManager = event.getGuild().getAudioManager();
            audioManager.setSendingHandler(handler);
            audioManager.setReceivingHandler(handler);
            audioManager.openAudioConnection(connectedChannel);
            event.getChannel().sendMessage("I am now echoing you're speech").queue();
        }
        //Resets the audio Handlers to null
        else if (args.get(0).equalsIgnoreCase("stop")){
            event.getGuild().getAudioManager().setSendingHandler(null);
            event.getGuild().getAudioManager().setReceivingHandler(null);
            event.getChannel().sendMessage("I am no longer echoing you're speech").queue();
        }
        else{
            event.getChannel().sendMessage("Argument not recognized").queue();
        }
    }
    @Override
    public String getHelp() {
        return "Will echo speech. Start with 'Echo Start' and stop with 'Echo Stop'";
    }
    @Override
    public String getName() {
        return "echo";
    }
}
