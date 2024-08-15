import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        
        //Naive way is we write code to detect cycle from node-a.
        // if it says that cycle exists then we don't add into cycle.
        // we reset visited & samePath array & try with next node.
        
        boolean visited[]=new boolean[graph.length]; // to check if node is visited or not
        boolean samePath[]=new boolean[graph.length]; // to check cycle
        boolean safeNodes[]=new boolean[graph.length]; // list of node which are safe.

        List<Integer> ans=new ArrayList<>();

        for(int i=0;i<graph.length;i++){
            if(graph[i].length==0){
                safeNodes[i]=true; // if no outgoing edge means it's terminal & also cycle cannot exist.
                // add it to safeNode list.
                continue;
            }
            if(visited[i]==false){
                // if node is not visited.
                visited[i]=true;  //mark as visited
                samePath[i]=true; // since we are exploring i, we mark take i in path.
                dfs(i,graph,visited,samePath,safeNodes);
            }
        }
        for(int i=0;i<graph.length;i++){
            if(safeNodes[i]==true){
                ans.add(i);
            }
        }
        return  ans;
    }

    boolean dfs(int currNode,int [][]graph,boolean[] visited,boolean[]samePath,boolean[]safeNodes){

        // samePath will have node set to true if cycle exists. we don't set to false even if have started exploring other component.
        for(Integer adjNodes:graph[currNode]){
            if(visited[adjNodes]==false){
                // if adjacent node is not visited.
                visited[adjNodes]=true;
                samePath[adjNodes]=true;
                if(   dfs(adjNodes,graph,visited,samePath,safeNodes))  {
                //samePath[adjNodes]=false;
                    // NOTE THAT we do not mark adjNode in samePath false here.
                    // we are here because cycle exists
                    return  true;
                }
                // after exploring current node's adj we didn't find cycle. (we might have found safe states.)
                // we remove current adj node from path.
                samePath[adjNodes]=false;
            }else{
                if(samePath[adjNodes]) {
                    // if node is visited & in the path we return true.
                    //samePath[adjNodes]=false;
                    // NOTE THAT we do not mark adjNode in samePath false here.
                    // we are here because cycle exists
                    return true;
                }
            }
        }
        // we are returning false because we haven't detected cycle.
        // we are storing node as safe because even by exploring all child (adj) of current node we haven't found cycle.
        safeNodes[currNode]=true;
        return  false;
    }
}

// we solved with cycle detection technique. we can also try topo sort
