package ch.bibbias.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import ch.bibbias.config.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Controller for FXML View Root.fxml.
 *
 */

@Controller
public class RootController implements Initializable {

	@FXML
	private MenuItem exit;

	@FXML
	private MenuItem about;

	@FXML
	void about(ActionEvent event) {

	}

	@FXML
	void exit(ActionEvent event) {

	}

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
