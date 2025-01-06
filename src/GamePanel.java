import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener {
    final int B_WIDTH = 800;
    final int B_HEIGHT = 800;
    final int DOT_SIZE = 100;
    int gold_count = 0, weight_count = 0;
    int DELAY = 150;
    boolean inGame = false, hint_show = false, play_again = false;
    Timer timer;
    String game_result;
    Image player_image, gold_image, back_ground, bg_1, finish_image, hint_image;
    Player player;
    public GamePanel(){
        addKeyListener(new GetKey(this));
        addMouseListener(new GetMouse(this));
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT + 50));
        loadImages();
        initGame();
    }

    private void loadImages() {
        ImageIcon ii = new ImageIcon("bg.png");
        back_ground = ii.getImage();

        ii = new ImageIcon("player_image.png");
        player_image = ii.getImage();

        ii = new ImageIcon("bg_1.png");
        bg_1 = ii.getImage();

        ii = new ImageIcon("gold_image.png");
        gold_image = ii.getImage();

        ii = new ImageIcon("finish.png");
        finish_image = ii.getImage();

        ii = new ImageIcon("hint.png");
        hint_image = ii.getImage();
    }

    public void initGame() {
        gold_count = 0;
        inGame = true;
        hint_show = false;
        if (!play_again)
            player = new Player(this);
        else {
            player.x = 0;
            player.y = 0;
            for (int i = 0; i < player.n; i++) {
                for (int j = 0; j < player.n; j++) {
                    player.eaten[i][j] = 0;
                }
            }
        }
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        g.drawImage(bg_1, 0, 0, B_WIDTH, B_HEIGHT+50, this);
        g.drawImage(back_ground, 0, 0, B_WIDTH, B_HEIGHT, this);

        Font small = new Font("Helvetica", Font.BOLD, 18);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.black);
        g.setFont(small);
        if (inGame) {
            int cnt = 1;
            for (int i = 0; i < player.n; i++) {
                for (int j = 0; j < player.n; j++) {
                    g.drawImage(back_ground, i * DOT_SIZE, j * DOT_SIZE, DOT_SIZE, DOT_SIZE, this);
                    if (player.eaten[i][j] == 0 && player.gold[i][j] > 0) {
                        g.drawImage(gold_image, i * DOT_SIZE + 10, j * DOT_SIZE + 10, DOT_SIZE - 15, DOT_SIZE - 15, this);
                        g.setColor(Color.black);
                        g.drawString(player.weight[i][j]+" kg", i * DOT_SIZE + 28, j * DOT_SIZE + 40);
                        g.setColor(Color.RED);
                        g.drawString(player.gold[i][j]+" $", i * DOT_SIZE + 28, j * DOT_SIZE + 65);
                        g.setColor(Color.black);
                        if (hint_show && player.sol[i][j] == 1 && cnt == 1) {
                            g.drawImage(hint_image, i * DOT_SIZE, j * DOT_SIZE, DOT_SIZE, DOT_SIZE, this);
                            cnt = 0;
                        }
                    }
                }
            }
            g.drawImage(finish_image, B_WIDTH - DOT_SIZE, B_HEIGHT - DOT_SIZE, DOT_SIZE, DOT_SIZE, this);
            g.drawImage(player_image, player.x, player.y, DOT_SIZE, DOT_SIZE, this);

            g.setColor(Color.YELLOW);
            String msg = "Gold: " + gold_count + " $ / " + player.result + " $" ;
            g.drawString(msg, 10, B_HEIGHT + 20);
            msg = "Weight: " + weight_count + " / 20 kg";
            g.drawString(msg, 10, B_HEIGHT + 41);
            Toolkit.getDefaultToolkit().sync();

        } else {
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString(game_result, getXTextCenter(game_result, g), 400);
        }

        g.setColor(Color.YELLOW);
        g.setFont(new Font("NewellsHand", Font.BOLD, 25));

        String play = "New Game";
        g.drawString(play, 230, B_HEIGHT + 30);

        String play_again = "Play Again";
        g.drawString(play_again, 450, B_HEIGHT + 30);

        String show_hint = "Hint";
        g.drawString(show_hint, 700, B_HEIGHT + 30);


    }
    public int  getXTextCenter(String text, Graphics g)
    {
        FontMetrics metrics = g.getFontMetrics();
        int x = (B_WIDTH - metrics.stringWidth(text)) / 2;
        return x;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            player.checkCollision();
        }
        repaint();
    }
}
