
package app.databasemysql;
import java.sql.*;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.SQLException;
public class MySQL {
    private static final String host = "SimonPC"; //DESKTOP-4PGGMQ6
    private static final String port = "3306";
    private static final String database = "belegschaft";
    private static final String username = "Simon"; //Killerponix
    private static final String password = "CVH"; // DBKillerponix



    private static Connection con;

    public static boolean isConnected() {
        return (con == null ? false : true);
    }
    public static void connect() throws ClassNotFoundException {
        if(!isConnected()) {
            try {

                Class.forName("com.mysql.cj.jdbc.Driver");

                con = DriverManager.getConnection("jdbc:mysql://" + host + ":"+port+"/"+database, username, password);
                System.out.println("[MySQL] Verbunden");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
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
    public static void executeQuery(String query) {
        try {
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


    public void connect(String text, String text1, String text2) {
        if(!isConnected()) {
            try {

                Class.forName("com.mysql.cj.jdbc.Driver");

                con = DriverManager.getConnection("jdbc:mysql://" + text2 + ":"+port+"/"+database, text, text1);
                System.out.println("[MySQL] Verbunden");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
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
