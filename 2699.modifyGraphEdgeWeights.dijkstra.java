import java.util.*;

class Solution {
    int LARGE=(int)2e9;
    void pr(int[][]ed){
        for(int edd[]:ed){
            System.out.println(edd[0]+" "+edd[1]+" "+edd[2]);
        }
        System.out.println("=============");
    }
    public int[][] modifiedGraphEdges(int n, int[][] edges, int source, int destination, int target) {
        // 3 cases
        // case-1. current shortest path is lesser than target
        // case-2. current shortest path is equal to target
        // case-3. current shortest path is greater than target

        // case-1. we cannot decrease the shortest path by adding new edge or by increasing edge weight.
        // no answer
        // case-2. we don't have to do anything. just mark INFINITY to non-weighted edges. and return edges.
        int shortestPath=dijkstra(n,edges,source,destination);

        if(shortestPath<target)return  new int[][]{};
        // shortestPath is very small.
        // we cannot decrease it by adding new edges.
        // so answer is not possible

        if(shortestPath==target){
            // graph already have the shortest path
            for(int i=0;i<edges.length;i++){
                // assigning INFINITY to remaining non-weighted edges. so that it do not affect our current shortest path.
                if(edges[i][2]==-1)edges[i][2]=LARGE;
            }
            return  edges;
        }
        // case-3.
        // shortest path is too large so modify edges to decrease it

        // approach
        // we try to add every edge one by one.
        // if after taking edge (minimal weight (1)),
        // if the shortest path is equal to target then we stop and assign INFINITY to remaining non-weighted edges.
        // if the shortest path is still greater than target then we continue with other edges.
        // if the shortest path becomes lesser than target then
                // we found answer
                // instead of 1 we assign (target-currentShortestPath +1) to edge, and we stop and assign INFINITY to remaining non-weighted edges.
                // explained below

        int i=0;
        int shortestPathNow=shortestPath;
        boolean found=false;  // we need this because what if graph do not have edge -1? means we cannot change weight and shortestPath>target?
        // if in below iteration if we find then we toggle the boolean
        for(i=0;i<edges.length;i++){
            if(edges[i][2]==-1){
                edges[i][2]=1;
                shortestPathNow=dijkstra(n,edges,source,destination);
                if(shortestPathNow>target){
                    // still shortest path is large. we need to add more edges of weight 1 to check if it decreases shortestPath or not.
                }else if(shortestPathNow==target){
                    // we got the ans
                    found=true;
                    break;
                }else{
                    // by adding more edges if weight 1 the shortest path is not reduced and less than target.
                    edges[i][2]=(target-shortestPathNow) +1 ;
                    // we want shortestPath == target.
                    // current edge's 1 is counted in shortestPath.
                    // currently shortestPath < target
                    // how much we need to add?
                    // shortestPath + (target-shortestPath) ==target
                    found=true;
                    break;
                }
            }
        }
        if(i<edges.length){
            //assign INFINITY to remaining non-weighted edge.
            while (i<edges.length){
                if(edges[i][2]==-1)edges[i][2]=LARGE;
                i++;
            }
        }else{
            // we tried every edge, but we are not able to find answer.
            if(!found)return new int[][]{}; // we were not able to build shortestPath

            // we found!
            return  edges;
        }
        return edges;
    }

    int dijkstra(int n,int [][]edges,int source, int destination){
        HashMap<Integer, ArrayList<Pair>> hm=new HashMap<>();
        for(int i=0;i<n;i++)hm.put(i,new ArrayList<>());
        for(int[] edge:edges){
            int src=edge[0];
            int des=edge[1];
            int wt=edge[2];
            hm.get(src).add(new Pair(des,wt));
            hm.get(des).add(new Pair(src,wt));
        }
        PriorityQueue<Pair> pq=new PriorityQueue<>((a,b)->a.wt-b.wt);
        pq.add(new Pair(source,0));
        HashMap<Integer,Integer> dis=new HashMap<>();
        dis.put(source,0);
        while (!pq.isEmpty()){
            Pair p=pq.poll();
            for(Pair node:hm.get(p.node)){
                if(node.wt==-1)continue;
                if(dis.containsKey(node.node)){
                    if(p.wt+node.wt<dis.get(node.node)){
                        dis.put(node.node,p.wt+node.wt);
                        pq.add(new Pair(node.node,p.wt+node.wt));
                    }

                }else{
                    dis.put(node.node,p.wt+node.wt);
                    pq.add(new Pair(node.node,p.wt+node.wt));
                }
            }
        }
        return dis.containsKey(destination)?dis.get(destination):LARGE;
    }
}
class Pair{
    int node,wt;
    Pair(int node,int wt){
        this.node=node;
        this.wt=wt;
    }
}