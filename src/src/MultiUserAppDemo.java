//package edu.mtholyoke.cs.comsc243.kinectUDP;

import java.io.IOException;

import processing.core.PApplet;
//import processing.core.PShape;
import processing.core.PVector;

/**
 * @author eitan
 *
 */
public class MultiUserAppDemo extends PApplet 
{

	
	Long firstPersonId = null;
	Long secondPersonId = null;
	
	KinectBodyDataProvider kinectReader;
	PersonTracker tracker = new PersonTracker();
	
	public static float PROJECTOR_RATIO = 1080f/1920.0f;

	
	/**
	 * 
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
		
//		
//		try 
//		{
//			kinectReader = new KinectBodyDataProvider("test.kinect", 10);
//		} 
//		catch (IOException e) 
//		{
//			System.out.println("Unable to create kinect producer");
//		}
//		 
//		
		kinectReader = new KinectBodyDataProvider(8008);

		
		/*try 
		{
			kinectReader = new KinectBodyDataProvider("test.kinect", 10);
		} 
		catch (IOException e) 
		{
			System.out.println("Unable to create kinect producer");
		}*/
		 
		
		kinectReader = new KinectBodyDataProvider(8008);
		kinectReader.start();

	}
	
	/**
	 * 
	 */
	public void draw(){
		setScale(.5f);
		
		noStroke();

//		background(200,200,200);
//		background(0);

//		fill(255,0,0);
		fill(0, 0, 0, 1);
		rect(-2,-2,4,4);

		KinectBodyData bodyData = kinectReader.getMostRecentData();
		tracker.update(bodyData);
		
		if(! tracker.getPeople().isEmpty()) {
			if(firstPersonId == null) {
				for(Long id : tracker.getIds()) 
				{
					if(id != secondPersonId) {
					firstPersonId = id;
					}
				}
				
			}
		if(secondPersonId == null) {
			for(Long id : tracker.getIds()) 
			{
				if(id != firstPersonId) {
					secondPersonId = id;
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
			
//		Body person1 = null;
//		Body person2 = null;
		
		if(tracker.getPeople().containsKey(firstPersonId) && 
				tracker.getPeople().containsKey(secondPersonId)) 
		{
			 person1 = tracker.getPeople().get(firstPersonId);
			 person2 = tracker.getPeople().get(secondPersonId);
		} 
		else 
		{
			firstPersonId = null;
			secondPersonId = null;
		}
		
		
	
		
		if(person1 != null && person2 != null )
		{
			PVector head1 = person1.getJoint(Body.HEAD);
			PVector spine1 = person1.getJoint(Body.SPINE_SHOULDER);
			PVector spineBase1 = person1.getJoint(Body.SPINE_BASE);
			PVector shoulderLeft1 = person1.getJoint(Body.SHOULDER_LEFT);
			PVector shoulderRight1 = person1.getJoint(Body.SHOULDER_RIGHT);
			PVector footLeft1 = person1.getJoint(Body.FOOT_LEFT);
			PVector footRight1 = person1.getJoint(Body.FOOT_RIGHT);
			PVector handLeft1 = person1.getJoint(Body.HAND_LEFT);
			PVector handRight1 = person1.getJoint(Body.HAND_RIGHT);


			fill(255,255,255);
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


			
			drawShape(handLeft1, handRight1, footLeft1, footRight1);
			
			// person 2 joints
			
			PVector head2 = person2.getJoint(Body.HEAD);
			PVector spine2 = person2.getJoint(Body.SPINE_SHOULDER);
			PVector spineBase2 = person2.getJoint(Body.SPINE_BASE);
			PVector shoulderLeft2 = person2.getJoint(Body.SHOULDER_LEFT);
			PVector shoulderRight2 = person2.getJoint(Body.SHOULDER_RIGHT);
			PVector footLeft2 = person2.getJoint(Body.FOOT_LEFT);
			PVector footRight2 = person2.getJoint(Body.FOOT_RIGHT);
			PVector handLeft2 = person2.getJoint(Body.HAND_LEFT);
			PVector handRight2 = person2.getJoint(Body.HAND_RIGHT);


			fill(255,255,255);//yellow
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


			
			drawShape(handLeft2, handRight2, footLeft2, footRight2);

		}
		
		if(person1 != null)
		{
			System.out.println("Second Person Exists");
			PVector head1 = person1.getJoint(Body.HEAD);
			PVector spine1 = person1.getJoint(Body.SPINE_SHOULDER);
			PVector spineBase1 = person1.getJoint(Body.SPINE_BASE);
			PVector shoulderLeft1 = person1.getJoint(Body.SHOULDER_LEFT);
			PVector shoulderRight1 = person1.getJoint(Body.SHOULDER_RIGHT);
			PVector footLeft1 = person1.getJoint(Body.FOOT_LEFT);
			PVector footRight1 = person1.getJoint(Body.FOOT_RIGHT);
			PVector handLeft1 = person1.getJoint(Body.HAND_LEFT);
			PVector handRight1 = person1.getJoint(Body.HAND_RIGHT);


			fill(255,195,13);//yellow 
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

//			if( 
//					(footRight != null) &&
//					(footLeft != null) &&
//				//	(shoulderLeft != null) &&
//				//	(shoulderRight != null) 
//					(handLeft != null) &&
//					(handRight != null) 
//					) 
//			{
//				stroke(255,0,0, 100);
//				noFill();
//				strokeWeight(.05f); // because of scale weight needs to be much thinner
//				curve(
//						footLeft.x, footLeft.y, 
//						handLeft.x, handLeft.y, 
//						handRight.x, handRight.y,
//						footRight.x, footRight.y
//						);
//
//			}
			
			drawShape(handLeft1, handRight1, footLeft1, footRight1);

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

	}


	/**
	 * 
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 */
	public void drawShape(PVector p1, PVector p2, 
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
		
		
	}
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) 
	{
		PApplet.main(MultiUserAppDemo.class.getName());
	}

}