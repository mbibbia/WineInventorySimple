package com.jeitziner.view;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.slf4j.Logger;

import com.jeitziner.json.JsonObjectReader;

import javafx.geometry.Orientation;

/**
 * @author Christian Jeitziner
 * 
 * The Composite pattern (Design Pattern, Gamma/Helm/Johnson/Vlissides)
 * is used to model a window having multiple sub-views which are arranged in
 * horizontal and/or vertical SplitPane controls.

 * A Desktop is a special kind of ViewGroup, it can have only one component,
 * either a View or a ViewGroup. Check this in the method addComponent.
 *
 */
public class Desktop extends ViewGroup {
	//--------------------------------------------------------------------------
	// INSTANCE AND CLASS VARIABLES.
	//--------------------------------------------------------------------------	
	private static final Logger LOG = getLogger(Desktop.class);
	
	private static boolean useTestGroupId = false;
	private static int groupId = 0;

	//--------------------------------------------------------------------------
	// CONSTRUCTORS
	//--------------------------------------------------------------------------
	private Desktop(String name) {
		// We can assign an arbitrary orientation to the first group.
		super(name, Orientation.HORIZONTAL);
	}
	
	//--------------------------------------------------------------------------
	// STATIC METHODS
	//--------------------------------------------------------------------------	
	/*
	public static Desktop createDesktopFromJsonFile(String desktopName, String filePath) {
		JsonObject rootObj = JsonObjectReader.getJsonObjectFromFile(filePath);
		if (rootObj == null) {
			LOG.error("JsonFile: rootObj is null");
			return null;
		}		
		return Desktop.create(desktopName, rootObj);
	}

	public static Desktop createDesktopFromJsonString(String desktopName, String jsonString) {
		JsonObject rootObj = JsonObjectReader.getJsonObjectFromString(jsonString);
		if (rootObj == null) {
			LOG.error("JsonString: rootObj is null");
		return null;
		}		
		return Desktop.create(desktopName, rootObj);
	}
	*/

	public static String createSpacer(char ch, int num) {		
		return new String(new char[num]).replace('\0', ch);
	}
	
	public static Map<String, Desktop> getDesktopsFromFile(String filePath) {
		JsonObject rootObj = JsonObjectReader.getJsonObjectFromFile(filePath);
		if (rootObj == null) {
			LOG.error("JsonFile: rootObj is null");
			return null;
		}		
		return Desktop.getDesktopsFromJsonObj(rootObj);
	}

	
	public static Map<String, Desktop> getDesktopsFromJsonObj(JsonObject rootObj) {
		HashMap<String, Desktop> desktopMap = new HashMap<>();

		if (rootObj == null) {
			LOG.error("Cannot create Desktop with nullObj");
			return null;
		}

		// Add all views to a map.
		Map<String, View> viewMap = new HashMap<>();
		JsonArray views = rootObj.getJsonArray("views");
		if (views == null) {
			LOG.error("Json field 'views' does not exist.");
			return null;
		}		
		LOG.debug(String.format("Number of views: %d", views.size()));		
		for (int vi = 0; vi < views.size(); ++vi) {
			JsonObject currentView = views.getJsonObject(vi);
			String name = currentView.getString("name");
			String fxmlFile = currentView.getString("fxmlFile");
			viewMap.put(name, new View(name, fxmlFile));
		}
		
		// Add all viewGroups to a map
		Map<String, JsonObject> viewGroupObjMap = new HashMap<>();
		JsonArray viewsGroups = rootObj.getJsonArray("viewGroups");
		if (viewsGroups == null) {
			LOG.error("Json field 'viewGroups' does not exist.");
			return null;
		}		
		LOG.debug(String.format("Number of viewGroups: %d", viewsGroups.size()));
		for (int gi = 0; gi < viewsGroups.size(); ++gi) {
			JsonObject currentViewGroup = viewsGroups.getJsonObject(gi);
			String name = currentViewGroup.getString("name");
			viewGroupObjMap.put(name, currentViewGroup);
		}
		
		// Add all desktop objects = JSON objects to a map.
		Map<String, JsonObject> desktopObjMap = new HashMap<>();
		JsonArray desktops = rootObj.getJsonArray("desktops");
		if (desktops == null) {
			LOG.error("Json field 'desktops' does not exist.");
			return null;
		}		
		for (int di = 0; di < desktops.size(); ++di) {
			JsonObject currentDesktopObj = desktops.getJsonObject(di);
			String name = currentDesktopObj.getString("name");
			desktopObjMap.put(name, currentDesktopObj);
		}		

		// Iterate over desktopObjMap
		for (String name: desktopObjMap.keySet()) {
			JsonObject currentDesktopObj = desktopObjMap.get(name);
			String viewGroupName = currentDesktopObj.getString("viewGroup");
			
			Map<String, ViewGroup> viewGroupMap = new HashMap<>();

			// Get viewGroupObj from map
			JsonObject viewGroupObj = viewGroupObjMap.get(viewGroupName);
			if (viewGroupObj == null) {
				LOG.warn(String.format("No view group found with name '%s'", viewGroupName));
				continue;
			}			
			
			JsonArray viewGroups = viewGroupObj.getJsonArray("viewGroups");
			LOG.debug(String.format("Desktop %s. Number of viewsGroups: %d", name, viewGroups.size()));			
			// If there are no viewGroups, ignore this desktop.
			if (viewGroups.size() == 0) {
				continue;
			}
			
			Set<String> processedViewGroupNames = new HashSet<String>(); 
			ViewGroup viewGroup = Desktop.initializeViewGroup(viewGroupName,
					                                          processedViewGroupNames,
					                                          viewGroupObjMap,
					                                          viewMap);
			if (viewGroup != null) {
				Desktop desktop = new Desktop(name);
				desktop.addComponent(viewGroup);
				desktopMap.put(name, desktop);
			}
		}

		return desktopMap;
	}
	
	public static ViewGroup initializeViewGroup(String viewGroupName,
			                                    Set<String> processedViewGroupNames, 
			                                    Map<String, JsonObject> viewGroupObjMap,
			                                    Map<String, View> viewMap) {
		
		if (processedViewGroupNames.contains(viewGroupName)) {
			return null;
		}
		
		JsonObject viewGroupObj = viewGroupObjMap.get(viewGroupName);
		if (viewGroupObj == null) {
			return null;
		}
		
		String orientationStr = viewGroupObj.getString("orientation");		
		Orientation orientation = ViewGroup.getOrientation(orientationStr);
		ViewGroup viewGroup = new ViewGroup(viewGroupName, orientation);
		
		// We don't want to process viewGroupName again.
		processedViewGroupNames.add(viewGroupName);		
		JsonArray subViewGroups = viewGroupObj.getJsonArray("viewGroups");
		if (subViewGroups.size() == 0) {
			return null;
		}
		
		boolean acceptViewGroup = false;
		for (int svgi = 0; svgi < subViewGroups.size(); ++svgi) {
			JsonObject currentSubViewGroup = subViewGroups.getJsonObject(svgi);
			String type = currentSubViewGroup.getString("type");
			String subViewGroupName = currentSubViewGroup.getString("name");
			if (type.equals("view")) {
				View view = viewMap.getOrDefault(subViewGroupName, null);
				if (view != null) {
					acceptViewGroup = true;
					// Create a clone of the view.
					View cloneView = new View(view.getName(), view.getFxmlFile());					
					viewGroup.addComponent(cloneView);
				}
			} else if (type.equals("viewGroup")) {
				ViewGroup subViewGroup = Desktop.initializeViewGroup(subViewGroupName,
																	 processedViewGroupNames,
																	 viewGroupObjMap,
																	 viewMap);				
				if (subViewGroup != null) {
					acceptViewGroup = true;
					viewGroup.addComponent(subViewGroup);
				}			
			}			
		}

		if (acceptViewGroup) {
			return viewGroup;
		}
				
		return null;
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
			String fxmlFile = currentView.getString("fxmlFile");
			viewMap.put(name, new View(name, fxmlFile));
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
			String orientationStr = currentViewGroup.getString("orientation");
			Orientation orientation = ViewGroup.getOrientation(orientationStr);			
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

		return new Desktop(desktopName);
	}

	//--------------------------------------------------------------------------
	// METHODS
	//--------------------------------------------------------------------------
	
	public void addComponentLeft(Component component, boolean splitCurrentComponent) {
		// Empty implementation, we cannot add a component to the left
		// of a desktop.
	}

	/**
	 * @return : Unique groupName, either ViewGroup_1, ViewGroup_2, ...
	 *           or UUID.
	 */
	static String createGroupName() {
		String returnValue;
		
		if (Desktop.getUseTestGroupId()) {
			groupId += 1;
			returnValue = String.format("ViewGroup_%d", groupId);
		} else {
			UUID uuid = UUID.randomUUID();
			returnValue = uuid.toString();
		}
		
		return returnValue;
	}
	
	//--------------------------------------------------------------------------
	// UTILITY METHODS
	//--------------------------------------------------------------------------
	public String toString() {
		return toString(0);
	}

	public String toString(int indent) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s", super.toString(indent)));
		return sb.toString();
	}

	//--------------------------------------------------------------------------
	// GETTER AND SETTER METHODS
	//--------------------------------------------------------------------------	
	static boolean getUseTestGroupId() {
		return useTestGroupId;
	}

	static void setUseTestGroupId(boolean useTestGroupId) {
		Desktop.useTestGroupId = useTestGroupId;
	}

}
