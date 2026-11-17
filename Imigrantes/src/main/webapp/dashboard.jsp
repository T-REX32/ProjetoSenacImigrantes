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
            <span>Ola, <c:out value="${sessionScope.usuarioLogado.nome}"/></span>
            <form action="${pageContext.request.contextPath}/sair" method="post"><button class="secondary small" type="submit">Sair</button></form>
        </div>
    </header>
    <main class="page">
        <div class="page-heading">
            <div>
                <h1>Painel de gestao</h1>
                <p class="muted">Acesse os cadastros e fluxos da plataforma.</p>
            </div>
        </div>
        <div class="grid">
            <c:forEach var="modulo" items="${modulos}">
                <a class="card module-card" href="${pageContext.request.contextPath}/gestao/${modulo.codigo}">
                    <h2><c:out value="${modulo.rotulo}"/></h2>
                    <p><c:choose><c:when test="${modulo.somenteLeitura}">Acompanhar registros gerados pelo sistema</c:when><c:otherwise>Consultar e gerenciar registros</c:otherwise></c:choose></p>
                </a>
            </c:forEach>
        </div>
    </main>
</body>
</html>
