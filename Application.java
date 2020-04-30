/*
 * @author Alexander Kline and Whitney Trovinger
 * https://www.youtube.com/watch?time_continue=1&v=zVrPdF7f4-I&feature=emb_logo
 * https://www.geeksforgeeks.org/graph-and-its-representations/
 */
import java.util.*;
public class Application {
	static int numV, numE, minVertex, lastVertex, numColors;
	static LinkedList<Integer> graph[];
	static ArrayList<Integer> check; // new
	static ArrayList<Edge> edges; // for testing
	static Scanner input;
	static int[] colors;
	
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
				

		if(numV%2==0 && (numE==numV*(numV-1)/2)) { //if the graph is K-complete and even, it should take n-1 colors
			numColors=numV-1;
		}else if(numV%2==1 &&(numE==numV*(numV-1)/2)) {//if its K-complete and odd, it should take n colors
			numColors=numV;
		}else {
			numColors=largestDegree(); //most the time the maximum degree should be the amount of colors
		}//set number of colors to be used

		colors = new int[numColors];//used to help check colors to prevent wrong colorings
		//when n is odd, n colors are needed: each color can only be used for (n-1)/2 edges
		//this colors array holds how many times we've used a color, is created dynamically
		graph = new LinkedList[numV]; // generate linkedList
		check = new ArrayList<Integer>(); // used to check that we only visited each vertex once

		for (int v = 0; v < numV; v++) {
			graph[v] = new LinkedList<>();
		} // end for
		
		for(int i = 0; i<numColors;i++) {
			colors[i]=0;
		}
		
		createGraph();
		
		boolean visited[] = new boolean[numV];
		traverseGraph(0,visited);
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
	
	public static int largestDegree() {
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
					if(getColor(i,j)==null) { //only color if not already, this seems to work better than without. 
						color=pickColor();
						while(!checkColor(i, color)) {
							color=pickColor();
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
		boolean canUse =false;
		int c = randColor.nextInt(numColors);
	/*	supposed to check if a color has been used too many times	
		if(numV%2==1 &&(numE==numV*(numV-1)/2)) {
			while(!canUse) {
				if(colors[c]>=((numV-1)/2)) {
					c=randColor.nextInt(numColors);
				}else{
					canUse=true;
				}
			}//end while
		}
	*/	
		switch(c) {
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
		}
		return color;
	}//end pickColor
	
	static boolean checkColor(int v, String color) {
		for(Edge e: edges) {
			if(e.v1==v) {
				if(color==e.getColor()) {
					return false;
				}//end if
			}else if(e.v2==v) {
				if(color==e.getColor()) {
					return false;
				}//end if
			}
		}//end for edges
		
		/*
		for (Integer n : graph[v]) {
			//currentColor = getColor(v,n); // set the edge's color to currentColor
			//if the color we are trying to give the edge equals any of the colors, then we return false. 
			if(color == getColor(v,n)) {
				return false;  
			}//end if 
		} //end for 
		*/
		return true; 
	}//end checkColor
	
	public static void setColor(int v1, int v2, String color) {
		int c=0;
		for (Edge e : edges) {
			if (e.v1 == v1 && e.v2 == v2) {
				e.setColor(color);
			}else if(e.v1 == v2 && e.v2 == v1) {//end if
				e.setColor(color);
			}
		} // end for
		switch(color) {
		case "Blue": c = 0;
		break;
		case "Red": c = 1;
		break;
		case "Black": c = 2;
		break;
		case "Green": c = 3;
		break;
		case "Orange": c = 4;
		break;
		case "Pink": c = 5;
		break;
		case "Purple": c = 6;
		break;
		case "Yellow": c = 7;
		break;
		case "Brown": c = 8;
		break;
		case "White": c = 9;
		break;
		}
		colors[c]= colors[c] +1;
	}//end setColor
	
	public static String getColor(int v1, int v2) {
		String color= null;
		
		for (Edge e : edges) {
			if (e.v1 == v1 && e.v2 == v2 || e.v1 == v2 && e.v2 == v1) {
				color = e.getColor();
			}//end if
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