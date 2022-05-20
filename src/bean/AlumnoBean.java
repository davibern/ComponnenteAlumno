package bean;

import entity.Alumno;
import event.ModificarBDEvent;
import event.ModificarBDListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author davibern
 * @version 1.0
 */
public class AlumnoBean implements Serializable {
    
    private PropertyChangeSupport propertyChangeSupport;
    protected String dni;
    protected String nombre;
    protected String apellidos;
    protected String direccion;
    protected Date fechaNacimiento;
    private final Vector alumnos = new Vector();
    private ModificarBDListener listener;

    public AlumnoBean() {
        propertyChangeSupport = new PropertyChangeSupport(this);
        
        try {
            recargarFilasBD();
        } catch (ClassNotFoundException e) {
            this.dni = "";
            this.nombre = "";
            this.apellidos = "";
            this.direccion = "";
            this.fechaNacimiento = null;
            System.err.println(e.getMessage());
        }
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    public void setPropertyChangeSupport(PropertyChangeSupport propertyChangeSupport) {
        this.propertyChangeSupport = propertyChangeSupport;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public ModificarBDListener getListener() {
        return listener;
    }

    public void setListener(ModificarBDListener listener) {
        this.listener = listener;
    }
    
    public int size() {
        return this.alumnos.size();
    }
    
    private void recargarFilasBD() throws ClassNotFoundException {
        
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/alumnos?useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select * from alumnos");
            
            while (rs.next()) {
                Alumno a = new Alumno(
                        rs.getString("DNI"),
                        rs.getString("Nombre"),
                        rs.getString("Apellidos"),
                        rs.getString("Direccion"),
                        rs.getDate("FechaNac"));
                
                alumnos.add(a);
            }
            
            Alumno a = new Alumno();
            a = (Alumno) alumnos.elementAt(1);
            this.dni = a.dni;
            this.nombre = a.nombre;
            this.apellidos = a.apellidos;
            this.direccion = a.direccion;
            this.fechaNacimiento = a.fechaNacimiento;
            
            rs.close();
            stm.close();
            conn.close();
            
        } catch (SQLException e) {
            this.dni = "";
            this.nombre = "";
            this.apellidos = "";
            this.direccion = "";
            this.fechaNacimiento = null;
        }
        
    }

    public void seleccionarFilasBD(int i) {
        if (i <= this.size()) {
            Alumno a = new Alumno();
            a = (Alumno) alumnos.elementAt(i);
            this.dni = a.dni;
            this.nombre = a.nombre;
            this.apellidos = a.apellidos;
            this.direccion = a.direccion;
            this.fechaNacimiento = a.fechaNacimiento;
        } else {
            this.dni = "";
            this.nombre = "";
            this.apellidos = "";
            this.direccion = "";
            this.fechaNacimiento = null;
        }
    }
    
    public void seleccionarFilasDniBD(String dni) {
        Alumno a = new Alumno();
        int i = 0;
        
        this.dni = "";
        this.nombre = "";
        this.apellidos = "";
        this.direccion = "";
        this.fechaNacimiento = null;
        
        while (this.dni.equals("") && i <= this.size()) {
            a = (Alumno) alumnos.elementAt(i);
            
            if (a.dni.equals(dni)) {
                this.dni = a.dni;
                this.nombre = a.nombre;
                this.direccion = a.direccion;
                this.fechaNacimiento = a.fechaNacimiento;
            }
        }
    }
    
    public void addAlumno() throws ClassNotFoundException {
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/alumnos?useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement s = con.prepareStatement("insert into alumnos values (?, ?, ?, ?, ?)");
            
            s.setString(1, this.dni);
            s.setString(2, this.nombre);
            s.setString(3, this.apellidos);
            s.setString(4, this.direccion);
            s.setDate(5, (java.sql.Date) this.fechaNacimiento);
            
            s.executeUpdate();
            recargarFilasBD();
            listener.capturarModificacionBD(new ModificarBDEvent(this));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }
    
    public void addModificarBDListener(ModificarBDListener listener) {
        this.listener = listener;
    }
    
    public void removeModificarBDListener(ModificarBDListener listener) {
        this.listener = null;
    }

}
