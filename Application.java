/*
 * @author Alexander Kline and Whitney Trovinger
 * https://www.youtube.com/watch?time_continue=1&v=zVrPdF7f4-I&feature=emb_logo
 * https://www.geeksforgeeks.org/graph-and-its-representations/
 */
import java.util.*;
public class Application {
	static int numV, numE, minVertex, lastVertex, numColors, reset;
	static LinkedList<Integer> graph[];
	static ArrayList<Integer> check; // new
	static ArrayList<Edge> edges; // for testing
	static Scanner input;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		input = new Scanner(System.in);
		System.out.print("Enter the number of Vertices: ");
		numV = input.nextInt();
		
		while (numV > 50) {// check to make sure there's not too many vertices
			System.out.println("Too many Vertices, dont break me. \nTry again: ");
			numV = input.nextInt();
		} // end check
		
		System.out.print("Enter the number of Edges: "); 
		numE = input.nextInt();
		
		while(numE > numV*(numV-1)/2) {//can not have more than a complete graph
			System.out.println("Too many Edges, don't break me! \nTry again: "); 
			numE = input.nextInt(); 
		}// end of while 
				

		graph = new LinkedList[numV]; // generate linkedList
		check = new ArrayList<Integer>(); // used to check that we only visited each vertex once

		for (int v = 0; v < numV; v++) {
			graph[v] = new LinkedList<>();
		} // end for
		
		createGraph();
		
		if(numV%2==0 && (numE==numV*(numV-1)/2)) { //if the graph is K-complete and even, it should take n-1 colors
			numColors=numV-1;//numColors is used in pickColors
		}else if(numV%2==1 &&(numE==numV*(numV-1)/2)) {//if its K-complete and odd, it should take n colors
			numColors=numV;
		}else {
			numColors=largestDegree(); //most the time the maximum degree should be the amount of colors
		}//set number of colors to be used
		
		boolean visited[] = new boolean[numV];
		traverseGraph(0,visited);
		System.out.println("Done Traversing, Coloring\n");
		colorGraph();
		
		print();
	
	}//end main
	
	static void createGraph() {
		int rNum, rNum2;
		Random rand = new Random();
		
		edges = new ArrayList<Edge>(); // make edge array for check
		
		for (int i = 0; i < numE; i++) { // loop numE times
			do {// the while check if the edge already exists. Sometimes edges get duplicated it
				// seems.
				rNum = rand.nextInt(numV);
				rNum2 = rand.nextInt(numV);

				while (rNum == rNum2) {
					rNum = rand.nextInt(numV);
				} // end while. Make sure we arent connecting the node to itself
			} while (checkEdge(rNum, rNum2));// while loop checks if edge exists
			//color=pickColor();
			edges.add(new Edge(rNum, rNum2, null)); // add our edge to edges so we can loop through edges in check edge
			edges.add(new Edge(rNum2, rNum, null));
			addEdge(rNum, rNum2); // actually add the edge to the graph

		} // end for loop to populate graph
	}//end create graph
	
	public static boolean checkEdge(int num1, int num2) { // return true if edge exists
		// Doesnt work with smaller vertices i think
		for (Edge e : edges) {
			if ((e.v1 == num1 && e.v2 == num2) || (e.v1 == num2 && e.v2 == num1)) {
				return true;
			} // end if else chain
		} // end for
		return false;
	}// end checkEdge
	
	public static void addEdge(int v1, int v2) { // add edge to the graph
		graph[v1].add(v2); // at vertex 1 add an edge to vertex 2
		graph[v2].add(v1); // since its unweighted, also add an edge from vertex 2, to vertex 1
	}// end add edge
	
	public static int largestDegree() {//redid largest degree to not need countEdges method
		int largest=0;
		for(int i=0;i<numV;i++){
			int count =0;
			for(Integer j: graph[i]) {
				if(checkEdge(i,j)) {
					count++;
				}//end if
			}//end j for
			//count= count/2;
			if(count>largest) {
				largest = count;
			}//end if
		}//end i for
		return largest;
	}//end largest
	
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
		}//end while
	}// end traverseGraph
	
	public static void colorGraph() { //supposed to color entire graph in one method call
		String color = null;
		for(int i=0; i<numV; i++) {
			for(Integer j: graph[i]) {
				if(checkEdge(i,j)) {
					if(getColor(i,j)==null) { //only color if not already, loops infinitely without 
						color=pickColor();
						reset=0;
						while(!checkColor(i,j, color)) {
							color=pickColor();
							reset++;
							if(reset>200000*numV) {//if we've looped a million times, reset colors to null and try again
								System.out.println("Reset\n");
								reset();
								break;
							}
						}//end while
						setColor(i,j,color);						
					}//end color==null
				}//end if checkedge
			}//end j for
		}//end i for
	}//end colorGraph

	static String pickColor() {
		String color = null;
		Random randColor = new Random();

		switch(randColor.nextInt(numColors)) { //added more colors
		case 0: color = "Blue";
		break;
		case 1: color = "Red";
		break;
		case 2: color = "Black";
		break;
		case 3: color = "Green";
		break;
		case 4: color = "Orange";
		break;
		case 5: color = "Pink";
		break;
		case 6: color = "Purple";
		break;
		case 7: color = "Yellow";
		break;
		case 8: color = "Brown";
		break;
		case 9: color = "White";
		break;
		case 10: color = "Grey";
		break;
		case 11: color = "Violet";
		break;
		case 12: color = "Cyan";
		break;
		case 13: color = "Magenta";
		break;
		case 14: color = "Scarlet";
		break;
		case 15: color = "Indigo";
		break;
		case 16: color = "Cerulean";
		break;
		case 17: color = "Bitter Lemon";
		break;
		case 18: color = "Blond";
		break;
		case 19: color = "Quartz";
		break;
		case 20: color = "Rose Gold";
		break;
		case 21: color = "Gold";
		break;
		case 22: color = "Silver";
		break;
		case 23: color = "Orchid";
		break;
		case 24: color = "Burgandy";
		break;
		}
		return color;
	}//end pickColor
	
	static boolean checkColor(int v,int v2, String color) { //tried to see if i can make check colors work better, idk if it does
		
		for (Integer n : graph[v]) {
			//currentColor = getColor(v,n); // set the edge's color to currentColor
			//if the color we are trying to give the edge equals any of the colors, then we return false. 
			if(color == getColor(v,n)) {
				return false;  
			}//end if 
			if(color==getColor(n,v)) {
				return false;
			}//end if
		} //end for n
		
		for(Integer i: graph[v2]) { //check if the other node has that color already
			//we dont want to use orange if our other node has orange on it already, i think(it seems to work)
			if(color==getColor(v2,i)) {
				return false;
			}//end if
			if(color==getColor(i,v2)) {
				return false;
			}
		}//end for i
		//i know this extra check is probably pointless and does the same thing as the others, i just am not sure how to make it check correctly
		//i've seen some other colorings that use back tracking and i feel like we're gonna have to do something similar.
		for(Edge e : edges) {
			if(v==e.v1 || v==e.v2) {
				if(color==e.getColor()) {
					return false;
				}//end color if
			}//end v1 if
			if(v2==e.v1 || v2==e.v2) {
				if(color==e.getColor()) {
					return false;
				}//end color if
			}//end v2 if
		}//end for
		
		return true;
	}//end checkColor
	
	public static void setColor(int v1, int v2, String color) {
		for (Edge e : edges) {
			if (e.v1 == v1 && e.v2 == v2) {
				e.setColor(color);
			}else if(e.v1 == v2 && e.v2 == v1) {//end if
				e.setColor(color);
			}//end set color
		} // end for
	}//end setColor
	
	public static String getColor(int v1, int v2) {
		String color= null;
		
		for (Edge e : edges) {
			if (e.v1 == v1 && e.v2 == v2) {
				color = e.getColor();
			}else if( e.v1 == v2 && e.v2 == v1) {
				color = e.getColor();
			}
		} // end for	
		return color;
	}
	
	public static void print() { // print our graph
		for (int v = 0; v < numV; v++) {
			System.out.println("Vertex " + v); // get our column
			for (Integer n : graph[v]) {
				System.out.print(" -> " + n + " is colored " + getColor(v,n)); // each N is a node connected to the vertex V
			} // end inner for
			System.out.println();
		} // end for
	}// end print	
	
	static void reset() {
		for(int i=0; i<numV; i++) {
			for(Integer n: graph[i]) {
				setColor(i,n,null);
				setColor(n,i,null);//just making sure its all resetting, worst case scenario it replaces null with null
			}//end int n for
		}//end for i
		colorGraph();
	}//end reset
	
}//end class

class Edge {
	int v1, v2;
	String color;
	public Edge(int v1, int v2, String color) {
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
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String toString() {
		return v1 + " is connected to " + v2;
	}// toString to print edges
}// end class edge