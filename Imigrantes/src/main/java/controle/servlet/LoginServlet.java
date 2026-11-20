package controle.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.dao.usuario.AutenticacaoDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final AutenticacaoDAO dao = new AutenticacaoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        if (email == null || senha == null) {
            request.setAttribute("erro", "Informe e-mail e senha.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        try {
            Map<String, Object> usuario = dao.autenticar(email.trim(), senha);
            if (usuario == null) {
                request.setAttribute("erro", "E-mail ou senha inválidos.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
            if ("PENDENTE".equals(usuario.get("status"))) {
                request.setAttribute("informacao",
                        "Seu cadastro está aguardando aprovação da equipe. Você receberá acesso quando ele for aprovado.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
            if (!"ATIVO".equals(usuario.get("status"))) {
                request.setAttribute("erro", "Esta conta está inativa. Entre em contato com a equipe responsável.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
            if (request.getSession(false) != null) {
                request.getSession(false).invalidate();
            }
            request.getSession(true).setAttribute("usuarioLogado", usuario);
            if ("IMIGRANTE".equals(usuario.get("perfil"))
                    && !Boolean.TRUE.equals(usuario.get("formularioPreenchido"))) {
                response.sendRedirect(request.getContextPath() + "/formulario-inicial");
            } else {
                response.sendRedirect(request.getContextPath() + "/gestao");
            }
        } catch (SQLException e) {
            throw new ServletException("Nao foi possivel iniciar a sessao.", e);
        }
    }
}
