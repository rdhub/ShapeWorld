public class Player
{
	private int player_X, player_Y;
	private int player_height, player_width;
	private int wepReloadSpeed;
	private int maxHp, currentHp;
	private int currentExp, nextLevelExp;
	
	public Player(int player_X, int player_Y)
	{
		currentExp = 0;
		nextLevelExp = 25;
		currentHp = maxHp = 100;
		player_height = player_width = 28;
		wepReloadSpeed = 500;
		
		// Centers the player on the center of the screen
		this.player_X = player_X - player_width/2;
		this.player_Y = player_Y - player_height/2;
	}
	
	public int getWepReloadSpeed()
	{
		return wepReloadSpeed;
	}
	
	public void decreaseWepReloadSpeed()
	{
		wepReloadSpeed -= 100;
	}
	
	public int getX()
	{
		return player_X;
	}
	
	public int getY()
	{
		return player_Y;
	}
	
	public int getHeight()
	{
		return player_height;
	}
	
	public int getWidth()
	{
		return player_width;
	}
	
	public int getCurrentExp()
	{
		return currentExp;
	}
	
	public int getNextLevelExp()
	{
		return nextLevelExp;
	}
	
	public int getCurrentHp()
	{
		return currentHp;
	}
	
	public int getMaxHp()
	{
		return maxHp;
	}
	
	public void updateX(int dx)
	{
		player_X += dx;
	}
	
	public void updateY(int dy)
	{
		player_Y += dy;
	}
	
	public void setX(int x)
	{
		player_X = x;
	}
	
	public void setY(int y)
	{
		player_Y = y;
	}
}
