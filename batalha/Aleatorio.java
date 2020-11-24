import java.util.Random;

public class Aleatorio {
	private Aleatorio() {
		
	}

	/* Gera um numero pseudo-aleatorio entre min e max, inclusive, com
	distribuicao (pseudo-)uniforme discreta */
	public static int randInt(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}
	

}


