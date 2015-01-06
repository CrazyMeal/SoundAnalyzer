package audio;

import org.junit.Test;

public class AudioRecorderTest {

	@Test
	public void test() {
		AudioRecorder recorder = new AudioRecorder();
		recorder.setup();
		recorder.record();
	}

}
