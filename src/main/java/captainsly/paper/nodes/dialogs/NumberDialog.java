package captainsly.paper.nodes.dialogs;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class NumberDialog extends Dialog<Integer> {
	
	public NumberDialog(String title, String headerText) {
		this.setTitle(title);
		this.setHeaderText(headerText);
		
		this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		Node okayButton = getDialogPane().lookupButton(ButtonType.OK);
		okayButton.setDisable(true);
		
		TextField contextText = new TextField();
		contextText.setPromptText("Item Amount");
		contextText.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
					String newValue) {

				okayButton.setDisable(newValue.trim().isEmpty());
				if (!newValue.matches("\\d*")) {
					contextText.setText(newValue.replaceAll("[^\\d]", ""));
				}

			}
		});
		
		GridPane dialogGrid = new GridPane();
		dialogGrid.setHgap(10);
		dialogGrid.setVgap(10);
		dialogGrid.setPadding(new Insets(20, 150, 10, 10));
		
		dialogGrid.add(new Label("Item Amount:"), 0, 0);
		dialogGrid.add(contextText, 1, 0);
		
		this.getDialogPane().setContent(dialogGrid);
		
		Platform.runLater(() -> contextText.requestFocus());
		this.setResultConverter(dialogButton -> {
			if (dialogButton == ButtonType.OK)
				return Integer.parseInt(contextText.getText());

			return null;
		});
		
	}

}
