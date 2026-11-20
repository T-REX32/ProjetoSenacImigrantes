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
        <div class="auth-layout">
            <aside class="auth-intro">
                <a class="brand" href="${pageContext.request.contextPath}/">Imigrantes</a>
                <div class="auth-intro-copy">
                    <span class="eyebrow">Que bom ter você de volta</span>
                    <h2>Seu próximo capítulo começa aqui.</h2>
                    <p>Entre para continuar sua jornada de aprendizagem e encontrar novas oportunidades.</p>
                </div>
                <p class="auth-intro-foot">Aprender também é encontrar novos caminhos.</p>
            </aside>
            <section class="auth-card">
                <h1>Acesse sua conta</h1>
                <p class="muted">Informe seus dados para continuar.</p>
                <c:if test="${param.cadastro == 'ok'}">
                    <div class="notice success" role="status">Conta criada. Entre com seu e-mail e senha para responder ao formulário inicial.</div>
                </c:if>
                <c:if test="${param.cadastro == 'pendente'}">
                    <div class="notice success" role="status">Solicitação recebida. Seu perfil ficará disponível após a aprovação da equipe.</div>
                </c:if>
                <c:if test="${param.cadastro == 'aprovado'}">
                    <div class="notice success" role="status">Cadastro aprovado para o primeiro acesso de teste. Entre com seu e-mail e senha.</div>
                </c:if>
                <c:if test="${param.acesso == 'indisponivel'}">
                    <div class="notice error" role="alert">Esta conta não está ativa. Entre em contato com a equipe responsável.</div>
                </c:if>
                <c:if test="${not empty erro}">
                    <div class="notice error" role="alert"><c:out value="${erro}"/></div>
                </c:if>
                <c:if test="${not empty informacao}">
                    <div class="notice success" role="status"><c:out value="${informacao}"/></div>
                </c:if>
                <form action="${pageContext.request.contextPath}/login" method="post">
                    <div class="field">
                        <label for="email">E-mail</label>
                        <input id="email" name="email" type="email" autocomplete="username" placeholder="voce@exemplo.com" required>
                    </div>
                    <div class="field">
                        <label for="senha">Senha</label>
                        <input id="senha" name="senha" type="password" autocomplete="current-password" placeholder="Sua senha" required>
                    </div>
                    <button type="submit">Entrar na plataforma</button>
                </form>
                <p class="auth-foot">Ainda não tem conta? <a href="${pageContext.request.contextPath}/cadastro">Criar conta gratuita</a></p>
            </section>
        </div>
    </main>
</body>
</html>
