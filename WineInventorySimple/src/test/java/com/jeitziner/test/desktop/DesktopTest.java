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

		desktop.setUseTestGroupId(true);

		ViewGroup viewGroup = (ViewGroup)desktop.getComponents().get(0);			
		ViewGroup subViewGroup = (ViewGroup)viewGroup.getComponents().get(0);				
		View view = (View)subViewGroup.getComponents().get(0);
		
		View newView = new View("LeftView", "dummy.fxml");	
		String name = desktop.createGroupName();
		view.addComponentLeft(name, newView, false);
				
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("ViewGroup (HORIZONTAL): Desktop1" + "\n");
		sb.append("--ViewGroup (VERTICAL): group1" + "\n");
		sb.append("----ViewGroup (HORIZONTAL): group2" + "\n");
		sb.append("------View: LeftView dummy.fxml" + "\n");
		sb.append("------ViewGroup (VERTICAL): ViewGroup_1" + "\n");
		sb.append("--------View: view1 /fxml/dummy.fxml" + "\n");
		sb.append("--------View: view2 /fxml/dummy.fxml" + "\n");
		sb.append("----ViewGroup (VERTICAL): group3" + "\n");
		sb.append("------View: view1 /fxml/dummy.fxml" + "\n");
		sb.append("----ViewGroup (VERTICAL): group4" + "\n");
		sb.append("------View: view1 /fxml/dummy.fxml");
		String desktopString = sb.toString();
				
		assertTrue(desktop.toString().equals(desktopString));		
	}

	@Test
	public void addViewToViewRight() {	
		LOG.info("");
		LOG.info("Test: addViewToViewLeft");
		String jsonFileName = "desktopTest2.json";
		String jsonFilePath = Paths.get(this.basePath, jsonFileName).toString();		
		Map<String, Desktop> desktopMap = Desktop.getDesktopsFromFile(jsonFilePath);
		Desktop desktop = desktopMap.getOrDefault("Desktop1", null);

		desktop.setUseTestGroupId(true);

		ViewGroup viewGroup = (ViewGroup)desktop.getComponents().get(0);			
		ViewGroup subViewGroup = (ViewGroup)viewGroup.getComponents().get(0);				
		View view = (View)subViewGroup.getComponents().get(0);
		
		System.out.println(desktop.toString());
		
		View newView = new View("RightView", "dummy.fxml");		
		String name = desktop.createGroupName();
		view.addComponentRight(name, newView, false);
				
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("ViewGroup (HORIZONTAL): Desktop1" + "\n");
		sb.append("--ViewGroup (VERTICAL): group1" + "\n");
		sb.append("----ViewGroup (HORIZONTAL): group2" + "\n");
		sb.append("------ViewGroup (VERTICAL): ViewGroup_1" + "\n");
		sb.append("--------View: view1 /fxml/dummy.fxml" + "\n");
		sb.append("--------View: view2 /fxml/dummy.fxml" + "\n");
		sb.append("------View: RightView dummy.fxml" + "\n");
		sb.append("----ViewGroup (VERTICAL): group3" + "\n");
		sb.append("------View: view1 /fxml/dummy.fxml" + "\n");
		sb.append("----ViewGroup (VERTICAL): group4" + "\n");
		sb.append("------View: view1 /fxml/dummy.fxml");
		String desktopString = sb.toString();

		System.out.println(String.format("<%s>", desktop.toString()));
		System.out.println(String.format("<%s>", desktopString));		
		
		assertTrue(desktop.toString().equals(desktopString));		
	}
	
	@Test
	public void addViewToViewTop() {	
		LOG.info("");
		LOG.info("Test: addViewToViewLeft");
		String jsonFileName = "desktopTest2.json";
		String jsonFilePath = Paths.get(this.basePath, jsonFileName).toString();		
		Map<String, Desktop> desktopMap = Desktop.getDesktopsFromFile(jsonFilePath);
		Desktop desktop = desktopMap.getOrDefault("Desktop1", null);

		desktop.setUseTestGroupId(true);

		ViewGroup viewGroup = (ViewGroup)desktop.getComponents().get(0);			
		ViewGroup subViewGroup = (ViewGroup)viewGroup.getComponents().get(0);				
		View view = (View)subViewGroup.getComponents().get(0);
		
		System.out.println("original");
		System.out.println(String.format("<%s>", desktop.toString()));
		
		
		View newView = new View("RightView", "/nodir/nofile.fxml");		
		String name = desktop.createGroupName();
		view.addComponentTop(name, newView, false);
				
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("ViewGroup (HORIZONTAL): Desktop1" + "\n");
		sb.append("--ViewGroup (VERTICAL): group1" + "\n");
		sb.append("----ViewGroup (VERTICAL): group2" + "\n");
		sb.append("------View: RightView /nodir/nofile.fxml" + "\n");
		sb.append("------View: view1 /fxml/dummy.fxml" + "\n");
		sb.append("------View: view2 /fxml/dummy.fxml" + "\n");
		sb.append("----ViewGroup (VERTICAL): group3" + "\n");
		sb.append("------View: view1 /fxml/dummy.fxml" + "\n");
		sb.append("----ViewGroup (VERTICAL): group4" + "\n");
		sb.append("------View: view1 /fxml/dummy.fxml");
		String desktopString = sb.toString();

		System.out.println("changed");
		System.out.println(String.format("<%s>", desktop.toString()));
		System.out.println("expected");
		System.out.println(String.format("<%s>", desktopString));		
		
		assertTrue(desktop.toString().equals(desktopString));		
	}

	@Test
	public void addViewToViewBottom() {	
		LOG.info("");
		LOG.info("Test: addViewToViewLeft");
		String jsonFileName = "desktopTest2.json";
		String jsonFilePath = Paths.get(this.basePath, jsonFileName).toString();		
		Map<String, Desktop> desktopMap = Desktop.getDesktopsFromFile(jsonFilePath);
		Desktop desktop = desktopMap.getOrDefault("Desktop1", null);

		desktop.setUseTestGroupId(true);

		ViewGroup viewGroup = (ViewGroup)desktop.getComponents().get(0);			
		ViewGroup subViewGroup = (ViewGroup)viewGroup.getComponents().get(0);				
		View view = (View)subViewGroup.getComponents().get(0);
		
		System.out.println("original");
		System.out.println(String.format("<%s>", desktop.toString()));
		
		
		View newView = new View("RightView", "/nodir/nofile.fxml");		
		String name = desktop.createGroupName();
		view.addComponentBottom(name, newView, false);
				
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("ViewGroup (HORIZONTAL): Desktop1" + "\n");
		sb.append("--ViewGroup (VERTICAL): group1" + "\n");
		sb.append("----ViewGroup (VERTICAL): group2" + "\n");
		sb.append("------View: view1 /fxml/dummy.fxml" + "\n");
		sb.append("------View: view2 /fxml/dummy.fxml" + "\n");
		sb.append("------View: RightView /nodir/nofile.fxml" + "\n");
		sb.append("----ViewGroup (VERTICAL): group3" + "\n");
		sb.append("------View: view1 /fxml/dummy.fxml" + "\n");
		sb.append("----ViewGroup (VERTICAL): group4" + "\n");
		sb.append("------View: view1 /fxml/dummy.fxml");
		String desktopString = sb.toString();

		System.out.println("changed");
		System.out.println(String.format("<%s>", desktop.toString()));
		System.out.println("expected");
		System.out.println(String.format("<%s>", desktopString));		
		
		assertTrue(desktop.toString().equals(desktopString));		
	}
	
	
}
