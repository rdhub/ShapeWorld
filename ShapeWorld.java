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
		for (int i = 0; i < 4; i++)
		{
			Opponent opponent = new Opponent(10, 1, 100 + i*50, 100); //(health, attack, x, y)
			enemies.add(opponent);
		}
		
		//~ Opponent opponent = new Opponent(10, 1, 100, 100); //(health, attack, x, y)
		//~ enemies.add(opponent);
		//~ opponent = new Opponent(10, 1, 200, 100); //(health, attack, x, y)
		//~ enemies.add(opponent);
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
		int playerXCenterPos = screenCenter - player.getWidth()/2;
		int playerYCenterPos = screenCenter - player.getHeight()/2;
		int playerX = player.getX();
		int playerY = player.getY();
		
		if(dy > 0) //moving up
		{
			// Moves position of the map
			if(map_y + dy < mapTopEdge && playerY <= playerYCenterPos)
			{
				map_y += dy;
				updateShots(dx, dy);
				updateEnemies(dx, dy);
			}
			else//Moves the player position
			{
				if(playerY - dy >= mapTopEdge)
					player.updateY(-dy);
				else
					player.setY(mapTopEdge); //set playerY to edge value
			}
		}
		if(dy < 0) //moving down
		{
			// Moves position of the map
			if(map_y + dy > mapBotEdge && playerY >= playerYCenterPos)
			{
				map_y += dy;
				updateShots(dx, dy);
				updateEnemies(dx, dy);
			}
			else//Moves the player position
			{
				if(playerY + player.getHeight() - dy <= screenSize)
					player.updateY(-dy);
				else
					player.setY(screenSize - player.getHeight()); //set playerY to edge value
			}
		}
		if(dx > 0) //moving left
		{
			// Moves position of the map
			if(map_x + dx < mapLeftEdge && playerX <= playerXCenterPos)
			{
				map_x += dx;
				updateShots(dx, dy);
				updateEnemies(dx, dy);
			}
			else //Moves the player position
			{
				if(playerX - dx >= mapLeftEdge)
					player.updateX(-dx);
				else
					player.setX(mapLeftEdge); //set playerX to edge value
			}
		}
		if(dx < 0) //moving right
		{
			// Moves position of the map
			if(map_x + dx > mapRightEdge && playerX >= playerXCenterPos)
			{
				map_x += dx;
				updateShots(dx, dy);
				updateEnemies(dx, dy);
			}
			else//Moves the player position
			{
				if(playerX + player.getWidth() - dx <= screenSize)
					player.updateX(-dx);
				else
					player.setX(screenSize - player.getWidth()); //set playerX to edge value
			}
		}
	}
	
	// Creates a new shot
	public void shotFired(int targetX, int targetY)
	{
		Projectile shot = new Projectile(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2, null, screenSize/2*Math.sqrt(2));
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
						break; // Break because only 1 enemy can be hit
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
			shot.updateX(moveDistX);
			shot.updateY(moveDistY);
		}
	}
	
	public void updateEnemies(int moveDistX, int moveDistY)
	{
		for (int i = enemies.size() - 1; i >= 0; i--)
		{
			Opponent enemy = enemies.get(i);
			enemy.updateX(moveDistX);
			enemy.updateY(moveDistY);
		}
	}
	// Returns x position of the shot
	public int getShotX(int index)
	{
		return (int)Math.round(shots.get(index).getX());
	}
	
	// returns y position of the shot
	public int getShotY(int index)
	{
		return (int)Math.round(shots.get(index).getY());
	}
	
	public Opponent getEnemy(int index)
	{
		return enemies.get(index);
	}
	
	public int getNumberOfEnemies()
	{
		return enemies.size();
	}
	
	public boolean isOnScreen(int xCoord, int yCoord)
	{
		return xCoord >= 0 && xCoord <= screenSize && yCoord >= 0 && yCoord <= screenSize;
	}
	
	public void moveEnemies(long currentTime)
	{
		//Moves the enemy
		for (int i = 0; i < enemies.size(); i++)
		{
			Opponent enemy = enemies.get(i);
			
			// Checks if the enemy is in range of the player and initiates it to chase
			if(enemy.isInRangeOfPlayer(player.getX(), player.getY()))
			{
				enemy.generatePlayerDirection(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2);
			}
			
			// Toggle the move status if the time passed exceeds the movement time threshold
			if(currentTime - enemy.getMovementTimeStamp() >= enemy.getMoveDuration())
			{
				enemy.setMovementTimeStamp(currentTime);
				enemy.toggleMoving(); // Alternates the enemy between moving and pausing
				if(enemy.isMoving())
				{
					enemy.generateMoveDirection();
				}
			}
			
			// Move the enemies if they are within range of player or they are set to move.
			if(enemy.isMoving()||enemy.isInRangeOfPlayer(player.getX(), player.getY()))
			{
				enemy.move();
			}
		}
	}
}
