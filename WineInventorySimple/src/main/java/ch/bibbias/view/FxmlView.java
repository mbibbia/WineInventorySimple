package ch.bibbias.view;

import java.util.ResourceBundle;

public enum FxmlView {

	WINE {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("wine.title");

		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Wine.fxml";
		}

	};

	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}

}
