import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Arkan extends JFrame{
	Timer gameTime;

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
		Scanner inFile = new Scanner(new BufferedReader(new FileReader("levels.txt")));
		int tot = Integer.parseInt(inFile.nextLine());
		for(int i=0;i<tot;i++){

		}
    }
}

class Level{
	private Paddle pad;
	private Block[][] layout;

	public Level(){
		layout = {{" "," "," "," "," "," "," "," "," "," "," "},
				  {" "," "," "," "," "," "," "," "," "," "," "},
				  {" "," "," "," "," "," "," "," "," "," "," "},
				  {" "," "," "," "," "," "," "," "," "," "," "},
				  {" "," "," "," "," "," "," "," "," "," "," "},
				  {" "," "," "," "," "," "," "," "," "," "," "},
				  {" "," "," "," "," "," "," "," "," "," "," "},
				  {" "," "," "," "," "," "," "," "," "," "," "},
				  {" "," "," "," "," "," "," "," "," "," "," "},
				  {" "," "," "," "," "," "," "," "," "," "," "},
				  {" "," "," "," "," "," "," "," "," "," "," "}};
	}
}

class Paddle{
	private int x,y;

	public Paddle(){

	}
}

class Block{
	private int x,y;

	public Block(){

	}
}