import java.lang.Math;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.io.PrintWriter;


public class Arena {
	Terreno[][] mapa;
	int largura;
	int altura;
	Graficos graficos; // PARTE GRAFICA
	
	/* Cria uma arena largura x altura onde a probabilidade relativa de cada tipo de terreno eh dado pelo vetor probabilidadeRelativaDeCadaTipoDeTerreno
	Supoe que todos os numeros inteiros de 0 a probabilidadeDeCadaTipoDeTerreno.length - 1 sao tipos de terreno validos
	(exceto 0 e 3, correspondentes a base e residuo de base, respectivamente)
	Nao coloca base e residuo de base (0 e 3), de modo que as posições 0 e 3 do vetor são ignoradas.*/
	public Arena(int largura, int altura, double[] probabilidadeDeCadaTipoDeTerreno) {
		this.largura = largura;
		this.altura = altura;
		mapa = new Terreno[altura][largura];
      probabilidadeDeCadaTipoDeTerreno[0] = 0;
      probabilidadeDeCadaTipoDeTerreno[3] = 0;
      double soma = 0;
      for(int i = 0; i < probabilidadeDeCadaTipoDeTerreno.length; i++){
         soma += probabilidadeDeCadaTipoDeTerreno[i];
      }
      for(int i = 0; i < probabilidadeDeCadaTipoDeTerreno.length; i++){
         probabilidadeDeCadaTipoDeTerreno[i] /= soma;
      }      
      for(int i = 0; i < probabilidadeDeCadaTipoDeTerreno.length-1; i++){
         probabilidadeDeCadaTipoDeTerreno[i+1] += probabilidadeDeCadaTipoDeTerreno[i];
      }
      for(int i = 0; i < probabilidadeDeCadaTipoDeTerreno.length; i++){
         System.out.println(probabilidadeDeCadaTipoDeTerreno[i]);
      }
		for(int i = 0; i < altura; i++){
			for(int j = 0; j < largura; j++){
				double numAleat = Math.random();
            int tipo = 0;
				for(int k = 1; k < probabilidadeDeCadaTipoDeTerreno.length; k++){
               if (numAleat > probabilidadeDeCadaTipoDeTerreno[k-1]) {
        				tipo++;
               }
               else {
                  break;
               }
            }
            System.out.println("Definido terreno da posição (" + i + "," + j + ") como do tipo " + tipo);
            mapa[i][j] = new Terreno(0,i,j,false,tipo);
			}
		}
		this.graficos = new Graficos(this.largura, this.altura, mapa); // PARTE GRAFICA
	}
	
	
	/* gets */
	public int largura() {
	   return this.largura;
	}
	
	public int altura() {
	   return this.altura;
	}
	
	
	/* sets */
	
	/* semi-sets */
	
	
	
	/* outros metodos */
	
	
	
	/* Coloca a base dada num dos quatro cantos do mapa, de acordo com o time
	da base.
	supoe que o mapa eh grande o suficiente para comportar a base. */
	public void instalaBaseNumDosQuatroCantosDoMapa(Base base) {
	   System.out.println("instalando base " + base.time());
		/* base do canto inferior esquerdo (do time 0) */
		if(base.time() == 0) {
			for(int i = 2; i < 2 + base.altura(); i++) {
				for(int j = 2; j < 2 + base.largura(); j++) {
					mapa[i][j].colocaBase(base);
					base.poePosicao(new Posicao(i, j));
				}
			}
		}
		/* base do canto superior direito (do time 1) */
		else if(base.time() == 1) {
			for(int i = this.altura - 2 - base.altura(); i < this.altura-2; i++) {
				for(int j = this.largura -2- base.largura(); j < this.largura-2; j++) {
					mapa[i][j].colocaBase(base);
					base.poePosicao(new Posicao(i, j));
				}
			}
		}
		/* base do canto inferior direito (do time 2) */
		else if(base.time() == 2) {
		   for(int i = 2; i < 2+base.altura(); i++) {
				for(int j = this.largura -2- base.largura(); j < this.largura-2; j++) {
					mapa[i][j].colocaBase(base);
					base.poePosicao(new Posicao(i, j));
				}
			}
		}
		/* base do canto superior esquerdo (do time 3) */
		else if(base.time() == 3) {
		   for(int i = this.altura -2- base.altura(); i < this.altura-2; i++) {
				for(int j = 2; j < 2+base.largura(); j++) {
					mapa[i][j].colocaBase(base);
					base.poePosicao(new Posicao(i, j));
				}
			}
		}
	}
	
	/* Encontra um Terreno do mapa (pseudo-aleatorio) para por o robo.
	A probabilidade eh maior do Robo cair proximo de sua base.
	O vetor bases dah a base de cada time. */
	public void insereRobo(Robo robo, Base[] bases) {
	   int numTentativas = 0;
		while(true) {
		   numTentativas++;
			int i = Aleatorio.randInt(0, this.altura - 1);
			int j = Aleatorio.randInt(0, this.largura - 1);
			if(!mapa[i][j].temRobo()) {
				int distancia = distancia(new Posicao(i, j), bases[robo.time()]);
				double prob = Math.exp(-0.02*distancia*distancia);
				double numAleat = Math.random();
				if(numAleat < prob) {
				   /* com probabilibade prob colocamos o robo ali */
				   mapa[i][j].colocaRobo(robo);
				   robo.poePosicao(new Posicao(i, j));
				   System.out.println("numTentativas para robo " + robo.time() + ":" + numTentativas);
				   return;
				}
			}
		}
	}
	
	/* Coloca n cristais na arena com probabilidade maior de cairem num terreno do tipo 4 (repositorio de cristais) */
	public void poeCristais(int n) {
	   int cont = 0;
	   while(cont < n) {
	      int i = Aleatorio.randInt(0, this.altura - 1);
			int j = Aleatorio.randInt(0, this.largura - 1);
			if(mapa[i][j].tipo == 0 || mapa[i][j].tipo == 3) {
			   // se for base ou residuo de base, rejeitamos
			   continue;
			}
			double probabilidadeDeRejeicao;
			if(mapa[i][j].tipo == 4) {
			   // se for repositorio de cristais, probabilidade de haver cristal deve ser maior, por isso aceitamos sempre o sorteio
			   probabilidadeDeRejeicao = 0;
			}
			else {
			   // se for outro tipo qualquer de terreno, probabilidade de haver cristal deve ser pequena, por isso rejeitamos quase sempre o sorteio
			   probabilidadeDeRejeicao = 0.98;
			}
			double numAleat = Math.random(); // numero gerado (pseudo-)uniformemente ao acaso entre 0 e 1
			if(numAleat < probabilidadeDeRejeicao) continue;
         
			mapa[i][j].poeCristais(1);
			cont++;
	   }
	}
	
	/*** Funcoes auxiliares ***/
	
	/* Retorna a distancia "taxicab" entre duas posicoes */
	public int distancia(Posicao A, Posicao B) {
	   /*System.out.println("calculando dist de " + A.coord1() + "," + A.coord2() + ") ate "+B.coord1() + "," + B.coord2());*/
		int d1 = Math.abs(A.coord1() - B.coord1());
		int d2 = Math.abs(A.coord2() - B.coord2());
		if(d1 >= d2) return d1;
		else return d2;
	}
	
	
	
	
	public int distancia(Posicao pos, Base base) {
		Set<Posicao> posicoesOcupadasPelaBase = base.posicoes();
		Iterator iterador = posicoesOcupadasPelaBase.iterator();
		/*if(!iterador.hasNext()) System.out.println("Oh my GOD!");
		else System.out.println("Tem um elemento sim, ufa.");*/
		int menorDistanciaDoRoboAtePosicaoOcupadaPelaBase = 
			distancia(pos, (Posicao)(iterador.next()));
		while(iterador.hasNext()) {
			int dist = distancia(pos, (Posicao)iterador.next());
			if(dist < menorDistanciaDoRoboAtePosicaoOcupadaPelaBase) {
				menorDistanciaDoRoboAtePosicaoOcupadaPelaBase = dist;
			}
		}
		//System.out.println("dist pos"+pos.coord1() + "," + pos.coord2() + " ate base " + base.time() + ": " + menorDistanciaDoRoboAtePosicaoOcupadaPelaBase);
		return menorDistanciaDoRoboAtePosicaoOcupadaPelaBase;
	}
	
	public void imprimeNoTerminal(int rodada) {
      //final String ANSI_CLS = "\u001b[2J";
      final String ANSI_HOME = "\u001b[H";
      System.out.print(/*ANSI_CLS +*/ ANSI_HOME);
      //System.out.flush();
	   System.out.println("-------------INICIANDO-IMPRESSAO-DA-ARENA-RODADA-" + rodada + "-------------");
	   for(int i = this.altura - 1; i >= 0; i--) {
	      if(i%2 == 0) { /* linha par */
	         System.out.print(" ");
	         for(int j = 0; j < this.largura; j++)
	            imprimePosicao(i, j);
	      }
	      else { /* linha impar */
	         for(int j = 0; j < this.largura; j++) {
	            imprimePosicao(i, j);
	         }
	      }
	      System.out.println("");
	   }
	   System.out.println("-------------TERMINANDO-IMPRESSAO-DA-ARENA-RODADA-" + rodada + "---------------");
		graficos.setTitle("Batalha de Robos - Rodada " + (-rodada));
	}
	
	/* imprime na saida padrao informacoes correspondente a posicao de coordenadas (i, j) do mapa hexagonal */
	public void imprimePosicao(int i, int j) {
	   if(mapa[i][j].temRobo())
	      System.out.print("R" + mapa[i][j].robo().numCristais() + " ");
	   else if(!mapa[i][j].temRobo() && mapa[i][j].tipo() == 0)
	      System.out.print("B" + mapa[i][j].base().numDeCristais() + " ");
	   else
	      System.out.print("  " + " ");
	}
	
	public void atualizaTelaDoJogo() { // PARTE GRAFICA
	   graficos.atualiza(this.mapa);
	}
}
