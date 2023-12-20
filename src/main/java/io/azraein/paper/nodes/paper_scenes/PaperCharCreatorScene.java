package io.azraein.paper.nodes.paper_scenes;

import java.util.Objects;
import java.util.function.UnaryOperator;

import io.azraein.paper.core.background.CharacterBackground;
import io.azraein.paper.core.background.CharacterBackgroundFactory;
import javafx.util.Callback;
import org.tinylog.Logger;

import io.azraein.paper.PaperApp;
import io.azraein.paper.core.Paper;
import io.azraein.paper.core.entities.Player;
import io.azraein.paper.core.entities.stats.Characteristics;
import io.azraein.paper.core.entities.stats.Skills;
import io.azraein.paper.core.system.DiceUtils;
import io.azraein.paper.nodes.char_nodes.SkillSpinner;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PaperCharCreatorScene extends PaperScene {

	private final IntegerProperty[] charaDummy;
	private final IntegerProperty skillPoints = new SimpleIntegerProperty();

	private CharacterBackground characterBackground;

	public PaperCharCreatorScene(PaperApp paperApp) {
		super(paperApp, new VBox());
		VBox root = (VBox) rootContent;
		root.setPadding(new Insets(10));

		skillPoints.set(110);
		charaDummy = new SimpleIntegerProperty[Characteristics.values().length];
		IntegerProperty[] skillDummy = new SimpleIntegerProperty[Skills.values().length];
		IntegerProperty[] skillModDummy = new SimpleIntegerProperty[Skills.values().length];

		for (Characteristics chara : Characteristics.values())
			charaDummy[chara.ordinal()] = new SimpleIntegerProperty();

		for (Skills skill : Skills.values()) {
			skillDummy[skill.ordinal()] = new SimpleIntegerProperty();
			skillModDummy[skill.ordinal()] = new SimpleIntegerProperty();
		}

		// Character Information
		HBox playerInfoHBox = new HBox();
		playerInfoHBox.setPadding(new Insets(10));

		Label playerNameLbl = new Label("Name: ");
		playerNameLbl.setPadding(new Insets(10));

		TextField playerNameField = new TextField();
		playerNameField.setPadding(new Insets(10));
		playerNameField.setPromptText("Player Name...");
		playerNameField.setTextFormatter(new TextFormatter<Change>(new FixedLengthFilter()));

		playerInfoHBox.getChildren().addAll(playerNameLbl, playerNameField);

		// Character Stat Information
		HBox playerStatBox = new HBox();
		GridPane playerStatsGrid = new GridPane();
		GridPane playerSkillsGrid = new GridPane();

		playerStatsGrid.setPadding(new Insets(10));
		playerStatsGrid.setHgap(10);
		playerStatsGrid.setVgap(10);

		ScrollPane playerSkillsScroll = new ScrollPane(playerSkillsGrid);
		playerSkillsScroll.setMaxHeight(512);
		playerSkillsScroll.setMaxWidth(600);

		playerSkillsGrid.setPadding(new Insets(10));
		playerSkillsGrid.setHgap(10);
		playerSkillsGrid.setVgap(10);

		Label skillPointLbl = new Label("Skill Points: " + skillPoints.intValue());
		skillPoints.addListener((obs, oldValue, newValue) -> {
			if (newValue != null)
				skillPointLbl.setText("Skill Points: " + skillPoints.intValue());
		});

		Label[] charaLbls = new Label[Characteristics.values().length];
		for (Characteristics chara : Characteristics.values())
			charaLbls[chara.ordinal()] = new Label(chara.name() + ": 0");

		SkillSpinner[] skillSpinners = new SkillSpinner[Skills.values().length];
		for (Skills skill : Skills.values()) {
			skillSpinners[skill.ordinal()] = new SkillSpinner(skill, this);
			skillSpinners[skill.ordinal()].setSpinnerDisable(true);

			skillDummy[skill.ordinal()].addListener((obs, oldValue, newValue) -> {

				if (newValue != null) {
					skillSpinners[skill.ordinal()].setValue(newValue.intValue());
				}

			});
		}

		Button generateStatsBtn = new Button("Generate Stats");

		// Create the Character
		Button createCharacterBtn = new Button("Create Character");
		createCharacterBtn.setDisable(true);
		createCharacterBtn.setOnAction(e -> {
			String playerName = playerNameField.getText().trim();

			if (playerName.isEmpty()) playerName = "Player";
			
			Player player = new Player(playerName, characterBackground);

			for (Characteristics chara : Characteristics.values())
				player.getEntityCharacteristics()[chara.ordinal()] = charaDummy[chara.ordinal()].intValue();

			// Character Background Stuff
			for (Skills skill : Skills.values()) {
				skillDummy[skill.ordinal()].set(skillSpinners[skill.ordinal()].getValue());

				player.getEntitySkills()[skill.ordinal()] = skillDummy[skill.ordinal()].get()
						+ player.getEntityCharacterBackground().getBackgroundSkillsPoints()[skill.ordinal()];
			}

			if (skillPoints.get() > 0) {

				while (skillPoints.get() > 0) {
					Skills skill = Skills.values()[Paper.rnJesus.nextInt(Skills.values().length)];
					int distributingPoints = Paper.rnJesus.nextInt(1, 35);

					if (distributingPoints > skillPoints.get())
						continue;

					int isHigher = skillDummy[skill.ordinal()].add(distributingPoints).get();

					if (isHigher <= 65) {
						skillDummy[skill.ordinal()].add(distributingPoints);
						skillPoints.set(skillPoints.subtract(distributingPoints).get());
					} else
						continue;

				}
			}

			paperApp.playerProperty().set(player);

			paperApp.changePaperScene("gameScene");
		});

		generateStatsBtn.setOnAction(e -> {
			charaDummy[Characteristics.STR.ordinal()].set(DiceUtils.rollDice("3d6*5"));
			charaDummy[Characteristics.CON.ordinal()].set(DiceUtils.rollDice("3d6*5"));
			charaDummy[Characteristics.SIZ.ordinal()].set(DiceUtils.rollDice("(2d6+6)*5"));
			charaDummy[Characteristics.DEX.ordinal()].set(DiceUtils.rollDice("3d6*5"));
			charaDummy[Characteristics.APP.ordinal()].set(DiceUtils.rollDice("3d6*5"));
			charaDummy[Characteristics.INT.ordinal()].set(DiceUtils.rollDice("(2d6+6)*5"));
			charaDummy[Characteristics.POW.ordinal()].set(DiceUtils.rollDice("3d6*5"));
			charaDummy[Characteristics.EDU.ordinal()].set(DiceUtils.rollDice("(2d6+6)*5"));

			for (Characteristics chara : Characteristics.values())
				charaLbls[chara.ordinal()].setText(chara.name() + ": " + charaDummy[chara.ordinal()].intValue());

			for (Skills skill : Skills.FIGHTING_SKILLS()) {
				int mod = Paper.rnJesus.nextInt(2) + 2;
				skillSpinners[skill.ordinal()].setValue(charaDummy[Characteristics.STR.ordinal()].intValue() / mod);
			}

			for (Skills skill : Skills.SOCIAL_SKILLS()) {
				int mod = Paper.rnJesus.nextInt(3) + 2;
				skillSpinners[skill.ordinal()].setValue(charaDummy[Characteristics.APP.ordinal()].intValue() / mod);
			}

			for (Skills skill : Skills.INVESTIGATION_SKILLS()) {
				int mod = Paper.rnJesus.nextInt(3) + 2;
				skillSpinners[skill.ordinal()].setValue(charaDummy[Characteristics.INT.ordinal()].intValue() / mod);
			}

			for (Skills skill : Skills.FIRST_AID_SKILLS()) {
				int mod = Paper.rnJesus.nextInt(4) + 2;
				skillSpinners[skill.ordinal()].setValue(charaDummy[Characteristics.CON.ordinal()].intValue() / mod);
			}

			for (Skills skill : Skills.STEALTH_SKILLS()) {
				int mod = Paper.rnJesus.nextInt(3) + 2;
				skillSpinners[skill.ordinal()].setValue(charaDummy[Characteristics.DEX.ordinal()].intValue() / mod);
			}

			for (Skills skill : Skills.GATHERING_SKILLS()) {
				int mod = Paper.rnJesus.nextInt(3) + 2;
				skillSpinners[skill.ordinal()].setValue(charaDummy[Characteristics.SIZ.ordinal()].intValue() / mod);
			}

			for (Skills skill : Skills.PROCESSING_SKILLS()) {
				int mod = Paper.rnJesus.nextInt(4) + 2;
				skillSpinners[skill.ordinal()].setValue(charaDummy[Characteristics.EDU.ordinal()].intValue() / mod);
			}

			for (Skills skill : Skills.MAGIC_SKILLS()) {
				int mod = Paper.rnJesus.nextInt(3) + 2;
				skillSpinners[skill.ordinal()].setValue(charaDummy[Characteristics.POW.ordinal()].intValue() / mod);
			}

			skillSpinners[Skills.DODGE.ordinal()].setValue(charaDummy[Characteristics.DEX.ordinal()].intValue() / 2);

			for (Skills skill : Skills.values()) {
				skillSpinners[skill.ordinal()].setSpinnerDisable(false);
				skillDummy[skill.ordinal()].set(skillSpinners[skill.ordinal()].getValue());
			}

			createCharacterBtn.setDisable(false);
		});

		// Character Background stuff
		ComboBox<String> characterBackgroundComboBox = new ComboBox<>();
		ObservableList<String> items = FXCollections.observableArrayList();
		items.addAll("Mage", "Thief");

		characterBackgroundComboBox.setItems(items);
		characterBackgroundComboBox.setPromptText("Background");
		characterBackgroundComboBox.setCellFactory(new Callback<>() {
			@Override
			public ListCell<String> call(ListView<String> param) {
				return new ListCell<>() {

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						this.setText(Objects.requireNonNullElse(item, ""));
					}
				};
			}
		});

		characterBackgroundComboBox.getSelectionModel().selectedItemProperty()
				.addListener(((observable, oldValue, newValue) -> {
					Logger.debug("Quack");
					if (newValue != null) {
						Logger.debug(newValue);
						CharacterBackgroundFactory cbf = new CharacterBackgroundFactory();
						if (newValue.equalsIgnoreCase("Thief"))
							characterBackground = cbf.createThiefBackground();
						else if (newValue.equalsIgnoreCase("Mage"))
							characterBackground = cbf.createMageBackground();

						characterBackground.generateSkillPoints();

						for (Skills skill : Skills.values()) {
							skillSpinners[skill.ordinal()]
									.setModifier(characterBackground.getBackgroundSkillsPoints()[skill.ordinal()]);
							skillModDummy[skill.ordinal()]
									.set(characterBackground.getBackgroundSkillsPoints()[skill.ordinal()]);
						}

					}

				}));

		for (int i = 0; i < Characteristics.values().length; i++)
			playerStatsGrid.add(charaLbls[Characteristics.values()[i].ordinal()], 0, i);

		playerStatsGrid.add(skillPointLbl, 0, Characteristics.values().length + 1);
		playerStatsGrid.add(generateStatsBtn, 0, Characteristics.values().length + 2);
		playerStatsGrid.add(characterBackgroundComboBox, 0, Characteristics.values().length + 3);

		for (int i = 0; i < Skills.values().length; i++) {

			int row = i + 1;
			if (i < 10) {
				playerSkillsGrid.add(skillSpinners[i], 0, row);
			} else if (i < 20 && i >= 10) {
				row = (i + 1) - 10;
				playerSkillsGrid.add(skillSpinners[i], 1, row);
			} else {
				row = (i + 1) - 20;
				playerSkillsGrid.add(skillSpinners[i], 2, row);
			}

		}

		playerStatBox.getChildren().addAll(playerStatsGrid, playerSkillsScroll);

		root.getChildren().addAll(playerInfoHBox, playerStatBox, createCharacterBtn);
	}

	public IntegerProperty skillPointsProperty() {
		return skillPoints;
	}

	private static class FixedLengthFilter implements UnaryOperator<Change> {

		@Override
		public Change apply(Change t) {
			if (t.getControlNewText().length() > 10)
				return null;
			else
				return t;
		}

	}
}
