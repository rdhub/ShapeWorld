import java.util.*;

public class ShapeWorld {
	
	private ArrayList<Projectile> shots;
	private int player_X, player_Y;
	private int player_height, player_width;
	private int wepReloadSpeed;
	
	public ShapeWorld(int centerX, int centerY)
	{
		shots = new ArrayList<Projectile>();
		player_X = centerX;
		player_Y = centerY;
		player_height = player_width = 30;
		wepReloadSpeed = 1000;
	}
	
	public int getWepReloadSpeed()
	{
		return wepReloadSpeed;
	}
	
	public void decreaseWepReloadSpeed()
	{
		wepReloadSpeed -= 100;
	}
	
	public int getPlayerX()
	{
		return player_X;
	}
	
	public int getPlayerY()
	{
		return player_Y;
	}
	
	public int getPlayerHeight()
	{
		return player_height;
	}
	
	public int getPlayerWidth()
	{
		return player_width;
	}
}
