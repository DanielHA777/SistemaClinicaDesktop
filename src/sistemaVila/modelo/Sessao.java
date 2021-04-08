package sistemaVila.modelo;

public enum Sessao {
Motora("Motora"), 
Respirat�ria("Respirat�ria"),
Massagem("Massagem"),
RM("Motora + Respirat�ria"),
Bipap("BIPAP");

       Sessao(String string){
	this.nome = string;
}
	private String nome;
    public String toString() { // devolvendo nome 
    	return nome;
    }
    
}
