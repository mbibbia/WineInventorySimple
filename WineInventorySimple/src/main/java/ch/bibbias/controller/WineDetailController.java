package ch.bibbias.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.bibbias.bean.Classification;
import ch.bibbias.bean.Country;
import ch.bibbias.bean.Image;
import ch.bibbias.bean.Producer;
import ch.bibbias.bean.Region;
import ch.bibbias.bean.Wine;
import ch.bibbias.bean.WineType;
import ch.bibbias.config.StageManager;
import ch.bibbias.controller.validation.ControllerValidation;
import ch.bibbias.event.SaveWineEvent;
import ch.bibbias.event.ShowImageEvent;
import ch.bibbias.event.WineDetailsEvent;
import ch.bibbias.service.ClassificationService;
import ch.bibbias.service.CountryService;
import ch.bibbias.service.ImageService;
import ch.bibbias.service.ProducerService;
import ch.bibbias.service.WineService;
import ch.bibbias.service.WineTypeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Controller for FXML View WineDetail.fxml.
 *
 */

@Controller
public class WineDetailController implements Initializable {

	@FXML
	private Label wineId;

	@FXML
	private Label userId;

	@FXML
	private TextField name;

	@FXML
	private ComboBox<WineType> type;

	@FXML
	private ComboBox<Classification> classification;

	@FXML
	private ComboBox<Country> country;

	@FXML
	private ComboBox<Region> region;

	@FXML
	private ComboBox<Producer> producer;

	@FXML
	private Button browseImage;

	@FXML
	private Button reset;

	@FXML
	private Button saveWine;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private WineService wineService;

	@Autowired
	private WineTypeService wineTypeService;

	@Autowired
	private ClassificationService classificationService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private ProducerService producerService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private ControllerValidation validation;

	private Image image;

	@Component
	class ShowWineDetailEventHandler implements ApplicationListener<WineDetailsEvent> {

		@Override
		public void onApplicationEvent(WineDetailsEvent event) {
			wineId.setText(Long.toString(event.getWine().getId()));
			name.setText(event.getWine().getName());
			type.setValue(event.getWine().getType());
			classification.setValue(event.getWine().getClassification());
			country.setValue(event.getWine().getCountry());
			region.setValue(event.getWine().getRegion());
			producer.setValue(event.getWine().getProducer());
			if (event.getWine().getImage() != null) {
				browseImage.setText("Change Image...");
			} else {
				browseImage.setText("Add Image...");
			}
			saveWine.setDisable(false);

		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		type.setItems(loadTypes());
		classification.setItems(loadClassifications());
		country.setItems(loadCountries());
		producer.setItems(loadProducers());
		saveWine.setDisable(true);
		browseImage.setText("Add Image...");

	}

	private ObservableList<WineType> loadTypes() {
		ObservableList<WineType> list = FXCollections.observableArrayList(wineTypeService.findAll());
		list.add(new WineType());
		return list;
	}

	private ObservableList<Classification> loadClassifications() {
		ObservableList<Classification> list = FXCollections.observableArrayList(classificationService.findAll());
		list.add(new Classification());
		return list;
	}

	private ObservableList<Country> loadCountries() {
		ObservableList<Country> list = FXCollections.observableArrayList(countryService.findAll());
		list.add(new Country());
		return list;
	}

	private ObservableList<Producer> loadProducers() {
		ObservableList<Producer> list = FXCollections.observableArrayList(producerService.findAll());
		list.add(new Producer());
		return list;
	}

	private String getName() {
		return name.getText();
	}

	private WineType getType() {
		return type.getValue();
	}

	private Classification getClassification() {
		return classification.getValue();
	}

	private Country getCountry() {
		return country.getValue();
	}

	private Region getRegion() {
		return region.getValue();
	}

	private Producer getProducer() {
		return producer.getValue();
	}

	@FXML
	void reset(ActionEvent event) {
		clearFields();
		saveWine.setDisable(true);
		browseImage.setText("Add Image...");
	}

	private void clearFields() {

		wineId.setText(null);
		name.clear();
		type.getSelectionModel().clearSelection();
		classification.getSelectionModel().clearSelection();
		country.getSelectionModel().clearSelection();
		region.getSelectionModel().clearSelection();
		producer.getSelectionModel().clearSelection();
		browseImage.setText("Add Image...");
		saveWine.setDisable(true);

	}

	@FXML
	private void handleRegionClicked() {

		if (country.getValue() != null) {
			ObservableList<Region> regions = FXCollections.observableArrayList(country.getValue().getRegions());
			region.setItems(regions);
		}
	}

	@FXML
	private void handleNameChanged() {
		saveWine.setDisable(false);

	}

	@FXML
	private void browseImage() {
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(browseImage.getScene().getWindow());

		if (file != null) {

			BufferedImage bImage;
			try {
				bImage = ImageIO.read(file);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ImageIO.write(bImage, "jpg", bos);
				byte[] data = bos.toByteArray();
				image = new Image();
				image.setName(file.getName());
				image.setType(getFileExtension(file));
				image.setData(data);
				imageService.save(image);
				browseImage.setText("Change Image...");
				raiseEventShowImage(image);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	@FXML
	private void removeImage() {
		image = null;
		if (wineId.getText() != null || wineId.getText() != "") {
			Wine wine = wineService.find(Long.parseLong(wineId.getText()));
			wine.setImage(image);
			raiseEventShowImage(image);
		}

	}

	private static String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}

	@FXML
	private void saveWine(ActionEvent event) {

		// if (validation.validate("Name", getName(), "[a-zA-Z]+")) {
		if (validation.emptyValidation("Name", getName() == null)) {

			if (wineId.getText() == null || wineId.getText() == "") {

				Wine wine = new Wine();
				wine.setName(getName());
				wine.setType(getType());
				wine.setClassification(getClassification());
				wine.setCountry(getCountry());
				wine.setRegion(getRegion());
				wine.setProducer(getProducer());
				wine.setImage(image);

				Wine newWine = wineService.save(wine);
				raiseEventSaveWine(newWine);

				saveAlert(newWine);

			} else {
				Wine wine = wineService.find(Long.parseLong(wineId.getText()));
				wine.setName(getName());
				wine.setType(getType());
				wine.setClassification(getClassification());
				wine.setCountry(getCountry());
				wine.setRegion(getRegion());
				wine.setProducer(getProducer());
				wine.setImage(image);

				Wine updatedWine = wineService.update(wine);
				raiseEventSaveWine(updatedWine);
				updateAlert(updatedWine);

			}

			clearFields();

		}

	}

	private void raiseEventSaveWine(final Wine wine) {
		SaveWineEvent wineEvent = new SaveWineEvent(this, wine);
		applicationEventPublisher.publishEvent(wineEvent);
	}

	private void raiseEventShowImage(Image image) {
		ShowImageEvent imageEvent = new ShowImageEvent(this, image);
		applicationEventPublisher.publishEvent(imageEvent);
	}

	private void saveAlert(Wine wine) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Wine saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The wine " + wine.getName() + " has been created and \n id is " + wine.getId() + ".");
		alert.showAndWait();
	}

	private void updateAlert(Wine wine) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Wine updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The wine " + wine.getName() + " has been updated.");
		alert.showAndWait();
	}

}
