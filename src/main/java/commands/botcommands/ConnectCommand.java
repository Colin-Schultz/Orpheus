package commands.botcommands;

import commands.CommandContext;
import commands.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConnectCommand implements ICommand {
    @Override
    public void handle(CommandContext event) {
        connect(event);
    }
    @Override
    public String getHelp() {
        return "Connects the bot to the channel the user is in if it has permissions for it";
    }
    @Override
    public String getName() {
        return "connect";
    }

    @Override
    public List<String> getAliases(){
        return  Stream.of("join").collect(Collectors.toList());
    }

    public void connect(CommandContext event){
        if (!event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.VOICE_CONNECT)){
            event.getChannel().sendMessage("Sorry, I do not have permissions to connect to voice channels.").queue();
            return;
        }
        //Gets the user's channel
        VoiceChannel joinChannelRequest = event.getMember().getVoiceState().getChannel();
        //System.out.println(event.getGuild().getMember(event.getAuthor()).getVoiceState().getChannel());

        if(joinChannelRequest == null){
            String user = event.getAuthor().getName();
            event.getChannel().sendMessage(user + " is not in a voice channel.").queue();
        }
        else if (!event.getGuild().getSelfMember().hasPermission(joinChannelRequest, Permission.VOICE_CONNECT)){
            event.getChannel().sendMessage("Sorry, I do not have permission to connect to this channel.").queue();
        }
        else{
            AudioManager audioManager = event.getGuild().getAudioManager();
            if(audioManager.isAttemptingToConnect()) {
                event.getChannel().sendMessage("The bot is already attempting to connect. Please wait.").queue();
            }
            // Connects to the channel.
            audioManager.openAudioConnection(joinChannelRequest);
            // Obviously people do not notice someone/something connecting.
            event.getChannel().sendMessage("Connected to the voice channel!").queue();
        }
    }
}
