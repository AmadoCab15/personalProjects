public class NBodyExtreme {

	public static double readRadius(String file) {
		In reader = new In(file);
		int nPlanets = reader.readInt();
		double radiusUniverse = reader.readDouble();
		return radiusUniverse;
	}

	public static BodyExtreme[] readBodies(String file) {
		In reader = new In(file);
		int nPlanets = reader.readInt();
		double radiusUniverse = reader.readDouble();
		BodyExtreme[] arrayBodies = new BodyExtreme[nPlanets];
		for(int i = 0; i < nPlanets; i++) {
			BodyExtreme nBody = new BodyExtreme(reader.readDouble(),reader.readDouble(),reader.readDouble(),reader.readDouble(),reader.readDouble(),reader.readString());
			arrayBodies[i] = nBody;
		}
		return arrayBodies;
	}

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radiusUniverse = readRadius(filename);
		BodyExtreme[] bodies = readBodies(filename);
		StdDraw.setScale(-radiusUniverse, radiusUniverse);
		StdDraw.picture(dt, T, "images/starfield.jpg");
		StdDraw.enableDoubleBuffering();
		int time = 0;
		while (time <= T) {	
			double[] xForces = new double[bodies.length];
			double[] yForces = new double[bodies.length];
			int j = 0;
			double xCursor = StdDraw.mouseX();
        	double yCursor = StdDraw.mouseY();
			StdDraw.picture(xCursor, yCursor, "images/star_destroyer.gif");
        	StdDraw.show();
        	StdDraw.pause(10);
			for(int i = 0; i < bodies.length; i++) {
				if (StdDraw.isMousePressed()) {
					while (StdDraw.isMousePressed()) {
						StdDraw.picture(0, radiusUniverse - (radiusUniverse /6), "images/warning.png", 20e+10, 3e+10);
						StdDraw.picture(radiusUniverse - (radiusUniverse /4), radiusUniverse - (radiusUniverse /4), "images/face1.png", 10e+10, 10e+10);
						System.out.println("HELP! A black hole is about to make the planets disappear!");
						StdDraw.show();
						StdDraw.pause(50);
						for(int r = 0; r < bodies.length; r++) {
							bodies[r].pull(xCursor, yCursor);
							bodies[r].draw();
						}
						StdDraw.picture(0, radiusUniverse - (radiusUniverse /6), "images/warning.png", 20e+10, 3e+10);
						StdDraw.picture(radiusUniverse - (radiusUniverse /4), radiusUniverse - (radiusUniverse /4), "images/face2.png", 10e+10, 10e+10);
						StdDraw.show();
						StdDraw.pause(50);
					}
				}
				else {
					if (StdDraw.isKeyPressed('W')) {
						StdDraw.picture(0, radiusUniverse - (radiusUniverse /6), "images/SpeedUP.png", 20e+10, 3e+10);
						System.out.println("Speed was increased!");
						for(int s = 0; s <bodies.length; s++) {
							bodies[s].speedUp();
						}
						StdDraw.show();
						StdDraw.pause(500);
					}
					else if(StdDraw.isKeyPressed('S')) {
						StdDraw.picture(0, radiusUniverse - (radiusUniverse /6), "images/SpeedDOWN.png", 20e+10, 3e+10);
						System.out.println("Speed was decreased!");
						for(int l = 0; l <bodies.length; l++) {
							bodies[l].speedDown();
						}
						StdDraw.show();
						StdDraw.pause(500);
					}
					if (bodies[i].hitWall) {
						bodies[i].checkBorder(radiusUniverse);
						bodies[i].move();
						for(int k = 0; k < bodies.length; k++) {
							if (i == k) {
								continue;
							}
							if (bodies[i].closeEnough(bodies[k]) || bodies[i].closeToShip(xCursor, yCursor)) {
								if (bodies[i].closeToShip(xCursor, yCursor)) {
									StdDraw.picture(xCursor, yCursor, "images/collision.png", 5e+10, 5e+10);
									StdDraw.show();
									StdDraw.pause(10);
									bodies[i].bounceAway();
									bodies[i].move();
									bodies[i].checkBorder(radiusUniverse);
								}
								else {
									StdDraw.picture(bodies[i].xxPos, bodies[k].yyPos, "images/collision.png", 5e+10, 5e+10);
									StdDraw.show();
									StdDraw.pause(10);
									bodies[i].collide(bodies[k]);
									bodies[i].move();
									bodies[k].move();
									bodies[i].checkBorder(radiusUniverse);
									bodies[k].checkBorder(radiusUniverse);
								}
							}
						}
					}
					else {
						while (j < bodies.length) {
							xForces[j] = bodies[j].calcNetForceExertedByX(bodies);
							yForces[j] = bodies[j].calcNetForceExertedByY(bodies);
							j++;
						}
						bodies[i].update(time, xForces[i], yForces[i]);
						StdDraw.show();
						StdDraw.pause(10);
						bodies[i].outOfRange(radiusUniverse, xCursor, yCursor);
						if (bodies[i].closeToShip(xCursor, yCursor)) {
							StdDraw.picture(bodies[i].xxPos, bodies[i].yyPos, "images/collision.png", 5e+10, 5e+10);
							StdDraw.show();
							StdDraw.pause(10);
						}
						for(int k = 0; k < bodies.length; k++) {
							if (i == k) {
								continue;
							}
							if (bodies[i].closeEnough(bodies[k])) {
								StdDraw.picture(bodies[i].xxPos, bodies[k].yyPos, "images/collision.png", 5e+10, 5e+10);
								StdDraw.show();
								StdDraw.pause(10);
								bodies[i].collide(bodies[k]);
								bodies[i].move();
								bodies[k].move();
							}
						}
					}
				}
			}
			StdDraw.picture(dt, T, "images/starfield.jpg");
			for (BodyExtreme body: bodies) {
				body.draw();
				xCursor = StdDraw.mouseX();
        		yCursor = StdDraw.mouseY();
				StdDraw.picture(xCursor, yCursor, "images/star_destroyer.gif");
			}
			StdDraw.show();
			StdDraw.pause(60);
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