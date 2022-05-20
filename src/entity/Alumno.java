package entity;

import java.util.Date;

/**
 *
 * @author davibern
 * @version 1.0
 */
public class Alumno {
    
    public String dni;
    public String nombre;
    public String apellidos;
    public String direccion;
    public Date fechaNacimiento;

    public Alumno() {}

    public Alumno(String dni, String nombre, String apellidos, String direccion, Date fechaNacimiento) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
    }
    
}
