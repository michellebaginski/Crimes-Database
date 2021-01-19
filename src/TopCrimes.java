import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class TopCrimes {
    private Stage stage;
    private Scene scene;
    private DbConnect connection;
    private String choice;
    private HashMap<String, Integer> data = new HashMap<>();

    public TopCrimes() {
    }

    public TopCrimes(Stage stage, Scene scene, String choice, DbConnect connection) {
        this.stage = stage;
        this.scene = scene;
        this.choice = choice;
        this.connection = connection;
        data = connection.getCrimePercentages(choice, choice);
        init();
    }

    public void init() {
        BorderPane topScreen = new BorderPane();
        topScreen.setMinHeight(820);
        topScreen.setMinWidth(820);
        topScreen.getStylesheets().add("/style.css");

        Scene topScene = new Scene(topScreen);
        Button backButton = new Button("Back");

        HBox container = new HBox();
        container.setPadding(new Insets(5, 2, 2, 5));
        container.getChildren().add(backButton);
        topScreen.setTop(container);
        stage.setScene(topScene);

        Map<String, Integer> hm1 = sortByValue(data);

        // print the sorted hashmap
        for (Map.Entry<String, Integer> en : hm1.entrySet()) {
            System.out.println("Key = " + en.getKey() +
                    ", Value = " + en.getValue());
        }

        TableView<RowData> table = new TableView<>();
        //Creating columns
        TableColumn crime = new TableColumn("Crime Type");
        TableColumn count = new TableColumn("Occurrences");
        crime.setMinWidth(300);
        count.setMinWidth(197);

        crime.setCellValueFactory(new PropertyValueFactory<>("name"));
        count.setCellValueFactory(new PropertyValueFactory<>("surname"));

        // populate table with data
        RowData val1 = new RowData("1.      " + (String) hm1.keySet().toArray()[0], hm1.get(hm1.keySet().toArray()[0]), "1");
        RowData val2 = new RowData("2.      " + (String) hm1.keySet().toArray()[1], hm1.get(hm1.keySet().toArray()[1]), "2");
        RowData val3 = new RowData("3.      " + (String) hm1.keySet().toArray()[2], hm1.get(hm1.keySet().toArray()[2]), "3");
        RowData val4 = new RowData("4.      " + (String) hm1.keySet().toArray()[3], hm1.get(hm1.keySet().toArray()[3]), "4");
        RowData val5 = new RowData("5.      " +(String) hm1.keySet().toArray()[4], hm1.get(hm1.keySet().toArray()[4]), "5");
        RowData val6 = new RowData("6.      " +(String) hm1.keySet().toArray()[5], hm1.get(hm1.keySet().toArray()[5]), "6");
        RowData val7 = new RowData("7.      " +(String) hm1.keySet().toArray()[6], hm1.get(hm1.keySet().toArray()[6]), "7");
        RowData val8 = new RowData("8.      " +(String) hm1.keySet().toArray()[7], hm1.get(hm1.keySet().toArray()[7]), "8");
        RowData val9 = new RowData("9.      " +(String) hm1.keySet().toArray()[8], hm1.get(hm1.keySet().toArray()[8]), "9");
        RowData val10 = new RowData("10.      " +(String) hm1.keySet().toArray()[9], hm1.get(hm1.keySet().toArray()[9]), "10");

        // add data to table
        ObservableList<RowData> list = FXCollections.observableArrayList();
        list.addAll(val1, val2, val3, val4, val5, val6, val7, val8, val9, val10);

        table.setItems(list);
        table.getColumns().addAll(crime, count);
        table.setMaxSize(500, 600);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 50, 50, 60));
        vbox.getChildren().addAll(table);
        topScreen.setCenter(vbox);
        vbox.setAlignment(Pos.CENTER);


        backButton.setOnAction(event -> {
            stage.setScene(scene);
            stage.show();
        });
    }

    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
        // create a list of the map
        List<Map.Entry<String, Integer>> list = new LinkedList<>(hm.entrySet());

        // sort
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put sorted data into a hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


    protected class RowData {
        private String crimeName;
        private String crimeCount;

        public RowData(String name, int count, String rank) {
            crimeName = name;
            crimeCount = Integer.toString(count);
        }

        public String getName() {
            return crimeName;
        }

        public String getSurname() {
            return crimeCount;
        }

    }
}
