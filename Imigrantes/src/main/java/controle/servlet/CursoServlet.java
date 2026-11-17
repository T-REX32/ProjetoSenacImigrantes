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

import modelo.dao.curso.CursoDAO;
import modelo.dao.curso.CursoDAOImpl;
import modelo.entidade.curso.Curso;

@WebServlet(urlPatterns = { "/curso/novo", "/curso/inserir", "/curso/deletar", "/curso/editar", "/curso/atualizar", "/curso/listar" })
public class CursoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CursoDAO cursoDAO;

    @Override
    public void init() {
        cursoDAO = new CursoDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/curso/inserir":
                    inserirCurso(request, response);
                    break;
                case "/curso/deletar":
                    deletarCurso(request, response);
                    break;
                case "/curso/atualizar":
                    atualizarCurso(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/curso/novo":
                    mostrarFormularioNovoCurso(request, response);
                    break;
                case "/curso/inserir":
                case "/curso/deletar":
                case "/curso/atualizar":
                    response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                    break;
                case "/curso/editar":
                    mostrarFormularioEditarCurso(request, response);
                    break;
                default:
                    listarCursos(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listarCursos(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Curso> cursos = cursoDAO.recuperarCursos();
        request.setAttribute("cursos", cursos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/listar-curso.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNovoCurso(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/form-curso.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEditarCurso(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Curso curso = cursoDAO.recuperarCurso(new Curso(id));
        request.setAttribute("curso", curso);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/form-curso.jsp");
        dispatcher.forward(request, response);
    }

    private void inserirCurso(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String titulo = request.getParameter("titulo");
        String descricao = request.getParameter("descricao");
        Integer quantTurma = Integer.parseInt(request.getParameter("quant_turma"));

        cursoDAO.inserirCurso(new Curso(titulo, descricao, quantTurma));
        response.sendRedirect(request.getContextPath() + "/curso/listar");
    }

    private void atualizarCurso(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        String titulo = request.getParameter("titulo");
        String descricao = request.getParameter("descricao");
        Integer quantTurma = Integer.parseInt(request.getParameter("quant_turma"));

        cursoDAO.atualizarCurso(new Curso(id, titulo, descricao, quantTurma));
        response.sendRedirect(request.getContextPath() + "/curso/listar");
    }

    private void deletarCurso(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        cursoDAO.deletarCurso(new Curso(id));
        response.sendRedirect(request.getContextPath() + "/curso/listar");
    }
}