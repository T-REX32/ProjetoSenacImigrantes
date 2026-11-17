<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Aulas</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #f4f4f4; }
        a.btn { padding: 6px 12px; text-decoration: none; color: white; border-radius: 4px; font-size: 14px; }
        .btn-novo { background-color: #28a745; margin-bottom: 15px; display: inline-block; }
        .btn-editar { background-color: #ffc107; color: black; }
        .btn-deletar { background-color: #dc3545; }
        a.btn:hover { opacity: 0.8; }
    </style>
</head>
<body>

    <h2>Lista de Aulas Cadastradas</h2>

    <a href="${pageContext.request.contextPath}/nova-aula" class="btn btn-novo">+ Adicionar Nova Aula</a>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Título</th>
                <th>Conteúdo</th>
                <th>Duração (min)</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="aula" items="${listaAulas}">
                <tr>
                    <td>${aula.id}</td>
                    <td>${aula.titulo}</td>
                    <td>${aula.conteudo}</td>
                    <td>${aula.duracaoMinutos}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/editar-aula?id=${aula.id}" class="btn btn-editar">Editar</a>
                        <a href="${pageContext.request.contextPath}/deletar-aula?id=${aula.id}" class="btn btn-deletar" onclick="return confirm('Tem certeza que deseja excluir esta aula?');">Excluir</a>
                    </td>
                </tr>
            </c:forEach>
            
            <c:if test="${empty listaAulas}">
                <tr>
                    <td colspan="5" style="text-align: center;">Nenhuma aula cadastrada.</td>
                </tr>
            </c:if>
        </tbody>
    </table>

</body>
</html>