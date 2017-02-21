package BaseBean;

public class Price {
	private String time;
	private int cost;
	public Price(){}
	public Price(String time,int cost){
		this.time=time;
		this.cost=cost;
	}
	
	public void setTime(String time){
		this.time=time;
	}
	public void setCost(int cost){
		this.cost=cost;
	}
	
	public String getTime(){
		return time;
	}
	
	public int getCost(){
		return cost;
	}
}