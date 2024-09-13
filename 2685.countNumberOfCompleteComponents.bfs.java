import java.util.*;

class Solution {
    public int countCompleteComponents(int n, int[][] edges) {

        // once way is that we generate list of components {component-1, component-2, component-3....}
        // we also keep track of number of times we visit node (in-degree).
        // then we check that in every component, every node should have n-1 in-degree (n is number of node in that component.)

        // second and efficient way is for every component we count number of component and number of edges.
        // so if we have n nodes then we must have n*(n-1)/2 edges.
        // if above condition is true then we can say that component is good.
        List<List<Integer>> graph=new ArrayList<>();
        for(int i=0;i<n;i++){
            graph.add(new ArrayList<>());
        }
        for(int i=0;i<edges.length;i++){
            int src=edges[i][0],des=edges[i][1];
            graph.get(src).add(des);
            graph.get(des).add(src);
        }

        int visited[]=new int[n];
//        List<List<Integer>> compo=new ArrayList<>();
        Arrays.fill(visited,-1);

        int goodComponents=0;
        for(int i=0;i<n;i++){
            if(visited[i]==-1){
                // not visited.

                Queue<Integer> q=new ArrayDeque<>();
//                List<Integer> subCompo=new ArrayList<>();
                q.add(i);
                visited[i]=0;
//                subCompo.add(i);
                int numberOfNode=1; //starting node.
                int numberOfEdges=0;
                while (!q.isEmpty()){
                    int node=q.poll();

                    for(int adjNode:graph.get(node)){
                        if(visited[adjNode]==-1){
                            // node is not visited yet.
                            visited[adjNode]=0; // mark as visited
                            q.add(adjNode);
//                            subCompo.add(adjNode);
                            // we visited new adjacent node so increase node count.
                            // we visited adjacent node by edge only, so increase edge count.
                            numberOfNode++;
                            numberOfEdges++;
                        }else{
                            // node is already visited, but we came to this visited node via edge only.
                            // so we have to increase edges here too.
                            numberOfEdges++;
                        }
                    }
                }
                // this is undirected graph  so for one edge we have increase in-degree of two nodes.
                // so we actually have counted-edges/2 edges. 
                if(numberOfEdges/2==(numberOfNode*(numberOfNode-1))/2)goodComponents++;
//                compo.add(subCompo);
            }else{
//                visited[i]++;
                // visited.
            }
        }
//        int good=0;
//        for(List<Integer> subs:compo){
//            boolean isGood=true;
//            int len=subs.size()-1;
//            for(Integer cell:subs){
//                if(visited[cell]!=len){
//                    isGood=false;
//                    break;
//                }
//            }
//            if(isGood)good++;
//        }
        return  goodComponents;
    }
}
