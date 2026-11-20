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
        String perfil = request.getParameter("perfil");
        String senha = request.getParameter("senha");
        if (senha == null || !senha.equals(request.getParameter("confirmarSenha"))) {
            request.setAttribute("erro", "As senhas informadas não são iguais.");
            request.getRequestDispatcher("/cadastro.jsp").forward(request, response);
            return;
        }
        try {
            String status = dao.cadastrarUsuario(request.getParameter("nome"), request.getParameter("cpf"),
                    request.getParameter("email"), request.getParameter("telefone"), senha, perfil,
                    request.getParameter("areaConhecimento"), request.getParameter("experiencia"),
                    request.getParameter("formacao"), request.getParameter("especialidade"),
                    request.getParameter("organizacao"), request.getParameter("tipoOrganizacao"));
            String destino;
            if ("IMIGRANTE".equals(perfil)) {
                destino = "/login?cadastro=ok";
            } else if ("ATIVO".equals(status)) {
                destino = "/login?cadastro=aprovado";
            } else {
                destino = "/login?cadastro=pendente";
            }
            response.sendRedirect(request.getContextPath() + destino);
        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/cadastro.jsp").forward(request, response);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                request.setAttribute("erro", "Este e-mail ou CPF já está cadastrado.");
                request.getRequestDispatcher("/cadastro.jsp").forward(request, response);
                return;
            }
            throw new ServletException("Não foi possível criar a conta.", e);
        }
    }
}
