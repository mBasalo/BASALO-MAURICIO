import java.sql.Connection;
import java.sql.DriverManager;

public class Application {
    public Application() {
    }

    public static void main(String[] args) {
        Connection connection = null;

        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/back-end-examen;INIT=RUNSCRIPT FROM 'create.sql'", "mBasalo", "mBasalo");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }

    }
}