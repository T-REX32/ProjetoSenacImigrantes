package controle.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.dao.dominio.DominioDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final DominioDAO dao = new DominioDAO();

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
                request.setAttribute("erro", "E-mail ou senha invalidos, ou usuario inativo.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
            request.getSession(true).setAttribute("usuarioLogado", usuario);
            response.sendRedirect(request.getContextPath() + "/gestao");
        } catch (SQLException e) {
            throw new ServletException("Nao foi possivel iniciar a sessao.", e);
        }
    }
}
