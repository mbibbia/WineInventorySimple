package com.jeitziner.gui;

import java.util.Map;

import com.jeitziner.view.Desktop;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class DesktopView {
	private DesktopController controller;
	
	public MenuBar menuBar(Map<String, Desktop> desktopMap) {
		MenuBar menuBar = new MenuBar();
	    menuBar.getMenus().addAll(fileMenu(), desktopMenu(desktopMap));
	    return menuBar;		
	}
	
	private Menu fileMenu() {
		Menu desktopMenu = new Menu("File");
	    MenuItem exit = new MenuItem("Exit");
	    desktopMenu.getItems().addAll(exit);
	    return desktopMenu;	    
	}

	private Menu desktopMenu(Map<String, Desktop> desktopMap) {
		Menu menu = new Menu("Desktop");
		
		for (String name: desktopMap.keySet()) {
			MenuItem di = new MenuItem(name);
		    menu.getItems().add(di);
		    setDesktopEventHandler(di, name);
		}
	    return menu;	    
	}
	
	public void setController(DesktopController controller) {
		this.controller = controller;
	}

	public void setDesktopEventHandler(MenuItem desktopMenuItem,
									   String desktopName) {
		desktopMenuItem.setOnAction(e->controller.changeScene(desktopName));
	}
}
