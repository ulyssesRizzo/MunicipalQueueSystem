<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Panel Administrador</title>
    <link rel="stylesheet" href="estilos.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="navbar">
        <div class="navbar-container container">
            <a href="#" class="navbar-brand">Panel Administrador</a>
            <div class="navbar-menu">
                <a href="login.jsp"><i class="fas fa-sign-out-alt"></i> Cerrar sesión</a>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="card">
            <h2><i class="fas fa-ticket-alt"></i> Generar Nuevo Ticket</h2>
            
            <form action="ticket" method="post" class="form">
                <input type="hidden" name="accion" value="crear">
                
                <div class="form-group">
                    <label for="dependencia">Dependencia:</label>
                    <select name="dependencia" id="dependencia" class="form-control" required>
                        <option value="">Seleccione una dependencia</option>
                        <option value="Pago de IUSI">Pago de IUSI</option>
                        <option value="Pago de Servicio de Agua">Pago de Servicio de Agua</option>
                        <option value="Servicios de Catastro">Servicios de Catastro</option>
                        <option value="Servicios de Licencias de Construccion">Servicios de Licencias de Construcción</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="descripcion">Descripción:</label>
                    <textarea name="descripcion" id="descripcion" class="form-control" required></textarea>
                </div>
                
                <div class="form-group">
                    <button type="submit" class="btn">
                        <i class="fas fa-plus"></i> Generar Ticket
                    </button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>