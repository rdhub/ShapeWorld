public class Opponent
{
	private int maxHp, remainingHp, attack;
	private int hitBox;
	private double xPos, yPos;
	private double moveDx, moveDy;
	private int speed, moveDuration;
	private boolean moving;
	private long movement_time;
	
	public Opponent()
	{
		movement_time = 0;
		moving = false;
		moveDuration = 2000;
		moveDx = moveDy = 0;
		xPos = yPos = 100;
		hitBox = 20;
		maxHp = 0;
		remainingHp = 0;
		attack = 0;
		speed = 1;
	}
	
	public Opponent(int h, int a)
	{
		this();
		maxHp = h;
		remainingHp = maxHp;
		attack = a;
	}
	
	public void setXPos(double x)
	{
		xPos = x;
	}
	
	public void setYPos(double y)
	{
		yPos = y;
	}
	public int getX()
	{
		return (int)Math.round(xPos);
	}
	
	public int getY()
	{
		return (int)(Math.round(yPos));
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
	
	public void generateMoveDirection()
	{
		//360 degrees
		int angle = (int)(Math.random()*360+1);
		moveDx = Math.cos(angle);
		moveDy = Math.sin(angle);
	}
	
	public void move()
	{
		xPos += moveDx*speed;
		yPos += moveDy*speed;
	}
	
	public int getMoveDuration()
	{
		return moveDuration;
	}
	
	public void toggleMoving()
	{
		moving = !moving;
	}
	
	public boolean isMoving()
	{
		return moving;
	}
	
	public long getMovementTime()
	{
		return movement_time;
	}
	
	public void setMovementTime(long time)
	{
		movement_time = time;
	}
	public void updateXPosition(double x)
	{
		xPos += x;
	}
	
	public void updateYPosition(double y)
	{
		yPos += y;
	}
}
