import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;

public class Location {
    private Stage stage;
    private Scene scene;
    private DbConnect connection;
    private String choice;
    private HashMap<String, Integer> data = new HashMap<>();

    public Location() {}

    public Location(Stage stage, Scene scene, String choice, DbConnect connection) {
        this.stage = stage;
        this.scene = scene;
        this.choice = choice;
        this.connection = connection;
        init();
    }

    public void init() {
        BorderPane locScreen = new BorderPane();
        locScreen.setMinHeight(820);
        locScreen.setMinWidth(820);
        locScreen.getStylesheets().add("/style.css");

        Scene topScene = new Scene(locScreen);
        Button backButton = new Button("Back");

        final NumberAxis yAxis = new NumberAxis(0, 10000, 250);
        final NumberAxis xAxis = new NumberAxis(0, 12, 1);
        xAxis.tickLengthProperty().setValue(2);

        final ScatterChart<Number,Number> sc = new ScatterChart<>(xAxis,yAxis);
        xAxis.setLabel("Month");
        yAxis.setLabel("Number of Crimes");
        sc.setTitle("Crime Location");

        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        XYChart.Series series4 = new XYChart.Series();
        XYChart.Series series5 = new XYChart.Series();
        XYChart.Series series6 = new XYChart.Series();
        XYChart.Series series7 = new XYChart.Series();
        XYChart.Series series8 = new XYChart.Series();

        connection.getData1(choice);

        series1.setName("Street");
        data = connection.getScatterValues("STREET");
        for (HashMap.Entry<String, Integer> entry : data.entrySet()) {
            series1.getData().add(new XYChart.Data<Integer, Integer>(Integer.parseInt(entry.getKey()), entry.getValue()));
        }

        series2.setName("Residence");
        data = connection.getScatterValues("RESIDENCE");
        for (HashMap.Entry<String, Integer> entry : data.entrySet()) {
            series2.getData().add(new XYChart.Data<Integer, Integer>(Integer.parseInt(entry.getKey()), entry.getValue()));
        }

        series3.setName("Vehicle");
        data = connection.getScatterValues("VEHICLE");
        for (HashMap.Entry<String, Integer> entry : data.entrySet()) {
            series3.getData().add(new XYChart.Data<Integer, Integer>(Integer.parseInt(entry.getKey()), entry.getValue()));
        }

        series4.setName("Bar/Club");
        data = connection.getScatterValues("BAR");
        for (HashMap.Entry<String, Integer> entry : data.entrySet()) {
            series4.getData().add(new XYChart.Data<Integer, Integer>(Integer.parseInt(entry.getKey()), entry.getValue()));
        }

        series5.setName("Train");
        data = connection.getScatterValues("TRAIN");
        for (HashMap.Entry<String, Integer> entry : data.entrySet()) {
            series5.getData().add(new XYChart.Data<Integer, Integer>(Integer.parseInt(entry.getKey()), entry.getValue()));
        }

        series6.setName("School");
        data = connection.getScatterValues("SCHOOL");
        for (HashMap.Entry<String, Integer> entry : data.entrySet()) {
            series6.getData().add(new XYChart.Data<Integer, Integer>(Integer.parseInt(entry.getKey()), entry.getValue()));
        }

        series7.setName("Store");
        data = connection.getScatterValues("STORE");
        for (HashMap.Entry<String, Integer> entry : data.entrySet()) {
            series7.getData().add(new XYChart.Data<Integer, Integer>(Integer.parseInt(entry.getKey()), entry.getValue()));
        }

        series8.setName("Public");
        data = connection.getScatterValues("PUBLIC");
        for (HashMap.Entry<String, Integer> entry : data.entrySet()) {
            series8.getData().add(new XYChart.Data<Integer, Integer>(Integer.parseInt(entry.getKey()), entry.getValue()));
        }

        sc.setPrefSize(500, 400);
        sc.getData().addAll(series1, series2, series3, series4, series5, series6, series7, series8);
        locScreen.setCenter(sc);

        HBox container = new HBox();
        container.setPadding(new Insets(5, 2, 2, 5));
        container.getChildren().add(backButton);
        locScreen.setTop(container);
        stage.setScene(topScene);

        backButton.setOnAction(event -> {
            stage.setScene(scene);
            stage.show();
        });
    }

    public String convertYrToStr(String year) {
        String yearStr = "";

        switch (year) {
            case "01":
                yearStr = "Jan";
                break;
            case "02":
                yearStr = "Feb";
                break;
            case "03":
                yearStr = "Mar";
                break;
            case "04":
                yearStr = "Apr";
                break;
            case "05":
                yearStr = "May";
                break;
            case "06":
                yearStr = "June";
                break;
            case "07":
                yearStr = "July";
                break;
            case "08":
                yearStr = "Aug";
                break;
            case "09":
                yearStr = "Sept";
                break;
            case "10":
                yearStr = "Oct";
                break;
            case "11":
                yearStr = "Nov";
                break;
            case "12":
                yearStr = "Dec";
                break;
        }

        return yearStr;
    }



}
