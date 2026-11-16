<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Aula</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input[type="text"], input[type="number"], textarea { width: 100%; max-width: 400px; padding: 8px; box-sizing: border-box; }
        textarea { height: 100px; }
        button { padding: 10px 15px; background-color: #007BFF; color: white; border: none; cursor: pointer; }
        button:hover { background-color: #0056b3; }
        a { margin-left: 10px; text-decoration: none; color: #555; }
    </style>
</head>
<body>

    <% 
        boolean editando = request.getAttribute("aula") != null; 
    %>

    <h2><%= editando ? "Editar Aula" : "Nova Aula" %></h2>

    <form action="${pageContext.request.contextPath}/<%= editando ? "atualizar-aula" : "inserir-aula" %>" method="POST">

        <% if (editando) { %>
            <input type="hidden" name="id" value="${aula.id}">
        <% } %>

        <div class="form-group">
            <label for="titulo">Título:</label>
            <input type="text" id="titulo" name="titulo" value="${aula.titulo}" required>
        </div>

        <div class="form-group">
            <label for="conteudo">Conteúdo:</label>
            <textarea id="conteudo" name="conteudo" required>${aula.conteudo}</textarea>
        </div>

        <div class="form-group">
            <label for="duracaoMinutos">Duração (Minutos):</label>
            <input type="number" id="duracaoMinutos" name="duracaoMinutos" value="${aula.duracaoMinutos}" required>
        </div>

        <button type="submit"><%= editando ? "Salvar Alterações" : "Cadastrar Aula" %></button>
        <a href="${pageContext.request.contextPath}/listar">Cancelar</a>

    </form>

</body>
</html>