import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OdontologoDaoH2Test {
    private OdontologoDaoH2 odontologoDao;

    @BeforeEach
    public void setUp() {
        odontologoDao = new OdontologoDaoH2();
    }

    @Test
    public void testListarTodos() {
        Odontologo odontologo1 = new Odontologo(1, "Juan", "Perez");
        Odontologo odontologo2 = new Odontologo(2, "Maria", "Lopez");
        odontologoDao.guardar(odontologo1);
        odontologoDao.guardar(odontologo2);

        List<Odontologo> odontologos = odontologoDao.listarTodos();

        Assertions.assertFalse(odontologos.isEmpty());
        Assertions.assertTrue(odontologos.contains(odontologo1));
        Assertions.assertTrue(odontologos.contains(odontologo2));
    }
}
