//207-Course Schedule
//第一种是DFS记录DNF看有没有backedge就行，判断有无cycle
//第二种是BFS，但是是按照入度数判断是否进入queue的
class Solution {
    /*
    List<List<Integer>> adjList;
    int[] status;
    boolean ans=true;
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        adjList=new LinkedList<>();
        status=new int[numCourses];
        for(int i=0;i<numCourses;i++){
            status[i]=-1;
            adjList.add(new LinkedList<>());
        }
        for(int[] pair: prerequisites)
            adjList.get(pair[0]).add(pair[1]);
        
        for(int i=0;i<numCourses;i++){
            if(status[i]==-1) dfs(i);
        }
        return ans;
    }

    public void dfs(int courseNum){
        status[courseNum]=0;
        for(Integer neigh: adjList.get(courseNum)){
            int neighStatus=status[neigh];
            if(neighStatus==0){ans=false;break;}
            if(neighStatus==1) continue;
            dfs(neigh);
        }
        status[courseNum]=1;
    }*/
    public boolean canFinish(int numCourses, int[][] prerequisites){
        List<List<Integer>> adjList=new LinkedList<>();
        int[] indegree=new int[numCourses];
        Queue<Integer> queue=new LinkedList<>();
        for(int i=0;i<numCourses;i++)
            adjList.add(new LinkedList<>());
        for(int[] pair: prerequisites){
            adjList.get(pair[1]).add(pair[0]);
            indegree[pair[0]]++;
        }
        for(int i=0;i<numCourses;i++){
            if(indegree[i]==0) queue.offer(i);
        }
        while(queue.size()>0){
            int tmp=queue.poll();
            numCourses--;
            for(Integer i: adjList.get(tmp)){
                indegree[i]--;
                if(indegree[i]==0) queue.offer(i);
            }
        }
        return numCourses==0;
    }
}