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
                    <span class="eyebrow">Uma comunidade, muitos caminhos</span>
                    <h2>Conhecimento abre portas.</h2>
                    <p>Escolha como quer participar: aprendendo, ensinando ou conectando sua instituição a novas oportunidades.</p>
                </div>
                <p class="auth-intro-foot">Cada perfil tem acessos adequados à sua atuação.</p>
            </aside>
            <section class="auth-card">
                <h1>Crie sua conta</h1>
                <p class="muted">Escolha um perfil. Cada conta tem uma única função na plataforma.</p>
                <c:if test="${not empty erro}">
                    <div class="notice error" role="alert"><c:out value="${erro}"/></div>
                </c:if>
                <form action="${pageContext.request.contextPath}/cadastro" method="post" id="cadastro-form">
                    <div class="field">
                        <label for="perfil">Como você quer participar?</label>
                        <select id="perfil" name="perfil" required>
                            <option value="IMIGRANTE" <c:if test="${empty param.perfil or param.perfil == 'IMIGRANTE'}">selected</c:if>>Imigrante — aprender e acompanhar minha jornada</option>
                            <option value="PROFESSOR_FORMADO" <c:if test="${param.perfil == 'PROFESSOR_FORMADO'}">selected</c:if>>Professor formado — criar cursos e apoiar educadores</option>
                            <option value="PROFESSOR_VOLUNTARIO" <c:if test="${param.perfil == 'PROFESSOR_VOLUNTARIO'}">selected</c:if>>Voluntário — oferecer aulas e compartilhar conhecimento</option>
                            <option value="REPRESENTANTE" <c:if test="${param.perfil == 'REPRESENTANTE'}">selected</c:if>>Representante — disponibilizar espaços da instituição ou ONG</option>
                        </select>
                        <span class="form-hint">Cadastros de professores, voluntários e representantes ficam pendentes até aprovação. Não existe cadastro público de administrador.</span>
                    </div>
                    <div class="field">
                        <label for="nome">Nome completo</label>
                        <input id="nome" name="nome" type="text" maxlength="150" autocomplete="name" placeholder="Seu nome e sobrenome" value="<c:out value='${param.nome}'/>" required>
                    </div>
                    <div class="form-grid">
                        <div class="field">
                            <label for="cpf">CPF</label>
                            <input id="cpf" name="cpf" type="text" inputmode="numeric" maxlength="14" autocomplete="off" placeholder="000.000.000-00" value="<c:out value='${param.cpf}'/>" required>
                        </div>
                        <div class="field">
                            <label for="telefone">Telefone com DDD</label>
                            <input id="telefone" name="telefone" type="tel" maxlength="40" autocomplete="tel" placeholder="(00) 00000-0000" value="<c:out value='${param.telefone}'/>" required>
                        </div>
                    </div>
                    <div class="field">
                        <label for="email">E-mail</label>
                        <input id="email" name="email" type="email" maxlength="255" autocomplete="email" placeholder="voce@exemplo.com" value="<c:out value='${param.email}'/>" required>
                    </div>

                    <fieldset class="role-details" data-role="PROFESSOR_FORMADO" hidden disabled>
                        <legend>Dados profissionais</legend>
                        <div class="field">
                            <label for="formacao">Formação</label>
                            <input id="formacao" name="formacao" type="text" maxlength="150" value="<c:out value='${param.formacao}'/>">
                        </div>
                        <div class="field">
                            <label for="especialidade">Área de especialidade</label>
                            <input id="especialidade" name="especialidade" type="text" maxlength="150" value="<c:out value='${param.especialidade}'/>">
                        </div>
                    </fieldset>
                    <fieldset class="role-details" data-role="PROFESSOR_VOLUNTARIO" hidden disabled>
                        <legend>Atuação voluntária</legend>
                        <div class="field">
                            <label for="areaConhecimento">Área de conhecimento</label>
                            <input id="areaConhecimento" name="areaConhecimento" type="text" maxlength="150" value="<c:out value='${param.areaConhecimento}'/>">
                        </div>
                        <div class="field">
                            <label for="experiencia">Experiência e competências <span class="form-hint">Opcional</span></label>
                            <textarea id="experiencia" name="experiencia" rows="3" maxlength="2000"><c:out value="${param.experiencia}"/></textarea>
                        </div>
                    </fieldset>
                    <fieldset class="role-details" data-role="REPRESENTANTE" hidden disabled>
                        <legend>Instituição ou ONG</legend>
                        <div class="field">
                            <label for="tipoOrganizacao">Tipo de organização</label>
                            <select id="tipoOrganizacao" name="tipoOrganizacao">
                                <option value="INSTITUICAO" <c:if test="${empty param.tipoOrganizacao or param.tipoOrganizacao == 'INSTITUICAO'}">selected</c:if>>Instituição</option>
                                <option value="ONG" <c:if test="${param.tipoOrganizacao == 'ONG'}">selected</c:if>>ONG</option>
                            </select>
                        </div>
                        <div class="field">
                            <label for="organizacao">Nome da instituição ou ONG</label>
                            <input id="organizacao" name="organizacao" type="text" maxlength="150" value="<c:out value='${param.organizacao}'/>">
                        </div>
                    </fieldset>

                    <div class="field">
                        <label for="senha">Crie uma senha</label>
                        <input id="senha" name="senha" type="password" minlength="8" autocomplete="new-password" aria-describedby="senha-ajuda" required>
                        <span class="form-hint" id="senha-ajuda">Use no mínimo 8 caracteres.</span>
                    </div>
                    <div class="field">
                        <label for="confirmarSenha">Confirme sua senha</label>
                        <input id="confirmarSenha" name="confirmarSenha" type="password" minlength="8" autocomplete="new-password" required>
                    </div>
                    <button type="submit">Criar minha conta</button>
                </form>
                <p class="auth-foot">Já tem uma conta? <a href="${pageContext.request.contextPath}/login">Entrar</a></p>
            </section>
        </div>
    </main>
    <script>
        (function () {
            var perfil = document.getElementById("perfil");
            var detalhes = document.querySelectorAll(".role-details");
            function atualizarDetalhes() {
                detalhes.forEach(function (grupo) {
                    var ativo = grupo.getAttribute("data-role") === perfil.value;
                    grupo.hidden = !ativo;
                    grupo.disabled = !ativo;
                    grupo.querySelectorAll("input, select, textarea").forEach(function (campo) {
                        campo.required = ativo && (campo.id === "formacao" || campo.id === "especialidade"
                                || campo.id === "areaConhecimento" || campo.id === "organizacao");
                    });
                });
            }
            perfil.addEventListener("change", atualizarDetalhes);
            atualizarDetalhes();
        }());
    </script>
</body>
</html>
