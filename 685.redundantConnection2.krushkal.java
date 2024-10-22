import java.util.ArrayList;
import java.util.Arrays;

class Solution {
    public int[] findRedundantDirectedConnection(int[][] edges) {
        
        /*
        only one edge is added

in directed tree, adding edge can cause 3 issues
1. node will have 2 parent
2. node will have cycle
3. node will have 2 parent + cycle

we can count in-degree of every node (we track edgeIndex in that array)
if node have in-degree of two then in loop if we see that node already have some edgeIndex, that means node have two parent. (collect both edgeIndexes)

if node do not have two parent => node has cycle
	simply do mst, and return ignored edge (that ignored edge must have caused cycle)

else (now graph has two parents and/or cycle )


	ignore one of the edge (edge-1)
		cycle do not exist => answer is edge-1
		
	now after removing edge-1 if cycle exists
	ignore edge-2
		cycle do not exist => answer is edge-2
	
	now after removing edge-2 if cycle exists
	no answer is possible
	
after removing one edge, if cycle still exists that also means our graph became disconnected.
        
         */
        int n=-1;
        for(int edge[]:edges) {
            n = Math.max(n, Math.max(edge[0], edge[1]));
        }
        int e1=-1,e2=-1;
        int ind[]=new int[n+1];
        Arrays.fill(ind,-1);
        for(int i=0;i<edges.length;i++){
            int des=edges[i][1];
            if(ind[des]==-1){
                ind[des]=i;
            }else{
                // two parent
                e1=ind[des];
                e2=i;
                break;
            }
        }
        // two edges we have
        if(e1==-1 && e2==-1){
            // no two parent. there can be only cycle
            int cycleIndex=  mst(edges,n,-1);
            if(cycleIndex==-1)return  new int[]{};
            return  edges[cycleIndex];
        }else{
            int afterE2=mst(edges,n,e2);
            if(afterE2==-1){
                // good to go
                return  edges[e2];
            }else{
                // after removing e2 we still have cycle
                int afterE1=mst(edges,n,e1);
                if(afterE1==-1){
                    // good to go
                    return  edges[e1];
                }else{
                    return new  int[]{};
                }

            }
        }
    }
    int mst(int[][]edges,int n,int ignore){
        DSU dj=new DSU(n);
        int cycle=-1;
        for(int i=0;i<edges.length;i++){
            int src=edges[i][0],des=edges[i][1];
            if(i==ignore)continue;
            if(dj.find(src)==dj.find(des)){
                cycle=i;
            }else{
                dj.union(src,des);
            }
        }
        return  cycle;
    }

}
class DSU{
    int parent[];
    int size[];
    DSU(int n){
        parent=new int[n+1];
        size=new int[n+1];
        for(int i=0;i<=n;i++){
            parent[i]=i;
            size[i]=1;
        }
    }
    int find(int i){
        if(parent[i]==i)return i;
        parent[i]=find(parent[i]);
        return  parent[i];
    }
    void union(int i,int j){
        int ui=find(i);
        int uj=find(j);
        int ui_size=size[ui];
        int uj_size=size[uj];

        int combined=ui_size+uj_size;
        if(ui_size>uj_size){
            parent[uj]=ui;
            size[ui]=combined;
        }else{
            parent[ui]=uj;
            size[uj]=combined;
        }
    }

}
