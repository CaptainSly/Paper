package captainsly.paper.mechanics;

import java.util.ArrayList;
import java.util.List;

public class Lootlist {

	private String lootlistId;
	private List<String> itemIds;
	
	public Lootlist(String lootlistId) {
		this.lootlistId = lootlistId;
		itemIds = new ArrayList<String>();
	}
	
	public void add(String id) {
		itemIds.add(id);
	}
	
	public List<String> getLootList() {
		return itemIds;
	}
	
	public String getLootListId() {
		return lootlistId;
	}

}
