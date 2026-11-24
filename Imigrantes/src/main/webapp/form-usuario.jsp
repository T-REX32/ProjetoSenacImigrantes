<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Criar conta | Imigrantes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css?v=20261124-1">
</head>
<body>
    <main class="auth-shell">
        <div class="auth-layout">
            <aside class="auth-intro">
                <a class="brand" href="${pageContext.request.contextPath}/">Imigrantes</a>
                <div class="auth-intro-copy">
                    <span class="eyebrow">Um novo começo</span>
                    <h2>Conhecimento abre portas.</h2>
                    <p>Faça parte de uma comunidade que acredita no aprendizado, no acolhimento e nas oportunidades.</p>
                </div>
                <p class="auth-intro-foot">Sua jornada tem lugar por aqui.</p>
            </aside>
            <section class="auth-card">
                <h1>Crie sua conta</h1>
                <p class="muted">Leva só um instante para começar.</p>
                <c:if test="${not empty erro}"><div class="notice error" role="alert"><c:out value="${erro}"/></div></c:if>
                <form action="${pageContext.request.contextPath}/usuario/inserir" method="post">
                    <div class="field"><label for="nome">Nome completo</label><input id="nome" name="nome" maxlength="150" autocomplete="name" required></div>
                    <div class="field"><label for="email">E-mail</label><input id="email" name="email" type="email" maxlength="255" autocomplete="email" required></div>
                    <div class="field"><label for="telefone">Telefone <span class="form-hint">Opcional</span></label><input id="telefone" name="telefone" type="tel" maxlength="40" autocomplete="tel"></div>
                    <div class="field">
                        <label for="senha">Crie uma senha</label>
                        <input id="senha" name="senha" type="password" minlength="8" autocomplete="new-password" aria-describedby="senha-ajuda" required>
                        <span class="form-hint" id="senha-ajuda">Use no mínimo 8 caracteres.</span>
                    </div>
                    <button type="submit">Criar minha conta</button>
                </form>
                <p class="auth-foot">Já tem uma conta? <a href="${pageContext.request.contextPath}/login">Entrar</a></p>
            </section>
        </div>
    </main>
</body>
</html>