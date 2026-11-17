<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listar Cursos</title>
</head>
<body>

    <h1>Cursos</h1>

    <a href="${pageContext.request.contextPath}/curso/novo">Novo Curso</a>

    <br><br>

    <table border="1" cellpadding="6">
        <thead>
            <tr>
                <th>Id</th>
                <th>Título</th>
                <th>Descrição</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="curso" items="${cursos}">
                <tr>
                    <td><c:out value="${curso.id}" /></td>
                    <td><c:out value="${curso.titulo}" /></td>
                    <td><c:out value="${curso.descricao}" /></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/curso/editar?id=<c:out value='${curso.id}'/>">Editar</a>
                        <a href="${pageContext.request.contextPath}/curso/deletar?id=<c:out value='${curso.id}'/>">Deletar</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</body>
</html>
