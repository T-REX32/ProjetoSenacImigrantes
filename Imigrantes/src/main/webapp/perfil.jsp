<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Meu perfil | Imigrantes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
</head>
<body>
    <header class="topbar">
        <a class="brand" href="${pageContext.request.contextPath}/gestao">Imigrantes</a>
        <div class="topbar-right">
            <a href="${pageContext.request.contextPath}/gestao">Painel</a>
            <form action="${pageContext.request.contextPath}/sair" method="post"><button class="secondary small" type="submit">Sair</button></form>
        </div>
    </header>
    <main class="page narrow-page">
        <div class="page-heading">
            <div>
                <span class="eyebrow">Sua conta</span>
                <h1>Meu perfil</h1>
                <p class="muted">Atualize seu nome ou altere sua senha com segurança.</p>
            </div>
        </div>
        <c:if test="${param.atualizado == '1'}">
            <div class="notice success" role="status">Perfil atualizado.</div>
        </c:if>
        <c:if test="${not empty erro}">
            <div class="notice error" role="alert"><c:out value="${erro}"/></div>
        </c:if>
        <section class="panel">
            <form action="${pageContext.request.contextPath}/perfil" method="post" id="perfil-form">
                <div class="field">
                    <label for="nome">Nome completo</label>
                    <input id="nome" name="nome" type="text" maxlength="150" autocomplete="name"
                        value="<c:out value='${perfil.nome}'/>" required>
                </div>
                <div class="form-grid">
                    <div class="field">
                        <label for="email">E-mail</label>
                        <input id="email" type="email" value="<c:out value='${perfil.email}'/>" readonly aria-describedby="dados-fixos">
                    </div>
                    <div class="field">
                        <label for="cpf">CPF</label>
                        <input id="cpf" type="text" value="<c:out value='${perfil.cpf}'/>" readonly aria-describedby="dados-fixos">
                    </div>
                </div>
                <div class="field">
                    <label for="telefone">Telefone</label>
                    <input id="telefone" type="tel" value="<c:out value='${perfil.telefone}'/>" readonly aria-describedby="dados-fixos">
                    <span class="form-hint" id="dados-fixos">E-mail e CPF não podem ser alterados. O telefone permanece bloqueado enquanto não houver verificação de contato.</span>
                </div>
                <hr>
                <h2>Alterar senha</h2>
                <p class="muted">Deixe os campos abaixo em branco para manter sua senha atual.</p>
                <div class="field">
                    <label for="senhaAtual">Senha atual</label>
                    <input id="senhaAtual" name="senhaAtual" type="password" autocomplete="current-password">
                </div>
                <div class="form-grid">
                    <div class="field">
                        <label for="novaSenha">Nova senha</label>
                        <input id="novaSenha" name="novaSenha" type="password" minlength="8" autocomplete="new-password">
                    </div>
                    <div class="field">
                        <label for="confirmarSenha">Confirme a nova senha</label>
                        <input id="confirmarSenha" name="confirmarSenha" type="password" minlength="8" autocomplete="new-password">
                    </div>
                </div>
                <div class="form-actions">
                    <button type="submit">Salvar alterações</button>
                    <a class="button secondary" href="${pageContext.request.contextPath}/gestao">Voltar ao painel</a>
                </div>
            </form>
        </section>
    </main>
    <script>
        (function () {
            var novaSenha = document.getElementById("novaSenha");
            var atual = document.getElementById("senhaAtual");
            var confirmar = document.getElementById("confirmarSenha");
            novaSenha.addEventListener("input", function () {
                var alterando = novaSenha.value.length > 0;
                atual.required = alterando;
                confirmar.required = alterando;
            });
        }());
    </script>
</body>
</html>
