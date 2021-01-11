import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.*;


public class Lab30st {
    public static void main(String args[]) {
        int numOfCircles = Integer.parseInt(JOptionPane.showInputDialog("How many circles do you want on screen?"));
        int circleDiameter = Integer.parseInt(JOptionPane.showInputDialog("What is the diameter"));
        GfxApp gfx = new GfxApp(numOfCircles,circleDiameter);
        gfx.setSize(800, 600);
        gfx.addWindowListener(new WindowAdapter() {
            public void
            windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        gfx.show();
    }
}

class Coord {
    private int xLoc;
    private int yLoc;

    public Coord(int x, int y) {
        xLoc = x;
        yLoc = y;
    }
}

class GfxApp extends Frame {
    private Queue<Circle> holdCircles = new LinkedList<>();
    private int circleCount, circleSize;

    public GfxApp(int numOfCircles,int diameter) {
        circleCount = numOfCircles;
        circleSize = diameter;
    }


    class Coord {
        private int xPos;
        private int yPos;

        public Coord(int x, int y) {
            xPos = x;
            yPos = y;
        }
    }

    public void paint(Graphics g) {
        int incX = 5;
        int incY = 5;
        int diameter = 30;
        int timeDelay = 10;
        Circle c = new Circle(g, diameter, incX, incY, timeDelay,circleSize);
        while (true) {
           /* for (int k = 1; k <= 50; k++) {
                c.drawCircle(g);
                c.hitEdge();
            }*/
            Circle tempCircle = new Circle(g,diameter,incX,incY,timeDelay,circleSize);
            tempCircle.setTlX(c.getTlX());
            tempCircle.setTlY(c.getTlY());
            holdCircles.add(tempCircle);
            if(holdCircles.size() > circleCount){
                Circle circleToRemove = holdCircles.remove();
                circleToRemove.eraseCircle(circleToRemove.getTlX(),circleToRemove.getTlY());
            }
            c.drawCircle(g);
            c.hitEdge();
        }
    }
}


class Circle extends JFrame {
    private int tlX;        // top-left X coordinate
    private int tlY;        // top-left Y coordinate
    int incX;        // increment movement of X coordinate
    int incY;        // increment movement of Y coordinate
    private boolean addX;    // flag to determine add/subtract of increment for X
    private boolean addY;    // flag to determine add/subtract of increment for Y
    private int size;        // diameter of the circle
    private int timeDelay;    // time delay until next circle is drawn
    private Graphics g;


    public Circle(Graphics g, int s, int x, int y, int td,int circleDiameter) {
        incX = x;
        incY = y;
        size = s;
        addX = true;
        addY = false;
        tlX = 400;
        tlY = 300;
        timeDelay = td;
        size = circleDiameter;
        this.g = g;
    }

    public void delay(int n) {
        long startDelay = System.currentTimeMillis();
        long endDelay = 0;
        while (endDelay - startDelay < n)
            endDelay = System.currentTimeMillis();
    }

    public void eraseCircle(int x, int y) {
        g.clearRect(x, y, size+5, size+5);
    }

    public int getTlX() {
        return tlX;
    }

    public int getTlY() {
        return tlY;
    }

    public void setTlX(int x){
        tlX = x;
    }

    public void setTlY(int y){
        tlY = y;
    }
    public void drawCircle(Graphics g) {
        g.setColor(Color.blue);
        g.drawOval(tlX, tlY, size, size);
        delay(timeDelay);
        if (addX)
            tlX += incX;
        else
            tlX -= incX;
        if (addY)
            tlY += incY;
        else
            tlY -= incY;
    }


    public void newData() {
        incX = (int) Math.round(Math.random() * 7 + 5);
        incY = (int) Math.round(Math.random() * 7 + 5);
    }

    public void hitEdge() {
        boolean flag = false;
        if (tlX < incX) {
            addX = true;
            flag = true;
        }
        if (tlX > 800 - (30 + incX)) {
            addX = false;
            flag = true;
        }
        if (tlY < incY + 30)  // The +30 is due to the fact that the title bar covers the top 30 pixels of the window
        {
            addY = true;
            flag = true;
        }
        if (tlY > 600 - (30 + incY)) {
            addY = false;
            flag = true;
        }
        if (flag)
            newData();
    }

}
