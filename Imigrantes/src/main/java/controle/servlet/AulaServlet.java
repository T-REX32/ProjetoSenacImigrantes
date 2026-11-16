package controle.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.dao.aula.AulaDAO;
import modelo.dao.aula.AulaDAOImpl;
import modelo.entidade.aula.Aula;

@WebServlet("/aulas/*")
public class AulaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private AulaDAO aulaDAO;

	@Override
	public void init() {
		aulaDAO = new AulaDAOImpl();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if ("/novo".equals(request.getPathInfo())) {
			request.getRequestDispatcher("/aula.jsp").forward(request, response);
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
		String data = request.getParameter("data");
		String curso = request.getParameter("curso");
		if (estaVazio(titulo) || estaVazio(data) || estaVazio(curso)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Preencha todos os campos da aula.");
			return;
		}

		aulaDAO.inserirAula(new Aula(titulo.trim(), data.trim(), curso.trim()));

		response.sendRedirect(request.getContextPath() + "/aulas/novo?status=sucesso");
	}

	private boolean estaVazio(String valor) {
		return valor == null || valor.trim().isEmpty();
	}
}
