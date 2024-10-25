import java.util.ArrayDeque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

class Solution {
    int xd[]={0,0,1,-1};
    int yd[]={1,-1,0,0};
    public int cutOffTree(List<List<Integer>> forest) {
        int n=forest.size();
        int m=forest.get(0).size();
        if(forest.get(0).get(0)==0)return -1; // if source is blocked then we cannot do anything

        PriorityQueue<Pair> pq=new PriorityQueue<>((a,b)->a.height-b.height);
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                // if node is not blocked, and it is not 1(means we need to cut them)
                if(forest.get(i).get(j)!=0 && forest.get(i).get(j)!=1) pq.add(new Pair(i,j,forest.get(i).get(j)));
            }
        }
        // reason behind priority queue is that we need to start with smallest height first.
        // from the smallest height, we go to the next smallest tree.
        if(pq.size()==0)return -1;  // no trees to cut
        Pair curr=pq.poll(); // our source node.

        // if initial node is not 0,0 then we need to find the shortest path from 0,0 to given node.
        int ans=curr.x==0 && curr.y==0?0:shortest(forest,0,0,curr.x,curr.y,n,m);
        if(ans==-1)return -1; // we cannot reach initial node from 0,0
        while (!pq.isEmpty()){
            Pair second=pq.poll();
            // second node.
            // find the shortest path between two node
            int shortest=shortest(forest,curr.x,curr.y,second.x,second.y,n,m);
            if(shortest==-1)return -1; // if the shortest path is not possible then return -1
            ans+=shortest; // increase answer
            curr=second; // this second node is now our source
        }
        return  ans;
    }
    int shortest(List<List<Integer>> forest,int x,int y,int dx,int dy,int n,int m){
        // distance between two adjacent node is 1.
        // so we can use bfs to retrieve the shortest path
        Queue<Pair> q=new ArrayDeque<>();
        boolean visited[][]=new boolean[n][m];
        q.add(new Pair(x,y,0));
        while (!q.isEmpty()){
            Pair node=q.poll();

            for(int i=0;i<xd.length;i++){
                int nx= node.x+xd[i];
                int ny= node.y+yd[i];
                if(isValid(nx,ny,n,m) && forest.get(nx).get(ny)!=0 && visited[nx][ny]==false){
                    if(nx==dx && ny==dy)return node.height+1;
                    visited[nx][ny]=true;
                    q.add(new Pair(nx,ny,node.height+1));
                }
            }
        }
        return -1;
    }
    boolean isValid(int x,int y,int n,int m){
        return  x>=0 && y>=0 && x<n && y<m;
    }
}
class  Pair{
    int x,y, height;
    Pair(int x,int y,int height){
        this.x=x;
        this.y=y;
        this.height=height;
    }
}
