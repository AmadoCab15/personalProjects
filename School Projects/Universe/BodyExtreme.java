public class BodyExtreme {
	public String imgFileName;
	public double mass;
	public double xxPos;
	public double xxVel;
	public double yyPos;
	public double yyVel;
	public boolean hitWall;

	/** Constructor with passed parameters*/
	public BodyExtreme(double xP, double yP, double xV, double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
		hitWall = false;
	}

	/** Constructor that copies a passed Body b*/
	public BodyExtreme(BodyExtreme b) {
	this.xxPos = b.xxPos;
	this.yyPos = b.yyPos;
	this.xxVel = b.xxVel;
	this.yyVel = b.yyVel;
	this.mass = b.mass;
	this.imgFileName = b.imgFileName;
	this.hitWall = false;
	}

	/** Calculates distance between two bodies*/
	public double calcDistance(BodyExtreme otherBody) {
		double dx = this.xxPos - otherBody.xxPos;
		double dy = this.yyPos - otherBody.yyPos;
		double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		return distance;
	}

	/** Calculates the force exerted by two bodies 
	* and handles the scenario when two passed bodies are the same*/
	public double calcForceExertedBy(BodyExtreme otherBody) {
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
	public double calcForceExertedByX(BodyExtreme otherBody) {
		if (this.calcDistance(otherBody) == 0) {
			return 0;
		}
		double dx = otherBody.xxPos - this.xxPos;
		double forceX = ((this.calcForceExertedBy(otherBody) * dx) / this.calcDistance(otherBody));
		return forceX;
	}

	/** Calculates the y-force between by two bodies*/
	public double calcForceExertedByY(BodyExtreme otherBody) {
		if (this.calcDistance(otherBody) == 0) {
			return 0;
		}
		double dy = otherBody.yyPos - this.yyPos;
		double forceY = ((this.calcForceExertedBy(otherBody) * dy) / this.calcDistance(otherBody));
		return forceY;
	}

	/** Calculates the x-force exerted by multiple bodies
	* with respect a specific body*/
	public double calcNetForceExertedByX(BodyExtreme[] bodies) {
		double totalForceX = 0;
		for (BodyExtreme body: bodies) {
			double forceX = this.calcForceExertedByX(body);
			totalForceX += forceX;
		}
		return totalForceX;
	}
	/** Calculates the y-force exerted by multiple bodies
	* with respect a specific body*/
	public double calcNetForceExertedByY(BodyExtreme[] bodies) {
		double totalForceY = 0;
		for (BodyExtreme body: bodies) {
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

	/**Determines whether two bodies are about to collide*/
	public boolean closeEnough(BodyExtreme otherBody) {
		//xPos: -8.937117028187914E9
		//yPos: 4.23912281546579E10
		if (this.calcDistance(otherBody) <= 1.5e+10) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean closeToShip(double xAxis, double yAxis) {
		double dx = this.xxPos - xAxis;
		double dy = this.yyPos - yAxis;
		double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		if (distance <= 2.0e+10) {
			return true;
		}
		return false;
	}

	public void bounceAway() {
		this.xxVel = -this.xxVel;
		this.yyVel = -this.yyVel;
	}

	public void outOfRange(double framePicture, double xCursor, double yCursor){
		if ((this.xxPos > framePicture)|| (this.xxPos < -framePicture) ||
			 (this.yyPos > framePicture ) || (this.yyPos < -framePicture) || (this.closeToShip(xCursor, yCursor)))  {
			this.hitWall = true;
			this.xxVel = this.xxPos / 10;
			this.yyVel = this.yyPos / 10;
		}
	}

	public void checkBorder(double limit) {
        if (Math.abs(this.xxPos + this.xxVel) > limit) this.xxVel = -this.xxVel;
        if (Math.abs(this.yyPos + this.yyVel) > limit) this.yyVel = -this.xxVel;
	}
	public void move() {
		this.xxPos = this.xxPos + this.xxVel;
		this.yyPos = this.yyPos + this.yyVel;
	}

	public void collide(BodyExtreme otherBody) {
		this.xxVel = -this.xxVel;
		this.yyVel = -this.yyVel;
		otherBody.xxVel = -otherBody.xxVel;
		otherBody.yyVel = -otherBody.yyVel;

	}

	public void pull(double xCursor, double yCursor) {
		double dx = this.xxPos - xCursor;
		double dy = this.yyPos - yCursor; 
		double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		if (xCursor > this.xxPos) {
			this.xxPos += (distance / 10);
		}
		if (xCursor < this.xxPos) {
			this.xxPos -= (distance / 10);
		}
		if (yCursor > this.yyPos) {
			this.yyPos += (distance / 10);
		}
		if (yCursor < this.yyPos) {
			this.yyPos -= (distance / 10);	
		}
	}

	public void speedUp() {
		this.xxVel += (this.xxVel /8);
		this.yyVel += (this.yyVel /8);
	}
	public void speedDown() {
		this.xxVel -= (this.xxVel /8);
		this.yyVel -= (this.yyVel /8);
	}
}