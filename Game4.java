import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.MouseInfo;

public class Game4 extends JFrame implements ActionListener{
	Timer myTimer;
	GamePanel game;

    public Game4() {
		super("Move the Box");
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
	private int boxx,boxy,moveH,moveV,angle,vx,vy;
	private boolean []keys;
	private Image back;
	private Game4 mainFrame;
	private boolean moving;

	public GamePanel(Game4 m){
		keys = new boolean[KeyEvent.KEY_LAST+1];
		back = new ImageIcon("OuterSpace.jpg").getImage();

		mainFrame = m;
	    boxx = 170;
        boxy = 170;
        vx = 5;
        vy = 1;
        moveH = vx;
        moveV = vy;
        moving = false;


		setPreferredSize( new Dimension(800, 600));
        addKeyListener(this);
	}

    public void addNotify() {
        super.addNotify();
        setFocusable(true);
        requestFocus();
        mainFrame.start();
    }

	public void move(){
		/*if(keys[KeyEvent.VK_RIGHT] ){
			boxx += speed;
		}
		if(keys[KeyEvent.VK_LEFT] ){
			boxx -= speed;
		}
		if(keys[KeyEvent.VK_DOWN] ){
			boxy += speed;
		}*/
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
    	if(boxx == 790) {
    		moveH = -5;
    		if(moveV > 0) {
    			moveV = 1;
    		} else {
    			moveV = -1;
    		}
    	}

    	if(boxx == 0) {
    		moveH = 5;
			if(moveV > 0) {
    			moveV = 1;
    		} else {
    			moveV = -1;
    		}
    	}

    	if(boxy == 590) {
    		moveV = -1;
    		if(moveH > 0) {
    			moveH = 0-moveH;
    		} else {
    			moveH = -1*moveH;
    		}
    	}

    	if(boxy == 0) {
    		moveV = 1;
    		if(moveH > 0) {
    			moveH = 0-moveH;
    		} else {
    			moveH = -1*moveH;
    		}
    	}
    }
}
