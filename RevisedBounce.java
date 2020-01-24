import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

public class Bouncing extends JFrame implements ActionListener{
	Timer myTimer; 
	GamePanel game;

	public static Level currentLevel;

    public Bouncing() {
		super("Move the Ball");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		myTimer = new Timer(7, this);	 // trigger every 10 ms


		game = new GamePanel(this);
		add(game);
		pack();
		setResizable(false);
		setVisible(true);
    }

	public void start(){
		myTimer.start();
	}

	public void actionPerformed(ActionEvent evt){
		game.move();
		game.repaint();
	}

    public static void main(String[] args) throws IOException {
    	Scanner inFile = new Scanner(new BufferedReader(new FileReader("levels.txt")));

		ArrayList<Level> levels = new ArrayList<Level>();
		int numLevels = Integer.parseInt(inFile.nextLine());
		for(int i=0;i<numLevels;i++){
			String line = inFile.nextLine();
			Level lev = new Level(line,i);
			levels.add(lev);//add to Level arraylist

			System.out.print("\n");//**************************************************************************************************
		}
		currentLevel = levels.get(0);
	 	Bouncing frame = new Bouncing();
    }

    public static void gameRunning(){
		currentLevel.buildLayout();
	}
}

class GamePanel extends JPanel implements KeyListener{
	private boolean []keys;
	private int moveX, moveY, ballX, ballY, padX, padY;
	private Image back, ballPic, paddlePic;
	private Bouncing mainFrame;
	private boolean moving;

	Ball ball = new Ball(275, 604, 5, -1); 						//calling ball method 
	Paddle paddle = new Paddle(237, 620, 5, -5); 				//calling paddle method

	public GamePanel (Bouncing m){
		keys = new boolean[KeyEvent.KEY_LAST+1];
		back = new ImageIcon("background.png").getImage();
		ballPic = new ImageIcon("ball.png").getImage();
		paddlePic = new ImageIcon("1.png").getImage();

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

class Level{
	private int lvlNum;
	private int lives;
	private ArrayList<Block> layout;
	private String[] colours;
	private int[] fillAmount;

	public Level(String line,int currentLvl){
		layout = new ArrayList<Block>();
		lvlNum = currentLvl;
		lives = 5;
		String[] rawAssets = line.split(",");
		int numTrueObjects = rawAssets.length/2;//objects in pairs: colour and amount
		colours = new String[numTrueObjects];
		fillAmount = new int[numTrueObjects];
		int count = 0;
		for(int i=0;i<rawAssets.length;i++){
			if(i%2==0){//Colors (String array)
				colours[count]=rawAssets[i];
			}
			else{//Blocks to create(int array)
				fillAmount[count]=Integer.parseInt(rawAssets[i]);
				count++;//only increases every 2 indexes
			}
		}
		loadLayout(numTrueObjects);
	}
	public void loadLayout(int numObj){
		/////////////pseudo-grid/////////////
		int currentPos = 0;//current position on grid
		int line1 = 11;
		int line2 = 22;
		int line3 = 33;//    Ends of each line on the grid
		int line4 = 44;
		int line5 = 55;//    maximum level size is 5 rows
		/////////////////////////////////////

		int x,y = 0;
		for(int i=0;i<numObj;i++){//for each colour and amount
			for(int j=0;j<fillAmount[i];j++){//make fillAmount[i] Blocks
				currentPos+=1;
				if(currentPos<=line1){
					y = 1;//actual position determined later with scalar constants
					x = currentPos;
					Block b = new Block(x,y,colours[i]);
					layout.add(b);//add the block to arrayList
				}
				if(currentPos>line1 && currentPos<=line2){//between line1 and line2
					y = 2;
					x = currentPos-11;//x will be adjusted for subsequent lines
					Block b = new Block(x,y,colours[i]);
					layout.add(b);
				}
				if(currentPos>line2 && currentPos<=line3){
					y = 3;
					x = currentPos-22;
					Block b = new Block(x,y,colours[i]);
					layout.add(b);
				}
				if(currentPos>line3 && currentPos<=line4){
					y = 4;
					x = currentPos-33;
					Block b = new Block(x,y,colours[i]);
					layout.add(b);
				}
				if(currentPos>line4 && currentPos<=line5){
					y = 5;
					x = currentPos-44;
					Block b = new Block(x,y,colours[i]);
					layout.add(b);
				}
			}
		}
	}
	public void buildLayout(){
		System.out.println("PLACEHOLDER buildLayout");
	}

	public int getLvl(){
		return lvlNum;
	}
	public int getLives(){
		return lives;
	}
}

/*class Ball {
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
}*/
class Ball{
	private int diameter;//size of ball
	private int x,y;//position
	private int velX,velY;//velocity components
	
	public Ball(){//create the ball
		diameter = 17;
		x=275;y=610;//starting position
		velX=0;velY=0;//stopped
	}
	//return position
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	//return velocity
	public int getVelX(){
		return velX;
	}
	public int getVelY(){
		return velY;
	}
	
	public void newVelX(int velocity){
		velX = velocity;
	}
	public void newVelY(int velocity){
		velY = velocity;
	}
}

/*class Paddle {
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
}*/
class Paddle{
	private int height,width;
	private int x,y;
	private int state;//default or power up
	
	public static int DEFAULT,CATCH;
	
	public Paddle(){
		DEFAULT=0;CATCH=1;
		height=13;width=75;
		x=275;//x will change
		y=620;//y is constant
		state = DEFAULT;
	}
	
	public int getPadX(){
		return x;
	}
	public int getPadY(){
		return y;//might come in handy
	}
	public int getState(){
		return state;
	}
	
	public void goLeft(){
		if(x>1){
			x=x-1;
		}
		else{
			x=5;
		}
	}
	public void goRight(){
		if(x<549){
			x=x+1;
		}
		else{
			x=545;
		}
	}
}

class Block{
	private int x,y,width,height;//position and dimensions
	private int hp,colour;//intrinsic properties
	private boolean holdsPowerUp;

	public static int SILVER,WHITE,LGREY,DGREY,BLACK;//colours for the blocks

	public Block(int rawX,int rawY,String col){
		SILVER=0;WHITE=1;LGREY=2;DGREY=3;BLACK=4;
		width = 50; height = 20;
		x = rawX*width;
		y = rawY*height+20;//shifted down 20px
		//after multiplying and shifting, last possible block should end up at 550,120
		hp = 1;//minimum hp
		if(col.equals("Silver")){
			colour = SILVER;
			hp+=2;//Silver blocks take 3 hits
		}
		if(col.equals("White")){
			colour = WHITE;
		}
		if(col.equals("LGrey")){
			colour = LGREY;
		}
		if(col.equals("DGrey")){
			colour = DGREY;
		}
		if(col.equals("Black")){
			colour = BLACK;
		}
		holdsPowerUp = false;
		//each block has a 1/10 chance of holding one of three power ups
		int powerPick = (int)(Math.random() * 10 + 1);//random number between 1 and 10
		if(powerPick==1){
			holdsPowerUp = true;
		}

		System.out.println(colour+","+x+","+y+holdsPowerUp);//**************************************************************************
	}
}
