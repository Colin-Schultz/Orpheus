import configs.Config;
import events.AudioEventListener;
import events.Listener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class OrpheusDriver {

    public static void main(String[] args) throws Exception{
        JDA jda = JDABuilder.createDefault(Config.get("BOT_TOKEN")).build();

        jda.addEventListener(new Listener());
        //jda.addEventListener(new TextEventListener());
        jda.addEventListener(new AudioEventListener());
    }
}
