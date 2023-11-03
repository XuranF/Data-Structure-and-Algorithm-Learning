/*
LC875 Koko Eating Bananas
*/

class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        //最小化最大值问题
        //在所有方案中找到一个可以尽量填满hours的最小值
        //答案肯定落在最大的数组值和1之间。采用二分法
        int left = 1;
        int right = -1;
        for(int number: piles) right = Math.max(right,number);
        while(left<right){
            int mid = (left+right)/2;
            int tmp = calculateHours(mid,piles);
            if(tmp>h) left=mid+1;
            else right=mid;
        }
        return left;
    }

    public int calculateHours(int speed, int[] piles){
        int total = 0;
        for(int number: piles){
            total += number/speed;
            if(number%speed!=0) total+=1;
        }
        return total;
    }
}