package controle.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controle.seguranca.PermissaoUsuario;
import modelo.dao.dominio.CampoDominio;
import modelo.dao.dominio.ModuloDominio;
import modelo.dao.gestao.GestaoDAO;

@WebServlet(urlPatterns = {"/gestao", "/gestao/*"})
public class GestaoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final GestaoDAO dao = new GestaoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String codigo = codigoModulo(request);
        @SuppressWarnings("unchecked")
        Map<String, Object> usuario = (Map<String, Object>) request.getSession(false)
                .getAttribute("usuarioLogado");
        String perfilUsuario = (String) usuario.get("perfil");
        if (codigo == null) {
            request.setAttribute("modulos",
                    PermissaoUsuario.filtrarModulos(perfilUsuario, dao.listarModulos()));
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
            return;
        }

        ModuloDominio modulo = dao.buscarModulo(codigo);
        if (modulo == null || !PermissaoUsuario.podeAcessarModulo(perfilUsuario, codigo)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String acao = request.getParameter("acao");
        try {
            if ("editar".equals(acao)) {
                long registroId = id(request);
                if (!PermissaoUsuario.podeEditarModulo(perfilUsuario, codigo, registroId)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                Map<String, Object> registro = dao.recuperar(modulo, registroId);
                if (registro == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                request.setAttribute("registro", registro);
                request.setAttribute("modoFormulario", Boolean.TRUE);
            } else if ("novo".equals(acao)) {
                if (!PermissaoUsuario.podeEditarModulo(perfilUsuario, codigo, 0)
                        || "usuarios".equals(codigo)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                request.setAttribute("modoFormulario", Boolean.TRUE);
            } else if ("validar".equals(acao) && "certificados".equals(codigo)) {
                String codigoValidacao = request.getParameter("codigo");
                if (codigoValidacao != null && !codigoValidacao.trim().isEmpty()) {
                    request.setAttribute("certificadoResultado",
                            dao.validarCertificado(codigoValidacao.trim()));
                }
            }
            prepararModulo(request, modulo);
            request.getRequestDispatcher("/gestao.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", e.getMessage());
            prepararModulo(request, modulo);
            request.getRequestDispatcher("/gestao.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Nao foi possivel carregar o modulo solicitado.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String codigo = codigoModulo(request);
        ModuloDominio modulo = codigo == null ? null : dao.buscarModulo(codigo);
        @SuppressWarnings("unchecked")
        Map<String, Object> usuario = (Map<String, Object>) request.getSession(false)
                .getAttribute("usuarioLogado");
        String perfilUsuario = (String) usuario.get("perfil");
        if (modulo == null || !PermissaoUsuario.podeAcessarModulo(perfilUsuario, codigo)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {
            String acao = request.getParameter("acao");
            if ("salvar".equals(acao)) {
                long registroId = idOpcional(request);
                if (!PermissaoUsuario.podeEditarModulo(perfilUsuario, codigo, registroId)
                        || ("usuarios".equals(codigo) && registroId == 0)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                Map<String, String> valores = new LinkedHashMap<>();
                for (CampoDominio campo : modulo.getCampos()) {
                    valores.put(campo.getNome(), request.getParameter(campo.getNome()));
                }
                long usuarioId = ((Number) usuario.get("id")).longValue();
                if (registroId == 0 && "PROFESSOR_VOLUNTARIO".equals(perfilUsuario)
                        && "aulas".equals(codigo)) {
                    valores.put("professor_voluntario_id", String.valueOf(usuarioId));
                }
                if (registroId == 0 && "REPRESENTANTE".equals(perfilUsuario)
                        && "salas".equals(codigo)) {
                    valores.put("representante_id", String.valueOf(usuarioId));
                }
                if ("REPRESENTANTE".equals(perfilUsuario) && "horarios".equals(codigo)
                        && !dao.representantePossuiSala(usuarioId,
                                parseId(valores.get("sala_id")))) {
                    throw new IllegalArgumentException("Selecione uma sala disponibilizada pela sua instituição.");
                }
                dao.salvar(modulo, registroId, valores);
            } else if ("excluir".equals(acao)) {
                if ("usuarios".equals(codigo)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                exigirAdministrador(perfilUsuario, response);
                if (!"ADMINISTRADOR".equals(perfilUsuario)) {
                    return;
                }
                dao.excluir(modulo, id(request));
            } else if ("confirmar".equals(acao) && "inscricoes".equals(codigo)) {
                exigirAdministrador(perfilUsuario, response);
                if (!"ADMINISTRADOR".equals(perfilUsuario)) {
                    return;
                }
                dao.alterarStatusInscricao(id(request), "ATIVA");
            } else if ("cancelar".equals(acao) && "inscricoes".equals(codigo)) {
                exigirAdministrador(perfilUsuario, response);
                if (!"ADMINISTRADOR".equals(perfilUsuario)) {
                    return;
                }
                dao.alterarStatusInscricao(id(request), "CANCELADA");
            } else if ("concluir-aula".equals(acao) && "inscricoes".equals(codigo)) {
                exigirAdministrador(perfilUsuario, response);
                if (!"ADMINISTRADOR".equals(perfilUsuario)) {
                    return;
                }
                dao.concluirAula(numero(request, "inscricao_id"), numero(request, "aula_id"));
            } else if ("validar".equals(acao) && "certificados".equals(codigo)) {
                String codigoValidacao = request.getParameter("codigo");
                request.setAttribute("certificadoResultado",
                        codigoValidacao == null ? null : dao.validarCertificado(codigoValidacao.trim()));
                request.setAttribute("modulo", modulo);
                request.setAttribute("registros", listarParaUsuario(modulo, usuario));
                request.setAttribute("modulos",
                        PermissaoUsuario.filtrarModulos(perfilUsuario, dao.listarModulos()));
                request.getRequestDispatcher("/gestao.jsp").forward(request, response);
                return;
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acao invalida.");
                return;
            }
            response.sendRedirect(request.getContextPath() + "/gestao/" + codigo);
        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", e.getMessage());
            request.setAttribute("modoFormulario", Boolean.TRUE);
            Map<String, String> valores = new LinkedHashMap<>();
            for (CampoDominio campo : modulo.getCampos()) {
                if (!"password".equals(campo.getTipo())) {
                    valores.put(campo.getNome(), request.getParameter(campo.getNome()));
                }
            }
            request.setAttribute("valores", valores);
            String id = request.getParameter("id");
            if (id != null && !id.trim().isEmpty()) {
                Map<String, Object> registro = new LinkedHashMap<>();
                registro.put(modulo.getChave(), id);
                request.setAttribute("registro", registro);
            }
            prepararModulo(request, modulo);
            request.getRequestDispatcher("/gestao.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Nao foi possivel salvar a alteracao. Verifique os dados e os relacionamentos.", e);
        }
    }

    private void prepararModulo(HttpServletRequest request, ModuloDominio modulo)
            throws ServletException {
        request.setAttribute("modulo", modulo);
        Map<String, Object> opcoes = new LinkedHashMap<>();
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> usuario = (Map<String, Object>) request.getSession(false)
                    .getAttribute("usuarioLogado");
            request.setAttribute("registros", listarParaUsuario(modulo, usuario));
            request.setAttribute("podeEditar", PermissaoUsuario.podeEditarModulo(
                    (String) usuario.get("perfil"), modulo.getCodigo(), 0)
                    && !"usuarios".equals(modulo.getCodigo()));
            request.setAttribute("podeEditarRegistros",
                    "ADMINISTRADOR".equals(usuario.get("perfil"))
                            || ("PROFESSOR_FORMADO".equals(usuario.get("perfil"))
                                && "trilhas".equals(modulo.getCodigo())));
            request.setAttribute("podeExcluir",
                    "ADMINISTRADOR".equals(usuario.get("perfil"))
                            && !"usuarios".equals(modulo.getCodigo()));
            for (CampoDominio campo : modulo.getCampos()) {
                if (campo.getConsultaOpcoes() != null) {
                    opcoes.put(campo.getNome(), dao.listarOpcoes(campo,
                            (String) usuario.get("perfil"),
                            ((Number) usuario.get("id")).longValue()));
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Nao foi possivel carregar os dados deste modulo.", e);
        }
        request.setAttribute("opcoes", opcoes);
    }

    private List<Map<String, Object>> listarParaUsuario(ModuloDominio modulo, Map<String, Object> usuario)
            throws SQLException {
        return dao.listar(modulo, (String) usuario.get("perfil"),
                ((Number) usuario.get("id")).longValue());
    }

    private long parseId(String valor) {
        try {
            long id = Long.parseLong(valor);
            if (id <= 0) {
                throw new NumberFormatException();
            }
            return id;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Selecione um registro relacionado válido.");
        }
    }

    private void exigirAdministrador(String perfil, HttpServletResponse response) throws IOException {
        if (!"ADMINISTRADOR".equals(perfil)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private String codigoModulo(HttpServletRequest request) {
        String path = request.getPathInfo();
        if (path == null || "/".equals(path)) {
            return null;
        }
        return path.substring(1);
    }

    private long id(HttpServletRequest request) {
        return numero(request, "id");
    }

    private long idOpcional(HttpServletRequest request) {
        String valor = request.getParameter("id");
        return valor == null || valor.trim().isEmpty() ? 0 : numero(request, "id");
    }

    private long numero(HttpServletRequest request, String parametro) {
        String valor = request.getParameter(parametro);
        try {
            if (valor == null || valor.trim().isEmpty()) {
                throw new NumberFormatException();
            }
            long numero = Long.parseLong(valor);
            if (numero <= 0) {
                throw new NumberFormatException();
            }
            return numero;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Informe um identificador valido.");
        }
    }
}
