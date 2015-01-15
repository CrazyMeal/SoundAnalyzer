package audio.analysis;

public class AmplitudeDatas {
	private int[] datas;
	private double[] normalizedDatas;
	private int max, min;
	private boolean normalized;
	
	public AmplitudeDatas(int[] datas){
		this.datas = datas;
		
		for(int data : this.datas){
			if(data > this.max)
				max = data;
			else
				if(data < this.min)
					min = data;
		}
		this.normalizedDatas = new double[this.datas.length];
		this.normalized = false;
	}
	
	public double[] getNormalizedDatas(){
		if(!this.normalized){
			for(int index=0; index < this.datas.length; index++){
				if(this.datas[index] == 0)
					this.normalizedDatas[index] = 0;
				else{
					if(this.datas[index] > 0)
						this.normalizedDatas[index] = ((float)datas[index]) / this.max;
					else{
						this.normalizedDatas[index] = -((float)datas[index]) / this.min;
					}
				}
			}
			this.normalized = true;
		}
		return this.normalizedDatas;
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
