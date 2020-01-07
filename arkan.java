import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Arkan extends JFrame{

    public static void main(String[] args){
		Scanner inFile = new Scanner(new BufferedReader(new FileReader("levels.txt")));
		int tot =
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
				  {" "," "," "," "," "," "," "," "," "," "," "}}
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