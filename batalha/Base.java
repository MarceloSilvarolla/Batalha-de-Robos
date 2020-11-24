import java.util.Set;
import java.util.HashSet;


public class Base {
	int time;
	int numDeCristais;
	int largura;
	int altura;
	Set<Posicao> posicoesOcupadas;
	
	
	public Base(int time, int largura, int altura) {
		this.time = time;
		this.numDeCristais = 0;
		this.largura = largura;
		this.altura = altura;
		posicoesOcupadas = new HashSet<Posicao>();
	}
	
	
	/*gets*/
	public int time() {
		return this.time;
	}
	
	public int numDeCristais() {
		return this.numDeCristais;
	}
	
	public int largura() {
		return this.largura;
	}
	
	public int altura() {
		return this.altura;
	}
	
	public Set<Posicao> posicoes() {
		return this.posicoesOcupadas;
	}
	
	
	/*sets*/
	public void poeCristais(int n) {
		this.numDeCristais += n;
	}
	
	public void poePosicao(Posicao pos) {
	   System.out.println("Colocando posicao (" + pos.coord1() + ", " + pos.coord2() + ") na base " + this.time);
		this.posicoesOcupadas.add(pos);
	}
}
