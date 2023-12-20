package io.azraein.paper.core.entities;

import org.tinylog.Logger;

import io.azraein.paper.core.background.CharacterBackground;
import io.azraein.paper.core.entities.stats.Characteristics;
import io.azraein.paper.core.entities.stats.Skills;
import io.azraein.paper.core.items.Item;
import io.azraein.paper.core.items.container.Inventory;
import io.azraein.paper.core.items.equipment.EquipType;
import io.azraein.paper.core.items.equipment.Equipment;
import io.azraein.paper.core.system.Registry;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public abstract class Entity {

	private final String entityId;
	private String entityName;

	// Entity Inventories
	private final Inventory entityInventory;
	private final ObjectProperty<Equipment>[] entityEquipment;

	private final ObjectProperty<EntityState> entityStateProperty;

	// Entity Stats
	private CharacterBackground entityBackground;

	private int[] entityCharacteristics;
	private int[] entitySkills;
	private int[] entitySkillModifiers;

	private int entityMaxHealthPoints;
	private final IntegerProperty entityCurrentHealthPoints;

	private int entityMaxManaPoints;
	private final IntegerProperty entityCurrentManaPoints;

	private final IntegerProperty entityGold;

	@SuppressWarnings("unchecked")
	public Entity(String entityId, String entityName, CharacterBackground entityBackground) {
		this.entityId = entityId;
		this.entityName = entityName;
		this.entityBackground = entityBackground;

		entityEquipment = new SimpleObjectProperty[EquipType.values().length];
		for (EquipType equipType : EquipType.values())
			entityEquipment[equipType.ordinal()] = new SimpleObjectProperty<Equipment>();

		entityStateProperty = new SimpleObjectProperty<>(EntityState.ALIVE);

		// Setup Default Entity Stats
		// TODO: Add in the characterBackground scores to skills
		entityMaxHealthPoints = 100;
		entityCurrentHealthPoints = new SimpleIntegerProperty(entityMaxHealthPoints);

		entityMaxManaPoints = 50;
		entityCurrentManaPoints = new SimpleIntegerProperty(entityMaxManaPoints);

		entityGold = new SimpleIntegerProperty();
		entityGold.set(25);

		// Setup Listeners
		entityCurrentHealthPoints.addListener((obs, oldValue, newValue) -> {

			if (newValue.intValue() == 0) {
				// TODO: Deathstate
				entityStateProperty.set(EntityState.DEAD);
			}

			Logger.debug("Entity Health: " + newValue);
		});

		for (EquipType equipType : EquipType.values()) {
			entityEquipment[equipType.ordinal()].addListener((obs, oldValue, newValue) -> {

				if (oldValue != null && newValue != null) {
					// Swapping Equipment
					Logger.debug("Swapping Equipment");
					unequipEquipment(oldValue.getEquipmentEquipType(), oldValue.getItemId());
					equipEquipment(newValue.getEquipmentEquipType(), newValue.getItemId());
				} else if (oldValue == null && newValue != null) {
					// Adding Equipment
					Logger.debug("Equipping:  " + newValue.getItemName());
					equipEquipment(newValue.getEquipmentEquipType(), newValue.getItemId());
				} else if (oldValue != null && newValue == null) {
					// Unequip Equpment
					Logger.debug("Unequipping: " + oldValue.getItemName());
					unequipEquipment(oldValue.getEquipmentEquipType(), oldValue.getItemId());
				}

			});
		}

		entityCharacteristics = new int[Characteristics.values().length];
		entitySkills = new int[Skills.values().length];
		entitySkillModifiers = new int[Skills.values().length];

		entityInventory = new Inventory();

	}

	// Public Entity Methods

	public void attackEntity(Entity opponent) {
		// TODO: Figure out damage, etc. for when attacking an opponent.
		opponent.subEntityHealth(5);
	}

	public void addEntityHealth(int hp) {
		entityCurrentHealthPoints.set(entityCurrentHealthPoints.get() + hp);
	}

	public void subEntityHealth(int hp) {
		entityCurrentHealthPoints.set(entityCurrentHealthPoints.get() - hp);
	}

	public void addEntityMana(int mp) {
		entityCurrentManaPoints.set(entityCurrentManaPoints.get() + mp);
	}

	public void subEntityMana(int mp) {
		entityCurrentManaPoints.set(entityCurrentManaPoints.get() - mp);
	}

	public void addItem(Item item, int amount) {
		if (amount == 0)
			amount = 1;
		entityInventory.addItem(item.getItemId(), amount);
	}

	public void removeItem(Item item, int amount) {
		entityInventory.removeItem(item.getItemId(), amount);
	}

	public void equipEntityEquipment(Equipment equipment) {
		entityEquipment[equipment.getEquipmentEquipType().ordinal()].set(equipment);
	}

	public void unequipEntityEquipment(EquipType equipType) {
		entityEquipment[equipType.ordinal()].set(null);
	}

	// Private Entity Methods

	private void unequipEquipment(EquipType equipType, String itemId) {
		Equipment e = (Equipment) Registry.getItem(itemId);
		if (e.getEquipmentSkillType() != null)
			entitySkillModifiers[e.getEquipmentSkillType().ordinal()] -= e.getEquipmentSkillBonus();

		addItem(e, 1);
	}

	private void equipEquipment(EquipType equipType, String itemId) {
		Equipment e = (Equipment) Registry.getItem(itemId);
		if (e.getEquipmentSkillType() != null)
			entitySkillModifiers[e.getEquipmentSkillType().ordinal()] += e.getEquipmentSkillBonus();

		removeItem(e, 1);
	}

	// Getters and Setters

	public void setEntityCharacterBackground(CharacterBackground entityBackground) {
		this.entityBackground = entityBackground;
	}

	public String getEntityId() {
		return entityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public EntityState getEntityState() {
		return entityStateProperty.get();
	}

	public CharacterBackground getEntityCharacterBackground() {
		return entityBackground;
	}

	public ObjectProperty<EntityState> entityStateProperty() {
		return entityStateProperty;
	}

	public Inventory getEntityInventory() {
		return entityInventory;
	}

	public IntegerProperty entityCurrentHealthPointsProperty() {
		return entityCurrentHealthPoints;
	}

	public int getEntityMaxHealthPoints() {
		return entityMaxHealthPoints;
	}

	public int getEntityCurrentHealthPoints() {
		return entityCurrentHealthPoints.get();
	}

	public IntegerProperty entityCurrentManaPointsProperty() {
		return entityCurrentManaPoints;
	}

	public int getEntityMaxManaPoints() {
		return entityMaxManaPoints;
	}

	public int getEntityCurrentManaPoints() {
		return entityCurrentManaPoints.get();
	}

	public Equipment getEntityEquipment(EquipType equipType) {
		return entityEquipment[equipType.ordinal()].get();
	}

	public ObjectProperty<Equipment> entityEquipmentProperty(EquipType equipType) {
		return entityEquipment[equipType.ordinal()];
	}

	public IntegerProperty entityGoldProperty() {
		return entityGold;
	}

	public int getEntityGold() {
		return entityGold.get();
	}

	public int getEntityCharacteristic(Characteristics chara) {
		return entityCharacteristics[chara.ordinal()];
	}

	public int getEntitySkill(Skills skill) {
		return entitySkills[skill.ordinal()] + entitySkillModifiers[skill.ordinal()];
	}

	public int[] getEntitySkills() {
		return entitySkills;
	}

	public int[] getEntitySkillModifiers() {
		return entitySkillModifiers;
	}

	public int[] getEntityCharacteristics() {
		return entityCharacteristics;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

}
