package controle.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.dao.formulario.FormularioInicialDAO;

@WebServlet("/formulario-inicial")
public class FormularioInicialServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final FormularioInicialDAO dao = new FormularioInicialDAO();

    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> usuario = (Map<String, Object>) request.getSession(false)
                .getAttribute("usuarioLogado");
        try {
            Map<String, Object> dados = dao.recuperar(
                    ((Number) usuario.get("id")).longValue());
            if (dados == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            if (Boolean.TRUE.equals(dados.get("formulario_preenchido"))) {
                response.sendRedirect(request.getContextPath() + "/gestao");
                return;
            }
            request.setAttribute("dados", dados);
            request.getRequestDispatcher("/formulario-inicial.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Não foi possível carregar o formulário inicial.", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Map<String, Object> usuario = (Map<String, Object>) request.getSession(false)
                .getAttribute("usuarioLogado");
        long usuarioId = ((Number) usuario.get("id")).longValue();
        try {
            dao.salvar(usuarioId, request.getParameter("pais_origem"),
                    request.getParameter("idioma_preferido"), request.getParameter("escolaridade"),
                    request.getParameter("experiencia_profissional"),
                    request.getParameter("objetivo_profissional"));
            usuario.put("formularioPreenchido", Boolean.TRUE);
            response.sendRedirect(request.getContextPath() + "/gestao?boasVindas=1");
        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", e.getMessage());
            Map<String, Object> dados = new LinkedHashMap<>();
            dados.put("pais_origem", request.getParameter("pais_origem"));
            dados.put("idioma_preferido", request.getParameter("idioma_preferido"));
            dados.put("escolaridade", request.getParameter("escolaridade"));
            dados.put("experiencia_profissional", request.getParameter("experiencia_profissional"));
            dados.put("objetivo_profissional", request.getParameter("objetivo_profissional"));
            request.setAttribute("dados", dados);
            request.getRequestDispatcher("/formulario-inicial.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Não foi possível enviar o formulário inicial.", e);
        }
    }
}
