public class InfoDoTerreno implements Empilhavel {
	int n_cristais;
	Posicao pos;
	int info_robo; /* time do robo que ocupa esse terreno, se existir, ou -1, se estiver sem robô */
	int info_base; /* time da base que ocupa esse terreno, se existir, ou -1, se não for base */
	int info_tipo; /* 0 eh base, 1 eh terreno plano, 2 eh terreno rugoso, 3 sao residuos de uma base, 4 eh repositorio de cristais (maior probabilidade de ter cristais) */

	public InfoDoTerreno(int n_cristais, int i, int j, int info_robo,
      int info_base, int tipo){
     // System.out.println("Info:sendo setado!");
		this.n_cristais = n_cristais;
		this.pos = new Posicao(i,j);
		this.info_robo = info_robo;
		this.info_base = info_base;
		this.info_tipo = info_tipo;
		//System.out.println("Info:fuisetado!");
	}

   public int info_robo() {
      return info_robo;
   }

   public int info_tipo() {
      return info_tipo;
   }

   public int info_base() {
      return info_base;
   }

   public int n_cristais() {
      return n_cristais;
   }

   public int[] coordenadas() {
      int[] retorno = {pos.coord1(), pos.coord2()};
      return retorno;
   }
	
	/*public void print(){
		System.out.println("Numero de cristais :" + n_cristais.valor());
		System.out.println("Coordenadas da posicao : (" + pos.get_coord1() + " , " + pos.get_coord2() + ")");
		if(tem_robo)
			System.out.println("Tem robo!");
		if(tipo == 0)
			System.out.println("Eh base!");
		//else
		
	
	}*/
}
