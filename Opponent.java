public class Opponent
{
	private int maxHealth, remainingHealth, power;
	
	public Opponent()
	{
		maxHealth = 0;
		remainingHealth = 0;
		power = 0;
	}
	
	public Opponent(int h, int p)
	{
		this();
		maxHealth = h;
		remainingHealth = maxHealth;
		power = p;
	}
	
	public void setPower(int p)
	{
		power = p;
	}
	
	public int getPower()
	{
		return power;
	}
	
	public void setMaxHealth(int h)
	{
		maxHealth = h;
	}
	
	public int getMaxHealth()
	{
		return maxHealth;
	}
	
	public void decreaseHealth(int d)
	{
		remainingHealth -= d;
	}
	
	public int getRemainingHealth()
	{
		return remainingHealth;
	}
}
