import javax.swing.plaf.IconUIResource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import static java.lang.Math.min;

public class Player {
    int step;
    GamePanel gp;
    int x, y;
    int n;
    int[][] gold;
    int[][] dp;
    int[][] sol;
    public Player(GamePanel gp) {
        this.gp = gp;
        x = 0;
        y = 0;

        n = gp.B_HEIGHT / gp.DOT_SIZE;
        gold = new int [n][n];
        dp = new int [n][n];
        sol = new int [n][n];
        step = 2 * n - 1;

        Random random = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                gold[i][j] = 0;
                int exist_gold = random.nextInt(3);
                if (exist_gold > 0)
                    gold[i][j] = random.nextInt(15) + 5;
                dp[i][j] = 2000000000;
                sol[i][j] = 0;
            }
        }
        gold[0][0] = 0;
        gold[n - 1][n - 1] = 0;

        FindSolution();
    }

    public void checkCollision() {
        gp.gold_count += gold[x/gp.DOT_SIZE][y/gp.DOT_SIZE];
        gold[x/gp.DOT_SIZE][y/gp.DOT_SIZE] = 0;
        sol[x/gp.DOT_SIZE][y/gp.DOT_SIZE] = 0;
        if (x == gp.B_WIDTH - gp.DOT_SIZE && y == gp.B_HEIGHT - gp.DOT_SIZE) {
            if (step < 0) {
                gp.game_result = "You're too slow!!!";
            } else if (gp.gold_count != dp[n-1][n-1]) {
                gp.game_result = "You're too greedy!!!";
            } else {
                gp.game_result = "Congratulation!!!";
            }
            gp.inGame = false;
        }
    }

    public void FindSolution() {


        String filePath = "output.txt";
        String content = "";

        dp[0][0] = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i > 0)
                    dp[i][j] = min(dp[i - 1][j] + gold[i][j], dp[i][j]);
                if (j > 0)
                    dp[i][j] = min(dp[i][j - 1] + gold[i][j], dp[i][j]);

                for (int i1 = 0; i1 < n; i1++) {
                    for (int j1 = 0; j1 < n; j1++) {
                        content = content + " " + dp[j1][i1];
                    }
                    content = content + "\n";
                }
                content = content + "\n";
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
            System.out.println("File written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        int i = n - 1, j = n - 1;
        while (!(i == 0 && j == 0)) {
            sol[i][j] = 1;
            if (i > 0)
                if (dp[i][j] == dp[i - 1][j] + gold[i][j]) {
                    i--;
                    sol[i][j] = 1;
                }
            if (j > 0)
                if (dp[i][j] == dp[i][j - 1] + gold[i][j]) {
                    j--;
                    sol[i][j] = 1;
                }
        }
    }
}
