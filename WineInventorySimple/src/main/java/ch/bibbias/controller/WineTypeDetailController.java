package ch.bibbias.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.bibbias.bean.WineType;
import ch.bibbias.config.StageManager;
import ch.bibbias.event.WineTypeDetailsEvent;
import ch.bibbias.event.SaveWineTypeEvent;
import ch.bibbias.service.WineTypeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

@Controller
public class WineTypeDetailController implements Initializable {

	@FXML
	private Label wineTypeId;

	@FXML
	private TextField name;

	@FXML
	private Button reset;

	@FXML
	private Button saveWineType;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private WineTypeService wineTypeService;

	@Component
	class ShowWineTypeDetailEventHandler implements ApplicationListener<WineTypeDetailsEvent> {

		@Override
		public void onApplicationEvent(WineTypeDetailsEvent event) {
			wineTypeId.setText(Long.toString(event.getWineType().getId()));
			name.setText(event.getWineType().getName());
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

		wineTypeId.setText(null);
		name.clear();

	}

	@FXML
	private void saveWineType(ActionEvent event) {

		/*
		 * if (validate("Name", getName(), "[a-zA-Z]+") && validate("Type", getType(),
		 * "[a-zA-Z]+") && emptyValidation("WineType", getWineType() &&
		 * emptyValidation("WineType", getWineType() {
		 */

		if (wineTypeId.getText() == null || wineTypeId.getText() == "") {

			WineType wineType = new WineType();
			wineType.setName(getName());

			WineType newWineType = wineTypeService.save(wineType);

			saveAlert(newWineType);

			raiseEventSaveWineType(newWineType);

		} else {
			WineType wineType = wineTypeService.find(Long.parseLong(wineTypeId.getText()));
			wineType.setName(getName());
			WineType updatedWineType = wineTypeService.update(wineType);
			updateAlert(updatedWineType);

			raiseEventSaveWineType(updatedWineType);
		}

		clearFields();

	}

	private void raiseEventSaveWineType(final WineType wineType) {
		SaveWineTypeEvent wineTypeEvent = new SaveWineTypeEvent(this, wineType);
		applicationEventPublisher.publishEvent(wineTypeEvent);
	}

	private void saveAlert(WineType wineType) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Wine saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The wine type " + wineType.getName() + " has been created and \n id is "
				+ wineType.getId() + ".");
		alert.showAndWait();
	}

	private void updateAlert(WineType wineType) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The wine type " + wineType.getName() + " has been updated.");
		alert.showAndWait();
	}

}
