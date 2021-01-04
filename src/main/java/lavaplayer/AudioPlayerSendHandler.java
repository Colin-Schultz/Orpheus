package lavaplayer;

import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.audio.CombinedAudio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;


import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AudioPlayerSendHandler implements AudioSendHandler{
    /*
        All methods in this class are called by JDA threads when resources are available/ready for processing.
        The receiver will be provided with the latest 20ms of PCM stereo audio
        Note you can receive even while setting yourself to deafened!
        The sender will provide 20ms of PCM stereo audio (pass-through) once requested by JDA
        When audio is provided JDA will automatically set the bot to speaking!
     */
    private final AudioPlayer audioPlayer;
    private final ByteBuffer buffer;
    private final MutableAudioFrame frame;

    /* Receive Handling */
    public AudioPlayerSendHandler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        this.buffer = ByteBuffer.allocate(1024);
        this.frame = new MutableAudioFrame();
        this.frame.setBuffer(buffer);

    }

    @Override
    public boolean canProvide()
    {return this.audioPlayer.provide(this.frame);

    }

    @Override
    public ByteBuffer provide20MsAudio(){
        final Buffer tmp = ((Buffer) this.buffer).flip();
        return (ByteBuffer)tmp;
    }

    @Override
    public boolean isOpus()
    {
        return true;
    }
}
