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
                .append("\"fecha\":\"").append(formatFecha(t.getFecha())).append("\"")
                .append("},");
        }
        if (!tickets.isEmpty()) {
            json.deleteCharAt(json.length() - 1); // Eliminar última coma
        }
        json.append("]");
        return json.toString();
    }

    private String formatFecha(Object fecha) {
        try {
            if (fecha instanceof java.util.Date) {
                return DATE_FORMAT.format((java.util.Date) fecha);
            } else if (fecha instanceof java.sql.Timestamp) {
                return DATE_FORMAT.format(new java.util.Date(((java.sql.Timestamp) fecha).getTime()));
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }

    private String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\b", "\\b")
                    .replace("\f", "\\f")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }
}
