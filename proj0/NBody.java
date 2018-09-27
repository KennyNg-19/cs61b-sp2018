import com.sun.prism.paint.Stop;

public class NBody {

    /** Define main function*/
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        System.out.println(T);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] Planets = readPlanets(filename);
        
        String imgPath = "./images/starfield.jpg";
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        StdDraw.picture(0, 0, imgPath);
        StdDraw.show();

        for (Planet p : Planets) {
            p.draw();
        }

    
        for (double time = 0; time <= T; time += dt) {
            double[] xForces = new double[Planets.length];
            double[] yForces = new double[Planets.length];
            for (int i = 0; i < Planets.length; i++) {
                xForces[i] = Planets[i].calcNetForceExertedByX(Planets);
                yForces[i] = Planets[i].calcNetForceExertedByY(Planets);
            }
            for (int i = 0; i < Planets.length; i++) {
                Planets[i].update(dt, xForces[i], yForces[i]);
            }
            // StdDraw.clear();
            StdDraw.picture(0, 0, imgPath);
            for (int i = 0; i < Planets.length; i++) {
                Planets[i].draw();
            }
            StdDraw.show();
            int pauseTime = 10;
            StdDraw.pause(pauseTime);
        }
        double[] xForces = new double[Planets.length];

    }

    /** Read radius information from given file */

    public static double readRadius(String filePath) {
        In in = new In(filePath);
        int number = in.readInt();
        double radius = in.readDouble();
        return radius;
    
    }

    /** Read planets info based on given file */
    public static Planet[] readPlanets(String filePath) {
        In in = new In(filePath);
        int number = in.readInt();
        double radius = in.readDouble();
        /* build planets based on given info */
        Planet[] Planets = new Planet[number];
        for (int i = 0; i < number; i++) {
            double xPos = in.readDouble();
            double yPos = in.readDouble();
            double xVel = in.readDouble();
            double yVel = in.readDouble();
            double mass = in.readDouble();
            String img = in.readString();

            Planet p = new Planet(xPos, yPos, xVel, yVel, mass, img);
            Planets[i] = p;
        }
        return Planets;
    }



}
