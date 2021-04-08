package sistemaVila.modelo;

import java.util.List;

public class Fisio {
	private int id;
	private Sessao sessao;
	private Residente residente;
	private Colaborador colaborador;
	private List<Residente> resis;
	private String nomeCola;
	private String nomeResi;
	private String data;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Sessao getSessao() {
		return sessao;
	}
	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}
	public Residente getResidente() {
		return residente;
	}
	public void setResidente(Residente residente) {
		this.residente = residente;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public List<Residente> getResis() {
		return resis;
	}
	public void setResis(List<Residente> resis) {
		this.resis = resis;
	}
	public Colaborador getColaborador() {
		return colaborador;
	}
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}
	
	public String getNomeCola() {
		return nomeCola;
	}
	public void setNomeCola(String nomeCola) {
		this.nomeCola = nomeCola;
	}
	public String getNomeResi() {
		return nomeResi;
	}
	public void setNomeResi(String nomeResi) {
		this.nomeResi = nomeResi;
	}
}
