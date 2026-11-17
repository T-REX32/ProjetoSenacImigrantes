<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Aulas | Imigrantes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
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
                <span class="eyebrow">Aprendizado</span>
                <h1>Aulas</h1>
                <p class="muted">Consulte e gerencie as aulas cadastradas.</p>
            </div>
            <a class="button" href="${pageContext.request.contextPath}/nova-aula">Nova aula</a>
        </div>
        <section class="panel">
            <div class="table-wrap">
                <table>
                    <thead>
                        <tr><th>ID</th><th>Título</th><th>Conteúdo</th><th>Duração</th><th>Ações</th></tr>
                    </thead>
                    <tbody>
                        <c:forEach var="aula" items="${listaAulas}">
                            <tr>
                                <td><c:out value="${aula.id}"/></td>
                                <td><c:out value="${aula.titulo}"/></td>
                                <td><c:out value="${aula.conteudo}"/></td>
                                <td><c:out value="${aula.duracaoMinutos}"/> min</td>
                                <td class="action-cell">
                                    <div class="actions">
                                        <a class="button secondary small" href="${pageContext.request.contextPath}/editar-aula?id=${aula.id}">Editar</a>
                                        <form class="inline-form" action="${pageContext.request.contextPath}/deletar-aula" method="post" onsubmit="return confirm('Excluir esta aula?');">
                                            <input type="hidden" name="id" value="<c:out value='${aula.id}'/>">
                                            <button class="danger small" type="submit">Excluir</button>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty listaAulas}">
                            <tr><td class="empty" colspan="5">Ainda não há aulas cadastradas.</td></tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </section>
    </main>
</body>
</html>
