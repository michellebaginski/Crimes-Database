Steps To Run Application
---------------------------------------------------
1. The link to download the data files can be found included here: https://www.kaggle.com/currie32/crimes-in-chicago?select=Chicago_Crimes_2008_to_2011.csv
There are four files representing data from 2001-2017. My database uses all four of the files on the aforementioned link. The only thing you will need to do to download this data is to make an account with the website, then click the "Download" link at the top of the page or the down arrow.

2. 
IDE: IntelliJ IDEA 2018.3.3
Language: Java 
Libraries:
JavaFX
JDK 8
MYSQL connector 8.0.221
Database: MySQL (used MySQLWorkbench)

Setting up the project:
a. 	Download the JDBC driver for MySQL
	- Go to: https://dev.mysql.com/downloads/connector/j/
	- Select "Platform Independent" as OS and download zip
	- Extract zip to any location

b. Connect to IDE
	- In IntelliJ, go to: 
	File > 
	Project Structure > 
	Libraries > 
	"+" > 
	New Project Library > 
	Select the extracted JDBC driver here >
	Apply >
	Ok 

c. Create the database schema in MySQLWorkbench:
	- Create a new schema and name it "CrimesDatabase"
	- Please make sure the Datatypes for columns on all four datasets are as follows and import them into Workbench: 

	MyUnknownColumn:				BIGINT
	ID: 					        BIGINT
	Case Number: 			    		TEXT
	Date:					        TEXT
	Block:				        	TEXT
	IUCR:		 			        TEXT
	Primary Type:				    	TEXT
	Description:				    	TEXT
	Location Description: 				TEXT
	Arrest:					        TEXT
	Domestic:				        TEXT
	Beat:					        INT
	District:				        DOUBLE
	Ward:					        DOUBLE
	Community Area:				  	DOUBLE
	FBI Code:				        TEXT
	X Coordinate:				    	DOUBLE
	Y Coordinate:				    	DOUBLE
	Year:					        INT
	Updated On:			      		TEXT
	Latitude:				        DOUBLE
	Longitude:			      		DOUBLE
	Location:				        TEXT

	- Before running the application, make sure to navigate to the "DbConnect.java" file and
	adjust the "password" field on line 29 to be the one corresponding to your MySQL database.

        Now, you should have all the necessary tools to run the application!


3. For instructions on how to run the application, please refer to Steps 1 and 2.
The interaction my application offers is data manipulation and visualization.

I will sequentially go through features as they appear in order on the application.

	a)  Choose any span of years to view crime percentages in the form of a pie chart.
	b)  Choose a category and a year to get sub-categorical breakdowns of crime and their counts 
	represented as a bar graph.
	c) Select at least one and up to four years for comparison. This feature gives a monthly
	breakdown of crime for all months of the year using line graphs. (May need to wait a few 
	seconds.)
	d) Select a year to view the top 10 crimes for that year.
	e) Select a year to view a scatterplot of the crime locations. The scatterplot plots the
	how many occurrences of crime there was for a specific area. The breakdown is monthly.

 
