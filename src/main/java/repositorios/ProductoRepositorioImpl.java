package repositorios;

import models.Productos;
import util.ConexionBaseDeDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorioImpl implements Repositorio<Productos> {


    @Override
    public List<Productos> listar() {
        List<Productos> productosList = new ArrayList();
        try(Statement stmt = getConnetion().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM productos")){
            while(rs.next()){
                Productos producto = new Productos();
                producto = getProductoFromDB(rs, producto);
                productosList.add(producto);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return productosList;
    }

    @Override
    public Productos obtenerPorID(Integer id) {
        Productos producto = new Productos();
        try(PreparedStatement stmt = getConnetion().prepareStatement("SELECT * FROM productos WHERE idproductos = ?")){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                producto = getProductoFromDB(rs, producto);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return producto;
    }

    @Override
    public void guardar(Productos productos) {
        String sql = "";
        if(productos.getId() != null){
            sql = "UPDATE productos SET nombre = ?, precio = ? , fechaAlta = ?)";
        } else {
            sql = "INSERT INTO productos(nombre,precio,fechaAlta) VALUES (?,?,?)";
        }

        try(PreparedStatement stmt = getConnetion().prepareStatement(sql)){
            stmt.setString(1,productos.getNombre());
            stmt.setDouble(2,productos.getPrecio());
            stmt.setDate(3, new Date(productos.getFechaAlta().getTime()));

            stmt.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {
        try (PreparedStatement stmt = getConnetion().prepareStatement("DELETE FROM productos WHERE idproductos = ?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Productos getProductoFromDB(ResultSet rs, Productos producto) throws SQLException {
        producto.setId(rs.getInt("idproductos"));
        producto.setNombre(rs.getString("nombre"));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setFechaAlta(rs.getDate("fechaAlta"));
        return producto;
    }

    private Connection getConnetion() throws SQLException {
        return ConexionBaseDeDatos.getConnection();
    }

}
