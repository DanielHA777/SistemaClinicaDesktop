package sistemaVila.modelo;

import java.util.Calendar;
import java.util.List;


public class BuscaAtiva {
	private int id;
//	private Colaborador colaborador;
	private double temperatura;
	private String febre;
	private String cansaco;
	private String dorCabeca;
	private String data;
	public String getData() {
		return data;
	}
	private String tosseCoriza;
	private Colaborador colaborador;
	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}
    private String nome;
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	private String diarreia;
	private String nauseaVomito;
	private List<Colaborador> colaboradores;
	public List<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(List<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
	}

	private String dorCorpo;
	private String paladarOlfato;
	private String descricao;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public double getTemp() {
		return temperatura;
	}

	public void setTemp(double temperatura) {
		this.temperatura = temperatura;
	}

	public String getFebre() {
		return febre;
	}

	public void setFebre(String febre) {
		this.febre = febre;
	}

	public String getCansaco() {
		return cansaco;
	}

	public void setCansaco(String cansaco) {
		this.cansaco = cansaco;
	}

	public String getDorCabeca() {
		return dorCabeca;
	}

	public void setDorCabeca(String dorCabeca) {
		this.dorCabeca = dorCabeca;
	}

	public String getTosseCoriza() {
		return tosseCoriza;
	}

	public void setTosseCoriza(String tosseCoriza) {
		this.tosseCoriza = tosseCoriza;
	}

	public String getDiarreia() {
		return diarreia;
	}

	public void setDiarreia(String diarreia) {
		this.diarreia = diarreia;
	}

	public String getNauseaVomito() {
		return nauseaVomito;
	}

	public void setNauseaVomito(String nauseaVomito) {
		this.nauseaVomito = nauseaVomito;
	}

	public String getDorCorpo() {
		return dorCorpo;
	}

	public void setDorCorpo(String dorCorpo) {
		this.dorCorpo = dorCorpo;
	}

	public String getPaladarOlfato() {
		return paladarOlfato;
	}

	public void setPaladarOlfato(String paladarOlfato) {
		this.paladarOlfato = paladarOlfato;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	

	public double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(double temperatura) {
		this.temperatura = temperatura;
	}

	public void setData(String data) {
		this.data = data;
		
	}

}
