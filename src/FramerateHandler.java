
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;

public class FramerateHandler {

    public final static int UPS = 60;  // UPS = updates per second should be the same for everyone
    public static int FPS; // FPS = frames per second depends on the monitor refresh rate
    public int frames, updates;
    public long lastTime, timer;
    public final double ns;
    double delta;

    public FramerateHandler() {
        // fetch refreshrate from monitor
        FPS = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getRefreshRate();
        ns = 1000000000d / (double) this.UPS;
        timer = System.currentTimeMillis();
        lastTime = System.nanoTime();

        frames = 0;
        updates = 0;
        delta = 0;
    }

    // PUT YOUR UPDATE LOGIC HERE !
    private void update() {
        Main.instance.draw.updateCubes();
    }

    // PUT YOUR RENDER LOGIC HERE !
    private void render(Graphics g) {
        // Background
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, Draw.WIDTH, Draw.HEIGHT);

        // Draw Cubes
        g.setColor(new Color(255, 0, 0));
        for (Cube cube : Main.instance.draw.cubes) {
            g.fillRect(cube.x, cube.y, cube.size, cube.size);
        }
    }

    public void run(Graphics g) {
        long now = System.nanoTime();
        delta += (now - lastTime) / ns;
        lastTime = now;

        long dur = System.nanoTime();
        while (delta >= 1) { // updates get processed here
            update();
            updates++;
            delta--;
        }

        render(g);

        int latency = (int) ((System.nanoTime() - dur) / (1000000d));
        sleep(1000 / FPS - latency);

        frames++;
        if (System.currentTimeMillis() - timer > 1000) {
            System.out.println(updates + " ups, " + frames + " fps" + " latency: " + latency + "ms");
            updates = 0;
            frames = 0;
            timer = System.currentTimeMillis();
        }
    }

    public void sleep(int ms) {
        if (ms < 0) ms = 0;
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // restore interrupted thread
            Thread.currentThread().interrupt();
        }
    }
}