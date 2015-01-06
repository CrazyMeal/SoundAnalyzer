package audio;

import static org.junit.Assert.*;

import org.junit.Test;

import audio.play.AudioPlayer;

public class AudioPlayerTest {

	@Test
	public void test() {
		AudioPlayer player = new AudioPlayer();
		player.createStreamFromFile("./src/res/Music.wav");
		assertEquals(1, player.getStreamQuantity());
		player.readStreams();
		player.clearStreamList();
		assertEquals(0, player.getStreamQuantity());
	}

}
