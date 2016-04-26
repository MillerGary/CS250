/*************************************************************************
 *  Compilation:  javac DijkstraSP.java
 *  Execution:    java DijkstraSP input.txt s
 *  Dependencies: EdgeWeightedDigraph.java IndexMinPQ.java Stack.java DirectedEdge.java
 *  Data files:   http://algs4.cs.princeton.edu/44sp/tinyEWD.txt
 *                http://algs4.cs.princeton.edu/44sp/mediumEWD.txt
 *                http://algs4.cs.princeton.edu/44sp/largeEWD.txt
 *
 *  Dijkstra's algorithm. Computes the shortest path tree.
 *  Assumes all weights are nonnegative.
 *
 *  % java DijkstraSP tinyEWD.txt 0
 *  0 to 0 (0.00)
 *  0 to 1 (1.05)  0->4  0.38   4->5  0.35   5->1  0.32
 *  0 to 2 (0.26)  0->2  0.26
 *  0 to 3 (0.99)  0->2  0.26   2->7  0.34   7->3  0.39
 *  0 to 4 (0.38)  0->4  0.38
 *  0 to 5 (0.73)  0->4  0.38   4->5  0.35
 *  0 to 6 (1.51)  0->2  0.26   2->7  0.34   7->3  0.39   3->6  0.52
 *  0 to 7 (0.60)  0->2  0.26   2->7  0.34
 *
 *  % java DijkstraSP mediumEWD.txt 0
 *  0 to 0 (0.00)
 *  0 to 1 (0.71)  0->44  0.06   44->93  0.07   ...  107->1  0.07
 *  0 to 2 (0.65)  0->44  0.06   44->231  0.10  ...  42->2  0.11
 *  0 to 3 (0.46)  0->97  0.08   97->248  0.09  ...  45->3  0.12
 *  0 to 4 (0.42)  0->44  0.06   44->93  0.07   ...  77->4  0.11
 *  ...
 *
 *************************************************************************/


/**
 *  The <tt>DijkstraSP</tt> class represents a data type for solving the
 *  single-source shortest paths problem in edge-weighted digraphs
 *  where the edge weights are nonnegative.
 *  <p>
 *  This implementation uses Dijkstra's algorithm with a binary heap.
 *  The constructor takes time proportional to <em>E</em> log <em>V</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  Afterwards, the <tt>distTo()</tt> and <tt>hasPathTo()</tt> methods take
 *  constant time and the <tt>pathTo()</tt> method takes time proportional to the
 *  number of edges in the shortest path returned.
 *  <p>
 *  For additional documentation, see <a href="/algs4/44sp">Section 4.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
//import packages
import java.util.ArrayList;

public class DijkstraSP {
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private double[] priceFor;
    private DirectedEdge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
    private DirectedEdge[] priceEdgeTo;
    private IndexMinPQ<Double> pq;    // priority queue of vertices
    private IndexMinPQ<Double> pqPrice;

    /**
     * Computes a shortest paths tree from <tt>s</tt> to every other vertex in
     * the edge-weighted digraph <tt>G</tt>.
     * @param G the edge-weighted digraph
     * @param s the source vertex
     * @throws IllegalArgumentException if an edge weight is negative
     * @throws IllegalArgumentException unless 0 &le; <tt>s</tt> &le; <tt>V</tt> - 1
     */
    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        for (DirectedEdge e : G.edges()) {
            if (e.weight1() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
            if (e.weight2() < 0)
                throw new IllegalArgumentException("edge " +e+ " has negative weight");
        }

        distTo = new double[G.V()];
        priceFor = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        priceEdgeTo = new DirectedEdge[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        for (int v = 0; v < G.V(); v++)
            priceFor[v] = Double.POSITIVE_INFINITY;
        priceFor[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge e : G.adj(v))
                relax(e);
        }
        //relax vertices in order of distance from s by price
        pqPrice = new IndexMinPQ<Double>(G.V());
        pqPrice.insert(s, priceFor[s]);
        while (!pqPrice.isEmpty()) {
            int v = pqPrice.delMin();
            for (DirectedEdge e : G.adj(v))
                priceRelax(e);
        } //while pqPrice is not empty

        // check optimality conditions
        assert check(G, s);
    }

    // relax edge e and update pq if changed
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight1()) {
            distTo[w] = distTo[v] + e.weight1();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
    }

    //relax edge e and update pqprice if changed
    private void priceRelax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (priceFor[w] > priceFor[v] + e.weight2()) {
            priceFor[w] = priceFor[v] +e.weight2();
            priceEdgeTo[w] = e;
            if (pqPrice.contains(w)) pqPrice.decreaseKey(w, priceFor[w]);
            else                    pqPrice.insert(w, priceFor[w]);
        }
    } //priceRelax

    /**
     * Returns the length of a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>.
     * @param v the destination vertex
     * @return the length of a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>;
     *    <tt>Double.POSITIVE_INFINITY</tt> if no such path
     */
    public double distTo(int v) {
        return distTo[v];
    }
    public double priceFor(int v) {
        double total = 0.0;
        if (!hasPathTo(v)) return total;
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            total = total + e.weight2();
        } //for calculating total price of flight
        return total;
    }
    public int numberFlights(int v) {
        int flights = 0;
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            flights++;
        } //for counting flights
        return flights;
    }

    /**
     * Is there a path from the source vertex <tt>s</tt> to vertex <tt>v</tt>?
     * @param v the destination vertex
     * @return <tt>true</tt> if there is a path from the source vertex
     *    <tt>s</tt> to vertex <tt>v</tt>, and <tt>false</tt> otherwise
     */
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>.
     * @param v the destination vertex
     * @return a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>
     *    as an iterable of edges, and <tt>null</tt> if no such path
     */
    public Iterable<String> pathTo(int v, ArrayList<String> cities) {
        if (!hasPathTo(v)) {
            System.out.println("No path exists");
            return null;
        } //if no path
        Stack<String> path = new Stack<String>();
        String str;
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            str = "\n" +cities.get(e.from())+ " to " +cities.get(e.to())+ ", This flight is " +e.weight1()+ " miles";
            path.push(str);
            //System.out.println(cities.get(e.from())+ " to " +cities.get(e.to())+ " This flight cost " +e.weight1());
        }
        return path;
    }

    public Iterable<String> priceTo(int v, ArrayList<String> cities) {
        if (!hasPathTo(v)) {
            System.out.println("No path exists.");
            return null;
        } //if no path
        Stack<String> path = new Stack<String>();
        String str;
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            str = "\n" +cities.get(e.from())+ " to " +cities.get(e.to())+ ", This flight cost $" +e.weight2()+ ".";
            path.push(str);
        } //for path from source to target by lowest price
        return path;
    } //MST by price from source to target city

    public Iterable<String> flightsTo(int v, ArrayList<String> cities) {
        if (!hasPathTo(v)) {
            System.out.println("no Path exists.");
            return null;
        } //if no path
        Stack<String> path = new Stack<String>();
        String str;
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            str = "\n" +cities.get(e.from())+ " to " +cities.get(e.to())+ ".";
            path.push(str);
        } //for path from source to target by least number of flights
        return path;
    } //MST by number of flights from source to target

    public Iterable<String> budgetTrip(double budget, int source, ArrayList<String> cities, EdgeWeightedDigraph diGraph) {
        Stack<String> trips = new Stack<String>();
        String str;
        DirectedDFS DFS = new DirectedDFS(diGraph, source, budget, cities);
        //DirectedDFS DFS = new DirectedDFS(diGraph, source);

        /*for (int v = 0; v < diGraph.V(); v++) {
            if (DFS.marked(v)) System.out.println(cities.get(v)+ " ");
        } //for printing DFS*/

        str = "\n";
        trips.push(str);

        return trips;
    } //All affordable trips within inputted budget


    // check optimality conditions:
    // (i) for all edges e:            distTo[e.to()] <= distTo[e.from()] + e.weight()
    // (ii) for all edge e on the SPT: distTo[e.to()] == distTo[e.from()] + e.weight()
    private boolean check(EdgeWeightedDigraph G, int s) {

        // check that edge weights are nonnegative
        for (DirectedEdge e : G.edges()) {
            if (e.weight1() < 0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }

        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < G.V(); v++) {
            if (v == s) continue;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }

        // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
        for (int v = 0; v < G.V(); v++) {
            for (DirectedEdge e : G.adj(v)) {
                int w = e.to();
                if (distTo[v] + e.weight1() < distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < G.V(); w++) {
            if (edgeTo[w] == null) continue;
            DirectedEdge e = edgeTo[w];
            int v = e.from();
            if (w != e.to()) return false;
            if (distTo[v] + e.weight1() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }


    /**
     * Unit tests the <tt>DijkstraSP</tt> data type.
     */
    /*public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        int s = Integer.parseInt(args[1]);

        // compute shortest paths
        DijkstraSP sp = new DijkstraSP(G, s);


        // print shortest path
        for (int t = 0; t < G.V(); t++) {
            if (sp.hasPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
                if (sp.hasPathTo(t)) {
                    for (DirectedEdge e : sp.pathTo(t)) {
                        StdOut.print(e + "   ");
                    }
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no path\n", s, t);
            }
        }
    }//main- Unit test*/
} //class DijkstraSP

