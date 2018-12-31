package ch.bibbias.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.bibbias.bean.Country;
import ch.bibbias.bean.Region;
import ch.bibbias.config.StageManager;
import ch.bibbias.event.RegionDetailsEvent;
import ch.bibbias.event.SaveRegionEvent;
import ch.bibbias.service.RegionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

@Controller
public class RegionDetailController implements Initializable {

	@FXML
	private Label regionId;

	@FXML
	private ComboBox<Country> country;

	@FXML
	private TextField name;

	@FXML
	private Button reset;

	@FXML
	private Button saveRegion;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private RegionService regionService;

	@Component
	class ShowRegionDetailEventHandler implements ApplicationListener<RegionDetailsEvent> {

		@Override
		public void onApplicationEvent(RegionDetailsEvent event) {
			regionId.setText(Long.toString(event.getRegion().getId()));
			country.setValue(event.getRegion().getCountry());
			name.setText(event.getRegion().getName());
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	private Country getCountry() {
		return country.getValue();
	}

	private String getName() {
		return name.getText();
	}

	@FXML
	void reset(ActionEvent event) {
		clearFields();
	}

	private void clearFields() {

		regionId.setText(null);
		country.setValue(null);
		name.clear();

	}

	@FXML
	private void saveRegion(ActionEvent event) {

		/*
		 * if (validate("Name", getName(), "[a-zA-Z]+") && validate("Type", getType(),
		 * "[a-zA-Z]+") && emptyValidation("Classification", getClassification() &&
		 * emptyValidation("Country", getCountry() {
		 */

		if (regionId.getText() == null || regionId.getText() == "") {

			Region region = new Region();
			region.setCountry(getCountry());
			region.setName(getName());

			Region newRegion = regionService.save(region);

			saveAlert(newRegion);

			raiseEventSaveRegion(newRegion);

		} else {
			Region region = regionService.find(Long.parseLong(regionId.getText()));
			region.setCountry(getCountry());
			region.setName(getName());
			Region updatedRegion = regionService.update(region);
			updateAlert(updatedRegion);

			raiseEventSaveRegion(updatedRegion);
		}

		clearFields();

	}

	private void raiseEventSaveRegion(final Region region) {
		SaveRegionEvent regionEvent = new SaveRegionEvent(this, region);
		applicationEventPublisher.publishEvent(regionEvent);
	}

	private void saveAlert(Region region) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Wine saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText(
				"The region " + region.getName() + " has been created and \n id is " + region.getId() + ".");
		alert.showAndWait();
	}

	private void updateAlert(Region region) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The region " + region.getName() + " has been updated.");
		alert.showAndWait();
	}

}
