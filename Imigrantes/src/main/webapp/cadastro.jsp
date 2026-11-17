<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Criar conta | Imigrantes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
</head>
<body>
    <main class="auth-shell">
        <section class="auth-card">
            <a class="brand" href="${pageContext.request.contextPath}/">Imigrantes</a>
            <h1>Criar conta</h1>
            <p class="muted">O cadastro publico cria um perfil de imigrante.</p>
            <c:if test="${not empty erro}">
                <div class="notice error"><c:out value="${erro}"/></div>
            </c:if>
            <form action="${pageContext.request.contextPath}/cadastro" method="post">
                <div class="field">
                    <label for="nome">Nome</label>
                    <input id="nome" name="nome" type="text" maxlength="150" autocomplete="name" required>
                </div>
                <div class="field">
                    <label for="email">E-mail</label>
                    <input id="email" name="email" type="email" maxlength="255" autocomplete="email" required>
                </div>
                <div class="field">
                    <label for="telefone">Telefone</label>
                    <input id="telefone" name="telefone" type="tel" maxlength="40" autocomplete="tel">
                </div>
                <div class="field">
                    <label for="senha">Senha (minimo 8 caracteres)</label>
                    <input id="senha" name="senha" type="password" minlength="8" autocomplete="new-password" required>
                </div>
                <button type="submit">Criar conta</button>
            </form>
            <p class="auth-foot"><a href="${pageContext.request.contextPath}/login">Voltar para entrar</a></p>
        </section>
    </main>
</body>
</html>
