package dataset;

import java.io.File;

import org.jfree.data.xy.DefaultXYDataset;

import audio.analysis.AmplitudeDatas;
import audio.play.WavePlayer;

public class DatasetUtils {
	public static DefaultXYDataset loadFile(String name){
    	File file = new File(name);
		WavePlayer player = new WavePlayer(file);
		player.setup();
		int[] datas = player.analyze();
		player.close();
		DefaultXYDataset dataset = new DefaultXYDataset();
		int j = 0;
		double[][] data = new double[2][datas.length>10000?10000:datas.length];
		double step = (datas.length/10000)<1?1:datas.length/10000.0;
		System.out.println(step);
		for(int i = 0; i < datas.length/10000; i++){
			data[0][j] = ((double)j);
			data[1][j] = datas[(int)i];
			j++;
		}
		dataset.addSeries("test", data);
		System.out.println("dataset created");
		return dataset;
    }
    
    public static DefaultXYDataset loadFileAndNormalize(String name){
    	File file = new File(name);
		WavePlayer player = new WavePlayer(file);
		player.setup();
		AmplitudeDatas datas = new AmplitudeDatas(player.analyze(), player.getDuration());
		player.close();
		DefaultXYDataset dataset = new DefaultXYDataset();
		int j = 0;
		double[][] data = new double[2][datas.getDatas().length];
		for(double i : datas.getNormalizedDatas()){
			data[0][j] = ((double)j);
			data[1][j] = i;
			j++;
		}
		dataset.addSeries("test", data);
		System.out.println("normalized dataset created");
		return dataset;
    }
}
