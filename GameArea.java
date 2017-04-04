import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameArea extends JPanel implements ActionListener
{
	//Constants
	private static final int MAP_SIZE = 10000;
	private static final int SCREEN_CENTER = 250;
	private static final int SCREEN_CENTER_X = 250;
	private static final int SCREEN_CENTER_Y = 250;
	
	//UIComponents
	private JButton hpButton, atkButton, wepButton, afButton;
	
	//Variables
	private ShapeWorld game;
	private Image map, coin;
	private int map_x, map_y;
	
	public GameArea(Image map, Image coin)
	{
		this.setLayout(null);
		
		game = new ShapeWorld(SCREEN_CENTER_X, SCREEN_CENTER_Y);
		
		map_x = map_y = SCREEN_CENTER - MAP_SIZE/2;
		this.map = map;
		this.coin = coin;
		
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
		afButton.setBounds(520, 455, 110, 20);
		afButton.addActionListener(this);
		this.add(afButton);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.drawImage(map, map_x, map_y, MAP_SIZE, MAP_SIZE, this);
		
		g.setColor(Color.black);
		g.fillRect(game.getPlayerX(), game.getPlayerY(), game.getPlayerWidth(), game.getPlayerHeight());
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
	}
}
