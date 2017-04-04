import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameArea extends JPanel implements ActionListener, KeyListener
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
	private boolean moveUp, moveDown, moveLeft, moveRight;
	private Timer timer;
	
	public GameArea(Image map, Image coin)
	{
		this.setLayout(null);
		
		game = new ShapeWorld(SCREEN_CENTER_X, SCREEN_CENTER_Y, SCREEN_CENTER);
		timer = new Timer(5, this);
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
		this.requestFocus();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.drawImage(map, game.getMapX(), game.getMapY(), game.getMapSize(), game.getMapSize(), this);
		
		g.setColor(Color.black);
		g.fillRect(player.getPlayerX(), player.getPlayerY(), player.getPlayerWidth(), player.getPlayerHeight());
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if(moveUp)
			game.updateCoords(0, 5);
		if(moveDown)
			game.updateCoords(0, -5);
		if(moveLeft)
			game.updateCoords(5, 0);
		if(moveRight)
			game.updateCoords(-5, 0);
		this.repaint();
	}
	
	public void keyPressed(KeyEvent e) {}
	public void keyTyped(KeyEvent e){
		char character = e.getKeyChar();
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
		this.repaint();
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
}
