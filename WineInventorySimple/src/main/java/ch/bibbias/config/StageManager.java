package ch.bibbias.config;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.InputStream;

import org.slf4j.Logger;

import com.jeitziner.gui.DesktopController;
import com.jeitziner.gui.DesktopModel;
import com.jeitziner.gui.DesktopView;

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
	 * A desktop controller is responsible for creating the main view which
	 * consists of a menu bar and a view. The view is initialized from
	 * a JSON file (desktop.json).
	 */
	private DesktopController desktopController;

	public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
		this.springFXMLLoader = springFXMLLoader;
		this.primaryStage = stage;
	}

	/**
	 * @param is : The input stream is used to initialize the desktop model
	 *             from a json file.
	 */
	public void init(InputStream is) {
		LOG.info("Intialize StageManager");
		this.desktopController = new DesktopController(new DesktopModel(is),
				                                       new DesktopView(),
				                                       this,
				                                       new FxmlLoaderImpl(springFXMLLoader));
	}
	
	public void displayInitialScene() {
		this.desktopController.displayInitialScene();
	}

	public void switchScene(Pane pane, String sceneTitle) {
		show(pane, sceneTitle);
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