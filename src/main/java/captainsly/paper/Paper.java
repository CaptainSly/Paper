package captainsly.paper;

import captainsly.paper.entities.Player;
import captainsly.paper.mechanics.Registry;
import captainsly.paper.nodes.regions.WorldRegion;
import captainsly.paper.utils.Utils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Paper extends Application {

	private BorderPane paperRootPane;
	private WorldRegion worldNode;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		paperRootPane = new BorderPane();
		Registry.register();
		
		Player player = new Player();

		worldNode = new WorldRegion(player, Registry.locationRegistry.get("townCalinfor"));

		paperRootPane.setCenter(worldNode);
		
		Scene scene = new Scene(paperRootPane);
		scene.getStylesheets().add("style_dark.css");
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Reignleif - TextBased Adventure |v" + Utils.VERSION + "|");
		primaryStage.show();
	}

}
