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

public class DesktopController {	
	private static final Logger LOG = getLogger(DesktopController.class);
	
	private DesktopModel model;
	private DesktopView view;
	
	private FxmlLoader fxmlLoader;

	public DesktopController(DesktopModel model,
			                 DesktopView view,
			                 FxmlLoader fxmlLoader) {
		this.model = model;
		this.view = view;
		this.fxmlLoader = fxmlLoader;
	}
	
	public MenuBar getMenuBar() {		
		return this.view.menuBar(this.model.getDesktopMap());
	}

	public Pane createDesktop(String desktopName) {		
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
}
