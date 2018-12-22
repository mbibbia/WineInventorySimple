package com.jeitziner.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;

import com.jeitziner.json.JsonObjectReader;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Region;

/**
 * Usage of Composite pattern (Design Pattern, Gamma/Helm/Johnson/Vlissides)
 * to model a window having multiple subviews which are arranged in
 * horizontal and/or vertical SplitPane panes.
 * 
 * @author Christian Jeitziner
 *
 */
interface Component {
	Region getRegion();
	String toString();
}

class ViewGroup implements Component {
	private String name;
	private String orientation;
	protected List<Component> components;

	public ViewGroup(String name, String orientation) {
		this.name = name;
		this.orientation = orientation;
		this.components = new ArrayList<>();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("ViewGroup: %s", getName()));
		for (Component component: this.components) {
			sb.append(component.toString());
		}
		return sb.toString();
	}

	String getName() {
		return this.name;
	}

	String getOrientation() {
		return this.orientation;
	}

	void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public void addComponent(Component component) {
		this.components.add(component);
	}

	public Region getRegion() {
		if (this.components.size() > 1) {
			final SplitPane splitPane = new SplitPane();
			if (this.getOrientation().toLowerCase().equals("horizontal")) {
				splitPane.setOrientation(Orientation.HORIZONTAL);				
			} else {
				splitPane.setOrientation(Orientation.VERTICAL);				
			}
			for (Component component : this.components) {
				splitPane.getItems().add(component.getRegion());
			}
			return splitPane;
		} else if (this.components.size() == 1) {
			return this.components.get(0).getRegion();
		}
		return null;
	}
}

class View implements Component {	
	/**
	 * A view can be identified by a unique name.
	 */
	private String name;

	public View(String name) {
		this.name = name;
	}
	
	public String toString() {
		return String.format("View: %s", getName());
	}
	
	String getName() {
		return this.name;
	}

	public Region getRegion() {
		return ViewFactory.getInstance().getRegion(name);
	}
}

public class Desktop {
	private String name;
	private ViewGroup rootGroup;

	private Desktop(String name, ViewGroup rootGroup) {
		this.name = name;
		this.rootGroup = rootGroup;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Desktop %s", getName()));
		sb.append(this.rootGroup.toString());
		return sb.toString();
	}
	
	public String getName() {
		return this.name;
	}

	public static Desktop createDesktopFromJsonFile(String desktopName, String filePath) {
		JsonObject rootObj = JsonObjectReader.getJsonObjectFromFile(filePath);
		if (rootObj == null) {
			System.out.println("JsonFile: rootObj is null");
			return null;
		}		
		return Desktop.create(desktopName, rootObj);
	}

	public static Desktop createDesktopFromJsonString(String desktopName, String jsonString) {
		JsonObject rootObj = JsonObjectReader.getJsonObjectFromString(jsonString);
		if (rootObj == null) {
			System.out.println("JsonString: rootObj is null");
			return null;
		}		
		return Desktop.create(desktopName, rootObj);
	}


	public static Map<String, Desktop> getDesktopsFromFile(String filePath) {
		JsonObject rootObj = JsonObjectReader.getJsonObjectFromFile(filePath);
		if (rootObj == null) {
			System.out.println("JsonFile: rootObj is null");
			return null;
		}		
		return Desktop.getDesktopsFromJsonObj(rootObj);
	}

	
	public static Map<String, Desktop> getDesktopsFromJsonObj(JsonObject rootObj) {
		HashMap<String, Desktop> map = new HashMap<>();

		if (rootObj == null) {
			System.out.println("Cannot create Desktop with nullObj");
			return map;
		}

		// Read views from rootObj.
		Map<String, View> viewMap = new HashMap<>();
		JsonArray views = rootObj.getJsonArray("views");
		if (views == null) {
			System.out.println("views is null");
			return null;
		}
		for (int vi = 0; vi < views.size(); ++vi) {
			JsonObject currentView = views.getJsonObject(vi);
			String name = currentView.getString("name");
			viewMap.put(name, new View(name));
		}
		
		JsonArray desktops = rootObj.getJsonArray("desktops");
		Map<String, JsonObject> desktopObjMap = new HashMap<>();
		for (int di = 0; di < desktops.size(); ++di) {
			JsonObject currentDesktopObj = desktops.getJsonObject(di);
			String name = currentDesktopObj.getString("name");
			desktopObjMap.put(name, currentDesktopObj);
		}		

		// Iterate over desktopObjMap
		for (String name: desktopObjMap.keySet()) {
			JsonObject desktopObj = desktopObjMap.get(name);
			String rootGroupName = desktopObj.getString("viewGroup");

			// Read all viewGroups for desktop. This is a pre-initialization
			// step, the components list is not yet initialized.
			Map<String, ViewGroup> viewGroupMap = new HashMap<>();
			JsonArray viewGroups = rootObj.getJsonArray("viewGroups");

			// If there are no viewGroups, ignore this desktop.
			if (viewGroups.size() == 0) {
				continue;
			}
			
			ViewGroup rootViewGroup = null;

			for (int vgi = 0; vgi < viewGroups.size(); ++vgi) {
				JsonObject currentViewGroup = viewGroups.getJsonObject(vgi);
				String groupName = currentViewGroup.getString("name");
				String orientation = currentViewGroup.getString("orientation");
				ViewGroup viewGroup = new ViewGroup(groupName, orientation);
				if (groupName.equals(rootGroupName)) {
					rootViewGroup = viewGroup; 
				}
				viewGroupMap.put(groupName, viewGroup);
			}		

			// It's important that we detect recursion. ignore a viewGroup,
			// if it has not yet been initialized.
			Map<String, ViewGroup> processedViewGroups = new HashMap<>();

			// Read all viewGroups again to initialize rootViewGroup.
			for (int vgi = 0; vgi < viewGroups.size(); ++vgi) {
				JsonObject currentViewGroup = viewGroups.getJsonObject(vgi);
				String groupName = currentViewGroup.getString("name");
				ViewGroup viewGroup = viewGroupMap.get(groupName);

				JsonArray subViewGroups = currentViewGroup.getJsonArray("viewGroups");
				for (int svgi = 0; svgi < subViewGroups.size(); ++svgi) {
					JsonObject currentSubViewGroup = subViewGroups.getJsonObject(svgi);
					String type = currentSubViewGroup.getString("type");
					String subViewGroupName = currentSubViewGroup.getString("name");
					if (type.equals("view")) {
						View view = viewMap.getOrDefault(subViewGroupName, null);
						if (view != null) {
							viewGroup.addComponent(view);
						}
					} else if (type.equals("viewGroup")) {
						ViewGroup processedViewGroup = processedViewGroups.getOrDefault(subViewGroupName, null);
						if (processedViewGroup != null) {
							viewGroup.addComponent(processedViewGroup);
						}
					}
				}
				processedViewGroups.put(groupName, viewGroup);
			}			
			map.put(name, new Desktop(name, rootViewGroup));			
		}

		return map;
	} 

	public static Desktop create(String desktopName, JsonObject rootObj) {
		if (rootObj == null) {
			System.out.println("Cannot create Desktop with nullObj");
			return null;
		}

		JsonArray desktops = rootObj.getJsonArray("desktops");
		Map<String, JsonObject> desktopMap = new HashMap<>();
		for (int di = 0; di < desktops.size(); ++di) {
			JsonObject currentDesktop = desktops.getJsonObject(di);
			String name = currentDesktop.getString("name");
			desktopMap.put(name, currentDesktop);
		}		
		JsonObject desktop = desktopMap.getOrDefault(desktopName, null);
		if (desktop == null) {
			System.out.println(String.format("No desktop found with name '%s'", desktopName));
			return null;
		}
		String rootGroupName = desktop.getString("viewGroup");

		// Read views from rootObj.
		Map<String, View> viewMap = new HashMap<>();
		JsonArray views = rootObj.getJsonArray("views");
		if (views == null) {
			System.out.println("views is null");
			return null;
		}
		for (int vi = 0; vi < views.size(); ++vi) {
			JsonObject currentView = views.getJsonObject(vi);
			String name = currentView.getString("name");
			viewMap.put(name, new View(name));
		}		

		// Read all viewGroups for desktop. This is a pre-initialization
		// step, the components list is not yet initialized.
		Map<String, ViewGroup> viewGroupMap = new HashMap<>();
		JsonArray viewGroups = rootObj.getJsonArray("viewGroups");

		// If there are no viewGroups, we return null.
		if (viewGroups.size() == 0) {
			return null;
		}
		ViewGroup rootViewGroup = null;

		for (int vgi = 0; vgi < viewGroups.size(); ++vgi) {
			JsonObject currentViewGroup = viewGroups.getJsonObject(vgi);
			String name = currentViewGroup.getString("name");
			String orientation = currentViewGroup.getString("orientation");
			ViewGroup viewGroup = new ViewGroup(name, orientation);
			if (name.equals(rootGroupName)) {
				rootViewGroup = viewGroup; 
			}
			viewGroupMap.put(name, viewGroup);
		}		

		// It's important that we detect recursion. ignore a viewGroup,
		// if it has not yet been initialized.
		Map<String, ViewGroup> processedViewGroups = new HashMap<>();

		// Read all viewGroups again to initialize rootViewGroup.
		for (int vgi = 0; vgi < viewGroups.size(); ++vgi) {
			JsonObject currentViewGroup = viewGroups.getJsonObject(vgi);
			String name = currentViewGroup.getString("name");
			ViewGroup viewGroup = viewGroupMap.get(name);

			JsonArray subViewGroups = currentViewGroup.getJsonArray("viewGroups");
			for (int svgi = 0; svgi < subViewGroups.size(); ++svgi) {
				JsonObject currentSubViewGroup = subViewGroups.getJsonObject(svgi);
				String type = currentSubViewGroup.getString("type");
				String subViewGroupName = currentSubViewGroup.getString("name");
				if (type.equals("view")) {
					View view = viewMap.getOrDefault(subViewGroupName, null);
					if (view != null) {
						viewGroup.addComponent(view);
					}
				} else if (type.equals("viewGroup")) {
					ViewGroup processedViewGroup = processedViewGroups.getOrDefault(subViewGroupName, null);
					if (processedViewGroup != null) {
						viewGroup.addComponent(processedViewGroup);
					}
				}
			}
			processedViewGroups.put(name, viewGroup);
		}		

		return new Desktop(desktopName, rootViewGroup);
	}

	public Region getRegion() {
		if (this.rootGroup == null) {
			return null;
		}
		return this.rootGroup.getRegion();
	}
}
