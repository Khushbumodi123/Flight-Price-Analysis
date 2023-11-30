package com.acc.group4.FlyHigh.UIRepresentation;

import java.awt.Button;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.acc.group4.FlyHigh.pojo.FlightDetails;

public class FlightDetailsTable {
       public static void showFlights(ArrayList<FlightDetails> flightDetails) {
    	   JFrame f;      
    	    f=new JFrame("FlyHigh");  
    	    String data[][]=new String[flightDetails.size()][4];
    	    int i=0;
    	    for (FlightDetails flightDet : flightDetails) {
				data[i][0]=flightDet.getFlightName();
				data[i][1]=flightDet.getFlightPrice();
				data[i][2]=flightDet.getTimeToReach();
				data[i][3]=flightDet.getStops();
				i++;
			}
    	    String column[]={"Flight Names","Price","Time","Stops"}; 
    	    JTable jt=new JTable(data,column); 
    	    jt.setBounds(30,40,200,300);
    	    //jt.setFont(new Font("Arial", Font.PLAIN, 24));
    	    JScrollPane sp=new JScrollPane(jt); 
    	    f.add(sp); 
    	    f.setSize(300,400);    
    	    f.setVisible(true);  
       }
}
