package jdbc.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestJdbcTest {

    Connection conn;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        // Class.forName("com.greenhat.jdbc.Driver").newInstance();
        // Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
        String url = "jdbc:oracle:thin:@gh-db1-linux.hursley.ibm.com:1521:DBTEST";
        conn = DriverManager.getConnection(url, "RM_REAL", "RM_REAL");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void test() {
        String sql = "select object_name from all_objects where owner = 'RM_REAL'";
        Statement s;
        try {
            s = conn.createStatement();
            s.execute(sql);
            ResultSet rs = s.getResultSet();
            while (rs.next())
                System.out.println(rs.getString(1));
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
