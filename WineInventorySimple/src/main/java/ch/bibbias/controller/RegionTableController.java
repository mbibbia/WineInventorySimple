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
import ch.bibbias.bean.Region;
import ch.bibbias.config.StageManager;
import ch.bibbias.event.RegionDetailsEvent;
import ch.bibbias.event.SaveRegionEvent;
import ch.bibbias.service.RegionService;
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
public class RegionTableController implements Initializable {

	@FXML
	private TableView<Region> regionTable;

	@FXML
	private TableColumn<Region, Long> colRegionId;

	@FXML
	private TableColumn<Region, Country> colCountry;

	@FXML
	private TableColumn<Region, String> colName;

	@FXML
	private TableColumn<Region, Boolean> colEdit;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private RegionService regionService;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	private final ObservableList<Region> regionList = FXCollections.observableArrayList();

	@Component
	class SaveRegionEventHandler implements ApplicationListener<SaveRegionEvent> {

		@Override
		public void onApplicationEvent(SaveRegionEvent event) {
			loadRegions();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		regionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		setColumnProperties();
		loadRegions();

	}

	private void setColumnProperties() {
		colRegionId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colEdit.setCellFactory(cellFactory);
	}

	private void loadRegions() {
		regionList.clear();
		regionList.addAll(regionService.findAll());
		regionTable.setItems(regionList);
	}

	private void raiseEventShowRegion(final Region region) {
		RegionDetailsEvent regionEvent = new RegionDetailsEvent(this, region);
		applicationEventPublisher.publishEvent(regionEvent);
	}

	Callback<TableColumn<Region, Boolean>, TableCell<Region, Boolean>> cellFactory = new Callback<TableColumn<Region, Boolean>, TableCell<Region, Boolean>>() {
		@Override
		public TableCell<Region, Boolean> call(final TableColumn<Region, Boolean> param) {
			final TableCell<Region, Boolean> cell = new TableCell<Region, Boolean>() {
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
							Region region = getTableView().getItems().get(getIndex());
							raiseEventShowRegion(region);
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
	void editRegion(ActionEvent event) {
		Region region = regionTable.getSelectionModel().getSelectedItem();
		raiseEventShowRegion(region);

	}

	@FXML
	void deleteRegions(ActionEvent event) {
		List<Region> regions = regionTable.getSelectionModel().getSelectedItems();

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete selected?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK)
			regionService.deleteInBatch(regions);

		loadRegions();
	}

}
