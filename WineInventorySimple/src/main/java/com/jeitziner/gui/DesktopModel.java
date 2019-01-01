package com.jeitziner.gui;

import java.io.InputStream;
import java.util.Map;

import com.jeitziner.view.Desktop;

public class DesktopModel {
	private Map<String, Desktop> desktopMap;
	
	public DesktopModel(InputStream is) {
		this.desktopMap = Desktop.getDesktopsFromInputStream(is);
	}
				
	public Map<String, Desktop> getDesktopMap() {
		return this.desktopMap;
	}
}
