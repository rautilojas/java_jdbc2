package repositorios;

import java.util.List;

public interface Repositorio<T> {

    public List<T> listar();

    public T obtenerPorID(Integer id);

    public void guardar(T t);

    public void eliminar(Long id);
}
