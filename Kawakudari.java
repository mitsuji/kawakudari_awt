import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.util.Random;

class Kawakudari extends Canvas implements KeyListener {

  private Std15 std15;
  private int frame;
  private Random rnd;
  private int x;
  private boolean running;

  private void setup() {
    std15 = new Std15(512,384,32,24,this);
    frame = 0;
    rnd = new Random();
    x = 15;
    running = true;
  }

  private void update() {
    if (!running) return;
    if (frame % 5 == 0) {
      std15.locate(x,5);
      std15.putc('0');
      std15.locate(rnd.nextInt(32),23);
      std15.putc('*');
      std15.scroll(Std15.UP);
      if (std15.scr(x,5) != '\0') {
        std15.locate(0,23);
        std15.putstr("Game Over...");
        std15.putnum(frame);
        running = false;
      }
    }
  }

  @Override
  public void update(Graphics g) {
    std15.drawScreen(g);
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_LEFT)  x--;
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) x++;
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  public static void main(String args[]) {
    Kawakudari game = new Kawakudari();
    game.addKeyListener(game);
    Frame frame = new Frame();
    frame.setSize(512+40,384+60);
    frame.setTitle("Kawakudari");
    frame.add(game);
    frame.addKeyListener(game);
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    frame.setVisible(true);
    game.run();
  }

  public void run () {
    setup();
    try {
      while (true) {
        update();
        repaint();
        frame++;
        Thread.sleep(16);
      }
    } catch (InterruptedException ex) {
      // Do Nothing
    }
  }

}
