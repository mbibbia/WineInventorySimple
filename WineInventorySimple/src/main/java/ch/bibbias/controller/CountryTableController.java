package ch.bibbias.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.bibbias.bean.Country;
import ch.bibbias.config.StageManager;
import ch.bibbias.event.CountryDetailsEvent;
import ch.bibbias.event.SaveCountryEvent;
import ch.bibbias.service.CountryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

@Controller
public class CountryTableController implements Initializable {
	
	@FXML
	private TableView<Country> countryTable;

	@FXML
	private TableColumn<Country, Long> colCountryId;

	@FXML
	private TableColumn<Country, String> colCode;

	@FXML
	private TableColumn<Country, String> colName;

	@FXML
	private TableColumn<Country, Boolean> colEdit;

	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@Autowired
	private CountryService countryService;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	private final ObservableList<Country> countryList = FXCollections.observableArrayList();

	@Component
	class SaveCountryEventHandler implements ApplicationListener<SaveCountryEvent> {

		@Override
		public void onApplicationEvent(SaveCountryEvent event) {
			loadCountries();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		countryTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		setColumnProperties();
		loadCountries();

	}

	private void setColumnProperties() {
		colCountryId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colEdit.setCellFactory(cellFactory);
	}

	private void loadCountries() {
		countryList.clear();
		countryList.addAll(countryService.findAll());
		countryTable.setItems(countryList);
	}

	private void raiseEventShowCountry(final Country country) {
		CountryDetailsEvent countryEvent = new CountryDetailsEvent(this, country);
		applicationEventPublisher.publishEvent(countryEvent);
	}

	Callback<TableColumn<Country, Boolean>, TableCell<Country, Boolean>> cellFactory = new Callback<TableColumn<Country, Boolean>, TableCell<Country, Boolean>>() {
		@Override
		public TableCell<Country, Boolean> call(final TableColumn<Country, Boolean> param) {
			final TableCell<Country, Boolean> cell = new TableCell<Country, Boolean>() {
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
							Country country = getTableView().getItems().get(getIndex());
							raiseEventShowCountry(country);
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

			};
			return cell;
		}
	};

	@FXML
	void editCountry(ActionEvent event) {
		Country country = countryTable.getSelectionModel().getSelectedItem();
		raiseEventShowCountry(country);

	}

	@FXML
	void deleteCountries(ActionEvent event) {
		List<Country> countries = countryTable.getSelectionModel().getSelectedItems();

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete selected?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK)
			countryService.deleteInBatch(countries);

		loadCountries();
	}

}
