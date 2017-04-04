import java.util.*;

public class ShapeWorld {
	
	private static final int MAP_SIZE = 10000;
	
	private ArrayList<Projectile> shots;
	private Player player;
	private int map_x, map_y;
	private int screenCenter, screenSize;
	private int mapLeftEdge, mapTopEdge, mapBotEdge, mapRightEdge;

	public ShapeWorld(int centerX, int centerY, int center)
	{
		player = new Player(centerX, centerY);
		shots = new ArrayList<Projectile>();
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
	
	public void updateCoords(int dx, int dy)
	{
		int playerXCenterPos = screenCenter - player.getPlayerWidth()/2;
		int playerYCenterPos = screenCenter - player.getPlayerHeight()/2;
		int playerX = player.getPlayerX();
		int playerY = player.getPlayerY();
		
		if(dy > 0) //moving up
		{
			if(map_y + dy < mapTopEdge && playerY == playerYCenterPos)
			{
				map_y += dy;
			}
			else
			{
				if(playerY - dy >= mapTopEdge)
					player.updatePlayerY(-dy);
				else
					player.setPlayerY(mapTopEdge); //set playerY to edge value
			}
		}
		if(dy < 0) //moving down
		{
			if(map_y + dy > mapBotEdge && playerY == playerYCenterPos)
			{
				map_y += dy;
			}
			else
			{
				if(playerY + player.getPlayerHeight() - dy <= screenSize)
					player.updatePlayerY(-dy);
				else
					player.setPlayerY(screenSize - player.getPlayerHeight()); //set playerY to edge value
			}
		}
		if(dx > 0) //moving left
		{
			if(map_x + dx < mapLeftEdge && playerX == playerXCenterPos)
			{
				map_x += dx;
			}
			else
			{
				if(playerX - dx >= mapLeftEdge)
					player.updatePlayerX(-dx);
				else
					player.setPlayerX(mapLeftEdge); //set playerX to edge value
			}
		}
		if(dx < 0) //moving right
		{
			if(map_x + dx > mapRightEdge && playerX == playerXCenterPos)
			{
				map_x += dx;
			}
			else
			{
				if(playerX + player.getPlayerWidth() - dx <= screenSize)
					player.updatePlayerX(-dx);
				else
					player.setPlayerX(screenSize - player.getPlayerWidth()); //set playerX to edge value
			}
		}
	}
	public void updateMapX(int dx)
	{
		map_x += dx;
	}
	
	public void updateMapY(int dy)
	{
		map_y += dy;
	}
	
	public void shotFired(int targetX, int targetY)
	{
		Projectile shot = new Projectile(player.getPlayerX() + player.getPlayerWidth()/2, player.getPlayerY() + player.getPlayerHeight()/2, null);
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
			
			//Remove if shot goes out of screen
			if(shot.getXPosition() < mapLeftEdge || shot.getXPosition() > screenSize || shot.getYPosition() < mapTopEdge || shot.getYPosition() > screenSize)
				shots.remove(i);
		}
	}
	
	public int getShotX(int index)
	{
		return (int)Math.round(shots.get(index).getXPosition());
	}
	
	public int getShotY(int index)
	{
		return (int)Math.round(shots.get(index).getYPosition());
	}
	
}
