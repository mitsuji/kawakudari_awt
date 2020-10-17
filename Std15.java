import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;


class Std15 {

  public static final int UP = 0;
  public static final int RIGHT = 1;
  public static final int DOWN = 2;
  public static final int LEFT = 3;

  private static final int CHAR_W = 8;
  private static final int CHAR_H = 8;

  private float screenW;
  private float screenH;
  private int buffW;
  private int buffH;
  private char [] buff;
  private float dotW;
  private float dotH;
  private int cursorX;
  private int cursorY;

  private Canvas canvas;
  private Image buffImage;

  Std15(int screenW, int screenH, int buffW, int buffH, Canvas canvas) {
    this.screenW = screenW;
    this.screenH = screenH;
    this.buffW = buffW;
    this.buffH = buffH;
    buff = new char[buffW * buffH];
    dotW = screenW / buffW / CHAR_W;
    dotH = screenH / buffH / CHAR_H;
    dotH = screenH / buffH / CHAR_H;
    this.canvas = canvas;
    this.buffImage = canvas.createImage(screenW,screenH);
    cls();
  }

  public void locate (int x, int y) {
    cursorX = x;
    cursorY = y;
  }

  public void putc(char c) {
    setChar(cursorX, cursorY, c);
    if(cursorX < buffW-1) {
      cursorX ++;
    } else {
      if(cursorY < buffH-1) {
        cursorX = 0;
        cursorY ++;
      }
    }
  }

  public void putstr(String s) {
    for(int i = 0; i < s.length(); i++) {
      putc(s.charAt(i));
    }
  }

  public void putnum(int n) {
    putstr(String.valueOf(n));
  }

  public char scr(int x, int y) {
    return buff [y*buffW+x];
  }

  public void cls () {
    for (int y = 0; y < buffH; y++) {
      for (int x = 0; x < buffW; x++) {
        setChar(x,y,'\0');
      }
    }
  }

  public void scroll(int dir) {
    for (int y = 0; y < buffH; y++) {
      for (int x = 0; x < buffW; x++ ) {
        switch(dir) {
          case UP: {
          if (y == buffH-1) {
            setChar(x,y,'\0');
          } else {
            setChar(x,y,scr(x,y+1));
          }
          }
          break;
          case RIGHT: {
          if (x == buffW-1) {
            setChar(buffW-x-1,y,'\0');
          } else {
            setChar(buffW-x-1,y,scr((buffW-x-1)-1,y));
          }
          }
          break;
          case DOWN: {
          if (y == buffH-1) {
            setChar(x,(buffH-y-1),'\0');
          } else {
            setChar(x,(buffH-y-1),scr(x,(buffH-y-1)-1));
          }
          }
          break;
          case LEFT: {
          if (x == buffW-1) {
            setChar(x,y,'\0');
          } else {
            setChar(x,y,scr(x+1,y));
          }
          }
          break;
        }
      }
    }
  }

  public void pset(int x, int y) {
    int cx = x / 2;
    int cy = y / 2;
    char c = scr(cx,cy);
    char b = (char)Math.floor(Math.pow(2, ((y % 2) << 1) + (x % 2)));
    char d = (char)(((c & 0xf0) == 0x80 ? c : 0x80) | b);
    setChar(cx, cy, d);
  }

  private void setChar(int x, int y, char c) {
    buff [y*buffW+x] = c;
  }

  private void drawChar(Graphics g, int x, int y, char c) {
    long glyph = ICHIGOJAM_FONT[c];
    for (int cy = 0; cy < CHAR_H; cy++) {
      long line = (glyph >> ((CHAR_H-cy-1)*CHAR_W)) & 0xff;
      for (int cx= 0; cx < CHAR_W; cx++) {
        if (((line >> (CHAR_W-cx-1)) & 0x1) == 0x1) {
          g.setColor(Color.WHITE);
          int x0 = (x*CHAR_W+cx)*((int)dotW);
          int y0 = (y*CHAR_H+cy)*((int)dotH);
          g.fillRect(x0,y0,(int)dotW,(int)dotH);
        }
      }
    }
  }

  public void drawScreen(Graphics g) {
    Graphics bg = buffImage.getGraphics();
    bg.setColor(Color.BLACK);
    bg.fillRect(0,0,(int)screenW,(int)screenH);
    for (int y = 0; y < buffH; y++) {
      for (int x = 0; x < buffW; x++) {
        drawChar(bg, x, y, scr(x,y));
      }
    }
    g.drawImage(buffImage,0,0,canvas);
  }


/**
 *
 *  CC BY IchigoJam & mitsuji.org
 *  https://mitsuji.github.io/ichigojam-font.json/
 *
 */
  private final long [] ICHIGOJAM_FONT = {
    0x0000000000000000L,
    0xffffffffffffffffL,
    0xffaaff55ffaaff55L,
    0x55aa55aa55aa55aaL,
    0x005500aa005500aaL,
    0x995a3c5a5a242466L,
    0xfbfbfb00dfdfdf00L,
    0x24182424183c6624L,
    0x0a042a40fe402000L,
    0x000000000000ee00L,
    0x00042464fc602000L,
    0xeebaee447c447c44L,
    0x1042008001004208L,
    0x007e7e7e7e7e7e00L,
    0x007e424242427e00L,
    0x007e5e5e5e427e00L,
    0x007e7a7a6a427e00L,
    0x003c242424243c00L,
    0xc0c0c0c0c0c0c0c0L,
    0xffff000000000000L,
    0x000000000000ffffL,
    0x003c3c4242423c00L,
    0x003c665e5e663c00L,
    0x0303030303030303L,
    0x0000ff0000ff0000L,
    0x03070e1c3870e0c0L,
    0xc0e070381c0e0703L,
    0x606c34f018284e40L,
    0x102040fe40201000L,
    0x100804fe04081000L,
    0x1038549210101000L,
    0x1010109254381000L,
    0x0000000000000000L,
    0x1010101010001000L,
    0x2828000000000000L,
    0x28287c287c282800L,
    0x103c503814781000L,
    0x60640810204c0c00L,
    0x2050502054483400L,
    0x0810200000000000L,
    0x0810202020100800L,
    0x2010080808102000L,
    0x1054381038541000L,
    0x0010107c10100000L,
    0x0000000010102000L,
    0x0000007c00000000L,
    0x0000000000303000L,
    0x0000040810204000L,
    0x38444c5464443800L,
    0x1030501010107c00L,
    0x3844040418607c00L,
    0x3844041804443800L,
    0x18284848487c0800L,
    0x7c40780404443800L,
    0x3840784444443800L,
    0x7c44040808101000L,
    0x3844443844443800L,
    0x384444443c043800L,
    0x0000100000100000L,
    0x0000100010102000L,
    0x0810204020100800L,
    0x00007c007c000000L,
    0x2010080408102000L,
    0x3844440810001000L,
    0x3844043454543800L,
    0x384444447c444400L,
    0x7824243824247800L,
    0x3844404040443800L,
    0x7824242424247800L,
    0x7c40407c40407c00L,
    0x7c40407c40404000L,
    0x384440404c443c00L,
    0x4444447c44444400L,
    0x3810101010103800L,
    0x1c08080808483000L,
    0x4448506050484400L,
    0x4040404040407c00L,
    0x446c6c5454544400L,
    0x446464544c4c4400L,
    0x3844444444443800L,
    0x7844444478404000L,
    0x3844444454483400L,
    0x7844444478484400L,
    0x3844403804443800L,
    0x7c10101010101000L,
    0x4444444444443800L,
    0x4444282828101000L,
    0x4444545454282800L,
    0x4444281028444400L,
    0x4444281010101000L,
    0x7c04081020407c00L,
    0x3820202020203800L,
    0x0000402010080400L,
    0x3808080808083800L,
    0x1028440000000000L,
    0x0000000000007c00L,
    0x2010080000000000L,
    0x000038043c443a00L,
    0x4040586444447800L,
    0x0000384440443800L,
    0x0404344c44443c00L,
    0x000038447c403800L,
    0x1820207c20202000L,
    0x00003a44443c0438L,
    0x4040586444444400L,
    0x1000301010101000L,
    0x0800180808080830L,
    0x2020242830282400L,
    0x3010101010101800L,
    0x0000785454545400L,
    0x0000784444444400L,
    0x0000384444443800L,
    0x0000384444784040L,
    0x00003844443c0404L,
    0x0000586440404000L,
    0x00003c4038047800L,
    0x20207c2020201800L,
    0x0000484848483400L,
    0x0000444428281000L,
    0x0000445454282800L,
    0x0000442810284400L,
    0x0000444428281060L,
    0x00007c0810207c00L,
    0x0c10102010100c00L,
    0x1010101010101000L,
    0x6010100810106000L,
    0x0000205408000000L,
    0xa040a804fe040800L,
    0x0000000000000000L,
    0xf0f0f0f000000000L,
    0x0f0f0f0f00000000L,
    0xffffffff00000000L,
    0x00000000f0f0f0f0L,
    0xf0f0f0f0f0f0f0f0L,
    0x0f0f0f0ff0f0f0f0L,
    0xfffffffff0f0f0f0L,
    0x000000000f0f0f0fL,
    0xf0f0f0f00f0f0f0fL,
    0x0f0f0f0f0f0f0f0fL,
    0xffffffff0f0f0f0fL,
    0x00000000ffffffffL,
    0xf0f0f0f0ffffffffL,
    0x0f0f0f0fffffffffL,
    0xffffffffffffffffL,
    0x0000001818000000L,
    0x000000ffff000000L,
    0x1818181818181818L,
    0x181818ffff181818L,
    0x181818f8f8181818L,
    0x1818181f1f181818L,
    0x181818ffff000000L,
    0x000000ffff181818L,
    0x0000000f1f181818L,
    0x000000f0f8181818L,
    0x1818181f0f000000L,
    0x181818f8f0000000L,
    0xfffefcf8f0e0c080L,
    0xff7f3f1f0f070301L,
    0x80c0e0f0f8fcfeffL,
    0x0103070f1f3f7fffL,
    0x44287c107c101000L,
    0x0000000070507000L,
    0x0e08080000000000L,
    0x0000000010107000L,
    0x0000000040201000L,
    0x0000001818000000L,
    0x007e027e02041800L,
    0x0000007c14102000L,
    0x0000000c70101000L,
    0x0000107c44041800L,
    0x0000007c10107c00L,
    0x0000087c18284800L,
    0x0000207c24202000L,
    0x0000003808087c00L,
    0x00003c043c043c00L,
    0x0000005454040800L,
    0x000000007e000000L,
    0x00fe021410106000L,
    0x0006186808080800L,
    0x107e424202041800L,
    0x007c10101010fe00L,
    0x04047e0c14244400L,
    0x10107e1212224600L,
    0x10107e107e101000L,
    0x003e224202043800L,
    0x20203e4404043800L,
    0x00007e0202027e00L,
    0x0044fe4444043800L,
    0x0070027202047800L,
    0x007e020408146200L,
    0x0040fe4448403e00L,
    0x0042422404081000L,
    0x003e22520a043800L,
    0x043808fe08083000L,
    0x0052525202041800L,
    0x007c00fe08087000L,
    0x404040704c404000L,
    0x0008fe0808087000L,
    0x00007c000000fe00L,
    0x007e023408146200L,
    0x107e020418761000L,
    0x0002020202047800L,
    0x0028284444828200L,
    0x00404e7040403e00L,
    0x007e020202043800L,
    0x0000205088040200L,
    0x0010fe1054549200L,
    0x00fe024428100800L,
    0x00700e700e700e00L,
    0x001010202442fe00L,
    0x0002221408146200L,
    0x007c20fe20201e00L,
    0x2020fe2224202000L,
    0x00003c0404047e00L,
    0x007c047c04047c00L,
    0x007e007e02043800L,
    0x0044444404083000L,
    0x0050505052949800L,
    0x0020202224283000L,
    0x007e424242427e00L,
    0x007e424202043800L,
    0x0040220202047800L,
    0x1048200000000000L,
    0x7050700000000000L,
    0x183878ffff783818L,
    0x181c1effff1e1c18L,
    0x183c7effff181818L,
    0x181818ffff7e3c18L,
    0x10387cfefe387c00L,
    0x006cfefe7c381000L,
    0x3838d6fed6103800L,
    0x10387cfe7c381000L,
    0x3c66c38181c3663cL,
    0x3c7effffffff7e3cL,
    0x246a2a2a2a2a2400L,
    0x18244281bdbdbd7eL,
    0x245a4281a581423cL,
    0x3c4281a5817e2466L,
    0x0c0a0a0878f87000L,
    0x3c4299a5ada1924cL,
    0x181824247eff3c7eL,
    0x00182442ff540000L,
    0x1010080810100808L,
    0x7c101eb9ff9f107eL,
    0x085a6cfe3c7e4a11L,
    0x1c363a3a3a3e1c00L,
    0x003c427e5a427e00L,
    0x0006061e1e7e7e00L,
    0x007c446464447c00L,
    0x18183c5a5a242466L,
    0x00187e99183c2466L,
    0x00181a7e501c1466L,
    0x1818101010101018L,
    0x0018587e0a182e62L,
    0x1818080808080818L,
    0x043e2f566ad6acf0L
  };
}
