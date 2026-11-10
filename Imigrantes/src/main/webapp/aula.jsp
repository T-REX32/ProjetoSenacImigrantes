<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Aula</title>
</head>
<body>

    <h1>Cadastrar Aula</h1>

    <form action="inserir-aula">

        <label>Título da aula:</label>
        <input type="text" name="titulo" required>

        <br><br>

        <label>Data da aula:</label>
        <input type="date" name="data" required>

        <br><br>

        <label>Curso:</label>
        <input type="text" name="curso" required>

        <br><br>

        <button type="submit">Cadastrar</button>

    </form>

</body>
</html>