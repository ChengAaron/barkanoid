import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Arkan extends JFrame{
	Timer gameTime;
	public static ArrayList<Level> levels = new ArrayList<Level>();

	public Arkan(){
		super("Barkanoid");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(550,650);
		gameTime = new Timer(10, new TickListener());
		gameTime.start();

		setResizable(false);
		setVisible(true);
	}
    public static void main(String[] args){
		Scanner inFile = new Scanner(new BufferedReader(new FileReader("levels.txt")));//premade levels
		int tot = Integer.parseInt(inFile.nextLine());
		for(int i=0;i<tot;i++){
			String line = inFile.nextLine();
			Block b = new Block(line);
			levels.add(b);
		}
    }
}

class Level{
	private int lvlNum;
	private ArrayList<Block> layout;
	private Image back;

	public Level(String line){
		back = new ImageIcon("background.png").getImage();//load background
		String[] info = line.split(",");
		String[] col = new String[info.length/2];//colour names
		int[] numCol = new int[info.length/2];//number of blocks with that colour
		int counter = 0;
		for(String i:info){
			if(info.indexOf(i)%2 == 0){//odd indexes in text file (starts on 0)
				col[counter] = i;//add to String array
			}
			else{
				numCol[counter] = Integer.parseInt(i);//convert to integer
			}
		}
	}
	public void paint(){

	}
}

class Block{
	private int x,y;
	private String colour;

	public Block(){

	}
}