public class GenerateQuery {

    public GenerateQuery() { }

    // generates queries for pie charts based on user selections
    public String getPieQuery(String start, String end) {
        String query = "";
        int startInt = Integer.parseInt(start);
        int endInt = Integer.parseInt(end);

        if (startInt >= 2001 & endInt <= 2004)
            query = "SELECT `Primary Type` FROM chicago_crimes_2001_to_2004 WHERE YEAR BETWEEN " + start + " AND " + end;
        else if (startInt >= 2005 & endInt <= 2007)
            query = "SELECT `Primary Type` FROM chicago_crimes_2005_to_2007 WHERE YEAR BETWEEN " + start + " AND " + end;
        else if (startInt >= 2008 & endInt <= 2011)
            query = "SELECT `Primary Type` FROM chicago_crimes_2008_to_2011 WHERE YEAR BETWEEN " + start + " AND " + end;
        else if (startInt >= 2012 & endInt <= 2017)
            query = "SELECT `Primary Type` FROM chicago_crimes_2012_to_2017 WHERE YEAR BETWEEN " + start + " AND " + end;
        else if(startInt >= 2001 & endInt <= 2007) {
            query = "SELECT `primary type` FROM ((SELECT `primary type` FROM chicago_crimes_2001_to_2004 WHERE YEAR >= " + start + " ) UNION ALL " +
                    "(SELECT `primary type` FROM chicago_crimes_2005_to_2007 WHERE YEAR <= " + end +  ")) AS t";
        }

        else if (startInt >= 2005 & endInt <= 2011) {
            query = "SELECT `primary type` FROM ((SELECT `primary type` FROM chicago_crimes_2005_to_2007 WHERE YEAR >= " + start + " ) UNION ALL " +
                    "(SELECT `primary type` FROM chicago_crimes_2008_to_2011 WHERE YEAR <= " + end +  ")) AS t";
        }
        else if (startInt >= 2008 & endInt <= 2017) {
            query = "SELECT `primary type` FROM ((SELECT `primary type` FROM chicago_crimes_2008_to_2011 WHERE YEAR >= " + start + " ) UNION ALL " +
                    "(SELECT `primary type` FROM chicago_crimes_2012_to_2017 WHERE YEAR <= " + end +  ")) AS t";
        }
        else if(startInt >= 2001 && endInt <= 2011) {
            query = "SELECT `primary type` FROM \n" +
                    "((SELECT `primary type` FROM chicago_crimes_2001_to_2004 WhERE YEAR >=" + start + ")\n" +
                    "UNION ALL\n" +
                    "(SELECT `primary type` FROM chicago_crimes_2005_to_2007 WHERE YEAR >=" + start+1 + ") \n" +
                    "UNION ALL \n" +
                    "(SELECT `primary type` FROM chicago_crimes_2008_to_2011 WHERE YEAR <=" + start+2 + ")) AS t";
        }
        else if(startInt >= 2005 && endInt <= 2017) {
            query = "SELECT `primary type` FROM \n" +
                    "((SELECT `primary type` FROM chicago_crimes_2005_to_2007 WhERE YEAR >=" + start + ")\n" +
                    "UNION ALL\n" +
                    "(SELECT `primary type` FROM chicago_crimes_2008_to_2011 WHERE YEAR >=" + start+1 + ") \n" +
                    "UNION ALL \n" +
                    "(SELECT `primary type` FROM chicago_crimes_2012_to_2017 WHERE YEAR <=" + start+2 + ")) AS t";
        }
        else if (startInt >= 2001 && endInt <= 2017) {
            query = "SELECT `primary type` FROM " +
                    "((SELECT `primary type` FROM chicago_crimes_2001_to_2004 WhERE YEAR >=" + start + ") " +
                    "UNION ALL " +
                    "(SELECT `primary type` FROM chicago_crimes_2005_to_2007 WHERE YEAR >= " + start+1 + ") " +
                    "UNION ALL " +
                    "(SELECT `primary type` FROM chicago_crimes_2008_to_2011 WHERE YEAR <=" + start+2 + ") " +
                    "UNION ALL " +
                    "(SELECT `primary type` FROM chicago_crimes_2012_to_2017 WHERE YEAR <=" + end + ")) AS t";
        }

        return query;
    }

    public String getSingleYearBarQuery(String yearStr, String choice) {
        int year = Integer.parseInt(yearStr);
        String query = "";

        if (year >= 2001 && year <= 2004) {
            query = "select description FROM chicago_crimes_2001_to_2004 WHERE `primary type` = \"" + choice + "\"" + "AND YEAR =" + yearStr;
        }
        else if (year >= 2005 && year <= 2007) {
            query = "select description FROM chicago_crimes_2005_to_2007 WHERE `primary type` = \"" + choice + "\"" + "AND YEAR=" + yearStr;
        }
        else if (year >= 2008 && year <= 2011) {
            query = "select description FROM chicago_crimes_2008_to_2011 WHERE `primary type` = \"" + choice + "\"" + "AND YEAR=" + yearStr;
        }
        else {
            query = "select description FROM chicago_crimes_2012_to_2017 WHERE `primary type` = \"" + choice + "\"" + "AND YEAR=" + yearStr;
        }

        return query;
    }

    public String getLineGraphQuery(String choice, int monthNum, String year) {
        String month = "";
        choice = choice.toUpperCase();
        String query = "";
        int yearInt = Integer.parseInt(year);

        switch (monthNum) {
            case 0:
                month = "'01%'";
                break;
            case 1:
                month = "'02%'";
                break;
            case 2:
                month = "'03%'";
                break;
            case 3:
                month = "'04%'";
                break;
            case 4:
                month = "'05%'";
                break;
            case 5:
                month = "'06%'";
                break;
            case 6:
                month = "'07%'";
                break;
            case 7:
                month = "'08%'";
                break;
            case 8:
                month = "'09%'";
                break;
            case 9:
                month = "'10%'";
                break;
            case 10:
                month = "'11%'";
                break;
            case 11:
                month = "'12%'";
                break;
        }

        if (yearInt >= 2001 && yearInt <= 2004) {
            query = "SELECT COUNT(DATE) FROM chicago_crimes_2001_to_2004 WHERE DATE LIKE" + month + "AND YEAR = " + year + " AND `primary type` =" + "\"" + choice + "\"";
        }
        if (yearInt >= 2005 && yearInt <= 2007) {
            query = "SELECT COUNT(DATE) FROM chicago_crimes_2005_to_2007 WHERE DATE LIKE" + month + "AND YEAR = " + year + " AND `primary type` =" + "\"" + choice + "\"";
        }
        if (yearInt >= 2008 && yearInt <= 2011) {
            query = "SELECT COUNT(DATE) FROM chicago_crimes_2008_to_2011 WHERE DATE LIKE" + month + "AND YEAR = " + year + " AND `primary type` =" + "\"" + choice + "\"";
        }
        if (yearInt >= 2012 && yearInt <= 2017) {
            query = "SELECT COUNT(DATE) FROM chicago_crimes_2012_to_2017 WHERE DATE LIKE" + month + "AND YEAR = " + year + " AND `primary type` =" + "\"" + choice + "\"";
        }

        //query = "SELECT COUNT(DATE) FROM chicago_crimes_2001_to_2004 WHERE DATE LIKE" + month + "AND YEAR = " + year + " AND `primary type` =" + "\"" + choice + "\"";

        return query;
    }

}
