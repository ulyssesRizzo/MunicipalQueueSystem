<script>
function cargarTickets() {
    fetch("ticket-json")
        .then(res => {
            if (!res.ok) throw new Error("Error al cargar JSON: " + res.status);
            return res.json();
        })
        .then(data => {
            console.log("TICKETS RECIBIDOS:", data); // 👈 Esto nos dirá si llegan

            const atendiendo = document.getElementById("atendiendo");
            const pendientes = document.getElementById("pendientes");
            atendiendo.innerHTML = "";
            pendientes.innerHTML = "";

            data.forEach(t => {
                const li = document.createElement("li");
                li.textContent = `${t.dependencia} - ${t.id} (${t.estado})`;
                if (t.estado === "atendiendo") {
                    atendiendo.appendChild(li);
                } else if (t.estado === "pendiente") {
                    pendientes.appendChild(li);
                }
            });
        })
        .catch(error => {
            console.error("Error al procesar tickets:", error);
        })
}

setInterval(cargarTickets, 3000);
window.onload = cargarTickets;
</script>
