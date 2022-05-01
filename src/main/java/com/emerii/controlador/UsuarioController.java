
package com.emerii.controlador;

import com.emerii.dao.UsuarioDAO;
import com.emerii.dao.UsuarioDAOimplem;
import com.emerii.modelo.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UsuarioController", urlPatterns = {"/UsuarioController"})
public class UsuarioController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
                UsuarioDAO dao = new UsuarioDAOimplem();
        
        Usuario usu = new Usuario();
        int id;
        
        String action = (request.getParameter("action") != null) ? request.getParameter("action") : "view";
        switch (action) {
            case "add":
                request.setAttribute("usuario", usu);
                request.getRequestDispatcher("frusuario.jsp").forward(request, response);
                break;
            case "edit":
                id = Integer.parseInt(request.getParameter("id"));
                try {
                    usu = dao.getById(id);
                } catch (Exception ex) {
                    System.out.println("Eror al obtener registro " + ex.getMessage());
                }
                request.setAttribute("usuario", usu);
                request.getRequestDispatcher("frusuario.jsp").forward(request, response);
                break;
            case "delete":
                id = Integer.parseInt(request.getParameter("id"));
                try {
                    dao.delete(id);
                } catch (Exception ex) {
                    System.out.println("Erro al eliminar: " + ex.getMessage());
                }
                response.sendRedirect("UsuarioController");
                break;
            case "view":
                List<Usuario> lista = new ArrayList<Usuario>(); 
                try {
                    lista = dao.getAll();
                } catch (Exception ex) {
                    System.out.println("Error al listar "+ex.getMessage());
                }
                request.setAttribute("usuarios", lista);
                request.getRequestDispatcher("usuarios.jsp").forward(request, response);
                break;
            default:
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                int id = Integer.parseInt(request.getParameter("id"));
        String nombre =  request.getParameter("nombre");
        String correo =  request.getParameter("correo");
        String clave =  request.getParameter("clave");
        
        Usuario usu = new Usuario();
        
        usu.setId(id);
        usu.setNombre(nombre);
        usu.setCorreo(correo);
        usu.setClave(clave);
        
        UsuarioDAO dao = new UsuarioDAOimplem();
        
        if (id == 0){
            try {
                dao.insert(usu);
            } catch (Exception ex) {
                System.out.println("Error al insertar "+ ex.getMessage());
            }
        }
        else{
            try {
                dao.update(usu);
            } catch (Exception ex) {
                System.out.println("Error al editar" + ex.getMessage());
            }
        }
        response.sendRedirect("UsuarioController");

    }

}
