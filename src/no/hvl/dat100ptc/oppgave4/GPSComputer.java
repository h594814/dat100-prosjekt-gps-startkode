package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	// beregn total distances (i meter)
	public double totalDistance() {

		double distance = 0;

		for(int i=0; i < gpspoints.length-1; i++) {
			distance = distance + GPSUtils.distance(gpspoints[i],gpspoints[i+1]);
		}
		return distance;
	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;

		for(int i=0; i < gpspoints.length-1; i++) {
			
			double deltaElevation = gpspoints[i+1].getElevation() - gpspoints[i].getElevation();
			
			if(gpspoints[i].getElevation() < gpspoints[i+1].getElevation() && gpspoints[i].getElevation() != gpspoints[i+1].getElevation()) {
				elevation += deltaElevation;
				
			}
		}
		return elevation;
	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {
		
		int tid = gpspoints[gpspoints.length-1].getTime() - gpspoints[0].getTime();
		
		return tid;	
	}
		
	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {
		
		double [] avgSpeed = new double[gpspoints.length - 1];
		
		for(int i=0; i < gpspoints.length - 1; i++) {
			avgSpeed[i] = GPSUtils.speed(gpspoints[i], gpspoints[i+1]);
		}
		return avgSpeed;
	}
	
	public double maxSpeed() {
		
		double maxspeed = 0;
		double speeds[] = new double [gpspoints.length];
		
		for(int i=0; i < gpspoints.length - 1; i++) {
			speeds[i] = GPSUtils.speed(gpspoints[i], gpspoints[i+1]);
		}
		return GPSUtils.findMax(speeds);
	}

	public double averageSpeed() {

		double average = 0;
		
		average = totalDistance() / totalTime();
		
		return average*3.6;
	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling,
	 * general 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0
	 * bicycling, 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9
	 * mph, racing or leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph,
	 * racing/not drafting or >19 mph drafting, very fast, racing general 12.0
	 * bicycling, >20 mph, racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;		
		double speedmph = speed * MS ;
		double h = secs*3600;
		
		if(speedmph < 10) met = 4;
		if(speedmph >= 10 && speedmph < 12) met = 6;
		if(speedmph >= 12 && speedmph < 14) met = 8;
		if(speedmph >= 14 && speedmph < 16) met = 10;
		if(speedmph >= 16 && speedmph < 18) met = 12;
		if(speedmph > 20) met = 16;
		
		kcal = met/(weight*h);
		
	return kcal;
	}

	public double totalKcal(double weight) {

		double totalkcal = 0;
		
		double speed = averageSpeed();
		int secs = totalTime();
		
		totalkcal = kcal(weight,secs,speed);
		
		return totalkcal;
		
		
	}
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {

		System.out.println("==============================================");

		// TODO - START

		throw new UnsupportedOperationException(TODO.method());
		
		// TODO - SLUTT
		
	}

}
