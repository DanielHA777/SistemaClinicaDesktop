package sistemaVila.modelo;

public class Residente {
private int id;
private String nome; 
private String idade;
private String grau;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
public String getIdade() {
	return idade;
}
public void setIdade(String string) {
	this.idade = string;
}
public String getGrau() {
	return grau;
}
public void setGrau(String grau) {
	this.grau = grau;
} 
@Override
public String toString() {	
	return this.nome;
}
}
