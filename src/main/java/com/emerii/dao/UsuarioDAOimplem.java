package com.emerii.dao;

import com.emerii.modelo.Usuario;
import com.emerii.utiles.ConexionBD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UsuarioDAOimplem extends ConexionBD implements UsuarioDAO{


    @Override
    public void insert(Usuario usuario) throws Exception {
        String sql = "insert into usuarios (nombre,correo,clave) values (?,?,?)";
        this.conectar();
        PreparedStatement ps = this.conn.prepareStatement(sql);
        ps.setString(1, usuario.getNombre());
        ps.setString(2, usuario.getCorreo());
        ps.setString(3, usuario.getClave());
        ps.executeUpdate();
        this.desconectar();
         }

    @Override
    public void update(Usuario usuario) throws Exception {
        String sql = "update usuarios set nombre=?, correo=?,clave=? where id=?";
        this.conectar();
        PreparedStatement ps = this.conn.prepareStatement(sql);
        ps.setString(1,usuario.getNombre());
        ps.setString(2,usuario.getCorreo());
        ps.setString(3,usuario.getClave());
        ps.setInt(4, usuario.getId());
        ps.executeUpdate();
        this.desconectar();
        }

    @Override
    public void delete(int id) throws Exception {
          String sql = "delete from usuarios where id=?";
        this.conectar();
        PreparedStatement ps = this.conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        this.desconectar();
         }

    @Override
    public List<Usuario> getAll() throws Exception {
        List<Usuario> lista = null;
        String sql = "select * from usuarios";
        this.conectar();
        PreparedStatement ps = this.conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        lista = new ArrayList<Usuario>();
        while(rs.next()){
            Usuario usu = new Usuario();
            
            usu.setId(rs.getInt("id"));
            usu.setNombre(rs.getString("nombre"));
            usu.setCorreo(rs.getString("correo"));
            usu.setClave(rs.getString("clave"));
            
            lista.add(usu);
        }
        this.desconectar();
        return lista;
         }

    @Override
    public Usuario getById(int id) throws Exception {
         Usuario usu = new Usuario();
        try {
            String sql = "select * from usuarios where id=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                usu.setId(rs.getInt("id"));
                usu.setNombre(rs.getString("nombre"));
                usu.setCorreo(rs.getString("correo"));
                usu.setClave(rs.getString("clave"));
            }
        } catch (SQLException e) {
            throw e;
        } finally{
            this.desconectar();
        }
        return usu;
         }
}
