package com.jeitziner.view;

import ch.bibbias.view.FxmlLoader;
import javafx.scene.layout.Pane;

public class View extends Component {	
	//--------------------------------------------------------------------------
	// INSTANCE AND CLASS VARIABLES.
	//--------------------------------------------------------------------------
	
	/**
	 * A view is identified by name.
	 */
	private String name;
	
	/**
	 * The view is stored in the fxml file.
	 */
	private String fxmlFile;

	//--------------------------------------------------------------------------
	// CONSTRUCTORS
	//--------------------------------------------------------------------------		
	public View(String name, String fxmlFile) {
		this.name = name;
		this.fxmlFile = fxmlFile;
	}

	//--------------------------------------------------------------------------
	// METHODS
	//--------------------------------------------------------------------------
	public void addComponentLeft(Component component, boolean splitCurrentComponent) {
		// Delegate to parent view group
		getParentViewGroup().addComponentLeft(component, splitCurrentComponent);
	}

	public void addComponentRight(Component component, boolean splitCurrentComponent) {
		// Delegate to parent view group
		getParentViewGroup().addComponentRight(component, splitCurrentComponent);
	}
	
	public Pane createPane(FxmlLoader loader) {
		Pane pane = (Pane)loader.load(this.fxmlFile);
		return pane;
	}

	//--------------------------------------------------------------------------
	// UTILITY METHODS
	//--------------------------------------------------------------------------
	public String toString(int indent) {
		String spacer = Desktop.createSpacer('-', indent);
		return String.format("\n%sView: %s %s", spacer, getName(), getFxmlFile());
	}
	
	//--------------------------------------------------------------------------
	// GETTER AND SETTER METHODS
	//--------------------------------------------------------------------------	
	String getName() {
		return this.name;
	}

	String getFxmlFile() {
		return this.fxmlFile;
	}
}
