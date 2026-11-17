package controle.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.dao.aula.AulaDAOImpl;
import modelo.entidade.aula.Aula;

@WebServlet(urlPatterns = {"/nova-aula", "/editar-aula", "/inserir-aula", "/deletar-aula", "/atualizar-aula", "/listar"})
public class AulaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private AulaDAOImpl dao;

    @Override
    public void init() {
        dao = new AulaDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/nova-aula":
                    mostrarNovaAula(request, response);
                    break;
                case "/editar-aula":
                    mostrarFormularioEditarAula(request, response);
                    break;
                case "/inserir-aula":
                    inserirAula(request, response);
                    break;
                case "/deletar-aula":
                    deletarAula(request, response);
                    break;
                case "/atualizar-aula":
                    atualizarAula(request, response);
                    break;
                case "/listar":
                    listarAulas(request, response);
                    break;
                default:
                    listarAulas(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void mostrarNovaAula(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("aula.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEditarAula(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Aula aula = dao.recuperarAula(new Aula(id, null, null, null));
        request.setAttribute("aula", aula);

        RequestDispatcher dispatcher = request.getRequestDispatcher("aula.jsp");
        dispatcher.forward(request, response);
    }

    private void inserirAula(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String titulo = request.getParameter("titulo");
        String conteudo = request.getParameter("conteudo");
        Integer duracaoMinutos = Integer.parseInt(request.getParameter("duracaoMinutos"));

        dao.inserirAula(new Aula(null, titulo, conteudo, duracaoMinutos));
        response.sendRedirect("listar");
    }

    private void atualizarAula(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String titulo = request.getParameter("titulo");
        String conteudo = request.getParameter("conteudo");
        Integer duracaoMinutos = Integer.parseInt(request.getParameter("duracaoMinutos"));

        dao.atualizarAula(new Aula(id, titulo, conteudo, duracaoMinutos));
        response.sendRedirect("listar");
    }

    private void deletarAula(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Aula aula = new Aula(id, null, null, null);
        dao.deletarAula(aula);

        response.sendRedirect("listar");
    }

    private void listarAulas(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Aula> listaAulas = dao.listarAulas();
        request.setAttribute("listaAulas", listaAulas);

        RequestDispatcher dispatcher = request.getRequestDispatcher("listar-aulas.jsp");
        dispatcher.forward(request, response);
    }
}