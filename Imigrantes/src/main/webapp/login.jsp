<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Entrar | Imigrantes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
</head>
<body>
    <main class="auth-shell">
        <section class="auth-card">
            <a class="brand" href="${pageContext.request.contextPath}/">Imigrantes</a>
            <h1>Bem-vindo</h1>
            <p class="muted">Acesse a plataforma de ensino e acolhimento.</p>
            <c:if test="${param.cadastro == 'ok'}">
                <div class="notice success">Conta criada. Entre com seu e-mail e senha.</div>
            </c:if>
            <c:if test="${not empty erro}">
                <div class="notice error"><c:out value="${erro}"/></div>
            </c:if>
            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="field">
                    <label for="email">E-mail</label>
                    <input id="email" name="email" type="email" autocomplete="username" required>
                </div>
                <div class="field">
                    <label for="senha">Senha</label>
                    <input id="senha" name="senha" type="password" autocomplete="current-password" required>
                </div>
                <button type="submit">Entrar</button>
            </form>
            <p class="auth-foot">Ainda nao tem conta? <a href="${pageContext.request.contextPath}/cadastro">Cadastre-se</a></p>
        </section>
    </main>
</body>
</html>
