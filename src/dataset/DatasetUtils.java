package dataset;

import java.io.File;

import org.jfree.data.xy.DefaultIntervalXYDataset;
import org.jfree.data.xy.DefaultXYDataset;

import audio.analysis.AmplitudeDatas;
import audio.play.WavePlayer;

public class DatasetUtils {
	public static DefaultXYDataset loadFile(String name){
    	File file = new File(name);
		return loadFile(file);
    }
    
    public static DefaultXYDataset loadFileAndNormalize(String name){
		File file = new File(name);
		return loadFileAndNormalize(file);
    }
    
    public static DefaultXYDataset loadFileAndNormalize(File file){
		WavePlayer player = new WavePlayer(file);
		player.setup();
		AmplitudeDatas datas = new AmplitudeDatas(player.analyze(), player.getDuration());
		player.close();
		DefaultXYDataset dataset = new DefaultXYDataset();
		int j = 0;
		
		double nbPoint = 100000;
		double step = datas.getDatas().length/nbPoint;
		
		
		double[][] data = new double[2][(int) (nbPoint)];
		for(double i = 0; i < datas.getDatas().length; i+=step){
			if(j<nbPoint){
				data[0][j] = ((double)j);
				data[1][j] = datas.getNormalizedDatas()[(int)i];
				j++;
			}
		}
		dataset.addSeries("test", data);
		System.out.println("normalized dataset created");
		return dataset;
    }
    
    public static DefaultXYDataset loadFile(File file){
    	WavePlayer player = new WavePlayer(file);
		player.setup();
		int[] datas = player.analyze();
		double interval = 1/player.getFrameRate()/2;
		player.close();
		DefaultXYDataset dataset = new DefaultXYDataset();
		int j = 0;
		
		double nbPoint = 100000;
		double step = datas.length/nbPoint;
		
		
		double[][] data = new double[2][(int) (nbPoint)];
		for(double i = 0; i < datas.length; i+=step){
			if(j<nbPoint){
				data[0][j] = interval*i;
				data[1][j] = datas[(int)i];
				j++;
			}
		}
		dataset.addSeries("test", data);
		System.out.println("dataset created");
		return dataset;
    }
}
