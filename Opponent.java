public class Opponent
{
	private int maxHp, remainingHp, attack;
	private int hitBox, width, height;
	private double xPos, yPos;
	private double moveDx, moveDy;
	private int moveDuration;
	private double speed;
	private boolean moving;
	private long movement_time_stamp;
	private int prevMovementDirection;
	private int detectionRange;
	
	public Opponent()
	{
		detectionRange = 100;
		prevMovementDirection = -1;
		moving = false;
		moveDuration = 2000;
		movement_time_stamp = System.currentTimeMillis() - (long)(Math.random()*moveDuration); //randomizes initial time stamp so that multiple opponents will have different move patterns
		moveDx = moveDy = 0;
		xPos = yPos = 100;
		hitBox = 20;
		width = height = 20;
		maxHp = 0;
		remainingHp = 0;
		attack = 0;
		speed = 0.5;
	}
	
	public Opponent(int h, int a, double initialXPos, double initialYPos)
	{
		this();
		maxHp = h;
		remainingHp = maxHp;
		attack = a;
		xPos = initialXPos;
		yPos = initialYPos;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	public void setX(double x)
	{
		xPos = x;
	}
	
	public void setY(double y)
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
			angle = (int)(Math.random()*360);
		}
		else
		{
			angle = (int)(Math.random()*180 + prevMovementDirection + 90)%360;
		}
		prevMovementDirection = angle;
		moveDx = Math.cos(angle*Math.PI/180.0);// convert to radians
		moveDy = Math.sin(angle*Math.PI/180.0);
	}
	
	public boolean isInRangeOfPlayer(int playerX, int playerY)
	{
		double diffX = playerX - xPos;
		double diffY = playerY - yPos;
		return Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2)) <= detectionRange;
	}
	public void move()
	{
		xPos += moveDx*speed;
		yPos += moveDy*speed;
	}
	
	public void generatePlayerDirection(int playerCenterX, int playerCenterY)
	{
		double xCenter = xPos + hitBox/2;
		double yCenter = yPos + hitBox/2;
		double diffX = playerCenterX - xCenter;
		double diffY = playerCenterY - yCenter;
		double dist = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
		moveDx = diffX/dist;
		moveDy = diffY/dist;
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
	
	public long getMovementTimeStamp()
	{
		return movement_time_stamp;
	}
	
	public void setMovementTimeStamp(long time)
	{
		movement_time_stamp = time;
	}
	public void updateX(double x)
	{
		xPos += x;
	}
	
	public void updateY(double y)
	{
		yPos += y;
	}
	
	public boolean isHit(Projectile shot)
	{
		int shotSize = shot.getSize();
		
		if(shot.getX() < xPos + hitBox && //right edge of enemy
			shot.getX() + shotSize > xPos && //left edge
			shot.getY() < yPos + hitBox && //bottom edge
			shot.getY() + shotSize > yPos) //top edge
			return true;
		return false;
	}
}
