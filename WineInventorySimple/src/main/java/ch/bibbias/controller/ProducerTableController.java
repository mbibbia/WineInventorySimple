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
import ch.bibbias.bean.Producer;
import ch.bibbias.bean.Region;
import ch.bibbias.config.StageManager;
import ch.bibbias.event.SaveProducerEvent;
import ch.bibbias.event.ProducerDetailsEvent;
import ch.bibbias.service.ProducerService;
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
public class ProducerTableController implements Initializable {
	@FXML
	private TableView<Producer> producerTable;

	@FXML
	private TableColumn<Producer, Long> colProducerId;

	@FXML
	private TableColumn<Producer, String> colName;

	@FXML
	private TableColumn<Producer, String> colCompany;

	@FXML
	private TableColumn<Producer, String> colAddressLine1;

	@FXML
	private TableColumn<Producer, String> colAddressLine2;

	@FXML
	private TableColumn<Producer, String> colZipCode;

	@FXML
	private TableColumn<Producer, String> colPlace;

	@FXML
	private TableColumn<Producer, String> colPhone;

	@FXML
	private TableColumn<Producer, String> colFax;

	@FXML
	private TableColumn<Producer, String> colEmail;

	@FXML
	private TableColumn<Producer, String> colUrl;

	@FXML
	private TableColumn<Producer, Country> colCountry;

	@FXML
	private TableColumn<Producer, Region> colRegion;

	@FXML
	private TableColumn<Producer, Boolean> colEdit;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ProducerService producerService;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	private final ObservableList<Producer> producerList = FXCollections.observableArrayList();

	@Component
	class SaveProducerEventHandler implements ApplicationListener<SaveProducerEvent> {

		@Override
		public void onApplicationEvent(SaveProducerEvent event) {
			loadProducers();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		producerTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		setColumnProperties();
		loadProducers();

	}

	private void setColumnProperties() {
		colProducerId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
		colAddressLine1.setCellValueFactory(new PropertyValueFactory<>("addressLine1"));
		colAddressLine2.setCellValueFactory(new PropertyValueFactory<>("addressLine2"));
		colZipCode.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
		colPlace.setCellValueFactory(new PropertyValueFactory<>("place"));
		colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		colFax.setCellValueFactory(new PropertyValueFactory<>("fax"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		colUrl.setCellValueFactory(new PropertyValueFactory<>("url"));
		colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
		colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
		colEdit.setCellFactory(cellFactory);
	}

	private void loadProducers() {
		producerList.clear();
		producerList.addAll(producerService.findAll());
		producerTable.setItems(producerList);
	}

	private void raiseEventShowProducer(final Producer producer) {
		ProducerDetailsEvent producerEvent = new ProducerDetailsEvent(this, producer);
		applicationEventPublisher.publishEvent(producerEvent);
	}

	Callback<TableColumn<Producer, Boolean>, TableCell<Producer, Boolean>> cellFactory = new Callback<TableColumn<Producer, Boolean>, TableCell<Producer, Boolean>>() {
		@Override
		public TableCell<Producer, Boolean> call(final TableColumn<Producer, Boolean> param) {
			final TableCell<Producer, Boolean> cell = new TableCell<Producer, Boolean>() {
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
							Producer producer = getTableView().getItems().get(getIndex());
							raiseEventShowProducer(producer);
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
	void editProducer(ActionEvent event) {
		Producer producer = producerTable.getSelectionModel().getSelectedItem();
		raiseEventShowProducer(producer);

	}

	@FXML
	void deleteProducers(ActionEvent event) {
		List<Producer> producers = producerTable.getSelectionModel().getSelectedItems();

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete selected?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK)
			producerService.deleteInBatch(producers);

		loadProducers();
	}

}
