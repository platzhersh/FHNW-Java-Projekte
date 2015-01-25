package patterns.factory.gui;

import java.util.LinkedList;
import java.util.List;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.sun.javafx.application.PlatformImpl;

public class FactoryFX implements ComponentFactory {
	
	public FactoryFX(){
		new JFXPanel(); // this will prepare JavaFX toolkit and environment
		// Reference:
		// http://stackoverflow.com/questions/11273773/javafx-2-1-toolkit-not-initialized
	}
	
	
	public Button createButton(final String label, final ActionListener listener) {
		final ButtonFX b = new ButtonFX(label);
		
		b.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				listener.actionPerformed(b);
			}
		});
		
		return b;
	}

	public Label createLabel(final String label) {
		return new LabelFX(label);
	}

	public Field createField(int width, boolean enabled) {
		return new FieldFX(width, enabled);
	}

	public Frame createFrame(final String title) {
		return new FrameFX(title);
	}

	static class ButtonFX extends javafx.scene.control.Button implements
			ComponentFactory.Button {

		ButtonFX(String label) {
			super(label);
		}
		
	}

	class LabelFX extends javafx.scene.control.Label implements
			ComponentFactory.Label {

		LabelFX(String label) {
			super(label);
		}
	}

	static class FieldFX extends TextField implements
			ComponentFactory.Field {

		public FieldFX(int width, boolean enabled) {
			setPrefColumnCount(width);
			setEditable(enabled);
		}
	}

	public static class FrameFX implements ComponentFactory.Frame {

		private String title;
		private List<Component> components = new LinkedList<>();
		private int w, h;
		
		public FrameFX(String title) {
			this.title = title;
		}
		
		public void init() {
			System.out.println("init called");
		}

		public void add(Component c) {
			components.add(c);
		}

		public void setGrid(int h, int w) {
			this.w = w;
			this.h = h;
		}

		public @Override
		void setVisible(boolean visible) {
			
			PlatformImpl.startup(new Runnable() {
				@Override
				public void run() {
					// No need to do anything here
				}
			});
		      
		      
			Platform.runLater(new Runnable() {
		        @Override
		        public void run() {
		        	final Stage s = new Stage();
		        	s.setTitle(title);
					Group root = new javafx.scene.Group();
					Scene scene = new Scene(root);
					
					GridPane gridpane = new GridPane();
					gridpane.setPadding(new Insets(5));
					gridpane.setHgap(5);
					gridpane.setVgap(5);
					
					int i = 0;
					for(Component c : components) {
						gridpane.add((Node)c, i % w, i / w);
						i++;
						if(i == w * h) break;
					}
					
					root.getChildren().add(gridpane);
					s.setScene(scene);
					
					s.setOnCloseRequest(new EventHandler<WindowEvent>() {
					      public void handle(WindowEvent event) {
					    	  Platform.runLater(new Runnable(){public void run(){Platform.exit();}});
					      }
					});

					s.show();
		        }
		   });
			
		}
	}

	static {
		CurrentFactory.setFactory(new FactoryFX());
	}

}
