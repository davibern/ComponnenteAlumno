package pruebas;

import bean.AlumnoBean;
import bean.NotasBean;
import event.ModificarBDEvent;
import event.ModificarBDListener;
import java.sql.Date;

/**
 *
 * @author davibern
 * @version 1.0
 */
public class CargarDatos implements ModificarBDListener {
    
    private AlumnoBean alumnos;
    private NotasBean notas;
    
    public CargarDatos() {
        this.alumnos = new AlumnoBean();
        this.alumnos.addModificarBDListener(this);
        
        this.notas = new NotasBean();
        this.notas.addModificadaListener(this);
    }
    
    public void alumnos() {
        for (int i = 0; i < this.alumnos.size(); i++) {
            alumnos.seleccionarFilasBD(i);
            System.out.println("Alumno " + i + "\n\tDNI:" + alumnos.getDni());
            System.out.println("\tNombre: " + alumnos.getNombre());
            System.out.println("\tApellidos: " + alumnos.getApellidos());
            System.out.println("\tDireccion: " + alumnos.getDireccion());
            System.out.println("\tFecha de nacimiento: " + alumnos.getFechaNacimiento());
        }
    }
    
    public void notas() {
        for(int i = 0; i < this.notas.size(); i++) {
            notas.seleccionarFilasBD(i);
            System.out.println("Nota " + i + "\n\tDNI:" + notas.getDni());
            System.out.println("\tNombre Módulo: " + notas.getNombreModulo());
            System.out.println("\tCurso: " + notas.getCurso());
            System.out.println("\tNota: " + notas.getNota());
        }
    }
    
    public void agregarAlumno() {
        alumnos.setDni("23327766F");
        alumnos.setNombre("Daniel");
        alumnos.setApellidos("Fernández León");
        alumnos.setDireccion("C/ Marie Curie, nº 27");
        alumnos.setFechaNacimiento(Date.valueOf("1999-12-31"));
        try {
            alumnos.addAlumno();
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void anadeNota() {
        
        notas.setDni("23456789B");
        notas.setNombreModulo("FOL");
        notas.setCurso("21-22");
        notas.setNota(6.5);
        try {
            notas.agregarNotaModuloBD();
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        
    }

    @Override
    public void capturarModificacionBD(ModificarBDEvent ev) {
        System.out.println("Se ha añadido un elemento nuevo a la base de datos");
    }
    
}
