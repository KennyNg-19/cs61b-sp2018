public class Planet {
    /** determine instance variables */
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    /**This is the first constructor, recieving data and assign to instance variables */
    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
                      this.xxPos = xP;
                      this.yyPos = yP;
                      this.xxVel = xV;
                      this.yyVel = yV;
                      this.mass = m;
                      this.imgFileName = img;
                  }
    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    /**define method to calculate distance between planets */
    public double calcDistance(Planet rocinante) {
        return Math.sqrt(Math.pow(this.xxPos - rocinante.xxPos, 2) + Math.pow(this.yyPos - rocinante.yyPos, 2));
    }

    /**Calculate force between planets */
    public double calcForceExertedBy(Planet p) {
        double distance = this.calcDistance(p);
        double G = 6.67e-11;
        double force = G * this.mass * p.mass / Math.pow(distance, 2);
        return force;
    }

    /** Compute force in different directions */
    public double calcForceExertedByX(Planet p) {
        double distance = this.calDistance(p);
        double force = calcForceExertedBy(p);
        double forceX = (p.xxPos - this.xxPos) / distance * force;
        return forceX;
    }

    public double calcForceExertedByY(Planet p) {
        double distance = this.calDistance(p);
        double force = calcForceExertedBy(p);
        double forceY = (p.yyPos - this.yyPos) / distance * force;
        return forceY;
    }

    /**Calculate net force by multiple planets */
    public double calcNetForceExertedByX(Planet[] allPlanets) {
        double netForceX = 0;
        for (int i = 0; i < allPlanets.length; i++) {
            if (this.equals(allPlanets[i])) {
                continue;
            }
            netForceX += this.calcForceExertedByX(allPlanets[i]);
        }
        return netForceX;
    }

    public double calcNetForceExertedByY(Planet[] allPlanets) {
        double netForceY = 0;
        // for (int i = 0; i < allPlanets.length; i++) {
        //     if (this.equals(allPlanets[i])) {
        //         continue;
        //     }
        for (Planet p : allPlanets) {
            if (this.equals(p)) {
                continue;
            }
            netForceY += this.calcForceExertedByY(p);
        }
        return netForceY;
    }

    /** Update the position and velocity info based on give time and force*/
    public void update(double dt, double fX, double fY) {
        double aX = fX / this.mass;
        double aY = fY / this.mass;
        this.xxVel += aX * dt;
        this.yyVel += aY * dt;
        this.xxPos += this.xxVel * dt;
        this.yyPos += this.yyVel * dt;
    }
    /** draw the picture on the background */
    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "./images/" + this.imgFileName);
        StdDraw.show();
    }


}