public class Opponent
{
	private int maxHp, remainingHp, attack;
	private int hitBox;
	private double xPos, yPos;
	private double moveDx, moveDy;
	private int moveDuration;
	private double speed;
	private boolean moving;
	private long movement_time;
	private int prevMovementDirection;
	
	public Opponent()
	{
		prevMovementDirection = -1;
		movement_time = 0;
		moving = false;
		moveDuration = 2000;
		moveDx = moveDy = 0;
		xPos = yPos = 100;
		hitBox = 20;
		maxHp = 0;
		remainingHp = 0;
		attack = 0;
		speed = 0.5;
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
		int angle = 0;
		//360 degrees:rand angle will be from 0 to 359 degrees
		if(prevMovementDirection == -1)
		{
			System.out.println("first angle");
			angle = (int)(Math.random()*360);
		}
		else
		{
			System.out.println("modified angle");
			angle = (int)(Math.random()*180 + prevMovementDirection + 90)%360;
		}
		prevMovementDirection = angle;
		moveDx = Math.cos(angle*Math.PI/180.0);// convert to radians
		moveDy = Math.sin(angle*Math.PI/180.0);
		System.out.println(angle + "\tmoveDx:" + moveDx + "\tmoveDy:" + moveDy);
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
