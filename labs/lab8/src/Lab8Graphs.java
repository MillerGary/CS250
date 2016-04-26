//import packages
import java.util.*;
import java.io.*;

public class Lab8Graphs {
    public static void main(String[] args) throws IOException {
        //initialize instance variables
        File file = new File("airline.txt");
        FileReader fr = new FileReader(file); //reads input file
        BufferedReader br = new BufferedReader(fr); //wrap file reader in buffered reader
        int numVert = Integer.parseInt(br.readLine()); //reads first line which designates the number of vertices
        //System.out.println(numVert); //debugging output

        //Inialize an ArrayList to hold cities with proper index for vertices
        ArrayList<String> cities = new ArrayList<String>();
        for (int i = 0; i < numVert; i++) {
            cities.add(br.readLine());
        } //for adding vertices
        //System.out.println(cities); //debugging output
        In in = new In(new File("input.txt"));
        In in2 = new In(new File("input.txt"));
        fr.close(); //closes file reader
        br.close(); //close bufferedreader

        //initialize a new edge weighted graph with data from input file
        EdgeWeightedGraph graph = new EdgeWeightedGraph(in);
        //initialize a new edge weighted digraph with data from input file
        EdgeWeightedDigraph diGraph = new EdgeWeightedDigraph(in2);

        //variables used for reading user input and calling selected methods
        Scanner scan = new Scanner(System.in);
        String input;

        //begin menu for running the program
        System.out.println("Please enter a valid command, enter help for a list of valid commands.");
        System.out.println("Enter exit to terminate the program.");
        input = scan.next(); //saves user input

        //while loops until user exits program, checks for next methods to be executed each iteration
        while (!input.equals("exit")) {
            switch (input.toLowerCase()) {
                case "exit":
                    break;
                case "help":
                    showHelp();
                    System.out.println("\nPlease enter a valid command.");
                    input = scan.next();
                    break;
                case "cities":
                    //method to print the list of valid cities
                    showCities(cities);
                    System.out.println("\nEnter another command or type exit to terminate the program");
                    input = scan.next();
                    break;
                case "display":
                    //method to display well formatted version of graph
                    showGraph(cities, graph);
                    System.out.println("Enter another command or type exit to terminate the program.");
                    input = scan.next();
                    break;
                case "mst":
                    //method to display MST of EdgeWeightedGraph
                    showMST(cities, graph);
                    System.out.println("Enter another command or type exit to terminate the program");
                    input = scan.next();
                    break;
                case "miles":
                    //method to display shortest path from source to target by distance
                    shortestMiles(cities, diGraph);
                    System.out.println("\nEnter another command or type exit to terminate the program");
                    input = scan.next();
                    break;
                case "price":
                    //method to display cheapest path from source to target by least amount of dollars
                    lowestPrice(cities, diGraph);
                    System.out.println("\nEnter another command or type exit to terminate the program.");
                    input = scan.next();
                    break;
                case "flights":
                    //method used to display the path from source to target by least number of flights
                    leastFlights(cities, diGraph);
                    System.out.println("\nEnter another command or type exit to terminate the program");
                    input = scan.next();
                    break;
                case "trips":
                    //method used to display the path from source to target with a budget constrain on the trip
                    affordableTrips(cities, graph);
                    System.out.println("\nEnter another command or type exit to terminate the program");
                    input = scan.next();
                    break;
                case "add":
                    //method used to add a new trip to the list of valid trips
                    addTrip(cities, graph);
                    System.out.println("\nEnter another command or type exit to terminate the program.");
                    input = scan.next();
                    break;
                case "remove":
                    //method used to remove trips from the list of valid trips
                    removeTrip(cities, graph);
                    System.out.println("\nEnter another command or type exit to terminate the program.");
                    input = scan.next();
                    break;
                default:
                    System.out.println("\nCommand is invalid, please enter a valid command or type exit to terminate the program");
                    input = scan.next();
                    break;
            } //switch (input)
        } //while
        System.out.println("Thank you for using the flight calculator, Goodbye.");
    } //main

    //method to display legal commands
    public static void showHelp() {
        System.out.println("\nhelp: displays valid commands that the system takes in.");
        System.out.println("cities: list out the cities in which flights depart from or arrive at.");
        System.out.println("exit: terminates the program.");
        System.out.println("display: displays the edge weighted graph in a well formatted fashion.");
        System.out.println("mst: displays the minimum spanning tree of the edge weighted graph.");
        System.out.println("miles: displays the shortest path by miles from inputted source to inputted target cities.");
        System.out.println("price: displays the cheapest path by least amount of dollars from inputted source to inputted target cities");
        System.out.println("flights: displays the path that requires the lest number of flights from an inputted source city to an inputted target city");
        System.out.println("trips: displays the trips that can be made with an inputted price limit for flights");
        System.out.println("add: allows user to add a trip to the list of valid flights");
        System.out.println("remove: allows user to remove a trip from the list of valid flights");
    } //show legal commands

    //method used to print the list of valid cities
    public static void showCities(ArrayList<String> cities) {
        System.out.println();
        for (int i = 0; i < cities.size(); i++) {
            System.out.println(cities.get(i));
        } //for printing cities
    } //show cities

    //method used to print a well formated version of the entire EdgeWeightedGraph
    public static void showGraph(ArrayList<String> cities, EdgeWeightedGraph graph) {
        System.out.println(graph.printGraph(cities)); //prints well formated display of flights,
    } //show graph

    //method used to find and display the MST of the Edgeweightedgraph
    public static void showMST(ArrayList<String> cities, EdgeWeightedGraph graph) {
        //initialize instance of Lazy Prim MST Class
        LazyPrimMST mst = new LazyPrimMST(graph);
        System.out.println("\nMinimum Spanning Tree is: ");
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        } //for printing edges
        StdOut.printf("Total weight of flight distances: %.1f\n\n", mst.weight1());
    } //show MST

    //method used to find the shortest path from a source city to a target city by distance
    public static void shortestMiles(ArrayList<String> cities, EdgeWeightedDigraph diGraph) {
        //initialize scanner for use within method's scope
        Scanner scan = new Scanner(System.in);
        //initialize variables to be on scope of entire method
        String sourceCity, targetCity;
        int source, target;
        //print output to get source and target city from user
        System.out.println("Valid cities are: ");
        showCities(cities);
        System.out.println("Please enter the city you wish to depart from.");
        sourceCity = scan.next();
        System.out.println("Please enter the city you wish to travel to");
        targetCity = scan.next();
        //find index value of source and target cities
        source = cities.indexOf(sourceCity);
        target = cities.indexOf(targetCity);
        //check to make sure valid cities were inputted
        if (source == -1 || target == -1) {
            System.out.println("\nInvalid cities were inputted.");
        } //if input cities are invalid
        else { //else valid input
            //System.out.println("Source city index is: " +source+ ". Target city index is: " +target+ "."); //debugging output
            DijkstraSP spMiles = new DijkstraSP(diGraph, source);
            System.out.println("\nThe shortest path from " +sourceCity+ " to " +targetCity+ " is:");
            System.out.println(spMiles.pathTo(target, cities));
            System.out.println("The distance it takes to fly this path is " +spMiles.distTo(target)+ " miles.");
        } //else input is valid
    } //shortestMiles

    public static void lowestPrice(ArrayList<String> cities, EdgeWeightedDigraph diGraph) {
        //initialize scanner for use within method's scope
        Scanner scan = new Scanner(System.in);
        //initialize variables to be on scope of entire method
        String sourceCity, targetCity;
        int source, target;
        //print output to get source and target city from user
        System.out.println("Valid cities are: ");
        showCities(cities);
        System.out.println("Please enter the city you wish to depart from.");
        sourceCity = scan.next();
        System.out.println("Please enter the city you wish to travel to");
        targetCity = scan.next();
        //find index value of source and target cities
        source = cities.indexOf(sourceCity);
        target = cities.indexOf(targetCity);
        //check to make sure valid cities were inputted
        if (source == -1 || target == -1) {
            System.out.println("\nInvalid cities were inputted.");
        } //if input cities are invalid
        else { //else valid input
            DijkstraSP spPrice = new DijkstraSP(diGraph, source);
            System.out.println("The cheapest path from " +sourceCity+ " to " +targetCity+ " is:");
            System.out.println(spPrice.priceTo(target, cities));
            System.out.println("The total price it takes to fly this path is $" +spPrice.priceFor(target)+ ".");
        } //else valid input
    } //lowest price

    //method to find the path from a source to a target uses the least number of flights
    public static void leastFlights(ArrayList<String> cities, EdgeWeightedDigraph diGraph) {
        //initialize scanner for use within method's scope
        Scanner scan = new Scanner(System.in);
        //initialize variables to be on scope of entire method
        String sourceCity, targetCity;
        int source, target;
        //print output to get source and target city from user
        System.out.println("Valid cities are: ");
        showCities(cities);
        System.out.println("Please enter the city you wish to depart from.");
        sourceCity = scan.next();
        System.out.println("Please enter the city you wish to travel to.");
        targetCity = scan.next();
        //find index of source and target city
        source = cities.indexOf(sourceCity);
        target = cities.indexOf(targetCity);
        //check to make sure valid cities were inputted
        if (source == -1 || target == -1) {
            System.out.println("\nInvalid cities were inputted.");
        } //if input cities are invalid
        else { //valid input
            DijkstraSP spFlights = new DijkstraSP(diGraph, source);
            System.out.println("The path with the least number of flights from " +sourceCity+ " to " +targetCity+ " is:");
            System.out.println(spFlights.flightsTo(target, cities));
            System.out.println("The total number of flights this path route takes is " +spFlights.numberFlights(target));
        } //else valid input
    } //least flights

    //method to determine the affordable trips given a budget constraint
    public static void affordableTrips(ArrayList<String> cities, EdgeWeightedGraph graph) {
        //initialize scanner for use within method's scope
        Scanner scan = new Scanner(System.in);
        //initialize variable to be on scope of entire method
        double budget;
        System.out.println("Please enter the budget you have allocated for your trip");
        budget = scan.nextDouble();
        if (budget < 0.0) {
            System.out.println("Invalid input, negative budget is not allowed.");
        } //if budget value is invalid
        else { //else the budget is valid
            System.out.println("List of affordable trips with a budget of $" +budget+ " is:");
            for (int source = 0; source < cities.size(); source++) {
                DepthFirstSearch dfs = new DepthFirstSearch(graph, source, budget);
                for (int v = 0; v < graph.V(); v++) {
                    if (dfs.marked(v)) {
                        StdOut.print(cities.get(v) + " ");
                    }
                }
                StdOut.println();
                if (dfs.count() != graph.V()) StdOut.println("City is not connected.");
                else StdOut.println("City is connected.");
                System.out.println("Trip " +(cities.get(source))+ "-" +cities.get(cities.size()-1));
            } //for finding affordable paths from each source
        } //else valid budget
    } //affordable trips

    public static void addTrip(ArrayList<String> cities, EdgeWeightedGraph graph) {
        Scanner scan = new Scanner(System.in);
        String startCity, endCity;
        int a,b,c,d;
        showCities(cities);
        System.out.println("Please enter the starting point of the new trip");
        startCity = scan.next();
        a = cities.indexOf(startCity);
        System.out.println("Please enter the destination of the new trip");
        endCity = scan.next();
        b = cities.indexOf(endCity);
        System.out.println("Please enter the distance of the new trip");
        c = scan.nextInt();
        System.out.println("Please enter the price of the new trip");
        d = scan.nextInt();

        if ( a == -1 || b == -1) {
            System.out.println("Invalid cities were enterred.");
        } //if cities are invalid
        else {
            System.out.println("Cities are valid.");
            //call add method here
            System.out.println("Information was added.");
        } //else valid cities were enterred
    } //add trip

    public static void removeTrip(ArrayList<String> cities, EdgeWeightedGraph graph) {
        Scanner scan = new Scanner(System.in);
        String startCity, endCity;
        int start, end;
        showCities(cities);
        System.out.println("Please enter the starting city");
        startCity = scan.next();
        System.out.println("Please enter the destination city");
        endCity = scan.next();
        start = cities.indexOf(startCity);
        end = cities.indexOf(endCity);
        if (start == -1 || end == -1) {
            System.out.println("Invalid cities were entered");
        } //if cities are invalid
        else {
            System.out.println("Cities are valid");

            System.out.println("Information was removed");
        } //else cities are valid
    } //remove trip

} //Lab8Graphs- class
