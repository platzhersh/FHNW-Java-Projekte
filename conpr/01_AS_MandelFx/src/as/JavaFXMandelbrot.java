package as;

import static as.Mandelbrot.IMAGE_LENGTH;

import java.util.function.Function;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.WritableDoubleValue;
import javafx.beans.value.WritableStringValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class JavaFXMandelbrot extends Application {
	private static Plane INITIAL_PLANE = new Plane(new Complex(0, 0), 4);

	private final Property<Plane> plane = new SimpleObjectProperty<Plane>(new Plane(new Complex(0, 0), 0)); // Dummy start value

	private final StringProperty timeProp = new SimpleStringProperty();
	private final DoubleProperty progressProp = new SimpleDoubleProperty();
	private final BooleanProperty cancelProp = new SimpleBooleanProperty();

	private <A, B> Property<B> unimap(Property<A> aProp, Function<A, B> aToB) {
		Property<B> bProp = new SimpleObjectProperty<B>(aToB.apply(aProp.getValue()));
		aProp.addListener((o, oldVal, newVal) -> {
			bProp.setValue(aToB.apply(newVal));
		});

		return bProp;
	}

	@Override
	public void start(Stage stage) {
		BorderPane layout = new BorderPane();
		Scene scene = new Scene(layout, Color.BLACK);
		stage.setScene(scene);

		GridPane grid = createControlUI();
		layout.setRight(grid);

		ImageView imageView = new ImageView();
		imageView.setFitHeight(IMAGE_LENGTH);
		imageView.setFitWidth(IMAGE_LENGTH);
		imageView.setOnMouseClicked(e -> plane.setValue(mouseToPlane(e, plane.getValue())));

		plane.addListener((o, oldPlane, newPlane) -> {
			Image image = drawMandel(imageView, newPlane, progressProp, cancelProp, timeProp);
			imageView.setImage(image);
		});

		layout.setCenter(imageView);

		plane.setValue(INITIAL_PLANE);

		stage.setTitle("Mandelbrot Set");
		stage.show();

		System.out.println(Thread.getAllStackTraces().keySet());
	}

	private GridPane createControlUI() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Control");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		grid.add(new Label("re:"), 0, 1);
		TextField re = new TextField();
		re.textProperty().bind(unimap(plane, p -> "" + p.center.r));
		re.setEditable(false);
		grid.add(re, 1, 1);

		grid.add(new Label("im:"), 0, 2);
		TextField im = new TextField();
		im.textProperty().bind(unimap(plane, p -> "" + p.center.i));
		im.setEditable(false);
		grid.add(im, 1, 2);

		grid.add(new Label("Zoom:"), 0, 3);
		TextField zoom = new TextField();
		zoom.textProperty().bind(unimap(plane, p -> "" + p.length));
		zoom.setEditable(false);
		grid.add(zoom, 1, 3);

		grid.add(new Label("Progress:"), 0, 4);
		ProgressBar pb = new ProgressBar();
		pb.setMaxWidth(Double.MAX_VALUE);
		pb.progressProperty().bind(progressProp);
		grid.add(pb, 1, 4);

		grid.add(new Label("Time:"), 0, 5);
		TextField time = new TextField();
		time.textProperty().bind(timeProp);
		time.setEditable(false);
		grid.add(time, 1, 5);

		Button stopBtn = new Button("Stop");
		cancelProp.bind(stopBtn.pressedProperty());
		stopBtn.disableProperty().bind(progressProp.lessThan(1.0).not());
		grid.add(stopBtn, 0, 6);

		Button resetBtn = new Button("Reset");
		resetBtn.setOnAction(e -> plane.setValue(INITIAL_PLANE));
		grid.add(resetBtn, 1, 6);
		return grid;
	}

	private Plane mouseToPlane(MouseEvent e, Plane p) {
		Complex old = p.center;

		double step = p.length / IMAGE_LENGTH;
		double reMin = old.r - p.length / 2;
		double imMax = old.i + p.length / 2;

		double re = reMin + e.getX() * step;
		double im = imMax - e.getY() * step;
		return new Plane(new Complex(re, im), p.length / 2);
	}

	private Image drawMandel(
			ImageView imageView, 
			Plane plane,
			WritableDoubleValue progress, 
			ObservableBooleanValue cancelled,
			WritableStringValue millis) {
		
		millis.set("...");
		WritableImage image = new WritableImage(IMAGE_LENGTH, IMAGE_LENGTH);
		PixelPainter painter = new PixelWriterPixelPainter(
				image.getPixelWriter(), IMAGE_LENGTH * IMAGE_LENGTH, progress);

		CancelSupport cancelSupport = new CancelSupport();
		cancelled.addListener((o, oldVal, newVal) -> cancelSupport.cancel());
		
		{ // <<<<<<<<<<<<<<<<<<<< This block of code should run in a separate Thread >>>>>>>>>>>>>>>>>>>>
			double start = System.currentTimeMillis();
			// Replace the following line with Mandelbrot.computeParallel(...)
			//Mandelbrot.computeSequential(painter, plane, cancelSupport);
			Mandelbrot.computeParallel(painter, plane, cancelSupport);
			double end = System.currentTimeMillis();
			Platform.runLater(() -> millis.set((end - start) + "ms"));
		} // <<<<<<<<<<<<<<<<<<<< This block of code should run in a separate Thread >>>>>>>>>>>>>>>>>>>>
		
		return image;
	}

	public static void main(String[] args) {
		launch(args);
	}
};
