<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Usuário</title>
</head>
<body>

    <h1>Formulário de Cadastro de Usuário</h1>

    <form action="${pageContext.request.contextPath}/usuario/inserir" method="POST">
        
        <label>Nome:</label><br>
        <input type="text" name="nome" required><br><br>

        <label>E-mail:</label><br>
        <input type="text" name="email" required><br><br>

        <button type="submit">Salvar</button>

    </form>

</body>
</html>