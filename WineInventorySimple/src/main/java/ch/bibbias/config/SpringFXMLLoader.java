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
 *         Lädt die FXML Hierarchie wie in Methode load angegeben und
 *         registriert Spring als FXML Controller Factory. Erlaubt Spring und
 *         JavaFX nebeneinander zu laufen, sobald der Spring Application Context
 *         geladen wurde.
 *
 */
@Component
public class SpringFXMLLoader {
	private final ResourceBundle resourceBundle;
	private final ApplicationContext context;

	//@Autowired
	public SpringFXMLLoader(ApplicationContext context, ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
		this.context = context;
	}

	public Parent load(String fxmlPath) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setControllerFactory(context::getBean); // Spring now FXML Controller Factory
		loader.setResources(resourceBundle);
		loader.setLocation(getClass().getResource(fxmlPath));
		return loader.load();
	}
}
