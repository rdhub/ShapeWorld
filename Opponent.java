public class Opponent
{
	private int maxHp, remainingHp, attack;
	private int xPos, yPos, hitBox;
	
	public Opponent()
	{
		xPos = yPos = 0;
		hitBox = 20;
		maxHp = 0;
		remainingHp = 0;
		attack = 0;
	}
	
	public Opponent(int h, int a)
	{
		this();
		maxHp = h;
		remainingHp = maxHp;
		attack = a;
	}
	
	public int getX()
	{
		return xPos;
	}
	
	public int getY()
	{
		return yPos;
	}
	
	public void setAttack(int a)
	{
		attack = a;
	}
	
	public int getAttack()
	{
		return attack;
	}
	
	public void setMaxHp(int h)
	{
		maxHp = h;
	}
	
	public int getMaxHp()
	{
		return maxHp;
	}
	
	public void decreaseHp(int d)
	{
		remainingHp -= d;
	}
	
	public int getRemainingHp()
	{
		return remainingHp;
	}
	
	public boolean isDamaged()
	{
		return remainingHp < maxHp;
	}
}
