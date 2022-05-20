package entity;

/**
 *
 * @author davibern
 * @version 1.0
 */
public class Notas {
    
    public String dni;
    public String nombreModulo;
    public String curso;
    public double nota;
    
    public Notas() {}

    public Notas(String dni, String nombreModulo, String curso, double nota) {
        this.dni = dni;
        this.nombreModulo = nombreModulo;
        this.curso = curso;
        this.nota = nota;
    }
    
}
