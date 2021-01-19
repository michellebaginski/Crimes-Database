import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;

public class PieClass {
    private Stage stage;
    private Scene scene;
    private DbConnect connection;
    private String startYear;
    private String endYear;
    private HashMap<String, Integer> pieData = new HashMap<>();

    public PieClass() {}

    public PieClass(Stage stage, Scene scene, String startYear, String endYear, DbConnect connection) {
        this.stage = stage;
        this.scene = scene;
        this.startYear = startYear;
        this.endYear = endYear;
        this.connection = connection;
        pieData = connection.getCrimePercentages(startYear, endYear);
        init();
    }

    public void init() {
        BorderPane pieScreen = new BorderPane();
        pieScreen.setMinHeight(820);
        pieScreen.setMinWidth(820);
        pieScreen.getStylesheets().add("/style.css");

        int arson = 0;
        int assault = 0;
        int battery = 0;
        int crimAssault = 0;
        int homicide = 0;
        int narcotics = 0;
        int prostitution = 0;
        int theft = 0;
        int kidnapping = 0;
        int stalking = 0;
        int robbery = 0;
        int motorTheft = 0;
        int weapViolation = 0;
        int crimDamage = 0;
        int other = 0;

        if (pieData.containsKey("ARSON"))                   arson = pieData.get("ARSON");
        if (pieData.containsKey("ASSAULT"))                 assault = pieData.get("ASSAULT");
        if (pieData.containsKey("BATTERY"))                 battery = pieData.get("BATTERY");
        if (pieData.containsKey("CRIM SEXUAL ASSAULT"))     crimAssault = pieData.get("CRIM SEXUAL ASSAULT");
        if (pieData.containsKey("HOMICIDE"))                homicide = pieData.get("HOMICIDE");
        if (pieData.containsKey("NARCOTICS"))               narcotics = pieData.get("NARCOTICS");
        if (pieData.containsKey("PROSTITUTION"))            prostitution = pieData.get("PROSTITUTION");
        if (pieData.containsKey("THEFT"))                   theft = pieData.get("THEFT");
        if (pieData.containsKey("KIDNAPPING"))              kidnapping = pieData.get("KIDNAPPING");
        if (pieData.containsKey("STALKING"))                stalking = pieData.get("STALKING");
        if (pieData.containsKey("ROBBERY"))                 robbery = pieData.get("ROBBERY");
        if (pieData.containsKey("MOTOR VEHICLE THEFT"))     motorTheft = pieData.get("MOTOR VEHICLE THEFT");
        if (pieData.containsKey("WEAPONS VIOLATION"))       weapViolation = pieData.get("THEFT");
        if (pieData.containsKey("CRIMINAL DAMAGE"))         crimDamage = pieData.get("CRIMINAL DAMAGE");
        if (pieData.containsKey("OTHER OFFENSE"))           other = pieData.get("OTHER OFFENSE");

        int size = arson + assault + battery + crimAssault + homicide + narcotics + prostitution + theft + kidnapping +
                stalking + robbery + motorTheft + weapViolation + crimDamage + other;
        DecimalFormat df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.DOWN);

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Battery " + df.format(((float)battery/(float)size) * 100) + "%", battery),
                new PieChart.Data("Arson " + df.format(((float)arson/(float)size) * 100) +"%", arson),
                new PieChart.Data("Assault " + df.format(((float)assault/(float)size) * 100) + "%", assault),
                new PieChart.Data("Crim. Sex. Assault " + df.format(((float)crimAssault/(float)size) * 100) + "%", crimAssault),
                new PieChart.Data("Homicide " + df.format(((float)homicide/(float)size) * 100) + "%", homicide),
                new PieChart.Data("Narcotics " + df.format(((float)narcotics/(float)size) * 100) + "%", narcotics),
                new PieChart.Data("Prostitution " + df.format(((float)prostitution/(float)size) * 100) + "%", prostitution),
                new PieChart.Data("Theft " + df.format(((float)theft/(float)size) * 100) + "%", theft),
                new PieChart.Data("Kidnapping " + df.format(((float)kidnapping/(float)size) * 100) + "%", kidnapping),
                new PieChart.Data("Stalking " + df.format(((float)stalking/(float)size) * 100) + "%", stalking),
                new PieChart.Data("Robbery " + df.format(((float)robbery/(float)size) * 100) + "%", robbery),
                new PieChart.Data("Vehicle Theft " + df.format(((float)motorTheft/(float)size) * 100) + "%", motorTheft),
                new PieChart.Data("Weapons Violation " + df.format(((float)weapViolation/(float)size) * 100) + "%", weapViolation),
                new PieChart.Data("Criminal Damage " + df.format(((float)crimDamage/(float)size) * 100) + "%", crimDamage),
                new PieChart.Data("Other Offense " + df.format(((float)other/(float)size) * 100) + "%", other)
                );

        PieChart pieChart = new PieChart(pieData);
        pieChart.setPrefHeight(700);
        pieChart.setPrefWidth(700);
        HBox pieContainer = new HBox(pieChart);
        pieScreen.setCenter(pieContainer);
        pieContainer.setTranslateY(-110);
        pieContainer.setTranslateX(80);
        Scene pieScene = new Scene(pieScreen);

        HBox container = new HBox();
        container.setPadding(new Insets(5, 2, 2, 5));

        String titleStr = "";
        if (startYear == endYear) {
            titleStr = startYear + " Crime Percentages";
        }
        else {
            titleStr = startYear + " - " + endYear + " Crime Percentages";
        }
        Label title = new Label(titleStr);

        Button backButton = new Button("Back");
        container.getChildren().addAll(backButton, title);
        title.setTranslateY(15);
        title.setTranslateX(273);
        title.setId("pieTitle");

        backButton.setOnAction(event -> {
            stage.setScene(scene);
            stage.show();
        });

        pieScreen.setTop(container);
        stage.setScene(pieScene);
    }

}
