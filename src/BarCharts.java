import com.sun.org.glassfish.gmbal.Description;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.HashMap;

public class BarCharts {
    private Stage stage;
    private Scene scene;
    private DbConnect connection;
    String choice, start, end;
    private HashMap<String, Integer> barData = new HashMap<>();

    public BarCharts() {}

    public BarCharts(Stage stage, Scene scene, String choice, String start, DbConnect connection) {
        this.stage = stage;
        this.scene = scene;
        this.choice = choice;
        this.start = start;
        this.end = end;
        this.connection = connection;
        System.out.println("Choice: " + choice);
        barData = connection.getSecondaryTypes(start, choice.toUpperCase()); // update later
        init();
    }

    public void init() {
        BorderPane barScreen = new BorderPane();
        barScreen.setMinHeight(820);
        barScreen.setMinWidth(1000);
        barScreen.getStylesheets().add("/style.css");

        Scene barScene = new Scene(barScreen);
        Button backButton = new Button("Back");

        HBox container = new HBox();
        container.setPadding(new Insets(5, 2, 2, 5));
        container.getChildren().add(backButton);
        barScreen.setTop(container);

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<Number, String> bc = new BarChart<Number, String>(yAxis, xAxis);
        bc.setTitle(choice + " " + start);
        xAxis.setLabel("Description");
        yAxis.setLabel("Value");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName(choice);
        for (HashMap.Entry<String, Integer> entry : barData.entrySet()) {
            series1.getData().add(new XYChart.Data<Integer, String>(entry.getValue(), entry.getKey()));

        }
//
        barScreen.setCenter(bc);
        bc.getData().addAll(series1);

        stage.setScene(barScene);

        backButton.setOnAction(event -> {
            stage.setScene(scene);
            stage.show();
        });

        barScreen.setTop(container);
    }
}
