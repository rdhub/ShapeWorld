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
		player_height = player_width = 30;
		wepReloadSpeed = 1000;
		
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
	
	public void updatePlayerX(int dx)
	{
		player_X += dx;
	}
	
	public void updatePlayerY(int dy)
	{
		player_Y += dy;
	}
	
	public void setPlayerX(int x)
	{
		player_X = x;
	}
	
	public void setPlayerY(int y)
	{
		player_Y = y;
	}
}
