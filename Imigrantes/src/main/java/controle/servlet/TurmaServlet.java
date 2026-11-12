package controle.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Turma;
import model.Curso;
import model.ProfessorVoluntario;
import model.Imigrante;
import model.Aula;

@WebServlet(urlPatterns = {"/nova-turma"})
public class TurmaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("turma.jsp")
               .forward(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // Recebendo os IDs do formulário
            Long idCurso = Long.parseLong(request.getParameter("curso"));
            Long idProfessor = Long.parseLong(request.getParameter("professor"));
            Long idAluno = Long.parseLong(request.getParameter("aluno"));
            Long idAula = Long.parseLong(request.getParameter("aula"));

            // Criando os objetos relacionados
            Curso curso = new Curso();
            curso.setId(idCurso);

            ProfessorVoluntario professor = new ProfessorVoluntario();
            professor.setId(idProfessor);

            Imigrante aluno = new Imigrante();
            aluno.setId(idAluno);

            Aula aula = new Aula();
            aula.setId(idAula);

            // Criando a turma
            Turma turma = new Turma();

            turma.setCurso(curso);
            turma.setProfessorVoluntario(professor);
            turma.setAluno(aluno);
            turma.setAula(aula);

            // Encaminhando o objeto para a próxima etapa
            request.setAttribute("turma", turma);

            request.setAttribute("mensagem",
                    "Dados da turma recebidos com sucesso!");

            request.getRequestDispatcher("turma.jsp")
                   .forward(request, response);

        } catch (NumberFormatException e) {

            request.setAttribute("erro",
                    "Verifique os IDs informados.");

            request.getRequestDispatcher("turma.jsp")
                   .forward(request, response);
        }
    }
}