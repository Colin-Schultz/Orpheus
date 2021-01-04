import events.AudioEventListener;
import events.TextEventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class OrpheusDriver {

    public static void main(String[] args) throws Exception{
        JDA jda = JDABuilder.createDefault(System.getenv("BOT_TOKEN")).build();

        jda.addEventListener(new TextEventListener());
        jda.addEventListener(new AudioEventListener());
    }
}
