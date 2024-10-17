import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class Solution {
    public int minCostConnectPoints(int[][] points) {
        // we can create fully connected graph.
        // then apply mst algorithm.
        int n=points.length;

        ArrayList<int[]> edges=new ArrayList<>();
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(i==j)continue;
                int x1=points[i][0];
                int y1=points[i][1];

                int x2=points[j][0];
                int y2=points[j][1];
                // edge weight will be manhattan distance
                edges.add(new int[]{i,j,Math.abs(x1-x2)+ Math.abs(y1-y2)});
            }
        }
        Collections.sort(edges,(a,b)->a[2]-b[2]); // sort by edge weight
        DSU dis=new DSU(edges.size());
        int ans=0;
        for(int[] edge:edges){
            int src=edge[0],des=edge[1],wt=edge[2];
            if(dis.find(src)==dis.find(des)){
                // both belong to same component
                // adding src<->des will form a cycle
            }else{
                // we should take this as this will not form a cycle
                dis.union(src,des);
                ans+=wt;
            }
        }
        return ans;
    }
}
class DSU{
    ArrayList<Integer> sizes;
    ArrayList<Integer> parents;
    public DSU(int n){
        sizes=new ArrayList<>(n);
        parents=new ArrayList<>(n);
        for(int i=0;i<n;i++){
            sizes.add(1);
            parents.add(i);
        }
    }
    void union(int i,int j){
        if(find(i)==find(j))return;
        int ui=find(i);
        int uj=find(j);
        int ui_size=sizes.get(ui);
        int uj_size=sizes.get(uj);
        int combined=ui_size+uj_size;
        if(ui_size<uj_size){
            parents.set(ui,uj);
            sizes.set(uj,combined);
        }else{
            // ui_size >= uj_size
            parents.set(uj,ui);
            sizes.set(ui,combined);
        }
    }
    int find(int i){
        if(parents.get(i)==i)return i;
        int uparent=find(parents.get(i));
        parents.set(i,uparent);
        return  uparent;
    }

}
