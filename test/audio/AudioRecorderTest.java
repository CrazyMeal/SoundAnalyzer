package audio;

import org.junit.Test;

import audio.record.AudioRecorder;

public class AudioRecorderTest {

	@Test
	public void test() {
		AudioRecorder recorder = new AudioRecorder();
		recorder.setup();
		recorder.record();
	}

}
