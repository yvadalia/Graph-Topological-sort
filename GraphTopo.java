package graphtopo;

/**
 *
 * @author Yagnesh Vadalia
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Vertex {
    public int label;
    public Vertex(int lab) {
        label = lab;
    }
}

public class GraphTopo 
{
    private final int MAX_VERTS = 50;
    private Vertex vertexList[]; // list of vertices
    static int adjacency_matrix[][];
    private int matrix[][]; // adjacency matrix
    private int numVerts; // current number of vertices
    private int sortedArray[];
    private Stack<Integer> stack;
    public GraphTopo() throws IOException 
    {
        vertexList = new Vertex[MAX_VERTS];
        numVerts = 0;
        BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\Yagnesh Vadalia\\Desktop\\Graph3.csv")));;
        String line = "";
        String splitBy = ",";
        matrix = new int[MAX_VERTS][MAX_VERTS];
        adjacency_matrix = new int[MAX_VERTS][MAX_VERTS];
        int i =0;
        vertexList = new Vertex[MAX_VERTS];
        while ((line = br.readLine()) != null)
        {
            String[] elements = line.split(splitBy);
            int[] m = new int[MAX_VERTS];
            int j=0;
            for(String e : elements)
            {
                m[j] = Integer.parseInt(e);
                j = j+1;
            }
            instMatrix(matrix[i],m);
            instMatrix(adjacency_matrix[i],m);
            addVertex(i);
            i = i+1;
        }
        br.close();
        sortedArray = new int[MAX_VERTS]; // sorted vert labels
    }
    private void instMatrix(int[] matrix, int[] m) 
    {
        for(int i=0; i<m.length;i++)
            matrix[i] = m[i];
    }

    public void addVertex(int lab) 
    {
        vertexList[numVerts++] = new Vertex(lab);
    }

    /*public void displayVertex(int v) 
    {
        System.out.print(vertexList[v].label);
    }*/

    public void topoSort()
    {
        int orig_nVerts = numVerts;
        while (numVerts > 0)                        // while there are vertices
        {
            int currentVertex = noSuccessors();     // get a vertex with no successors
            if (currentVertex == -1)                // condition for a cycle
            {
                System.out.println("Graph has cycles");
                printCycle();
                return;
            }
            sortedArray[numVerts - 1] = vertexList[currentVertex].label;        // insert vertex label in sorted array
            deleteVertex(currentVertex);                                        // delete inserted vertex
        }
        
        System.out.print("Topologically sorted order: ");                       // print sortedArray
        for (int j = 0; j < orig_nVerts; j++)
            System.out.print((sortedArray[j])+1 + " ");
            //System.out.println("\nNo of elements: " + sortedArray.length);
            System.out.println("");
    }

    private void printCycle() 
    {
        stack = new Stack<Integer>();
        int start  = 0;
        stack.push(start);
        int element = start;
        int destination;
        int visited[] = new int[MAX_VERTS];
        visited[element] = 1;
        element = stack.peek();
        destination = element;
        do{
            if(destination == MAX_VERTS)
            {
                int j = stack.pop();
                element = stack.peek();
                destination = 0;        
            }
            if (adjacency_matrix[element][destination] == 1 && visited[destination] == 1 && stack.contains(destination))        // if the element is visited again 
            {
                List<Integer> cycle = new ArrayList<Integer>();
                cycle.add(destination);
                while(stack!=null)
                {
                    if(destination == stack.peek())
                        break;
                    cycle.add(stack.pop());
                }
                cycle.add(stack.pop());                                     // add elements forming cycle from stack to arraylist of cyclic element
                System.out.println("Cycle : ");
                for(int i =cycle.size(); i>0;i--)
                {
                    System.out.print((cycle.get(i-1)+1) + " ");
                }
                break;
            }
            if (adjacency_matrix[element][destination] == 1 && visited[destination] == 0)       // if there is edge, move to the vertex pointed by edge
            {
                stack.push(destination);
                visited[destination] = 1;                                                       // visited flag for the vertex
                adjacency_matrix[element][destination] = 0;
                element = destination;
                destination = 0;
                //System.out.print(element + "\t");
                continue;
            }
            destination++;
        }
        while (destination <= MAX_VERTS);                                               // compete the same for all vertex
    }

    public int noSuccessors()                   // returns vertices with no successors
    {
        boolean edgeFlag;                         // edge from row to column in adjacency matrix
        for (int row = 0; row < numVerts; row++) 
        {
            edgeFlag = false;                     // check for edge
            for (int col = 0; col < numVerts; col++) 
            {
                if (matrix[row][col] > 0)       // if edge found
                {
                    edgeFlag = true;
                    break;                      // this vertex has a successor try another
                }
            }
            if (!edgeFlag)                        // if there are no edges, return vertex with no successors
                return row;
        }
        return -1;                              // there is cycle
    }

    public void deleteVertex(int del_Vert)       
    {
        if (del_Vert != numVerts - 1)            // if not the last vertex, delete from vertexList
        {
            for (int j = del_Vert; j < numVerts - 1; j++)
                vertexList[j] = vertexList[j + 1];
            
            for (int row = del_Vert; row < numVerts - 1; row++)
                updateRow(row, numVerts);
            
            for (int col = del_Vert; col < numVerts - 1; col++)
                updateColumn(col, numVerts - 1);
        }
        numVerts--; // one less vertex
    }

    private void updateRow(int row, int length) 
    {
        for (int col = 0; col < length; col++)
            matrix[row][col] = matrix[row + 1][col];
    }

    private void updateColumn(int col, int length) 
    {
        for (int row = 0; row < length; row++)
            matrix[row][col] = matrix[row][col + 1];
    }

    public static void main(String[] args) throws IOException {
        GraphTopo ts = new GraphTopo();
        ts.topoSort();                           // topological sort
    }
} 
