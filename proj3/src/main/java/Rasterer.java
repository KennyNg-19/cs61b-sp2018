import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    /* the longitude length per file*/
    private double lonPerFile;
    /* the latitude length per file*/
    private double latPerFile;
    /* the rows of files covering the query box */
    private int rowNum;
    /* the columns of files covering the query box */
    private int colNum;

    public Rasterer() {
        lonPerFile = 0.0;
        latPerFile = 0.0;
        rowNum = 0;
        colNum = 0;
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <lklki>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        double ullon = params.get("ullon");
        double ullat = params.get("ullat");
        double lrlon = params.get("lrlon");
        double lrlat = params.get("lrlat");
        double w = params.get("w");
        double h = params.get("h");
        Map<String, Object> results = new HashMap<>();
        if (lrlon <= MapServer.ROOT_ULLON || ullon >= MapServer.ROOT_LRLON ||
                lrlat >= MapServer.ROOT_ULLAT || ullat <= MapServer.ROOT_LRLAT) {
            setArbitrary(results);
        }
        if (ullon >= lrlon || ullat <= lrlat) {
            setArbitrary(results);
        }

        int depth = queryDepth(ullon, lrlon, w);
        int[] ulFileInfo = findULFile(ullon,ullat, depth);
        int[][][] filesInfo = generateFileArray(lrlon, lrlat, ulFileInfo);
        Map<String, Double> ulFile = fileNameToBoundingBox(filesInfo[0][0]);
        Map<String, Double> lrFile = fileNameToBoundingBox(filesInfo[rowNum - 1][colNum - 1]);
        String[][] fileNames = new String[rowNum][colNum];
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                fileNames[i][j] = arrayToString(filesInfo[i][j]);
            }
        }

//        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
//                           + "your browser.");

        results.put("raster_ul_lon", ulFile.get("ullon"));
        results.put("raster_ul_lat", ulFile.get("ullat"));
        results.put("raster_lr_lon", lrFile.get("lrlon"));
        results.put("raster_lr_lat", lrFile.get("lrlat"));
        results.put("depth", depth);
        results.put("query_success", true);
        results.put("render_grid", fileNames);
//        System.out.println(results);
//        for (int i = 0; i < rowNum; i++) {
//            for (int j = 0; j < colNum; j++) {
//                System.out.println(fileNames[i][j]);
//            }
//        }
        return results;
    }

    private int queryDepth(double ullon, double lrlon, double w) {
        double targetLonDPP = (lrlon - ullon) / w;
        double originlonDPP = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE;
        int depth = (int) Math.ceil(originlonDPP / (2 * targetLonDPP));
        if (depth > MapServer.MAX_DEPTH) {
            return MapServer.MAX_DEPTH;
        }
        return depth;
    }

    private Map<String, Double> fileNameToBoundingBox(int[] fileInfo) {

        int depth = fileInfo[0];
        int xi = fileInfo[2];
        int yi = fileInfo[1];
        double parts = Math.pow(2, depth);
        double lonPerFile = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / parts;
        double latPerFile = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / parts;

        Map<String, Double> results = new HashMap<>();
        results.put("ullon", xi * lonPerFile + MapServer.ROOT_ULLON);
        results.put("ullat", - yi * latPerFile + MapServer.ROOT_ULLAT);
        results.put("lrlon", (xi + 1) * lonPerFile + MapServer.ROOT_ULLON);
        results.put("lrlat", - (yi + 1) * latPerFile + MapServer.ROOT_ULLAT);
        return results;
    }
    /* find the upper left file based on the upper left coordinate of query box */
    private int[] findULFile(double ullon, double ullat, int depth) {
        int[] ulFileInfo = new int[3];
        double parts = Math.pow(2, depth);
        lonPerFile = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / parts;
        latPerFile = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / parts;
        ulFileInfo[0] = depth;
        ulFileInfo[2] = ullon <= MapServer.ROOT_ULLON ? 0
                : (int) Math.floor((ullon - MapServer.ROOT_ULLON) / lonPerFile);
        ulFileInfo[1] = ullat >= MapServer.ROOT_ULLAT ? 0
                : (int) Math.floor((MapServer.ROOT_ULLAT - ullat) / latPerFile);
        return ulFileInfo;
    }

    private int[][][] generateFileArray(double lrlon, double lrlat, int[] ulFileInfo) {
        Map<String, Double> ulFileCoordinate = fileNameToBoundingBox(ulFileInfo);
        /* the distance between the boundary of the ulfile and the query box */
        double lonInterval = lrlon >= MapServer.ROOT_LRLON ? MapServer.ROOT_LRLON - ulFileCoordinate.get("lrlon")
                : lrlon - ulFileCoordinate.get("lrlon");
        double latInterval = lrlat <= MapServer.ROOT_LRLAT ? ulFileCoordinate.get("lrlat") - MapServer.ROOT_LRLAT
                : ulFileCoordinate.get("lrlat") - lrlat;
        /*The number of rows and columns of the files that can cover the query box */
        rowNum = (int) Math.ceil(latInterval / latPerFile) + 1;
        colNum = (int) Math.ceil(lonInterval / lonPerFile) + 1;
        int[][][] filesInfo = new int[rowNum][colNum][3];
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                filesInfo[i][j][0] = ulFileInfo[0];
                filesInfo[i][j][1] = ulFileInfo[1] + i;
                filesInfo[i][j][2] = ulFileInfo[2] + j;
            }
        }
        return filesInfo;
    }
    private String arrayToString(int[] fileInfo) {
        return "d" + Integer.toString(fileInfo[0]) + "_" + "x" + Integer.toString(fileInfo[2])
                + "_" + "y" + Integer.toString(fileInfo[1]) + ".png";
    }

    private void setArbitrary(Map<String, Object> results) {

        results.put("raster_ul_lon", Math.random());
        results.put("raster_ul_lat", Math.random());
        results.put("raster_lr_lon", Math.random());
        results.put("raster_lr_lat", Math.random());
        results.put("depth", Math.random());
        results.put("query_success", false);
        results.put("render_grid", Math.random());
    }
    public static void main(String[] args) {
        Map<String, Double> params = new HashMap<>();
//        params.put("ullon", -122.24163047377972);
//        params.put("ullat", 37.87655856892288);
//        params.put("lrlon", -122.24053369025242);
//        params.put("lrlat", 37.87548268822065);
//        params.put("w", 892.0);
//        params.put("h", 875.0);

        params.put("ullon", -122.30410170759153);
        params.put("ullat", 37.870213571328854);
        params.put("lrlon", -122.2104604264636);
        params.put("lrlat", 37.8318576119893);
        params.put("w", 1091.0);
        params.put("h", 566.0);
        Rasterer r = new Rasterer();
        Map<String, Object> results = r.getMapRaster(params);
        System.out.println(results.toString());
    }
}
