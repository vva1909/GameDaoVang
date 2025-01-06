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
        if (mouseX >= 450 && mouseX <= 572 && mouseY >= 810 && mouseY <= 835) {
            gp.play_again = true;
            gp.initGame();
            gp.inGame = true;
            gp.play_again = false;
            gp.gold_count = 0;
            gp.weight_count = 0;
        }

        if (mouseX >= 230 && mouseX <= 360 && mouseY >= 810 && mouseY <= 835) {
            gp.initGame();
            gp.inGame = true;
            gp.gold_count = 0;
            gp.weight_count = 0;
        }

        if (mouseX >= 700 && mouseX <= 750 && mouseY >= 810 && mouseY <= 835) {
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
