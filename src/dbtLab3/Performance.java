package dbtLab3;

public class Performance {
	private String theater;
	private String movie;
	private String date;
	private int seats;



	public  Performance(String movie, String date){
		this.movie = movie;
		this.date = date;
	}
	
	public void setSeats(int seats){
		this.seats = seats;
	}
	
	
	public int getSeats(){
		return seats;
	}
	
	

	public void setTheater(String theater){
		this.theater = theater;
	}

	public String getTheater() {
		return theater;
	}
}
