import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
    int xd[]={1,-1,0,0};
    int yd[]={0,0,1,-1};

    public int containVirus(int[][] isInfected) {
        int n=isInfected.length,m=isInfected[0].length;
        int ans=0;
     // idea is
        // 1. collect components with their border cell and number of walls
        // 2. remove component which can effect most AMOUNT OF HEALTHY CELLS (we used walls, border before which was completely wrong)
        // 3. mark entire component as -1 and save border cells as answers.
        // 4. remove all other components and infect all border cells of respective component.
        // 5. repeat.
        while (true){
            boolean visited[][]=new boolean[n][m];
            PriorityQueue<Component> pq=new PriorityQueue<>((a,b)->b.hs.size()- a.hs.size());

            // two loops M*N
            // dfs M*N
            // all dfs will explore M*N cells only.
            // so below two loops => M*N + M*N
            // assume there are R components
            // for R components we will add component in heap R times
            // for heap R*logR
            // total two loops below M*N + M*N + R*logR
            // two entire for loops to mark cells => M*N
            // total iteration will be K
            // worst case => K will be M*N => every iteration virus spread by one cell
            // in every iteration we bound one cell
            // this may not happen because cell are adjacent so K will be very small. but worst case is worst case
            // total time complexity=> K(M*N + M*N + R*logR + M*N) => M*N*M*N
            // space complexity => M*N because of grid

            for(int i=0;i<n;i++){
                for(int j=0;j<m;j++){
                    if(isInfected[i][j]==1 && visited[i][j]==false){
                        Component comp=new Component();
                        visited[i][j]=true;
                        comp.cells.add(new Pair(i,j));
                        dfs(isInfected,comp,visited,i,j,n,m); //dfs for collecting compoenent info
                        comp.walls=comp.border.size();
                        pq.add(comp);
                    }

                }
            }
            if(pq.size()==0)break; // no infected cell left without bounded walls.
            Component cmp=pq.poll();
            for(Pair node:cmp.cells){
                isInfected[node.x][node.y]=-1; // mark cells as -1 so we do not count them in later iterations.
            }
            ans+=cmp.walls;  //number of walls
            while (!pq.isEmpty()){
                Component cmp2=pq.poll();
                for (Pair node:cmp2.border){
                    isInfected[node.x][node.y]=1;
                    // infect border cells of all remaining components.
                }
            }
            // after this we will explore all border cell of current components, and we will add border of border (new level or new layer)
        }
        return ans;
    }
    void dfs(int [][]grid,Component compo,boolean visited[][],int x,int y,int n,int m) {
        for(int i=0;i<xd.length;i++){
            int nx=x+xd[i];
            int ny=y+yd[i];
            if(isValid(nx,ny,n,m)){
                if(grid[nx][ny]==1 && visited[nx][ny]==false){
                    compo.cells.add(new Pair(nx,ny));
                    visited[nx][ny]=true;
                    dfs(grid,compo,visited,nx,ny,n,m);

                }else if(grid[nx][ny]==0){
                    // if cell is valid and 0 means this is border cell of current node.
                    // add current node in border.
                    // same node can be included multiple times too
                    // if this empty node is surrounded by infected cell then all adjacent cell shared boundary with this empty cell (total walls 4)
                    compo.border.add(new Pair(nx,ny));
                    compo.hs.add(nx+","+ny); // add border cell in set because we also wants number of unique empty cells which can be infected (mostly for heap)
                }
            }
        }
    }
    boolean isValid(int x,int y,int n,int m){
        return x>=0 && y>=0 && x<n && y<m;
    }
}


class Component{
    int walls;
    List<Pair> cells,border;
    HashSet<String> hs;
    Component(){
        this.cells=new ArrayList<>();
        this.border=new ArrayList<>();
        this.hs=new HashSet<>();
    }
}
class Pair{
    int x,y;
    Pair(int x,int y){
        this.x=x;
        this.y=y;
    }


}
