<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Iniciar sesión - Sistema de Colas</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .login-box {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px #999;
            width: 300px;
        }

        .login-box h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        .login-box input[type="text"],
        .login-box input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 8px 0;
            box-sizing: border-box;
        }

        .login-box input[type="submit"] {
            width: 100%;
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px;
            cursor: pointer;
        }

        .error {
            color: red;
            font-size: 0.9em;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="login-box">
    <h2>Iniciar Sesión</h2>

    <form action="auth" method="post">
        <input type="text" name="username" placeholder="Usuario" required />
        <input type="password" name="password" placeholder="Contraseña" required />
        <input type="submit" value="Entrar" />
    </form>

    <% if (request.getParameter("error") != null) { %>
        <p class="error">Credenciales incorrectas.</p>
    <% } %>
</div>
</body>
</html>
