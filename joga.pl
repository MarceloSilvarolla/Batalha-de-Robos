#!/usr/bin/perl

# Supondo que voce jah tenha um arquivo com codigo na linguagem de alto nivel do EP
# para comecar o jogo tudo o que voce precisa fazer eh executar este script
#
# Uso: perl joga.pl programa_em_alto_nivel numTimes numRobosPorTime numRodadas impressaoDaArenaNoTerminal  rodadasPorImpressao rodadasPorAtualizacaoDosGraficos queroLog [nomeArquivoSaida caminhoParaDiretorioDeArquivoSaida]
#
# Este script irah:
# 1) Copiar o programa_em_alto_nivel para o diretorio compilador
# 2) Mudar para o diretorio compilador
# 3) Compilar Compilador.jj com o JavaCC
# 4) Compilar todo o codigo .java do diretorio compilador
# 5) Chamar o Compilador sobre o programa_em_alto_nivel, de modo a produzir a saida Programas.java
# 6) Mudar para o diretorio pai de compilador (aquele onde estah o script)
# 7) Mover o Programas.java gerado na etapa 5 para dentro do diretorio batalha
# 8) Mudar para o diretorio batalha
# 9) Compilar todo o codigo .java do diretorio batalha
# 10) Executar BatalhaDeRobos com os argumentos passados (isto eh, numTimes numRobosPorTime numRodadas impressaoDaArenaNoTerminal  rodadasPorImpressao rodadasPorAtualizacaoDosGraficos queroLog [nomeArquivoSaida caminhoParaDiretorioDeArquivoSaida]) 


use File::Copy;

if(@ARGV < 8 || @ARGV > 10) {
   print "Uso: perl joga.pl programa_em_alto_nivel numTimes numRobosPorTime numRodadas impressaoDaArenaNoTerminal  rodadasPorImpressao rodadasPorAtualizacaoDosGraficos queroLog [nomeArquivoSaida caminhoParaDiretorioDeArquivoSaida]\n";
   print  "onde: numTimes eh um inteiro de 2 a 4, que é o número de exércitos de robôs";
	print  "numRobosPorTime eh um inteiro de 1 a 100";
	print  "numRodadas eh um inteiro que indica o numero de rodadas que o jogo deve ter (0 para infinitas rodadas)";
	print  "impressaoDaArenaNoTerminal eh um inteiro, podendo ser 0 (se voce nao quer que a arena seja impressa no terminal) ou 1 (se voce quer)";
	print  "rodadasPorImpressao eh um inteiro positivo, dizendo a cada quantas rodadas imprimiremos a arena no terminal\n";
	print  "rodadasPorAtualizacaoDosGraficos eh um inteiro positivo, dizendo a cada quantas rodadas atualizaremos a tela do jogo\n";
	print  "queroLog eh um inteiro, sendo 1 se quero gravar o arquivo de log (ver os dois próximos parâmetros) e 0 caso contrario.\n";
   print  "Caso queroLog seja 1, devem também ser entrados dois parâmetros adicionais:\n";
  	print  "caminhoParaDiretorioDeArquivoSaida eh algo como, por exemplo, /home/usuario/coisas/\n";
   print  "nomeArquivoSaida eh o nome do arquivo (menos o caminho para ele, que deve ter sido entrado no parâmetro anterior) onde serah escrito tudo o que acontecer no jogo, exceto o desenho da arena, que, se for feito, o serah na saida padrao\n";
	print  "\n";
	print  "Para quem ficar impaciente com tantos parâmetros, eis alguns exemplos completos de entrada para o programa (mas talvez voce tenha de mudar o diretorio de gravação do arquivo de log)\n";
	print  "perl joga.pl programa_em_alto_nivel 2 15 0 1 30 30 1 LogDaBatalha.txt ~/\n";
   print  "perl joga.pl programa_em_alto_nivel 3 35 0 1 30 30 0\n";
   print  "perl joga.pl programa_em_alto_nivel 2 4 0 1 300 300 0\n";
   die;
}

if($ARGV[0] =~ m/.*\.(java|jj|pl|class|png)/) {
   die "$ARGV[0] eh um arquivo '.java' ou '.jj' ou '.pl' ou '.class' ou '.png'. Nao aceitamos, para evitar conflitos com nossos arquivos. Altere o nome do seu programa_em_alto_nivel !"
}

# 1
print "joga.pl: Proxima tarefa: 1) Copiar o programa_em_alto_nivel para o diretorio compilador\n";
copy($ARGV[0], "compilador") or die "Falha ao tentar copiar $ARGV[0] para o diretorio compilador: $!";
# 2
print "joga.pl: Proxima tarefa: 2) Mudar para o diretorio compilador\n";
chdir("compilador") or die "Falha ao tentar me mover para o diretorio compilador: $!";
# 3
print "joga.pl: Proxima tarefa: 3) Compilar Compilador.jj com o JavaCC\n";
system("javacc Compilador.jj") == 0 or die "Falha ao tentar compilar Compilador.jj";
# 4
print "joga.pl: Proxima tarefa: 4) Compilar todo o codigo .java do diretorio compilador\n";
system("javac *.java") == 0 or die "Erro ao tentar compilar todos os arquivos .java do diretorio compilador";
# 5
print "joga.pl: Proxima tarefa: 5) Chamar o Compilador sobre o programa_em_alto_nivel, de modo a produzir a saida Programas.java\n";
system("java Compilador < $ARGV[0] > Programas.java") == 0 or die "Falha ao tentar compilar $ARGV[0]";
# 6
print "joga.pl: Proxima tarefa: 6) Mudar para o diretorio pai de compilador (aquele onde estah o script)\n";
chdir("..") or die "Falha ao tentar me mover para o diretorio pai de compilador: $!";
# 7
print "joga.pl: Proxima tarefa: 7) Mover o Programas.java gerado na etapa 5 para dentro do diretorio batalha\n";
move("compilador/Programas.java", "batalha") or die "Falha ao tentar mover compilador/Programas.java para dentro do diretorio batalha: $!";
# 8
print "joga.pl: Proxima tarefa: 8) Mudar para o diretorio batalha\n";
chdir("batalha") or die "Falha ao tentar me mover para o diretorio batalha: $!";
# 9
print "joga.pl: Proxima tarefa: 9) Compilar todo o codigo .java do diretorio batalha\n";
system("javac *.java") == 0 or die "Erro ao tentar compilar todos os arquivos .java do diretorio batalha";
# 10
print "joga.pl: Proxima tarefa: 10) Executar BatalhaDeRobos com os argumentos passados (isto eh, numTimes numRobosPorTime numRodadas impressaoDaArenaNoTerminal  rodadasPorImpressao rodadasPorAtualizacaoDosGraficos queroLog [nomeArquivoSaida caminhoParaDiretorioDeArquivoSaida])\n";
($_, my @argumentosParaBatalhaDeRobos) = @ARGV;
system("java BatalhaDeRobos @argumentosParaBatalhaDeRobos") == 0 or die "Falha ao tentar executar BatalhaDeRobos com os argumentos @argumentosParaBatalhaDeRobos: $!";
