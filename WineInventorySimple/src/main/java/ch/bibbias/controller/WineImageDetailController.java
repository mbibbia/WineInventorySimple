package ch.bibbias.controller;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.bibbias.config.StageManager;
import ch.bibbias.event.SaveWineEvent;
import ch.bibbias.event.ShowImageEvent;
import ch.bibbias.event.WineDetailsEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

@Controller
public class WineImageDetailController implements Initializable {

	@FXML
	private ImageView imageView;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Component
	class WineDetailEventHandler implements ApplicationListener<WineDetailsEvent> {
		@Override
		public void onApplicationEvent(WineDetailsEvent event) {
			imageView.setImage(null);
			if (event.getWine().getImage() != null) {
				imageView.setImage(new Image(new ByteArrayInputStream(event.getWine().getImage().getData())));
			}

		}
	}

	@Component
	class ShowWineImageEventHandler implements ApplicationListener<ShowImageEvent> {
		@Override
		public void onApplicationEvent(ShowImageEvent event) {
			imageView.setImage(null);
			if (event.getImage() != null) {
				imageView.setImage(new Image(new ByteArrayInputStream(event.getImage().getData())));
			}

		}
	}

	@Component
	class SaveWineEventHandler implements ApplicationListener<SaveWineEvent> {

		@Override
		public void onApplicationEvent(SaveWineEvent event) {

			imageView.setImage(null);
			if (event.getWine().getImage() != null) {
				imageView.setImage(new Image(new ByteArrayInputStream(event.getWine().getImage().getData())));
			}
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		imageView.setImage(null);
		AnchorPane parent = (AnchorPane) imageView.getParent();
		imageView.fitWidthProperty().bind(parent.widthProperty());
		imageView.fitHeightProperty().bind(parent.heightProperty());
	}

}
