package controle.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.dao.usuario.UsuarioDAOImpl;
import modelo.entidade.usuario.Usuario;

@WebServlet(urlPatterns = {"/usuario/novo", "/usuario/inserir"})
public class UsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAOImpl dao;

    @Override
    public void init() {
        dao = new UsuarioDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        if (action.equals("/usuario/novo")) {
            request.getRequestDispatcher("/form-usuario.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");

        dao.inserir(new Usuario(null, nome, email));
        response.sendRedirect(request.getContextPath() + "/usuario/novo");
    }
}