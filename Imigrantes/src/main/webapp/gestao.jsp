<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${modulo.rotulo}"/> | Imigrantes</title>
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
                <a href="${pageContext.request.contextPath}/gestao">Painel</a>
                <span class="eyebrow"><c:choose>
                    <c:when test="${sessionScope.usuarioLogado.perfil == 'IMIGRANTE'}">Minha aprendizagem</c:when>
                    <c:when test="${sessionScope.usuarioLogado.perfil == 'PROFESSOR_FORMADO'}">Coordenação educacional</c:when>
                    <c:when test="${sessionScope.usuarioLogado.perfil == 'PROFESSOR_VOLUNTARIO'}">Meu voluntariado</c:when>
                    <c:when test="${sessionScope.usuarioLogado.perfil == 'REPRESENTANTE'}">Minha instituição</c:when>
                    <c:otherwise>Área administrativa</c:otherwise>
                </c:choose></span>
                <h1><c:out value="${modulo.rotulo}"/></h1>
                <p class="muted"><c:choose>
                    <c:when test="${sessionScope.usuarioLogado.perfil == 'IMIGRANTE'}">Conteúdos disponíveis e seu andamento nesta área.</c:when>
                    <c:when test="${sessionScope.usuarioLogado.perfil == 'PROFESSOR_VOLUNTARIO'}">Gerencie apenas as aulas atribuídas à sua conta.</c:when>
                    <c:when test="${sessionScope.usuarioLogado.perfil == 'REPRESENTANTE'}">Gerencie os espaços e horários da sua instituição.</c:when>
                    <c:otherwise>Consulte e mantenha as informações desta área.</c:otherwise>
                </c:choose></p>
            </div>
            <c:if test="${podeEditar and not modoFormulario}">
                <a class="button" href="${pageContext.request.contextPath}/gestao/${modulo.codigo}?acao=novo">Novo registro</a>
            </c:if>
        </div>

        <c:if test="${not empty erro}">
            <div class="notice error" role="alert"><c:out value="${erro}"/></div>
        </c:if>

        <c:if test="${modoFormulario}">
            <section class="panel">
                <h2><c:choose><c:when test="${not empty registro}">Editar registro</c:when><c:otherwise>Novo registro</c:otherwise></c:choose></h2>
                <form action="${pageContext.request.contextPath}/gestao/${modulo.codigo}" method="post">
                    <input type="hidden" name="acao" value="salvar">
                    <c:if test="${not empty registro}">
                        <input type="hidden" name="id" value="<c:out value='${registro[modulo.chave]}'/>">
                    </c:if>
                    <div class="form-grid">
                        <c:forEach var="campo" items="${modulo.campos}">
                            <c:set var="atual" value="${valores[campo.nome]}"/>
                            <c:if test="${empty valores}">
                                <c:set var="atual" value="${registro[campo.nome]}"/>
                            </c:if>
                            <c:if test="${not (sessionScope.usuarioLogado.perfil == 'PROFESSOR_VOLUNTARIO' and campo.nome == 'professor_voluntario_id') and not (sessionScope.usuarioLogado.perfil == 'REPRESENTANTE' and campo.nome == 'representante_id') and not (sessionScope.usuarioLogado.perfil == 'IMIGRANTE' and (campo.nome == 'imigrante_id' or campo.nome == 'data_inscricao'))}">
                            <div class="field ${campo.tipo == 'textarea' ? 'full' : ''} ${campo.tipo == 'checkbox' ? 'check-field' : ''}">
                                <c:choose>
                                    <c:when test="${campo.tipo == 'checkbox'}">
                                        <input id="${campo.nome}" name="${campo.nome}" type="checkbox" value="true"
                                            <c:if test="${atual == true or atual eq 'true'}">checked</c:if>>
                                        <label for="${campo.nome}"><c:out value="${campo.rotulo}"/></label>
                                    </c:when>
                                    <c:otherwise>
                                        <label for="${campo.nome}"><c:out value="${campo.rotulo}"/></label>
                                        <c:choose>
                                            <c:when test="${campo.tipo == 'textarea'}">
                                                <textarea id="${campo.nome}" name="${campo.nome}" <c:if test="${campo.obrigatorio}">required</c:if>><c:out value="${atual}"/></textarea>
                                            </c:when>
                                            <c:when test="${campo.tipo == 'select'}">
                                                <select id="${campo.nome}" name="${campo.nome}" <c:if test="${campo.obrigatorio}">required</c:if>>
                                                    <option value="">Selecione</option>
                                                    <c:choose>
                                                        <c:when test="${not empty campo.consultaOpcoes}">
                                                            <c:forEach var="opcao" items="${opcoes[campo.nome]}">
                                                                <option value="${opcao.id}" <c:if test="${atual eq opcao.id}">selected</c:if>><c:out value="${opcao.rotulo}"/></option>
                                                            </c:forEach>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach var="valor" items="${campo.valores}">
                                                                <option value="${valor}" <c:if test="${atual eq valor}">selected</c:if>><c:out value="${valor}"/></option>
                                                            </c:forEach>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </select>
                                            </c:when>
                                            <c:otherwise>
                                                <input id="${campo.nome}" name="${campo.nome}" type="${campo.tipo}"
                                                    <c:if test="${campo.tipo != 'password'}">value="<c:out value='${atual}'/>"</c:if>
                                                    <c:if test="${campo.obrigatorio and (campo.tipo != 'password' or (empty registro and empty param.id))}">required</c:if>
                                                    <c:if test="${campo.tipo == 'number' and campo.nome == 'nota'}">min="1" max="5"</c:if>
                                                    <c:if test="${campo.tipo == 'number' and campo.nome != 'nota'}">min="0"</c:if>
                                                    <c:if test="${campo.tipo == 'time'}">step="1"</c:if>>
                                                <c:if test="${campo.tipo == 'password' and not empty registro}">
                                                    <span class="hint">Deixe em branco para manter a senha atual.</span>
                                                </c:if>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            </c:if>
                        </c:forEach>
                    </div>
                    <div class="form-actions">
                        <button type="submit">Salvar</button>
                        <a class="button secondary" href="${pageContext.request.contextPath}/gestao/${modulo.codigo}">Cancelar</a>
                    </div>
                </form>
            </section>
        </c:if>

        <c:if test="${modulo.codigo == 'inscricoes' and sessionScope.usuarioLogado.perfil != 'IMIGRANTE'}">
            <section class="panel">
                <h2>Registrar aula concluída</h2>
                <p class="muted">O progresso é atualizado automaticamente. Ao concluir todas as aulas disponíveis, o certificado é emitido.</p>
                <form class="form-grid" action="${pageContext.request.contextPath}/gestao/inscricoes" method="post">
                    <input type="hidden" name="acao" value="concluir-aula">
                    <div class="field"><label for="inscricao_id">ID da inscrição</label><input id="inscricao_id" name="inscricao_id" type="number" min="1" required></div>
                    <div class="field"><label for="aula_id">ID da aula</label><input id="aula_id" name="aula_id" type="number" min="1" required></div>
                    <div class="field full"><button type="submit">Registrar conclusão</button></div>
                </form>
            </section>
        </c:if>

        <c:if test="${modulo.codigo == 'certificados'}">
            <section class="panel">
                <h2>Validar certificado</h2>
                <form class="inline-form" action="${pageContext.request.contextPath}/gestao/certificados" method="get">
                    <input type="hidden" name="acao" value="validar">
                    <input name="codigo" type="text" maxlength="36" placeholder="Código de validação" required>
                    <button type="submit">Validar</button>
                </form>
                <c:if test="${not empty param.codigo}">
                    <c:choose>
                        <c:when test="${not empty certificadoResultado}">
                            <div class="notice success" role="status">Certificado válido: <c:out value="${certificadoResultado.aluno}"/> — <c:out value="${certificadoResultado.trilha}"/>.</div>
                        </c:when>
                        <c:otherwise><div class="notice error" role="alert">Nenhum certificado encontrado para esse código.</div></c:otherwise>
                    </c:choose>
                </c:if>
            </section>
        </c:if>

        <c:if test="${not modoFormulario}">
            <section class="panel">
                <div class="table-wrap">
                    <table>
                        <thead>
                            <tr>
                                <c:forEach var="coluna" items="${modulo.colunas}"><th><c:out value="${modulo.getRotuloColuna(coluna)}"/></th></c:forEach>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="registroAtual" items="${registros}">
                                <tr>
                                    <c:forEach var="coluna" items="${modulo.colunas}">
                                        <td>
                                            <c:choose>
                                                <c:when test="${coluna == 'status'}">
                                                    <span class="status-badge status-${fn:toLowerCase(registroAtual[coluna])}"><c:out value="${registroAtual[coluna]}"/></span>
                                                </c:when>
                                                <c:when test="${coluna == 'percentual'}">
                                                    <div class="progress-cell">
                                                        <div class="progress-track" role="progressbar" aria-label="Progresso da inscrição" aria-valuemin="0" aria-valuemax="100" aria-valuenow="<c:out value='${registroAtual[coluna]}'/>">
                                                            <div class="progress-value" style="width: <c:out value='${registroAtual[coluna]}'/>%;"></div>
                                                        </div>
                                                        <span><c:out value="${registroAtual[coluna]}"/>%</span>
                                                    </div>
                                                </c:when>
                                                <c:otherwise><c:out value="${registroAtual[coluna]}"/></c:otherwise>
                                            </c:choose>
                                        </td>
                                    </c:forEach>
                                    <td class="action-cell">
                                        <c:if test="${podeEditarRegistros}">
                                            <div class="actions">
                                                <a class="button secondary small" href="${pageContext.request.contextPath}/gestao/${modulo.codigo}?acao=editar&id=${registroAtual[modulo.chave]}">Editar</a>
                                                <c:if test="${podeExcluir}">
                                                    <form class="inline-form" action="${pageContext.request.contextPath}/gestao/${modulo.codigo}" method="post" onsubmit="return confirm('Excluir este registro?');">
                                                        <input type="hidden" name="acao" value="excluir">
                                                        <input type="hidden" name="id" value="${registroAtual[modulo.chave]}">
                                                        <button class="danger small" type="submit">Excluir</button>
                                                    </form>
                                                </c:if>
                                            </div>
                                        </c:if>
                                        <c:if test="${modulo.codigo == 'inscricoes' and registroAtual.status == 'PENDENTE'}">
                                            <form action="${pageContext.request.contextPath}/gestao/inscricoes" method="post">
                                                <input type="hidden" name="acao" value="confirmar">
                                                <input type="hidden" name="id" value="${registroAtual.id}">
                                                <button class="small" type="submit">Confirmar</button>
                                            </form>
                                        </c:if>
                                        <c:if test="${modulo.codigo == 'inscricoes' and registroAtual.status == 'ATIVA'}">
                                            <form action="${pageContext.request.contextPath}/gestao/inscricoes" method="post">
                                                <input type="hidden" name="acao" value="cancelar">
                                                <input type="hidden" name="id" value="${registroAtual.id}">
                                                <button class="danger small" type="submit">Cancelar inscrição</button>
                                            </form>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty registros}">
                                <tr><td class="empty" colspan="${modulo.colunas.size() + 1}">Nenhum registro encontrado.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </section>
        </c:if>
    </main>
</body>
</html>
