import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

class Solution {
    public int maximumDetonation(int[][] bombs) {
        // how to calculate distance between two bomb?
        // pythagoras!
        //d^2= x^2 + y^2 => d^2= (x2-x1)^2 + (y2-y1)^2  => d= sqrt((x2-x1)^2 + (y2-y1)^2)
            // now if distance between bomb-A and bomb-B is less than bomb-A's range then bomb-A will also detonate bomb-B. so there is edge between A->B
        ArrayList<ArrayList<Integer>>  adj=new ArrayList<>();

        int n=bombs.length;
        for(int i=0;i<n;i++)adj.add(new ArrayList<>());
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){  // it cannot start from  i+1. because we have to check both the way, A to B and B to A.
                if(i==j)continue;

                double dis=distance(bombs[i][0],bombs[i][1],bombs[j][0],bombs[j][1]);

                if((double)bombs[i][2]>=dis){
                    // bomb i can detonate bomb j.
                    adj.get(i).add(j);
                }
            }
        }

        // we detonate every bomb one by one and find how many node we can visit.
        int maxAns=-1;
        for(int i=0;i<n;i++){
            boolean visited[]=new boolean[n];
            Queue<Pair> q=new ArrayDeque<>();
            visited[i]=true;
            q.add(new Pair(i,1));
            int localAns=1; //because of i.
            while (!q.isEmpty()){
                Pair node=q.poll();
                for(int adjNode:adj.get(node.node)){
                    if(visited[adjNode]==false){
                        visited[adjNode]=true;
                        // we visited new node so
                        localAns++;
                        q.add(new Pair(adjNode,node.dis+1));
                    }
                }

            }
            maxAns=Math.max(maxAns,localAns); // max ans
        }
        return  maxAns;

    }
    double distance(int x1,int y1,int x2,int y2){
        return  Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }
}
class Pair{
    int node,dis;
    Pair(int node,int dis){
        this.dis=dis;
        this.node=node;
    }
}

