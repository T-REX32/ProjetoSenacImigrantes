package controle.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.dao.aula.AulaDAO;

@WebServlet(urlPatterns = {"/nova-aula"})
public class AulaServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private AulaDAO dao;

	public void init() {
//		dao = new ContatoDAOImpl();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath();

		try {
			
			switch (action) {
			
			case "/nova-aula":
				mostrarNovaAula(request, response);
				break;
				
			case "/inserir-aula":
				inserirAula(request, response);
				break;
				
			case "/deletar-aula":
				deletarAula(request, response);
				break;
				
			case "/editar-aula":
				mostrarFormularioEditarAula(request, response);
				break;
				
			case "/atualizar-aula":
				atualizarAula(request, response);
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
		
		long id = Long.parseLong(request.getParameter("id"));
//		Contato contato = dao.recuperarAula(new Aula(id));
//		request.setAttribute("aula", contato);
		RequestDispatcher dispatcher = request.getRequestDispatcher("form-contato.jsp");
		dispatcher.forward(request, response);
	}

	private void inserirAula(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		
		String titulo = request.getParameter("titulo");
		String data = request.getParameter("data");
		String curso = request.getParameter("curso");
		dao.inserirAula(new Contato(titulo, data));
		response.sendRedirect("listar");
	}

	private void atualizarAula(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		
		long id = Long.parseLong(request.getParameter("id"));
		String telefone = request.getParameter("titulo");
		String email = request.getParameter("data");
//		dao.atualizarAula(new Contato(id,titulo, data));
		response.sendRedirect("listar");
	}

	private void deletarAula(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		
		long id = Long.parseLong(request.getParameter("id"));
//		Contato contato = dao.recuperarContato(new Contato(id));
//		dao.deletarAula(contato);
		response.sendRedirect("listar");
		
	}
}