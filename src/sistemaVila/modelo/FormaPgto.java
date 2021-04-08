package sistemaVila.modelo;

public enum FormaPgto {
	DINHEIRO("Dinheiro"),
	CARTAO_DEB("Cart�o de d�bito"),
	CARTAO_CRED("Cart�o de Cr�dito"),
	Mensalidade("Mensalidade"),
	TRANSFERENCIA("Transfer�ncia");
		
		private String desc;
		
		FormaPgto(String desc){
			this.desc = desc;
		}
		
		public String toString(){
			return this.desc;
		}
}
