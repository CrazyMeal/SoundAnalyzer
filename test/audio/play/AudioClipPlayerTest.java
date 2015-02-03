package audio.play;

import org.junit.Test;

import audio.old.AudioClipPlayer;

public class AudioClipPlayerTest {

	@Test
	public void test() {
		AudioClipPlayer player =new AudioClipPlayer();
		player.setup();
		player.play();
	}

}
