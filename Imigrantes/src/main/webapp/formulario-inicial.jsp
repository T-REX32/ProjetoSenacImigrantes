<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Vamos conhecer você | Imigrantes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
</head>
<body>
    <header class="topbar">
        <a class="brand" href="${pageContext.request.contextPath}/">Imigrantes</a>
        <div class="topbar-right">
            <span>Olá, <c:out value="${sessionScope.usuarioLogado.nome}"/></span>
            <form action="${pageContext.request.contextPath}/sair" method="post"><button class="secondary small" type="submit">Sair</button></form>
        </div>
    </header>
    <main class="page narrow-page">
        <div class="page-heading">
            <div>
                <span class="eyebrow">Seu primeiro passo</span>
                <h1>Vamos conhecer você?</h1>
                <p class="muted">Estas informações ajudam a equipe a compreender seus objetivos e preparar sua experiência.</p>
            </div>
        </div>
        <div class="notice" role="note">O formulário é enviado uma única vez. Responda com tranquilidade; você pode deixar sua experiência profissional em branco.</div>
        <c:if test="${not empty erro}">
            <div class="notice error" role="alert"><c:out value="${erro}"/></div>
        </c:if>
        <section class="panel">
            <form action="${pageContext.request.contextPath}/formulario-inicial" method="post">
                <div class="form-grid">
                    <div class="field">
                        <label for="pais_origem">País de origem</label>
                        <input id="pais_origem" name="pais_origem" type="text" maxlength="100" autocomplete="country-name"
                            value="<c:out value='${dados.pais_origem}'/>" required>
                    </div>
                    <div class="field">
                        <label for="idioma_preferido">Idioma preferido</label>
                        <input id="idioma_preferido" name="idioma_preferido" type="text" maxlength="100"
                            value="<c:out value='${dados.idioma_preferido}'/>" required>
                    </div>
                    <div class="field full">
                        <label for="escolaridade">Escolaridade</label>
                        <select id="escolaridade" name="escolaridade" required>
                            <option value="">Selecione</option>
                            <option value="Fundamental incompleto" <c:if test="${dados.escolaridade == 'Fundamental incompleto'}">selected</c:if>>Ensino fundamental incompleto</option>
                            <option value="Fundamental completo" <c:if test="${dados.escolaridade == 'Fundamental completo'}">selected</c:if>>Ensino fundamental completo</option>
                            <option value="Médio incompleto" <c:if test="${dados.escolaridade == 'Médio incompleto'}">selected</c:if>>Ensino médio incompleto</option>
                            <option value="Médio completo" <c:if test="${dados.escolaridade == 'Médio completo'}">selected</c:if>>Ensino médio completo</option>
                            <option value="Superior incompleto" <c:if test="${dados.escolaridade == 'Superior incompleto'}">selected</c:if>>Ensino superior incompleto</option>
                            <option value="Superior completo" <c:if test="${dados.escolaridade == 'Superior completo'}">selected</c:if>>Ensino superior completo</option>
                            <option value="Outro" <c:if test="${dados.escolaridade == 'Outro'}">selected</c:if>>Outro</option>
                        </select>
                    </div>
                    <div class="field full">
                        <label for="experiencia_profissional">Experiência profissional <span class="form-hint">Opcional</span></label>
                        <textarea id="experiencia_profissional" name="experiencia_profissional" rows="4" maxlength="4000"><c:out value="${dados.experiencia_profissional}"/></textarea>
                    </div>
                    <div class="field full">
                        <label for="objetivo_profissional">O que você gostaria de aprender ou alcançar?</label>
                        <textarea id="objetivo_profissional" name="objetivo_profissional" rows="4" maxlength="4000" required><c:out value="${dados.objetivo_profissional}"/></textarea>
                    </div>
                </div>
                <div class="form-actions">
                    <button type="submit">Enviar formulário</button>
                    <a class="button secondary" href="${pageContext.request.contextPath}/perfil">Meu perfil</a>
                </div>
            </form>
        </section>
    </main>
</body>
</html>
