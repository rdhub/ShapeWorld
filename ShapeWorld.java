import java.util.*;

public class ShapeWorld {
	
	private static final int MAP_SIZE = 10000;
	
	private ArrayList<Projectile> shots;
	private Player player;
	private int map_x, map_y;
	private int screenCenter, screenSize;
	private int mapLeftEdge, mapTopEdge, mapBotEdge, mapRightEdge;
	private ArrayList<Opponent> enemies;

	public ShapeWorld(int centerX, int centerY, int center)
	{
		player = new Player(centerX, centerY);
		shots = new ArrayList<Projectile>(100);
		enemies = new ArrayList<Opponent>();
		Opponent opponent = new Opponent(10, 1); //(health, attack)
		enemies.add(opponent);
		map_x = map_y = center - MAP_SIZE/2;
		screenCenter = center;
		screenSize = center*2;
		mapLeftEdge = mapTopEdge = 0;
		mapBotEdge = mapRightEdge = -(MAP_SIZE - screenSize);
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public int getMapX()
	{
		return map_x;
	}
	
	public int getMapY()
	{
		return map_y;
	}
	
	public int getMapSize()
	{
		return MAP_SIZE;
	}
	
	public void updateCoords(int dx, int dy) // Updates coordinates when the player moves
	{
		int playerXCenterPos = screenCenter - player.getPlayerWidth()/2;
		int playerYCenterPos = screenCenter - player.getPlayerHeight()/2;
		int playerX = player.getPlayerX();
		int playerY = player.getPlayerY();
		
		if(dy > 0) //moving up
		{
			// Moves position of the map
			if(map_y + dy < mapTopEdge && playerY == playerYCenterPos)
			{
				map_y += dy;
				updateShots(dx, dy);
				updateEnemies(dx, dy);
			}
			else//Moves the player position
			{
				if(playerY - dy >= mapTopEdge)
					player.updatePlayerY(-dy);
				else
					player.setPlayerY(mapTopEdge); //set playerY to edge value
			}
		}
		if(dy < 0) //moving down
		{
			// Moves position of the map
			if(map_y + dy > mapBotEdge && playerY == playerYCenterPos)
			{
				map_y += dy;
				updateShots(dx, dy);
				updateEnemies(dx, dy);
			}
			else//Moves the player position
			{
				if(playerY + player.getPlayerHeight() - dy <= screenSize)
					player.updatePlayerY(-dy);
				else
					player.setPlayerY(screenSize - player.getPlayerHeight()); //set playerY to edge value
			}
		}
		if(dx > 0) //moving left
		{
			// Moves position of the map
			if(map_x + dx < mapLeftEdge && playerX == playerXCenterPos)
			{
				map_x += dx;
				updateShots(dx, dy);
				updateEnemies(dx, dy);
			}
			else //Moves the player position
			{
				if(playerX - dx >= mapLeftEdge)
					player.updatePlayerX(-dx);
				else
					player.setPlayerX(mapLeftEdge); //set playerX to edge value
			}
		}
		if(dx < 0) //moving right
		{
			// Moves position of the map
			if(map_x + dx > mapRightEdge && playerX == playerXCenterPos)
			{
				map_x += dx;
				updateShots(dx, dy);
				updateEnemies(dx, dy);
			}
			else//Moves the player position
			{
				if(playerX + player.getPlayerWidth() - dx <= screenSize)
					player.updatePlayerX(-dx);
				else
					player.setPlayerX(screenSize - player.getPlayerWidth()); //set playerX to edge value
			}
		}
	}
	
	// Creates a new shot
	public void shotFired(int targetX, int targetY)
	{
		Projectile shot = new Projectile(player.getPlayerX() + player.getPlayerWidth()/2, player.getPlayerY() + player.getPlayerHeight()/2, null, screenSize/2*Math.sqrt(2));
		shot.setTargetX(targetX);
		shot.setTargetY(targetY);
		shot.calculate();
		shots.add(shot);
	}
	
	public int getNumberOfShots()
	{
		return shots.size();
	}
	
	public void updateShots()
	{
		for (int i = shots.size() - 1; i >= 0; i--)
		{
			Projectile shot = shots.get(i);
			shot.updateCoords();
			
			//Remove if shot reaches its max travel distance
			if(shot.isAtMaxShotDist())
				shots.remove(i);
			else
			{
				for(int j = 0; j < enemies.size(); j++)
				{
					Opponent enemy = enemies.get(j);
					if(enemy.isHit(shot))
					{
						System.out.println("hit!");
						shots.remove(i);
						break;
					}
				}
			}
		}
	}
	
	public void updateShots(int moveDistX, int moveDistY)
	{
		for (int i = shots.size() - 1; i >= 0; i--)
		{
			Projectile shot = shots.get(i);
			shot.updateXPosition(moveDistX);
			shot.updateYPosition(moveDistY);
		}
	}
	
	public void updateEnemies(int moveDistX, int moveDistY)
	{
		for (int i = enemies.size() - 1; i >= 0; i--)
		{
			Opponent enemy = enemies.get(i);
			enemy.updateXPosition(moveDistX);
			enemy.updateYPosition(moveDistY);
		}
	}
	// Returns x position of the shot
	public int getShotX(int index)
	{
		return (int)Math.round(shots.get(index).getXPosition());
	}
	
	// returns y position of the shot
	public int getShotY(int index)
	{
		return (int)Math.round(shots.get(index).getYPosition());
	}
	
	public Opponent getEnemy(int index)
	{
		return enemies.get(index);
	}
	
	public int getNumberOfEnemies()
	{
		return enemies.size();
	}
}
