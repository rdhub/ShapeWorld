import javafx.scene.image.*;

public class Projectile
{
	private double centerX, centerY;
	private double targetX, targetY;
	private double dx, dy, dist, speed;
	private int size;
	private Image image;
	
	public Projectile()
	{
		targetX = targetY = 0;
		dx = dy = dist = 0;
		speed = 2;
		size = 10;
	}
	
	public Projectile(double centerX, double centerY, Image im)
	{
		this();
		this.centerX = centerX;
		this.centerY = centerY;
		speed = 2;
		size = 10;
		image = im;
	}
	
	public void setTargetX(double targetX)
	{
		this.targetX = targetX;
	}
	
	public void setTargetY(double targetY)
	{
		this.targetY = targetY;
	}
	public void calculate()
	{
		double diffX = targetX-centerX;
		double diffY = targetY-centerY;
		dist = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
		dx = diffX/dist*speed;
		dy = diffY/dist*speed;
	}
	public void updateCoords()
	{
		centerX += dx;
		centerY += dy;
	}
	
	public void setCenterX(double x)
	{
		centerX = x;
	}
	
	public void setCenterY(double y)
	{
		centerY = y;
	}
	
	public double getCenterX()
	{
		return centerX;
	}
	
	public double getCenterY()
	{
		return centerY;
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
}
