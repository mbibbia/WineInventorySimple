package ch.bibbias.view;

import static org.slf4j.LoggerFactory.getLogger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;

import ch.bibbias.config.SpringFXMLLoader;
import ch.bibbias.config.StageManager;
import javafx.scene.Parent;

public class FxmlLoaderImpl implements FxmlLoader {
	
	private static final Logger LOG = getLogger(FxmlLoaderImpl.class);
	private final SpringFXMLLoader springFXMLLoader;
	
	public FxmlLoaderImpl(SpringFXMLLoader springFXMLLoader) {
		this.springFXMLLoader = springFXMLLoader;
	}
	
	@Override
	public Parent load(String fxmlFilePath) {		
		//Path path = Paths.get("data/logging.properties");
		//boolean pathExists = Files.exists(path);
		//System.out.println(String.format("Path exists: %s", pathExists));
		
		Parent rootNode = null;
		try {
			rootNode = springFXMLLoader.load(fxmlFilePath);
			if (rootNode == null) {
			LOG.error("Unable to load FXML view: " + fxmlFilePath);}
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			LOG.error("Exception while trying to load FXML view: " + fxmlFilePath, exception);
		}
		return rootNode;
	}
}
