package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min;

		min = da[0];
		
		for(double d : da) {
			if ( d < min) {
				min = d;
			}
		}
		
		return min;
	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double []getlat = new double[gpspoints.length];
		
		for(int i=0; i<gpspoints.length; i++) {
			getlat[i] = gpspoints[i].getLatitude();
		}
		return getlat;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double []getlong = new double[gpspoints.length];
		
		for(int i=0; i<gpspoints.length; i++) {
			getlong[i] = gpspoints[i].getLongitude();
		}
		return getlong;

	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d; // avstand i meter mellom gps punkt 1 og 2
		double lat1, long1, lat2, long2;
		
		lat1 = toRadians(gpspoint1.getLatitude());
		lat2 = toRadians(gpspoint2.getLatitude());
		long1 = toRadians(gpspoint1.getLongitude());
		long2 = toRadians(gpspoint2.getLongitude());
		
		double deltaLat = lat2 - lat1;
		double deltaLong = long2 - long1;
		
		double a = pow(sin(deltaLat/2),2) + cos(lat1)*cos(lat2)*pow(sin(deltaLong/2),2);
		double c = 2*atan2(sqrt(a),sqrt(1-a));
		d = R*c;
		
		return d;
		
	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;

		int deltaSec = gpspoint2.getTime() - gpspoint1.getTime();
		
		speed = distance(gpspoint1,gpspoint2) / deltaSec;
		
		return speed*3.6;

	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";
		String formatert;
		
		int hr = (secs)/3600;
		int min = (secs%3600)/60;
		int sec = (secs%3600)%60;
		
		String h = hr + "";
		String m = min + "";
		String s = sec + "";
		
		
		while (h.length() < 2) h = "0" + h;
		while (m.length() < 2) m = "0" + m;
		while (s.length() < 2) s = "0" + s;
		
		formatert = String.format("  "+h+":"+m+":"+s);

		return formatert;

	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str;
		
		str = String.format("      %.2f",d);
		System.out.println(str.length());
		
		return str;
		
		
		
	}
}
