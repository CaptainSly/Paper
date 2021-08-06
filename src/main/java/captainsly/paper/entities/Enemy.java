package captainsly.paper.entities;

import java.util.ArrayList;
import java.util.List;

import captainsly.paper.mechanics.items.Item;

public class Enemy extends Actor {

	private String enemyDescription;
	private List<Item> enemyLootList;

	public Enemy(String actorId, String actorName) {
		super(actorId, actorName);
		enemyLootList = new ArrayList<Item>();
	}

	public void setEnemyDescription(String enemyDescription) {
		this.enemyDescription = enemyDescription;
	}

	public String getEnemyDescription() {
		return enemyDescription;
	}

	public List<Item> getEnemyLootList() {
		return enemyLootList;
	}

}
