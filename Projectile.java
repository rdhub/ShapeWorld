import javafx.scene.image.*;

public class Projectile
{
	private double xPosition, yPosition;
	private double targetX, targetY;
	private double dx, dy, speed, travelDist, maxTravelDist;
	private int size;
	private Image image;
	
	public Projectile()
	{
		targetX = targetY = 0;
		dx = dy = travelDist = 0;
		speed = 4;
		size = 10;
	}
	
	public Projectile(double xPosition, double yPosition, Image im, double maxShotDist)
	{
		this();
		this.xPosition = xPosition - size/2;
		this.yPosition = yPosition - size/2;
		image = im;
		maxTravelDist = maxShotDist;
	}
	
	public double getTravelDist()
	{
		return travelDist;
	}
	
	public void updateTravelDist(double dist)
	{
		travelDist += dist;
	}
	public void setTargetX(double targetX)
	{
		this.targetX = targetX - size/2;
	}
	
	public void setTargetY(double targetY)
	{
		this.targetY = targetY - size/2; // Centers the shot
	}
	public void calculate()
	{
		double diffX = targetX - xPosition;
		double diffY = targetY - yPosition;
		double dist = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
		dx = diffX/dist*speed;
		dy = diffY/dist*speed;
	}
	public void updateCoords()
	{
		xPosition += dx;
		yPosition += dy;
		updateTravelDist(Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)));
	}
	
	public void setXPosition(double x)
	{
		xPosition = x;
	}
	
	public void setYPosition(double y)
	{
		yPosition = y;
	}
	
	public void updateXPosition(double x)
	{
		xPosition += x;
	}
	
	public void updateYPosition(double y)
	{
		yPosition += y;
	}
	
	public double getXPosition()
	{
		return xPosition;
	}
	
	public double getYPosition()
	{
		return yPosition;
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public double getTargetX()
	{
		return targetX;
	}
	
	public double getTargetY()
	{
		return targetY;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public boolean isAtMaxShotDist()
	{
		return travelDist >= maxTravelDist;
	}
}
