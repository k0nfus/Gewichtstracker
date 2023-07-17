import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

public class App {
    static String url = "jdbc:mysql://localhost:3306/Jan";
    static String username = "";
    static String password = "";

    public static void login() {
        System.out.println("Benutzername: ");
        Scanner eingabe = new Scanner(System.in);
        username = eingabe.next();

        System.out.println("Passwort: ");
        Scanner eingabepw = new Scanner(System.in);
        password = eingabepw.next();
    }

    public static void create() {
        System.out.println("Bitte geben Sie das Gewicht in KG ein (123.10) ");
        Scanner eingabegewicht = new Scanner(System.in);
        String gewicht = eingabegewicht.next();

        System.out.println("Bitte geben Sie ein Datum für den Eintrag ein (JJJJ-MM-TT):");
        Scanner eingabedatum = new Scanner(System.in);
        String datum = eingabedatum.next();
        
        // Kontrollausgabe ohne Semikolon
        // System.out.println("INSERT INTO gewicht (date, gewicht) VALUES (" + "\"" + datum + "\", \"" + gewicht + "\")");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement stmt = connection.createStatement();        
            String sql = "INSERT INTO gewicht (date, gewicht) VALUES (" + "\"" + datum + "\", \"" + gewicht + "\")";
            stmt.executeUpdate(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    public static void read() {
        String sql = "SELECT id, date, gewicht FROM gewicht";
        System.out.println("\n\nID:\tDatum:\t\tGewicht:\n");
        try(Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        ) {		      
        while(rs.next()){
        System.out.println(rs.getInt("id") + "\t" + rs.getDate("date") + "\t" +rs.getDouble("gewicht"));
        }
        } catch (SQLException e) {
        e.printStackTrace();
        } 
    }

    public static void update() {
        String select = "SELECT id, date, gewicht FROM gewicht";

        System.out.println("Welcher Eintrag (ID) soll bearbeitet werden?");
        Scanner eingabeid = new Scanner(System.in);
        int id = eingabeid.nextInt();

        System.out.println("Bitte geben Sie das Gewicht in KG ein (Beispiel: 123.45): ");
        Scanner eingabegewicht = new Scanner(System.in);
        String gewicht = eingabegewicht.next();

        System.out.println("Bitte geben Sie ein Datum für den Eintrag ein (Beispiel: JJJJ-MM-TT): ");
        Scanner eingabedatum = new Scanner(System.in);
        String datum = eingabedatum.next();
        
        System.out.println("\n\nID:\tDatum:\t\tGewicht:\n");
        
        try(Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
        ) {		      
            String sqldatum = "UPDATE gewicht SET date = \"" + datum + "\"" + "WHERE id = " + id;
            stmt.executeUpdate(sqldatum);

            String sqlgewicht = "UPDATE gewicht " + "SET gewicht = \"" + gewicht + "\"" + "WHERE id = " + id;
            stmt.executeUpdate(sqlgewicht);

            ResultSet rs = stmt.executeQuery(select);
            while(rs.next()){
                System.out.println(rs.getInt("id") + "\t" + rs.getDate("date") + "\t" +rs.getDouble("gewicht"));
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    public static void delete() {
        System.out.println("Welcher Eintrag (ID) soll gelöscht werden?");
        Scanner eingabeid = new Scanner(System.in);
        int id = eingabeid.nextInt();
        String select = "SELECT id, date, gewicht FROM gewicht";
        
        try(Connection conn = DriverManager.getConnection(url, username, password);
        Statement stmt = conn.createStatement();
        ) {		      
        String sql = "DELETE FROM gewicht " +
        "WHERE id = " + id;
        stmt.executeUpdate(sql);
        ResultSet rs = stmt.executeQuery(select);

        while(rs.next()){
        System.out.println(rs.getInt("id") + "\t" + rs.getDate("date") + "\t" +rs.getDouble("gewicht"));
        }

        rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    public static void menu() {
        Scanner benutzereingabe = new Scanner(System.in);
        System.out.println("\n\nC - Neuen Eintrag erstellen (Create)");
        System.out.println("R - Abfrage aller Einträge (Read)");
        System.out.println("U - Einen Eintrag bearbeiten (Update)");
        System.out.println("D - Einen Eintrag löschen (Delete)");
        System.out.println("X - Programm beenden");
        String menu = benutzereingabe.next();
        switch(menu) {
            case "C":
            case "c":
                create();
                menu();
            case "R":
            case "r":
                read();
                menu();
            case "U":
            case "u":
                update();
                menu();
            case "D":
            case "d":
                delete();
                menu();
            case "X":
            case "x":
                System.exit(0);
            break;
            default:
            System.out.println("Sie haben keine gültige Auswahl getroffen!");
            menu();
            }
    }
    public static void main(String[] args) throws Exception {
        System.out.println("\n\n##### Tracking des Gewichts mittels einer MySQL-Datenbank #####");
        login();
        menu();
    }
}