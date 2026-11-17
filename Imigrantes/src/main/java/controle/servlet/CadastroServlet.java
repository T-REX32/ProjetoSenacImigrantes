package controle.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.dao.dominio.DominioDAO;

@WebServlet("/cadastro")
public class CadastroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final DominioDAO dao = new DominioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/cadastro.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            dao.cadastrarImigrante(request.getParameter("nome"), request.getParameter("email"),
                    request.getParameter("telefone"), request.getParameter("senha"));
            response.sendRedirect(request.getContextPath() + "/login?cadastro=ok");
        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/cadastro.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Nao foi possivel criar a conta. Verifique se o e-mail ja esta cadastrado.", e);
        }
    }
}
