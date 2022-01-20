import java.awt.GraphicsEnvironment;

public class FramerateHandler {

    public static boolean RUNNING = true;
    // UPS = updates per second should be the same for everyone
    public final static int UPS = 60;
    // FPS = frames per second depends on the monitor refresh rate
    public static int FPS;
    public int frames, updates;
    public long lastTime, timer;
    public final double ns;
    double delta;

    public FramerateHandler() {
        // fetch refreshrate from monitor-
        FPS = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getRefreshRate();
        ns = 1000000000d / (double) this.UPS;
        timer = System.currentTimeMillis();
        lastTime = System.nanoTime();
        // messure the actual fps count
        frames = 0;
        // messure the actual ups count
        updates = 0;
        // time difference between the current and last frame
        delta = 0;
    }

    public void run() {
        while (RUNNING) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) { // updates get processed here
                Main.draw.updateCubes();
                updates++;
                delta--;
            }
            
            // drawing gets processed here
            Main.draw.redraw();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                System.out.println(updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
                timer = System.currentTimeMillis();
            }
            
            // Should sleep UPS times
            sleep(1000 / FPS);
        }
    }

    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }
}
