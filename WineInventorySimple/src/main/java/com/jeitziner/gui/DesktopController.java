package com.jeitziner.gui;


import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

import com.jeitziner.view.Desktop;

import ch.bibbias.config.AppProperties;
import ch.bibbias.config.StageManager;
import ch.bibbias.view.FxmlLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * @author Christian Jeitziner
 *
 */
public class DesktopController {
	//--------------------------------------------------------------------------
	// INSTANCE AND CLASS VARIABLES.
	//--------------------------------------------------------------------------
	private static final Logger LOG = getLogger(DesktopController.class);
	
	private DesktopModel model;
	private DesktopView view;
	private StageManager stageManager;
	private FxmlLoader fxmlLoader;

	//--------------------------------------------------------------------------
	// CONSTRUCTORS
	//--------------------------------------------------------------------------
	public DesktopController(DesktopModel model,
			                 DesktopView view,
			                 StageManager stageManager,
							 FxmlLoader fxmlLoader) {
		this.model = model;
		this.view = view;
		this.stageManager = stageManager;
		this.fxmlLoader = fxmlLoader;

		// Connect view to controller
		this.view.setController(this);
	}

	//--------------------------------------------------------------------------
	// METHODS
	//--------------------------------------------------------------------------
	/**
	 * Changes to the selected scene identified by desktop name.
	 * @param desktopName
	 */
	public void changeScene(String desktopName) {
		// Create main view and set its title.
		Pane pane = createMainView(desktopName);
		String sceneTitle = getSceneTitle(desktopName);

		// Activate the radio menu item. This steps needs to be done here,
		// since the menu is always recreated in the method createDesktop.
		this.view.activateDesktopMenuItem(desktopName);

		this.stageManager.switchScene(pane, sceneTitle);
	}

	/**
	 * Creates the main view of the application and sets the first scene
	 * to the view identified by desktopName.
	 *
	 * @param desktopName
	 * @return Pane
	 */
	public Pane createMainView(String desktopName) {
		final BorderPane mainPane = new BorderPane();
		mainPane.setTop(getMenuBar());
		Desktop currentDesktop = getDesktop(desktopName);
		if (currentDesktop == null) {
			LOG.error(String.format("Desktop %s does not exist", desktopName));
		} else {
			LOG.info(String.format("Desktop %s exists", currentDesktop.getName()));
			mainPane.setCenter(currentDesktop.createPane(this.fxmlLoader));
		}
		return mainPane;
	}
	
	/**
	 * Displays the initial scene, the name of the first scene is identified in
	 * the application.properties file, parameter "initialDesktop".
	 */
	public void displayInitialScene() {
		String initialDesktopName = AppProperties.getInstance().initialDesktop;
		changeScene(initialDesktopName);
	}

	//--------------------------------------------------------------------------
	// GETTER AND SETTER METHODS
	//--------------------------------------------------------------------------		
	/**
	 * Returns the desktop identified by desktopName. If desktopName cannot be 
	 * found, the method returns the first desktop object from the desktop map
	 * unless it is empty. In this case it returns null.
	 * @param desktopName
	 * @return
	 */
	public Desktop getDesktop(String desktopName) {
		Desktop usedDesktop = this.model.getDesktopMap().getOrDefault(desktopName, null);
		if (usedDesktop == null) {
			// Try to find another desktop.
			for (Desktop desktop : this.model.getDesktopMap().values()) {
				usedDesktop = desktop;
				LOG.warn(String.format("Desktop %s not found, use desktop %s",
						               desktopName,
						               usedDesktop.getName()));			
				break;
			}
		}
		return usedDesktop;		
	}
		
	
	/**
	 * @return Menubar, initialized using the desktop map.
	 */
	public MenuBar getMenuBar() {		
		return this.view.menuBar(this.model.getDesktopMap());
	}

	/**
	 * Returns a title for the main view. The title is a combination
	 * of desktop name and application name and version. The latter
	 * two parameters are read from the application.properties file.
	 * @param desktopName
	 * @return String : scene title
	 */
	private String getSceneTitle(String desktopName) {
		String appName = AppProperties.getInstance().appName;
		Integer appVersion = AppProperties.getInstance().appVersion;

		String sceneTitle = null;
		if (appName == null || appName.isEmpty()) {
			sceneTitle = desktopName;
		} else {
			sceneTitle = String.format("%s (Version %d) - %s", appName, appVersion, desktopName);
		}

		return sceneTitle;
	}

}
