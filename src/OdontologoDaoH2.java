import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class OdontologoDaoH2 implements IDao<Odontologo> {
    private static final Logger LOGGER = Logger.getLogger(OdontologoDaoH2.class);

    public OdontologoDaoH2() {
    }

    public Odontologo guardar(Odontologo odontologo) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement("INSERT INTO ODONTOLOGOS (NUMERO_MATRICULA, NOMBRE, APELLIDO) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, odontologo.getNumeroMatricula());
            ps.setString(2, odontologo.getNombre());
            ps.setString(3, odontologo.getApellido());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                odontologo.setNumeroMatricula(rs.getInt(1));
            }

            connection.commit();
            LOGGER.info("Se ha registrado el odontólogo: " + odontologo);
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.error("Error al guardar el odontólogo: " + e.getMessage());
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("No se pudo realizar la conexión, inténtalo nuevamente.");
                } catch (SQLException rollbackException) {
                    LOGGER.error("Error al hacer rollback: " + rollbackException.getMessage());
                    rollbackException.printStackTrace();
                }
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException closeException) {
                LOGGER.error("Error al cerrar la conexión: " + closeException.getMessage());
                closeException.printStackTrace();
            }
        }

        return odontologo;
    }

    public List<Odontologo> listarTodos() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Odontologo> odontologos = new ArrayList<>();

        try {
            //al colocar en el catch especificamente la SQLException, el metodo requiere agregar la ClassNotFoundException, por eso el getConnection() arroja error de compilacion
            connection = H2Connection.getConnection();
            ps = connection.prepareStatement("SELECT * FROM ODONTOLOGOS");
            rs = ps.executeQuery();

            while (rs.next()) {
                Odontologo odontologo = crearOdontologo(rs);
                odontologos.add(odontologo);
            }

            LOGGER.info("Listado de todos los odontólogos: " + odontologos);
        } catch (SQLException e) {
            LOGGER.error("Error al obtener el listado de odontólogos: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException closeException) {
                LOGGER.error("Error al cerrar la conexión: " + closeException.getMessage());
                closeException.printStackTrace();
            }
        }

        return odontologos;
    }

    private Odontologo crearOdontologo(ResultSet rs) throws SQLException {
        int numeroMatricula = rs.getInt("NUMERO_MATRICULA");
        String nombre = rs.getString("NOMBRE");
        String apellido = rs.getString("APELLIDO");
        return new Odontologo(numeroMatricula, nombre, apellido);
    }
}

