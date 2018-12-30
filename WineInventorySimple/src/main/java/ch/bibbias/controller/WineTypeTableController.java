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

import ch.bibbias.bean.WineType;
import ch.bibbias.config.StageManager;
import ch.bibbias.event.WineTypeDetailsEvent;
import ch.bibbias.event.SaveWineTypeEvent;
import ch.bibbias.service.WineTypeService;
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
public class WineTypeTableController implements Initializable {

	@FXML
	private TableView<WineType> wineTypeTable;

	@FXML
	private TableColumn<WineType, Long> colWineTypeId;

	@FXML
	private TableColumn<WineType, String> colName;

	@FXML
	private TableColumn<WineType, Boolean> colEdit;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private WineTypeService wineTypeService;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	private final ObservableList<WineType> wineTypeList = FXCollections.observableArrayList();

	@Component
	class SaveWineTypeEventHandler implements ApplicationListener<SaveWineTypeEvent> {

		@Override
		public void onApplicationEvent(SaveWineTypeEvent event) {
			loadWineTypes();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		wineTypeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		setColumnProperties();
		loadWineTypes();

	}

	private void setColumnProperties() {
		colWineTypeId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colEdit.setCellFactory(cellFactory);
	}

	private void loadWineTypes() {
		wineTypeList.clear();
		wineTypeList.addAll(wineTypeService.findAll());
		wineTypeTable.setItems(wineTypeList);
	}

	private void raiseEventShowWineType(final WineType wineType) {
		WineTypeDetailsEvent wineTypeEvent = new WineTypeDetailsEvent(this, wineType);
		applicationEventPublisher.publishEvent(wineTypeEvent);
	}

	Callback<TableColumn<WineType, Boolean>, TableCell<WineType, Boolean>> cellFactory = new Callback<TableColumn<WineType, Boolean>, TableCell<WineType, Boolean>>() {
		@Override
		public TableCell<WineType, Boolean> call(final TableColumn<WineType, Boolean> param) {
			final TableCell<WineType, Boolean> cell = new TableCell<WineType, Boolean>() {
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
							WineType wineType = getTableView().getItems().get(getIndex());
							raiseEventShowWineType(wineType);
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
	void editWineType(ActionEvent event) {
		WineType wineType = wineTypeTable.getSelectionModel().getSelectedItem();
		raiseEventShowWineType(wineType);

	}

	@FXML
	void deleteWineTypes(ActionEvent event) {
		List<WineType> countries = wineTypeTable.getSelectionModel().getSelectedItems();

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete selected?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK)
			wineTypeService.deleteInBatch(countries);

		loadWineTypes();
	}

}
