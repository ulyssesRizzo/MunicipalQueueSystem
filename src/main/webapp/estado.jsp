<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Estado de Tickets</title>
    <link rel="stylesheet" href="estilos.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="navbar">
        <div class="navbar-container container">
            <a href="#" class="navbar-brand">Estado de Tickets</a>
            <div class="navbar-menu">
                <a href="login.jsp"><i class="fas fa-sign-out-alt"></i> Cerrar sesión</a>
            </div>
        </div>
    </div>

    <div class="container">
        <h1><i class="fas fa-tasks"></i> Estado de Tickets</h1>
        
        <div class="card">
            <h2><i class="fas fa-user-clock"></i> En Atención</h2>
            <ul id="atendiendo" class="ticket-list"></ul>
        </div>
        
        <div class="card">
            <h2><i class="fas fa-clock"></i> Pendientes</h2>
            <ul id="pendientes" class="ticket-list"></ul>
        </div>
    </div>

    <script>
        function cargarTickets() {
            fetch("ticket-json")
                .then(res => {
                    if (!res.ok) throw new Error("Error en la respuesta del servidor");
                    return res.json();
                })
                .then(data => {
                    console.log("Tickets cargados:", data);

                    const atendiendo = document.getElementById("atendiendo");
                    const pendientes = document.getElementById("pendientes");

                    atendiendo.innerHTML = data.filter(t => t.estado === "atendiendo").length === 0 ? 
                        '<li class="empty">No hay tickets en atención</li>' : '';
                        
                    pendientes.innerHTML = data.filter(t => t.estado === "pendiente").length === 0 ? 
                        '<li class="empty">No hay tickets pendientes</li>' : '';

                    data.forEach(ticket => {
                        const li = document.createElement("li");
                        li.className = ticket.estado;
                        
                        li.innerHTML = `
                            <div class="ticket-info">
                                <span class="ticket-id">#${ticket.id}</span>
                                <span class="ticket-dep">${ticket.dependencia}</span>
                                <span class="ticket-desc">${ticket.descripcion}</span>
                            </div>
                            <span class="badge badge-${ticket.estado}">
                                ${ticket.estado}
                            </span>
                        `;

                        if (ticket.estado === "atendiendo") {
                            atendiendo.appendChild(li);
                        } else if (ticket.estado === "pendiente") {
                            pendientes.appendChild(li);
                        }
                    });
                })
                .catch(error => {
                    console.error("Error al cargar tickets:", error);
                    const container = document.querySelector(".container");
                    container.innerHTML += `
                        <div class="error-message">
                            <i class="fas fa-exclamation-triangle"></i> Error al cargar tickets. Por favor, intente nuevamente.
                        </div>
                    `;
                });
        }

        setInterval(cargarTickets, 3000);
        window.onload = cargarTickets;
    </script>
</body>
</html>