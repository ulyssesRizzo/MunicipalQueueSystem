<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Prueba de Tickets</title>
</head>
<body>

<h1>Tickets Pendientes</h1>
<ul id="lista"></ul>

<script>
    function cargarTickets() {
        fetch("ticket-json")
            .then(res => res.json())
            .then(data => {
                console.log("Tickets cargados:", data);

                const lista = document.getElementById("lista");
                lista.innerHTML = ""; // limpia

                data.forEach(ticket => {
                    if (ticket.estado === "pendiente") {
                        const li = document.createElement("li");
                        li.textContent = `${ticket.id} - ${ticket.dependencia} - ${ticket.descripcion}`;
                        lista.appendChild(li);
                    }
                });
            })
            .catch(error => {
                console.error("Error al cargar tickets:", error);
            });
    }

    window.onload = cargarTickets;
</script>

</body>
</html>
