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
			Level lev = new Level(line,i);//i for level number
			levels.add(lev);
		}
		int count = 0;
		Level currentLevel = levels[count];
		while(count<tot && currentLevel.getLives>=0){

		}
    }
}

class Level{
	private int lvlNum;
	private int lives;
	private ArrayList<Block> layout;
	private Image back;

	public Level(String line,int i){
		lvlNum = i;
		lives = 6;//each level starts with same number of lives
		layout = new ArrayList<Block>();
		back = new ImageIcon("background.png").getImage();//load background
		String[] info = line.split(",");
		String[] col = new String[info.length/2];//colour names
		int[] numCol = new int[info.length/2];//number of blocks with that colour
		int counter = 0;
		for(String s:info){
			if(info.indexOf(s)%2 == 0){//odd indexes in text file (starts on 0)
				col[counter] = s;//add to String array
			}
			else{
				numCol[counter] = Integer.parseInt(s);//convert to integer & add to int array
			}
			count++;//proceed to next index
		}
		for(int i=0;i<col.length;i++){//for each colour block
			for(int j=0;j<numCol[i];j++){//for the number of specified blocks
				int arrPos = layout.size();
				Block b = new Block(col[i]);
				layout.add(b);
			}
		}
	}
	public void paint(){

	}
	public int getLives(){
		return lives;
	}
}

class Block{
	private String colour;

	public Block(String col){
		colour = col;
	}
	public int getPoints(String col){
		if(col=="White"){

		}
		if(col=="Yellow"){

		}
		if(col=="Green"){

		}
		if(col=="Orange"){

		}
		if(col=="Red"){

		}
		if(col=="LBlue"){

		}
		if(col=="DBlue"){

		}
		if(col=="Magenta"){

		}
	}
}
/*
    1  2  3  4  5  6  7  8  9 10 11
   12 13 14 15 16 17 18 19 20 21 22
   23 24 25 26 27 28 29 30 31 32 33
   34 35 36 37 38 39 40 41 42 43 44
   45 46 47 48 49 50 51 52 53 54 55
*/