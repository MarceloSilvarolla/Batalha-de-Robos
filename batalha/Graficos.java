import java.awt.*;
import java.awt.event.*;
import java.awt.TexturePaint;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;

public class Graficos extends JFrame{

	Terreno[][] terreno;
	Campo campo;
	
	public Graficos(int larguraDaArena, int alturaDaArena, Terreno[][] mapa) {
		terreno = new Terreno[20][20];
		setTitle("Batalha de Robos");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(760, 670);
		campo = new Campo(21, 750, 700, mapa);
		add(campo);
		setVisible(true);
	}
	
	public void atualiza(Terreno[][] mapa) {
		remove(campo);
		campo = new Campo(21, 750, 700, mapa);
		add(campo);
		setVisible(true);
	}
}

class Celula {
	Polygon p = new Polygon();
	BufferedImage textura;
	Graphics2D Gime;

	Celula(int x, int y, int r, BufferedImage textura) {
		this.textura = textura;

		for (int i = 0; i < 6; i++)
			p.addPoint(x + (int) (r * Math.sin(i * 2 * Math.PI / 6)),
					   y + (int) (r * Math.cos(i * 2 * Math.PI / 6)));
		
		Gime = textura.createGraphics();
	}

	void draw(Graphics g) { 
		Graphics2D g2d = (Graphics2D) g;
		Rectangle r = new Rectangle(0,0,600,600);
		g2d.setPaint(new TexturePaint(textura, r));
		g2d.fill(p);
	}	
}

class Campo extends JPanel {
	Celula[][] cel = new Celula[20][20]; // define a matriz de células do terreno (mapa do jogo)
	int Larg, Alt, Dx, Dy; // largura do terreno, altura do terreno, incremento em x e incremento em y
	BufferedImage base, plano, rugoso,  residuos, repcristal, cristal, cristal2, teste; // texturas a serem carregadas para o terreno
	BufferedImage[][] robo = new BufferedImage[4][5];
	BufferedImage[][] bases = new BufferedImage[4][5];
	BufferedImage[] cristais = new BufferedImage[9];
	Terreno[][] terreno;
	int L;
	int[][] terrenos;
	int[][] robos = new int[20][20];
	
	

	Campo(int L, int W, int H, Terreno[][] mapa) {
		Dx = (int) (2 * L * Math.sin(2 * Math.PI / 6)); // incremento em x para desenhar os hexágonos
		Dy = 3* L/2; // idem para y
		Larg = W; Alt = H;
		this.L = L;
		setBackground( new Color(107, 106, 104) );
		terreno = new Terreno[20][20];
		
			
		for(int i = 0; i < 20; i++)
			for(int j = 0; j < 20; j++)
			{
				terreno[i][j] = mapa[i][j];
				if(mapa[i][j].temRobo)
					robos[i][j] = mapa[i][j].robo.time;
				else
					robos[i][j] = -1;
			}
		
		// cada try..catch que segue carregará uma textura, ou levantará uma exceção que encerrará a aplicação com erro
		try {
			rugoso = ImageIO.read(this.getClass().getResource("rugoso.png"));
		}
		catch (Exception e) {
			System.exit(1);
		}

		try {
			plano = ImageIO.read(this.getClass().getResource("areia2.png"));
		}
		catch (Exception e) {
			System.exit(1);
		}

		try {
			base = ImageIO.read(this.getClass().getResource("b0.png"));
		}
		catch (Exception e) {
			System.exit(1);
		}
		
		try {
			residuos = ImageIO.read(this.getClass().getResource("b0.png"));
		}
		catch (Exception e) {
			System.exit(1);
		}
		
		try {
			repcristal = ImageIO.read(this.getClass().getResource("repcristais.png"));
		}
		catch (Exception e) {
			System.exit(1);
		}
		try {
			cristal = ImageIO.read(this.getClass().getResource("cristal.png"));
		}
		catch (Exception e) {
			System.exit(1);
		}
		try {
			for(int i = 0; i < 9; i++)
				cristais[i] = ImageIO.read(this.getClass().getResource((i+1)+"c.png"));
		}
		catch (Exception e) {
			System.exit(1);
		}

		BufferedImage[] Textura = {base, plano, rugoso, residuos, repcristal}; // array de texturas (valores de enumeração: 0, 1, 2)
		for(int i = 0; i < 4; i++)
		{
		   for(int j = 0; j < 5; j++)
		   {
			try{
				robo[i][j] = ImageIO.read(this.getClass().getResource(20*(j+1) + "t" + (i+1) + ".png"));
				bases[i][j] = ImageIO.read(this.getClass().getResource((i+1) + "b" + 20*(j+1) + ".png"));
			}
			catch (Exception e) {
			System.out.println(e);
			//System.exit(1);
			}
		   }
		}  	
		
		
		int DELTA = 0;
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				// instância das células hexagonais, com as texturas adequadas, e atribuição destas ao mapa (a ser renderizado em paintComponent)
				if(mapa[19-j][i].tipo == 0 && mapa[19-j][i].base.numDeCristais < 5)
				{
					int nCristaisBase = mapa[19-j][i].base.numDeCristais;
					int timeBase = mapa[19-j][i].base.time;
					cel[i][j] = new Celula(DELTA + L + i*Dx, L + j*Dy, L, bases[timeBase][4-nCristaisBase]);
				}					
				else
					cel[i][j] = new Celula(DELTA + L + i*Dx, L + j*Dy, L, Textura[mapa[19-j][i].tipo]); 
				DELTA = DELTA == 0 ? Dx/2 : 0;				
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i < 20; i++) 
		{
			for (int j = 0; j < 20; j++)
			{
				cel[i][j].draw(g); // pinta as células no contexto gráfico
			}
		}
			g2d.drawImage(teste, (int)((0.7)*Math.sqrt(2.955)*21) + 12, ( (int)((18)*(1.48)*L)) -14, null);
		for (int i = 0; i < 20; i++) 
		{
			for (int j = 0; j < 20; j++)
			{
			
				if(terreno[19-i][j].numCristais != 0)
				{	
						if((19-i)%2 == 0)
								g2d.drawImage(cristal,(int)((j + 0.5)*Math.sqrt(2.955)*21) + 7 ,( (int)((i)*(1.48)*L)) , null);
							else
								g2d.drawImage(cristal, (int)((j)*Math.sqrt(2.955)*21) + 7, ( (int)((i)*(1.48)*L)) , null);	
				}
				if(terreno[19-i][j].temRobo)
				{	
					int time = terreno[19-i][j].robo.time;
					int vida = terreno[19-i][j].robo.vida;
					int numCristais = terreno[19-i][j].robo.numCristais;
					if(vida != 0)
					{	if((19-i)%2 == 0)
							g2d.drawImage(robo[time][vida-1],(int)((j + 0.5)*Math.sqrt(2.955)*21) + 7 ,( (int)((i)*(1.48)*L)) , null);
						else
							g2d.drawImage(robo[time][vida-1], (int)((j)*Math.sqrt(2.955)*21) + 7, ( (int)((i)*(1.48)*L)) , null);
					}
					if(numCristais != 0)
					{
						if (numCristais > 9)
							numCristais = 9;
						if((19-i)%2 == 0)
							g2d.drawImage(cristais[numCristais-1],(int)((j + 0.5)*Math.sqrt(2.955)*21) + 12 ,( (int)((i)*(1.48)*L)) -14 , null);
						else
							g2d.drawImage(cristais[numCristais-1], (int)((j)*Math.sqrt(2.955)*21) + 12, ( (int)((i)*(1.48)*L)) -14, null);
						
					}
					
				}
				
			}
		}
		
		    
		
	}
}
