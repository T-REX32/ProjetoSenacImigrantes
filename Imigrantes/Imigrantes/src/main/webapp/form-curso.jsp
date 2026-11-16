<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro Curso</title>
</head>
<body>

    <h1>Cadastro de Cursos</h1>

    <c:if test="${curso != null}">
        <form action="${pageContext.request.contextPath}/curso/atualizar" method="post">
            <input type="hidden" name="id" value="<c:out value='${curso.id}' />">
    </c:if>
    <c:if test="${curso == null}">
        <form action="${pageContext.request.contextPath}/curso/inserir" method="post">
    </c:if>

        <label>Titulo do Curso</label>
        <input type="text" name="titulo" value="<c:out value='${curso.titulo}' />" required>

        <br><br>

        <label>Descrição</label>
        <input type="text" name="descricao" value="<c:out value='${curso.descricao}' />">

        <br><br>

        <label>Quantidade de Turmas</label>
        <select name="quant_turma">
            <option value="1" ${curso.quantTurma == 1 ? 'selected' : ''}>1</option>
            <option value="2" ${curso.quantTurma == 2 ? 'selected' : ''}>2</option>
            <option value="3" ${curso.quantTurma == 3 ? 'selected' : ''}>3</option>
            <option value="4" ${curso.quantTurma == 4 ? 'selected' : ''}>4</option>
        </select>

        <br><br>

        <button type="submit">Cadastrar</button>
        <a href="${pageContext.request.contextPath}/curso/listar">Voltar</a>

    </form>

</body>
</html>