import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class BlockLevels_v2 extends JFrame{
	Timer myTimer;
	GamePanel game;

	public static ArrayList<Level> levels = new ArrayList<Level>();

    public BlockLevels_v2(){
    	//set up frame
    	super("Barkanoid");//title
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//close button closes
		setSize(550,650);//550 wide, 650 high

		//set up timer
		myTimer = new Timer(10, new TickListener());
		myTimer.start();

		//set up panel
		game = new GamePanel();
		add(game);
		setResizable(false);
		setVisible(true);
    }

	public static void main(String[] args){
		Scanner inFile = new Scanner(new BufferedReader(new FileReader("levels.txt")));//premade levels
		int tot = Integer.parseInt(inFile.nextLine());
		for(int i=0;i<tot;i++){//load levels
			String line = inFile.nextLine();
			Level lev = new Level(line,i);//i for level number
			levels.add(lev);//add to arraylist
		}

    }
}

class GamePanel extends JPanel{
	//private Paddle paddle;
	public boolean ready=false;
	private boolean[] keys;

	public GamePanel(){

	}
	public void paint(Graphics g){
		g.setColor(new Color(222,255,222));
        g.fillRect(0,0,getWidth(),getHeight());
    }
}

