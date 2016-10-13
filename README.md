# Graph-Topological-sort

## Algroritm used to find cycle in Digraph: 
 
Perform Depth First search (DFS) transversal on graph and the elements are stored in the stack 
 
Approach: - 
 
### printCycle() 
First we initialize a stack to store the visited vertex and an array list to store the elements in the cycle. Now whenever we find an edge we traverse to the vertex pointed by the edge by setting the visited flag to 1 and storing the vertex in the stack. But when the vertex is revisited during the same traverse we will pop out the vertex from the stack and store it in the array list. So the Array list will be populated with the cycle till the stack is empty.

## Algoritm used for Topological sort: 
 
To Compute a topological sort of Graph G:  Find a node v with no incoming edges and order it first in the list (noSuccessors())  Delete that node v(deleteVertex()) Recursively compute topological sort of G  Append this order after the node v 
 
Approach: - 
 
### noSuccessors() 
A topological order of a directed graph G = (V, E) is an ordering of its nodes as v1 , v2 , …, vn so that for every edge (vi, vj) we have i < j. To do this we need to find the node with no parent or successor. So to do I used a flag to determine if there is edge or not. If there is no edge the noSuccessors() will return that row number i.e vertex which will be stored in an array where topologocally sorted elements are present and deleteVertex() will delete the vertex . If none of this condition is fulfilled then the noSuccessors() will return -1 which means there is an cycle present in the graph.  
 
### deleteVertex() 
Once we find the vertex with no parent we need to delete that vertex and simultaniously move the row below it upward updateRow() and move the column on the left updateColumn(). 


## Conclusion
As are using Adjacency Matrix the space complexity of this algoritm will be |V|^2, where V is number of vertex (n in our case).  To find the vertices with no successors we have to scan the entire graph, which will cost us O(|V|^2) time. And we’ll have to do that |V| times. This the time complexity will be |V|^3
