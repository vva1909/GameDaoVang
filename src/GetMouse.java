import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GetMouse implements MouseListener {
    GamePanel gp;
    public static int mouseX, mouseY;
    public GetMouse(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        System.out.println(mouseX + " " + mouseY);
        if (mouseX >= 180 && mouseX <= 250 && mouseY >= 610 && mouseY <= 635) {
            gp.initGame();
            gp.inGame = true;
            gp.gold_count = 0;
            gp.weight_count = 0;
        }

        if (mouseX >= 310 && mouseX <= 390 && mouseY >= 610 && mouseY <= 635) {
            gp.play_again = true;
            gp.initGame();
            gp.inGame = true;
            gp.play_again = false;
            gp.gold_count = 0;
            gp.weight_count = 0;
        }

        if (mouseX >= 490 && mouseX <= 530 && mouseY >= 610 && mouseY <= 635) {
            gp.hint_show = !gp.hint_show;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
