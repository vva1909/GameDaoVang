import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Random;
import javax.sound.sampled.AudioSystem;

import static java.lang.Math.max;


public class Player {
    int weight_limit;
    GamePanel gp;
    int x, y;
    int n;
    int[][] gold;
    int[][] weight;
    int[][] dp;
    int result;
    int[][] sol, eaten;
    Clip clip;
    File sound;
    public Player(GamePanel gp) {
        this.gp = gp;
        x = 0;
        y = 0;

        n = gp.B_HEIGHT / gp.DOT_SIZE;
        gold = new int [n][n];
        weight = new int[n][n];

        eaten = new int[n][n];
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
                sol[i][j] = eaten[i][j] = 0;
            }
        }
        gold[0][0] = weight[0][0] = 0;
        gold[n - 1][n - 1] = weight[n - 1][n - 1] = 0;

        sound = new File("coin.wav");
        try {
            this.clip = AudioSystem.getClip();
            this.clip.open(AudioSystem.getAudioInputStream(sound));
        } catch (Exception ignored){}

        FindSolution();
    }

    public void checkCollision() {
        if (x == gp.B_WIDTH - gp.DOT_SIZE && y == gp.B_HEIGHT - gp.DOT_SIZE) {
            if (gp.weight_count > weight_limit) {
                gp.game_result = "You lost everything!!!";
            } else if (gp.gold_count < result) {
                gp.game_result = "You've got " + gp.gold_count + " / " + result+"$";
            } else {
                gp.game_result = "Congratulation!!!";
            }
            gp.inGame = false;
        }
    }

    public void mine_gold(){

        int i = x/gp.DOT_SIZE;
        int j = y/gp.DOT_SIZE;

        if (eaten[i][j] == 1 || gold[i][j] == 0) return;

        try {
            this.clip = AudioSystem.getClip();
            this.clip.open(AudioSystem.getAudioInputStream(sound));
        } catch (Exception ignored){}
        clip.start();

        gp.gold_count += gold[i][j];
        gp.weight_count += weight[i][j];
        eaten[i][j] = 1;
    }

    public void FindSolution() {
        int[] a = new int[n * n];
        int[] b = new int[n * n];
        int[] d = new int[n * n];

        int m = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (gold[i][j] > 0) {
                    a[m] = gold[i][j];
                    b[m] = weight[i][j];
                    d[m] = i * n + j;
                    m++;
                }
            }
        }

        dp = new int[m][weight_limit + 5];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j <= weight_limit; j++) dp[i][j] = 0;
        }

        dp[0][b[0]] = a[0];
        for (int i = 1; i < m; i++) {
            for (int j = 0; j <= weight_limit; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= b[i]) {
                    dp[i][j] = max(dp[i][j], dp[i - 1][j - b[i]] + a[i]);
                }
            }
        }

        result = 0;
        int k = 0;
        for (int j = 0; j <= weight_limit; j++) {
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
    }
}
