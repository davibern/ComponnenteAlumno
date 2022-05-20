package bean;

import entity.Notas;
import event.ModificarBDEvent;
import event.ModificarBDListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author davibern
 * @version 1.0
 */
public class NotasBean implements Serializable {
    
    private PropertyChangeSupport propertyChangeSupport;
    protected String dni;
    protected String nombreModulo;
    protected String curso;
    protected double nota;
    private final Vector notas = new Vector();
    private ModificarBDListener listener;

    public NotasBean() {
        
        propertyChangeSupport = new PropertyChangeSupport(this);
        
        try {
            recargarFilasBD();
        } catch (ClassNotFoundException e) {
            this.dni = "";
            this.nombreModulo = "";
            this.curso = "";
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

    public String getNombreModulo() {
        return nombreModulo;
    }

    public void setNombreModulo(String nombreModulo) {
        this.nombreModulo = nombreModulo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public ModificarBDListener getListener() {
        return listener;
    }

    public void setListener(ModificarBDListener listener) {
        this.listener = listener;
    }
    
    public int size() {
        return this.notas.size();
    }

    private void recargarFilasBD() throws ClassNotFoundException {
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/alumnos?useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select * from notasfinales");
            
            while (rs.next()) {
                Notas n = new Notas(
                        rs.getString("DNI"),
                        rs.getString("NombreModulo"),
                        rs.getString("Curso"),
                        rs.getDouble("Nota"));
                notas.add(n);
            }
            
            Notas n = new Notas();
            n = (Notas) notas.elementAt(1);
            this.dni = n.dni;
            this.nombreModulo = n.nombreModulo;
            this.curso = n.curso;
            this.nota = n.nota;
            
            rs.close();
            stm.close();
            conn.close();
            
        } catch (SQLException e) {
            this.dni = "";
            this.nombreModulo = "";
            this.curso = "";
            System.err.println(e.getMessage());
        }
        
    }
    
    public void agregarNotaModuloBD() throws ClassNotFoundException {
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/alumnos?useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement pstm = conn.prepareStatement("insert into notasfinales values (?, ?, ?, ?)");
            
            pstm.setString(1, this.dni);
            pstm.setString(2, this.nombreModulo);
            pstm.setString(3, this.curso);
            pstm.setDouble(4, this.nota);
            
            pstm.executeUpdate();
            this.recargarFilasBD();
            listener.capturarModificacionBD(new ModificarBDEvent(this));
            
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
    }
    
    public void seleccionarFilasBD(int i) {
        if (i <= notas.size()) {
            Notas n = new Notas();
            n = (Notas) notas.elementAt(i);
            this.dni = n.dni;
            this.nombreModulo = n.nombreModulo;
            this.curso = n.curso;
            this.nota = n.nota;
        } else {
            this.dni = "";
            this.nombreModulo = "";
            this.curso = "";
        }
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
    
    public void addModificadaListener(ModificarBDListener receptor) {
        this.listener = receptor;
    }
    
    public void removeBDModificadaListener(ModificarBDListener receptor) {
        this.listener = null;
    }
    
}
