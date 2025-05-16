package com.municipal.controller;

import com.municipal.dao.TicketDAO;
import com.municipal.model.Ticket;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@WebServlet("/ticket")
public class TicketController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String accion = request.getParameter("accion");
        
        try {
            switch (accion) {
                case "crear":
                    crearTicket(request, response);
                    break;
                case "atender":
                    atenderTicket(request, response);
                    break;
                case "cerrar":
                    cerrarTicket(request, response);
                    break;
                default:
                    response.sendRedirect("operador.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("operador.jsp?error=1");
        }
    }

    private void crearTicket(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String dependencia = request.getParameter("dependencia");
        String descripcion = request.getParameter("descripcion");
        
        Ticket nuevo = new Ticket();
        nuevo.setDependencia(dependencia);
        nuevo.setDescripcion(descripcion);
        
        TicketDAO.agregarTicket(nuevo);
        response.sendRedirect("admin.jsp?msg=creado");
    }

private void atenderTicket(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
    int id = Integer.parseInt(request.getParameter("id"));

    if (TicketDAO.cambiarEstado(id, "atendiendo")) {
        List<Ticket> tickets = TicketDAO.obtenerTodos();
        request.setAttribute("tickets", tickets);
        request.setAttribute("msg", "atendido");
        request.getRequestDispatcher("operador.jsp").forward(request, response);
    } else {
        response.sendRedirect("operador.jsp?error=atender");
    }
}

private void cerrarTicket(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
    int id = Integer.parseInt(request.getParameter("id"));

    TicketDAO.eliminarTicket(id);

    List<Ticket> tickets = TicketDAO.obtenerTodos();
    request.setAttribute("tickets", tickets);
    request.setAttribute("msg", "cerrado");
    request.getRequestDispatcher("operador.jsp").forward(request, response);
}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String vista = request.getParameter("view");
        
        try {
            List<Ticket> tickets = TicketDAO.obtenerTodos();
            request.setAttribute("tickets", tickets);
            
            if ("operador".equals(vista)) {
                request.getRequestDispatcher("operador.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("usuario.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp");
        }
    }
}