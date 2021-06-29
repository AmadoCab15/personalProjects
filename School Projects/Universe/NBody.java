public class NBody {

	/**Given a file with information about bodies and the universe, return the radius of the universe*/
	public static double readRadius(String file) {
		In reader = new In(file);
		int nPlanets = reader.readInt();
		double radiusUniverse = reader.readDouble();
		return radiusUniverse;
	}

	/**Given a file with information about bodies and the universe, create and array of bodies and return it*/
	public static Body[] readBodies(String file) {
		In reader = new In(file);
		int nPlanets = reader.readInt();
		double radiusUniverse = reader.readDouble();
		Body[] arrayBodies = new Body[nPlanets];
		for(int i = 0; i < nPlanets; i++) {
			Body nBody = new Body(reader.readDouble(),reader.readDouble(),reader.readDouble(),reader.readDouble(),reader.readDouble(),reader.readString());
			arrayBodies[i] = nBody;
		}
		return arrayBodies;
	}

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radiusUniverse = readRadius(filename);
		Body[] bodies = readBodies(filename);
		StdDraw.setScale(-radiusUniverse, radiusUniverse);
		StdDraw.picture(dt, T, "images/starfield.jpg");
		for (Body body: bodies) {
				body.draw();
			}
		StdDraw.enableDoubleBuffering();
		int time = 0;
		while (time <= T) {	
			double[] xForces = new double[bodies.length];
			double[] yForces = new double[bodies.length];
			for(int i = 0; i < bodies.length; i++){
				xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
				yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
			}
			for(int j = 0; j < bodies.length; j++) {
				bodies[j].update(time, xForces[j], yForces[j]);
			}
			StdDraw.picture(dt, T, "images/starfield.jpg");
			for (Body body: bodies) {
				body.draw();
			}
			StdDraw.show();
			StdDraw.pause(100);
			time += dt;
		}
		StdOut.printf("%d\n", bodies.length);
		StdOut.printf("%.2e\n", radiusUniverse);
		for (int i = 0; i < bodies.length; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
		}
	}

}