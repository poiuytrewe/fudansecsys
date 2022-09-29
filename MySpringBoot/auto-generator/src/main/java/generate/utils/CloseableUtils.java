package generate.utils;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class CloseableUtils {


    public static void close(Closeable fos) throws IOException {
        if (null != fos) {
            fos.close();
        }
    }


    public static void closeConn(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
