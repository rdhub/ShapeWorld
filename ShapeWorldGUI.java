//Richard Du
//Created: 03/03/2017
//ShapeWorldGUI.java
//Updated Code Structure from Game.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ShapeWorldGUI extends JApplet implements KeyListener, MouseListener,
											MouseMotionListener, ActionListener
{
	private CardLayout cardLayout;
	private Container c;
	//~ private Finish endscreen;
	private GameArea gameArea;
	//~ private MainMenu menu;
	
	private Image map, coin, title, eyes;
	
	//Constructor
	public ShapeWorldGUI()
	{
		
	}
	
	//Initializes and adds the panels. Gets the images
	public void init()
	{
		map = getImage(getDocumentBase(), "Map.jpg");
		WaitForImage(this, map);
		coin = getImage( getDocumentBase ( ), "Coin.png");
		WaitForImage ( this, coin );
		title = getImage( getDocumentBase ( ), "Title.png");
		WaitForImage ( this, title );
		eyes = getImage( getDocumentBase ( ), "Eyes.png");
		WaitForImage ( this, eyes );
		
		c = this.getContentPane();
		
		cardLayout = new CardLayout();
		c.setLayout(cardLayout);
		
		//~ menu = new MainMenu();
		//~ c.add(menu, "MainMenu");
		
		gameArea = new GameArea(map, coin);
		c.add(gameArea, "GamePanel");
		
		
		//~ endscreen = new Finish();
		//~ c.add(endscreen, "EndScreen");
		
		gameArea.requestFocus();
	}
	
	public void WaitForImage ( JApplet component, Image image )
	{
		MediaTracker tracker = new MediaTracker ( component );
		try
		{
			tracker.addImage ( image, 0 );
			tracker.waitForID ( 0 );
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace ( );
		}
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void actionPerformed(ActionEvent e) {}
}
