// import com.sun.prism.paint.Stop;

// public class NBody {

//     /** Define main function*/
//     public static void main(String[] args) {
//         double T = Double.parseDouble(args[0]);
//         double dt = Double.parseDouble(args[1]);
//         String filename = args[2];
//         double radius = readRadius(filename);
//         Planet[] Planets = readPlanets(filename);
        
//         String imgPath = "./images/starfield.jpg";
//         StdDraw.enableDoubleBuffering();
//         StdDraw.setScale(-radius, radius);
//         StdDraw.clear();
//         StdDraw.picture(0, 0, imgPath);
//         StdDraw.show();

//         for (Planet p : Planets) {
//             p.draw();
//         }

    
//         for (double time = 0; time <= T; time += dt) {
//             double[] xForces = new double[Planets.length];
//             double[] yForces = new double[Planets.length];
//             for (int i = 0; i < Planets.length; i++) {
//                 xForces[i] = Planets[i].calcNetForceExertedByX(Planets);
//                 yForces[i] = Planets[i].calcNetForceExertedByY(Planets);
//             }
//             for (int i = 0; i < Planets.length; i++) {
//                 Planets[i].update(dt, xForces[i], yForces[i]);
//             }
//             // StdDraw.clear();
//             StdDraw.picture(0, 0, imgPath);
//             for (int i = 0; i < Planets.length; i++) {
//                 Planets[i].draw();
//             }
//             StdDraw.show();
//             int pauseTime = 10;
//             StdDraw.pause(pauseTime);
//         }
//         double[] xForces = new double[Planets.length];

//     }

//     /** Read radius information from given file */

//     public static double readRadius(String filePath) {
//         In in = new In(filePath);
//         int number = in.readInt();
//         double radius = in.readDouble();
//         return radius;
    
//     }

//     /** Read planets info based on given file */
//     public static Planet[] readPlanets(String filePath) {
//         In in = new In(filePath);
//         int number = in.readInt();
//         double radius = in.readDouble();
//         /* build planets based on given info */
//         Planet[] Planets = new Planet[number];
//         for (int i = 0; i < number; i++) {
//             double xPos = in.readDouble();
//             double yPos = in.readDouble();
//             double xVel = in.readDouble();
//             double yVel = in.readDouble();
//             double mass = in.readDouble();
//             String img = in.readString();

//             Planet p = new Planet(xPos, yPos, xVel, yVel, mass, img);
//             Planets[i] = p;
//         }
//         return Planets;
//     }



// }
/*
 * This is a class for NBody simulator
 */

public class NBody {

    static String imageRoot = "./images/";
    static String background = "starfield.jpg";

    public static void main (String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        // planet array
        Planet[] pArray = readPlanets(filename);

        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        drawBackgroundPlanet(pArray);

        // animation loop
        double t = 0;
        double[] xForces = new double[pArray.length];
        double[] yForces = new double[pArray.length];

        while (t < T) {
            // calculate xForces and yForces
            for (int i = 0; i < pArray.length; i++) {
                xForces[i] = pArray[i].calcNetForceExertedByX(pArray);
                yForces[i] = pArray[i].calcNetForceExertedByY(pArray);
            }
            // update planet's position
            for (int i = 0; i < pArray.length; i++) {
                pArray[i].update(dt, xForces[i], yForces[i]);
            }
            drawBackgroundPlanet(pArray);
            StdDraw.show(10);
            t += dt;
        }

        StdOut.printf("%d\n", pArray.length);
        StdOut.printf("%.2e\n", radius);
        for (Planet aPArray : pArray) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    aPArray.xxPos, aPArray.yyPos, aPArray.xxVel, aPArray.yyVel, aPArray.mass, aPArray.imgFileName);
        }
    }

    public static void drawBackgroundPlanet(Planet[] pArray) {
        StdDraw.picture(0, 0, imageRoot + background);
        for (Planet p: pArray) {
            p.draw();
        }
    }

    public static double readRadius (String filename) {
        In in = new In(filename);
        int numPlanets = in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets (String filename) {
        In in = new In(filename);
        int numPlanets = in.readInt();
        double radius = in.readDouble();
        Planet[] pArray = new Planet[numPlanets];

        for (int i = 0; i < numPlanets; i++) {
            pArray[i] = new Planet(0.0, 0.0, 0.0, 0.0, 0.0, "");
            pArray[i].xxPos = in.readDouble();
            pArray[i].yyPos = in.readDouble();
            pArray[i].xxVel = in.readDouble();
            pArray[i].yyVel = in.readDouble();
            pArray[i].mass = in.readDouble();
            pArray[i].imgFileName = imageRoot + in.readString();
        }
        return pArray;
    }
}