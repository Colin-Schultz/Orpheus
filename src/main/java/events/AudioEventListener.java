package events;

import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class AudioEventListener extends ListenerAdapter {
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event){

        VoiceChannel vc = event.getChannelLeft();
        //Bot isn't connected
        if (vc.getGuild().getSelfMember().getVoiceState().getChannel() == null){
            return;
        }
        System.out.println("user left");
        if(vc.getMembers().size() <= 1 && vc.getGuild().getSelfMember().getVoiceState().getChannel().equals(vc)){
            System.out.println("Disconnecting due to no users in channel after leave");
            event.getGuild().getAudioManager().closeAudioConnection();
        }
    }
    public void onGuildVoiceMove(GuildVoiceMoveEvent event){
        VoiceChannel vc = event.getChannelLeft();
        VoiceChannel bc = vc.getGuild().getSelfMember().getVoiceState().getChannel();
        //Bot isn't connected
        if (vc.getGuild().getSelfMember().getVoiceState().getChannel() == null){
            return;
        }
        System.out.println("user was moved");
        if(!vc.equals(bc) && bc.getMembers().size() <= 1){
            System.out.println("Disconnecting due to being moved to an empty channel");
            event.getGuild().getAudioManager().closeAudioConnection();
        }
        if(vc.getMembers().size() <= 1 && vc.getGuild().getSelfMember().getVoiceState().getChannel().equals(vc)){
            System.out.println("Disconnecting due to no users in channel after move");
            event.getGuild().getAudioManager().closeAudioConnection();
        }
    }
}
