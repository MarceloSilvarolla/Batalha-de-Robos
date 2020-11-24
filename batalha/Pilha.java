public class Pilha {
    int tamPilha;
    int posTopo;
    Empilhavel[] pilha;
     
    public Pilha() {
        tamPilha = 100;
        posTopo = 0;
        pilha = new Empilhavel[100];
    }
	
   public void esvazia() {
      posTopo = 0;
   }

public void push(Empilhavel emp) throws RobotRuntimeException {
		if(posTopo < tamPilha) {
   		pilha[posTopo++] = emp;
      }
      else {
         throw new RobotRuntimeException("Pilha cheia");
      }
	}


/*	public void push(Empilhavel emp) {
		if(posTopo == tamPilha) {
			Empilhavel[] pilhaMaior = new Empilhavel[2*tamPilha];
			for(int i = 0; i < tamPilha; i++) {
				pilhaMaior[i] = pilha[i];
			}
			pilha = pilhaMaior;
		}
		pilha[posTopo++] = emp;
		tamPilha *= 2;
	}
*/
	public Empilhavel pop() throws RobotRuntimeException {
      if (posTopo == 0) {
         throw new RobotRuntimeException("Pilha vazia");
      }
      else {
   		return pilha[--posTopo];
      }
	}
	
	
}





