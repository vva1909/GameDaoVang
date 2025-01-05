import javax.swing.plaf.IconUIResource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import static java.lang.Math.min;
import static sun.swing.MenuItemLayoutHelper.max;

public class Player {
    int weight_limit;
    GamePanel gp;
    int x, y;
    int n;
    int[][] gold;
    int[][] weight;
    int[][] dp;
    int result;
    int[][] sol;
    public Player(GamePanel gp) {
        this.gp = gp;
        x = 0;
        y = 0;

        n = gp.B_HEIGHT / gp.DOT_SIZE;
        gold = new int [n][n];
        weight = new int[n][n];
        dp = new int [25*25][25];
        sol = new int [n][n];
        weight_limit = 20;

        Random random = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                gold[i][j] = weight[i][j] = 0;
                int exist_gold = random.nextInt(2);
                if (exist_gold > 0) {
                    gold[i][j] = random.nextInt(15) + 5;
                    weight[i][j] = random.nextInt(5) + 1;
                }
                sol[i][j] = 0;
            }
        }
        gold[0][0] = weight[0][0] = 0;
        gold[n - 1][n - 1] = weight[n - 1][n - 1] = 0;

        FindSolution();
    }

    public void checkCollision() {
        if (x == gp.B_WIDTH - gp.DOT_SIZE && y == gp.B_HEIGHT - gp.DOT_SIZE) {
            if (gp.weight_count > weight_limit || gp.gold_count != result) {
                gp.game_result = "Good luck next time!!!";
            } else {
                gp.game_result = "Congratulation!!!";
            }
            gp.inGame = false;
        }
    }

    public void mine_gold(){
        int i = x/gp.DOT_SIZE;
        int j = y/gp.DOT_SIZE;

        if (gold[i][j] == 0) return;
        gp.gold_count += gold[i][j];
        gp.weight_count += weight[i][j];

        gold[i][j] = 0;
        sol[i][j] = 0;
    }

    public void FindSolution() {

        result = 0;

//        String filePath = "output.txt";
//        StringBuilder content = new StringBuilder();
        int m = 0;
        int[] a = new int[n * n];
        int[] b = new int[n * n];
        int[] d = new int[n * n];

        for (int i = 0; i < n * n; i++) {
            for (int j = 0; j <= 20; j++) dp[i][j] = 0;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (gold[i][j] > 0) {
                    a[m] = gold[i][j];
                    b[m] = weight[i][j];
                    d[m] = i * n + j;
                    System.out.println(a[m] + " " + b[m]);
                    m++;
                }
            }
        }

        dp[0][b[0]] = a[0];
        for (int i = 1; i < m; i++) {
            for (int j = b[i]; j <= 20; j++) {
                dp[i][j] = dp[i - 1][j];
                if (dp[i][j] < dp[i - 1][j - b[i]] + a[i]) {
                    dp[i][j] = dp[i - 1][j - b[i]] + a[i];
                }
            }
        }

        int k = 1;
        for (int j = 0; j <= 20; j++) {
            if (dp[m - 1][j] > result) {
                result = dp[m - 1][j];
                k = j;
            }
        }

        for (int i = m - 1; i >= 0; i--) {
            if ((i == 0 && k > 0) || (k >= b[i] && dp[i][k] == dp[i - 1][k - b[i]] + a[i])) {
                int i_r = d[i] / n;
                int j_r = d[i] % n;
                sol[i_r][j_r] = 1;
                k -= b[i];
            }
        }
        System.out.println(result);
    }
}
