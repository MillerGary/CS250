/*************************************************************************
 *  Compilation:  javac Edge.java
 *  Execution:    java Edge
 *
 *  Immutable weighted edge.
 *
 *************************************************************************/

/**
 *  The <tt>Edge</tt> class represents a weighted edge in an
 *  {@link EdgeWeightedGraph}. Each edge consists of two integers
 *  (naming the two vertices) and a real-value weight. The data type
 *  provides methods for accessing the two endpoints of the edge and
 *  the weight. The natural order for this data type is by
 *  ascending order of weight.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
//import packages
import java.util.ArrayList;

public class Edge implements Comparable<Edge> {

    private final int v;
    private final int w;
    private final double weight1;
    private final double weight2;

    /**
     * Initializes an edge between vertices <tt>v/tt> and <tt>w</tt> of
     * the given <tt>weight</tt>.
     * param v one vertex
     * param w the other vertex
     * param weight the weight of the edge
     * @throws IndexOutOfBoundsException if either <tt>v</tt> or <tt>w</tt>
     *    is a negative integer
     * @throws IllegalArgumentException if <tt>weight</tt> is <tt>NaN</tt>
     */
    public Edge(int v, int w, double weight1, double weight2) {
        if (v < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
        if (w < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
        if (Double.isNaN(weight1)) throw new IllegalArgumentException("Distance is NaN");
        if (Double.isNaN(weight2)) throw new IllegalArgumentException("Price is NaN");
        this.v = v; //endpoint 1
        this.w = w; //endpoint 2
        this.weight1 = weight1; //flight distance
        this.weight2 = weight2; //ticket price
    }

    /**
     * Returns the distance of the flight.
     * @return the distance of the flight
     */
    public double weight1() {
        return weight1;
    }
    public double weight2() {
        return weight2; //return price of flight
    }

    /**
     * Returns either endpoint of the edge.
     * @return either endpoint of the edge
     */
    public int either() {
        return v;
    }

    public int getV() {
        return v;
    }
    public int getW() {
        return w;
    }

    /**
     * Returns the endpoint of the edge that is different from the given vertex
     * (unless the edge represents a self-loop in which case it returns the same vertex).
     * @param vertex one endpoint of the edge
     * @return the endpoint of the edge that is different from the given vertex
     *   (unless the edge represents a self-loop in which case it returns the same vertex)
     * @throws java.lang.IllegalArgumentException if the vertex is not one of the endpoints
     *   of the edge
     */
    public int other(int vertex) {
        if      (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new IllegalArgumentException("Illegal endpoint");
    }

    /**
     * Compares two edges by weight.
     * @param that the other edge
     * @return a negative integer, zero, or positive integer depending on whether
     *    this edge is less than, equal to, or greater than that edge
     */
    public int compareTo(Edge that) {
        if      (this.weight1() < that.weight1()) return -1;
        else if (this.weight1() > that.weight1()) return +1;
        else                                    return  0;
    }
    /*public int compareToPrice(Edge that) {
        if      (this.weight2() < that.weight2()) return -1;
        else if (this.weight2() < that.weight2()) return +1;
        else                                      return 0;
    }*/

    /**
     * Returns a string representation of the edge.
     * @return a string representation of the edge
     */
    public String toString() {
        return String.format("%d-%d %.1f %.1f", v, w, weight1, weight2);
    }
    public String toString(ArrayList<String> cities) {
        return String.format("%s-%s, distance of flight is: %.1f, price of flight is: %.1f", cities.get(v), cities.get(w), weight1, weight2);
    }

    /**
     * Unit tests the <tt>Edge</tt> data type.
     */
    /*public static void main(String[] args) {
        //Edge e = new Edge(12, 23, 3.14);
        //StdOut.println(e);
    }*/
}
