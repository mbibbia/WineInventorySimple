package ch.bibbias.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.bibbias.bean.Wine;
import ch.bibbias.config.StageManager;
import ch.bibbias.event.SaveWineEvent;
import ch.bibbias.event.WineDetailsEvent;
import ch.bibbias.service.WineService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

@Controller
public class WineTableController implements Initializable {
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

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private WineService wineService;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	private final ObservableList<Wine> wineList = FXCollections.observableArrayList();

	@Component
	class SaveWineEventHandler implements ApplicationListener<SaveWineEvent> {

		@Override
		public void onApplicationEvent(SaveWineEvent event) {
			wineList.add(event.getWine());

		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		wineTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// setColumnProperties
		colWineId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colType.setCellValueFactory(new PropertyValueFactory<>("type"));
		colClassification.setCellValueFactory(new PropertyValueFactory<>("classification"));
		colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
		colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
		colProducer.setCellValueFactory(new PropertyValueFactory<>("producer"));
		colEdit.setCellFactory(cellFactory);

		// loadWineDetails
		wineList.clear();
		wineList.addAll(wineService.findAll());

		wineTable.setItems(wineList);

	}

	private void raiseEventShowWine(final Wine wine) {
		WineDetailsEvent wineEvent = new WineDetailsEvent(this, wine);
		applicationEventPublisher.publishEvent(wineEvent);
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
							raiseEventShowWine(wine);
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

}
