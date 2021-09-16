package commands.botcommands.musiccommands;

import commands.CommandContext;
import commands.ICommand;
import commands.botcommands.ConnectCommand;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayCommand extends ConnectCommand {

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();
        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if(ctx.getArgs().isEmpty()){
            channel.sendMessage("I need arguments for this command.").queue();
        }

        if(!selfVoiceState.inVoiceChannel()){
            connect(ctx);
        }

        else if(!memberVoiceState.inVoiceChannel()){
            channel.sendMessage("You need to be in a voice channel for this to work").queue();
            return;
        }

        else if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){
            channel.sendMessage("You need to be in the same voice channel as me for this to work").queue();
            return;
        }

        String link = String.join(" ", ctx.getArgs());

        if(!isUrl(link)){
            link = "ytsearch:" + link;
        }
        PlayerManager.getInstance().loadAndPlay(channel, link);
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public List<String> getAliases(){
        return  Stream.of("p").collect(Collectors.toList());
    }

    @Override
    public String getHelp() {
        return "Plays music\n" + "!play <youtube link>";
    }

    private boolean isUrl(String url){
        try{
            URI test = new URL(url).toURI();
            return true;
        }catch (URISyntaxException | MalformedURLException e){
            return false;
        }
    }
}
