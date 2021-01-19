import com.sun.tools.javac.comp.Check;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class Main extends Application {
    private DbConnect connection;
    private Stage stage;
    private HashMap<String, Integer> pieData = new HashMap<>();
    private BorderPane mainScreen = new BorderPane();
    private BorderPane pieScreen = new BorderPane();
    private Scene scene, pieScene1;
    private GUI gui = new GUI();
    private VBox centerContents = new VBox();

    public void setUp() {
        mainScreen.setMinHeight(820);
        mainScreen.setMaxHeight(820);
        mainScreen.setMinWidth(820);
        mainScreen.getStylesheets().add("/style.css");
        mainScreen.setCenter(centerContents);

        // title for the application
        HBox titleContainer = new HBox();
        Label title = new Label();
        title.setText("Chicago Crimes Database (2001-2017)");
        title.setId("appTitle");
        title.setTranslateX(25);
        titleContainer.getChildren().add(title);
        titleContainer.setPadding(new Insets(20, 50, 25, 225));
        mainScreen.setTop(titleContainer);

        centerContents.setAlignment(Pos.TOP_CENTER);
        centerContents.setSpacing(45);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        connection = new DbConnect();
        primaryStage.setTitle("Crimes Database 2001-2017");
        stage = primaryStage;

        setUp();
        setUpImage();
        setUpCrimePercentages();
        setUpBarGraph();
        setUpLineChart();
        setUpRank();
        setUpLocation();

        scene = new Scene(mainScreen);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setUpImage() {
        Image chicagoImg = new Image("/skyline.png");
        ImageView imageView = new ImageView(chicagoImg);
        imageView.setPreserveRatio(true);
        centerContents.getChildren().add(imageView);
        //imageView.setFitHeight(100);
        //imageView.setFitWidth(650);
        imageView.setTranslateX(-10);
        imageView.setTranslateY(50);
    }

    // sets up the first function: pie charts for crime percentages
    private void setUpCrimePercentages() {
        // holds the contents of the center page
        HBox contents = new HBox();
        contents.setPadding(new Insets(25, 0, 10, 0));
        contents.setSpacing(10);

        Label IUCRLabel = new Label("View crime percentages based on type of crime.");
        Label fromLabel = new Label("From");
        Label toLabel = new Label("to");

        // drop down menus for "to" and "from" years
        ChoiceBox<String> IUCRMenu = new ChoiceBox<>();
        ChoiceBox<String> IUCRMenu2 = new ChoiceBox<>();
        IUCRMenu.setValue("2001");
        IUCRMenu2.setValue("2001");
        IUCRMenu.getItems().addAll("2001", "2002", "2003", "2004", "2005", "2006",
                "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017");
        IUCRMenu2.getItems().addAll("2001", "2002", "2003", "2004", "2005", "2006",
                "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017");

        Button doneButton = new Button("Done");
        contents.getChildren().addAll(IUCRLabel, fromLabel, IUCRMenu, toLabel, IUCRMenu2, doneButton);
        contents.setAlignment(Pos.TOP_CENTER);
        centerContents.getChildren().add(contents);

        IUCRLabel.setTranslateX(150);
        fromLabel.setTranslateY(25);
        fromLabel.setTranslateX(-120);
        IUCRMenu.setTranslateY(20);
        IUCRMenu.setTranslateX(-120);
        IUCRMenu2.setTranslateY(20);
        IUCRMenu2.setTranslateX(-120);
        toLabel.setTranslateY(25);
        toLabel.setTranslateX(-120);
        doneButton.setTranslateY(20);
        doneButton.setTranslateX(-120);

        doneButton.setOnAction(event -> {
            String choice1 = IUCRMenu.getValue();
            String choice2 = IUCRMenu2.getValue();
            System.out.println(choice1 + " " + choice2);
            new PieClass(stage, scene, choice1, choice2, connection);
        });

    }

    // sets up the second function: bar graphs for crime categorizations (subcategory of the primary description)
    public void setUpBarGraph() {
        HBox contents = new HBox();
        contents.setPadding(new Insets(10, 0, 0, 0));
        contents.setSpacing(10);

        Label barLabel = new Label("View the categorical breakdowns of types of crime.");
        Label fromLabel = new Label("From ");
        Label toLabel = new Label("to ");
        Label errorLabel = new Label("You can only select up four years at once.");
        errorLabel.setVisible(false);
        errorLabel.setId("errorLabel");
        Button doneButton = new Button("Done");

        ChoiceBox<String> crimeType = new ChoiceBox<>();
        crimeType.getItems().addAll("Arson", "Assault", "Battery", "Criminal Damage", "Criminal Trespass", "Crim Sexual Assault", "Deceptive Practice", "Gambling", "Homicide", "Interference with Public Officer", "Intimidation", "Kidnapping",
                "Liquor Law Violation", "Motor Vehicle Theft", "Narcotics", "Obscenity", "Other Offense", "Offense Involving Children", "Prostitution",
                "Public Peace Violation", "Robbery", "Sex Offense", "Stalking", "Theft", "Weapons Violation");
        crimeType.setValue("Arson");

        ChoiceBox<String> fromMenu = new ChoiceBox<>();
        fromMenu.getItems().addAll("2001", "2002", "2003", "2004", "2005", "2006",
                "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017");
        fromMenu.setValue("2001");

        ChoiceBox<String> toMenu = new ChoiceBox<>();
        toMenu.getItems().addAll("2001", "2002", "2003", "2004", "2005", "2006",
                "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017");
        toMenu.setValue("2001");

        contents.setSpacing(5);


        HBox errorBox = new HBox();
        errorBox.getChildren().add(errorLabel);

        errorLabel.setTranslateY(100);
        contents.setAlignment(Pos.TOP_CENTER);
        barLabel.setTranslateX(185);
        crimeType.setTranslateY(20);
        crimeType.setTranslateX(-120);
        fromLabel.setTranslateY(25);
        fromLabel.setTranslateX(-120);
        toLabel.setTranslateY(25);
        toLabel.setTranslateX(-120);
        fromMenu.setTranslateY(20);
        fromMenu.setTranslateX(-120);
        toMenu.setTranslateY(20);
        toMenu.setTranslateX(-120);
        errorLabel.setTranslateY(-110);
        doneButton.setTranslateY(20);
        doneButton.setTranslateX(-120);


        contents.getChildren().addAll(barLabel, crimeType, fromMenu, doneButton);
        centerContents.getChildren().addAll(contents); //, checkBoxes1, checkBoxes2, errorLabel);

        doneButton.setOnAction(event -> {
            String crime = crimeType.getValue();

            int countChecked = 0;
//            for (int i=0; i<boxList.size(); i++) {
//                CheckBox box = boxList.get(i);
//                if (box.isSelected()) {
//                    countChecked++;
//                }
//            }
            if (countChecked > 4) {
                errorLabel.setVisible(true);
            }
            else {
                errorLabel.setVisible(false);
                new BarCharts(stage, scene, crime, fromMenu.getValue(), connection);
            }
            //System.out.println("count checked: " + countChecked);
        });
    }

    public void setUpLineChart() {
        HBox lineContents = new HBox();
        HBox checkBoxes = new HBox();
        HBox checkBoxes2 = new HBox();
        checkBoxes.setPadding(new Insets(0, 5, 0, 5));
        checkBoxes2.setPadding(new Insets(0, 5, 10, 5));
        lineContents.setPadding(new Insets(10, 0, 0, 0));

        Label errorLabel = new Label("You can only select up to four years at once.");
        Label loading = new Label("Please wait...calculating query. May take up to 1 minute");
        Label label = new Label("View monthly breakdown of crime throughout the year.");
        Label label2 = new Label("Select a type of crime and check up to four years for comparison.");
        Button doneButton = new Button("Done");
        errorLabel.setVisible(false);
        errorLabel.setId("errorLabel");
        loading.setVisible(false);

        ChoiceBox<String> crimeType = new ChoiceBox<>();
        crimeType.getItems().addAll("Arson", "Assault", "Battery", "Criminal Damage", "Criminal Trespass", "Crim Sexual Assault", "Deceptive Practice", "Gambling", "Homicide", "Interference with Public Officer", "Intimidation", "Kidnapping",
                "Liquor Law Violation", "Motor Vehicle Theft", "Narcotics", "Obscenity", "Other Offense", "Offense Involving Children", "Prostitution",
                "Public Peace Violation", "Robbery", "Sex Offense", "Stalking", "Theft", "Weapons Violation");
        crimeType.setValue("Arson");

        List<CheckBox> boxList = new ArrayList<>();
        CheckBox cb2001 = gui.makeCheckBox("2001", 0, 15, 0, 1);
        CheckBox cb2002 = gui.makeCheckBox("2002", 0, 15, 0, 1);
        CheckBox cb2003 = gui.makeCheckBox("2003", 0, 15, 0, 1);
        CheckBox cb2004 = gui.makeCheckBox("2004", 0, 15, 0, 1);
        CheckBox cb2005 = gui.makeCheckBox("2005", 0, 15, 0, 1);
        CheckBox cb2006 = gui.makeCheckBox("2006", 0, 15, 0, 1);
        CheckBox cb2007 = gui.makeCheckBox("2007", 0, 15, 0, 1);
        CheckBox cb2008 = gui.makeCheckBox("2008", 0, 15, 0, 1);
        CheckBox cb2009 = gui.makeCheckBox("2009", 0, 15, 0, 1);
        CheckBox cb2010 = gui.makeCheckBox("2010", 0, 15, 0, 1);
        CheckBox cb2011 = gui.makeCheckBox("2011", 0, 15, 0, 1);
        CheckBox cb2012 = gui.makeCheckBox("2012", 0, 15, 0, 1);
        CheckBox cb2013 = gui.makeCheckBox("2013", 0, 15, 0, 1);
        CheckBox cb2014 = gui.makeCheckBox("2014", 0, 15, 0, 1);
        CheckBox cb2015 = gui.makeCheckBox("2015", 0, 15, 0, 1);
        CheckBox cb2016 = gui.makeCheckBox("2016", 0, 15, 0, 1);
        CheckBox cb2017 = gui.makeCheckBox("2017", 0, 15, 0, 1);

        boxList.add(cb2001);    boxList.add(cb2002);    boxList.add(cb2003);    boxList.add(cb2004);
        boxList.add(cb2005);    boxList.add(cb2006);    boxList.add(cb2007);    boxList.add(cb2008);
        boxList.add(cb2009);    boxList.add(cb2010);    boxList.add(cb2011);    boxList.add(cb2012);
        boxList.add(cb2013);    boxList.add(cb2014);    boxList.add(cb2015);    boxList.add(cb2016); boxList.add(cb2017);

        checkBoxes.getChildren().addAll(cb2001, cb2002, cb2003, cb2004, cb2005, cb2006, cb2007, cb2008, cb2009);
        checkBoxes2.getChildren().addAll(cb2010, cb2011, cb2012, cb2013, cb2014, cb2015, cb2016, cb2017, doneButton);

        errorLabel.setTranslateY(-70);
        label.setTranslateX(300);
        label2.setTranslateY(20);
        label2.setTranslateX(-30);
        checkBoxes.setTranslateY(10);
        cb2010.setTranslateX(-14);
        cb2011.setTranslateX(-15);
        cb2012.setTranslateX(-11);
        cb2013.setTranslateX(-9);
        cb2015.setTranslateX(-5);
        cb2016.setTranslateX(-2);
        cb2014.setTranslateX(-7);
        checkBoxes2.setTranslateY(-18);
        checkBoxes2.setTranslateX(-6);
        crimeType.setTranslateX(-310);
        crimeType.setTranslateY(40);
        doneButton.setTranslateY(-3);

        lineContents.getChildren().addAll(label, label2, crimeType);
        lineContents.setAlignment(Pos.TOP_CENTER);
        checkBoxes.setAlignment(Pos.TOP_CENTER);
        checkBoxes2.setAlignment(Pos.TOP_CENTER);
        centerContents.getChildren().addAll(lineContents, checkBoxes, checkBoxes2, errorLabel, loading);

        doneButton.setOnAction(event -> {
            ArrayList<Integer> yearList = new ArrayList<>();
            int countChecked = 0;

            for (int i=0; i<boxList.size(); i++) {
                CheckBox box = boxList.get(i);
                if (box.isSelected()) {
                    countChecked++;
                    yearList.add(i, i+1);
                }
                else {
                    yearList.add(i, -1);
                }
            }
            if (countChecked > 4) {
                errorLabel.setVisible(true);
            }
            else {
                errorLabel.setVisible(false);
                new LineCharts(stage, scene, crimeType.getValue(), yearList, connection);
            }

        });
    }

    public void setUpRank() {
        HBox rankContents = new HBox();
        rankContents.setPadding(new Insets(10, 0, 0, 0));

        Button doneButton = new Button("Done");
        Label instructions = new Label("View the top ten crimes for a given year.");
        rankContents.getChildren().add(instructions);

        ChoiceBox<String> menu = new ChoiceBox<>();
        menu.getItems().addAll("2001", "2002", "2003", "2004", "2005", "2006",
                "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017");
        menu.setValue("2001");

        rankContents.setTranslateX(325);
        rankContents.setTranslateY(-165);
        menu.setTranslateY(-205);
        menu.setTranslateX(-30);
        doneButton.setTranslateY(-277);
        doneButton.setTranslateX(40);

        centerContents.getChildren().addAll(rankContents, menu, doneButton);

        doneButton.setOnAction(event -> {

            new TopCrimes(stage, scene, menu.getValue(), connection);
        });


    }

    public void setUpLocation() {
        HBox rankContents = new HBox();
        rankContents.setPadding(new Insets(10, 0, 0, 0));

        Button doneButton = new Button("Done");
        Label instructions = new Label("View the locations for crimes per year with a scatter plot.");
        rankContents.getChildren().add(instructions);

        ChoiceBox<String> menu = new ChoiceBox<>();
        menu.getItems().addAll("2001", "2002", "2003", "2004", "2005", "2006",
                "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017");
        menu.setValue("2001");

        rankContents.setTranslateY(-290);
        rankContents.setTranslateX(330);
        instructions.setTranslateX(-47);
        menu.setTranslateY(-330);
        menu.setTranslateX(-30);
        doneButton.setTranslateY(-402);
        doneButton.setTranslateX(41);
        centerContents.getChildren().addAll(rankContents, menu, doneButton);
        centerContents.setTranslateY(90);

        doneButton.setOnAction(event -> {

            new Location(stage, scene, menu.getValue(), connection);
        });
    }
}
