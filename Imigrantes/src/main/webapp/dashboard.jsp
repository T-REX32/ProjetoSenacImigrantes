<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Painel | Imigrantes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
</head>
<body>
    <header class="topbar">
        <a class="brand" href="${pageContext.request.contextPath}/gestao">Imigrantes</a>
        <div class="topbar-right">
            <span>Olá, <c:out value="${sessionScope.usuarioLogado.nome}"/></span>
            <form action="${pageContext.request.contextPath}/sair" method="post"><button class="secondary small" type="submit">Sair</button></form>
        </div>
    </header>
    <main class="page">
        <div class="page-heading">
            <div>
                <span class="eyebrow">Área de <c:choose>
                    <c:when test="${sessionScope.usuarioLogado.perfil == 'IMIGRANTE'}">aprendizagem</c:when>
                    <c:when test="${sessionScope.usuarioLogado.perfil == 'PROFESSOR_FORMADO'}">educação</c:when>
                    <c:when test="${sessionScope.usuarioLogado.perfil == 'PROFESSOR_VOLUNTARIO'}">voluntariado</c:when>
                    <c:when test="${sessionScope.usuarioLogado.perfil == 'REPRESENTANTE'}">instituição parceira</c:when>
                    <c:otherwise>administração</c:otherwise>
                </c:choose></span>
                <h1>Bem-vindo, <c:out value="${sessionScope.usuarioLogado.nome}"/>.</h1>
                <p class="muted">
                    <c:choose>
                        <c:when test="${sessionScope.usuarioLogado.perfil == 'IMIGRANTE'}">Encontre trilhas e aulas disponíveis para sua jornada.</c:when>
                        <c:when test="${sessionScope.usuarioLogado.perfil == 'PROFESSOR_FORMADO'}">Apoie os educadores e organize trilhas de aprendizagem.</c:when>
                        <c:when test="${sessionScope.usuarioLogado.perfil == 'PROFESSOR_VOLUNTARIO'}">Organize suas aulas e consulte espaços disponíveis.</c:when>
                        <c:when test="${sessionScope.usuarioLogado.perfil == 'REPRESENTANTE'}">Organize os espaços e horários da sua instituição.</c:when>
                        <c:otherwise>Gerencie usuários, cadastros e atividades da plataforma.</c:otherwise>
                    </c:choose>
                </p>
            </div>
            <a class="button secondary" href="${pageContext.request.contextPath}/perfil">Meu perfil</a>
        </div>
        <c:if test="${param.boasVindas == '1'}">
            <div class="notice success" role="status">Formulário recebido. Sua área de aprendizagem está pronta.</div>
        </c:if>
        <div class="grid">
            <c:forEach var="modulo" items="${modulos}" varStatus="estado">
                <a class="card module-card" href="${pageContext.request.contextPath}/gestao/${modulo.codigo}">
                    <span class="feature-number"><c:out value="${estado.count}"/></span>
                    <h2><c:out value="${modulo.rotulo}"/></h2>
                    <p>
                        <c:choose>
                            <c:when test="${modulo.codigo == 'usuarios'}">Perfis e acesso à plataforma.</c:when>
                            <c:when test="${modulo.codigo == 'trilhas'}">Percursos e conteúdo de aprendizagem.</c:when>
                            <c:when test="${modulo.codigo == 'inscricoes'}">Participantes e andamento das trilhas.</c:when>
                            <c:when test="${modulo.codigo == 'progressos'}">Acompanhe aulas concluídas.</c:when>
                            <c:when test="${modulo.codigo == 'certificados'}">Consulte e valide certificados emitidos.</c:when>
                            <c:otherwise><c:choose><c:when test="${modulo.somenteLeitura}">Consulte os registros gerados pelo sistema.</c:when><c:otherwise>Consulte e gerencie os registros desta área.</c:otherwise></c:choose></c:otherwise>
                        </c:choose>
                    </p>
                </a>
            </c:forEach>
        </div>
    </main>
</body>
</html>
