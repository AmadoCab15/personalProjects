package bearmaps.proj2d;

import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.WeirdPointSet;
import bearmaps.proj2c.streetmap.Node;
import bearmaps.proj2c.streetmap.StreetMapGraph;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private WeirdPointSet graph;
    private List<Point> lstPoints = new ArrayList<>();
    private HashMap<Point, Node> nodeLocation = new HashMap<>();
    private Trie trieMap = new Trie();
    private HashMap<String, List<Node>> trieLocation = new HashMap<>();


    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        for(Node node : nodes) {
            if (node.name() != null) {
                String cleanedName = cleanString(node.name());
                if (trieMap.contains(cleanedName)) {
                    trieLocation.get(cleanedName).add(node);
                    trieMap.insert(cleanedName);

                } else {
                    trieLocation.put(cleanedName, new LinkedList<>());
                    trieLocation.get(cleanedName).add(node);
                    trieMap.insert(cleanedName);
                }
            }
            long address = node.id();
            long neighborsAround = neighbors(address).size();
            if (neighborsAround > 0) {
                Point point = new Point(node.lon(), node.lat());
                lstPoints.add(point);
                nodeLocation.put(point, node);
            }
        }
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        graph = new WeirdPointSet(lstPoints);
        Point nearest = graph.nearest(lon, lat);
        Node address = nodeLocation.get(nearest);
        return address.id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> answer = new ArrayList<>();
        String cleanedPrefix = cleanString(prefix);
        for (String word : trieMap.startsWith(cleanedPrefix)) {
            for (Node node: trieLocation.get(word)) {
                answer.add(node.name());
            }
        }
        return answer;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Map<String, Object>> answer = new ArrayList<>();
        String cleanedName = cleanString(locationName);
        for (Node node : trieLocation.get(cleanedName)) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("name", node.name());
            temp.put("lon", node.lon());
            temp.put("id", node.id());
            temp.put("lat", node.lat());
            answer.add(temp);
        }
        return answer;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
