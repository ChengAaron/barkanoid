import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.MouseInfo;

public class arkan extends JFrame implements ActionListener{
	Timer myTimer;
	GamePanel game;

    public arkan() {
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
	 arkan frame = new arkan();
    }
}

class GamePanel extends JPanel implements KeyListener{
	private boolean []keys;
	private int moveH, moveV, ballx, bally, padx, pady;
	private Image back, ballPic, paddlePic;
	private arkan mainFrame;
	private boolean moving;

	Ball ball = new Ball(170, 170, 5, 1);
	Paddle paddle = new Paddle(237, 620, 5, -5);

	public GamePanel (arkan m){
		keys = new boolean[KeyEvent.KEY_LAST+1];
		back = new ImageIcon("background.png").getImage();
		ballPic = new ImageIcon("ball.png").getImage();
		paddlePic = new ImageIcon("paddle.png").getImage();

		mainFrame = m;
		moveH = ball.getVX();
		moveV = ball.getVY();
        ballx = ball.getBallX();
        bally = ball.getBallY();
        padx = paddle.getPadX();
        pady = paddle.getPadY();
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
		if(keys[KeyEvent.VK_RIGHT] ){
			paddle.setPadX(paddle.getMoveR());
		}
		if(keys[KeyEvent.VK_LEFT] ){
			paddle.setPadX(paddle.getMoveL());
		}
		if(moving == true){
			ballx += ball.getVX();
			bally += ball.getVY();
			collisions();
		}


		Point mouse = MouseInfo.getPointerInfo().getLocation();
		Point offset = getLocationOnScreen();
		//System.out.println("("+(mouse.x-offset.x)+", "+(mouse.y-offset.y)+")");
		System.out.println(ballx+", "+ball.getBallX()+", "+ball.getVX());
		//System.out.println("("+(ballx)+", "+(bally)+")");
	}

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void paint(Graphics g){
    	g.drawImage(back,0,0,null); //background
		g.drawImage(ballPic, ballx, bally, null);
		g.drawImage(paddlePic, paddle.getPadX(), paddle.getPadY(), null);
    }

    public void collisions() {

    	if(ballx >= getWidth()-17) {
    		ball.setVX(-5);
    		if(ball.getVY() > 0) {
    			ball.setVY(-5);
    		} else {
    			ball.setVY(5);
    		}
    	}

    	if(ballx <= 0) {
    		ball.setVX(5);
			if(moveV > 0) {
    			moveV = ball.getVY();
    		} else {
    			moveV = ball.getVY()*-1;
    		}
    	}

    	if(bally == 0) {
    		ball.setVY(1);
    		if(moveH > 0) {
    			moveH = ball.getVX();
    		} else {
    			moveH = ball.getVX()*-1;
    		}
    	}

    	if(bally >= getHeight()-17) {
    		ball.setVY(-1);
    		if(moveH > 0) {
    			moveH = ball.getVX();
    		} else {
    			moveH = ball.getVX()*-1;
    		}
    		//gameOver();
    	}
    }

    public void gameOver() {
    	System.out.println("Game Over");
    	System.exit(0);
    }
}


class Ball {
	private int ballx,bally,vx,vy;

	public Ball(int ballx, int bally, int vx, int vy) {
		this.ballx = ballx;
		this.bally = bally;
		this.vx = vx;
		this.vy = vy;
	}

	public int getBallX() {
		return ballx;
	}

	public int getBallY() {
		return bally;
	}

	public int getVX() {
		return vx;
	}

	public int getVY() {
		return vy;
	}

	public void setBallx(int ballx) { 
		this.ballx += ballx;
	}

	public void setBally(int bally) {
		this.bally += bally;
	}

	public void setVX(int i) {
		vx = i;
	}

	public void setVY(int i) {
		vx = i;
	}
}

class Paddle {
	private int padx,pady,moveR,moveL;

	public Paddle(int padx, int pady, int moveR, int moveL) {
		this.padx = padx;
		this.pady = pady;
		this.moveR = moveR;
		this.moveL = moveL;
	}

	public int getPadX() {
		return padx;
	}

	public int getPadY() {
		return pady;
	}

	public int getMoveR() {
		return moveR;
	}

	public int getMoveL() {
		return moveL;
	}

	public void setPadX(int move) {
		padx += move;
	}
}