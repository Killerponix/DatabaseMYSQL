
package app.databasemysql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class MySQL {
//    private static final String host = "SimonPC"; //DESKTOP-4PGGMQ6
//    private static final String port = "3306";
//    private static final String database = "belegschaft";
//    private static final String username = "Simon"; //Killerponix
//    private static final String password = "CVH"; // DBKillerponix

    private static final String host = "localhost";
    private static final String port = "3306";
    private static final String database = "belegschaft";
    private static final String username = "Simon";
    private static final String password = "CVH";




    private static Connection con;

    public static boolean isConnected() {
        return (con == null ? false : true);
    }


//    public static void connect() throws ClassNotFoundException {
//        if(!isConnected()) {
//            try {
//
//                Class.forName("com.mysql.cj.jdbc.Driver");
//
//                con = DriverManager.getConnection("jdbc:mysql://" + host + ":"+port+"/"+database, username, password);
//                System.out.println("[MySQL] Verbunden");
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }



    public static void disconnect() {
        if(isConnected()) {
            try {
                con.close();
                System.out.println("[MySQL] Verbindung Geschlossen");
            } catch (SQLException e) {
                System.out.println("rein");
                e.printStackTrace();


            }
        }
    }
    //	public static void update(String qry) {
//		try {
//			Statement ps = con.createStatement();
//			ResultSet res = ps.executeQuery(qry);
//            int ang_nr;
//            int salary;
//            Date d1,d2;
//            System.out.println("AngestelltenNR. " + "Gehalt" + "Von" +"Bis");
//            while (res.next()) {
//                ang_nr = res.getInt(1);
//                salary = res.getInt(2);
//            	d1 = res.getDate(3);
//                d2 = res.getDate(4);
//                boolean exist = true;
//                System.out.println(ang_nr + " " + salary + " " + d1 + " " + d2);
//            };
//        ps.close();
//        res.close();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//
//		}
//	}
    public static Object[] execute(String sql) {
/*        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-10s   ", metaData.getColumnName(i));
            }
            System.out.println("");

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("%-10s   ", resultSet.getString(i));
                }
                System.out.println("");
            }

            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        try {
            if (sql.equalsIgnoreCase("update")){

            }else if (sql.equalsIgnoreCase("Insert")){
                PreparedStatement prep = con.prepareStatement(sql);
            }else {
                Statement stmt = con.createStatement();
                stmt.execute(sql);
                stmt.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    return null;
    }




    /**
     *
     * @return
     */

    public ObservableList<angestellte> getAngestellte() {
        ObservableList<angestellte> angestellteList = FXCollections.observableArrayList();
        String query = "SELECT * FROM angestellte";
        try (
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int ang_nr = rs.getInt("ang_nr");
                String vorname = rs.getString("vorname");
                String nachname = rs.getString("nachname");
                Date birth_date = rs.getDate("birth_date");
                String geschlecht = rs.getString("geschlecht");
                Date hire_date = rs.getDate("hire_date");
                if (geschlecht==null)
                {
                    geschlecht="";
                }
//                System.out.println(geschlecht);

                angestellte ang = new angestellte(ang_nr, vorname, nachname, birth_date, geschlecht.toString(), hire_date);
                angestellteList.add(ang);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return angestellteList;
    }

    /**
     *
     * @return
     */
    public ObservableList<gehalt> getgeh(){
        ObservableList<gehalt> gehlist = FXCollections.observableArrayList();
        String query = "SELECT * FROM gehälter";
        try (
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int ang_nr = rs.getInt("ang_nr");
                int gehalt = rs.getInt("gehalt");
                Date from_date = rs.getDate("from_date");
                Date to_date = rs.getDate("to_date");
                gehalt geh = new gehalt(ang_nr,gehalt,from_date,to_date);
                gehlist.add(geh);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return gehlist;}

    /**
     *
     * @return
     */
    public ObservableList<titel> gettitel(){
        ObservableList<titel> titelList = FXCollections.observableArrayList();
        String query = "SELECT * FROM titel";
        try (
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int ang_nr = rs.getInt("ang_nr");
                String titel = rs.getString("titel");
                Date from_date = rs.getDate("from_date");
                Date to_date = rs.getDate("to_date");
                titel tit = new titel(ang_nr,titel,from_date,to_date);
                titelList.add(tit);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    return titelList;}

    public ObservableList<gesamt> getGesamtInformation() {
        ObservableList<gesamt> gesamtList = FXCollections.observableArrayList();
        String query = "SELECT * FROM gesamt_information_view";
        try (
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int ang_nr = rs.getInt("AngestelltenID");
                String vorname = rs.getString("Vorname");
                String nachname = rs.getString("Nachname");
                Date birth_date = rs.getDate("Geburtsdatum");
                String geschlecht = rs.getString("Geschlecht");
                Date hire_date = rs.getDate("Einstellungsdatum");
                String titel = rs.getString("Titel");
                Date titel_from = rs.getDate("Titel_Von");
                Date titel_to = rs.getDate("Titel_Bis");
                int gehalt = rs.getInt("Gehalt");
                Date gehalt_from = rs.getDate("Gehalt_Von");
                Date gehalt_to = rs.getDate("Gehalt_Bis");

                gesamt gesamtInfo = new gesamt(ang_nr, vorname, nachname, birth_date, geschlecht, hire_date, titel
                        , titel_from, titel_to, gehalt, gehalt_from, gehalt_to);
                gesamtList.add(gesamtInfo);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return gesamtList;
    }


    public Object[] getGesamt(){

    return null;}
    public Object[] getanggeh(){

    return null;}
    public Object[] gettitgeh(){

    return null;}

//    <<<<<<<<<<<<<<<<<<<<<<<<<<<<---------------------------------------------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    /**Funktion die einen Angestellten der Datenbank hinzufügt
     *
     * @param usr Vorname
     * @param pw  Machname
     * @param birth Geburtsdatum
     * @param geschlecht    Geschlecht M/F
     * @param hire  Anstelldatum
     */
    public void addAngestellter(String usr, String pw, Date birth, String geschlecht, Date hire){
        try {
            String sql = "INSERT INTO angestellte (vorname, nachname, birth_date, geschlecht, hire_date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement prep = con.prepareStatement(sql);
            prep.setString(1,usr);
            prep.setString(2,pw);
            prep.setDate(3,birth);
            prep.setString(4,geschlecht);
            prep.setDate(5,hire);
            System.out.println(prep);
            prep.execute();
            prep.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addGehalt(int nr,int gehalt, Date from, Date to){
        try {
            String sql = "INSERT INTO gehälter (ang_nr,gehalt,from_date,to_date) VALUES (?, ?, ?, ?)";    // DIe angestellten nummer muss noch eingetragen werden
            PreparedStatement prep = con.prepareStatement(sql);
            prep.setInt(1,nr);
            prep.setInt(2,gehalt);
            prep.setDate(3,from);
            prep.setDate(4,to);

            prep.execute();
            prep.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTitel(int nr,String titel, Date from, Date to){
        try {
            String sql = "INSERT INTO Titel (ang_nr, titel,from_date,to_date) VALUES(?,?,?,?)";
            PreparedStatement prep = con.prepareStatement(sql);
            prep.setInt(1,nr);
            prep.setString(2,titel);
            prep.setDate(3,from);
            prep.setDate(4,to);
            prep.execute();
            prep.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static void executeUpdate(String query) {
        try {
            Statement statement = con.createStatement();
            int rowsAffected = statement.executeUpdate(query);
            System.out.println("Rows affected: " + rowsAffected);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void test(String sql){
        try{
            sql = "INSERT INTO ANGESTELLTE VALUES (?,?,?,?)";
            PreparedStatement prep = con.prepareStatement(sql);
            prep.setString(1,"Enum");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**Verbindet sich mit der Datenbank, anhand der eigegeben Daten in der loginpage
     *
     * @param Username Username
     * @param Password Password
     * @param Adr Hostadr.
     */
    public void connect(String Username, String Password, String Adr) {
        if(!isConnected()) {
            try {

                Class.forName("com.mysql.cj.jdbc.Driver");

                con = DriverManager.getConnection("jdbc:mysql://" + Adr + ":"+port+"/"+database, Username, Password);
                System.out.println("[MySQL] Verbunden");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void delete(Object[] o) {
        String sql = "DELETE FROM "+o[0]+" WHERE ang_nr=?";
//        System.out.println(o[0]+" "+ o[1]);
        if (o[0].toString().equalsIgnoreCase("Angestellte")){

            int ang_nr = (int) o[1];
            try {
                // Zuerst löschen Sie die entsprechenden Zeilen aus der Tabelle `gehälter`
                String deleteGehälterSQL = "DELETE FROM gehälter WHERE ang_nr=?";
                PreparedStatement deleteGehälterStatement = con.prepareStatement(deleteGehälterSQL);
                deleteGehälterStatement.setInt(1, ang_nr);
                deleteGehälterStatement.executeUpdate();
                deleteGehälterStatement.close();

                String deletetitelSQL = "DELETE FROM titel WHERE ang_nr=?";
                PreparedStatement deletetitelStatement = con.prepareStatement(deletetitelSQL);
                deletetitelStatement.setInt(1, ang_nr);
                deletetitelStatement.executeUpdate();
                deletetitelStatement.close();

                // Dann löschen Sie die Zeile aus der Tabelle `Angestellte`
                String deleteAngestellteSQL = "DELETE FROM Angestellte WHERE ang_nr=?";
                PreparedStatement deleteAngestellteStatement = con.prepareStatement(deleteAngestellteSQL);
                deleteAngestellteStatement.setInt(1, ang_nr);
                deleteAngestellteStatement.executeUpdate();
                deleteAngestellteStatement.close();


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }else {
            try {
                PreparedStatement prep = con.prepareStatement(sql);
//            prep.setString(1,o[0].toString().replace("'",""));
                prep.setInt(1,java.lang.Integer.valueOf(o[1].toString()) );
                System.out.println(prep);
                prep.execute();
                prep.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public void updateMitarbeiter(Object[] objects, Object ID) {
        LocalDate birth = (LocalDate) objects[2];
        LocalDate hiredatum = (LocalDate) objects[4];
        String sql = "UPDATE angestellte SET vorname=?, nachname=?, birth_date=?, geschlecht=?, hire_date=? WHERE ang_nr=?";
        try {
            PreparedStatement prep = con.prepareStatement(sql);
            prep.setString(1,objects[0].toString());
            prep.setString(2,objects[1].toString());
            prep.setDate(3, Date.valueOf(birth));
            prep.setString(4,objects[3].toString());
            prep.setDate(5, Date.valueOf(hiredatum));
            prep.setInt(6, (Integer) ID);
            System.out.println(prep);
            prep.execute();
            prep.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateGehalt(Object[] objects) {
        LocalDate from = (LocalDate) objects[2];
        LocalDate to = (LocalDate) objects[3];
        String sql = "UPDATE gehalt SET ang_nr=, nachname=?, birth_date=?, geschlecht=?, hire_date=? WHERE ang_nr=?";
    }
}


//old Code
/*
import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class MySQL {
    public void update(String qry) {
        try {
            Statement ps = con.createStatement();
            ResultSet res = ps.executeQuery(qry);
            int ang_nr;
            int salary;
            Date d1, d2;
            System.out.println("AngestelltenNR. " + "Gehalt" + "Von" + "Bis");
            while (res.next()) {
                ang_nr = res.getInt(1);
                salary = res.getInt(2);
                d1 = res.getDate(3);
                d2 = res.getDate(4);
                boolean exist = true;
                System.out.println(ang_nr + " " + salary + " " + d1 + " " + d2);
            }
            ;
            ps.close();
            res.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Connection getCon() {
        return con;
    }

    public static String getDatabase() {
        return database;
    }

//    private static String host = "SimonPC";
    private static String host = "DESKTOP-4PGGMQ6";
    String url = "jdbc:mysql://localhost:3306/belegschaft";
    private static String port = "3306";
    private static String database = "belegschaft";
    private static String username = "Simon";
    private static String password = "CVH";
    private static Connection con;

    public boolean isConnected() {
        return false;
        //        return (con != null);
    }




    public void connect(String username, String password, String host) {
        if(!isConnected()){

        }
            try {

//                Class.forName("com.mysql.cj.jdbc.Driver");

//                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database,"killerponix" , "Test#1234");
                con = DriverManager.getConnection(url,"killerponix" , "Test#1234");
                System.out.println("[MySQL] Verbunden");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    public void disconnect() {
        if (isConnected()) {
            try {
                con.close();
                System.out.println("[MySQL] Verbindung Geschlossen");
            } catch (SQLException e) {
                System.out.println("rein");
                e.printStackTrace();


            }
        }
    }

}

*/
