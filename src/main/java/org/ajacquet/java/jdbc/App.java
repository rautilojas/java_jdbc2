package org.ajacquet.java.jdbc;

import models.Clientes;
import models.Productos;
import repositorios.ClientesRepositorioImpl;
import repositorios.ProductoRepositorioImpl;
import repositorios.Repositorio;
import util.ConexionBaseDeDatos;

import java.sql.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       try  {
           Repositorio<Productos> repositorio = new ProductoRepositorioImpl();
           repositorio.listar().forEach(System.out::println);

           Repositorio<Clientes> repositorio1 = new ClientesRepositorioImpl();
           repositorio1.listar().forEach(System.out::println);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
