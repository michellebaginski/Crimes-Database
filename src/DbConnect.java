import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.util.HashMap;

public class DbConnect {
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private GenerateQuery genQuery;
    private HashMap<String, Integer> streetCount = new HashMap<>();
    private HashMap<String, Integer> trainCount = new HashMap<>();
    private HashMap<String, Integer> vehicleCount = new HashMap<>();
    private HashMap<String, Integer> residenceCount = new HashMap<>();
    private HashMap<String, Integer> barCount = new HashMap<>();
    private HashMap<String, Integer> schoolCount = new HashMap<>();
    private HashMap<String, Integer> storeCount = new HashMap<>();
    private HashMap<String, Integer> publicCount = new HashMap<>();


    public DbConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // create the connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CrimesDatabase", "root", "A9w82sbd");

            // create the statement
            st = con.createStatement();

        }
        catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        genQuery = new GenerateQuery();
    }

    public void getData() {
        try {
            String query = "SELECT * FROM chicago_crimes_2001_to_2004";
            rs = st.executeQuery(query);

            System.out.println("Records from Database");

            int count = 0;
            while (rs.next() && count < 100) {
                String name = rs.getString("IUCR");
                System.out.println(name);
                count++;
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }


    // this will create a hashmap of all the IUCR codes and their percentages
    public HashMap<String, Integer> getCrimePercentages(String start, String end) {
        HashMap<String, Integer> codeCount = new HashMap<>();

        try {
            String query = genQuery.getPieQuery(start, end);
            rs = st.executeQuery(query);

            //int count = 0;
            while (rs.next()) {
                String type = rs.getString(rs.getMetaData().getColumnName(1));

                if (codeCount.containsKey(type)) {
                        codeCount.put(type, codeCount.get(type)+1);
                }
                else {
                    codeCount.putIfAbsent(type, 1);
                }
            }
            int total = 0;
            for (HashMap.Entry<String, Integer> entry : codeCount.entrySet()) {
                System.out.println(entry.getKey()+" : "+entry.getValue());
                total += entry.getValue();
            }
            System.out.println(total);
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return codeCount;

    }

    // maps secondary types and counts their number of occurences
    public HashMap<String, Integer> getSecondaryTypes(String start, String choice) {
        HashMap<String, Integer> codeCount = new HashMap<>();

        try {
            String query = genQuery.getSingleYearBarQuery(start, choice);
            rs = st.executeQuery(query);

            while (rs.next()) {
                String type = rs.getString(rs.getMetaData().getColumnName(1));
                if (codeCount.containsKey(type)) {
                        codeCount.put(type, codeCount.get(type)+1);
                }
                else {
                    codeCount.putIfAbsent(type, 1);
                }
            }

        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return codeCount;

    }

    public HashMap<String, Integer> getMonthlyData(String choice, String year) {
        HashMap<String, Integer> monthlyCount = new HashMap<>();
        System.out.println("choice=" + choice + "  YEAR=" + year);

        try {
            for (int i=0; i<12; i++) {
                String query = genQuery.getLineGraphQuery(choice, i, year);

                rs = st.executeQuery(query);
                if (rs.next()) {
                    monthlyCount.put(Integer.toString(i), rs.getInt(1));
                    System.out.println(i+1 + ":" + rs.getInt(1));
                }
            }

        }

        catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return monthlyCount;

    }

    public void getData1(String year) {
        initializeMaps(streetCount);
        initializeMaps(barCount);
        initializeMaps(schoolCount);
        initializeMaps(residenceCount);
        initializeMaps(vehicleCount);
        initializeMaps(trainCount);
        initializeMaps(storeCount);
        initializeMaps(publicCount);

        int yearInt = Integer.parseInt(year);
        String query = "";

        try {
            if (yearInt >= 2001 && yearInt <= 2004) {
                query = "SELECT * FROM chicago_crimes_2001_to_2004";
            }
            else if (yearInt >= 2005 && yearInt <= 2007) {
                query = "SELECT * FROM chicago_crimes_2005_to_2007";
            }
            else if (yearInt >= 2008 && yearInt <= 2011) {
                query = "SELECT * FROM chicago_crimes_2008_to_2011";
            }
            else if (yearInt >= 2012 && yearInt <= 2017) {
                query = "SELECT * FROM chicago_crimes_2012_to_2017";
            }
            rs = st.executeQuery(query);

            System.out.println("Records from Database");

            int count = 0;
            while (rs.next() ) {
                String description = rs.getString("Location Description");
                String month = rs.getString("Date");
                month = month.substring(0,2);

                if (description.equals("STREET") || description.equals("SIDEWALK") || description.equals("ALLEY")) {
                        streetCount.put(month, (streetCount.get(month)+1));
                }
                else if (description.equals("CTA TRAIN") || description.equals("CTA PLATFORM") || description.equals("RAILROAD PROPERTY")) {
                        trainCount.put(month, (trainCount.get(month)+1));
                }
                else if (description.equals("TAXICAB") || description.equals("TRUCK") || description.equals("TRAILER") || description.equals("TAXI CAB")
                        || description.equals("VEHICLE NON-COMMERCIAL") || description.equals("VEHICLE-COMMERCIAL") || description.equals("CTA BUS")
                        || description.equals("OTHER COMMERCIAL TRANSPORTATION") || description.equals("HIGHWAY") || description.equals("AUTO") || description.equals("DELIVERY TRUCK")) {
                        vehicleCount.put(month, (vehicleCount.get(month)+1));
                }
                else if (description.equals("RESIDENCE") || description.equals("APARTMENT") || description.equals("HOUSE") || description.equals("HOTEL") || description.equals("MOTEL")
                        || description.equals("HOTEL/MOTEL") || description.equals("RESIDENCE PORCH/HALLWAY") || description.equals("RESIDENCE-GARAGE")
                        || description.equals("RESIDENTIAL YARD (FRONT/BACK)") || description.equals("YARD") || description.equals("BASEMENT") || description.equals("LAUNDRY ROOM")
                        || description.equals("DRIVEWAY - RESIDENTIAL")) {
                        residenceCount.put(month, (residenceCount.get(month)+1));
                }
                else if (description.equals("CLUB") || description.equals("BAR OR TAVERN") || description.equals("TAVERN") || description.equals("TAVERN/LIQUOR STORE")) {
                        barCount.put(month, (barCount.get(month)+1));
                }
                else if (description.equals("SCHOOL YARD") || description.equals("SCHOOL, PRIVATE, BUILDING") || description.equals("SCHOOL, PUBLIC, BUILDING")
                        || description.equals("SCHOOL, PRIVATE, GROUNDS") || description.equals("SCHOOL, PUBLIC, GROUNDS") || description.equals("PUBLIC HIGH SCHOOL")
                        || description.equals("PUBLIC GRAMMAR SCHOOL") || description.equals("COLLEGE/UNIVERSITY GROUNDS") || description.equals("COLLEGE/UNIVERSITY RESIDENCE HALL")) {
                        schoolCount.put(month, (schoolCount.get(month)+1));
                }
                else if (description.equals("RETAIL STORE") || description.equals("GROCERY FOOD STORE") || description.equals("DEPARTMENT STORE") || description.equals("CLEANING STORE")
                        || description.equals("APPLIANCE STORE") || description.equals("BANK") || description.equals("CONVENIENCE STORE")
                        || description.equals("DRUG STORE") || description.equals("SMALL RETAIL STORE") || description.equals("SAVINGS AND LOAN") || description.equals("GAS STATION")) {
                    storeCount.put(month, (storeCount.get(month)+1));
                }
                else if (description.equals("YMCA") || description.equals("SPORTS ARENA/STADIUM") || description.equals("PARK PROPERTY") || description.equals("LIBRARY")
                        || description.equals("HOSPITAL") || description.equals("CHURCH") || description.equals("CHURCH/SYNAGOGUE/PLACE OF WORSHIP")
                        || description.equals("BOWLING ALLEY") || description.equals("ATHLETIC CLUB") || description.equals("ANIMAL HOSPITAL")) {
                    publicCount.put(month, (publicCount.get(month)+1));
                }
            }

            System.out.println("HERE");
            for (HashMap.Entry<String, Integer> entry : publicCount.entrySet()) {
                System.out.println(entry.getKey() + " --- " + entry.getValue());
            }

        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void initializeMaps(HashMap<String, Integer> hm) {
        hm.put("01", 0);
        hm.put("02", 0);
        hm.put("03", 0);
        hm.put("04", 0);
        hm.put("05", 0);
        hm.put("06", 0);
        hm.put("07", 0);
        hm.put("08", 0);
        hm.put("09", 0);
        hm.put("10", 0);
        hm.put("11", 0);
        hm.put("12", 0);
    }

    public HashMap<String, Integer> getScatterValues(String category) {
        HashMap<String, Integer> hm = new HashMap<>();

        switch (category) {
            case "PUBLIC":
                hm = publicCount;
                break;
            case "STORE":
                hm = storeCount;
                break;
            case "BAR":
                hm = barCount;
                break;
            case "SCHOOL":
                hm = schoolCount;
                break;
            case "RESIDENCE":
                hm = residenceCount;
                break;
            case "TRAIN":
                hm = trainCount;
                break;
            case "VEHICLE":
                hm = vehicleCount;
                break;
            case "STREET":
                hm = streetCount;
                break;
        }
        return hm;
     }

}
