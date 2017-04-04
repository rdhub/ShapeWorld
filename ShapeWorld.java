import java.util.*;

public class ShapeWorld {
	
	private ArrayList<Projectile> shots;
	private Player player;

	public ShapeWorld(int centerX, int centerY)
	{
		player = new Player(centerX, centerY);
		shots = new ArrayList<Projectile>();
	}
	
	public Player getPlayer()
	{
		return player;
	}
}
