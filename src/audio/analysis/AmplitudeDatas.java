package audio.analysis;

public class AmplitudeDatas {
	private int[] datas;
	private int max, min;
	
	public AmplitudeDatas(int[] datas){
		this.datas = datas;
		
		for(int data : this.datas){
			if(data > this.max)
				max = data;
			else
				if(data < this.min)
					min = data;
		}
	}
	
	public double[] getNormalizedDatas(){
		double[] normalizedDatas = new double[this.datas.length];
		
		for(int index=0; index < this.datas.length; index++){
			if(this.datas[index] == 0)
				normalizedDatas[index] = 0;
			else{
				if(this.datas[index] > 0)
					normalizedDatas[index] = ((float)datas[index]) / this.max;
				else{
					normalizedDatas[index] = -((float)datas[index]) / this.min;
				}
			}
		}
		
		return normalizedDatas;
	}
	
	public int[] getDatas(){
		return this.datas;
	}
	public int getMax(){
		return this.max;
	}
	public int getMin(){
		return this.min;
	}
}
