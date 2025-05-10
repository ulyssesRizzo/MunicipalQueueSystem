package com.municipal.dao;

import com.municipal.model.Ticket;
import com.municipal.controller.util.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {

    public static void agregarTicket(Ticket ticket) {
        String sql = "INSERT INTO tickets (dependencia, descripcion, estado) VALUES (?, ?, 'pendiente')";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ticket.getDependencia());
            ps.setString(2, ticket.getDescripcion());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

public static List<Ticket> obtenerTodos() {
    List<Ticket> lista = new ArrayList<>();
    String sql = "SELECT * FROM tickets ORDER BY dependencia, estado, id";
    try (Connection conn = DB.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Ticket t = new Ticket();
            t.setId(rs.getInt("id"));
            t.setDependencia(rs.getString("dependencia"));
            t.setDescripcion(rs.getString("descripcion"));
            t.setEstado(rs.getString("estado"));
            lista.add(t);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}

    public static Ticket atenderSiguiente(String dependencia) {
        String sql = "SELECT * FROM tickets WHERE dependencia = ? AND estado = 'pendiente' ORDER BY id ASC LIMIT 1";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dependencia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");

                // Actualizamos el estado a 'atendiendo'
                try (PreparedStatement update = conn.prepareStatement("UPDATE tickets SET estado = 'atendiendo' WHERE id = ?")) {
                    update.setInt(1, id);
                    update.executeUpdate();
                }

                Ticket t = new Ticket();
                t.setId(id);
                t.setDependencia(rs.getString("dependencia"));
                t.setDescripcion(rs.getString("descripcion"));
                t.setEstado("atendiendo");
                return t;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void eliminarTicket(int id) {
        String sql = "DELETE FROM tickets WHERE id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Ticket> listarAtendiendo() {
        List<Ticket> lista = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE estado = 'atendiendo'";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ticket t = new Ticket();
                t.setId(rs.getInt("id"));
                t.setDependencia(rs.getString("dependencia"));
                t.setDescripcion(rs.getString("descripcion"));
                t.setEstado("atendiendo");
                lista.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public static List<Ticket> listarPendientes() {
    List<Ticket> lista = new ArrayList<>();
    String sql = "SELECT * FROM tickets WHERE estado = 'pendiente' ORDER BY id ASC";
    try (Connection conn = DB.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Ticket t = new Ticket();
            t.setId(rs.getInt("id"));
            t.setDependencia(rs.getString("dependencia"));
            t.setDescripcion(rs.getString("descripcion"));
            t.setEstado(rs.getString("estado"));
            lista.add(t);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}

    public static boolean cambiarEstado(int id, String nuevoEstado) {
        String sql = "UPDATE tickets SET estado = ? WHERE id = ?";
        
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, id);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}

