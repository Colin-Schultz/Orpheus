import events.AudioEventListener;
import events.TextEventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class OrpheusDriver {

    public static void main(String[] args) throws Exception{
        JDA jda = JDABuilder.createDefault("Nzk1NjgwNDk1MTk0ODY1NzM2.X_M5NQ.hh_j_AxGbh6PjLFiY_ijyk6vprQ").build();

        jda.addEventListener(new TextEventListener());
        jda.addEventListener(new AudioEventListener());
    }
}
