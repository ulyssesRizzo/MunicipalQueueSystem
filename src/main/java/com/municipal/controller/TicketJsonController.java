package com.municipal.controller;

import com.municipal.dao.TicketDAO;
import com.municipal.model.Ticket;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/ticket-json")
public class TicketJsonController extends HttpServlet {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        try {
            List<Ticket> tickets = TicketDAO.obtenerTodos();
            String json = convertToJson(tickets);
            response.getWriter().write(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error al generar los datos\"}");
            e.printStackTrace();
        }
    }

  private String convertToJson(List<Ticket> tickets) {
    StringBuilder json = new StringBuilder("[");
    
    for (Ticket t : tickets) {
        json.append("{")
            .append("\"id\":").append(t.getId()).append(",")
            .append("\"dependencia\":\"").append(escapeJson(t.getDependencia())).append("\",")
            .append("\"descripcion\":\"").append(escapeJson(t.getDescripcion())).append("\",")
            .append("\"estado\":\"").append(escapeJson(t.getEstado())).append("\",")
            .append("\"fecha\":\"")
            .append(formatFecha(t.getFecha()))  // Usamos el método mejorado
            .append("\"},");
    }
    
    if (!tickets.isEmpty()) {
        json.deleteCharAt(json.length() - 1); // Eliminar última coma
    }
    
    json.append("]");
    return json.toString();
}

// Método mejorado para formatear fechas
private String formatFecha(Object fecha) {
    if (fecha == null) {
        return "";
    }
    
    try {
        if (fecha instanceof java.util.Date) {
            return DATE_FORMAT.format((java.util.Date) fecha);
        } else if (fecha instanceof java.sql.Date) {
            return DATE_FORMAT.format(new java.util.Date(((java.sql.Date) fecha).getTime()));
        } else if (fecha instanceof java.sql.Timestamp) {
            return DATE_FORMAT.format(new java.util.Date(((java.sql.Timestamp) fecha).getTime()));
        } else if (fecha instanceof String) {
            // Si es un String, intentamos parsearlo
            try {
                java.util.Date parsedDate = DATE_FORMAT.parse((String) fecha);
                return DATE_FORMAT.format(parsedDate);
            } catch (Exception e) {
                return fecha.toString(); // Devuelve el string original si no se puede parsear
            }
        }
        return fecha.toString(); // Para otros tipos
    } catch (Exception e) {
        return ""; // En caso de cualquier error, retornar string vacío
    }
}
    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        
        return value.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\b", "\\b")
                   .replace("\f", "\\f")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t")
                   .replace("/", "\\/");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        try {
            String accion = request.getParameter("accion");
            int id = Integer.parseInt(request.getParameter("id"));
            
            boolean exito;
            switch(accion) {
                case "atender":
                    exito = TicketDAO.cambiarEstado(id, "atendiendo");
                    break;
                case "completar":
                    exito = TicketDAO.cambiarEstado(id, "atendido");
                    break;
                default:
                    throw new IllegalArgumentException("Acción no válida");
            }
            
            if (exito) {
                response.getWriter().write("{\"success\":true}");
            } else {
                response.getWriter().write("{\"success\":false, \"error\":\"No se pudo actualizar el ticket\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\":false, \"error\":\"" + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
    @WebServlet("/actualizar-ticket")
public class ActualizarTicketController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Verificar sesión de operador
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("operador") == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autorizado");
                return;
            }

            String accion = request.getParameter("accion");
            int id = Integer.parseInt(request.getParameter("id"));
            
            boolean exito;
            switch(accion) {
                case "atender":
                    exito = TicketDAO.cambiarEstado(id, "atendiendo");
                    break;
                case "completar":
                    exito = TicketDAO.cambiarEstado(id, "atendido");
                    break;
                case "cancelar":
                    exito = TicketDAO.cambiarEstado(id, "cancelado");
                    break;
                default:
                    throw new IllegalArgumentException("Acción no válida");
            }
            
            response.getWriter().write("{\"success\":" + exito + "}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\":false, \"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
}
