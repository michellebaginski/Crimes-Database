import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;

public class LineCharts {
    private Stage stage;
    private Scene scene;
    private DbConnect connection;
    private String choice;
    private ArrayList<Integer> yearList;
    private ArrayList<Integer> yearsFinal = new ArrayList<>();
    private int count;
    private HashMap<String, Integer> lineData = new HashMap<>();

    public LineCharts() {}

    public LineCharts(Stage stage, Scene scene, String choice, ArrayList<Integer> yearList, DbConnect connection) {
        this.stage = stage;
        this.scene = scene;
        this.choice = choice;
        this.connection = connection;
        this.yearList = yearList;
        countNumComparisons();
        init();
    }

    public void init() {
        BorderPane lineScreen = new BorderPane();
        lineScreen.setMinHeight(820);
        lineScreen.setMinWidth(820);
        lineScreen.getStylesheets().add("/style.css");

        Scene lineScene = new Scene(lineScreen);
        Button backButton = new Button("Back");

        HBox container = new HBox();
        container.setPadding(new Insets(5, 2, 2, 5));
        container.getChildren().add(backButton);
        lineScreen.setTop(container);

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        final LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxis,yAxis);
        lineChart.setTitle(choice);

        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        XYChart.Series series4 = new XYChart.Series();

        if (count >= 1) {
            if (yearsFinal.get(0) <= 9) {
                series1.setName("200" + (yearsFinal.get(0)).toString());
            }
            else {
                series1.setName("20" + (yearsFinal.get(0)).toString());
            }
            lineData = connection.getMonthlyData(choice.toUpperCase(), convertYear(yearsFinal.get(0)));
            for (HashMap.Entry<String, Integer> entry : lineData.entrySet()) {
                series1.getData().add(new XYChart.Data<String, Integer>(convertYrToStr(entry.getKey()), entry.getValue()));
            }
            lineChart.getData().add(series1);
        }
        if (count >= 2) {
            if (yearsFinal.get(1) <= 9) {
                series2.setName("200" + (yearsFinal.get(1)).toString());
            }
            else {
                series2.setName("20" + (yearsFinal.get(1)).toString());
            }
            lineData = connection.getMonthlyData(choice.toUpperCase(), convertYear(yearsFinal.get(1)));
            for (HashMap.Entry<String, Integer> entry : lineData.entrySet()) {
                series2.getData().add(new XYChart.Data<String, Integer>(convertYrToStr(entry.getKey()), entry.getValue()));
            }
            lineChart.getData().add(series2);
        }
        if (count >= 3) {
            if (yearsFinal.get(2) <= 9) {
                series3.setName("200" + (yearsFinal.get(2)).toString());
            }
            else {
                series3.setName("20" + (yearsFinal.get(2)).toString());
            }
            lineData = connection.getMonthlyData(choice.toUpperCase(), convertYear(yearsFinal.get(2)));
            for (HashMap.Entry<String, Integer> entry : lineData.entrySet()) {
                series3.getData().add(new XYChart.Data<String, Integer>(convertYrToStr(entry.getKey()), entry.getValue()));
            }
            lineChart.getData().add(series3);
        }
        if (count == 4) {
            if (yearsFinal.get(3) <= 9) {
                series4.setName("200" + (yearsFinal.get(3)).toString());
            }
            else {
                series4.setName("20" + (yearsFinal.get(3)).toString());
            }
            lineData = connection.getMonthlyData(choice.toUpperCase(), convertYear(yearsFinal.get(3)));
            for (HashMap.Entry<String, Integer> entry : lineData.entrySet()) {
                series4.getData().add(new XYChart.Data<String, Integer>(convertYrToStr(entry.getKey()), entry.getValue()));
            }
            lineChart.getData().add(series4);
        }

        lineScreen.setCenter(lineChart);
        stage.setScene(lineScene);

        backButton.setOnAction(event -> {
            stage.setScene(scene);
            stage.show();
        });

        lineScreen.setTop(container);
    }

    // counts the number of comparisons the user selected and makes a new array with all the years
    public void countNumComparisons() {
        count = 0;

        for (int i=0; i < yearList.size(); i++) {
            if (yearList.get(i) != -1) {
                count++;
                yearsFinal.add(yearList.get(i));
            }
        }
    }

    public String convertYear(int numeral) {
        String year = "";
        if (numeral >= 1 && numeral <= 9) {
            year = "200" + numeral;
        }
        else {
            year = "20" + numeral;
        }
        return year;
    }

    // converts the key numeral to the string of its corresponding month
    public String convertYrToStr(String year) {
        String yearStr = "";

        switch (year) {
            case "0":
                yearStr = "Jan";
                break;
            case "11":
                yearStr = "Feb";
                break;
            case "1":
                yearStr = "Mar";
                break;
            case "2":
                yearStr = "Apr";
                break;
            case "3":
                yearStr = "May";
                break;
            case "4":
                yearStr = "June";
                break;
            case "5":
                yearStr = "July";
                break;
            case "6":
                yearStr = "Aug";
                break;
            case "7":
                yearStr = "Sept";
                break;
            case "8":
                yearStr = "Oct";
                break;
            case "9":
                yearStr = "Nov";
                break;
            case "10":
                yearStr = "Dec";
                break;
        }

        return yearStr;
    }


}
