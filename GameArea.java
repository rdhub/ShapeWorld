

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameArea extends JPanel
{
	//Constants
	public static final int MAP_SIZE = 10000;
	public static final int SCREEN_CENTER = 250;
	public static final int SCREEN_CENTER_X = 250;
	public static final int SCREEN_CENTER_Y = 250;
	
	//UIComponents
	public JButton hpButton, atkButton, wepButton, afButton;
	
	//Variables
	public Image map, coin;
	public int map_x, map_y;
	public int 
	
	
	public GameArea()
	{
		this.setLayout(null);
		
		map_x = map_y = SCREEN_CENTER-MAP_SIZE/2;
		map = getImage(getDocumentBase(), "Map.jpg");
		WaitForImage(this, map);
		coin = getImage( getDocumentBase ( ), "Coin.png");
		WaitForImage ( this, coin );
		
		hpButton = new JButton("Health + 5");
		hpButton.setBounds(520, 320, 110, 20);
		hpButton.addActionListener(this);
		this.add(hpButton);
		
		atkButton = new JButton("Damage + 1");
		atkButton.setBounds(520, 365, 110, 20);
		atkButton.addActionListener(this);
		this.add(atkButton);
		
		wepButton = new JButton("Weapon + 1");
		wepButton.setBounds(520, 410, 110, 20);
		wepButton.addActionListener(this);
		this.add(wepButton);
		
		afButton = new JButton("Autofire");
		afButton.seBounds(520, 455, 110, 20);
		afButton.addActionListener(this);
		this.add(afButton);
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
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.drawImage(map, map_x, map_y, MAP_SIZE, MAP_SIZE, this);
		
		
	}
}
