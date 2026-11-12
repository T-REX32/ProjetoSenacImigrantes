package model;

public class Turma {
    public long id;
    public Curso curso;
    public ProfessorVoluntario professorVoluntario;
    public Imigrante aluno;
    public Aula aula;

    public Turma() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public ProfessorVoluntario getProfessorVoluntario() {
        return professorVoluntario;
    }

    public void setProfessorVoluntario(ProfessorVoluntario professorVoluntario) {
        this.professorVoluntario = professorVoluntario;
    }

    public Imigrante getAluno() {
        return aluno;
    }

    public void setAluno(Imigrante aluno) {
        this.aluno = aluno;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }
}