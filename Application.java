/*
 * @author Alexander Kline and Whitney Trovinger
 *
 * https://www.youtube.com/watch?time_continue=1&v=zVrPdF7f4-I&feature=emb_logo
 * https://www.geeksforgeeks.org/graph-and-its-representations/
 */

import java.awt.Color;
import java.util.*;

public class Application {
	static int numV, numE, minVertex, lastVertex, numOfEdges;
	static LinkedList<Integer> graph[];
	static ArrayList<Integer> check; // new
	static ArrayList<Edge> edges; // for testing
	static Scanner input;
	
	public static void main(String[] args) {
		Random rand = new Random();
		input = new Scanner(System.in);
		System.out.print("Enter the number of Vertices: ");
		numV = input.nextInt();
		
		Color color = null; //set the color of the edge 
		
		
		while (numV > 50) {// check to make sure there's not too many vertices
			System.out.println("Too many Vertices, dont break me. \nTry again: ");
			numV = input.nextInt();
		} // end check
		
		System.out.print("Enter the number of Edges!"); 
		numE = input.nextInt();
		
		while(numE > 50) {
			System.out.println("Too many Edges, don't break me! \nTry again: "); 
			numE = input.nextInt(); 
		}// end of while 
				
		
		graph = new LinkedList[numV]; // generate linkedList
		check = new ArrayList<Integer>(); // used to check that we only visited each vertex once

		for (int v = 0; v < numV; v++) {
			graph[v] = new LinkedList<>();
		} // end for

		edges = new ArrayList<Edge>(); // make edge array for check

		int rNum, rNum2;
		
		for (int i = 0; i < numE; i++) { // loop numE times
			do {// the while check if the edge already exists. Sometimes edges get duplicated it
				// seems.
				rNum = rand.nextInt(numV);
				rNum2 = rand.nextInt(numV);

				while (rNum == rNum2) {
					rNum = rand.nextInt(numV);
				} // end while. Make sure we arent connecting the node to itself
			} while (checkEdge(rNum, rNum2));// while loop checks if edge exists
			color=pickColor();
			edges.add(new Edge(rNum, rNum2, color)); // add our edge to edges so we can loop through edges in check edge
			addEdge(rNum, rNum2); // actually add the edge to the graph

		} // end populate graph

		print();
		System.out.println();

		for (int i = 0; i < numV; i++) { // Traverse the graph numV times
			boolean visited[] = new boolean[numV];
			check.clear();
			System.out.println("Iteration " + i + "\n");
			traverseGraph(i, visited);
		}
	}// end main

	
	static Color pickColor() {
		Color color = null;
		Random randColor = new Random();
		switch(randColor.nextInt(3)) {
		case 0: color = Color.BLUE;
		break;
		case 1: color= Color.RED;
		break;
		case 2: color = Color.BLACK;
		break;
		}
		return color;
	}

	/*
	 * Count Edges returns an array of the number of edges each vertex has.
	 */
	public static int[] countEdges() {

		// array to hold numbers
		int[] totalEdges = new int[numV];

		// counts the number of edges for each vertex
		for (int v = 0; v < numV; v++) {

			int countEdges = 0;
			for (Integer n : graph[v]) {
				countEdges++;
			} // end inner for

			totalEdges[v] = countEdges;
		} // end for

		return totalEdges;
	}// count Edges

	/**
	 * @param num1
	 * @param num2
	 * @return a boolean
	 */
	public static boolean checkEdge(int num1, int num2) { // return true if edge exists
		// Doesnt work with smaller vertices i think
		for (Edge e : edges) {
			if ((e.v1 == num1 && e.v2 == num2) || (e.v1 == num2 && e.v2 == num1)) {
				return true;
			} // end if else chain
		} // end for
		return false;
	}// end checkEdge
	
	public static String getColor(int v1, int v2) {
		Color c = null;
		String color= null;
		
		for (Edge e : edges) {
			if (e.v1 == v1 && e.v2 == v2) {
				c = e.getColor();
			} else if(e.v1 == v2 && e.v2 == v1) {
				c = e.getColor();
			}//end else if
		} // end for
		
		if(c == Color.RED) {
			color = "Red";
		} else if(c== Color.BLACK) {
			color ="Black";
		} else if(c==Color.BLUE) {
			color = "Blue";
		}
		
		
		return color;
	}
	/**
	 * @param v1
	 * @param v2
	 */
	public static void addEdge(int v1, int v2) { // add edge to the graph
		graph[v1].add(v2); // at vertex 1 add an edge to vertex 2
		graph[v2].add(v1); // since its unweighted, also add an edge from vertex 2, to vertex 1
	}// end add edge

	public static void print() { // print our graph
		for (int v = 0; v < numV; v++) {
			System.out.println("Vertex " + v); // get our column
			for (Integer n : graph[v]) {
				System.out.print(" -> " + n + " is colored " + getColor(v,n)); // each N is a node connected to the vertex V
			} // end inner for
			System.out.println();
		} // end for
	}// end print

	/*
	 * traverse the graph recursively code for search found here:
	 * https://www.geeksforgeeks.org/depth-first-search-or-dfs-for-a-graph/
	 */
	static void traverseGraph(int v, boolean visit[]) {
		// Mark the current node as visited and print it
		visit[v] = true;
		check.add(v); //add vertex to check
		System.out.print(v + " -> ");

		// Recur for all the vertices adjacent to this vertex
		Iterator<Integer> i = graph[v].listIterator();
		lastVertex = v;
		while (i.hasNext()) {
			int n = i.next();
			if (!visit[n])
				traverseGraph(n, visit);
		}
	}// end traverseGraph

}// end class
//Edge class was only made to be able to check the graph for duplicate edges

class Edge {
	int v1, v2;
	Color color;
	public Edge(int v1, int v2, Color color) {
		this.v1 = v1;
		this.v2 = v2;
		this.color = color;
	}// end constructor

	public int getV1() {
		return v1;
	}// end get v1

	public int getV2() {
		return v2;
	}// end get v2
	
	public Color getColor() {
		return color;
	}

	public String toString() {
		return v1 + " is connected to " + v2;
	}// toString to print edges
}// end class edge