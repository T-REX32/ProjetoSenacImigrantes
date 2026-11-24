<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:choose><c:when test="${not empty curso}">Editar curso</c:when><c:otherwise>Novo curso</c:otherwise></c:choose> | Imigrantes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css?v=20261124-1">
</head>
<body>
    <header class="topbar">
        <a class="brand" href="${pageContext.request.contextPath}/">Imigrantes</a>
        <nav class="public-nav-links" aria-label="Navegação principal">
            <a href="${pageContext.request.contextPath}/">Início</a>
            <a href="${pageContext.request.contextPath}/curso/listar">Cursos</a>
            <a class="button secondary small" href="${pageContext.request.contextPath}/login">Entrar</a>
        </nav>
    </header>
    <main class="page">
        <div class="page-heading">
            <div>
                <a href="${pageContext.request.contextPath}/curso/listar">Cursos</a>
                <h1><c:choose><c:when test="${not empty curso}">Editar curso</c:when><c:otherwise>Novo curso</c:otherwise></c:choose></h1>
                <p class="muted">Preencha as informações do curso.</p>
            </div>
        </div>
        <section class="panel">
            <c:choose>
                <c:when test="${not empty curso}"><c:set var="formAction" value="/curso/atualizar"/></c:when>
                <c:otherwise><c:set var="formAction" value="/curso/inserir"/></c:otherwise>
            </c:choose>
            <form action="${pageContext.request.contextPath}${formAction}" method="post">
                <c:if test="${not empty curso}">
                    <input type="hidden" name="id" value="<c:out value='${curso.id}'/>">
                </c:if>
                <div class="form-grid">
                    <div class="field full">
                        <label for="titulo">Título do curso</label>
                        <input id="titulo" name="titulo" type="text" maxlength="150" value="<c:out value='${curso.titulo}'/>" required>
                    </div>
                    <div class="field full">
                        <label for="descricao">Descrição</label>
                        <textarea id="descricao" name="descricao" maxlength="255"><c:out value="${curso.descricao}"/></textarea>
                    </div>
                    <div class="field">
                        <label for="quant_turma">Quantidade de turmas</label>
                        <select id="quant_turma" name="quant_turma" required>
                            <option value="1" <c:if test="${empty curso or curso.quantTurma == 1}">selected</c:if>>1</option>
                            <option value="2" <c:if test="${curso.quantTurma == 2}">selected</c:if>>2</option>
                            <option value="3" <c:if test="${curso.quantTurma == 3}">selected</c:if>>3</option>
                            <option value="4" <c:if test="${curso.quantTurma == 4}">selected</c:if>>4</option>
                        </select>
                    </div>
                </div>
                <div class="form-actions">
                    <button type="submit"><c:choose><c:when test="${not empty curso}">Salvar alterações</c:when><c:otherwise>Cadastrar curso</c:otherwise></c:choose></button>
                    <a class="button secondary" href="${pageContext.request.contextPath}/curso/listar">Cancelar</a>
                </div>
            </form>
        </section>
    </main>
</body>
</html>
