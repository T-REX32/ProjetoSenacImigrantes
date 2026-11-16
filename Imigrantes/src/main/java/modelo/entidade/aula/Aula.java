package modelo.entidade.aula;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aula")
public class Aula implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_aula")
	private Long id;

	@Column(name = "titulo", nullable = false, length = 150)
	private String titulo;

	@Column(name = "data_aula", nullable = false, length = 10)
	private String data;

	@Column(name = "curso", nullable = false, length = 150)
	private String curso;

	public Aula() {
	}

	public Aula(Long id) {
		this.id = id;
	}

	public Aula(String titulo, String data, String curso) {
		this.titulo = titulo;
		this.data = data;
		this.curso = curso;
	}

	public Aula(Long id, String titulo, String data, String curso) {
		this(titulo, data, curso);
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
