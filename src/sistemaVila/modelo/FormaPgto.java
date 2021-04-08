package sistemaVila.modelo;

public enum FormaPgto {
	DINHEIRO("Dinheiro"),
	CARTAO_DEB("Cartão de débito"),
	CARTAO_CRED("Cartão de Crédito"),
	Mensalidade("Mensalidade"),
	TRANSFERENCIA("Transferência");
		
		private String desc;
		
		FormaPgto(String desc){
			this.desc = desc;
		}
		
		public String toString(){
			return this.desc;
		}
}
