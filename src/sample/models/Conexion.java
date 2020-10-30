package sample.models;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    private static String server = "localhost";
    private static String user = "topicos2020";
    private static String pwd = "123tamarindo";
    private static String db = "restaurante";

    public static Connection con;

    public static void crearConeccion(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://"+server+":3306/"+db,user,pwd);
        }
        catch (Exception e){ e.printStackTrace(); }
    }
}
