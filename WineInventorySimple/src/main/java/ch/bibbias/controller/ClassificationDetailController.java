package ch.bibbias.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.bibbias.bean.Classification;
import ch.bibbias.config.StageManager;
import ch.bibbias.event.ClassificationDetailsEvent;
import ch.bibbias.event.SaveClassificationEvent;
import ch.bibbias.service.ClassificationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Controller for FXML View ClassificationDetail.fxml.
 *
 */

@Controller
public class ClassificationDetailController implements Initializable {

	@FXML
	private Label classificationId;

	@FXML
	private TextField name;

	@FXML
	private Button reset;

	@FXML
	private Button saveClassification;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private ClassificationService classificationService;

	@Component
	class ShowClassificationDetailEventHandler implements ApplicationListener<ClassificationDetailsEvent> {

		@Override
		public void onApplicationEvent(ClassificationDetailsEvent event) {
			classificationId.setText(Long.toString(event.getClassification().getId()));
			name.setText(event.getClassification().getName());
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	private String getName() {
		return name.getText();
	}

	@FXML
	void reset(ActionEvent event) {
		clearFields();
	}

	private void clearFields() {

		classificationId.setText(null);
		name.clear();

	}

	@FXML
	private void saveClassification(ActionEvent event) {

		/*
		 * if (validate("Name", getName(), "[a-zA-Z]+") && validate("Type", getType(),
		 * "[a-zA-Z]+") && emptyValidation("Classification", getClassification() &&
		 * emptyValidation("Classification", getClassification() {
		 */

		if (classificationId.getText() == null || classificationId.getText() == "") {

			Classification classification = new Classification();
			classification.setName(getName());

			Classification newClassification = classificationService.save(classification);

			saveAlert(newClassification);

			raiseEventSaveClassification(newClassification);

		} else {
			Classification classification = classificationService.find(Long.parseLong(classificationId.getText()));
			classification.setName(getName());
			Classification updatedClassification = classificationService.update(classification);
			updateAlert(updatedClassification);

			raiseEventSaveClassification(updatedClassification);
		}

		clearFields();

	}

	private void raiseEventSaveClassification(final Classification classification) {
		SaveClassificationEvent classificationEvent = new SaveClassificationEvent(this, classification);
		applicationEventPublisher.publishEvent(classificationEvent);
	}

	private void saveAlert(Classification classification) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Wine saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The classification " + classification.getName() + " has been created and \n id is "
				+ classification.getId() + ".");
		alert.showAndWait();
	}

	private void updateAlert(Classification classification) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The classification " + classification.getName() + " has been updated.");
		alert.showAndWait();
	}

}
