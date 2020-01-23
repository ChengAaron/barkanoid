import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.MouseInfo;

public class Game4 extends JFrame implements ActionListener{
	Timer myTimer;
	GamePanel game;

    public Game4() {
		super("Move the Ball");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setSize(800,650);

		myTimer = new Timer(10, this);	 // trigger every 10 ms


		game = new GamePanel(this);
		add(game);
		pack();
		//setResizable(false);
		setVisible(true);
    }

	public void start(){
		myTimer.start();
	}

	public void actionPerformed(ActionEvent evt){
		game.move();
		game.repaint();
	}

    public static void main(String[] arguments) {
		Game4 frame = new Game4();
    }
}

class GamePanel extends JPanel implements KeyListener{
	private int boxx,boxy,moveH,moveV,vx,vy;
	private boolean []keys;
	private Image back;
	private Game4 mainFrame;
	private boolean moving;

	public GamePanel(Game4 m){
		keys = new boolean[KeyEvent.KEY_LAST+1];
		back = new ImageIcon("background.png").getImage();

		mainFrame = m;
	    boxx = 170;
        boxy = 170;
        vx = 5; //horizontal movement of ball
        vy = 2; //the angle of the ball
        moveH = vx;
        moveV = vy;
        moving = false;


		setPreferredSize( new Dimension(550, 650));
        addKeyListener(this);
	}

    public void addNotify() {
        super.addNotify();
        setFocusable(true);
        requestFocus();
        mainFrame.start();
    }

	public void move(){
		if(keys[KeyEvent.VK_UP] ){
			moving = true;
		}
		if(moving==true) {
			boxx += moveH;
			boxy += moveV;
			collisions();
		}


		Point mouse = MouseInfo.getPointerInfo().getLocation();
		Point offset = getLocationOnScreen();
		//System.out.println("("+(mouse.x-offset.x)+", "+(mouse.y-offset.y)+")");
		System.out.println("("+(boxx)+", "+(boxy)+")");
	}

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void paint(Graphics g){
    	g.drawImage(back,0,0,null);
		g.setColor(Color.red);
		g.fillOval(boxx,boxy,10,10);
    }

    public void collisions() {
    	if(boxx == getWidth()-10) {
    		moveH = -5;
    		if(moveV > 0) {
    			moveV = vy;
    		} else {
    			moveV = vy*-1;
    		}
    	}

    	if(boxx == 0) {
    		moveH = 5;
			if(moveV > 0) {
    			moveV = vy;
    		} else {
    			moveV = vy*-1;
    		}
    	}

    	if(boxy == getHeight()-10) {
    		moveV = -2;
    		if(moveH > 0) {
    			moveH = vx;
    		} else {
    			moveH = vx*-1;
    		}
    	}

    	if(boxy == 0) {
    		moveV = 2;
    		if(moveH > 0) {
    			moveH = vx;
    		} else {
    			moveH = vx*-1;
    		}
    	}
    }
}

class Paddle implements KeyListener {
	private int boxx,boxy,moveR,moveL;
	private boolean []keys;

	public Paddle() {
		keys = new boolean[KeyEvent.KEY_LAST+1];
		boxx = 237;
		boxy = 620;
		moveR = 5;
		moveL = -5;
		addKeyListener(this);
	}

	public void addNotify() {
        super.addNotify();
        setFocusable(true);
        requestFocus();
    }

	public void move(){
		if(keys[KeyEvent.VK_RIGHT] ){
			boxx += moveR;
		}
		if(keys[KeyEvent.VK_LEFT] ){
			boxx += moveL;
		}
	}

	public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void paint(Graphics g){
		g.setColor(Color.white);
		g.fillRect(boxx,boxy,75,10);
    }
}
