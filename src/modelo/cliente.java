/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ppbet
 */
public class cliente extends persona{
    private int id;
    private String nit;
    Conexion cn;
    public cliente(){
        
    }
    
    public cliente(int id, String nit, String nombres, String apellidos, String direccion, String telefono, String fecha_nacimiento) {
        super(nombres, apellidos, direccion, telefono, fecha_nacimiento);
        this.id = id;
        this.nit = nit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }
    
    public DefaultTableModel leer(){
        DefaultTableModel tabla = new DefaultTableModel();
        try{
            cn = new Conexion();
            cn.abrir_conexion();
            String query;
            query = "Select id_cliente as id,Nit,Nombres,Apellidos,Direccion,Telefono,Fecha_Nacimiento from clientes;";
            ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
            
            String encabezado[] = {"Id","Nit","Nombres","Apellido","Direccion","Telefono","Nacimiento"};
            tabla.setColumnIdentifiers(encabezado);
            
            String datos[] = new String[7];
            
            while(consulta.next()){
                datos[0] = consulta.getString("id");
                datos[1] = consulta.getString("Nit");
                datos[2] = consulta.getString("Nombres");
                datos[3] = consulta.getString("Apellidos");
                datos[4] = consulta.getString("Direccion");
                datos[5] = consulta.getString("Telefono");
                datos[6] = consulta.getString("Fecha_Nacimiento");
                tabla.addRow(datos);
            }
            cn.cerrar_conexion();
        }catch(SQLException ex){
            cn.cerrar_conexion();
            System.out.println("Error....... " + ex.getMessage());
        }
        return tabla;
    }
    
    @Override
    public void agregar(){
        try{
            PreparedStatement parametro;
            String query ="INSERT INTO clientes(Nit,Nombres,Apellidos,Direccion,Telefono,Fecha_Nacimiento) VALUES(?,?,?,?,?,?);";
            cn = new Conexion();
            cn.abrir_conexion();
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, this.getNit());
            parametro.setString(2, this.getNombres());
            parametro.setString(3, this.getApellidos());
            parametro.setString(4, this.getDireccion());
            parametro.setString(5, this.getTelefono());
            parametro.setString(6, this.getFecha_nacimiento());
            
            int executar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null,"Registro Ingresado: " + Integer.toString(executar),"Agregar",JOptionPane.INFORMATION_MESSAGE);
            
            
        }catch(HeadlessException | SQLException ex){
            System.out.println("Error....."+ ex.getMessage());
        }
        
    }
    
    @Override
    public void actualizar(){
        try{
            PreparedStatement parametro;
            String query ="update clientes set Nit = ?,Nombres= ?,Apellidos= ?,Direccion= ?,Telefono= ?,Fecha_Nacimiento= ? "+ "where id_cliente = ?";
            cn = new Conexion();
            cn.abrir_conexion();
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, this.getNit());
            parametro.setString(2, this.getNombres());
            parametro.setString(3, this.getApellidos());
            parametro.setString(4, this.getDireccion());
            parametro.setString(5, this.getTelefono());
            parametro.setString(6, this.getFecha_nacimiento());
            parametro.setInt(7, this.getId());
            
            int executar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null,"Registro Actualizado: " + Integer.toString(executar),"Actualizado",JOptionPane.INFORMATION_MESSAGE);
            
            
        }catch(HeadlessException | SQLException ex){
            System.out.println("Error....."+ ex.getMessage());
        }
        
        
    }
    
    @Override
    public void eliminar(){
        try{
            PreparedStatement parametro;
            String query ="delete from clientes where id_cliente = ?";
            cn = new Conexion();
            cn.abrir_conexion();
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setInt(1, this.getId());
            
            int executar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null,"Registro Eliminado: " + Integer.toString(executar),"Eliminado",JOptionPane.INFORMATION_MESSAGE);
            
            
        }catch(HeadlessException | SQLException ex){
            System.out.println("Error....."+ ex.getMessage());
        }
    }
}
