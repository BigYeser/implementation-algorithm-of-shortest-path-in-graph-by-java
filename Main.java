
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	static boolean vis[];

	static UndirectedWeightedGraph oGraph;
	static UndirectedWeightedGraph myGraph;
	static ArrayList<Integer> res;
    public static void main(String[] args) throws FileNotFoundException {
    	vis = new boolean[130];
    	res = new ArrayList<>(130);
    	int test[][] = {{0,1,3},{0,2,2},{0,3,3},{1,3,1},{1,2,2},{2,3,2}};
    	int sz = 4;
     	oGraph = new UndirectedWeightedGraph(sz);

        for (int i = 0; i < sz ; i++) {
            	oGraph.addEdge(test[i][0], test[i][1], test[1][2]);
            
        }
        
        double[] pi = new double[oGraph.getNodeCount()];    //reachable vertices length
        int[] pred = new int[oGraph.getNodeCount()];        //predeceesor nodes for these paths
        for (int i = 0; i < oGraph.getNodeCount(); i++) {
            pi[i] = Double.POSITIVE_INFINITY;
            pred[i] = -1;
        }
        int root = 0;
        int v =  root;
        pi[v] = 0.0;
        pred[v] = v;
        IndexedHeap oHeap = new IndexedHeap(oGraph.getNodeCount());    //all nodes have to be covered yet
     
        for (int i = 0; i < oGraph.getNodeCount(); i++) {
            oHeap.insert(i, pi[i]);
        }

        while (!oHeap.empty()) {
            v = oHeap.deleteMin();            //minimum reachable node
            List<UndirectedWeightedGraph.Edge> oEdges = oGraph.getNode(v).getEdges();
            for (UndirectedWeightedGraph.Edge edge : oEdges)    //test edges from v
            {
                int w = edge.to;
                if (oHeap.contains(w) && edge.weight < pi[w]) {
                    pred[w] = v;
                    pi[w] = edge.weight;
                    oHeap.change(w, pi[w]);
                }
            }
        }
        
        myGraph = new UndirectedWeightedGraph(oGraph.getNodeCount());
        
        for(int i = 0 ; i < sz ; i++)
        {
        	myGraph.addEdge(i, pred[i], pi[i]);
        }

        dfs(myGraph.getNode(root));
        
        boolean ok[] = new boolean[500];
        int from = 0;
        ok[from] = true;
        for(int i = 1 ; i < res.size() ; i++)
        {
        	if(!ok[res.get(i)])
        	{
        		int to = res.get(i);
        		System.out.print("[from : " + from + " to :" + to +"]\n");
        		ok[to] = true;
        		from =to;
        	}
        }
		System.out.print("[from : " + from + " to :" + root +"]\n");
		
       
    }
  
    
   
    static void dfs(UndirectedWeightedGraph.Vertex node)
    {
    	if(vis[node.id])
    		return;
    	vis[node.id] = true;
    	ArrayList<UndirectedWeightedGraph.Vertex > nodes = node.getConnectedVertices();
    	for(UndirectedWeightedGraph.Vertex vertex: nodes)
    	{
    			if(!vis[vertex.id])
    			{    	
    				res.add(node.id);
    				res.add(vertex.id);
    				dfs(vertex);	
    				res.add(vertex.id);
    				res.add(node.id);
    			}
    	}
    } 
}
