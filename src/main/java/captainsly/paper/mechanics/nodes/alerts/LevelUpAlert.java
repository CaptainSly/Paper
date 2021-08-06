package captainsly.paper.mechanics.nodes.alerts;

import java.util.ArrayList;
import java.util.List;

import com.bernardomg.tabletop.dice.history.RollHistory;
import com.bernardomg.tabletop.dice.interpreter.DiceRoller;
import com.bernardomg.tabletop.dice.parser.DefaultDiceParser;

import captainsly.paper.entities.Player;
import captainsly.paper.entities.stats.Skill;
import captainsly.paper.entities.stats.Stat;
import captainsly.paper.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;

public class LevelUpAlert extends Alert {

	private Player player;
	private GridPane statGrid;

	// The Player will be able to put points into a skill to level them up.
	// They have so many stat points that they can put into each skill. The amount
	// of stat points is influenced by the Intelligence skill

	private int skillPoints;
	private Label[] skillLabels;
	private List<Spinner<Integer>> skillSpinners;

	public LevelUpAlert(Player player) {
		super(AlertType.INFORMATION);
		this.player = player;
		this.setTitle("Congratulations " + player.getActorName() + ", you've leveled up to Level "
				+ player.getActorStat(Stat.LEVEL));
		this.setHeaderText("ASCENDED TO LEVEL " + player.getActorStat(Stat.LEVEL));
		this.setGraphic(null);

		// TODO: Maybe see if it'd be better to have the skillpoints be tied to your
		// level or a stat or something

		skillPoints = 10;
		statGrid = new GridPane();
		statGrid.setHgap(10);
		statGrid.setVgap(10);
		statGrid.setPadding(new Insets(10, 10, 10, 10));

		setupStatGrid();

		this.getButtonTypes().clear();
		this.getButtonTypes().add(ButtonType.CLOSE);
		Node closeButton = getDialogPane().lookupButton(ButtonType.CLOSE);
		closeButton.setDisable(true);

		this.getDialogPane().setContent(statGrid);

	}

	public void showDialog() {
		this.showAndWait().filter(response -> response == ButtonType.CLOSE).ifPresent(response -> {
			for (int i = 0; i < Skill.values().length; i++) {
				Skill skill = Skill.values()[i];
				Spinner<Integer> spinner = skillSpinners.get(i);
				player.modifyActorSkill(skill, spinner.getValue());
			}
		});
	}

	private void setupStatGrid() {
		// Roll some dice for the Stats and then allow the player to put points in to
		// there skills

		Label playerHpLabel = new Label();
		Label playerMpLabel = new Label();
		Label playerAtkLabel = new Label();
		Label playerDefLabel = new Label();
		Label playerSpdLabel = new Label();
		Label playerWisLabel = new Label();

		int hpIncrease = rollStat(Stat.MAX_HP);
		int mpIncrease = rollStat(Stat.MAX_MP);
		int atkIncrease = rollStat(Stat.ATK);
		int defIncrease = rollStat(Stat.DEF);
		int spdIncrease = rollStat(Stat.SPD);
		int wisIncrease = rollStat(Stat.WIS);

		playerHpLabel.setText(
				"HP: " + player.getActorStat(Stat.MAX_HP) + "/" + player.getActorStat(Stat.HP) + " | +" + hpIncrease);
		playerMpLabel.setText(
				"MP: " + player.getActorStat(Stat.MAX_MP) + "/" + player.getActorStat(Stat.MP) + " | +" + mpIncrease);

		playerAtkLabel.setText("ATK: " + player.getActorStat(Stat.ATK) + " | +" + atkIncrease);
		playerDefLabel.setText("DEF: " + player.getActorStat(Stat.DEF) + " | +" + defIncrease);
		playerSpdLabel.setText("SPD: " + player.getActorStat(Stat.SPD) + " | +" + spdIncrease);
		playerWisLabel.setText("WIS: " + player.getActorStat(Stat.WIS) + " | +" + wisIncrease);

		Label skillPointLabel = new Label("SkillPoints: " + skillPoints);

		skillLabels = new Label[Skill.values().length];
		skillSpinners = new ArrayList<Spinner<Integer>>();
		for (int i = 0; i < Skill.values().length; i++) {
			Skill skill = Skill.values()[i];
			skillLabels[i] = new Label(Utils.toNormalCase(skill.name()) + ": " + player.getActorSkill(skill) + " | +");
			Spinner<Integer> spinner = new Spinner<Integer>(0, skillPoints, 0);
			spinner.valueProperty().addListener(new ChangeListener<Integer>() {

				@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					Node closeButton = getDialogPane().lookupButton(ButtonType.CLOSE);

					if (newValue < oldValue) {
						skillPoints++;
					} else if (newValue > oldValue) {
						skillPoints--;
						if (skillPoints < 0) {
							spinner.getValueFactory().setValue(oldValue);
							skillPoints = 0;
						}
					}

					skillPointLabel.setText("SkillPoints: " + skillPoints);
					closeButton.setDisable(skillPoints == 0 ? false : true);
				}
			});

			skillSpinners.add(spinner);
			statGrid.add(skillLabels[i], 0, i + 4);
		}

		int i = 4;
		for (Spinner<Integer> spinner : skillSpinners) {
			statGrid.add(spinner, 1, i);
			i++;
		}

		statGrid.add(skillPointLabel, 0, 0);
		statGrid.add(playerHpLabel, 0, 1);
		statGrid.add(playerMpLabel, 1, 1);
		statGrid.add(playerAtkLabel, 0, 2);
		statGrid.add(playerDefLabel, 1, 2);
		statGrid.add(playerSpdLabel, 0, 3);
		statGrid.add(playerWisLabel, 1, 3);

	}

	private int rollStat(Stat stat) {
		// TODO: The stats are extremely unbalanced. You take a d3 and roll it. Then add
		// the level.
		RollHistory roll = new DiceRoller()
				.transform(new DefaultDiceParser().parse("1d3+" + player.getActorStat(Stat.LEVEL)));
		player.modifyActorStat(stat, roll.getTotalRoll());
		return roll.getTotalRoll();
	}

}
