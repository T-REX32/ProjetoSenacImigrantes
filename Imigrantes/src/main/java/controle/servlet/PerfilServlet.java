package controle.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.dao.usuario.PerfilDAO;

@WebServlet("/perfil")
public class PerfilServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final PerfilDAO dao = new PerfilDAO();

    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> usuario = (Map<String, Object>) request.getSession(false)
                .getAttribute("usuarioLogado");
        try {
            request.setAttribute("perfil", dao.recuperarPerfil(((Number) usuario.get("id")).longValue()));
            request.getRequestDispatcher("/perfil.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Não foi possível carregar o perfil.", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Map<String, Object> usuario = (Map<String, Object>) request.getSession(false)
                .getAttribute("usuarioLogado");
        long usuarioId = ((Number) usuario.get("id")).longValue();
        try {
            dao.atualizarPerfil(usuarioId, request.getParameter("nome"),
                    request.getParameter("senhaAtual"), request.getParameter("novaSenha"),
                    request.getParameter("confirmarSenha"));
            usuario.put("nome", request.getParameter("nome").trim());
            response.sendRedirect(request.getContextPath() + "/perfil?atualizado=1");
        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", e.getMessage());
            try {
                request.setAttribute("perfil", dao.recuperarPerfil(usuarioId));
            } catch (SQLException consultaErro) {
                e.addSuppressed(consultaErro);
                throw new ServletException("Não foi possível recarregar o perfil.", e);
            }
            request.getRequestDispatcher("/perfil.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Não foi possível salvar as alterações do perfil.", e);
        }
    }
}
