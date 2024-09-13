import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Backup{
    //create a connection with the database
    private Connection dbConnection;

    public Backup(Connection dbConnection){
        this.dbConnection = dbConnection;
    }

    //backup the database
    public void backupDatabase(String fileName){
        try{FileWriter file= mew FileWriter(fileName){
            
        }}
    }
}