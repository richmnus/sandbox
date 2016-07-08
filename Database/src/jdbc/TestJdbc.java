package jdbc;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Properties;
import java.util.Set;

public class TestJdbc {

    Connection conn;
    String url;

    /**
     * @param args
     */
    public static void main(String[] args) {
        TestJdbc test = new TestJdbc();
    }

    public TestJdbc() {
        System.out.println("-- " + this.getClass().getName() + ".init() --");

        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
            // url =
            // "jdbc:db2://192.168.1.81:50000/results:driverType=4;fullyMaterializeLobData=true;fullyMaterializeInputStreams=true;progressiveStreaming=2;progresssiveLocators=2;";
            url = "jdbc:db2://9.20.95.217:50000/results";
            conn = DriverManager.getConnection(url, "gh02", "passw0rd");

            // Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            // url =
            // "jdbc:oracle:thin:@ghserver01.rtp.raleigh.ibm.com:1521:RIT";
            // conn = DriverManager.getConnection(url, "system", "r00tDBpwd");

            // Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            // url = "jdbc:oracle:thin:@192.168.1.82:1521:XE";
            // conn = DriverManager.getConnection(url, "gh02", "passw0rd");

            // Dump connection properties already set
            // Properties prop = conn.getClientInfo();
            // Set<String> strs = prop.stringPropertyNames();
            // for (String s : strs) {
            // System.out.printf("%s = %s\n", s, prop.getProperty(s));
            // }

            doTests();
            // demoCallable();
            // demoCallable2();
            // demoMeta();
            // printBlobs();
            // writePreparedBlob();
//            writePreparedClob();
            printClobs();
            conn.close();
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IllegalAccessException ex) {
            System.err.println(ex.getMessage());
        } catch (InstantiationException ex) {
            System.err.println(ex.getMessage());
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void doTests() {
        {
            System.out.println("-- " + this.getClass().getName()
                    + ".doTests() --");
            String query = "SELECT * FROM db_details";
            try {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    String s = rs.getString("value");
                    System.out.println(s);
                }
                st.close();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }

            query = "SELECT * FROM project";
            try {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    String s = rs.getString("uniqueid");
                    System.out.println(s);
                }
                st.close();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public void demoCallable() {
        CallableStatement cs = null;
        System.out.println("-- " + this.getClass().getName()
                + ".demoCallable() --");

        // String query =
        // "SELECT id INTO ? FROM NEW TABLE (INSERT INTO project(id, uniqueid, name) VALUES(project_seq.nextval, ?, ?))";
        String query = "SELECT ? FROM NEW TABLE (INSERT INTO project(id, uniqueid, name) VALUES(project_seq.nextval, ?, ?))";
        try {
            cs = conn.prepareCall(query);
            cs.registerOutParameter(1, Types.DECIMAL);
            cs.setString(2, "info20");
            cs.setString(3, "text");
            cs.execute();
            System.out.println(cs.getLong(1));
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void demoCallable2() {
        CallableStatement cs = null;
        String query = "SELECT id INTO ? FROM project";
        System.out.println("-- " + this.getClass().getName()
                + ".demoCallable2() --");

        // String query =
        // "BEGIN INSERT INTO action_execution(id, action_id, parent_id) VALUES(action_execution_seq.nextval, ?, ?) RETURNING id INTO ?; END;";
        try {
            cs = conn.prepareCall(query);
            cs.registerOutParameter(1, Types.DECIMAL);
            cs.execute();
            System.out.println(cs.getLong(1));
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void demoMeta() {
        ResultSet rs = null;
        Integer i = null;
        System.out
                .println("-- " + this.getClass().getName() + ".demoMeta() --");
        try {
            DatabaseMetaData meta = conn.getMetaData();
            // For DB2, the schema name, table name and column name must be
            // uppercase
            rs = meta.getColumns(null, "GH02", "SCENARIO", "DISPLAY");
            if (rs.next()) {
                System.out.println("Got a result");
                i = rs.getInt("COLUMN_SIZE");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
        System.out.println(i);
    }

    public void printBlobs() {
        {
            System.out.println("-- " + this.getClass().getName()
                    + ".printBlobs() --");
            String query = "SELECT id,received FROM action_execution ORDER BY id ASC";
            try {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    int id = rs.getInt(1);
                    Blob blob = rs.getBlob(2);
                    if (blob != null) {
                        byte[] bytes = blob.getBytes(1, 256);
                        System.out.printf("id = %d, blob %d bytes = ", id,
                                bytes.length);
                        for (byte b : bytes) {
                            System.out.printf("%x ", b);
                        }
                        System.out.printf("\n");
                    }
                }
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void printClobs() {
        {
            System.out.println("-- " + this.getClass().getName()
                    + ".printClobs() --");
            String query = "SELECT execution_id,note FROM execution_note ORDER BY execution_id ASC";
            try {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    int id = rs.getInt(1);
                    Clob clob = rs.getClob(2);
                    if (clob != null) {
                        String str = clob.getSubString(1, 256);
                        System.out.printf("id = %d, clob %d chars = ", id,str.length());
                        System.out.printf("%s\n",str);
                    }
                }
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void writePreparedBlob() {
        System.out.println("-- " + this.getClass().getName()
                + ".writePreparedBlob() --");
        PreparedStatement prepStmt = null;
        String sql = "UPDATE action_execution SET received=? WHERE id=33";
        byte[] bytes = { 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20,
                0x20 };
        try {
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setBytes(1, bytes);
            prepStmt.execute();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writePreparedClob() {
        System.out.println("-- " + this.getClass().getName()
                + ".writePreparedClob() --");
        PreparedStatement prepStmt = null;
        String sql = "INSERT INTO execution_note (note=? WHERE id=33";
        char[] chars = { 'h', 'e', 'l', 'l', 'o' };
        try {
//            Clob clob = (Clob) chars;
//            clob.
//            prepStmt = conn.prepareStatement(sql);
//            prepStmt.setClob(1, chars);
            prepStmt.execute();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
