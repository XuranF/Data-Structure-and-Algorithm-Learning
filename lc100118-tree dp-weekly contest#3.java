/*
Use List to construct tree instead of two-dimension array due to memory limit
正着想的逻辑会很晕，最好反过来想，从总和sum中减去最小的cost，再利用tree dp

*/

class Solution {
    //boolean[][] tree;
    List<Integer>[] arr;
    long[] keptTrack;// track cost
    long[] floatTrack;//track score
    int[] values;
    public long maximumScoreAfterOperations(int[][] edges, int[] _values) {
        int size = _values.length;
        //tree = new boolean[size][size];
        arr = new ArrayList[size];
        Arrays.setAll(arr, e->new ArrayList<>());
        keptTrack = new long[size];
        floatTrack = new long[size];
        values = _values;
        // for(int[] pair: edges){
        //     tree[pair[0]][pair[1]]=true;
        //     tree[pair[1]][pair[0]]=true;
        // }
        for(int[] pair: edges){
            int x = pair[0];
            int y = pair[1];
            arr[x].add(y);
            arr[y].add(x);
        }
        
        dfs(0, 0);
        return floatTrack[0];
    }
    
    public void dfs(int parent, int current){
        boolean hasChild=false;
        for(int i: arr[current]){
            if(parent==i) continue;
            hasChild=true;
            dfs(current, i);
            keptTrack[current] += keptTrack[i];
            floatTrack[current] += floatTrack[i];
            // if(tree[current][i]) {
            //     hasChild=true;
            //     dfs(current, i);
            //     keptTrack[current] += keptTrack[i];
            //     floatTrack[current] += floatTrack[i];
            // }
            
        }
        if(!hasChild){
            keptTrack[current] = values[current];
            floatTrack[current] = 0;
        }
        else{
            if(keptTrack[current]<=values[current]) {
                floatTrack[current] += values[current];
            }
            else {
                floatTrack[current] += keptTrack[current];
                keptTrack[current] = values[current];
            }
        }
        
    }
}