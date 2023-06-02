package repositorios;

import models.Clientes;
import util.ConexionBaseDeDatos;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientesRepositorioImpl implements Repositorio<Clientes> {


    @Override
    public List<Clientes> listar() {
        List<Clientes> clientesList = new ArrayList();
        try(Statement stmt = getConnetion().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM clientes")){
            while(rs.next()){
                Clientes cliente = new Clientes();
                cliente = getClienteFromDB(rs, cliente);
                clientesList.add(cliente);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return clientesList;
    }

    @Override
    public Clientes obtenerPorID(Integer id) {
        Clientes cliente = new Clientes();
        try(PreparedStatement stmt = getConnetion().prepareStatement("SELECT * FROM clientes WHERE idclientes = ?")){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                cliente = getClienteFromDB(rs, cliente);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return cliente;
    }

    @Override
    public void guardar(Clientes clientes) {
        String sql = "";
        if(clientes.getId() != null){
            sql = "UPDATE clientes SET nombre = ?, apellido = ?, dni = ? , email = ?)";
        } else {
            sql = "INSERT INTO clientes(nombre,apellido,dni,email) VALUES (?,?,?,?)";
        }

        try(PreparedStatement stmt = getConnetion().prepareStatement(sql)){
            stmt.setString(1,clientes.getNombre());
            stmt.setString(2,clientes.getApellido());
            stmt.setString(3,clientes.getDni());
            stmt.setString(4,clientes.getEmail());

            stmt.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {
        try (PreparedStatement stmt = getConnetion().prepareStatement("DELETE FROM clientes WHERE idclientes = ?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Clientes getClienteFromDB(ResultSet rs, Clientes cliente) throws SQLException {
        cliente.setId(rs.getInt("idclientes"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setApellido(rs.getString("apellido"));
        cliente.setDni(rs.getString("dni"));
        cliente.setEmail(rs.getString("email"));
        return cliente;
    }

    private Connection getConnetion() throws SQLException{
        return ConexionBaseDeDatos.getConnection();
    }

}