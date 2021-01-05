package commands.botcommands;

import commands.CommandContext;
import commands.ICommand;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class LeaveCommand implements ICommand {
    @Override
    public void handle(CommandContext event) {
        VoiceChannel connectedChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();
        if (connectedChannel == null) {
            event.getChannel().sendMessage("I am currently not connected to any voice channels").queue();
        } else {
            event.getGuild().getAudioManager().closeAudioConnection();
            event.getChannel().sendMessage("Disconnected from the voice channel.").queue();
        }
    }
    @Override
    public String getHelp() {
        return "Disconnects the bot from a channel if it is connected";
    }
    @Override
    public String getName() {
        return "leave";
    }
}
