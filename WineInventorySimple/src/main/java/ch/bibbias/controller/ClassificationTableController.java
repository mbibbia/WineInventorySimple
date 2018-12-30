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

import ch.bibbias.bean.Classification;
import ch.bibbias.config.StageManager;
import ch.bibbias.event.ClassificationDetailsEvent;
import ch.bibbias.event.SaveClassificationEvent;
import ch.bibbias.service.ClassificationService;
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
public class ClassificationTableController implements Initializable {

	@FXML
	private TableView<Classification> classificationTable;

	@FXML
	private TableColumn<Classification, Long> colClassificationId;

	@FXML
	private TableColumn<Classification, String> colName;

	@FXML
	private TableColumn<Classification, Boolean> colEdit;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ClassificationService classificationService;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	private final ObservableList<Classification> classificationList = FXCollections.observableArrayList();

	@Component
	class SaveClassificationEventHandler implements ApplicationListener<SaveClassificationEvent> {

		@Override
		public void onApplicationEvent(SaveClassificationEvent event) {
			loadClassifications();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		classificationTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		setColumnProperties();
		loadClassifications();

	}

	private void setColumnProperties() {
		colClassificationId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colEdit.setCellFactory(cellFactory);
	}

	private void loadClassifications() {
		classificationList.clear();
		classificationList.addAll(classificationService.findAll());
		classificationTable.setItems(classificationList);
	}

	private void raiseEventShowClassification(final Classification classification) {
		ClassificationDetailsEvent classificationEvent = new ClassificationDetailsEvent(this, classification);
		applicationEventPublisher.publishEvent(classificationEvent);
	}

	Callback<TableColumn<Classification, Boolean>, TableCell<Classification, Boolean>> cellFactory = new Callback<TableColumn<Classification, Boolean>, TableCell<Classification, Boolean>>() {
		@Override
		public TableCell<Classification, Boolean> call(final TableColumn<Classification, Boolean> param) {
			final TableCell<Classification, Boolean> cell = new TableCell<Classification, Boolean>() {
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
							Classification classification = getTableView().getItems().get(getIndex());
							raiseEventShowClassification(classification);
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
	void editClassification(ActionEvent event) {
		Classification classification = classificationTable.getSelectionModel().getSelectedItem();
		raiseEventShowClassification(classification);

	}

	@FXML
	void deleteClassifications(ActionEvent event) {
		List<Classification> countries = classificationTable.getSelectionModel().getSelectedItems();

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete selected?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK)
			classificationService.deleteInBatch(countries);

		loadClassifications();
	}

}
