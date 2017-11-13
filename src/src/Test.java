/*
 * Test for Graphic Capabilities for Project 2
 * Things we want to try:
 * - timed draw (ellipse) delivery
 * - 
 * 
 */

import java.io.IOException;
import processing.core.PApplet;
//import processing.core.PShape;
import processing.core.PVector;

public class Test extends PApplet {
	
	KinectBodyDataProvider kinectReader;
	PersonTracker tracker = new PersonTracker();

	public static void main(String[] args) 
	{
		PApplet.main(Test.class.getName());
	}
	
	int time = second();
	 
	public void setup() {
//	  size(200, 200);
	  background(102);
	  noStroke();
		try 
		{
			kinectReader = new KinectBodyDataProvider("test.kinect", 10);
		} 
		catch (IOException e) 
		{
			System.out.println("Unable to create kinect producer");
		}
		 
		//kinectReader = new KinectBodyDataProvider(8008);
		kinectReader.start();
	}
	 
	public void draw() {
	  background(100);
	  fill(255); // sets fill color to white
	 
	  int passedMillis = millis() - time; // calculates passed milliseconds
	  if(passedMillis >= 215){
	      time = millis();
	      fill(255,0,0);  // if more than 215 milliseconds passed set fill color to red
	  }
	  ellipse(50,50,50,50); // draw first circle
	 
	  fill(255); // fill white
	  ellipse(150,150,50,50); // draw second circle
	 
	  fill(255,0,0);  // fill red
	  arc(50, 55, 50, 50, (float) (TWO_PI / 215.0 * passedMillis),PIE); // draw red pie over second circle
	}
}
