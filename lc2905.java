/*
LC2905 周赛第三题，Find Indices With Index and Value Difference II
*/

//这个题目要理解到只记录并维护前面最小和最大值即可，不需要开map把所有的都记录下来，否则只是在增加没必要的复杂度
class Solution {
    public int[] findIndices(int[] nums, int indexDifference, int valueDifference) {
        // if(nums.length==1&&indexDifference==0&&valueDifference==0) return new int[]{0,0};
        // if(nums.length==1&&indexDifference!=0&&valueDifference!=0) return new int[]{-1,-1};
        // Map<Integer, Integer> map = new HashMap<>();
        // for(int i=0;i<indexDifference;i++){
        //     if(!map.containsKey(nums[i])) map.put(nums[i],i);
        // }
        // int[] ans = new int[]{-1,-1};
        // for(int i=indexDifference;i<nums.length;i++){
        //     int tmp1=nums[i]+valueDifference;
        //     int tmp2=nums[i]-valueDifference;
        //     for(int key:map.keySet()){
        //         if((key>=tmp1 || key<=tmp2)&&(i-map.get(key)>=indexDifference)){ans[0]=map.get(key); ans[1]=i;return ans;}
        //     }
        //     if(!map.containsKey(nums[i])) map.put(nums[i],i);
        // }
        // return ans;
        int maxIndex=0, minIndex=0;
        for(int i=indexDifference;i<nums.length;i++){
            int tmp=i-indexDifference;
            if(nums[tmp]>nums[maxIndex]) maxIndex=tmp;
            if(nums[tmp]<nums[minIndex]) minIndex=tmp;
            if(nums[i]-nums[minIndex]>=valueDifference) return new int[]{minIndex,i};
            if(nums[maxIndex]-nums[i]>=valueDifference) return new int[]{i,maxIndex};
        }
        return new int[]{-1,-1};
    }
}