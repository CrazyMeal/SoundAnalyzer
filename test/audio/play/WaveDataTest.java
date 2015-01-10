package audio.play;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import audio.analysis.WaveData;

public class WaveDataTest {

	@Test
	public void test() {
		File file = new File("src/res/sqr-10-2Hz.wav");
		WaveData wd = new WaveData();
		int[] tab = wd.extractAmplitudeFromFile(file);
		for(int i : tab){
			System.out.println(i);
		}
	}

}
