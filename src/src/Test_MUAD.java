//package edu.mtholyoke.cs.comsc243.kinectUDP;

import java.io.IOException;
import processing.core.PApplet;
//import processing.core.PShape;
import processing.core.PVector;

/**
 * @author eitan
 *
 */
public class Test_MUAD extends PApplet 
{	
	Long firstPersonId = null;
	Long secondPersonId = null;
	Long thirdPersonId = null;
	
	KinectBodyDataProvider kinectReader;
	PersonTracker tracker = new PersonTracker();
	
	int time = second();//timer for draw methods
	
	public static float PROJECTOR_RATIO = 1080f/1920.0f;
	
	//variables to control effects
	int count = 0;
	int p1 = 0;int p2 = 0;int p3 = 0;
	int a1 = 0;	int a2 = 0; int a3 = 0; int a4 = 0;
	int b1 = 0; int b2 = 0; int b3 = 0; int b4 =0;
	int c1 = 0; int c2 = 0; int c3 = 0; int c4 = 0;

	/**
	 * @param useP2D
	 * @param isFullscreen
	 * @param windowsScale
	 */
	
	public void createWindow(boolean useP2D, boolean isFullscreen, float windowsScale) 
	{
		if (useP2D) 
		{
			if(isFullscreen) 
			{
				fullScreen(P2D);  			
			} 
			else 
			{
				size((int)(1920 * windowsScale), (int)(1080 * windowsScale), P2D);
			}
		} 
		else 
		{
			if(isFullscreen) 
			{
				fullScreen();  			
			} 
			else 
			{
				size((int)(1920 * windowsScale), (int)(1080 * windowsScale));
			}
		}		
	}
	
	// use lower numbers to zoom out (show more of the world)
	// zoom of 1 means that the window is 2 meters wide and approximately 1 meter tall.
	public void setScale(float zoom) 
	{
		scale(zoom* width/2.0f, zoom * -width/2.0f);
		translate(1f/zoom , -PROJECTOR_RATIO/zoom );		
	}

	/**
	 * 
	 */
	public void settings() 
	{
		createWindow(false, false, .25f);
	}

	/**
	 * 
	 */
	public void setup()
	{
		/*
		 * use this code to run your PApplet from data recorded by UPDRecorder 
		 */	
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
	
	/**
	 * 
	 */
	public void draw(){
		//setScale(1f);
		setScale(0.5f); //original scale
		noStroke();
		
		fill(0, 0, 0, 1); //transparent gray rectangle background
		//rect(0,0,25,25); //create rectangle
		rect(-2,-2,10,15);


		// *****************CREATE BODY*****************
		KinectBodyData bodyData = kinectReader.getMostRecentData();
		tracker.update(bodyData);
		
		if(! tracker.getPeople().isEmpty()) {
			if(firstPersonId == null) {
				for(Long id : tracker.getIds()) //first ID
				{
					if(id != secondPersonId) {
						firstPersonId = id;
					}
				}
			}
			if(secondPersonId == null) { 
				for(Long id : tracker.getIds())  //second ID
				{
					if(id != firstPersonId) {
						secondPersonId = id;
					}
				}
			}
			if (thirdPersonId == null) {
				for(Long id : tracker.getIds())  //second ID
				{
					if(id != firstPersonId && id != secondPersonId) {
						thirdPersonId = id;
					}
				}			
			}
		}
	
		Body person1 = null;
		if(firstPersonId != null) {
			person1 = tracker.getPeople().get(firstPersonId);
			if(person1 == null) {
				firstPersonId= null;
			}
		}
		Body person2 = null;
		if(secondPersonId != null) {
			person2 = tracker.getPeople().get(secondPersonId);
			if(person2 == null) {
				secondPersonId= null;
			}
		}
		Body person3 = null;
		if(thirdPersonId != null) {
			person3 = tracker.getPeople().get(thirdPersonId);
			if(person3 == null) {
				thirdPersonId= null;
			}
		}
			
		if(tracker.getPeople().containsKey(firstPersonId) || 
				tracker.getPeople().containsKey(secondPersonId) || 
				tracker.getPeople().containsKey(thirdPersonId)) 
		{
			 person1 = tracker.getPeople().get(firstPersonId);
			 person2 = tracker.getPeople().get(secondPersonId);
			 person3 = tracker.getPeople().get(thirdPersonId);
		} 
		else 
		{
			firstPersonId = null;
			secondPersonId = null;
			thirdPersonId = null;
		}
		
		//Check if 3 people are present
		if(person1 != null && person2 != null && person3 != null)
		{
			//testing -- jane
//			if (person1 != null) {
//				System.out.println("1st person present");
//			}
//			if (person2 != null) {
//				System.out.println("2nd person present");
//			}
//			if (person3 != null) {
//				System.out.println("3nd person present");
//			}
			
			//PERSON1
			PVector head1 = person1.getJoint(Body.HEAD);
			PVector spine1 = person1.getJoint(Body.SPINE_SHOULDER);
			PVector spineBase1 = person1.getJoint(Body.SPINE_BASE);
			PVector shoulderLeft1 = person1.getJoint(Body.SHOULDER_LEFT);
			PVector shoulderRight1 = person1.getJoint(Body.SHOULDER_RIGHT);
			PVector footLeft1 = person1.getJoint(Body.FOOT_LEFT);
			PVector footRight1 = person1.getJoint(Body.FOOT_RIGHT);
			PVector handLeft1 = person1.getJoint(Body.HAND_LEFT);
			PVector handRight1 = person1.getJoint(Body.HAND_RIGHT);
			fill (255,211,215);
			//tint(255, 126);
			noStroke();
			drawIfValid(head1);
			drawIfValid(spine1);
			drawIfValid(spineBase1);
			drawIfValid(shoulderLeft1);
			drawIfValid(shoulderRight1);
			drawIfValid(footLeft1);
			drawIfValid(footRight1);
			drawIfValid(handLeft1);
			drawIfValid(handRight1);
			//drawShape(handLeft1, handRight1, footLeft1, footRight1);
			
			//PERSON2 
			PVector head2 = person2.getJoint(Body.HEAD);
			PVector spine2 = person2.getJoint(Body.SPINE_SHOULDER);
			PVector spineBase2 = person2.getJoint(Body.SPINE_BASE);
			PVector shoulderLeft2 = person2.getJoint(Body.SHOULDER_LEFT);
			PVector shoulderRight2 = person2.getJoint(Body.SHOULDER_RIGHT);
			PVector footLeft2 = person2.getJoint(Body.FOOT_LEFT);
			PVector footRight2 = person2.getJoint(Body.FOOT_RIGHT);
			PVector handLeft2 = person2.getJoint(Body.HAND_LEFT);
			PVector handRight2 = person2.getJoint(Body.HAND_RIGHT);
			fill (255,134,145);
			//fill(b1,b2,b3); 
			tint(255, 128);
			noStroke();
			drawIfValid(head2);
			drawIfValid(spine2);
			drawIfValid(spineBase2);
			drawIfValid(shoulderLeft2);
			drawIfValid(shoulderRight2);
			drawIfValid(footLeft2);
			drawIfValid(footRight2);
			drawIfValid(handLeft2);
			drawIfValid(handRight2);
			//drawShape(handLeft2, handRight2, footLeft2, footRight2);
			
			//PERSON3
			PVector head3 = person3.getJoint(Body.HEAD);
			PVector spine3 = person3.getJoint(Body.SPINE_SHOULDER);
			PVector spineBase3 = person3.getJoint(Body.SPINE_BASE);
			PVector shoulderLeft3 = person3.getJoint(Body.SHOULDER_LEFT);
			PVector shoulderRight3 = person3.getJoint(Body.SHOULDER_RIGHT);
			PVector footLeft3 = person3.getJoint(Body.FOOT_LEFT);
			PVector footRight3 = person3.getJoint(Body.FOOT_RIGHT);
			PVector handLeft3 = person3.getJoint(Body.HAND_LEFT);
			PVector handRight3 = person3.getJoint(Body.HAND_RIGHT);
			//fill(c1,c2,c3, c4);
			fill(204, 107, 116);
			//fill(c1,c2,c3); 
			noStroke();
			drawIfValid(head3);
			drawIfValid(spine3);
			drawIfValid(spineBase3);
			drawIfValid(shoulderLeft3);
			drawIfValid(shoulderRight3);
			drawIfValid(footLeft3);
			drawIfValid(footRight3);
			drawIfValid(handLeft3);
			drawIfValid(handRight3);
			//drawShape(handLeft3, handRight3, footLeft3, footRight3);
			
			boolean case1 = false;
			boolean case2 = false;
			boolean case3 = false;
			
			// *****************EFFECTS*****************
			//Get flash of light for hand touching --explicit to right hand
			if (person1.getJoint(Body.HAND_RIGHT) == person2.getJoint(Body.HAND_RIGHT)) {
				case1 = true;
				count++;
			}
			if (person2.getJoint(Body.HAND_RIGHT) == person3.getJoint(Body.HAND_RIGHT)) {
//				fill(255,255,255); //CHANGE TO WHITE
//				rect(-2,-2,100,100);
				case2 = true;
				count++;
			}
			if (person1.getJoint(Body.HAND_RIGHT) == person3.getJoint(Body.HAND_RIGHT)) {
//				fill(255,255,255); //CHANGE TO WHITE
//				rect(-2,-2,100,100);
				case3= true;
				count++;
			}
			if (case1 == true && case2 == true && case3 == true) {
				fill(255,255,255); //CHANGE TO WHITE
				rect(-2,-2,100,100);
				count = count + 10;
			}
			
			
			
			
			//based on how many times this event happens, determine 
			if (count == 0) {
				//do nothing?
			}
			
			if (count > 1 && count < 20) {
				fill(190,19,11); 
				noStroke();
				drawIfValid(head1);
				drawIfValid(spine1);
				drawIfValid(spineBase1);
				drawIfValid(shoulderLeft1);
				drawIfValid(shoulderRight1);
				drawIfValid(footLeft1);
				drawIfValid(footRight1);
				drawIfValid(handLeft1);
				drawIfValid(handRight1);
				drawIfValid(head2);
				drawIfValid(spine2);
				drawIfValid(spineBase2);
				drawIfValid(shoulderLeft2);
				drawIfValid(shoulderRight2);
				drawIfValid(footLeft2);
				drawIfValid(footRight2);
				drawIfValid(handLeft2);
				drawIfValid(handRight2);
				drawIfValid(head3);
				drawIfValid(spine3);
				drawIfValid(spineBase3);
				drawIfValid(shoulderLeft3);
				drawIfValid(shoulderRight3);
				drawIfValid(footLeft3);
				drawIfValid(footRight3);
				drawIfValid(handLeft3);
				drawIfValid(handRight3);

			}
			
			if (count > 21 && count < 50 ) {
				fill(190,78,35); 
				noStroke();
				drawIfValid(head1);
				drawIfValid(spine1);
				drawIfValid(spineBase1);
				drawIfValid(shoulderLeft1);
				drawIfValid(shoulderRight1);
				drawIfValid(footLeft1);
				drawIfValid(footRight1);
				drawIfValid(handLeft1);
				drawIfValid(handRight1);
				drawIfValid(head2);
				drawIfValid(spine2);
				drawIfValid(spineBase2);
				drawIfValid(shoulderLeft2);
				drawIfValid(shoulderRight2);
				drawIfValid(footLeft2);
				drawIfValid(footRight2);
				drawIfValid(handLeft2);
				drawIfValid(handRight2);
				drawIfValid(head3);
				drawIfValid(spine3);
				drawIfValid(spineBase3);
				drawIfValid(shoulderLeft3);
				drawIfValid(shoulderRight3);
				drawIfValid(footLeft3);
				drawIfValid(footRight3);
				drawIfValid(handLeft3);
				drawIfValid(handRight3);
			}

			if (count > 51 && count < 80 ) {
				fill(190,175,23); 
				noStroke();
				drawIfValid(head1);
				drawIfValid(spine1);
				drawIfValid(spineBase1);
				drawIfValid(shoulderLeft1);
				drawIfValid(shoulderRight1);
				drawIfValid(footLeft1);
				drawIfValid(footRight1);
				drawIfValid(handLeft1);
				drawIfValid(handRight1);
				drawIfValid(head2);
				drawIfValid(spine2);
				drawIfValid(spineBase2);
				drawIfValid(shoulderLeft2);
				drawIfValid(shoulderRight2);
				drawIfValid(footLeft2);
				drawIfValid(footRight2);
				drawIfValid(handLeft2);
				drawIfValid(handRight2);
				drawIfValid(head3);
				drawIfValid(spine3);
				drawIfValid(spineBase3);
				drawIfValid(shoulderLeft3);
				drawIfValid(shoulderRight3);
				drawIfValid(footLeft3);
				drawIfValid(footRight3);
				drawIfValid(handLeft3);
				drawIfValid(handRight3);
			}

			if (count > 81 && count < 110 ) {
				fill(122,190,132); 
				noStroke();
				drawIfValid(head1);
				drawIfValid(spine1);
				drawIfValid(spineBase1);
				drawIfValid(shoulderLeft1);
				drawIfValid(shoulderRight1);
				drawIfValid(footLeft1);
				drawIfValid(footRight1);
				drawIfValid(handLeft1);
				drawIfValid(handRight1);
				drawIfValid(head2);
				drawIfValid(spine2);
				drawIfValid(spineBase2);
				drawIfValid(shoulderLeft2);
				drawIfValid(shoulderRight2);
				drawIfValid(footLeft2);
				drawIfValid(footRight2);
				drawIfValid(handLeft2);
				drawIfValid(handRight2);
				drawIfValid(head3);
				drawIfValid(spine3);
				drawIfValid(spineBase3);
				drawIfValid(shoulderLeft3);
				drawIfValid(shoulderRight3);
				drawIfValid(footLeft3);
				drawIfValid(footRight3);
				drawIfValid(handLeft3);
				drawIfValid(handRight3);
			}
			
			if (count > 111 && count < 140 ) {
				fill(77,190,170); 
				noStroke();
				drawIfValid(head1);
				drawIfValid(spine1);
				drawIfValid(spineBase1);
				drawIfValid(shoulderLeft1);
				drawIfValid(shoulderRight1);
				drawIfValid(footLeft1);
				drawIfValid(footRight1);
				drawIfValid(handLeft1);
				drawIfValid(handRight1);
				drawIfValid(head2);
				drawIfValid(spine2);
				drawIfValid(spineBase2);
				drawIfValid(shoulderLeft2);
				drawIfValid(shoulderRight2);
				drawIfValid(footLeft2);
				drawIfValid(footRight2);
				drawIfValid(handLeft2);
				drawIfValid(handRight2);
				drawIfValid(head3);
				drawIfValid(spine3);
				drawIfValid(spineBase3);
				drawIfValid(shoulderLeft3);
				drawIfValid(shoulderRight3);
				drawIfValid(footLeft3);
				drawIfValid(footRight3);
				drawIfValid(handLeft3);
				drawIfValid(handRight3);
			}
			
			if (count > 141 && count < 170 ) {
				fill(172,109,190); 
				noStroke();
				drawIfValid(head1);
				drawIfValid(spine1);
				drawIfValid(spineBase1);
				drawIfValid(shoulderLeft1);
				drawIfValid(shoulderRight1);
				drawIfValid(footLeft1);
				drawIfValid(footRight1);
				drawIfValid(handLeft1);
				drawIfValid(handRight1);
				drawIfValid(head2);
				drawIfValid(spine2);
				drawIfValid(spineBase2);
				drawIfValid(shoulderLeft2);
				drawIfValid(shoulderRight2);
				drawIfValid(footLeft2);
				drawIfValid(footRight2);
				drawIfValid(handLeft2);
				drawIfValid(handRight2);
				drawIfValid(head3);
				drawIfValid(spine3);
				drawIfValid(spineBase3);
				drawIfValid(shoulderLeft3);
				drawIfValid(shoulderRight3);
				drawIfValid(footLeft3);
				drawIfValid(footRight3);
				drawIfValid(handLeft3);
				drawIfValid(handRight3);
			}
			
			
			if (count > 171 && count < 200 ) {
				fill(119,94,190); 
				noStroke();
				drawIfValid(head1);
				drawIfValid(spine1);
				drawIfValid(spineBase1);
				drawIfValid(shoulderLeft1);
				drawIfValid(shoulderRight1);
				drawIfValid(footLeft1);
				drawIfValid(footRight1);
				drawIfValid(handLeft1);
				drawIfValid(handRight1);
				drawIfValid(head2);
				drawIfValid(spine2);
				drawIfValid(spineBase2);
				drawIfValid(shoulderLeft2);
				drawIfValid(shoulderRight2);
				drawIfValid(footLeft2);
				drawIfValid(footRight2);
				drawIfValid(handLeft2);
				drawIfValid(handRight2);
				drawIfValid(head3);
				drawIfValid(spine3);
				drawIfValid(spineBase3);
				drawIfValid(shoulderLeft3);
				drawIfValid(shoulderRight3);
				drawIfValid(footLeft3);
				drawIfValid(footRight3);
				drawIfValid(handLeft3);
				drawIfValid(handRight3);
			}
			
			if (count > 201 && count < 225) {
				fill(79,53,190); 
				noStroke();
				drawIfValid(head1);
				drawIfValid(spine1);
				drawIfValid(spineBase1);
				drawIfValid(shoulderLeft1);
				drawIfValid(shoulderRight1);
				drawIfValid(footLeft1);
				drawIfValid(footRight1);
				drawIfValid(handLeft1);
				drawIfValid(handRight1);
				drawIfValid(head2);
				drawIfValid(spine2);
				drawIfValid(spineBase2);
				drawIfValid(shoulderLeft2);
				drawIfValid(shoulderRight2);
				drawIfValid(footLeft2);
				drawIfValid(footRight2);
				drawIfValid(handLeft2);
				drawIfValid(handRight2);
				drawIfValid(head3);
				drawIfValid(spine3);
				drawIfValid(spineBase3);
				drawIfValid(shoulderLeft3);
				drawIfValid(shoulderRight3);
				drawIfValid(footLeft3);
				drawIfValid(footRight3);
				drawIfValid(handLeft3);
				drawIfValid(handRight3);
			}
			
			
			if (count == 225) {
				count = 0;
			}
			//count of how many right hand touches there are
			
		}
	}
	

	
	/**
	 * Draws an ellipse in the x,y position of the vector (it ignores z).
	 * Will do nothing is vec is null.  This is handy because get joint 
	 * will return null if the joint isn't tracked. 
	 * @param vec
	 */
	public void drawIfValid(PVector vec) 
	{
		if(vec != null) 
		{
			ellipse(vec.x, vec.y, .1f,.1f);
		}
		int passedMillis = millis() - time; // calculates passed milliseconds
		if(passedMillis >= 215){
			time = millis();
		    fill(255,0,0);  // if more than 215 milliseconds passed set fill color to red
		}
	}


	/**
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 */
/*	public void drawShape(PVector p1, PVector p2, 
			PVector p3, PVector p4)
	{
		if((p1!= null) && (p2!=null) && (p3 != null) && (p4 !=null)) 
		{
//			ellipse(vec.x, vec.y, .1f,.1f);
			beginShape();
			fill(153,0,76, 50);
			noStroke();
//			background(0);
			vertex(p1.x,p1.y);
			vertex(p2.x,p2.y);
			vertex(p3.x, p3.y);
			vertex(p4.x,p4.y);
			endShape(CLOSE);
		}
	}*/
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) 
	{
		PApplet.main(Test_MUAD.class.getName());
	}

}