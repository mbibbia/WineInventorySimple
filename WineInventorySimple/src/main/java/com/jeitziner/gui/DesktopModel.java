package com.jeitziner.gui;

import java.io.InputStream;
import java.util.Map;

import com.jeitziner.view.Desktop;

/**
 * @author Christian Jeitziner
 *
 */
public class DesktopModel {
	//--------------------------------------------------------------------------
	// INSTANCE AND CLASS VARIABLES.
	//--------------------------------------------------------------------------
	private Map<String, Desktop> desktopMap;
	
	//--------------------------------------------------------------------------
	// CONSTRUCTORS
	//--------------------------------------------------------------------------

	/**
	 * @param is InputStream which identifies a JSON file desktop.json where
	 *        the desktop configuration is stored.
	 */
	public DesktopModel(InputStream is) {
		this.desktopMap = Desktop.getDesktopsFromInputStream(is);
	}

	//--------------------------------------------------------------------------
	// GETTER AND SETTER METHODS
	//--------------------------------------------------------------------------
	public Map<String, Desktop> getDesktopMap() {
		return this.desktopMap;
	}
}
