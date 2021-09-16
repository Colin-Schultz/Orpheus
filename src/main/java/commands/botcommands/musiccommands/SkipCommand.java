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
import java.util.concurrent.TimeUnit;
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
        if(playingTrack == null){
            return;
        }
        AudioTrackInfo info = playingTrack.getInfo();

        channel.sendMessageFormat("Now playing `%s` by `%s`\n(Link: <%s>)\nDuration:%s", info.title, info.author, info.uri, formatTime(playingTrack.getDuration())).queue();



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
        return  Stream.of("skp","fs").collect(Collectors.toList());
    }

    private String formatTime(long duration){
        final long hours = duration / TimeUnit.HOURS.toMillis(1);
        final long minutes = duration / TimeUnit.MINUTES.toMillis(1);
        final long seconds = duration % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
