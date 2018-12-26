package ch.bibbias.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import ch.bibbias.bean.Wine;
import ch.bibbias.config.StageManager;
import ch.bibbias.service.WineService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

	private ObservableList<Wine> wineList = FXCollections.observableArrayList();

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

		// loadWineDetails
		wineList.clear();
		wineList.addAll(wineService.findAll());

		wineTable.setItems(wineList);

	}

}
