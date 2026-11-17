<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:choose><c:when test="${not empty aula}">Editar aula</c:when><c:otherwise>Nova aula</c:otherwise></c:choose> | Imigrantes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
</head>
<body>
    <header class="topbar">
        <a class="brand" href="${pageContext.request.contextPath}/">Imigrantes</a>
        <nav class="public-nav-links" aria-label="Navegação principal">
            <a href="${pageContext.request.contextPath}/">Início</a>
            <a href="${pageContext.request.contextPath}/curso/listar">Cursos</a>
            <a href="${pageContext.request.contextPath}/listar">Aulas</a>
            <a class="button secondary small" href="${pageContext.request.contextPath}/login">Entrar</a>
        </nav>
    </header>
    <main class="page">
        <div class="page-heading">
            <div>
                <a href="${pageContext.request.contextPath}/listar">Aulas</a>
                <h1><c:choose><c:when test="${not empty aula}">Editar aula</c:when><c:otherwise>Nova aula</c:otherwise></c:choose></h1>
                <p class="muted">Preencha as informações da aula.</p>
            </div>
        </div>
        <section class="panel">
            <c:choose>
                <c:when test="${not empty aula}"><c:set var="formAction" value="/atualizar-aula"/></c:when>
                <c:otherwise><c:set var="formAction" value="/inserir-aula"/></c:otherwise>
            </c:choose>
            <form action="${pageContext.request.contextPath}${formAction}" method="post">
                <c:if test="${not empty aula}">
                    <input type="hidden" name="id" value="<c:out value='${aula.id}'/>">
                </c:if>
                <div class="form-grid">
                    <div class="field full">
                        <label for="titulo">Título</label>
                        <input id="titulo" name="titulo" type="text" maxlength="150" value="<c:out value='${aula.titulo}'/>" required>
                    </div>
                    <div class="field full">
                        <label for="conteudo">Conteúdo</label>
                        <textarea id="conteudo" name="conteudo"><c:out value="${aula.conteudo}"/></textarea>
                    </div>
                    <div class="field">
                        <label for="duracaoMinutos">Duração (minutos)</label>
                        <input id="duracaoMinutos" name="duracaoMinutos" type="number" min="1" value="<c:out value='${aula.duracaoMinutos}'/>" required>
                    </div>
                </div>
                <div class="form-actions">
                    <button type="submit"><c:choose><c:when test="${not empty aula}">Salvar alterações</c:when><c:otherwise>Cadastrar aula</c:otherwise></c:choose></button>
                    <a class="button secondary" href="${pageContext.request.contextPath}/listar">Cancelar</a>
                </div>
            </form>
        </section>
    </main>
</body>
</html>
