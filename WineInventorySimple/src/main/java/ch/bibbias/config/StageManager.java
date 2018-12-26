package ch.bibbias.config;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Map;

import org.slf4j.Logger;

import com.jeitziner.view.Desktop;

import ch.bibbias.view.FxmlLoaderImpl;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StageManager {

	private static final Logger LOG = getLogger(StageManager.class);
	private final Stage primaryStage;
	private final SpringFXMLLoader springFXMLLoader;
	
	/**
	 * Read "desktop.json" file and create all Desktop instances. 
	 * Store Desktop instances in a map, key = desktop name
	 */
	private final Map<String, Desktop> desktopMap;

	public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
		this.springFXMLLoader = springFXMLLoader;
		this.primaryStage = stage;

		// load all configures desktops from json file.
		LOG.info("Working Directory = " + System.getProperty("user.dir"));
		String jsonFilePath = "src/main/resources/config/desktop.json";
		LOG.info("Read desktop config from: " + jsonFilePath);
		this.desktopMap = Desktop.getDesktopsFromFile(jsonFilePath);
	}
	
	public void switchSceneByName(String desktopName) {
		LOG.info(String.format("Switch to scene %s", desktopName));
		Desktop desktop = this.desktopMap.getOrDefault(desktopName, null);		
		if (desktop == null) {
			LOG.error(String.format("Cannot load desktop '%s'",desktopName));
			return;
		}
		LOG.info(String.format("Desktop %s successfully loaded", desktopName));
		LOG.info(String.format(desktop.toString()));

		if (desktop != null) {
			FxmlLoaderImpl fxmlLoader = new FxmlLoaderImpl(springFXMLLoader);
			Pane viewRootNodeHierarchy = desktop.createPane(fxmlLoader);
			
			String appName = AppProperties.getInstance().appName;
			Integer appVersion = AppProperties.getInstance().appVersion;
						
			//show(viewRootNodeHierarchy, "Should get title from view");
			String title = null;
			if (appName == null || appName.isEmpty()) {
				title = desktop.getName();
			} else {
				title = String.format("%s (Version %d) - %s", appName, appVersion, desktop.getName());
			}
			//show(viewRootNodeHierarchy, desktop.getName());
			show(viewRootNodeHierarchy, title);
		}

	}

	private void show(final Parent rootnode, String title) {
		Scene scene = prepareScene(rootnode);
		// scene.getStylesheets().add("/styles/Styles.css");

		// primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setTitle(title);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.centerOnScreen();

		try {
			primaryStage.show();
		} catch (Exception exception) {
			logAndExit("Unable to show scene for title" + title, exception);
		}
	}

	private Scene prepareScene(Parent rootnode) {
		Scene scene = primaryStage.getScene();

		if (scene == null) {
			scene = new Scene(rootnode);
		}
		scene.setRoot(rootnode);
		return scene;
	}

	private void logAndExit(String errorMsg, Exception exception) {
		LOG.error(errorMsg, exception, exception.getCause());
		Platform.exit();
	}

}
