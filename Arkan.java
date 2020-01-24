import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

public class Arkan extends JFrame{
public class Arkan {
	public static Level currentLevel;
	
	public static void main(String[] args) throws IOException{
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
		/*while(currentLevel.getLives()>0 && levels.size()>0){
			gameRunning();
		}*/
		
	}
	public static void gameRunning(){
		currentLevel.buildLayout();
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
		
		int x,y = 0;//*********************NOTE TO SELF************************if code is not working: try moving this inside the for loop
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
		if(col=="Silver"){
			colour = SILVER;
			hp+=2;//Silver blocks take 3 hits
		}
		if(col=="White"){
			colour = WHITE;
		}
		if(col=="LGrey"){
			colour = LGREY;
		}
		if(col=="DGrey"){
			colour = DGREY;
		}
		if(col=="Black"){
			colour = BLACK;
		}
		holdsPowerUp = false;
		//each block has a 1/10 chance of holding one of three power ups
		int powerPick = (int)(Math.random() * 10 + 1);//random number between 1 and 10
		if(powerPick==1){
			holdsPowerUp = true;
		}
		
		System.out.println(colour+x+","+y+holdsPowerUp);//**************************************************************************
	}
}