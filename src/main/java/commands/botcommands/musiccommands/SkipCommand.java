package commands.botcommands.musiccommands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import commands.CommandContext;
import commands.ICommand;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SkipCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();


        if(!selfVoiceState.inVoiceChannel()){
            channel.sendMessage("I need to be in a voice channel for this to work").queue();
            return;
        }
        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if(!memberVoiceState.inVoiceChannel()){
            channel.sendMessage("You need to be in a voice channel for this to work").queue();
            return;
        }

        if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){
            channel.sendMessage("You need to be in the same voice channel as me for this to work").queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;

        if(audioPlayer.getPlayingTrack() == null){
            channel.sendMessage("There is no track playing").queue();
            return;
        }
        musicManager.scheduler.nextTrack();
        channel.sendMessage("The current track has been skipped").queue();

        final AudioTrack playingTrack = audioPlayer.getPlayingTrack();
        AudioTrackInfo info = playingTrack.getInfo();

        long positionMinutes = playingTrack.getPosition()/1000/60;
        long positionSeconds = playingTrack.getPosition()/1000%60;
        String position = (positionMinutes + ":" + positionSeconds);
        long durationMinutes = playingTrack.getDuration()/1000/60;
        long durationSeconds = playingTrack.getDuration()/1000%60;
        String duration = (durationMinutes + ":" + durationSeconds);

        channel.sendMessageFormat("Now playing `%s` by `%s`\n(Link: <%s>)\nDuration:%s/%s", info.title, info.author, info.uri, position, duration).queue();



    }

    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getHelp() {
        return "Skips the current track.";
    }

    @Override
    public List<String> getAliases(){
        return  Stream.of("skp").collect(Collectors.toList());
    }
}
