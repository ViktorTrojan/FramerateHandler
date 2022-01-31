
import java.awt.Dimension;
import javax.swing.JFrame;

public class Main {

    public static Main instance;

    public FramerateHandler fpsHandler;

    public JFrame jf;
    public Draw draw;

    public Main() {
        instance = this;
        fpsHandler = new FramerateHandler();
        createFrame("Framerate Handler example");
    }

    public void createFrame(String title) {
        jf = new JFrame(title);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        draw = new Draw();

        draw.setPreferredSize(new Dimension(Draw.WIDTH, Draw.HEIGHT));
        draw.setVisible(true);
        jf.getContentPane().add(draw); // add draw to jf
        jf.pack(); // size the frame

        jf.setLocationRelativeTo(null);
        jf.requestFocus();
        jf.setVisible(true);
    }

    public static void main(String[] args) {
        Main m = new Main();
    }
}