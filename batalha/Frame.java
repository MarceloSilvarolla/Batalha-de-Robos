// Instância de execução de função
public class Frame {
	Empilhavel[] loc;			// memória local à função

	Frame(int n) {loc = new Empilhavel[n];}
	Frame()   	 {this(10);}

	Empilhavel get(int n) {
		return loc[n];
	}
	Empilhavel set(Empilhavel e, int n) {
		return loc[n] = e;
	}
}
