import java.util.Random;
import java.util.Iterator;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

public class Servidor { // eh um Sigleton, por isso o construtor eh privado e
   // soh se pode obter uma instancia (a unica) com o metodo getInstance() 
	int tempo;
	Arena arena;
	Robo[] robos;
	int numTimes; // numero de times original, nao o atual (permanece constante mesmo quando um time perde)
	int numTimesRestantes; // numero de times atual
	int numRobosPorTime;
	int[] numRobosVivosDeCadaTime; // numRobosVivosDeCadaTime[time] == 0 se e somente se o time time jah foi derrotado
	Programas programas;
	Base[] bases; // a cada time, associa sua base 
	PrintWriter saida; // basta fazer 'saida.println(algumastring); saida.flush();' para imprimir coisas no arquivo dado pelo usuario
   final String[] cores = {"verde", "azul", "laranja", "magenta"};
	int queroLog;
	
   private static Servidor instance = null;
   /* construtor do singleton */
   private Servidor() {
      }
      public static Servidor getInstance() {
        if (instance == null) {
            instance = new Servidor();
        }
        return instance;
    }

	public void inicializaJogo(int numTimes, int numRobosPorTime, Programas programas,
		int[] timeDeCadaRobo, int[] programaDeCadaRobo, PrintWriter saida, int queroLog) {
		/* 0. seta parametros */
		this.numTimes = numTimes;
		this.numRobosPorTime = numRobosPorTime;
		this.tempo = 0;
		this.saida = saida;
		this.numTimesRestantes = numTimes;
		this.numRobosVivosDeCadaTime = new int[numTimes];
		this.queroLog = queroLog;
		for(int time = 0; time < numTimes; time++) numRobosVivosDeCadaTime[time] = numRobosPorTime;
      /* 0 eh base, 1 eh terreno plano, 2 eh terreno rugoso, 3 sao residuos de uma base, 4 eh repositorio de cristais (maior probabilidade de ter cristais) */
      /* as probabilidades abaixo são relativas, pois como vemos elas não somam um */
      double probabilidadesTiposDeTerreno[] = {0.0, 1.0, 0.12, 0.0, 0.12};
		this.arena = new Arena(20, 20, probabilidadesTiposDeTerreno);
		this.programas = programas;
      /* 1. cria e instala as bases na arena */		
		this.bases = new Base[numTimes];
		for(int time = 0; time < numTimes; time++) {
			bases[time] = new Base(time, 5, 5);
			arena.instalaBaseNumDosQuatroCantosDoMapa(bases[time]);
		}
		/* 2. cria e instala os robos na arena */
		this.robos = new Robo[numTimes*numRobosPorTime];
		
		for(int i = 0; i < numTimes*numRobosPorTime; i++) {
			robos[i] = new Robo(timeDeCadaRobo[i], programaDeCadaRobo[i], i, 5);
			if(queroLog == 1) {saida.println("Vamos inserir " + i + "-esimo robo"); saida.flush(); }
			arena.insereRobo(robos[i], bases);
		}
		
		/* 3. distribui cristais na arena, num total de 25 vezes o número de exércitos*/
		arena.poeCristais(25*numTimes);
	}
   
   /* Responsavel por tratar as syscalls */
	public Empilhavel[] sistema(Operacao op) {
      final int CUSTO_PDVZ = 2;   /* Constantes que armazenam os custos de tempo das várias chamadas ao sistema */
      final int CUSTO_FIXO_MVRB_OUTROS_TERRENOS = 4;
      final int CUSTO_FIXO_MVRB_TERRENO_RUGOSO = 20;
      final int CUSTO_MVRB_POR_CRISTAL = 2;
      final int CUSTO_DPCR = 4;
      final int CUSTO_PGCR = 4;
      final int CUSTO_ATCK = 4;
      Robo rob = op.chamador();
      if (rob.timerDeCusto() == 0) { /* Cada chamada ao sistema é feita duas vezes: uma seta o timer correspondente ao custo, e a segunda executa-a de fato;
                                    agora estamos na primeira e vamos apenas setar o timer */
         if((op.codigo()).equals("PDVZ")) {
            rob.setaTimerDeCusto(CUSTO_PDVZ);
         }
         else if((op.codigo()).equals("DPCR")) {
            rob.setaTimerDeCusto(CUSTO_DPCR);
         }
         else if((op.codigo()).equals("PGCR")) {
            rob.setaTimerDeCusto(CUSTO_PDVZ);
         }
         else if((op.codigo()).equals("ATCK")) {
            rob.setaTimerDeCusto(CUSTO_ATCK);
         }
         else if((op.codigo()).equals("MVRB")) {
     	      int i = rob.posicao().coord1();
   	      int j = rob.posicao().coord2();
            if (arena.mapa[i][j].tipo() == 2) { /* Terreno da posição atual é rugoso */
               rob.setaTimerDeCusto(CUSTO_FIXO_MVRB_TERRENO_RUGOSO + rob.numCristais()*CUSTO_MVRB_POR_CRISTAL);
            }
            else {
               rob.setaTimerDeCusto(CUSTO_FIXO_MVRB_OUTROS_TERRENOS + rob.numCristais()*CUSTO_MVRB_POR_CRISTAL);
            }
         }
         return null;
      }  
      else {
         rob.setaTimerDeCusto(0); /* Cada chamada ao sistema é feita duas vezes: uma seta o timer correspondente ao custo, e a segunda executa-a de fato;
                                    agora estamos na hora de executar de fato */

   	   if((op.codigo()).equals("PDVZ")) { /* se a instrucao for a PDVZ, retorna um vetor de tamanho 1 contendo a vizinhanca de raio 1 do robo chamador*/
   	      InfoDoTerreno[] terrenos = new InfoDoTerreno[7];
   	      int i = op.chamador().posicao().coord1();
   	      int j = op.chamador().posicao().coord2();
   	      /* 1. (distancia 0) InfoDoTerreno onde estah o proprio Robo chamador */
   	    //  System.out.println("PDVZ:parte1");
   	      if(arena.mapa[i][j].tipo != 0) { /* nao tem base em (i, j) */
   	         /*observe que certamente tem robo em (i, j) e eh o chamador*/
   	         terrenos[0] = new InfoDoTerreno(arena.mapa[i][j].numCristais, i, j, arena.mapa[i][j].robo().time(), -1, arena.mapa[i][j].tipo());
   	      }
   	      else { /* tem base em (i, j) */
   	         terrenos[0] = new InfoDoTerreno(arena.mapa[i][j].numCristais, i, j, arena.mapa[i][j].robo().time(), arena.mapa[i][j].base().time(), 0);
   	      }
   	      /* 2. (distancia 1) InfoDoTerreno dos terrenos ao redor do Robo chamador */
   	     // System.out.println("PDVZ:parte2");
   	      int numeroDePosicoesDentreAsSeteQueEstaoDentroDoMapa;
   	      if(i%2 == 0) { /* linha par */
   	       //  System.out.println("Linha par.");
   	         int[][] direcoes = {{i+1, j+0}, {i+1, j+1}, {i+0, j+1}, {i-1, j+1}, {i-1, j+0}, {i+0, j-1}};
   	         int inic = Aleatorio.randInt(0, 5);
   	         int ind = inic;
   	         int m = 1;
   	       //  System.out.println("vou tentar fazer o do agora.");
   	         do {
   	          //  System.out.println("Entrei no do.");
   	            int k = direcoes[ind][0];
   	            int l = direcoes[ind][1];
   	         //   System.out.println(k + "," + l);
   	            if(0 <= k && k < arena.altura() && 0 <= l && l < arena.largura()) {
   	             //  System.out.println(k + "," + l + " dentro.");
   	               /*se a posicao (k, l) estiver dentro do mapa*/
   	               if(arena.mapa[k][l].temRobo() && (arena.mapa[k][l].tipo == 0)) {
   	                  /* tem robo e tem base */
   	             //     System.out.println("tem robo e tem base");
   	                  terrenos[m++] = new InfoDoTerreno(/*nao colocamos a informacao arena.mapa[k][l].numCristais, pois os cristais soh devem poder ser vistos pelos robos quando os robos estao a distancia zero deles. Caso contrario os repositorios de cristais nao serviriam para nada*/0, k, l, arena.mapa[k][l].robo().time(), arena.mapa[k][l].base().time(), 0);
   	               }
   	               else if(arena.mapa[k][l].temRobo() && !(arena.mapa[k][l].tipo == 0)) {
   	                  /* tem robo e nao tem base */
   	               //   System.out.println("tem robo e nao tem base");
   	                  terrenos[m++] = new InfoDoTerreno(/*arena.mapa[k][l].numCristais*/0, k, l, arena.mapa[k][l].robo().time(), -1, 0);
   	               }
   	               else if(!arena.mapa[k][l].temRobo() && (arena.mapa[k][l].tipo == 0)) {
   	                  /* nao tem robo e tem base */
   	                 // System.out.println("nao tem robo e tem base");
   	                 // arena.mapa[i][j].base();
   	               //   System.out.println("nao tem robo e tem base2");
   	                  terrenos[m++] = new InfoDoTerreno(/*arena.mapa[k][l].numCristais*/0, k, l, -1, (arena.mapa[k][l].base()).time(), 0);	                  
   	               }
   	               else {
   	                 // System.out.println("nao tem robo e nao tem base" + m);
   	                  /* nao tem robo e nao tem base */
   	                  
   	                  terrenos[m] = new InfoDoTerreno(/*arena.mapa[k][l].numCristais*/0, k, l, -1, -1, 0);
   	                  //System.out.println("Agora vou incrementar o m");
   	                  m++;
	                  //System.out.println("Incrementei o m="+m);
   	               }
   	            }
   	            
   	            ind = (ind+1)%6;
   	           // System.out.println("ind="+ind+"inic="+inic);
   	            
   	            
   	         } while(ind != inic);
   	         numeroDePosicoesDentreAsSeteQueEstaoDentroDoMapa = m;
   	         
   	      }
   	      else { /* linha impar */
   	      	//System.out.println("Linha impar.");
   	         int[][] direcoes = {{i+1, j+0}, {i+0, j+1}, {i-1, j+0}, {i-1, j-1}, {i+0, j-1}, {i+1, j-1}};
   	         int inic = Aleatorio.randInt(0, 5);
   	         int ind = inic;
   	         int m = 1;
   	         do {
   	            int k = direcoes[ind][0];
   	            int l = direcoes[ind][1];
   	            if(0 <= k && k < arena.altura() && 0 <= l && l < arena.largura()) {
   	               /*se a posicao (k, l) estiver dentro do mapa*/
   	               if(arena.mapa[k][l].temRobo() && (arena.mapa[k][l].tipo == 0)) {
   	                  /* tem robo e tem base */
   	                  terrenos[m++] = new InfoDoTerreno(/*arena.mapa[k][l].numCristais*/0, k, l, arena.mapa[k][l].robo().time(), arena.mapa[k][l].base().time(), 0);
   	               }
   	               else if(arena.mapa[k][l].temRobo() && !(arena.mapa[k][l].tipo == 0)) {
   	                  /* tem robo e nao tem base */
   	                  terrenos[m++] = new InfoDoTerreno(/*arena.mapa[k][l].numCristais*/0, k, l, arena.mapa[k][l].robo().time(), -1, 0);
   	               }
   	               else if(!arena.mapa[k][l].temRobo() && (arena.mapa[k][l].tipo == 0)) {
   	                  /* nao tem robo e tem base */
   	                  terrenos[m++] = new InfoDoTerreno(/*arena.mapa[k][l].numCristais*/0, k, l, -1, arena.mapa[k][l].base().time(), 0);	                  
   	               }
   	               else {
   	                  /* nao tem robo e nao tem base */
   	                  terrenos[m++] = new InfoDoTerreno(/*arena.mapa[k][l].numCristais*/0, k, l, -1, -1, 0);
   	               }
   	            }
   	            
   	            ind = (ind+1)%6;
   	            
   	            
   	            
   	         } while(ind != inic);
   	         numeroDePosicoesDentreAsSeteQueEstaoDentroDoMapa = m;
   	      }
   
   	      /*observe que talvez o array terrenos esteja com menos do que 7 elementos,
   	      jah que algumas posicoes poderiam estar fora do mapa e, portanto, as
   	      informacoes dos terrenos correspondentes nao teriam sido acrescidas a tal vetor.
   	      Por isso, vamos copiar tudo pra um vetor do tamanho correto antes de
   	      retornar.*/
            InfoDoTerreno[] terrenosReduzido = new InfoDoTerreno[numeroDePosicoesDentreAsSeteQueEstaoDentroDoMapa];
            for(int p = 0; p < numeroDePosicoesDentreAsSeteQueEstaoDentroDoMapa; p++) {
               terrenosReduzido[p] = terrenos[p];
            }
   	      Empilhavel[] retorno = new Empilhavel[1];
   	      retorno[0] = new Vizinhanca(terrenosReduzido);
   	      return retorno;
   	   }
   	   else if((op.codigo()).equals("ATCK")) {
   	      Posicao pos = (Posicao)op.operando();
   	      int iAtacado = pos.coord1();
   	      int jAtacado = pos.coord2();
   	      if(0 <= iAtacado && iAtacado < arena.altura() && 0 <= jAtacado && jAtacado < arena.largura()) {
   	         if(arena.mapa[iAtacado][jAtacado].temRobo()) {
   	            // se tem robo na posicao atacada, ele sofre dano
   	            Robo atacado = arena.mapa[iAtacado][jAtacado].robo();
   	            atacado.sofreDano();
   	            if(atacado.vida() <= 0) {
   	               // se o robo perdeu toda a sua vida
   	               // 1. Deposito os cristais dele no terreno em que ele se situa
   	               Terreno t = arena.mapa[iAtacado][jAtacado];
   	               t.poeCristais(atacado.numCristais());
   	               // 2. Mato o robo
   	               atacado.morre();
   	               // 3. Decremento o numero de robos vivos do time do robo (note que mantemos o time do robo em atacado.time() mesmo depois do robo estar morto)
   	               numRobosVivosDeCadaTime[atacado.time()]--;
   	               // 4. Removo o robo do terreno que ele ocupava
   	               t.removeRobo();
   	               // 5. Se o robo destruido era o ultimo de seu time
   	               if(numRobosVivosDeCadaTime[atacado.time()] == 0) {
   	                  // removemos o time
   	                  removeTime(atacado.time());
   	                  if(numTimesRestantes == 1)
   	                     terminaJogo();
   	               }
   	            }
   	         }
   	      }
   	   }
   	   
   	   else if((op.codigo()).equals("PGCR")) {
   	      int i = (rob.posicao()).coord1();
   	      int j = (rob.posicao()).coord2();
   	      if(rob.numCristais() < 9 && arena.mapa[i][j].numCristais() > 0) {
   	         rob.poeCristais(1);
   	         arena.mapa[i][j].removeUmCristal();
   	         if(queroLog == 1) { saida.println("Sistema: Robo pegou um cristal da posicao (" + i + "," + j + ")"); }
   	      }
   	      else {
   	         if(queroLog == 1) {
   	            saida.println("Sistema: Robo nao pegou um cristal da posicao (" + i + "," + j + ")");
   	         }
   	      }
   	      if(queroLog == 1) saida.flush();
   	   }
   	   else if((op.codigo()).equals("DPCR")) {
   	      int i = (rob.posicao()).coord1();
   	      int j = (rob.posicao()).coord2();
   	      if(arena.mapa[i][j].tipo == 0) {
   	         /* se tem base na posicao ocupada pelo robo */
   	         Base b = arena.mapa[i][j].base;
   	         if(b.numDeCristais() >= 5 || b.numDeCristais() < 0) System.out.println("ERRO: a base tem numero negativo ou maior que 4 de cristais!!!");
   	         if(rob.numCristais() > 0) {
   	            b.poeCristais(1);
   	            rob.removeUmCristal();
   	            if(queroLog == 1) { saida.println("Robô depositou com sucesso um cristal em base do time " + (b.time+1) + "!"); saida.flush(); }
   	         }
               
   	         if(b.numDeCristais() >= 5) {
                  if(queroLog == 1) { saida.println("Time " + (b.time()+1) + "("+cores[b.time]+ ") eliminado!"); saida.flush(); }
   	            removeTime(b.time());
   	            if(numTimesRestantes == 1)
   	               terminaJogo();
   	            
   	         }
   	         
   	      }
   	      else {
   	        /* se nao tem base na posicao ocupada pelo robo */
   	        if(rob.numCristais() > 0) {           
   	         Terreno t = arena.mapa[i][j];
   	         t.poeCristais(1);
   	         rob.removeUmCristal();
   	         if(queroLog == 1) { saida.println("Robo derrubou no chao um cristal!"); saida.flush(); }
   	        }
   	      }
   	   }
   	   else if((op.codigo()).equals("MVRB")) {
   	      int iRobo = rob.posicao().coord1();
   	      int jRobo = rob.posicao().coord2();
   	      int iQuerIr = ((Posicao)(op.operando())).coord1();
   	      int jQuerIr = ((Posicao)(op.operando())).coord2();
            boolean moveuSe = false;
   	      if(queroLog == 1) { saida.println("Robo quer se mover para " + iQuerIr +"," + jQuerIr); saida.flush(); }
   	      if(iRobo%2 == 0) {
   	         /* linha par */
   	         int[][] direcoes = {{iRobo+1, jRobo+0}, {iRobo+1, jRobo+1}, {iRobo+0, jRobo+1}, {iRobo-1, jRobo+1}, {iRobo-1, jRobo+0}, {iRobo+0, jRobo-1}};
   	         for(int ind = 0; ind < 6; ind++) {
   	            if(iQuerIr == direcoes[ind][0] && jQuerIr == direcoes[ind][1] && 0 <= iQuerIr && iQuerIr < arena.altura() && 0 <= jQuerIr && jQuerIr < arena.largura() && !arena.mapa[iQuerIr][jQuerIr].temRobo() ) {
	                  /* movo o robo para (iQuerIr, jQuerIr), pois essa eh uma posicao adjacente, sem robos, dentro do mapa */
	                  moveRobo(rob, iRobo, jRobo, iQuerIr, jQuerIr);
                     moveuSe = true;
	                  break; // nao precisava
	               }
	            }
	         }
	         else {
	            /* linha impar */
	            int[][] direcoes = {{iRobo+1, jRobo+0}, {iRobo+0, jRobo+1}, {iRobo-1, jRobo+0}, {iRobo-1, jRobo-1}, {iRobo+0, jRobo-1}, {iRobo+1, jRobo-1}};
	            for(int ind = 0; ind < 6; ind++) {
	               if(iQuerIr == direcoes[ind][0] && jQuerIr == direcoes[ind][1] && 0 <= iQuerIr && iQuerIr < arena.altura() && 0 <= jQuerIr && jQuerIr < arena.largura() && !arena.mapa[iQuerIr][jQuerIr].temRobo() ) {
	                  /* movo o robo para (iQuerIr, jQuerIr), pois essa eh uma posicao adjacente, sem robos, dentro do mapa*/
	                  moveRobo(rob, iRobo, jRobo, iQuerIr, jQuerIr);
                     moveuSe = true;
	                  break; // nao precisava
	               }
	            }
	         }
            if(queroLog == 1 && moveuSe == false) { saida.println("Robo em (" + iRobo + "," + jRobo + ") não pôde ir para ("+ iQuerIr+ "," + jQuerIr+")"); saida.flush(); }
	      }
	      return null; /* retorno das instruções MVRB, DPCR e PGCR */
   	}
	}
	
	/* Avanca um timestep do jogo */
	public void atualiza() {
	   if(queroLog == 1) { saida.println("rodada " + tempo); saida.flush(); }
		for(int r = 0; r < robos.length; r++) {
			Robo rob = robos[r];
		   if(!rob.morto) {
		      if(queroLog == 1) {
               saida.println("Robo " + rob.numeroIdentificador() + " (do time " + (rob.time() + 1) + " (" + cores[rob.time()]+ ")), sua vez!"); saida.flush();
            }
            if (rob.timerDeCusto() < 2) {
 		         rob.executaProxInstrucao(programas.programaDeIndice(rob.numeroDoPrograma), saida, queroLog);
            }
            else {
               rob.decrementaTimerDeCusto();
               if(queroLog == 1) {
                  saida.println("Apenas decrementando timer de custo de " + (rob.timerDeCusto()+1) + " para " + rob.timerDeCusto()+ "!"); saida.flush();
               }
            }
		   }
		}
		tempo++;
	}
	
	/* numRodadas igual a 0 indica numero ilimitado de rodadas, enquanto outros inteiros indicam o numero correspondente de rodadas */
	public void joga(int numRodadas, int impressaoDaArenaNoTerminal, int queroGraficos, int rodadasPorImpressao, int rodadasPorAtualizacaoDosGraficos) {
	   if(queroLog == 1) { saida.println("Senhoras e senhores, a batalha vai comecar!"); saida.flush(); }
	   
	   final String ANSI_CLS = "\u001b[2J";
	   System.out.print(ANSI_CLS); // Soh pra ajustar a saida antes de comecar a imprimir a arena
      do {
         permutaRobos();
         atualiza();
         if(impressaoDaArenaNoTerminal == 1 && numRodadas % rodadasPorImpressao == 0) {
            arena.imprimeNoTerminal(numRodadas);
         }
         if(queroGraficos == 1 && numRodadas % rodadasPorAtualizacaoDosGraficos == 0) {
            arena.atualizaTelaDoJogo(); // PARTE GRAFICA
         }
         numRodadas--;
      } while(numRodadas != 0);
   }
	
	
	/*********************************************** METODOS AUXILIARES **************************************/
	
	
	
	
	/* Gera uma permutacao do array de robos do Servidor de modo que a probabilidade
	de cada permutacao seja igual (supondo que o algoritmo randInt() gere numero
	uniformemente ao acaso entre min e max, inclusive) */
	public void permutaRobos() {
		for(int i = robos.length - 1; i >= 1; i--) {
			int j = Aleatorio.randInt(0, i);
			Robo auxiliar = robos[i];
			robos[i] = robos[j];
			robos[j] = auxiliar;
		}
	}
	
	/* Remove completamente do jogo um time derrotado. */
	public void removeTime(int time) {
	   /*TODO: verificar que nao esquecemos de nada*/
	   /*
	   1. Deposito os cristais de cada robo removido do time no terreno ocupado
	   2. Robos do time morrem (e portanto nao sao mais executados em atualiza(), alem de todos terem suas pilhas e memorias ressetadas, e sua vida zerada)
	    */
	   for(int i = 0; i < robos.length; i++) {
	      if(robos[i].time() == time) {
	         /*1*/
	         Terreno t = arena.mapa[(robos[i].posicao()).coord1()][(robos[i].posicao()).coord2()];
	         t.poeCristais(robos[i].numCristais());
	         /*2*/
	         robos[i].morre();
	      }
	   }
	   /* 3. Removo de cada Terreno do mapa da arena os robos e a base do time */
	   for(int i = 0; i < arena.altura(); i++) {
	      for(int j = 0; j < arena.largura(); j++) {
	         Terreno t = arena.mapa[i][j];
	         if(t.temRobo() && t.robo.time() == time) {
	            /* se tem robo do time */
	            t.removeRobo();
	         }
	         if(t.tipo() == 0 && t.base.time() == time) {
	            /* se tem base */
	            t.removeBase();
	         }
	      }
	   }
	   /* 5. Decremento o numero de times restantes */
	   numTimesRestantes--;
	   /* 6. Atualizo o vetor numRobosVivosDeCadaTime */
	   numRobosVivosDeCadaTime[time] = 0;
	}
	
	
	public void moveRobo(Robo robo, int iRobo, int jRobo, int iDestino, int jDestino) {
	   robo.posicao = new Posicao(iDestino, jDestino);
	   arena.mapa[iRobo][jRobo].removeRobo();
	   arena.mapa[iDestino][jDestino].colocaRobo(robo);
	}
	
	/* supoe que soh resta um time em jogo e termina o jogo */
	public void terminaJogo() {
	   if(queroLog == 1) { saida.println("FIM DE JOGO!"); }
      int timeVencedor = -42; // soh para o Java nao falar que nao estah inicialiado...
   	for(int time = 0; time < numTimes; time++)
   	   if(numRobosVivosDeCadaTime[time] > 0)
   	      timeVencedor = time;
		if(queroLog == 1) { 
		   saida.println("time " + (timeVencedor+1) + "("+cores[timeVencedor]+ ") eh o vencedor!"); saida.flush();
   	   saida.println("Sobraram ainda " + numRobosVivosDeCadaTime[timeVencedor] + " robos desse time."); saida.flush();
   	}
   	System.out.println("time " + (timeVencedor+1) + "("+cores[timeVencedor]+ ") eh o vencedor!");
   	System.out.println("Sobraram ainda " + numRobosVivosDeCadaTime[timeVencedor] + " robos desse time.");
		if(JOptionPane.showConfirmDialog(null, "time " + (timeVencedor+1) + " ("+cores[timeVencedor]+ ") eh o vencedor!\n"
		   + "Sobraram ainda " + numRobosVivosDeCadaTime[timeVencedor] + " robos desse time.\n" + "Deseja fechar o Jogo?", "Fim do Jogo", JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
	      System.exit(0);
	}
	
	
	
	
	
	
	
	
	
	
}









