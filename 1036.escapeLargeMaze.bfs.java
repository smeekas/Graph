import java.util.*;

class Solution {
    int xd[]={0,0,1,-1};
    int yd[]={1,-1,0,0};
    public boolean isEscapePossible(int[][] blocked, int[] source, int[] target) {


        // with given blocked cells, what is the biggest area they can cover
        // we are working with positive xy plane only
        // they can form a triangle with help of x-axis and y-axis
        // from top...
        // every row will have one blocked cell
        // with every row, column will increase by one.

        // 1 cell, 2 cell, 3 cell,.... 200 cell
        // 1+2+3+4...+200 =n*(n-1)/2 =19900 cells of area
        
        // we will try to find target from source
        // we will run bfs for at-most 20000 cells.
        // if in 20k steps from source, we find target then return true, if not then return true
        // why? means we have avoided max area blocked cell can cover. maze is infinite. so we will certainly are able to reach target without any blocker
        // target can be covered by blocked too.
        // in that case bfs from source will return true but bfs from target will return false
        // if target is reachable from source then source also should be reachable from target.
        
        // from source if target is covered by blocked cell, we will go beyond 20k steps and return true,
        // bfs from target will return false, as it is covered by blocked cell.
        
        // in the end
        //   if target is reachable from source then source also should be reachable from target.
        HashMap<Integer, HashSet<Integer>> blockedAdj=new HashMap<>();
        for(int[]blockedCell:blocked){
            int x=blockedCell[0],y=blockedCell[1];
            blockedAdj.putIfAbsent(x,new HashSet<>());
            blockedAdj.get(x).add(y);
        }

        return bfs(blockedAdj,source,target)&&bfs(blockedAdj,target,source);
    }
    boolean isValid(int x,int y){
        // max limit is 10^6.
        return  x>=0 && y>=0 && x<1000000 && y<1000000;
    }
    boolean bfs( HashMap<Integer, HashSet<Integer>> blockedAdj,int[] source, int[] target){
        HashMap<Integer, HashSet<Integer>> visitedAdj=new HashMap<>();
        Queue<Pair> q=new ArrayDeque<>();
        q.add(new Pair(source[0],source[1]));
        visitedAdj.putIfAbsent(source[0],new HashSet<>());
        visitedAdj.get(source[0]).add(source[1]);
        int steps=0;
        while (!q.isEmpty()){
            Pair node=q.poll();
            Queue<Pair> q2=new ArrayDeque<>();
            steps++;

            if(steps>20000) {


                return true;

            }
            if(node.x==target[0] && node.y==target[1])return true;
            for(int i=0;i<xd.length;i++){
                int nx=node.x+xd[i];
                int ny= node.y+yd[i];
                if(isValid(nx,ny) && !blockedAdj.getOrDefault(nx, new HashSet<>()).contains(ny) && !visitedAdj.getOrDefault(nx,new HashSet<>()).contains(ny)){
                    // cell is valid, cell is not blocked and not visited
                    visitedAdj.putIfAbsent(nx,new HashSet<>());
                    visitedAdj.get(nx).add(ny); // mark as visited
                    q2.add(new Pair(nx,ny));
                }
            }
            q.addAll(q2);
        }
        return false;
    }
}
class  Pair{
    int x, y;
    Pair(int x,int y){
        this.x=x;
        this.y=y;
    }
}
