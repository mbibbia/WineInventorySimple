package com.jeitziner.view;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;

import ch.bibbias.view.FxmlLoader;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ViewGroup extends Component {
	//--------------------------------------------------------------------------
	// INSTANCE AND CLASS VARIABLES.
	//--------------------------------------------------------------------------
	private static final Logger LOG = getLogger(ViewGroup.class);

	/**
	 * name : name for this group
	 */
	private String name;
	
	/**
	 * The orientation defines how the components are arranged in the SplitPane.
	 * Orientation.HORIZONTAL e.g. means the components are arranged from left to
	 * right, the SplitPane dividers are vertical.
	 */
	private Orientation orientation;
		
	/**
	 * The children of this view group.
	 */
	protected List<Component> components;

	//--------------------------------------------------------------------------
	// CONSTRUCTORS
	//--------------------------------------------------------------------------
	public ViewGroup(String name, Orientation orientation) {
		this.name = name;
		this.orientation = orientation;
		this.components = new ArrayList<>();
	}

	//--------------------------------------------------------------------------
	// STATIC METHODS
	//--------------------------------------------------------------------------
	public static Orientation getOrientation(String orientation) {
		if (orientation.toLowerCase().equals("horizontal")) {
			return Orientation.HORIZONTAL;
		}
		return Orientation.VERTICAL;
	}

	//--------------------------------------------------------------------------
	// METHODS
	//--------------------------------------------------------------------------	
	public void addComponentLeft(String name, Component component, boolean splitCurrentComponent) {
		// If the orientation is horizontal , we can just insert a component at
		// the first position of this.components ArrayList.
		if (getOrientation() == Orientation.HORIZONTAL) {
			// It's not necessary to add a new group if the orientation is 
			// horizontal.
			// front = true means add at front of component list
			addComponent(component, true);
		} else {
			// If the orientation is vertical, we need to wrap this view group
			// in a new view group whose orientation is horizontal. Then we add
			// component and this view group to the new view group.

			// Create a temporary list of all components
			ArrayList<Component> tempComponents = new ArrayList<Component>(this.components.size());
			for (Component cmp: this.components) {
				tempComponents.add(cmp);
			}
			
			// Clear this.components and recreate it.
			this.components.clear();
							
			if (splitCurrentComponent) {
				for (int i = 0; i < tempComponents.size(); ++i) {
					if (i == 0) {
						// Create a new ViewGroup
						ViewGroup viewGroup = new ViewGroup(name,
								                            Orientation.HORIZONTAL);
						viewGroup.addComponent(component);
						viewGroup.addComponent(tempComponents.get(i));
						addComponent(viewGroup);
					} else {
						addComponent(tempComponents.get(i));
					}
				}
			} else {
				// Need to change orientation, since new component is added to the left
				// of existing components.
				setOrientation(Orientation.HORIZONTAL);
				
				addComponent(component);
				
				// Create a new ViewGroup
				ViewGroup viewGroup = new ViewGroup(name,
						                            Orientation.VERTICAL);
				for (int i = 0; i < tempComponents.size(); ++i) {
					viewGroup.addComponent(tempComponents.get(i));						
				}									
				addComponent(viewGroup);				
			}
		}
	}

	public void addComponentRight(String name, Component component, boolean splitCurrentComponent) {
		// If the orientation is horizontal , we can just insert a component at
		// the first position of this.components ArrayList.
		if (getOrientation() == Orientation.HORIZONTAL) {
			// It's not necessary to add a new group if the orientation is 
			// horizontal.
			// front = false means add at end of component list
			addComponent(component, false);
		} else {
			// If the orientation is vertical, we need to wrap this view group
			// in a new view group whose orientation is horizontal. Then we add
			// component and this view group to the new view group.

			// Create a temporary list of all components
			ArrayList<Component> tempComponents = new ArrayList<Component>(this.components.size());
			for (Component cmp: this.components) {
				tempComponents.add(cmp);
			}
			
			// Clear this.components and recreate it.
			this.components.clear();
							
			if (splitCurrentComponent) {
				for (int i = 0; i < tempComponents.size(); ++i) {
					if (i == tempComponents.size() - 1) {
						// Create a new ViewGroup
						ViewGroup viewGroup = new ViewGroup(name,
								                            Orientation.HORIZONTAL);
						viewGroup.addComponent(tempComponents.get(i));
						viewGroup.addComponent(component);
						addComponent(viewGroup);
					} else {
						addComponent(tempComponents.get(i));
					}
				}
			} else {
				// Need to change orientation, since new component is added to the right
				// of existing components.
				setOrientation(Orientation.HORIZONTAL);
								
				// Create a new ViewGroup
				ViewGroup viewGroup = new ViewGroup(name,
						                            Orientation.VERTICAL);
				for (int i = 0; i < tempComponents.size(); ++i) {
					viewGroup.addComponent(tempComponents.get(i));						
				}
				addComponent(viewGroup);	
				
				addComponent(component);				
			}
		}
	}

	public void addComponentTop(String name, Component component, boolean splitCurrentComponent) {
		// If the orientation is vertical, we can just insert a component at
		// the first position of this.components ArrayList.
		if (getOrientation() == Orientation.VERTICAL) {
			// It's not necessary to add a new group if the orientation is 
			// horizontal.
			// front = true means add at front of component list
			addComponent(component, true);
		} else {
			// If the orientation is horizontal, we need to wrap this view group
			// in a new view group whose orientation is vertical. Then we add
			// component and this view group to the new view group.

			// Create a temporary list of all components
			ArrayList<Component> tempComponents = new ArrayList<Component>(this.components.size());
			for (Component cmp: this.components) {
				tempComponents.add(cmp);
			}
			
			// Clear this.components and recreate it.
			this.components.clear();
							
			if (splitCurrentComponent) {
				for (int i = 0; i < tempComponents.size(); ++i) {
					if (i == 0) {
						// Create a new ViewGroup
						ViewGroup viewGroup = new ViewGroup(name,
								                            Orientation.VERTICAL);
						viewGroup.addComponent(component);
						viewGroup.addComponent(tempComponents.get(i));
						addComponent(viewGroup);
					} else {
						addComponent(tempComponents.get(i));
					}
				}
			} else {
				// Need to change orientation, since new component is added at the top
				// of existing components.
				setOrientation(Orientation.VERTICAL);
				
				addComponent(component);
				
				// Create a new ViewGroup
				ViewGroup viewGroup = new ViewGroup(name,
						                            Orientation.HORIZONTAL);
				for (int i = 0; i < tempComponents.size(); ++i) {
					viewGroup.addComponent(tempComponents.get(i));						
				}									
				addComponent(viewGroup);				
			}
		}
	}

	public void addComponentBottom(String name, Component component, boolean splitCurrentComponent) {
		// If the orientation is vertical, we can just insert a component at
		// the last position of this.components ArrayList.
		if (getOrientation() == Orientation.VERTICAL) {
			// It's not necessary to add a new group if the orientation is 
			// horizontal.
			// front = false means add at end of component list
			addComponent(component, false);
		} else {
			// If the orientation is horizontal, we need to wrap this view group
			// in a new view group whose orientation is vertical. Then we add
			// component and this view group to the new view group.

			// Create a temporary list of all components
			ArrayList<Component> tempComponents = new ArrayList<Component>(this.components.size());
			for (Component cmp: this.components) {
				tempComponents.add(cmp);
			}
			
			// Clear this.components and recreate it.
			this.components.clear();
							
			if (splitCurrentComponent) {
				for (int i = 0; i < tempComponents.size(); ++i) {
					if (i == tempComponents.size() - 1) {
						// Create a new ViewGroup
						ViewGroup viewGroup = new ViewGroup(name,
								                            Orientation.VERTICAL);
						viewGroup.addComponent(tempComponents.get(i));
						viewGroup.addComponent(component);
						addComponent(viewGroup);
					} else {
						addComponent(tempComponents.get(i));
					}
				}
			} else {
				// Need to change orientation, since new component is added at the top
				// of existing components.
				setOrientation(Orientation.VERTICAL);
				
				// Create a new ViewGroup
				ViewGroup viewGroup = new ViewGroup(name,
						                            Orientation.HORIZONTAL);
				for (int i = 0; i < tempComponents.size(); ++i) {
					viewGroup.addComponent(tempComponents.get(i));						
				}									
				addComponent(viewGroup);				

				addComponent(component);				
			}
		}
	}
	
	public void addComponent(Component component, boolean front) {
		// Set connection from parent to child.
		component.setParentViewGroup(this);
		// Set connection from child to parent.
		if (front == false) {
			this.components.add(component);
		} else {
			this.components.add(0, component);
		}
	}

	public void addComponent(Component component) {
		addComponent(component, false);
	}
			
	public Pane createPane(FxmlLoader loader) {
		if (this.components.size() > 1) {
			final AnchorPane anchorPane = new AnchorPane();			
			final SplitPane splitPane = new SplitPane();

			// Connect subPane on all sides to enclosing anchorPane.
			AnchorPane.setTopAnchor(splitPane, 5.0);
			AnchorPane.setLeftAnchor(splitPane, 5.0);
			AnchorPane.setRightAnchor(splitPane, 5.0);
			AnchorPane.setBottomAnchor(splitPane, 5.0);
			
			anchorPane.getChildren().add(splitPane);
			splitPane.setOrientation(getOrientation());

			for (Component component : this.components) {
				final AnchorPane innerAnchorPane = new AnchorPane();
				splitPane.getItems().add(innerAnchorPane);
				Pane subPane = component.createPane(loader);
				innerAnchorPane.getChildren().add(subPane);	

				// Connect subPane on all sides to enclosing anchorPane.
				AnchorPane.setTopAnchor(subPane, 5.0);
				AnchorPane.setLeftAnchor(subPane, 5.0);
				AnchorPane.setRightAnchor(subPane, 5.0);
				AnchorPane.setBottomAnchor(subPane, 5.0);				
			}
			return anchorPane;
			
		} else if (this.components.size() == 1) {
			return this.components.get(0).createPane(loader);
		}
		return null;
	}
	
	//--------------------------------------------------------------------------
	// UTILITY METHODS
	//--------------------------------------------------------------------------
	public String toString() {
		return toString(0);
	}

	public String toString(int indent) {
		String spacer = Desktop.createSpacer('-', indent);
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("\n%sViewGroup (%s): %s", spacer, getOrientation(), getName()));
		for (Component component: this.components) {
			sb.append(String.format("%s", component.toString(indent + 2)));
		}
		return sb.toString();
	}
		
	//--------------------------------------------------------------------------
	// GETTER AND SETTER METHODS
	//--------------------------------------------------------------------------
	public String getName() {
		return this.name;
	}

	private Orientation getOrientation() {
		return this.orientation;
	}
	
	private void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	
	public List<Component> getComponents() {
		return this.components;
	}
	

}
