package com.jeitziner.test.desktop;

import static org.junit.Assert.*;

import static org.slf4j.LoggerFactory.getLogger;

import java.nio.file.Paths;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;

import com.jeitziner.view.Desktop;
import com.jeitziner.view.View;
import com.jeitziner.view.ViewGroup;

public class DesktopTest {

	private static final Logger LOG = getLogger(DesktopTest.class);
	
	private String basePath;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.basePath = "src/test/resources/desktops";
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createBasicDesktopFromFile() {	
		LOG.info("");
		LOG.info("Test: createBasicDesktopFromFile");
		String jsonFileName = "desktopTest1.json";
		String jsonFilePath = Paths.get(this.basePath, jsonFileName).toString();		
		Map<String, Desktop> desktopMap = Desktop.getDesktopsFromFile(jsonFilePath);
		Desktop desktop = desktopMap.getOrDefault("Desktop1", null);
		assertNotNull(desktop);
	}

	@Test
	public void addViewToDesktopLeft() {	
		LOG.info("");
		LOG.info("Test: addViewToDesktopLeft");
		String jsonFileName = "desktopTest1.json";
		String jsonFilePath = Paths.get(this.basePath, jsonFileName).toString();		
		Map<String, Desktop> desktopMap = Desktop.getDesktopsFromFile(jsonFilePath);
		Desktop desktop = desktopMap.getOrDefault("Desktop1", null);
		assertNotNull(desktop);

		System.out.println("Before");
		System.out.println(desktop.toString());
		
		assert(desktop.getComponents().size() > 0);
		if (desktop.getComponents().size() == 0) {
			return;
		}
	}

	@Test
	public void addViewToViewLeft() {	
		LOG.info("");
		LOG.info("Test: addViewToViewLeft");
		String jsonFileName = "desktopTest2.json";
		String jsonFilePath = Paths.get(this.basePath, jsonFileName).toString();		
		Map<String, Desktop> desktopMap = Desktop.getDesktopsFromFile(jsonFilePath);
		Desktop desktop = desktopMap.getOrDefault("Desktop1", null);
		assertNotNull(desktop);

		System.out.println("Before");
		System.out.println(desktop.toString());
		
		assert(desktop.getComponents().size() > 0);
		if (desktop.getComponents().size() == 0) {
			return;
		}
	
		ViewGroup viewGroup = (ViewGroup)desktop.getComponents().get(0);
		assertNotNull(viewGroup);
		
		assert(viewGroup.getComponents().size() > 0);
		if (viewGroup.getComponents().size() == 0) {
			return;
		}
		
		ViewGroup subViewGroup = (ViewGroup)viewGroup.getComponents().get(0);
		assertNotNull(subViewGroup);
		
		assert(subViewGroup.getComponents().size() > 0);
		if (subViewGroup.getComponents().size() == 0) {
			return;
		}
		
		View view = (View)subViewGroup.getComponents().get(0);
		assertNotNull(view);
		
		// We don't need the fxmlFile in this test, pass dummy string as second parameter.
		View newView = new View("LeftView", "dummy.fxml");	
		
		view.addComponentLeft(newView, false);
		
		System.out.println("");
		System.out.println("After");
		System.out.println(desktop.toString());
	}

}
