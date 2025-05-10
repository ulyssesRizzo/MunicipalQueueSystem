<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sistema de Colas - Atención</title>
    <link rel="stylesheet" href="estilos.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        /* Estilos adicionales específicos */
        .paneles-colas {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
            margin-top: 20px;
        }
        
        .panel {
            background: white;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            padding: 20px;
        }
        
        .ticket-activo {
            background-color: #e6f7ff;
            border-left: 4px solid #1890ff;
            padding: 15px;
            margin-bottom: 15px;
            border-radius: 5px;
        }
        
        .loading {
            text-align: center;
            padding: 40px;
            color: #666;
        }
        
        .error-message {
            background-color: #ffebee;
            color: #f94144;
            padding: 15px;
            border-radius: 8px;
            margin: 20px 0;
            border-left: 4px solid #f94144;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1><i class="fas fa-headset"></i> Sistema de Atención por Colas</h1>
        
        <div id="contenedor">
            <div class="loading">
                <div class="loading-spinner"></div>
                Cargando estado de colas...
            </div>
        </div>
    </div>

    <script>
// Función para formatear fechas
function formatFecha(fechaString) {
    if (!fechaString) return 'N/A';
    
    try {
        const fecha = new Date(fechaString);
        return fecha.toLocaleTimeString('es-ES', {
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit'
        });
    } catch(e) {
        return 'N/A';
    }
}

// Función para generar el HTML de un ticket pendiente
function generarTicketHTML(t, esPrimero) {
    var html = '<div style="padding: 12px; margin-bottom: 10px; background: ' + 
              (esPrimero ? '#fffbe6' : '#fff') + '; border-left: 3px solid ' + 
              (esPrimero ? '#faad14' : '#eee') + '; border-radius: 4px;">';
    
    html += '<div style="font-weight: bold; color: #333;">Ticket #' + t.id + '</div>';
    html += '<div style="margin-top: 5px;">';
    html += '<div><strong>' + (t.dependencia || 'Sin especificar') + '</strong></div>';
    html += '<div>' + (t.descripcion || 'Sin descripción') + '</div>';
    html += '</div>';
    html += '<div style="margin-top: 5px; font-size: 0.9em; color: #666;">';
    html += 'Esperando desde: ' + formatFecha(t.fecha);
    html += '</div>';
    
    if (esPrimero) {
        html += '<div style="margin-top: 5px;">';
        html += '<span class="badge" style="background: #fff7e6; color: #d48806;">Siguiente</span>';
        html += '</div>';
    }
    
    html += '</div>';
    return html;
}

// Función para actualizar la vista
function actualizarVista(data) {
    const contenedor = document.getElementById("contenedor");
    
    // Verificar si data es válido
    if (!data || !Array.isArray(data)) {
        contenedor.innerHTML = '<div class="error-message">' +
            '<i class="fas fa-exclamation-triangle"></i> Datos recibidos no válidos' +
            '<p>El formato de los datos no es el esperado</p>' +
            '</div>';
        return;
    }

    // Separar tickets
    const atendiendo = data.filter(t => t.estado === 'atendiendo');
    const pendientes = data.filter(t => t.estado === 'pendiente')
                          .sort((a, b) => new Date(a.fecha) - new Date(b.fecha));

    // Construir HTML
    let html = '<div class="paneles-colas">' +
               '<div class="panel">' +
               '<div class="panel-header" style="color: #1d39c4;">' +
               '<i class="fas fa-user-check"></i> En Atención' +
               '</div>';
    
    if (atendiendo.length > 0) {
        html += '<div class="ticket-activo">' +
                '<div style="font-size: 1.5rem; font-weight: bold; color: #1890ff;">' +
                'Ticket #' + atendiendo[0].id +
                '</div>' +
                '<div style="margin-top: 10px;">' +
                '<div><strong>' + (atendiendo[0].dependencia || 'Sin especificar') + '</strong></div>' +
                '<div>' + (atendiendo[0].descripcion || 'Sin descripción') + '</div>' +
                '<div style="margin-top: 5px;">' +
                '<span class="badge" style="background: #d6e4ff; color: #1d39c4;">Atendiendo</span>' +
                '</div>' +
                '<div style="margin-top: 5px; font-size: 0.9em; color: #666;">' +
                'Inició: ' + formatFecha(atendiendo[0].fecha) +
                '</div>' +
                '</div>' +
                '</div>';
    } else {
        html += '<div style="text-align: center; padding: 20px; color: #888;">' +
                '<i class="fas fa-user-clock"></i> Ningún ticket en atención' +
                '</div>';
    }
    
    html += '</div>' +
            '<div class="panel">' +
            '<div class="panel-header" style="color: #d48806;">' +
            '<i class="fas fa-clock"></i> Cola de Espera' +
            '</div>';
    
    if (pendientes.length > 0) {
        html += '<div>';
        for (var i = 0; i < pendientes.length; i++) {
            html += generarTicketHTML(pendientes[i], i === 0);
        }
        html += '</div>';
    } else {
        html += '<div style="text-align: center; padding: 20px; color: #888;">' +
                '<i class="fas fa-check-circle"></i> No hay tickets en espera' +
                '</div>';
    }
    
    html += '</div>' +
            '</div>';
    
    contenedor.innerHTML = html;
}

// Función para obtener datos con manejo de errores
function obtenerDatos() {
    const contenedor = document.getElementById("contenedor");
    
    fetch("ticket-json")
        .then(response => {
            if (!response.ok) {
                throw new Error('Error HTTP: ' + response.status);
            }
            return response.json();
        })
        .then(data => {
            console.log("Datos recibidos:", data);
            actualizarVista(data);
        })
        .catch(error => {
            console.error("Error:", error);
            contenedor.innerHTML = '<div class="error-message">' +
                '<i class="fas fa-exclamation-triangle"></i> Error al cargar colas' +
                '<p>' + error.message + '</p>' +
                '<button onclick="obtenerDatos()" ' +
                'style="margin-top: 10px; padding: 8px 15px; background: #1890ff; color: white; border: none; border-radius: 4px; cursor: pointer;">' +
                '<i class="fas fa-sync-alt"></i> Reintentar' +
                '</button>' +
                '</div>';
        });
}

// Iniciar carga al abrir la página y cada 5 segundos
document.addEventListener('DOMContentLoaded', function() {
    obtenerDatos();
    setInterval(obtenerDatos, 5000);
});
</script>
</body>
</html>