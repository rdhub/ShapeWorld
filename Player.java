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
		this.player_X = player_X;
		this.player_Y = player_Y;
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
}
