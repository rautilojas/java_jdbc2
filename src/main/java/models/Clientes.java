package models;

import lombok.Getter;
import lombok.Setter;
public class Clientes {

    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private String nombre;
    @Getter
    @Setter
    private String apellido;
    @Getter
    @Setter
    private String dni;
    @Getter
    @Setter
    private String email;

    @Override
    public String toString() {
        return  "id = " + id +
                " | nombre = '" + nombre + '\'' +
                " | apellido = " + apellido +
                " | dni = " + dni +
                " | email = " + email;
    }

}
