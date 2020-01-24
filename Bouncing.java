import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.MouseInfo;

public class Bouncing extends JFrame implements ActionListener{
	Timer myTimer; 
	GamePanel game;

    public Bouncing() {
		super("Move the Ball");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		myTimer = new Timer(7, this);	 // trigger every 10 ms


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
	 Bouncing frame = new Bouncing();
    }
}

class GamePanel extends JPanel implements KeyListener{
	private boolean []keys;
	private int moveX, moveY, ballX, ballY, padX, padY;
	private Image back, ballPic, paddlePic;
	private Bouncing mainFrame;
	private boolean moving, collides;

	Ball ball = new Ball(275, 604, 5, -1); 						//calling ball method 
	Paddle paddle = new Paddle(237, 620, 5, -5); 				//calling paddle method

	public GamePanel (Bouncing m){
		keys = new boolean[KeyEvent.KEY_LAST+1];
		back = new ImageIcon("background.png").getImage();
		ballPic = new ImageIcon("ball.png").getImage();
		paddlePic = new ImageIcon("paddle.png").getImage();

		mainFrame = m;
		moveX = ball.getVX(); 		//horizontal movement of ball
		moveY = ball.getVY(); 		//vertical movement of ball
        ballX = ball.getBallX(); 	//current x pos of ball
        ballY = ball.getBallY(); 	//current y pos of ball
        padX = paddle.getPadX(); 	//current x pos of paddle
        padY = paddle.getPadY(); 	//current y pos of paddle
        moving = false; 			//if ball is moving

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
			padX += paddle.getMoveR();
		}
		if(keys[KeyEvent.VK_LEFT] ){
			padX += paddle.getMoveL();
		}
		if(moving == true){
			ballX += moveX;
			ballY += moveY;
			bounceOffWall();
			collisions();
		}

		//System.out.println(ballx+", "+ball.getBallX()+", "+ball.getVX());
		//System.out.println("("+(ballX)+", "+(ballY)+")");
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
		g.drawImage(ballPic, ballX, ballY, this); //ball
		g.drawImage(paddlePic, padX, padY, this); //paddle
    }

    public void bounceOffWall() {

    	if(ballX >= getWidth()-17) {			//if ball bounces off of right side of screen
    		moveX = -5;
    		if(moveY > 0) {
    			moveY = ball.getVY()*-1;
    		} 
    		if(moveY < 0) {
    			moveY = ball.getVY();
    		}
    	}

    	if(ballX == 0) {						//if ball bounces off of left side of screen
    		moveX = 5;
			if(moveY > 0) {
    			moveY = ball.getVY()*-1;
    		} 
    		if(moveY < 0) {
    			moveY = ball.getVY();
    		}
    	}

    	if(ballY == 0) {						//if ball bounces off of top side of screen
    		moveY = 1;
    		if(moveX > 0) {
    			moveX = ball.getVX()*-5;
    		} 
    		if(moveX < 0) {
    			moveX = ball.getVX();
    		}
    	}

    	if(ballY >= getHeight()-17) {			//die if you touch bottom
    		gameOver();
    	}
    }

    public void collisions() {
    	if(ballX <= padX+75 && ballX >= padX && ballY == padY-17) { 	//if coords of ball are equal to top surface of paddle
    		moveY = -1;
    		if(moveX > 0) {
    			moveX = ball.getVX()*-5;
    		} 
    		if(moveX < 0) {
    			moveX = ball.getVX();
    		}
    	}

    	if(ballY >= padY-7 && ballY <= padY+10 && ballX == padX+75) { 	//if coords of ball are equal to right side of paddle
    		moveY = -1;
    		moveX = ball.getVX();
    	}

    	if(ballY >= padY-7 && ballY <= padY+10 && ballX == padX) { 	//if coords of ball are equal to left side of paddle
    		moveY = -1;
    		moveX = ball.getVX()*-5;
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
}
