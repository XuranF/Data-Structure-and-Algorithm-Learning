/*
LC97 Interleaving String
*/

class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        int n = s1.length();
        int m = s2.length();
        if(s3.length()!=n+m) return false;
        boolean[][] dp = new boolean[n+1][m+1];
        //transfer function is dp[i][j] = dp[i-1][j] && s3.charAt(i+j-1)==s1.charAt(i-1) || dp[i][j-1] && s3.charAt(i+j-1)==s2.charAt(j-1)
        //边界条件容易晕
        dp[0][0] = true;
        for(int i=1;i<=n;i++){
            if(s1.charAt(i-1)!=s3.charAt(i-1)) break;
            if(s1.charAt(i-1)==s3.charAt(i-1)) dp[i][0]=true;
        }
        for(int i=1;i<=m;i++){
            if(s2.charAt(i-1)!=s3.charAt(i-1)) break;
            if(s2.charAt(i-1)==s3.charAt(i-1)) dp[0][i]=true;
        }
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                dp[i][j] = (dp[i-1][j] && s3.charAt(i+j-1)==s1.charAt(i-1)) || (dp[i][j-1] && s3.charAt(i+j-1)==s2.charAt(j-1));
            }
        }
        return dp[n][m];
    }
}