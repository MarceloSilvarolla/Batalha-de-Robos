class Pilha {
	private Empilhável[] Stack;
	private int topo=0;

	Pilha(int n) {
		Stack = new Empilhável[n];
		topo=0;
	}

	void push(Empilhável x) {
		Stack[topo++] = x;
	}

	Empilhável pop() {
		return Stack[--topo];
	}
	void Mostra() {
		for (int i=0; i < topo; i++) 
			System.out.println(Stack[i]);
	}
}

