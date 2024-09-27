import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

class Solution {
    public int networkBecomesIdle(int[][] edges, int[] patience) {
        int n=patience.length;
        int path[]=new int[n];
        ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
        for(int i=0;i<n;i++){
            adj.add(new ArrayList<>());
        }
        for(int[] edge:edges){
            int src=edge[0];
            int des=edge[1];
            // undirected.
            adj.get(src).add(des);
            adj.get(des).add(src);
        }

        // first we find the shortest path for all nodes from 0.
        Arrays.fill(path,(int)1e7);
        Queue<Pair> q=new ArrayDeque<>();
        path[0]=0;
        q.add(new Pair(0,0));
        while (!q.isEmpty()){
            Pair node=q.poll();
            for(Integer adjNodes:adj.get(node.des)){
                if(node.wt+1<path[adjNodes]){
                    path[adjNodes]=node.wt+1;
                    q.add(new Pair(adjNodes,path[adjNodes]));
                }
            }
        }
        int ans=-1;
        for(int i=1;i<n;i++){
            if(patience[i]<(path[i]*2)){
                // if RTT of message is more then patience means server will send another message

                int firstMessageDuration=path[i]*2; //round trip

                int totalSecondsForLaterRequests=firstMessageDuration-1; // -1 because if we receive message back at time X then we don't  send request at time X.

                int totalMessages= totalSecondsForLaterRequests/patience[i]; //this many messages we will send in the time duration.
                // if total duration is 5 second and patience is 2 second then 5/2=2. we will send 2 message.

                int restOfTheMessages=(totalMessages)*patience[i]; // we have sent messages at every patience[i]. so we get back rest of the messages at every patience[i]


                if(firstMessageDuration+restOfTheMessages>ans){
                    // if this server's total time required for the idleness is more, then change the answer.
                    ans=firstMessageDuration+restOfTheMessages;
                }

            }else{
                if(path[i]*2>ans){
                    // if RTT of message is less then patience means server got message before patience ran out.
                    //server will not send any message after it received the message.
                    ans=path[i]*2;
                }
            }

        }
        return ans+1;

    }
}
class Pair{
    int des,wt;
    Pair(int des,int wt){
        this.des=des;
        this.wt=wt;
    }
}
