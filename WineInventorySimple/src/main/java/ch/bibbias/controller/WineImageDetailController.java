package ch.bibbias.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.bibbias.config.StageManager;
import ch.bibbias.event.WineDetailsEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

@Controller
public class WineImageDetailController implements Initializable {

	@FXML
	private ImageView imageView;
	
	@Lazy
	@Autowired
	private StageManager stageManager;

	@Component
	class ShowWineImageDetailEventHandler implements ApplicationListener<WineDetailsEvent> {
		@Override
		public void onApplicationEvent(WineDetailsEvent event) {
			InputStream is = getClass().getResourceAsStream("/img/Wine2.jpeg");
			if (is != null) {
		        Image image = new Image(is);
		        imageView.setImage(image);
			}
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
        AnchorPane parent = (AnchorPane)imageView.getParent();
        imageView.fitWidthProperty().bind(parent.widthProperty());
        imageView.fitHeightProperty().bind(parent.heightProperty());
	}
	
	private Image getImageView() {
		return imageView.getImage();
	}
}
