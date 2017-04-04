public class Opponent
{
	private int maxHealth, remainingHealth, attack;
	private int xPos, yPos, hitBox;
	
	public Opponent()
	{
		xPos = yPos = 0;
		hitBox = 20;
		maxHealth = 0;
		remainingHealth = 0;
		attack = 0;
	}
	
	public Opponent(int h, int a)
	{
		this();
		maxHealth = h;
		remainingHealth = maxHealth;
		attack = a;
	}
	
	public void setAttack(int a)
	{
		attack = a;
	}
	
	public int getAttack()
	{
		return attack;
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
	
	public boolean isDamaged()
	{
		return remainingHealth < maxHealth;
	}
}
