<%@ page import="java.util.*,com.municipal.model.Ticket" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Panel Operador</title>
    <link rel="stylesheet" href="estilos.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .btn-atender {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
        }
        .btn-cerrar {
            background-color: #dc3545;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
        }
        .action-buttons {
            display: flex;
            gap: 5px;
        }
        .error-message {
            color: #dc3545;
            font-weight: bold;
            margin: 10px 0;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <div class="navbar-container container">
            <a href="#" class="navbar-brand">Panel Operador</a>
            <div class="navbar-menu">
                <a href="login.jsp"><i class="fas fa-sign-out-alt"></i> Cerrar sesión</a>
            </div>
        </div>
    </div>
  
    <div class="container">
        <h1>Tickets Pendientes</h1>
        
        <%-- Mensajes de estado --%>
        <% String msg = request.getParameter("msg"); %>
        <% String error = request.getParameter("error"); %>
        
        <% if ("atendido".equals(msg)) { %>
            <p style="color: green; font-weight: bold;">✔ Ticket marcado como atendido con éxito.</p>
        <% } else if ("cerrado".equals(msg)) { %>
            <p style="color: green; font-weight: bold;">✔ Ticket cerrado con éxito.</p>
        <% } else if (error != null) { %>
            <p class="error-message">✖ Error al procesar la solicitud. Intente nuevamente.</p>
        <% } %>

        <div class="card">
            <table class="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Dependencia</th>
                        <th>Descripción</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        List<Ticket> tickets = (List<Ticket>) request.getAttribute("tickets");
                        if (tickets != null && !tickets.isEmpty()) {
                            for (Ticket t : tickets) {
                                String estado = t.getEstado().toLowerCase();
                                String estadoClass = "badge-" + estado.replace(" ", "-");
                    %>
                    <tr>
                        <td>#<%= t.getId() %></td>
                        <td><%= t.getDependencia() %></td>
                        <td><%= t.getDescripcion() %></td>
                        <td><span class="badge <%= estadoClass %>"><%= estado %></span></td>
                        <td>
                            <div class="action-buttons">
                                <% if ("pendiente".equals(estado)) { %>
                                <form action="ticket" method="post">
                                    <input type="hidden" name="accion" value="atender">
                                    <input type="hidden" name="id" value="<%= t.getId() %>">
                                    <button type="submit" class="btn-atender">
                                        <i class="fas fa-play"></i> Atender
                                    </button>
                                </form>
                                <% } else if ("atendiendo".equals(estado)) { %>
                                <form action="ticket" method="post">
                                    <input type="hidden" name="accion" value="cerrar">
                                    <input type="hidden" name="id" value="<%= t.getId() %>">
                                    <button type="submit" class="btn-cerrar">
                                        <i class="fas fa-stop"></i> Cerrar
                                    </button>
                                </form>
                                <% } %>
                            </div>
                        </td>
                    </tr>
                    <% 
                            }
                        } else { 
                    %>
                    <tr>
                        <td colspan="5" style="text-align: center;">No hay tickets pendientes</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>