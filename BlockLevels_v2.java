import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class BlockLevels_v2 extends JFrame{
	Timer myTimer;
	GamePanel game;
//	public static ArrayList<Level> levels = new ArrayList<Level>();

    public BlockLevels_v2(){
    	super("Barkanoid");//title
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(550,650);//550 wide, 650 long

		//set up timer
		myTimer = new Timer(10, new TickListener());
		myTimer.start();

		//set up panel
		game = new GamePanel();
		add(game);
		setResizable(false);
		setVisible(true);
    }
    class TickListener implements ActionListener{
		public void actionPerformed(ActionEvent evt){
			if(game!= null && game.ready){
				game.move();
				game.repaint();
			}
		}
	}
    public void actionPerformed(ActionEvent evt){
    	game.repaint();
    }

	public static void main(String[] args){

		Scanner inFile = new Scanner(new BufferedReader(new FileReader("levels.txt")));//premade levels
		int numLevels = Integer.parseInt(inFile.nextLine());//how many levels
		for(int i=0;i<numLevels;i++){//load levels
			String line = inFile.nextLine();
//			Level lev = new Level(line,i);
//			levels.add(lev);//add to arraylist
		}

    }
}

class GamePanel extends JPanel{
	private Paddle paddle;
	public boolean ready=false;
	private boolean[] keys;
	private Image back;

	public GamePanel(){
		keys = new boolean[KeyEvent.KEY_LAST+1];
		back = new ImageIcon("background.png").getImage();
	}
	public void move() {
		if(keys[KeyEvent.VK_RIGHT] ){
			paddle.move(5);
		}
		if(keys[KeyEvent.VK_LEFT] ){
			paddle.move(-5);
		}
    }
	public void paintComponent(Graphics g){
		g.drawImage(back,0,0,null);
    }
}

/*class Level{
	private int lvlNum;
	private int lives;
	private ArrayList<Block> layout;


}*/
class Paddle{
	private int x,y;
	private Image[] pics;
	private int dir;
	public static final int LEFT=0,RIGHT=1,STOP=2;

	public Paddle(int x,int y,String name,int powNum){//number of powerups************************
		this.x=x;
		this.y=y;
		dir=STOP;

		pics = new Image[powNum];
		for(int i=0;i<powNum;i++){
			pics[i] = new ImageIcon(name+"/"+name+i+".png").getImage();
		}
	}
}