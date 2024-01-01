package io.azraein.paper.nodes.char_nodes;

import org.tinylog.Logger;

import io.azraein.paper.core.entities.stats.Skills;
import io.azraein.paper.core.system.Utils;
import io.azraein.paper.scenes.PaperCharCreatorScene;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class SkillSpinner extends Region {

	private final HBox skillContainer;

	private Label skillLbl;
	private Label skillModLbl;
	private Spinner<Integer> skillSpinner;

	public SkillSpinner(Skills skill, PaperCharCreatorScene charScene) {
		skillContainer = new HBox();
		skillLbl = new Label(Utils.toNormalCase(skill.name()));
		skillModLbl = new Label("+0");
		skillSpinner = new Spinner<>();

		var skillPoints = charScene.skillPointsProperty();

		skillLbl.setPadding(new Insets(10));
		skillModLbl.setPadding(new Insets(10));
		skillSpinner.setPadding(new Insets(10));
		skillSpinner.setMaxWidth(110);
		skillSpinner.setValueFactory(new SpinnerValueFactoryExtension(skillPoints));

		skillContainer.getChildren().addAll(skillLbl, skillSpinner, skillModLbl);
		getChildren().add(skillContainer);
	}

	public void setModifier(int modifier) {
		skillModLbl.setText("+" + modifier);
	}

	public void setValue(int value) {
		skillSpinner.getValueFactory().setValue(value);
	}

	public void setSpinnerDisable(boolean disable) {
		skillSpinner.setDisable(disable);
	}

	public int getValue() {
		return skillSpinner.getValue();
	}

	private final class SpinnerValueFactoryExtension extends SpinnerValueFactory<Integer> {
		private final IntegerProperty skillPoints;

		private SpinnerValueFactoryExtension(IntegerProperty skillPoints) {
			this.skillPoints = skillPoints;
			valueProperty().addListener((obs, oldValue, newValue) -> {
				if (newValue != null) {
					if (newValue < 0)
						setValue(0);
				}
			});
			setValue(0);
		}

		@Override
		public void decrement(int steps) {
			Logger.debug("Steps: " + steps);
			Logger.debug("Total SkillPoints: " + skillPoints.get());

			final int newValue = getValue() - steps;

			if (newValue < 0)
				return;

			skillPoints.set(skillPoints.get() + steps);
			setValue(newValue);
		}

		@Override
		public void increment(int steps) {
			Logger.debug("Steps: " + steps);
			Logger.debug("Total SkillPoints: " + skillPoints.get());

			final int newValue = getValue() + steps;

			if (skillPoints.get() <= 0)
				return;

			skillPoints.set(skillPoints.get() - steps);
			setValue(newValue);
		}
	}

}
