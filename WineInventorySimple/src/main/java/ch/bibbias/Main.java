package ch.bibbias;

import javafx.application.Application;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import ch.bibbias.config.StageManager;
import ch.bibbias.view.FxmlView;
import com.jeitziner.view.Desktop;

@SpringBootApplication
public class Main extends Application {

	protected ConfigurableApplicationContext springContext;
	protected StageManager stageManager;

	public static void main(final String[] args) {
		Application.launch(args);
	}

	@Override
	public void init() throws Exception {
		springContext = springBootApplicationContext();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stageManager = springContext.getBean(StageManager.class, stage);
		displayInitialScene();
		
		//
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		String jsonFilePath = "src/main/resources/config/desktop.json";
		Desktop desktop = Desktop.createDesktopFromJsonFile("Desktop Three", jsonFilePath);		
		if (desktop != null) {
			Region region = desktop.getRegion();
			System.out.println(desktop.toString());
		}

		
	}

	@Override
	public void stop() throws Exception {
		springContext.close();
	}

	/**
	 * Useful to override this method by sub-classes wishing to change the first
	 * Scene to be displayed on startup. Example: Functional tests on main window.
	 */
	protected void displayInitialScene() {
		stageManager.switchScene(FxmlView.WINE);
	}

	private ConfigurableApplicationContext springBootApplicationContext() {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
		String[] args = getParameters().getRaw().stream().toArray(String[]::new);
		return builder.run(args);
	}

}
