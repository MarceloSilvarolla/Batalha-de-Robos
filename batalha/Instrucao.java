import java.io.PrintWriter;
import java.util.Random;
import static java.lang.Math.sqrt; 

public abstract class Instrucao {
	Empilhavel argumento;
	abstract int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException; /* o valor retornado eh o incremento do program counter (pc) do robo que deverah ser feito em seguida */
}

class ADD extends Instrucao { /* desempilha dois números (reais ou inteiros) no topo da pilha, soma-os e empilha o resultado */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando ADD."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
   		Empilhavel emp2 = p.pop();
  			if(emp1 instanceof Inteiro && emp2 instanceof Inteiro) {
   		   p.push(new Inteiro(((Inteiro)emp1).valor() + ((Inteiro)emp2).valor()));
   		}
         else if(emp1 instanceof Inteiro && emp2 instanceof Real) {
            p.push(new Real(((Inteiro)emp1).valor() + ((Real)emp2).valor()));
  			}
         else if(emp1 instanceof Real && emp2 instanceof Inteiro) {
   			p.push(new Real(((Real)emp1).valor() + ((Inteiro)emp2).valor()));
  			}
  			else if(emp1 instanceof Real && emp2 instanceof Real) {
   			p.push(new Real(((Real)emp1).valor() + ((Real)emp2).valor()));
         }
   		else {
   			throw new RobotRuntimeException("Tipo de dado errado para instrucao ADD (deveria ser um par de instâncias de Numero no topo da pilha)");
   		}
   		return 1; /* incremento do program counter */
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao ADD - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
	}
}

class SUB extends Instrucao {  /* desempilha dois números (reais ou inteiros) no topo da pilha, subtrai o número o número no topo do número abaixo dele e empilha o resultado */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando SUB."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
   		Empilhavel emp2 = p.pop();
   		if(emp1 instanceof Inteiro && emp2 instanceof Inteiro) {
   		   p.push(new Inteiro(-((Inteiro)emp1).valor() + ((Inteiro)emp2).valor()));
   		}
         else if(emp1 instanceof Inteiro && emp2 instanceof Real) {
            p.push(new Real(-((Inteiro)emp1).valor() + ((Real)emp2).valor()));
  			}
         else if(emp1 instanceof Real && emp2 instanceof Inteiro) {
   			p.push(new Real(-((Real)emp1).valor() + ((Inteiro)emp2).valor()));
  			}
  			else if(emp1 instanceof Real && emp2 instanceof Real) {
   			p.push(new Real(-((Real)emp1).valor() + ((Real)emp2).valor()));
         }
   		else {
   			throw new RobotRuntimeException("Tipo de dado errado para instrucao SUB (deveria ser um par de instâncias de Numero no topo da pilha)");
   		}
   		return 1;
   	}
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao SUB - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class MUL extends Instrucao { /* desempilha dois números (reais ou inteiros) no topo da pilha, multiplica-os e empilha o resultado */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando MUL."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
   		Empilhavel emp2 = p.pop();
      	if(emp1 instanceof Inteiro && emp2 instanceof Inteiro) {
   		   p.push(new Inteiro(((Inteiro)emp1).valor() * ((Inteiro)emp2).valor()));
   		}
         else if(emp1 instanceof Inteiro && emp2 instanceof Real) {
            p.push(new Real(((Inteiro)emp1).valor() * ((Real)emp2).valor()));
  			}
         else if(emp1 instanceof Real && emp2 instanceof Inteiro) {
   			p.push(new Real(((Real)emp1).valor() * ((Inteiro)emp2).valor()));
  			}
  			else if(emp1 instanceof Real && emp2 instanceof Real) {
   			p.push(new Real(((Real)emp1).valor() * ((Real)emp2).valor()));
         }
	   	else {
	   		throw new RobotRuntimeException("Tipo de dado errado para instrucao MUL (deveria ser um par de instâncias de Numero no topo da pilha)");
	   	}
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao MUL - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class DIV extends Instrucao { /* desempilha dois números (reais ou inteiros) no topo da pilha, divide pelo número no topo o número abaixo dele e empilha o resultado */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando DIV."); saida.flush();}
      try {
         Empilhavel emp2 = p.pop();
   		Empilhavel emp1 = p.pop();
   		if(emp1 instanceof Inteiro && emp2 instanceof Inteiro) {
            if (((Inteiro)emp2).valor() != 0) {
   		      p.push(new Inteiro(((Inteiro)emp1).valor() / ((Inteiro)emp2).valor()));
            }
            else {
               throw new RobotRuntimeException("Divisao por zero");
            }
   		}
         else if(emp1 instanceof Inteiro && emp2 instanceof Real) {
            if (((Real)emp2).valor() != 0) {
               p.push(new Real(((Inteiro)emp1).valor() / ((Real)emp2).valor()));
             }
            else {
               throw new RobotRuntimeException("Divisao por zero");
            }
  			}
         else if(emp1 instanceof Real && emp2 instanceof Inteiro) {
            if (((Inteiro)emp2).valor() != 0) {
   			   p.push(new Real(((Real)emp1).valor() / ((Inteiro)emp2).valor()));
            }
            else {
               throw new RobotRuntimeException("Divisao por zero");
            }
  			}
  			else if(emp1 instanceof Real && emp2 instanceof Real) {
            if (((Real)emp2).valor() != 0) {
   			   p.push(new Real(((Real)emp1).valor() / ((Real)emp2).valor()));
            }
            else {
               throw new RobotRuntimeException("Divisao por zero");
            }
	   	}
	   	else {
	   		throw new RobotRuntimeException("Tipo de dado errado para instrucao DIV (deveria ser um par de instâncias de Numero no topo da pilha)");
	   	}
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao DIV - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class RAND extends Instrucao { /* desempilha dois inteiros da pilha, gera um número aleatório inteiro entre os dois (incluindo possivelmente os mesmos) e o empilha */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando RAND."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
         Empilhavel emp2 = p.pop();
   		if(emp1 instanceof Inteiro && emp2 instanceof Inteiro && (((Inteiro)emp1).valor()) >= 0 && (((Inteiro)emp2).valor()) >= 0) {
            p.push(new Inteiro(Aleatorio.randInt(((Inteiro)emp2).valor, ((Inteiro)emp1).valor)));
   		}
   		else {
   			throw new RobotRuntimeException("Tipo de dado errado para instrucao RAND (deveria ser um par de instâncias de Inteiro com valor não negativo)");
   		}
   		return 1;
   	}
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao RAND - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class MOD extends Instrucao {  /* desempilha dois números inteiros do topo da pilha, empilhando no topo da mesma o resto da divisão do número mais "abaixo" pelo número no topo da pilha */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando MOD."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
         Empilhavel emp2 = p.pop();
   		if(emp1 instanceof Inteiro && emp2 instanceof Inteiro && (((Inteiro)emp1).valor()) != 0) {
            p.push(new Inteiro(((Inteiro)emp2).valor % ((Inteiro)emp1).valor));
   		}
   		else {
   			throw new RobotRuntimeException("Tipo de dado errado para instrucao MOD (deveria ser um par de instâncias de Inteiro, sendo a que está no topo não nula)");
   		}
   		return 1;
   	}
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao MOD - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class INT extends Instrucao { /* converte o Numero no topo da pilha para um Inteiro e o empilha */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando INT."); saida.flush();}
      try {
   		Empilhavel emp = p.pop();
   		if(emp instanceof Inteiro) {
            p.push(emp);
   		}
         else if(emp instanceof Real) {
            double real = ((Real)emp).valor();
            p.push(new Inteiro((int)real));
         }
   		else {
   			throw new RobotRuntimeException("Tipo de dado errado para instrucao INT (deveria ser um Numero)");
   		}
   		return 1;
   	}
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao INT - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}


class SQRT extends Instrucao { /* desempilha um Numero e empilha um Real que é sua raiz quadrada */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando SQRT."); saida.flush();}
      try {
   		Empilhavel emp = p.pop();
   		if(emp instanceof Inteiro && (((Inteiro)emp).valor) >= 0) {
            double real = ((Inteiro)emp).valor();
            p.push(new Real(sqrt(real)));
   		}
         else if(emp instanceof Real && (((Real)emp).valor) >= 0) {
            double real = ((Real)emp).valor;
            p.push(new Real(sqrt(real)));
         }
   		else {
   			throw new RobotRuntimeException("Tipo de dado errado para instrucao SQRT (deveria ser um Numero com valor não negativo)");
   		}
   		return 1;
   	}
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao SQRT - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class SWAP extends Instrucao { /* inverte a ordem de empilhamento dos dois objetos no topo da pilha */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando SWAP."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
   		Empilhavel emp2 = p.pop();
 			p.push(emp1);
  			p.push(emp2);
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao SWAP - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class STO extends Instrucao { /* desempilha primeiro um Endereco e depois um Empilhável a ser armazenado neste endereço na memória e faz o armazenamento */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando STO."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
   		Empilhavel emp2 = p.pop();
   		if(emp1 instanceof Endereco) {
            if (((Endereco)emp1).valor < memoria.length) {
   	   		memoria[((Endereco)emp1).valor] = emp2;
            }
            else {
   	   		throw new RobotRuntimeException("Instrucao STO: memória do robô esgotada e/ou endereço fora da faixa de memória do robô");
            }
	   	}
	   	else {
            /* mudamos a ordem de empilhamento dos dados em relação ao enunciado, pois percebemos que isso nos economizaria muitos usos da instrução SWAP */
	   		throw new RobotRuntimeException("Tipo de dado errado para instrucao STO (deveria ser um endereço no topo da pilha e um Empilhavel logo abaixo)");
	   	}
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao STO - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class SETV extends Instrucao { /* tem o efeito combinado das instruções ADR e STO, executadas uma após a outra */
  	SETV(Empilhavel argumento) {
		this.argumento = argumento;
	}
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando SETV."); saida.flush();}
      try {
   		Empilhavel emp = p.pop();
   		if(argumento instanceof Endereco) {
            if (((Endereco)argumento).valor < memoria.length) {
   	   		memoria[((Endereco)argumento).valor] = emp;
            }
            else {
   	   		throw new RobotRuntimeException("Instrucao SETV: memória do robô esgotada e/ou endereço fora da faixa de memória do robô");
            }
	   	}
	   	else {
	   		throw new RobotRuntimeException("Tipo de dado errado para instrucao SETV (deveria ser um endereço no topo da pilha e um Empilhavel logo abaixo)");
	   	}
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao SETV - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class ADR extends Instrucao { /* empilha o endereço com valor igual ao seu argumento (após a ação do montador; antes, são esperados nomes de variáveis) */
	ADR(Empilhavel argumento) {
		this.argumento = argumento;
	}
   int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando ADR."); saida.flush();}
      try {
         if(argumento instanceof Endereco) {
            p.push(argumento);
            return 1;
         }
         else {
            throw new RobotRuntimeException("Tipo de argumento errado para instrucao ADR (deveria ser um Endereco)");
         }
	   }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao ADR - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class PUSHV extends Instrucao { /* tem o efeito combinado das instruções ADR e RCL, executadas uma após a outra */
  	PUSHV(Empilhavel argumento) {
		this.argumento = argumento;
	}
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando PUSHV."); saida.flush();}
      try {
   		if(argumento instanceof Endereco) {
            if (((Endereco)argumento).valor < memoria.length) {
   	   		p.push(memoria[((Endereco)argumento).valor]);
            }
            else {
   	   		throw new RobotRuntimeException("Instrucao PUSHV: memória do robô esgotada e/ou endereço fora da faixa de memória do robô");
            }
	   	}
	   	else {
	   		throw new RobotRuntimeException("Tipo de dado errado para instrucao SETV (deveria ser um endereço no topo da pilha e um Empilhavel logo abaixo)");
	   	}
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao PUSHV - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class RCL extends Instrucao { /* desempilha um endereço da memória (Endereco) e empilha o conteúdo de tal endereço */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando RCL."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
   		if(emp1 instanceof Endereco) {
            if (((Endereco)emp1).valor < memoria.length) {
   	   		p.push(memoria[((Endereco)emp1).valor]);
            }
            else {
   	   		throw new RobotRuntimeException("Instrucao RCL: endereço fora da faixa de memória do robô");
            }
	   	}
	   	else {
	   		throw new RobotRuntimeException("Tipo de dado errado para instrucao RCL (deveria ser um endereço no topo da pilha)");
	   	}
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao RCL - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}


class JIT extends Instrucao { /* desempilha um Inteiro e, caso tal inteiro seja não nulo, realiza um desvio relativo em relação à posição atual do programa igual ao valor de seu argumento; antes da ação do montador, tais desvios relativos são indicados por meio de etiquetas  */
	JIT(Empilhavel argumento) {
		this.argumento = argumento;
	}
	JIT(int x) {
		this.argumento = new DesvioRelativo(x);
	}
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando JIT."); saida.flush();}
		if(argumento instanceof DesvioRelativo) {
         try {
      		Empilhavel emp = p.pop();  
			   if(emp instanceof Inteiro) {
			   	if(((Inteiro)emp).valor != 0) {
			   		return ((DesvioRelativo)argumento).valor();
			   	}
			   	else return 1;
			   }
			   else {
               throw new RobotRuntimeException("Instrucao JIT: o topo da pilha nao contem um Inteiro.");
			   }
		   }
         catch (RobotRuntimeException excecao) {
            String mensagem = excecao.getMessage();
            mensagem = "Instrucao JIT - " + mensagem;
            throw new RobotRuntimeException(mensagem);
         }
      }
		else {
         throw new RobotRuntimeException("Instrucao JIT: esta-se tentando dar um salto usando um argumento que nao eh um DesvioRelativo.");
		}
	}
}

class PUSH extends Instrucao { /* empilha um Inteiro ou Real com valor igual ao seu argumento */
	PUSH(Empilhavel argumento) {
		this.argumento = argumento;
	}
  	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando PUSH."); saida.flush();}
      try {
         p.push(argumento);
         return 1;
	   }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao PUSH - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class JIF extends Instrucao { /* desempilha um Inteiro e, caso tal inteiro seja nulo, realiza um desvio relativo em relação à posição atual do programa igual ao valor de seu argumento; antes da ação do montador, tais desvios relativos são indicados por meio de etiquetas */
	JIF(Empilhavel argumento) {
		this.argumento = argumento;
	}
	JIF(int x) {
		this.argumento = new DesvioRelativo(x);
	}
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando JIF."); saida.flush();}
		if(argumento instanceof DesvioRelativo) {
			try {
      		Empilhavel emp = p.pop();      
   			if(emp instanceof Inteiro) {
   				if(((Inteiro)emp).valor == 0) {
	   				return ((DesvioRelativo)argumento).valor();
	   			}
	   			else return 1;
	   		}
	   		else {
               throw new RobotRuntimeException("Instrucao JIF: o topo da pilha nao contem um Inteiro.");
	   		}
		   }
         catch (RobotRuntimeException excecao) {
            String mensagem = excecao.getMessage();
            mensagem = "Instrucao JIF - " + mensagem;
            throw new RobotRuntimeException(mensagem);
         }
      }
		else {
         throw new RobotRuntimeException("Instrucao JIF: esta-se tentando dar um salto usando um argumento que nao eh um DesvioRelativo.");
		}
	}
}


class JMP extends Instrucao { /* realiza um desvio relativo em relação à posição atual do programa igual ao valor de seu argumento; antes da ação do montador, tais desvios relativos são indicados por meio de etiquetas  */
	JMP(Empilhavel argumento) {
		this.argumento = argumento;
	}
	JMP(int x) {
		this.argumento = new DesvioRelativo(x);
	}
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando JMP."); saida.flush();}
		if(argumento instanceof DesvioRelativo) {
			return ((DesvioRelativo)argumento).valor();
		}
		else {
         throw new RobotRuntimeException("Instrucao JMP: esta-se tentando dar um salto usando um argumento que nao eh um DesvioRelativo.");
		}
	}
}

class POP extends Instrucao { /* desempilha e descarta o objeto no topo da pilha */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando POP."); saida.flush();}
      try {
   		p.pop();
   		return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao POP - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
	}
}

class DUP extends Instrucao { /* duplica no topo da pilha o objeto que já estava em seu topo */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando DUP."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
         if(emp1 instanceof Numero) {
	   		Numero num1 = (Numero) emp1;
	   		if(num1 instanceof Inteiro) {
               p.push(num1);
               p.push(new Inteiro(((Inteiro)num1).valor()));
            }
            else {
               p.push(num1);
               p.push(new Real(((Real)num1).valor()));
            }
         }
         else if(emp1 instanceof InfoDoTerreno || emp1 instanceof Posicao || emp1 instanceof Vizinhanca) {
            /* apenas copia a referência, sem criar um novo objeto, pois não temos operações que modifiquem Vizinhança, InfoDoTerreno ou Posicao*/
            p.push(emp1);
            p.push(emp1);
         }
	   	else {
	   		throw new RobotRuntimeException("Tipo de dado errado para instrucao DUP (só foi implementada para Numero, InfoDoTerreno, Vizinhança e Posicao");
	   	}
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao DUP - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class PRN extends Instrucao { /* se o "logging" está ativado, imprime no log o valor do Numero no topo da pilha */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando PRN."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
   		Numero num1;
   		if(emp1 instanceof Numero) {
   			num1 = (Numero) emp1;
   			if(num1 instanceof Inteiro) {
   				if(queroLog == 1) {saida.println("Imprimindo: " + ((Inteiro)num1).valor()); saida.flush();}
   			}
   			else {
   				if(queroLog == 1) {saida.println("Imprimindo: " + ((Real)num1).valor()); saida.flush();}
   			}
   		}
			else if(emp1 instanceof Cadeia) {
				Cadeia cadeia = (Cadeia) emp1;
				if(queroLog == 1) {saida.println("Imprimindo: " + cadeia.valor()); saida.flush();}
			}
   		else {
   			throw new RobotRuntimeException("Tipo de dado errado para instrucao PRN (no momento, suporte apenas para os tipos Numero e Cadeia)");
   		}
   		return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao PRN - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
	}
}

class SHIFT extends Instrucao { /* desempilha uma vizinhança; se estiver vazia, retorna um Inteiro de valor zero no topo da pilha; caso contrário, retorna três valores, na seguinte ordem, do topo da pilha para posições mais interiores da mesma: um Inteiro de valor 1, um InfoDoTerreno que foi retirado da Vizinhança que estava empilhada, e a Vizinhança que estava empilhada menos o InfoDoTerreno que acabamos de mencionar */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando SHIFT."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
         if(emp1 instanceof Vizinhanca) {
	   		Empilhavel[] retorno = ((Vizinhanca)emp1).shift();
            int j;
            for (j = 0; j < retorno.length; j++) {
               p.push(retorno[j]);
            }
         }
	   	else {
	   		throw new RobotRuntimeException("Tipo de dado errado para instrucao SHIFT (deveria ser Vizinhanca)");
	   	}
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao SHIFT - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}


class VRB extends Instrucao { /* desempilha um InfoDoTerreno e empilha um Inteiro correspondente ao número do exército do robô que está (ou esteve no passado) naquela posição, ou a -1, caso não houvesse robô naquela posição no momento em que foi gerada a Vizinhança */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando VRB."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
         if(emp1 instanceof InfoDoTerreno) {
	   		p.push(new Inteiro(((InfoDoTerreno)emp1).info_robo()));
         }
	   	else {
	   		throw new RobotRuntimeException("Tipo de dado errado para instrucao VRB (deveria ser InfoDoTerreno)");
	   	}
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao VRB - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class VBA extends Instrucao { /* desempilha um InfoDoTerreno e empilha um Inteiro correspondente ao número do exército cuja base se localiza naquela posição, ou a -1, caso não houvesse base naquela posição no momento em que foi gerada a Vizinhança */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando VBA."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
         if(emp1 instanceof InfoDoTerreno) {
	   		p.push(new Inteiro(((InfoDoTerreno)emp1).info_base()));
         }
	   	else {
	   		throw new RobotRuntimeException("Tipo de dado errado para instrucao VBA (deveria ser InfoDoTerreno)");
	   	}
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao VBA - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class VCR extends Instrucao { /* desempilha um InfoDoTerreno e empilha um Inteiro cujo valor é o número de cristais que havia naquela posição no momento em que foi gerada a Vizinhança */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando VCR."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
         if(emp1 instanceof InfoDoTerreno) {
	   		p.push(new Inteiro(((InfoDoTerreno)emp1).n_cristais()));
         }
	   	else {
	   		throw new RobotRuntimeException("Tipo de dado errado para instrucao VCR (deveria ser InfoDoTerreno)");
	   	}
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao VCR - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class VTT extends Instrucao { /* desempilha um InfoDoTerreno e empilha um Inteiro cujo valor indica o tipo de terreno que havia naquela posição no momento em que foi gerada a Vizinhança: 0 eh base, 1 eh terreno plano, 2 eh terreno rugoso, 3 sao residuos de uma base, 4 eh repositorio de cristais (maior probabilidade de ter cristais) */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando VCR."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
         if(emp1 instanceof InfoDoTerreno) {
	   		p.push(new Inteiro(((InfoDoTerreno)emp1).info_tipo()));
         }
	   	else {
	   		throw new RobotRuntimeException("Tipo de dado errado para instrucao VTT (deveria ser InfoDoTerreno)");
	   	}
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao VTT - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class PTR extends Instrucao { /* desempilha um InfoDoTerreno e empilha a Posicao correspondente ao mesmo */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando PTR."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
         if(emp1 instanceof InfoDoTerreno) {
            int[] coordenadas = ((InfoDoTerreno)emp1).coordenadas();
            int coord1 = coordenadas[0];
            int coord2 = coordenadas[1];
	   		p.push(new Posicao(coord1, coord2));
         }
	   	else {
	   		throw new RobotRuntimeException("Tipo de dado errado para instrucao PTR (deveria ser InfoDoTerreno)");
	   	}
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao PTR - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class NMPS extends Instrucao { /* Desempilha um par de instâncias de Inteiro e empilha uma Posicao tendo como número de coluna o valor no topo da pilha e número de linha o valor logo abaixo */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando NMPS."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
         Empilhavel emp2 = p.pop();
         if(emp1 instanceof Inteiro && emp2 instanceof Inteiro) {
            int coord1 = ((Inteiro)emp2).valor;
            int coord2 = ((Inteiro)emp1).valor;
	   		p.push(new Posicao(coord1, coord2));
         }
	   	else {
	   		throw new RobotRuntimeException("Tipo de dado errado para instrucao NMPS (deveria ser um par de instâncias de Inteiro)");
	   	}
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao NMPS - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class PSNM extends Instrucao { /* desempilha uma Posicao e empilha um par de instâncias de Inteiro: primeiro o número de linha e, no topo, o número de coluna da Posicao */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando PSNM."); saida.flush();}
      try {
   		Empilhavel emp = p.pop();
         if(emp instanceof Posicao) {
            int coord1 = ((Posicao)emp).coord1();
            int coord2 = ((Posicao)emp).coord2();
	   		p.push(new Inteiro(coord1));
            p.push(new Inteiro(coord2));
         }
	   	else {
	   		throw new RobotRuntimeException("Tipo de dado errado para instrucao PSNM (deveria ser Posicao)");
	   	}
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao PSNM - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class EQ extends Instrucao { /* desempilha um par de instâncias Numero e empilha um Inteiro de valor 1 ou 0, respectivamente, se os valores dessas duas instâncias forem iguais ou diferentes */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando EQ."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
   		Empilhavel emp2 = p.pop();
   		Numero num1; Numero num2;
   		if(emp1 instanceof Numero && emp2 instanceof Numero) {
   			num1 = (Numero) emp1;
   			num2 = (Numero) emp2;
   			if(num1 instanceof Inteiro && num2 instanceof Inteiro) {
   				if(((Inteiro)num2).valor() == ((Inteiro)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
            else if(num1 instanceof Inteiro && num2 instanceof Real) {
   				if(((Real)num2).valor() == ((Inteiro)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
            else if(num1 instanceof Real && num2 instanceof Inteiro) {
   				if(((Inteiro)num2).valor() == ((Real)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
   			else if(num1 instanceof Real && num2 instanceof Real) {
   				if(((Real)num2).valor() == ((Real)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
   		}
	   	else {
	   		throw new RobotRuntimeException("Tipo de dado errado para instrucao EQ (deveria ser Numero)");
	   	}
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao EQ - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}
 
class NE extends Instrucao { /* desempilha um par de instâncias Numero e empilha um Inteiro de valor 0 ou 1, respectivamente, se os valores dessas duas instâncias forem iguais ou diferentes */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando NE."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
   		Empilhavel emp2 = p.pop();
   		Numero num1; Numero num2;
   		if(emp1 instanceof Numero && emp2 instanceof Numero) {
   			num1 = (Numero) emp1;
   			num2 = (Numero) emp2;
   			if(num1 instanceof Inteiro && num2 instanceof Inteiro) {
   				if(((Inteiro)num2).valor() != ((Inteiro)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
            else if(num1 instanceof Inteiro && num2 instanceof Real) {
   				if(((Real)num2).valor() != ((Inteiro)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
            else if(num1 instanceof Real && num2 instanceof Inteiro) {
   				if(((Inteiro)num2).valor() != ((Real)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
   			else if(num1 instanceof Real && num2 instanceof Real) {
   				if(((Real)num2).valor() != ((Real)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
   		}
	   	else {
	   		throw new RobotRuntimeException("Tipo de dado errado para instrucao NE (deveria ser Numero)");
	   	}
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao NE - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class GT extends Instrucao { /* desempilha um par de instâncias Numero e empilha um Inteiro de valor 0 ou 1, respectivamente, se o valor na segunda posição for maior que o valor no topo da pilha */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando GT."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
   		Empilhavel emp2 = p.pop();
   		Numero num1; Numero num2;
   		if(emp1 instanceof Numero && emp2 instanceof Numero) {
   			num1 = (Numero) emp1;
   			num2 = (Numero) emp2;
   			if(num1 instanceof Inteiro && num2 instanceof Inteiro) {
   				if(((Inteiro)num2).valor() > ((Inteiro)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
            else if(num1 instanceof Inteiro && num2 instanceof Real) {
   				if(((Real)num2).valor() > ((Inteiro)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
            else if(num1 instanceof Real && num2 instanceof Inteiro) {
   				if(((Inteiro)num2).valor() > ((Real)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
   			else if(num1 instanceof Real && num2 instanceof Real) {
   				if(((Real)num2).valor() > ((Real)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
   		}
	   	else {
	   		throw new RobotRuntimeException("Tipo de dado errado para instrucao GT (deveria ser Numero)");
	   	}
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao GT - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class GE extends Instrucao { /* desempilha um par de instâncias Numero e empilha um Inteiro de valor 0 ou 1, respectivamente, se o valor na segunda posição for maior ou igual ao valor no topo da pilha */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando GE."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
   		Empilhavel emp2 = p.pop();
   		Numero num1; Numero num2;
   		if(emp1 instanceof Numero && emp2 instanceof Numero) {
   			num1 = (Numero) emp1;
   			num2 = (Numero) emp2;
   			if(num1 instanceof Inteiro && num2 instanceof Inteiro) {
   				if(((Inteiro)num2).valor() >= ((Inteiro)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
            else if(num1 instanceof Inteiro && num2 instanceof Real) {
   				if(((Real)num2).valor() >= ((Inteiro)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
            else if(num1 instanceof Real && num2 instanceof Inteiro) {
   				if(((Inteiro)num2).valor() >= ((Real)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
   			else if(num1 instanceof Real && num2 instanceof Real) {
   				if(((Real)num2).valor() >= ((Real)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
   		}
	   	else {
	   		throw new RobotRuntimeException("Tipo de dado errado para instrucao GE (deveria ser Numero)");
	   	}
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao GE - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class LT extends Instrucao { /* desempilha um par de instâncias Numero e empilha um Inteiro de valor 0 ou 1, respectivamente, se o valor na segunda posição for menor ou igual ao valor no topo da pilha */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando LT."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
   		Empilhavel emp2 = p.pop();
   		Numero num1; Numero num2;
   		if(emp1 instanceof Numero && emp2 instanceof Numero) {
   			num1 = (Numero) emp1;
   			num2 = (Numero) emp2;
   			if(num1 instanceof Inteiro && num2 instanceof Inteiro) {
   				if(((Inteiro)num2).valor() < ((Inteiro)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
            else if(num1 instanceof Inteiro && num2 instanceof Real) {
   				if(((Real)num2).valor() < ((Inteiro)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
            else if(num1 instanceof Real && num2 instanceof Inteiro) {
   				if(((Inteiro)num2).valor() < ((Real)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
   			else if(num1 instanceof Real && num2 instanceof Real) {
   				if(((Real)num2).valor() < ((Real)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
   		}
	   	else {
	   		throw new RobotRuntimeException("Tipo de dado errado para instrucao LT (deveria ser Numero)");
	   	}
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao LT - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class LE extends Instrucao { /* desempilha um par de instâncias Numero e empilha um Inteiro de valor 0 ou 1, respectivamente, se o valor na segunda posição for menor que o valor no topo da pilha */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando LE."); saida.flush();}
      try {
   		   		Empilhavel emp1 = p.pop();
   		Empilhavel emp2 = p.pop();
   		Numero num1; Numero num2;
   		if(emp1 instanceof Numero && emp2 instanceof Numero) {
   			num1 = (Numero) emp1;
   			num2 = (Numero) emp2;
   			if(num1 instanceof Inteiro && num2 instanceof Inteiro) {
   				if(((Inteiro)num2).valor() <= ((Inteiro)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
            else if(num1 instanceof Inteiro && num2 instanceof Real) {
   				if(((Real)num2).valor() <= ((Inteiro)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
            else if(num1 instanceof Real && num2 instanceof Inteiro) {
   				if(((Inteiro)num2).valor() <= ((Real)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
   			else if(num1 instanceof Real && num2 instanceof Real) {
   				if(((Real)num2).valor() <= ((Real)num1).valor()) {
                  p.push(new Inteiro(1));
               }
               else {
                  p.push(new Inteiro(0));
               }
   			}
   		}
	   	else {
	   		throw new RobotRuntimeException("Tipo de dado errado para instrucao LE (deveria ser Numero)");
	   	}
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao LE - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class NCR extends Instrucao { /* retorna um inteiro igual ao número de cristais que o robô porta consigo */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando NCR."); saida.flush();}
      try {
         p.push(new Inteiro(chamador.numCristais()));
	   	return 1;
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao NCR - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class END extends Instrucao { /* não realiza nada; é apenas um separador de programas */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando END."); saida.flush();}
      return 1;
   }
}

class EOP extends Instrucao {  /* não realiza nada; apenas indica o fim do último programa do arquivo de programas */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando EOP."); saida.flush();}
      return 1;
   }
}

class PDVZ extends Instrucao { /* instrução que realiza uma chamada ao sistema solicitando a vizinhança do robô em questão, a qual é empilhada como um tipo Vizinhanca (um vetor de InfoDoTerreno; note que a vizinhança só inclui informações sobre terrenos até distância um, de modo que terá no máximo 7 elementos, podendo ter menos, se o robô estiver em alguma borda; os InfoDoTerreno contém informações corretas para o momento no que se refere a bases, tipos de terreno e robôs, mas não cristais: os cristais somente são visíveis no InfoDoTerreno correspondente à posição atual do robô */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando PDVZ."); saida.flush();}
      try {
   		Operacao op = new Operacao("PDVZ", null, chamador);
         Servidor servidor = Servidor.getInstance();
         Empilhavel emp[] = servidor.sistema(op);
         if (emp != null) {
            p.push(emp[0]);
	   	   return 1;
         }
         else {
            return 0;
         }
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao PDVZ - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class MVRB extends Instrucao { /* desempilha uma Posicao e realiza uma chamada ao sistema: se a mesma corresponder a uma posição legítima na Arena que não esteja ocupada por um robô, move-se o robô para tal posição; não retorna nada */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando MVRB."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
         if(emp1 instanceof Posicao) {
   		   Operacao op = new Operacao("MVRB", ((Posicao)emp1), chamador);
            Servidor servidor = Servidor.getInstance();
            servidor.sistema(op);
            if (chamador.timerDeCusto == 0) {
      	   	return 1;
            }
            else {
               p.push(emp1);
               return 0;
            }
         }
         else {
            throw new RobotRuntimeException("Tipo de dado errado para instrucao MVRB (deveria ser Posicao)");
         }
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao MVRB - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}

class ATCK extends Instrucao { /* desempilha uma Posicao e realiza uma chamada ao sistema: se a mesma corresponder a uma posição legítima na Arena e contiver um robô, tal robô recebe um ataque; não retorna nada */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando ATCK."); saida.flush();}
      try {
   		Empilhavel emp1 = p.pop();
         if(emp1 instanceof Posicao) {
   		   Operacao op = new Operacao("ATCK", ((Posicao)emp1), chamador);
            Servidor servidor = Servidor.getInstance();
            servidor.sistema(op);
   	   	if (chamador.timerDeCusto == 0) {
      	   	return 1;
            }
            else {
               p.push(emp1);
               return 0;
            }
         }
         else {
            throw new RobotRuntimeException("Tipo de dado errado para instrucao ATCK (deveria ser Posicao)");
         }
      }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao ATCK - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
   }
}



class PGCR extends Instrucao { /* realiza uma chamada ao sistema: se houver cristais na posição atual, pega um deles; não retorna nada */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando PGCR."); saida.flush();}
  		Operacao op = new Operacao("PGCR", null, chamador);
      Servidor servidor = Servidor.getInstance();
      servidor.sistema(op);
     	if (chamador.timerDeCusto == 0) {
  	   	return 1;
      }
      else {
         return 0;
      }
   }
}

class DPCR extends Instrucao { /* realiza uma chamada ao sistema: se o robô portar cristais e houver uma base na posição atual, um deles é depositado na base, alterando seu estado e possivelmente contribuindo para sua destruição; se não houver base, um cristal será deixado no terreno; não retorna nada */
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
      if(queroLog == 1) {saida.println("Executando DPCR."); saida.flush();}
  		Operacao op = new Operacao("DPCR", null, chamador);
      Servidor servidor = Servidor.getInstance();
      servidor.sistema(op);
      if (chamador.timerDeCusto == 0) {
  	   	return 1;
      }
      else {
         return 0;
      }
   }
}

class CALL extends Instrucao {
	CALL(Empilhavel argumento) {
		this.argumento = argumento;
	}
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
		return -3;
	}
}

class RET extends Instrucao {
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
		return -3;
	}
}

class ENTRA extends Instrucao {
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
		chamador.Contexto.push(chamador.frame);
		chamador.frame=new Frame();
		return 1;
	}
}

// Empilha uma variável local
class PUSHLV extends Instrucao {
	PUSHLV(Empilhavel argumento) {
		this.argumento = argumento;
	}
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
		if(queroLog == 1) {saida.println("Executando PUSHLV."); saida.flush();}
      try {
         if(argumento instanceof Endereco) {
            p.push(chamador.frame.get(((Endereco)argumento).valor()));
            return 1;
         }
         else {
            throw new RobotRuntimeException("Tipo de argumento errado para instrucao PUSHLV (deveria ser um Endereco)");
         }
	   }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao PUSHLV - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
	}
}


// Atualiza uma variável local
class SETLV extends Instrucao {
	SETLV(Empilhavel argumento) {
		this.argumento = argumento;
	}
	int executa(Pilha p, Empilhavel[] memoria, Robo chamador, PrintWriter saida, int queroLog) throws RobotRuntimeException {
		if(queroLog == 1) {saida.println("Executando SETLV."); saida.flush();}
      try {
         if(argumento instanceof Endereco) {
				Empilhavel emp1 = p.pop();
            chamador.frame.set(emp1, ((Endereco)argumento).valor());
            return 1;
         }
         else {
            throw new RobotRuntimeException("Tipo de argumento errado para instrucao SETLV (deveria ser um Endereco)");
         }
	   }
      catch (RobotRuntimeException excecao) {
         String mensagem = excecao.getMessage();
         mensagem = "Instrucao SETLV - " + mensagem;
         throw new RobotRuntimeException(mensagem);
      }
	}
}


	/*********************************************************************************

'POP'   |	pop			|
'DUP'   |	duplicate		| uma das mais difíceis, pois tem de funcionar muitos tipos
'ADD'   |	add			| serve também como OR
'SUB'   |	subtract		|
'MUL'   |	multiply		| serve também como AND
'DIV'   |	divide			|
'MOD'   |   module
'RAND'  |   random integer
'INT'   |   conversion to integer
'JMP'   |	jump			|
'JIT'   |	jump if true		|
'JIF'   |	jump if false		|
'EQ'    |	equal			|
'GT'    |	greater than		|
'GE'    |	greater or equal	|
'LT'    |	less than		|
'LE'    |	less or equal		|
'NE'    |	not equal		|
'STO'   |	store			| só pra variáveis/endereços
'RCL'   |	recall			| só pra variáveis/endereços
'PRN'   | 	print			| melhor implementar só pra números, por simplicidade
'END'   |	end			| só separa programas, talvez nem implementemos agora
----------------
Especiais:
'VCR'   |   vê cristais		|InfoDoTerreno --> numero de cristais no terreno 		
'VRB'   |   ve Robo			|InfoDoTerreno --> número do time do robô se tem robo no terreno, e 0 caso contrário 
'VTT'   |   ve tipo de terreno	|InfoDoTerreno --> numero correspondente ao tipo do terreno		(deixaremos bem pro final)
'PTR'   |   posicao do terreno	|InfoDoTerreno --> Posição
'PSNM'  |   posição para número   | posição -> dois números (coordenada i, coordenada j) 
'NMPS'  |  número para posição  | dois números (coordenada i, coordenada j) -> posição 
'SHIFT' |   tira um elemento do array de InfoDoTerreno que é Vizinhanca | vizinhanca --> 1 se a vizinhanca nao esta vazia, 0 caso contrario + um InfoDoTerreno + a vizinhança com um InfoDoTerreno a menos (os dois últimos, apenas caso não seja 0 o primeiro retorno);  		
'VBA'   | vê base  | InfoDoTerreno -> número do time, se for base, ou zero caso contrário
'NCR' | retorna na pilha o número de cristais que o robô porta
------------------
SYSCALL: 'PDVZ' | pede vizinhança | não espera nada; retorna uma Vizinhança (por ora restrita às celulas estritamente adjacentes, começando pela própria)
SYSCALL: 'ATCK' | ataque | retira Posição da pilha, não retorna nada (pra saber se teve sucesso, o robô pode tentar pedir outra Vizinhança)
SYSCALL: 'PGCR' | pega cristais | não espera nada nem retorna nada; só funciona se o robô estiver na posição do cristal
SYSCALL: 'DPCR' | deposita cristais | não espera nada nem retorna nada; se a posição atual é base, deposita os cristais; se não é base, coloca os cristais no terreno
SYSCALL: 'MVRB' | move robô | espera uma Posição no topo da pilha; não retorna nada (pra saber se teve sucesso, o robô poderia pedir outra vizinhança)

		*/

