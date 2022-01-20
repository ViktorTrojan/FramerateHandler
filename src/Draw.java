
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JLabel;

class Cube {

    int vel, x, y, size;

    public Cube(int x, int y, int size, int vel) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.vel = vel;
    }

    void update() {
        // if the cube hits the border switch velocity
        if (x + size > Draw.WIDTH || x < 0) {
            vel = -vel;
        }

        // add velocity to position to make it move
        x += vel;
    }
}

public class Draw extends JLabel {

    public static int WIDTH = 400, HEIGHT = WIDTH;
    public int cubeAmount = 10;
    public ArrayList<Cube> cubes;

    public Draw() {
        cubes = new ArrayList<>();

        for (int i = 0; i < cubeAmount; i++) {
            int it = HEIGHT / cubeAmount;
            int size = it / 2;
            cubes.add(new Cube(0, i * it + size / 2, size, i + 1));
        }
    }

    // updateCubes gets called in FramerateHandler
    public void updateCubes() {
        for (Cube cube : cubes) {
            cube.update();
        }
    }

    // redraw gets called in FramerateHandler
    public void redraw() {
        repaint();
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2.setColor(new Color(255, 255, 255));
        g2.fillRect(0, 0, WIDTH, HEIGHT);

        // Draw Cubes
        g2.setColor(new Color(255, 0, 0));
        for (Cube cube : cubes) {
            g2.fillRect(cube.x, cube.y, cube.size, cube.size);
        }
    }
}
