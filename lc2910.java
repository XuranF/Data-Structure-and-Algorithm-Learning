/*
LC2910 周赛第三题，Minimum Number of Groups to Create a Valid Assignment
*/

class Solution {
    public int minGroupsForValidAssignment(int[] nums) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for(int x: nums){
            cnt.putIfAbsent(x,0);
            cnt.put(x, cnt.get(x)+1);
        }
        int k = nums.length;
        for(int c: cnt.values()) k=Math.min(k,c);
        int ans = 0;
        for(;k>=0;k--){
            //这个题目的逻辑复杂处在如下：
            //需要不断去试探符合要求的次数，而且不能用二分
            //原因在于判断二分是否还要继续的指标->不是线性
            //余数和整除组数的difference会随着整除组数的变化而发生突变，不能保证线性
            //感觉更多是数论的考察
            for(int c:cnt.values()){
                int tmp1 = c/k;
                int tmp2 = c%k;
                if(tmp1<tmp2){
                    ans=0;
                    break;
                }
                else {
                    ans += c/(k+1);
                    if((c%(k+1))!=0) ans++;
                }
            }
            if(ans>0) return ans;
        }
        return nums.length;
    }
}