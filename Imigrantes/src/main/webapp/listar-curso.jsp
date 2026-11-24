<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cursos | Imigrantes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css?v=20261124-1">
</head>
<body>
    <header class="topbar">
        <a class="brand" href="${pageContext.request.contextPath}/">Imigrantes</a>
        <nav class="public-nav-links" aria-label="Navegação principal">
            <a href="${pageContext.request.contextPath}/">Início</a>
            <a href="${pageContext.request.contextPath}/listar">Aulas</a>
            <a class="button secondary small" href="${pageContext.request.contextPath}/login">Entrar</a>
        </nav>
    </header>
    <main class="page">
        <div class="page-heading">
            <div>
                <span class="eyebrow">Formação</span>
                <h1>Cursos</h1>
                <p class="muted">Consulte e gerencie os cursos cadastrados.</p>
            </div>
            <c:if test="${sessionScope.usuarioLogado.perfil == 'ADMINISTRADOR' or sessionScope.usuarioLogado.perfil == 'PROFESSOR_FORMADO'}">
                <a class="button" href="${pageContext.request.contextPath}/curso/novo">Novo curso</a>
            </c:if>
        </div>
        <section class="panel">
            <div class="table-wrap">
                <table>
                    <thead>
                        <tr><th>ID</th><th>Título</th><th>Descrição</th><th>Turmas</th><th>Ações</th></tr>
                    </thead>
                    <tbody>
                        <c:forEach var="curso" items="${cursos}">
                            <tr>
                                <td><c:out value="${curso.id}"/></td>
                                <td><c:out value="${curso.titulo}"/></td>
                                <td><c:out value="${curso.descricao}"/></td>
                                <td><c:out value="${curso.quantTurma}"/></td>
                                <td class="action-cell">
                                    <c:if test="${sessionScope.usuarioLogado.perfil == 'ADMINISTRADOR' or sessionScope.usuarioLogado.perfil == 'PROFESSOR_FORMADO'}">
                                    <div class="actions">
                                        <a class="button secondary small" href="${pageContext.request.contextPath}/curso/editar?id=${curso.id}">Editar</a>
                                        <form class="inline-form" action="${pageContext.request.contextPath}/curso/deletar" method="post" onsubmit="return confirm('Excluir este curso?');">
                                            <input type="hidden" name="id" value="<c:out value='${curso.id}'/>">
                                            <button class="danger small" type="submit">Excluir</button>
                                        </form>
                                    </div>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty cursos}">
                            <tr><td class="empty" colspan="5">Ainda não há cursos cadastrados.</td></tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </section>
    </main>
</body>
</html>
