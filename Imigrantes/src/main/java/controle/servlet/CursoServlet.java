package controle.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.dao.curso.CursoDAO;
import modelo.dao.curso.CursoDAOImpl;
import modelo.dao.trilha.TrilhaDAO;
import modelo.dao.trilha.TrilhaDAOImpl;
import modelo.entidade.curso.Curso;
import modelo.entidade.trilha.Trilha;

@WebServlet("/cursos/*")
public class CursoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private CursoDAO cursoDAO;
	private TrilhaDAO trilhaDAO;

	@Override
	public void init() {
		cursoDAO = new CursoDAOImpl();
		trilhaDAO = new TrilhaDAOImpl();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if ("/novo".equals(request.getPathInfo())) {
			response.sendRedirect(request.getContextPath() + "/html/cadastro-curso.html");
			return;
		}

		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!"/inserir".equals(request.getPathInfo())) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		request.setCharacterEncoding("UTF-8");
		String titulo = request.getParameter("titulo");
		String descricao = request.getParameter("descricao");
		String idTrilha = request.getParameter("trilha");
		if (titulo == null || titulo.trim().isEmpty() || idTrilha == null || idTrilha.trim().isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Informe o título e a trilha do curso.");
			return;
		}
		if (titulo.trim().length() > 150 || (descricao != null && descricao.trim().length() > 255)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"O título deve ter até 150 caracteres e a descrição até 255 caracteres.");
			return;
		}

		Long trilhaId;
		try {
			trilhaId = Long.valueOf(idTrilha.trim());
		} catch (NumberFormatException erro) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "O código da trilha deve ser numérico.");
			return;
		}

		Trilha trilha = trilhaDAO.recuperarTrilha(trilhaId);
		if (trilha == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "A trilha informada não foi encontrada.");
			return;
		}

		Curso curso = new Curso();
		curso.setTitulo(titulo.trim());
		curso.setDescricao(descricao == null || descricao.trim().isEmpty() ? null : descricao.trim());
		curso.setTrilha(trilha);
		cursoDAO.inserirCurso(curso);
		response.sendRedirect(request.getContextPath() + "/html/cadastro-curso.html?status=sucesso");
	}
}
