

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GetKey extends KeyAdapter {
    GamePanel gp;
    public GetKey(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("key" + gp.player.x + " " + gp.player.y);
        int key = e.getKeyCode();
        boolean move = false;
        if (key == KeyEvent.VK_LEFT) {
            if (gp.player.x > 0) {
                gp.player.x -= gp.DOT_SIZE;
                --gp.player.step;
            }
        }

        if (key == KeyEvent.VK_RIGHT) {
            if (gp.player.x < gp.B_WIDTH - gp.DOT_SIZE) {
                gp.player.x += gp.DOT_SIZE;
                --gp.player.step;
            }
        }

        if (key == KeyEvent.VK_UP) {
            if (gp.player.y > 0) {
                gp.player.y -= gp.DOT_SIZE;
                --gp.player.step;
            }
        }

        if (key == KeyEvent.VK_DOWN){
            if (gp.player.y < gp.B_HEIGHT - gp.DOT_SIZE) {
                gp.player.y += gp.DOT_SIZE;
                --gp.player.step;
            }
        }
    }
}
