package controle.filtro;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controle.seguranca.PermissaoUsuario;
import modelo.dao.dominio.DominioDAO;

@WebFilter(urlPatterns = "/*")
public class AutenticacaoFilter implements Filter {
    private final DominioDAO dao = new DominioDAO();

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String contextPath = httpRequest.getContextPath();
        String path = httpRequest.getRequestURI().substring(contextPath.length());
        if (isPublic(path)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        Map<String, Object> usuario = session == null ? null
                : (Map<String, Object>) session.getAttribute("usuarioLogado");
        if (usuario == null) {
            httpResponse.sendRedirect(contextPath + "/login");
            return;
        }

        try {
            Map<String, Object> estado = dao.obterEstadoConta(((Number) usuario.get("id")).longValue());
            if (estado == null || !"ATIVO".equals(estado.get("status"))) {
                session.invalidate();
                httpResponse.sendRedirect(contextPath + "/login?acesso=indisponivel");
                return;
            }
            usuario.putAll(estado);
            String perfil = (String) usuario.get("perfil");

            if (isFormularioInicial(path)) {
                if (!"IMIGRANTE".equals(perfil)) {
                    httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            } else if ("IMIGRANTE".equals(perfil)
                    && !Boolean.TRUE.equals(usuario.get("formularioPreenchido"))
                    && !"/perfil".equals(path) && !"/sair".equals(path)
                    && !path.startsWith("/assets/")) {
                httpResponse.sendRedirect(contextPath + "/formulario-inicial");
                return;
            }

            if (path.startsWith("/gestao/") && path.length() > "/gestao/".length()) {
                String modulo = path.substring("/gestao/".length());
                if (!PermissaoUsuario.podeAcessarModulo(perfil, modulo)) {
                    httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            } else if ("/curso/listar".equals(path) || path.startsWith("/curso/")) {
                if (!PermissaoUsuario.podeAcessarCurso(perfil)) {
                    httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            } else if ("/listar".equals(path)) {
                if (!PermissaoUsuario.podeAcessarAulas(perfil)) {
                    httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            } else if (isGestaoAula(path)
                    && !"ADMINISTRADOR".equals(perfil)) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        } catch (SQLException e) {
            throw new ServletException("Não foi possível validar o acesso da conta.", e);
        }
        chain.doFilter(request, response);
    }

    private boolean isPublic(String path) {
        return "/".equals(path) || "/index.jsp".equals(path) || "/login".equals(path)
                || "/login.jsp".equals(path) || "/cadastro".equals(path)
                || "/cadastro.jsp".equals(path) || "/usuario/novo".equals(path)
                || "/usuario/inserir".equals(path) || path.startsWith("/assets/");
    }

    private boolean isFormularioInicial(String path) {
        return "/formulario-inicial".equals(path);
    }

    private boolean isGestaoAula(String path) {
        return "/nova-aula".equals(path) || "/editar-aula".equals(path)
                || "/inserir-aula".equals(path) || "/deletar-aula".equals(path)
                || "/atualizar-aula".equals(path);
    }

    @Override
    public void destroy() {
    }
}
