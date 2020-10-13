# kawakudari-awt

This project implements part of the [std15.h](https://github.com/IchigoJam/c4ij/blob/master/src/std15.h) API (from [c4ij](https://github.com/IchigoJam/c4ij)) with [AWT](https://docs.oracle.com/javase/8/docs/technotes/guides/awt/), and [Kawakudari Game](https://ichigojam.github.io/print/en/KAWAKUDARI.html) on top of it.

It will allow programming for [IchigoJam](https://ichigojam.net/index-en.html)-like targets that display [IchigoJam FONT](https://mitsuji.github.io/ichigojam-font.json/) on screen using a Java programming language.
```
  public void setup() {
    buffImage = createImage(512,384);
    std15 = new Std15(512,384,32,24);
    rnd = new Random();
    frame = 0;
    x = 15;
    running = true;
  }

  public void update() {
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
    std15.drawScreen(buffImage.getGraphics());
    g.drawImage(buffImage,0,0,this);
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_LEFT)  x--;
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) x++;
  }


```

## Prerequisite

* [Download](https://www.oracle.com/java/technologies/javase-downloads.html) and install JDK suitable for your environment.

## How to use

To build it
```
$ javac Kawakudari.java Std15.java
```

To run it
```
$ java Kawakudari
```


## License
[![Creative Commons License](https://i.creativecommons.org/l/by/4.0/88x31.png)](http://creativecommons.org/licenses/by/4.0/)
[CC BY](https://creativecommons.org/licenses/by/4.0/) [mitsuji.org](https://mitsuji.org)

This work is licensed under a [Creative Commons Attribution 4.0 International License](http://creativecommons.org/licenses/by/4.0/).
