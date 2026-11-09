package modelo.entidade.contato;




public class Aula {

	private int id;
	private String titulo;
	private String data;
	private String curso;

	public Aula(int id, String titulo, String data, String curso) {
		setId(id);
		setTitulo(titulo);
		setData(data);
		setCurso(curso);
	}

	public Aula(String titulo, String data, String curso) {
		setTitulo(titulo);
		setData(data);
		setCurso(curso);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

}