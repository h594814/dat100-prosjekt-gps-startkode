package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);
		
		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon)); 

		return xstep;
	}


	// antall y-pixels per breddegrad
	public double ystep() {

		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		double ystep = MAPYSIZE / Math.abs(maxlat - minlat);

		return ystep;
	}

	public void showRouteMap(int ybase) {
		// finn xstep og ystep for å seinere regne ut antall piksler per endring
		double xstep = this.xstep();
		double ystep = this.ystep();
		System.out.println("xstep: " + xstep + " ystep: " + ystep);
		// finn minste verdi av latitude og longitude for å bruke de som nullpunkt
		double minlong = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		double minlat  = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		System.out.println("minlong: " + minlong + " minlat: " + minlat);
		// lag nye arrays for å ha alle latitudes og longitudes
		int      points = gpspoints.length;
		double[] longs  = new double[points];
		double[] lats   = new double[points];
		// trekk i fra minste latitude og logitude fra hver verdi og lagre i ny array
		for (int i = 0; i < points; i++) {
			longs[i] = gpspoints[i].getLongitude() - minlong;
		}
		for (int i = 0; i < points; i++) {
			lats[i] = gpspoints[i].getLatitude() - minlat;
		}
		// System.out.println("longs: " + Arrays.toString(longs) + "\nlats: " + Arrays.toString(lats));
		setColor(0, 255, 0);
		for (int i = 0; i < points; i++) {
			// regn ut x og y verdi i forhold til xstep og ystep
			int x = (int) (MARGIN + (longs[i] * xstep));
			int y = (int) (ybase - (lats[i] * ystep));
			if (i == points - 1) {
				// hvis det er siste punkt så skal vi pynte litt på punktet og ikke tegne linje
				setColor(0, 0, 255);
				fillCircle(x, y, 5);
			} else {
				// må regne ut lokasjon til neste punkt
				int nextX = (int) (MARGIN + (longs[i + 1] * xstep));
				int nextY = (int) (ybase - (lats[i + 1] * ystep));
				if (i == 0) {
					// lag en rød sirkel ved startpunkt
					setColor(255, 0, 0);
					fillCircle(x, y, 5);
					setColor(0, 255, 0);
					drawLine(x, y, nextX, nextY);
				} else {
					// alle andre punkt skal få en linje til neste punkt
					fillCircle(x, y, 3);
					drawLine(x, y, nextX, nextY);
				}
			}

		}
	}

	public void showStatistics() {

		final int TEXTDISTANCE = 20;

		setColor(0, 0, 0);
		// må bruke en monospace font for at tekst skal lines opp
		setFont("Consolas", 16);

		String stringTime = String.format("%-15s:", "Total Time") +
		                    GPSUtils.formatTime(gpscomputer.totalTime()) +
		                    "\n";
		String stringDistance = String.format("%-15s:", "Total distance") +
		                        GPSUtils.formatDouble(gpscomputer.totalDistance() / 1000) + " km\n";
		String stringElevation = String.format("%-15s:", "Total elevation") +
		                         GPSUtils.formatDouble(gpscomputer.totalElevation()) + " m\n";
		String stringMaxSpeed = String.format("%-15s:", "Max speed") +
		                        GPSUtils.formatDouble(gpscomputer.maxSpeed()) + " km/t\n";
		String stringAvgSpeed = String.format("%-15s:", "Average speed") +
		                        GPSUtils.formatDouble(gpscomputer.averageSpeed()) + " km/t\n";
		String stringEnergy = String.format("%-15s:", "Energy") +
		                      GPSUtils.formatDouble(gpscomputer.totalKcal(GPSComputer.getWEIGHT())) +
		                      " kcal\n";

		drawString(stringTime, MARGIN, MARGIN);
		drawString(stringDistance, MARGIN, MARGIN + TEXTDISTANCE);
		drawString(stringElevation, MARGIN, MARGIN + TEXTDISTANCE * 2);
		drawString(stringMaxSpeed, MARGIN, MARGIN + TEXTDISTANCE * 3);
		drawString(stringAvgSpeed, MARGIN, MARGIN + TEXTDISTANCE * 4);
		drawString(stringEnergy, MARGIN, MARGIN + TEXTDISTANCE * 5);

	}

}