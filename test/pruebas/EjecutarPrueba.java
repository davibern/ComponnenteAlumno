package pruebas;

/**
 *
 * @author davibern
 * @version 1.0
 */
public class EjecutarPrueba {
    
    public static void main(String[] args) {
        CargarDatos cargar = new CargarDatos();

        cargar.alumnos();
        cargar.notas();
        //gestion.anadeAlumno();
        cargar.anadeNota(); 
    }
    
}
