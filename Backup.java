import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Backup{
    //create a connection with the database
    private Connection storedDbConnection;

    public Backup(Connection incomingNewConnection){
        this.storedDbConnection = incomingNewConnection;
    }

    //backup the database
    public void backupDatabase(String fileName){
        try{
            //Open backup file for writing
            Bufferedwriter dataWriter = new BufferedWriter(new FileWriter(fileName,"UTF-8"));
            //Get all table names from the database
            Statement statement = storedDbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SHOW TABLES");
            // Retrieve a list of all the tables .Write the SQL code for each table to the backup file
            List<String> tableNames = new ArrayList<String>();
            while(resultSet.next()){
                tableNames.add(resultSet.getString(1));
            }
            for(String tableName : tableNames){
                dataWriter.write("DROP TABLE IF EXISTS " + tableName + ";");
                dataWriter.newLine();
                //Get the create table statement for the current table
                resultSet = statement.executeQuery("SHOW CREATE TABLE " + tableName);
                if(resultSet.next()){
                    dataWriter.write(resultSet.getString(2) + ";");
                    dataWriter.newLine();
                }
                //Get the data from the current table
                resultSet = statement.executeQuery("SELECT * FROM " + tableName);
                while(resultSet.next()){
                    StringBuilder row = new StringBuilder();
                    row.append("INSERT INTO " + tableName + " VALUES(");
                    for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++){
                        row.append("'" + resultSet.getString(i) + "'");
                        if(i != resultSet.getMetaData().getColumnCount()){
                            row.append(",");
                        }
                    }
                    row.append(");");
                    dataWriter.write(row.toString());
                    dataWriter.newLine();
                }
            }

        }
    }
}