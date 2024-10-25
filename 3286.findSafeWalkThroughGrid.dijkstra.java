import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
    int LARGE=(int)-1e8;
    int xd[]={0,0,1,-1};
    int yd[]={1,-1,0,0};

    public boolean findSafeWalk(List<List<Integer>> grid, int health) {

        // simple dijkstra
        int n=grid.size(),m=grid.get(0).size();
        PriorityQueue<Pair> pq=new PriorityQueue<>((a,b)->b.wt-a.wt);
        int best[][]=new int[n][m];
        for(int row[]:best) Arrays.fill(row,LARGE);
        // we start with health
        // if adjacent is 0 we can go there without loosing health
        // if adjacent is 1 we can go there by loosing one health point
        int initialHealth=grid.get(0).get(0)==0?health:health-1;
        pq.add(new Pair(0,0,initialHealth));
        best[0][0]=initialHealth;
        while (!pq.isEmpty()){
            Pair node=pq.poll();
            for(int i=0;i<xd.length;i++){
                int nx=node.x+xd[i];
                int ny=node.y+yd[i];
                if(isValid(nx,ny,n,m)){
                    if(grid.get(nx).get(ny)==1){
                        // health will be reduced by 1.
                        if( node.wt-1>0 && node.wt-1>best[nx][ny]){
                            // if health is >0, and it is better than current health
                            best[nx][ny]= node.wt-1;
                            pq.add(new Pair(nx,ny, node.wt-1));
                        }
                    }else{
                        // health will not be reduced by 0.
                        if(node.wt>0 && node.wt>best[nx][ny]){
                            // if health is >0, and it is better than current health
                            best[nx][ny]= node.wt;
                            pq.add(new Pair(nx,ny,node.wt));
                        }

                    }
                }
            }
        }

        // if our best result is greater than 0 means we can reach from source to destination with given health
        return best[n-1][m-1]>=0;
    }
    boolean isValid(int x,int y,int n,int m){
        return  x>=0 && y>=0 && x<n && y<m;
    }
}
class  Pair{
    int x,y,wt;

    public Pair(int x, int y, int wt) {
        this.x = x;
        this.y = y;
        this.wt = wt;
    }
}
