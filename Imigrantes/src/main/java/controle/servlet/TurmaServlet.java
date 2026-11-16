package controle.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.dao.aula.AulaDAO;
import modelo.dao.aula.AulaDAOImpl;
import modelo.dao.curso.CursoDAO;
import modelo.dao.curso.CursoDAOImpl;
import modelo.dao.turma.TurmaDAO;
import modelo.dao.turma.TurmaDAOImpl;
import modelo.entidade.aula.Aula;
import modelo.entidade.curso.Curso;
import modelo.entidade.turma.Turma;

@WebServlet("/turmas/*")
public class TurmaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private AulaDAO aulaDAO;
	private CursoDAO cursoDAO;
	private TurmaDAO turmaDAO;

	@Override
	public void init() {
		aulaDAO = new AulaDAOImpl();
		cursoDAO = new CursoDAOImpl();
		turmaDAO = new TurmaDAOImpl();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "O cadastro de turma aceita apenas POST.");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!"/inserir".equals(request.getPathInfo())) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		request.setCharacterEncoding("UTF-8");
		Long idCurso;
		Long idAula;
		try {
			idCurso = Long.valueOf(request.getParameter("curso"));
			idAula = Long.valueOf(request.getParameter("aula"));
		} catch (NumberFormatException erro) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Informe IDs numéricos de curso e aula.");
			return;
		}

		Curso curso = cursoDAO.recuperarCurso(idCurso);
		Aula aula = aulaDAO.recuperarAula(idAula);
		if (curso == null || aula == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Curso ou aula não encontrados.");
			return;
		}

		Turma turma = new Turma();
		turma.setCurso(curso);
		turma.setAula(aula);
		turmaDAO.inserirTurma(turma);
		response.setStatus(HttpServletResponse.SC_CREATED);
	}
}
