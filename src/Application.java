import java.sql.Connection;
import java.sql.DriverManager;

public class Application {
    public Application() {
    }

    public static void main(String[] args) {
        Connection connection = null;

        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/back-end-examen;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "sa");
        } catch (Exception var11) {
            var11.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception var10) {
                var10.printStackTrace();
            }

        }

    }
}