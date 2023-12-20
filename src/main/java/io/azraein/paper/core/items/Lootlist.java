package io.azraein.paper.core.items;

import java.util.ArrayList;
import java.util.List;

public class Lootlist {

	private final List<String> lootlistItemIds;

	public Lootlist(String... itemIds) {
		lootlistItemIds = new ArrayList<>();
		for (String itemId : itemIds)
			lootlistItemIds.add(itemId);
	}

	public void addItem(String itemId) {
		lootlistItemIds.add(itemId);
	}

	public List<String> getLootlist() {
		return lootlistItemIds;
	}

}
