package ch.bibbias.config;

import java.io.IOException;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Loads FXML hierarchy as in method load and registers Spring as FXML
 *         Controller Factory. Allow Spring and JavaFX to run together, as soon
 *         as Spring Application Context is loaded.
 * 
 */
@Component
public class SpringFXMLLoader {
	private final ResourceBundle resourceBundle;
	private final ApplicationContext context;

	// @Autowired
	public SpringFXMLLoader(ApplicationContext context, ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
		this.context = context;
	}

	/**
	 * Loads FXML hierarchy
	 * 
	 * @param fxmlPath
	 * @return Parent scene
	 * @throws IOException
	 */
	public Parent load(String fxmlPath) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setControllerFactory(context::getBean); // Spring now FXML Controller Factory
		loader.setResources(resourceBundle);
		loader.setLocation(getClass().getResource(fxmlPath));
		return loader.load();
	}
}
