class Pair{
    int wt;
    int des;
    Pair(int des,int wt){
        this.des=des;
        this.wt=wt;
    }
}
class Solution
{

    static int[] dijkstra(int V, ArrayList<ArrayList<ArrayList<Integer>>> adj, int S)
    {
        ArrayList<ArrayList<Pair>> graph=new ArrayList<>();
        for(int i=0;i<V;i++){
            graph.add(new ArrayList<>());
        }
        for(int i=0;i<V;i++){
            for(int j=0;j<adj.get(i).size();j++){
                int src=i;
                int des=adj.get(i).get(j).get(0);
                int wt=adj.get(i).get(j).get(1);
                // undirected graph so add edge both way.
                graph.get(src).add(new Pair(des,wt));
                graph.get(des).add(new Pair(src,wt));

            }
        }
        int[]distance=new int[V];
        Arrays.fill(distance,(int)(1e9));
        distance[S]=0; //src node distance is 0.

        PriorityQueue<Pair> pq=new PriorityQueue<>((a,b)->a.wt-b.wt);
        pq.add(new Pair(S,0));

        while (!pq.isEmpty()){
            // for every node we will go to adj nodes.
            // we will update distance to adj node if...
            // distance src to current node + distance from node to adj node < distance from src to adj node.
            // if is it lesser, then we update distance and add adj node to priority queue so that adj node can update it's adj nodes.
            Pair node=pq.poll();
            for(Pair adjNode:graph.get(node.des)){

                // why not take distance from node? and taking from distance array in below if?
                // distance array is like global storage which always have updated value.
                // in some other iteration distance to node might have been lesser and updated in distance array.

                // then why we need to heap of Pair? can't we just store node(integer)?
                // no. we want minimum distance first(kind of sorted by min distance). that's why we are using heap of Pair
                // although we can also use node's distance. we will only update if it is lesser in below if.
                // instead of priority queue we could have also utilized normal queue.
                // but reason behind using priority queue & distance from array is that for larger graph we can reduce few iteration.
                
                // NOTE (edit): some thought why we should use distance from node polled from queue instead of distance array.
                // distance array stores kind of result.
                // when we take distance from node we are taking distance from specific parent node (from perticular path). we are using node for exploration.
                // there can be multiple path which we cannot maintain distance array. because distance array stores the path with minimum distance.
                if(distance[node.des] + adjNode.wt <distance[adjNode.des]){
                    distance[adjNode.des]=distance[node.des] + adjNode.wt;
                    pq.add(new Pair(adjNode.des, distance[adjNode.des]));
                }
            }
        }
        for(int i=0;i<V;i++){
            if(distance[i]==(int)(1e9)){
                distance[i]=-1;
            }
        }
        return  distance;
    }
}
