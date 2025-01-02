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
        if (mouseX >= 330 && mouseX <= 470 && mouseY >= 800 && mouseY <= 825) {
            gp.initGame();
            gp.inGame = true;
        }
        if (mouseX >= 600 && mouseX <= 650 && mouseY >= 800 && mouseY <= 825) {
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
