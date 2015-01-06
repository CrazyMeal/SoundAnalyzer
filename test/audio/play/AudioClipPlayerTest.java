package audio.play;

import org.junit.Test;

import audio.play.AudioClipPlayer;

public class AudioClipPlayerTest {

	@Test
	public void test() {
		AudioClipPlayer player =new AudioClipPlayer();
		player.setup();
		player.play();
	}

}
