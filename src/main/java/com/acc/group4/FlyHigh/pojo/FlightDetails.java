package com.acc.group4.FlyHigh.pojo;

public class FlightDetails {
	private String flightName;
	private String flightPrice;
	private String timeToReach;
	private String stops;
	public String getFlightName() {
		return flightName;
	}
	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}
	public String getFlightPrice() {
		return flightPrice;
	}
	public void setFlightPrice(String flightPrice) {
		this.flightPrice = flightPrice;
	}
	
	public String getTimeToReach() {
		return timeToReach;
	}
	public void setTimeToReach(String timeToReach) {
		this.timeToReach = timeToReach;
	}
	
	public String getStops() {
		return stops;
	}
	public void setStops(String stops) {
		this.stops = stops;
	}
	@Override
	public String toString() {
		return "FlightDetails [flightName=" + flightName + ", flightPrice=" + flightPrice + ", timeToReach="
				+ timeToReach + ", stops=" + stops + "]";
	}
	
	
	

}
