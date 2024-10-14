import java.util.*;

class Solution {
    public int secondMinimum(int n, int[][] edges, int time, int change) {
        // first dijkstra then bfs
        // dijkstra is easy to understand.

        if(true) return  bfs(n,edges,time,change);
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());
        for (int[] edge : edges) {
            int src = edge[0], des = edge[1];
            adj.get(src).add(des);
            adj.get(des).add(src);
        }

        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> a.wt - b.wt);
        int INF = (int) 1e8;
        // 1-indexed graph
        int minDis[] = new int[n + 1];
        int minDis2[] = new int[n + 1];
        Arrays.fill(minDis2, INF);
        Arrays.fill(minDis, INF);
        minDis[1] = 0;
        pq.add(new Pair(1, 0));
        while (!pq.isEmpty()) {
            Pair node = pq.poll();
            for (Integer adjNodes : adj.get(node.node)) {
                int newDistance = node.wt + 1 * time; //edge weight is {time}
                // from time 0, signal changes at every node at change interval.
                // initially signal is green.
                // if we divide current distance(time) by that signal changing interval {change} then we get we are at what interval.

                // means 0(green) 1(red) 2(green) 3(red) 4(green) 5(red).....
                int statusOfChange = newDistance / change;
                if (statusOfChange % 2 == 0) {
                    // green
                } else {
                    // red
                    // if it is red, then we have to wait for next interval.
                    // current statusOfChange represents red signal
                    // statusOfChange+1 is for green signal.
                    // signal changes at every {change}
                    // so after waiting for signal to turn green total time would be (statusOfChange + 1) * change.
                    newDistance = (statusOfChange + 1) * change;

                }
                if (newDistance < minDis[adjNodes]) {
                    // if new distance(time) is lower than current min
                    minDis2[adjNodes] = minDis[adjNodes]; // save current min as second-best min
                    minDis[adjNodes] = newDistance; //new min
                    pq.add(new Pair(adjNodes, minDis[adjNodes]));
                }else if(newDistance<minDis2[adjNodes] && newDistance!=minDis[adjNodes]){
                    // if new distance(time) is higher then minDis but lower than second-best min, then we have to update second-best min
                    minDis2[adjNodes]=newDistance;
                    pq.add(new Pair(adjNodes, minDis2[adjNodes]));
                }
                // once we read at destination which is red, we do not have to wait for it to turn green, which we have did in other nodes.

                // so if node is n(destination) and second min time has been found then  we return time to reach that node (without waiting incase of red)
                // it is same as signal is green
                if(adjNodes==n && minDis2[adjNodes]!=INF)return  node.wt+ 1*time;
                // why we are returning directly from here?
                // graph is of unit weight. so when we reach destination, it will be minimal only.
                // also in-case signal at destination is red, then we don't want to include waiting time.
                // we will return only if second min distance has been found.

            }
        }
        return minDis2[n];

    }
    int bfs(int n,int [][] edges,int time,int change){
        // since graph has unit weights, we can think of using unit weight
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());
        for (int[] edge : edges) {
            int src = edge[0], des = edge[1];
            adj.get(src).add(des);
            adj.get(des).add(src);
        }
        // here first we run entire algorithm and store number of steps we have taken to reach that node.( minimum and second minimum)

        int INF = (int) 1e8;
        int minDis[] = new int[n + 1];
        int minDis2[] = new int[n + 1];
        Arrays.fill(minDis2, INF);
        Arrays.fill(minDis, INF);
        minDis[1] = 0;
        Queue<Pair> q=new ArrayDeque<>();
        q.add(new Pair(1,0));
        while (!q.isEmpty()){
            Pair node=q.poll();
            for(Integer adjNode:adj.get(node.node)){
                if(node.wt+1<minDis[adjNode]){
                    minDis2[adjNode]=minDis[adjNode];
                    minDis[adjNode]=node.wt+1;
                    q.add(new Pair(adjNode,minDis[adjNode]));
                }else if(node.wt+1 < minDis2[adjNode] && node.wt+1 != minDis[adjNode]){
                    minDis2[adjNode]= node.wt+1;
                    q.add(new Pair(adjNode,minDis2[adjNode]));
                }
            }
        }
        int steps=minDis2[n];
        if(steps==INF)return -1;
        int ans=0;
        // now we try to simulate, red/green lights
        // for n steps we try to go 1 step at a time
        int i=0;
        // we only go till second last step
        // reason
        // if it is last node then regardless of green or red light we will take {time} duration. and at the last node we won't be waiting for light to turn red (if it is red)
        // so from second last node to last node we will be done in {time} duration only.
        while (i<steps-1){
            // 0 means green. so even means green and odd means red.
            int newStep=(ans+time)/change;  //  travel in {time} then dividing by {change} will give current step.
            // to check whether light is green or red we cannot use step (i)
            // reason
            // light changes at every {change} not on every step.
            if(newStep%2==0){
                // if green
                // just add travelling time {time}
                ans+=time;
            }else{
                // if red
                // we travelled let's say x.y steps so we considered x steps
                // from .y we have to wait till x+1 so that light turns green {x to x+1 is red signal}
                // so we need to convert x.y into x+1.
                ans=(newStep + 1)*change;
            }
            i++;
        }

        return ans+time;
    }
}
class Pair{
    int node,wt;

    public Pair(int node, int wt) {
        this.node = node;
        this.wt = wt;
    }
}
