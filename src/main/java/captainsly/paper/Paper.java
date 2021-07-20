package captainsly.paper;

import captainsly.paper.entities.Player;
import captainsly.paper.mechanics.Registry;
import captainsly.paper.nodes.WorldNode;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Paper extends Application {

	private BorderPane paperRootPane;
	private WorldNode worldNode;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		paperRootPane = new BorderPane();
		Registry.register();
		
		Player player = new Player();

		worldNode = new WorldNode(player, Registry.locationRegistry.get("townCalinfor"));

		paperRootPane.setCenter(worldNode);
		
		Scene scene = new Scene(paperRootPane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Reignleif - TextBased Adventure");
		primaryStage.show();
	}

}
