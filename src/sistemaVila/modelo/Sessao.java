package sistemaVila.modelo;

public enum Sessao {
Motora("Motora"), 
Respiratória("Respiratória"),
Massagem("Massagem"),
RM("Motora + Respiratória"),
Bipap("BIPAP");

       Sessao(String string){
	this.nome = string;
}
	private String nome;
    public String toString() { // devolvendo nome 
    	return nome;
    }
    
}
