package ch.bibbias.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import ch.bibbias.bean.Wine;
import ch.bibbias.bean.WineType;
import ch.bibbias.config.StageManager;
import ch.bibbias.service.WineService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

@Controller
public class WineController implements Initializable {

	@FXML
	private Label wineId;

	@FXML
	private Label userId;

	@FXML
	private TextField name;

	@FXML
	private ComboBox<WineType> type;

	@FXML
	private ComboBox<String> classification;

	@FXML
	private ComboBox<String> country;

	@FXML
	private ComboBox<String> region;

	@FXML
	private ComboBox<String> producer;

	@FXML
	private Button reset;

	@FXML
	private Button saveWine;

	@FXML
	private TableView<Wine> wineTable;

	@FXML
	private TableColumn<Wine, Long> colWineId;

	@FXML
	private TableColumn<Wine, String> colName;

	@FXML
	private TableColumn<Wine, String> colType;

	@FXML
	private TableColumn<Wine, String> colClassification;

	@FXML
	private TableColumn<Wine, String> colCountry;

	@FXML
	private TableColumn<Wine, String> colRegion;

	@FXML
	private TableColumn<Wine, String> colProducer;
	@FXML
	private TableColumn<Wine, Boolean> colEdit;

	@FXML
	private MenuItem deleteWines;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private WineService wineService;

	private ObservableList<Wine> wineList = FXCollections.observableArrayList();

	//private ObservableList<String> types = FXCollections.observableArrayList("", "Rotwein", "Weisswein");
	private ObservableList<String> classifications = FXCollections.observableArrayList("DOC", "DOCG");
	private ObservableList<String> countries = FXCollections.observableArrayList("CH", "FR", "IT");
	private ObservableList<String> regions = FXCollections.observableArrayList("Zürich", "Bordeaux", "Piemont");
	private ObservableList<String> producers = FXCollections.observableArrayList("Parusso", "Gérard Bertrand",
			"Sciavenza");

	@FXML
	void reset(ActionEvent event) {
		clearFields();
	}

	@FXML
	private void saveWine(ActionEvent event) {

		/*
		 * if (validate("Name", getName(), "[a-zA-Z]+") && validate("Type", getType(),
		 * "[a-zA-Z]+") && emptyValidation("Classification", getClassification() &&
		 * emptyValidation("Country", getCountry() {
		 */

		if (wineId.getText() == null || wineId.getText() == "") {

			Wine wine = new Wine();
			wine.setName(getName());
			wine.setType(getType());
			wine.setClassification(getClassification());
			//wine.setCountry(getCountry());
			//wine.setRegion(getRegion());
			wine.setProducer(getProducer());

			Wine newWine = wineService.save(wine);

			saveAlert(newWine);

		} else {
			Wine wine = wineService.find(Long.parseLong(wineId.getText()));
			wine.setName(getName());
			wine.setType(getType());
			wine.setClassification(getClassification());
			//wine.setCountry(getCountry());
			//wine.setRegion(getRegion());
			wine.setProducer(getProducer());
			Wine updatedWine = wineService.update(wine);
			updateAlert(updatedWine);
		}

		clearFields();
		loadWineDetails();
		// }

	}

	@FXML
	void deleteWines(ActionEvent event) {
		List<Wine> wines = wineTable.getSelectionModel().getSelectedItems();

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete selected?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK)
			wineService.deleteInBatch(wines);

		loadWineDetails();

	}

	@FXML
	void exit(ActionEvent event) {
		Platform.exit();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		//type.setItems(types);
		classification.setItems(classifications);
		country.setItems(countries);
		region.setItems(regions);
		producer.setItems(producers);

		wineTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		setColumnProperties();

		// Add all wines into table
		loadWineDetails();
	}

	private void setColumnProperties() {

		colWineId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colType.setCellValueFactory(new PropertyValueFactory<>("type"));
		colClassification.setCellValueFactory(new PropertyValueFactory<>("classification"));
		colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
		colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
		colProducer.setCellValueFactory(new PropertyValueFactory<>("producer"));
		colEdit.setCellFactory(cellFactory);
	}

	Callback<TableColumn<Wine, Boolean>, TableCell<Wine, Boolean>> cellFactory = new Callback<TableColumn<Wine, Boolean>, TableCell<Wine, Boolean>>() {
		@Override
		public TableCell<Wine, Boolean> call(final TableColumn<Wine, Boolean> param) {
			final TableCell<Wine, Boolean> cell = new TableCell<Wine, Boolean>() {
				Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
				final Button btnEdit = new Button();

				@Override
				public void updateItem(Boolean check, boolean empty) {
					super.updateItem(check, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnEdit.setOnAction(e -> {
							Wine wine = getTableView().getItems().get(getIndex());
							updateWine(wine);
						});

						btnEdit.setStyle("-fx-background-color: transparent;");
						ImageView iv = new ImageView();
						iv.setImage(imgEdit);
						iv.setPreserveRatio(true);
						iv.setSmooth(true);
						iv.setCache(true);
						btnEdit.setGraphic(iv);

						setGraphic(btnEdit);
						setAlignment(Pos.CENTER);
						setText(null);
					}
				}

				private void updateWine(Wine wine) {
					wineId.setText(Long.toString(wine.getId()));
					name.setText(wine.getName());
					//type.setValue(wine.getType());
					classification.setValue(wine.getClassification());
					//country.setValue(wine.getCountry());
					//region.setValue(wine.getRegion());
					producer.setValue(wine.getProducer());

				}
			};
			return cell;
		}
	};

	private void clearFields() {
		wineId.setText(null);
		name.clear();
		type.getSelectionModel().clearSelection();
		classification.getSelectionModel().clearSelection();
		country.getSelectionModel().clearSelection();
		region.getSelectionModel().clearSelection();
		producer.getSelectionModel().clearSelection();

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
		alert.setTitle("User updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The wine " + wine.getName() + " has been updated.");
		alert.showAndWait();
	}

	private String getName() {
		return name.getText();
	}

	private WineType getType() {
		return type.getValue();
	}

	private String getClassification() {
		return classification.getValue();
	}

	private String getCountry() {
		return country.getValue();
	}

	private String getRegion() {
		return region.getValue();
	}

	private String getProducer() {
		return producer.getValue();
	}

	private void loadWineDetails() {
		wineList.clear();
		wineList.addAll(wineService.findAll());

		wineTable.setItems(wineList);
	}

	private boolean validate(String field, String value, String pattern) {
		if (!value.isEmpty()) {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(value);
			if (m.find() && m.group().equals(value)) {
				return true;
			} else {
				validationAlert(field, false);
				return false;
			}
		} else {
			validationAlert(field, true);
			return false;
		}
	}

	private boolean emptyValidation(String field, boolean empty) {
		if (!empty) {
			return true;
		} else {
			validationAlert(field, true);
			return false;
		}
	}

	private void validationAlert(String field, boolean empty) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Validation Error");
		alert.setHeaderText(null);
		if (field.equals("Role"))
			alert.setContentText("Please Select " + field);
		else {
			if (empty)
				alert.setContentText("Please Enter " + field);
			else
				alert.setContentText("Please Enter Valid " + field);
		}
		alert.showAndWait();
	}
}
