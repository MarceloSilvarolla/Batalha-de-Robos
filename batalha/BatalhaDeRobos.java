import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;

public class BatalhaDeRobos {
	public static void main(String[] args) {
		/*0. Checa numero de argumentos */
		if(args.length != 7 && args.length != 9) {
		   System.out.println("Voce nos deu apenas " + args.length + " argumento(s)" +
		      ". Precisamos de 7 ou 9.");
			imprimeUso();
			System.exit(-1);
		}
		/*1. Faz parsing do numero de times */
		int numTimes = Integer.parseInt(args[0]);
		if(numTimes < 2 || numTimes > 4) {
		   System.out.println("Voce nos deu " + numTimes + " times." +
		      "Aceitamos apenas 2, 3 ou 4.");
			imprimeUso();
			System.exit(-1);
		}
		/*2.1 Faz parsing do numero de robos por time */
		int numRobosPorTime = Integer.parseInt(args[1]);
		if(numRobosPorTime < 1 || numRobosPorTime > 100) {
		   System.out.println("Voce nos deu " + numRobosPorTime + " robos por" +
		      " time. Aceitamos apenas 1 ou 2 ou 3 ou ... ou 100");
			imprimeUso();
			System.exit(-1);
		}
		/*2.2 Faz parsing do numero de rodadas */
		int numRodadas = Integer.parseInt(args[2]);
		if(numRodadas < 0) {
		   System.out.println("Voce nos deu " + numRodadas + " rodadas." +
		      " Aceitamos apenas 0 ou mais.");
		   imprimeUso();
		   System.exit(-1);
		}
		/*2.3 Faz parsing de impressaoDaArenaNoTerminal*/
		int impressaoDaArenaNoTerminal = Integer.parseInt(args[3]);
		if(impressaoDaArenaNoTerminal != 0 && impressaoDaArenaNoTerminal != 1) {
		   System.out.println("Voce nos deu " + impressaoDaArenaNoTerminal +
		   " de impressaoDaArenaNoTerminal, mas nos precisamos que seja 0 ou 1.");
		   imprimeUso();
		   System.exit(-1);
		}
		/*2.4 Faz parsing de rodadasPorImpressao*/
		int rodadasPorImpressao = Integer.parseInt(args[4]);
		if(rodadasPorImpressao <= 0) {
		   System.out.println("Voce nos deu " + rodadasPorImpressao +
		      " de rodadasPorImpressao.");
		   System.out.println("Mas rodadasPorImpressao tem que ser positivo!");
		   imprimeUso();
		   System.exit(-1);
		}
		/*2.5 Faz parsing de rodadasPorAtualizacaoDosGraficos*/
		int rodadasPorAtualizacaoDosGraficos = Integer.parseInt(args[5]);
		if(rodadasPorAtualizacaoDosGraficos <= 0) {
		   System.out.println("Voce nos deu " + rodadasPorAtualizacaoDosGraficos +
		      " de rodadasPorAtualizacaoDosGraficos.");
		   System.out.println("Mas rodadasPorAtualizacaoDosGraficos tem que ser positivo!");
		   imprimeUso();
		   System.exit(-1);
		}
		/*2.6 Faz parsing do queroLog*/
		int queroLog = Integer.parseInt(args[6]);
		if(queroLog != 1 && queroLog != 0) {
		   System.out.println("Voce nos deu " + queroLog + " de queroLog.");
		   System.out.println("Mas queroLog tem que ser 1 ou 0.");
		   imprimeUso();
		   System.exit(-1);
		} else if (queroLog == 1 && args.length != 9) {
		   System.out.println("Voce indicou que quer log, mas nao nos passou" +
		      " nomeArquivoSaida e caminhoParaDiretorioDeArquivoSaida!");
		   imprimeUso();
		   System.exit(-1);
		}
		/*2.7 e 2.8 Faz parsing do nomeArquivoSaida e do caminhoParaDiretorioDeArquivoSaida (se o usuario passou 9 argumentos)*/
		String nomeArquivoSaida = "";
		String caminhoParaDiretorioDeArquivoSaida = "";
      if (args.length == 9) {
         nomeArquivoSaida = args[7];
         caminhoParaDiretorioDeArquivoSaida = args[8];
      }
		/*3. Cria vetor de programas */
		Programas programas = new Programas();
		/*4. Pede para o usuario dizer com que programa cada robo ficara */
		Scanner entradaTeclado = new Scanner(System.in);
		System.out.println("O vetor de programas tem " + programas.length() +
		 " programas.");
		int[] timeDeCadaRobo = new int[numTimes*numRobosPorTime];
		int[] programaDeCadaRobo = new int[numTimes*numRobosPorTime];
      String[] cores = {"verde", "azul", "laranja", "magenta"};
		/*programaDeCadaRobo[k] eh o numero do programa que o k-esimo robo
		executarah durante o jogo*/
		int k = 0;
		for(int iTime = 0; iTime < numTimes; iTime++) {
			for(int iProg = 0; iProg < programas.length(); iProg++) {
			   int numRobos;
			   if(iProg < programas.length() - 1) { // se nao for o ultimo programa, perguntamos
				   System.out.println("Quantos robos do time " + (iTime + 1) + " (" + cores[iTime] + ") devem usar o "
					   + "programa " + (iProg + 1) + "?:");
				   numRobos = entradaTeclado.nextInt();
				   if(!(0 <= numRobos && numRobos <= (iTime+1)*numRobosPorTime - k)) {
					   System.out.println("O numero tem que ser nao-negativo e nao existe " + 
					   	"numero suficiente de robos no time");
					   System.exit(-1);
				   }
		      }
		      else { // se for o ultimo programa, jah sabemos quantos robos terao esse programa
		         numRobos = (iTime+1)*numRobosPorTime - k;
		         System.out.println(numRobos + " robos do time " + (iTime + 1) + " (" + cores[iTime] + ") vao usar o "
		            + "programa " + (iProg + 1) + ".");
		      }
				for(int i = 0; i < numRobos; i++) {
			   	timeDeCadaRobo[k] = iTime;
					programaDeCadaRobo[k] = iProg;
					k++;
				}
			}
		}
	   System.out.println("OK? (De enter para iniciarmos o jogo ou Ctrl+C para encerrar)");
		if(programas.length() > 1)
		   entradaTeclado.nextLine(); // come uma nova linha que jah havia sido digitada
		   // ver http://stackoverflow.com/q/7877529/2153942 para mais detalhes
	   entradaTeclado.nextLine(); // agora sim, espera a entrada do usuario para continuar
		
		/* prepara a escrita no arquivo de log */
      PrintWriter saida = null;
      File diretorio;
      File arquivoSaida;
      if (queroLog == 1) {
   		diretorio = new File(caminhoParaDiretorioDeArquivoSaida);
	   	arquivoSaida = new File(diretorio, nomeArquivoSaida);
      }
      else {
         diretorio = new File("/dev/");
	   	arquivoSaida = new File(diretorio, "null");
      }
	   try{
	      saida = new PrintWriter(arquivoSaida);
	   } 
	   catch (Exception e) {
	      System.out.println("Erro ao tentar preparar a escrita no arquivo" + nomeArquivoSaida + " do diretorio " + caminhoParaDiretorioDeArquivoSaida);
	      System.exit(-1);
	   }

		/*5. Cria o servidor */
      System.out.println("Vamos criar o servidor.");
		Servidor servidor = Servidor.getInstance();
      servidor.inicializaJogo(numTimes, numRobosPorTime, programas,
			timeDeCadaRobo, programaDeCadaRobo, saida, queroLog);
		servidor.joga(numRodadas, impressaoDaArenaNoTerminal, 1, rodadasPorImpressao, rodadasPorAtualizacaoDosGraficos);
		
		
		
		
	}
	
	public static void imprimeUso() {
		System.out.println("Uso: java BatalhaDeRobos numTimes numRobosPorTime numRodadas impressaoDaArenaNoTerminal  rodadasPorImpressao rodadasPorAtualizacaoDosGraficos queroLog [nomeArquivoSaida caminhoParaDiretorioDeArquivoSaida]");
		System.out.println("onde: numTimes eh um inteiro de 2 a 4, que é o número de exércitos de robôs");
		System.out.println("numRobosPorTime eh um inteiro de 1 a 100");
		System.out.println("numRodadas eh um inteiro que indica o numero de rodadas que o jogo deve ter (0 para infinitas rodadas)");
		System.out.println("impressaoDaArenaNoTerminal eh um inteiro, podendo ser 0 (se voce nao quer que a arena seja impressa no terminal) ou 1 (se voce quer)");
		System.out.println("rodadasPorImpressao eh um inteiro positivo, dizendo a cada quantas rodadas imprimiremos a arena no terminal");
		System.out.println("rodadasPorAtualizacaoDosGraficos eh um inteiro positivo, dizendo a cada quantas rodadas atualizaremos a tela do jogo");
		System.out.println("queroLog eh um inteiro, sendo 1 se quero gravar o arquivo de log (ver os dois próximos parâmetros) e 0 caso contrario.");
      System.out.println("Caso queroLog seja 1, devem também ser entrados dois parâmetros adicionais:");
  		System.out.println("caminhoParaDiretorioDeArquivoSaida eh algo como, por exemplo, /home/usuario/coisas/");
      System.out.println("nomeArquivoSaida eh o nome do arquivo (menos o caminho para ele, que deve ter sido entrado no parâmetro anterior) onde serah escrito tudo o que acontecer no jogo, exceto o desenho da arena, que, se for feito, o serah na saida padrao");
		System.out.println("");
		System.out.println("Para quem ficar impaciente com tantos parâmetros, eis alguns exemplos completos de entrada para o programa (mas talvez voce tenha de mudar o diretorio de gravação do arquivo de log)");
		System.out.println("java BatalhaDeRobos 2 15 0 1 30 30 1 LogDaBatalha.txt ~/");
      System.out.println("java BatalhaDeRobos 3 35 0 1 30 30 0");
      System.out.println("java BatalhaDeRobos 4 100 0 1 30 30 0");
      System.out.println("java BatalhaDeRobos 2 4 0 1 300 300 0");
	}
}
