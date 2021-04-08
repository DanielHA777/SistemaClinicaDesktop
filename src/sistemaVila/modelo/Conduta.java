package sistemaVila.modelo;

import java.util.List;

public class Conduta {
	private int id;
	private Colaboradores cola;
	private String sintomas;
	private String descricaoString;
	private String conduta;
	private Colaborador colaborador;
	private String data;
	private String nome;
	
	public String getSintomas() {
		return sintomas;
	}
	public void setSintomas(String sintomas) {
		this.sintomas = sintomas;
	}
	public String getDescricaoString() {
		return descricaoString;
	}
	public void setDescricaoString(String descricaoString) {
		this.descricaoString = descricaoString;
	}
	public String getConduta() {
		return conduta;
	}
	public void setConduta(String conduta) {
		this.conduta = conduta;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Colaboradores getCola() {
		return cola;
	}
	public void setCola(Colaboradores cola) {
		this.cola = cola;
	}
	public Colaborador getColaborador() {
		return colaborador;
	}
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
