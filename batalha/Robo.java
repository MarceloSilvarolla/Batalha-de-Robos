import java.io.PrintWriter;
import java.util.Stack;
public class Robo
{
   int vida;
   boolean morto;
	int contador;
	Pilha pilha;
   int timerDeCusto;
	int time;
   int numeroDoPrograma;
   int numCristais;
   Posicao posicao;
   Empilhavel[] memoria;
   int numeroIdentificador; /*apenas para fins de ter como diferenciar os robos facilmente */
	Frame frame;
	Stack<Frame> Contexto = new Stack<Frame>();

	
	public Robo(int time, int numeroDoPrograma, int numeroIdentificador, int vida) {
	   this.morto = false;
		this.contador = 0;
      this.timerDeCusto = 0;
		this.pilha = new Pilha();
		this.time = time;
      this.numCristais = 0;
      this.numeroDoPrograma = numeroDoPrograma;
      this.memoria = new Empilhavel[100];
      this.numeroIdentificador = numeroIdentificador;
      this.vida = vida;
		this.frame = new Frame(10);
		this.Contexto.push(frame);
	}

		/*gets*/
	public int vida() {
	   return this.vida;
	}
	public boolean morto() {
	   return this.morto;
	}
	
	public Posicao posicao() {
		return this.posicao;
	}
	
	public int time() {
		return this.time;
	}

 	public int numCristais() {
		return this.numCristais;
	}

   public int timerDeCusto() {
		return this.timerDeCusto;
	}
	
	public Pilha pilha() {
	   return this.pilha;
	}
	
	public int numeroIdentificador() {
	   return this.numeroIdentificador;
	}
	
	/* sets*/
	public void poePosicao(Posicao posicao) {
		this.posicao = posicao;
	}
	
	public void setaTimerDeCusto(int espera) {
		this.timerDeCusto = espera;
	}
	
	/* semi-sets*/
	public void decrementaTimerDeCusto() {
		--(this.timerDeCusto);
	}

	public void poeCristais(int n) {
	   this.numCristais += n;
	}
	
	public void removeTodosOsCristais() {
	   this.numCristais = 0;
	}
	
	public void removeUmCristal() {
	   this.numCristais--;
	}
	
	public void ressetaPilha() {
	   pilha = new Pilha();
	}
	
	public void ressetaMemoria() {
	   memoria = new Empilhavel[100];
	}
	
	public void morre() {
	   this.vida = 0;
	   this.morto = true;
	   this.contador = 0;
	   this.pilha = new Pilha();
	   this.numCristais = 0;
	   this.memoria = new Empilhavel[100];
	}
	
	public void sofreDano() {
	   this.vida--;
	}
	
//	Empilhável Roda(Instrução[] prg) {
//		Prog = prg;
//		p.push(new Endereço(0));
//		roda(0);
//		return p.pop();
//	}

	// Roda o programa a partir da posição iii
//	void roda(int ii) {
//		ip = ii;
//		Endereço ret = (Endereço)p.pop(); // guarda o endereço de retorno

		// RET é tratado explicitamente, para simplificar
//		while (!(Prog[ip] instanceof RET))
//			ip += Prog[ip].Exec(this);

		// Para prosseguir de onde foi chamado
//		frame = Contexto.pop();
//		ip = ret.val();
//	}


// Roda o programa a partir da posição ii
//	void roda(int ii) {
//		this.contador = ii;
//		Endereco ret = (Endereco)pilha.pop(); // guarda o endereço de retorno

		// RET é tratado explicitamente, para simplificar
//		while (!(Prog[contador] instanceof RET))
//			contador += Prog[contador].executa(this);

//		// Para prosseguir de onde foi chamado
//		frame = Contexto.pop();
//		contador = ret.val();
//	}
	
	/* outros metodos */
	public void executaProxInstrucao(Instrucao[] vetorDePrograma, PrintWriter saida, int queroLog){
		Instrucao proximaInstrucao = vetorDePrograma[contador];
      if(queroLog == 1) {
         saida.println("Robô localizado em (" + posicao.coord1() + "," + posicao.coord2() + "), portando " + numCristais + " cristais, vai executar instrução."); saida.flush();
      }
      try {
			if (proximaInstrucao instanceof CALL) {
				if(queroLog == 1) {saida.println("Executando CALL."); saida.flush();}
				if(proximaInstrucao.argumento instanceof Endereco) {
					try {
						pilha.push(new Endereco(contador+1));
					}
					catch (RobotRuntimeException excecao) {
			         String mensagem = excecao.getMessage();
			         mensagem = "Instrucao CALL - " + mensagem;
			         throw new RobotRuntimeException(mensagem);
      			}
					contador = ((Endereco)proximaInstrucao.argumento).valor();
				}
				else {
					throw new RobotRuntimeException("Instrução CALL - argumento deveria ser Endereco");
				}
			}
			else if (proximaInstrucao instanceof RET) {
				if(queroLog == 1) {saida.println("Executando RET."); saida.flush();}
				try {
					Empilhavel e1 = pilha.pop();
					contador = ((Endereco)pilha.pop()).valor();
					pilha.push(e1);
					frame = Contexto.pop();
				}
				catch (RobotRuntimeException excecao) {
			      String mensagem = excecao.getMessage();
			      mensagem = "Instrucao RET - " + mensagem;
			      throw new RobotRuntimeException(mensagem);
      		}
			}
			else {
//				int retornoExecucaoInstrucao = proximaInstrucao.executa(pilha, memoria, this, saida, queroLog);
//				if (retornoExecucaoInstrucao == -1) { // indica que próxima instrução deve ser executada
//		   		contador += 1;
//				}
//				else if (retornoExecucaoInstrucao == -2) { // indica que execução será postergada até que se cobre um custo em tempo do robô
//				}
//				else
//					contador = retornoExecucaoInstrucao; // indica que saltaremos para um certo endereço absoluto do vetor de programa
				contador += proximaInstrucao.executa(pilha, memoria, this, saida, queroLog);
   			contador = contador % (vetorDePrograma.length); /*se for cair fora do vetor ao somar um no contador, volta pro comeco (isso cria um loop, necessario para o prosseguimento do jogo, que pode demorar muito para acabar ou nao acabar nunca*/
			}
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Erro na linha " + (contador + 1) + " do programa número " + (numeroDoPrograma + 1) + ": " + mensagem;
         if(queroLog == 1) {
            saida.println(mensagem); saida.flush();
            saida.println("Reinicializando o robô com erro."); saida.flush();
         }
         pilha.esvazia();
         contador = 0;
         this.frame = new Frame(10);
         Contexto.removeAllElements();
      }
	}
	
	

	
}
