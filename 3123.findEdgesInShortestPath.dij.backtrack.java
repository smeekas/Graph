import java.util.*;

class Solution {
    public boolean[] findAnswer(int n, int[][] edges) {

        ArrayList<ArrayList<Pair>> adj=new ArrayList<>();
        for(int i=0;i<n;i++)adj.add(new ArrayList<>());
        for(int i=0;i<edges.length;i++){
            int src=edges[i][0],des=edges[i][1],wt=edges[i][2];
            adj.get(src).add(new Pair(des,wt,i));
            adj.get(des).add(new Pair(src,wt,i));
        }
        int fromSrc[]=dijkstra(adj,n,0);
        int fromDes[]=dijkstra(adj,n,n-1);
        int shortestPath=fromSrc[n-1];
        boolean answer[]=new boolean[edges.length];
        for(int i=0;i<edges.length;i++){
            int src=edges[i][0],des=edges[i][1],wt=edges[i][2];
            int total=fromSrc[src]+wt+fromDes[des];
            int total2=fromSrc[des]+wt+fromDes[src];
            answer[i]=total==shortestPath|| total2==shortestPath;
        }
        return answer;
//        HashSet<Integer> ans=new HashSet<>();
//        boolean visited[]=new boolean[edges.length];
//
//        int shortest=dis[n-1];
//        back(n-1,shortest,adj,new HashSet<Integer>(),ans,visited);
//
//        boolean[] ansBool=new boolean[edges.length];
//        Iterator<Integer> ii=ans.iterator();
//        while ((ii.hasNext())){
//            ansBool[ii.next()]=true;
//        }
//        return ansBool;
    }
    void back(int src,int wt,ArrayList<ArrayList<Pair>> adj,HashSet<Integer> currAns,HashSet<Integer> finalAns,boolean visited[]){
        if(wt==0 && src==0){
            finalAns.addAll(currAns);
            return;
        }
        for(Pair adjNode:adj.get(src)){
            int adjNodeVal=adjNode.des;
            int adjNodeIndex=adjNode.index;
            int adjDis=adjNode.wt;
            if(wt-adjDis>=0 && visited[adjNodeIndex]==false){
                visited[adjNodeIndex]=true;
                currAns.add(adjNodeIndex);
                back(adjNodeVal,wt-adjDis,adj,currAns,finalAns,visited);
                currAns.remove(adjNodeIndex);
                visited[adjNodeIndex]=false;
            }
        }
    }

    int[] dijkstra(ArrayList<ArrayList<Pair>> adj,int n,int src){
        int INF=(int)1e8;
        int dis[]=new int[n];
        Arrays.fill(dis,INF);
        dis[src]=0;
        PriorityQueue<Pair> pq=new PriorityQueue<>((a,b)->a.wt-b.wt);
        pq.add(new Pair(src,0,0));
        while (!pq.isEmpty()){
            Pair node=pq.poll();
            int wt=node.wt;
            for(Pair adjNodes:adj.get(node.des)){
                if (wt+adjNodes.wt <dis[adjNodes.des]){
                    dis[adjNodes.des]=wt+adjNodes.wt;
                    pq.add(new Pair(adjNodes.des,dis[adjNodes.des],-1));
                }
            }
        }
        return dis;
    }
}
class  Pair{
    int des,wt,index;

    public Pair(int des, int wt,int index) {
        this.des = des;
        this.wt = wt;
        this.index=index;
    }
}
