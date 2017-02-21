package BaseBean;

import java.util.Date;

public class CarBean {
	private String car_Type;
	private Date approach_Time;
	private Date leaving_Time;
	private String car_Number;
	private String car_Place;
	private int car_Cost;

	public CarBean() {
	}

	public CarBean(String car_Type, Date approach_Time, Date leaving_Time, String car_Number, String car_Place,
			int car_Cost) {
		this.car_Type = car_Type;
		this.approach_Time = approach_Time;
		this.leaving_Time = leaving_Time;
		this.car_Number = car_Number;
		this.car_Place = car_Place;
		this.car_Cost = car_Cost;
	}

	public void setCar_Type(String car_Type) {
		this.car_Type = car_Type;
	}

	public void setApproach_Time(Date approach_Time) {
		this.approach_Time = approach_Time;
	}

	public void setLeaving_Time(Date leaving_Time) {
		this.leaving_Time = leaving_Time;
	}

	public void setCar_Number(String car_Number) {
		this.car_Number = car_Number;
	}

	public void setCar_Place(String car_Place) {
		this.car_Place = car_Place;
	}

	public void setCar_Cost(int car_Cost) {
		this.car_Cost = car_Cost;
	}

	public String getCar_Type() {
		return car_Type;
	}

	public Date getApproach_Time() {
		return approach_Time;
	}

	public Date getLeaving_Time() {
		return leaving_Time;
	}

	public String getCar_Number() {
		return car_Number;
	}

	public String getCar_Place() {
		return car_Place;
	}

	public int getCar_Cost() {
		return car_Cost;
	}
}
