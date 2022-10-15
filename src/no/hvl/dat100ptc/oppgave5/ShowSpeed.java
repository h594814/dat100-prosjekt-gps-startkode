package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowSpeed extends EasyGraphics {
			
	private static final int MARGIN = 50;
	private static final int BARHEIGHT = 200; // assume no speed above 200 km/t

	private GPSComputer gpscomputer;
	private GPSPoint[] gpspoints;
	
	public ShowSpeed() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();
		
	}
	
	// read in the files and draw into using EasyGraphics
	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		int N = gpspoints.length-1; // number of data points
		
		makeWindow("Speed profile", 2*MARGIN + 2 * N, 2 * MARGIN + BARHEIGHT);
		
		showSpeedProfile(MARGIN + BARHEIGHT,N);
	}
	
	public void showSpeedProfile(int ybase, int N) {

		// get segments speeds from the GPS computer object		
		double[] speeds = gpscomputer.speeds();

		double distance = 0;
		for(int i=0; i < gpspoints.length-1; i++) {
			distance = distance + GPSUtils.distance(gpspoints[i],gpspoints[i+1]);
		}
		int tid = gpspoints[gpspoints.length-1].getTime() - gpspoints[0].getTime();
		
		double averageSpeed = (int) (distance / tid)*3.6;
		int value = (int) averageSpeed;
		setColor(0,225,0);
		
		int x1 = MARGIN;
		int x2 =  2*MARGIN + 2 * N - MARGIN;
		int y1 = ybase - value;
		drawLine(x1,y1,x2,y1);

		for (int i = 0; i<gpspoints.length-1; i++) {
			setColor(0,0,225);
			int x = MARGIN + i * 2;
			int y = (int) GPSUtils.speed(gpspoints[i], gpspoints[i+1]);
			if (y < 0) {
				y = 0;
			}
			drawLine(x,ybase,x,ybase- y);
		}
			
	}
}
