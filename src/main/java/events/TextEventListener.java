package events;

import AudioHandlers.EchoHandler;
import net.dv8tion.jda.api.Permission;

import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;



public class TextEventListener extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String messageSent = event.getMessage().getContentRaw();
        if(event.getMember().getUser().isBot()){return;}

        if(messageSent.toCharArray()[0] == '!'){
            String[] messageSentArray = messageSent.substring(1).split(" ");
            //Hello - World response
            switch (messageSentArray[0].toLowerCase()){
                case "hello":
                    event.getChannel().sendMessage("World!").queue();
                    break;
                case "nathan":
                    event.getChannel().sendMessage("He is the host of all!").queue();
                    break;

                case "colin":
                    event.getChannel().sendMessage("He is my creator!").queue();
                    break;
                case "connect":
                    //Checks perms for joining channels
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
                    break;
                case "leave": {
                    VoiceChannel connectedChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();
                    if (connectedChannel == null) {
                        event.getChannel().sendMessage("I am currently not connected to any voice channels").queue();
                    } else {
                        event.getGuild().getAudioManager().closeAudioConnection();
                        event.getChannel().sendMessage("Disconnected from the voice channel.").queue();
                    }
                    break;
                }
                //Repeats all audio
                case "echo":{
                    if (messageSentArray[1].equalsIgnoreCase("start")) {
                        VoiceChannel connectedChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();
                        if (connectedChannel == null) {
                            event.getChannel().sendMessage("I am currently not connected to any voice channels").queue();
                        }
                        EchoHandler handler = new EchoHandler();
                        AudioManager audioManager = event.getGuild().getAudioManager();
                        audioManager.setSendingHandler(handler);
                        audioManager.setReceivingHandler(handler);
                        audioManager.openAudioConnection(connectedChannel);
                    }
                    //Resets the audio Handlers to null
                    else if (messageSentArray[1].equalsIgnoreCase("stop")){
                        event.getGuild().getAudioManager().setSendingHandler(null);
                        event.getGuild().getAudioManager().setReceivingHandler(null);
                    }
                }
            }

        }


    }

}

