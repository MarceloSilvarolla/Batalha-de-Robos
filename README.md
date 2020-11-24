A inicialização do jogo é feita através de um script em Perl, o uso é:  
perl joga.pl programa_em_alto_nivel numTimes numRobosPorTime numRodadas impressaoDaArenaNoTerminal  rodadasPorImpressao rodadasPorAtualizacaoDosGraficos queroLog [nomeArquivoSaida caminhoParaDiretorioDeArquivoSaida]

onde,
- programa_em_alto_nivel é o arquivo contendo código do programa em alto nível a ser compilado;
- numTimes é o número de times no jogo, no mínimo 2 e no máximo 4;
- numRobosPorTime é o número de robôs por time, no máximo 100;
- numRodadas é o número de rodadas do jogo, 0 para infinito;
- impressaoDaArenaNoTerminal decide se a arena será impressa no terminal. 0 para 'não' e 1 para 'sim';
- rodadasPorImpressao é o número de rodadas entre atualizações da impressão da arena no terminal
- rodadasPorAtualizacaoDosGraficos é o número de rodadas entre atualizações da impressão da arena no modo gráfico;
- queroLog decide se será criado um arquivo de log. 0 para 'não' e 1 para 'sim';
- nomeArquivoSaida é o nome que será dado ao arquivo de saída
- caminhoParaDiretorioDeArquivoSaida é o diretório onde será salvo o arquivo de saída
