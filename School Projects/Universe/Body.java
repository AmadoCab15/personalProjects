public class Body {
	public String imgFileName;
	public double mass;
	public double xxPos;
	public double xxVel;
	public double yyPos;
	public double yyVel;

	/** Constructor with passed parameters*/
	public Body(double xP, double yP, double xV, double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	/** Constructor that copies a passed Body b*/
	public Body(Body b) {
	this.xxPos = b.xxPos;
	this.yyPos = b.yyPos;
	this.xxVel = b.xxVel;
	this.yyVel = b.yyVel;
	this.mass = b.mass;
	this.imgFileName = b.imgFileName;
	}

	/** Calculates distance between two bodies*/
	public double calcDistance(Body otherBody) {
		double dx = this.xxPos - otherBody.xxPos;
		double dy = this.yyPos - otherBody.yyPos;
		double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		return distance;
	}

	/** Calculates the force exerted by two bodies 
	* and handles the scenario when two passed bodies are the same*/
	public double calcForceExertedBy(Body otherBody) {
		if (this.equals(otherBody)) {
			return 0;
		}
		else {
			double numerator = (6.67 * Math.pow(10,-11)) * this.mass * otherBody.mass;
			double denominator = Math.pow(this.calcDistance(otherBody),2);
			return numerator / denominator;
		}
	}

	/** Calculates the x-force betweem by two bodies*/
	public double calcForceExertedByX(Body otherBody) {
		if (this.calcDistance(otherBody) == 0) {
			return 0;
		}
		double dx = otherBody.xxPos - this.xxPos;
		double forceX = ((this.calcForceExertedBy(otherBody) * dx) / this.calcDistance(otherBody));
		return forceX;
	}

	/** Calculates the y-force between by two bodies*/
	public double calcForceExertedByY(Body otherBody) {
		if (this.calcDistance(otherBody) == 0) {
			return 0;
		}
		double dy = otherBody.yyPos - this.yyPos;
		double forceY = ((this.calcForceExertedBy(otherBody) * dy) / this.calcDistance(otherBody));
		return forceY;
	}

	/** Calculates the x-force exerted by multiple bodies
	* with respect a specific body*/
	public double calcNetForceExertedByX(Body[] bodies) {
		double totalForceX = 0;
		for (Body body: bodies) {
			double forceX = this.calcForceExertedByX(body);
			totalForceX += forceX;
		}
		return totalForceX;
	}
	/** Calculates the y-force exerted by multiple bodies
	* with respect a specific body*/
	public double calcNetForceExertedByY(Body[] bodies) {
		double totalForceY = 0;
		for (Body body: bodies) {
			double forceY = this.calcForceExertedByY(body);
			totalForceY += forceY;
		}
		return totalForceY;
	}

	/** Updates the position and the velocity of a body according to 
	* a new passed acceleration*/
	public void update(double time, double xForce, double yForce) {
		double accelarationX = xForce / this.mass;
		double accelarationY = yForce / this.mass;
		this.xxVel = this.xxVel + time * accelarationX;
		this.yyVel = this.yyVel + time * accelarationY;
		this.xxPos = this.xxPos + time * this.xxVel;
		this.yyPos = this.yyPos + time * this.yyVel;
	}

	public void draw() {
		StdDraw.picture(this.xxPos, this.yyPos, "images/"+this.imgFileName);
	}






}