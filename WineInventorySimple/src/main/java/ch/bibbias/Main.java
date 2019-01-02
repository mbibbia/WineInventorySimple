package ch.bibbias;

import javafx.application.Application;
import javafx.stage.Stage;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import ch.bibbias.config.AppProperties;
import ch.bibbias.config.StageManager;

/**
 * 
 * @author Marco Bibbia
 *
 *         Main class to start application.
 */

@SpringBootApplication
public class Main extends Application {

	protected ConfigurableApplicationContext springContext;
	protected StageManager stageManager;

	public static void main(final String[] args) {
		Application.launch(args);
	}

	/**
	 * Initializes Spring Context
	 * 
	 */
	@Override
	public void init() throws Exception {
		springContext = springBootApplicationContext();
	}

	/**
	 * Method is called from context, loads Stage Manager and displays initial
	 * scene.
	 * 
	 */
	@Override
	public void start(Stage stage) throws Exception {
		Resource appProperties = springContext.getResource("classpath:application.properties");

		// Explicitly initialize singletons to avoid threading issues.
		AppProperties.init(appProperties.getInputStream());

		stageManager = springContext.getBean(StageManager.class, stage);

		Resource desktopProperties = springContext.getResource("classpath:config/desktop.json");
		stageManager.init(desktopProperties.getInputStream());

		// Explicitly initialize singletons to avoid threading issues.
		AppProperties.init(appProperties.getInputStream());

		stageManager = springContext.getBean(StageManager.class, stage);

		displayInitialScene();
	}
	
	/**
	 * Stops application
	 */
	@Override
	public void stop() throws Exception {
		springContext.close();
	}

	/**
	 * Sets the first Scene to be displayed on startup.
	 * 
	 */
	protected void displayInitialScene() {
		stageManager.displayInitialScene();
	}

	/**
	 * 
	 * @return Configurable Application Context
	 */
	private ConfigurableApplicationContext springBootApplicationContext() {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
		String[] args = getParameters().getRaw().stream().toArray(String[]::new);
		return builder.run(args);
	}

}
