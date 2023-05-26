import java.util.List;

public interface IDao<T> {
    T guardar(T var1);

    List<T> listarTodos();

}
