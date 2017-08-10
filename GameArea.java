// GUI for the main game playing area

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameArea extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener
{
	//Constants
	private static final int SCREEN_CENTER = 250;
	private static final int SCREEN_CENTER_X = 250;
	private static final int SCREEN_CENTER_Y = 250;
	
	//UIComponents
	private JButton hpButton, atkButton, wepButton, afButton;
	
	//Variables
	private ShapeWorld game;
	private Player player;
	private Image map, coin;
	private boolean moveUp, moveDown, moveLeft, moveRight, firingStatus;
	private int animationSpeed;
	private int mouseX, mouseY;
	private Timer timer;
	
	public GameArea(Image map, Image coin)
	{
		mouseX = mouseY = 0;
		moveUp = moveDown = moveLeft = moveRight = firingStatus = false;
		animationSpeed = 5;
		this.setLayout(null);
		
		game = new ShapeWorld(SCREEN_CENTER_X, SCREEN_CENTER_Y, SCREEN_CENTER);
		timer = new Timer(animationSpeed, this);
		timer.start();
		player = game.getPlayer();
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
		
		this.addKeyListener(this);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.requestFocus();
		
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		//Draws the map
		g.drawImage(map, game.getMapX(), game.getMapY(), game.getMapSize(), game.getMapSize(), this);
		
		//Draws the player
		g.setColor(Color.black);
		g.fillRect(player.getPlayerX(), player.getPlayerY(), player.getPlayerWidth(), player.getPlayerHeight());
		
		// Draws the right side upgrade panel
		g.setColor(Color.white);
		g.fillRect(505,0,195,500);
		g.setColor(Color.black);
		g.fillRect(500,0,5,500);
		
		//Draws the shots fired from the player
		for (int i = 0; i < game.getNumberOfShots(); i++)
		{
			g.fillOval(game.getShotX(i), game.getShotY(i), 10, 10);
		}
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
		//Checks which direction player is currently moving in
		if(moveUp)
			game.updateCoords(0, 5);
		if(moveDown)
			game.updateCoords(0, -5);
		if(moveLeft)
			game.updateCoords(5, 0);
		if(moveRight)
			game.updateCoords(-5, 0);
		
		
		
		if(firingStatus && player.getRemainingReloadTime() == 0)
		{
			//Fires a shot
			game.shotFired(mouseX, mouseY);
			player.setRemainingReloadTime(player.getWepReloadSpeed());
		}
		else
		{
			player.decreaseRemainingReloadTime(animationSpeed);
		}
		game.updateShots();
		this.repaint();
	}
	
	public void keyPressed(KeyEvent e) {}
	public void keyTyped(KeyEvent e){
		char character = e.getKeyChar();
		
		//Checks which way player is moving
		switch(character)
		{
			case 's':
				moveDown = true;
				break;
			case 'd':
				moveRight = true;
				break;
			case 'w':
				moveUp = true;
				break;
			case 'a':
				moveLeft = true;
				break;
		}
	}
	public void keyReleased(KeyEvent e) {
		//Checks which key to stop movement
		if(e.getKeyChar()=='w')
			moveUp = false;
		if(e.getKeyChar()=='a')
			moveLeft = false;
		if(e.getKeyChar()=='s')
			moveDown = false;
		if(e.getKeyChar()=='d')
			moveRight = false;
	}
	public void mouseClicked(MouseEvent e) {
		
		//Fires a shot
		//game.shotFired(mouseX, mouseY);
	}
	public void mousePressed(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
		firingStatus = true;
	}
	public void mouseReleased(MouseEvent e)
	{
		firingStatus = false;
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseMoved(MouseEvent e)
	{
		
	}
	public void mouseDragged(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}
}
