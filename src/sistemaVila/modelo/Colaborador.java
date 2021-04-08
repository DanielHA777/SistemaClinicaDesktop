package sistemaVila.modelo;

import java.util.List;

public class Colaborador {
	private int id;
	private Especialidade espe;
	private String nome;
	private String turno;
	private String senha;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Especialidade getEspe() {
		return espe;
	}
	public void setEspe(Especialidade espe) {
		this.espe = espe;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTurno() {
		return turno;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public List<Colaborador> get(int selectedRow) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {	
		return this.nome;
	}

	

	

}
