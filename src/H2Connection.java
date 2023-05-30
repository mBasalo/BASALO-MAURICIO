import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class H2Connection {
    private static final String CREATE = "DROP TABLE IF EXISTS ODONTOLOGOS; CREATE TABLE ODONTOLOGOS(NUMEROMATRICULA INT AUTO_INCREMENT PRIMARY KEY, NOMBRE VARCHAR(100) NOT NULL, APELLIDO VARCHAR(100) NOT NULL)";

    public H2Connection() {
    }

    //este metodo nunca es llamado, la tabla nunca es creada
    public static void crearTabla() {
        Connection connection = null;

        try {
            connection = getConnection();
            Statement st = connection.createStatement();
            st.execute(CREATE);
        } catch (Exception var10) {
            var10.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception var9) {
                var9.printStackTrace();
            }
        }
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/back-end-examen", "sa", "sa");
    }
}

