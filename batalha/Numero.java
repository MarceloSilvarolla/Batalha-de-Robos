abstract public class Numero implements Empilhavel {
}

/* class Booleano extends Numero implements Empilhavel {
	boolean valor;
	Booleano(boolean b) {
		valor = b;
	}
	boolean valor() {
		return valor;
	}
} */

class Inteiro extends Numero implements Empilhavel {
	int valor;
	Inteiro(int n) {
		valor = n;
	}
	int valor() {
		return valor;
	}
}

class Real extends Numero implements Empilhavel {
	double valor;
	Real(double x) {
		valor = x;
	}
	double valor() {
		return valor;
	}
}
