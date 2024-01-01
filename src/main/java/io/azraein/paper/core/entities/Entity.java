package io.azraein.paper.core.entities;

import org.tinylog.Logger;

import io.azraein.paper.core.background.CharacterBackground;
import io.azraein.paper.core.entities.stats.Characteristics;
import io.azraein.paper.core.entities.stats.Skills;
import io.azraein.paper.core.entities.status_buffs.Buff;
import io.azraein.paper.core.entities.status_buffs.Debuff;
import io.azraein.paper.core.entities.status_buffs.StatusConditions;
import io.azraein.paper.core.items.Item;
import io.azraein.paper.core.items.container.Inventory;
import io.azraein.paper.core.items.equipment.EquipType;
import io.azraein.paper.core.items.equipment.Equipment;
import io.azraein.paper.core.items.weapons.Weapon;
import io.azraein.paper.core.system.DiceUtils;
import io.azraein.paper.core.system.Registry;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Entity {

	private final String entityId;
	private String entityName;

	// Entity Inventories
	private final Inventory entityInventory;
	private final ObjectProperty<Equipment>[] entityEquipment;
	private final ObjectProperty<Weapon> entityEquippedWeapon;

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

	// Status Conditions, Buffs and Debuffs
	private final ObservableList<StatusConditions> entityStatusConditions = FXCollections.observableArrayList();
	private final ObservableList<Buff> entityBuffs = FXCollections.observableArrayList();
	private final ObservableList<Debuff> entityDebuffs = FXCollections.observableArrayList();

	@SuppressWarnings("unchecked")
	public Entity(String entityId, String entityName, CharacterBackground entityBackground) {
		this.entityId = entityId;
		this.entityName = entityName;
		this.entityBackground = entityBackground;

		entityEquipment = new SimpleObjectProperty[EquipType.values().length];
		for (EquipType equipType : EquipType.values())
			entityEquipment[equipType.ordinal()] = new SimpleObjectProperty<Equipment>();

		entityEquippedWeapon = new SimpleObjectProperty<>();

		entityStateProperty = new SimpleObjectProperty<>(EntityState.ALIVE);

		entityCharacteristics = new int[Characteristics.values().length];
		entitySkills = new int[Skills.values().length];
		entitySkillModifiers = new int[Skills.values().length];

		entityMaxHealthPoints = 100;
		entityCurrentHealthPoints = new SimpleIntegerProperty(entityMaxHealthPoints);

		entityMaxManaPoints = 50;
		entityCurrentManaPoints = new SimpleIntegerProperty(entityMaxManaPoints);

		entityGold = new SimpleIntegerProperty();
		entityGold.set(25);

		// Setup Listeners
		entityCurrentHealthPoints.addListener((obs, oldValue, newValue) -> {

			if (newValue.intValue() <= 0 && !entityStateProperty.get().equals(EntityState.ESSENTIAL)) {
				entityStateProperty.set(EntityState.DEAD);
				entityCurrentHealthPoints.set(0);
				onEntityDeath();
			} else if (newValue.intValue() == 0 && entityStateProperty.get().equals(EntityState.ESSENTIAL))
				entityCurrentHealthPoints.set(5); // TODO: Passive healing etc, etc, etc,

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

		entityInventory = new Inventory();

	}

	// Abstract Entity Methods

	public abstract void onEntityDeath();

	// Public Entity Methods

	public int attackEntity(Entity opponent) {
		// First check to see if the we are using a weapon
		Logger.debug(this.entityName + ": attacking " + opponent.getEntityName());
		int damage = 0;
		if (entityEquippedWeapon.get() != null) {
			var weapon = entityEquippedWeapon.get();

			Logger.debug("Using Weapon: " + weapon.getItemName());
			boolean canAttack = false;
			switch (weapon.getWeaponType()) {
			case MELEE:
				canAttack = DiceUtils.rollSkillCheck(this, Skills.MELEE);
				break;
			case RANGED:
				canAttack = DiceUtils.rollSkillCheck(this, Skills.RANGED);
				break;
			}

			if (canAttack) {
				Logger.debug("Attacking!");
				damage = DiceUtils.rollDice(weapon.getWeaponDamageRoll());
				opponent.damageEntity(damage);
				return damage;
			}

		} else {
			Logger.debug("We're gonna use the o'le fisties!");
			// We're not using a weapon so it's hand to hand combat.
			String hthDmgRoll = "2d4";
			if (DiceUtils.rollSkillCheck(this, Skills.MELEE)) {
				Logger.debug("BOOM! POW! SMACK! RIGHT IN DA KISSER!");
				damage = DiceUtils.rollDice(hthDmgRoll);
				opponent.damageEntity(damage);
				return damage;
			}
		}

		return 0;
	}

	public void healEntity(int hp) {
		entityCurrentHealthPoints.set(entityCurrentHealthPoints.get() + hp);
	}

	public void damageEntity(int hp) {
		entityCurrentHealthPoints.set(entityCurrentHealthPoints.get() - hp);
	}

	public void addEntityMana(int mp) {
		entityCurrentManaPoints.set(entityCurrentManaPoints.get() + mp);
	}

	public void subEntityMana(int mp) {
		entityCurrentManaPoints.set(entityCurrentManaPoints.get() - mp);
	}

	public void modifySkillModifier(Skills skill, int modifier) {
		entitySkillModifiers[skill.ordinal()] += modifier;
	}

	public void addGold(int goldAmt) {
		entityGold.set(entityGold.get() + goldAmt);
	}

	public void addStatusCondition(StatusConditions statusCondition) {
		if (!entityStatusConditions.contains(statusCondition))
			entityStatusConditions.add(statusCondition);
	}

	public void removeStatusCondition(StatusConditions statusCondition) {
		entityStatusConditions.remove(statusCondition);
	}

	public void addBuff(Buff buff) {
		if (!entityBuffs.contains(buff))
			entityBuffs.add(buff);
	}

	public void removeBuff(Buff buff) {
		entityBuffs.remove(buff);
	}

	public void addDebuff(Debuff debuff) {
		if (!entityDebuffs.contains(debuff))
			entityDebuffs.add(debuff);
	}

	public void removeDebuff(Debuff debuff) {
		entityDebuffs.remove(debuff);
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

	public void equipEntityWeapon(Weapon weapon) {

	}

	public void unequipEntityWeapon() {

	}

	// Private Entity Methods

	private void unequipEquipment(EquipType equipType, String itemId) {
		Equipment e = (Equipment) Registry.getItem(itemId);
		if (e.getEquipmentSkillType() != null)
			modifySkillModifier(e.getEquipmentSkillType(), -e.getEquipmentSkillBonus());

		modifySkillModifier(Skills.DODGE, -e.getEquipmentDefenceBonus());
		addItem(e, 1);
	}

	private void equipEquipment(EquipType equipType, String itemId) {
		Equipment e = (Equipment) Registry.getItem(itemId);
		if (e.getEquipmentSkillType() != null)
			modifySkillModifier(e.getEquipmentSkillType(), e.getEquipmentSkillBonus());

		modifySkillModifier(Skills.DODGE, e.getEquipmentDefenceBonus());
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

	public Weapon getEntityWeapon() {
		return entityEquippedWeapon.get();
	}

	public ObjectProperty<Weapon> entityEquippedWeaponProperty() {
		return entityEquippedWeapon;
	}

	public ObjectProperty<Equipment> entityEquipmentProperty(EquipType equipType) {
		return entityEquipment[equipType.ordinal()];
	}

	public ObservableList<StatusConditions> getEntityStatusConditions() {
		return entityStatusConditions;
	}

	public ObservableList<Buff> getEntityBuffs() {
		return entityBuffs;
	}

	public ObservableList<Debuff> getEntityDebuffs() {
		return entityDebuffs;
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
