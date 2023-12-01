package com.acc.group4.FlyHigh;

import com.acc.group4.FlyHigh.Bo.FlyHighBO;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Group four lets take off...:)" );
        FlyHighBO bo = FlyHighBO.getInstance();
        bo.getFlightDetails();
        System.out.println("Landing done succesfully.....:)");
        System.exit(0);
    }
}
