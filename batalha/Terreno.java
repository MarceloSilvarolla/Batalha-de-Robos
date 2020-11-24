public class Terreno {

	int numCristais;
	Posicao pos;
	boolean temRobo;
	int tipo; /* 0 eh base, 1 eh terreno plano, 2 eh terreno rugoso, 3 sao residuos de uma base, 4 eh repositorio de cristais (maior probabilidade de ter cristais) */
	Base base; /* Base que ocupa este terreno (se existir, eh unica) */
	Robo robo; /* Robo que ocupa este terreno (se existir, eh unico) */

	public Terreno(int numCristais, int i, int j, boolean temRobo, int tipo){
		this.numCristais = numCristais;
		this.pos = new Posicao(i,j);
		this.temRobo = temRobo;
		this.tipo = tipo;
	}
	
	/* gets */
	public int numCristais() {
		return this.numCristais;
	}
	
	public Posicao posicao() {
		return this.pos;
	}
	
	public boolean temRobo() {
		return this.temRobo;
	}
	
	public int tipo() {
		return this.tipo;
	}
	
	public Base base() {
		return this.base;
	}
	
	public Robo robo() {
		return this.robo;
	}
	
	/* sets */
	public void colocaBase(Base base) {
		this.tipo = 0;
		this.base = base;
	}
	
	public void colocaRobo(Robo robo) {
		this.temRobo = true;
		this.robo = robo;
	}
	
	public void removeTodosOsCristais() {
	   this.numCristais = 0;
	}
	
	/* semi-sets */
	public void poeCristais(int n) {
	   this.numCristais += n;
	}
	
	public void removeRobo() {
      temRobo = false;
	}
	
	public void removeBase() {
	   tipo = 3;
	}
	
	public void removeUmCristal() {
	   this.numCristais--;
	}
	
	public void print(){
		System.out.println("Numero de cristais :" + numCristais);
		System.out.println("Coordenadas da posicao : (" + pos.coord1() + " , " + pos.coord2() + ")");
		if(temRobo)
			System.out.println("Tem robo!");
		if(tipo == 0)
			System.out.println("Eh base!");
		//else
		
	
	}
	

}
