package controle.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.dao.dominio.CampoDominio;
import modelo.dao.dominio.DominioDAO;
import modelo.dao.dominio.ModuloDominio;

@WebServlet(urlPatterns = {"/gestao", "/gestao/*"})
public class GestaoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final DominioDAO dao = new DominioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String codigo = codigoModulo(request);
        if (codigo == null) {
            request.setAttribute("modulos", dao.listarModulos());
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
            return;
        }

        ModuloDominio modulo = dao.buscarModulo(codigo);
        if (modulo == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String acao = request.getParameter("acao");
        try {
            if ("editar".equals(acao)) {
                Map<String, Object> registro = dao.recuperar(modulo, id(request));
                if (registro == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                request.setAttribute("registro", registro);
                request.setAttribute("modoFormulario", Boolean.TRUE);
            } else if ("novo".equals(acao)) {
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
        if (modulo == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {
            String acao = request.getParameter("acao");
            if ("salvar".equals(acao)) {
                Map<String, String> valores = new LinkedHashMap<>();
                for (CampoDominio campo : modulo.getCampos()) {
                    valores.put(campo.getNome(), request.getParameter(campo.getNome()));
                }
                dao.salvar(modulo, idOpcional(request), valores);
            } else if ("excluir".equals(acao)) {
                dao.excluir(modulo, id(request));
            } else if ("confirmar".equals(acao) && "inscricoes".equals(codigo)) {
                dao.alterarStatusInscricao(id(request), "ATIVA");
            } else if ("cancelar".equals(acao) && "inscricoes".equals(codigo)) {
                dao.alterarStatusInscricao(id(request), "CANCELADA");
            } else if ("concluir-aula".equals(acao) && "inscricoes".equals(codigo)) {
                dao.concluirAula(numero(request, "inscricao_id"), numero(request, "aula_id"));
            } else if ("validar".equals(acao) && "certificados".equals(codigo)) {
                String codigoValidacao = request.getParameter("codigo");
                request.setAttribute("certificadoResultado",
                        codigoValidacao == null ? null : dao.validarCertificado(codigoValidacao.trim()));
                request.setAttribute("modulo", modulo);
                request.setAttribute("registros", dao.listar(modulo));
                request.setAttribute("modulos", dao.listarModulos());
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
        request.setAttribute("modulos", dao.listarModulos());
        Map<String, Object> opcoes = new LinkedHashMap<>();
        try {
            request.setAttribute("registros", dao.listar(modulo));
            for (CampoDominio campo : modulo.getCampos()) {
                if (campo.getConsultaOpcoes() != null) {
                    opcoes.put(campo.getNome(), dao.listarOpcoes(campo));
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Nao foi possivel carregar os dados deste modulo.", e);
        }
        request.setAttribute("opcoes", opcoes);
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
