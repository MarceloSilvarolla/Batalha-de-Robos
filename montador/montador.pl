#!/usr/bin/perl -s -- -*- mode:cperl -*-
use strict;
use feature "switch";

package Programa;

#construtor para objetos da classe Programa 
sub novo {
  my $p = shift;
  my $self = {};
  $self->{'vetor_de_programa'} = ();
  return bless $self, $p;
}

my @vetor_de_programa;
my %posicoes_das_variaveis; #hash de variaveis (variaveis => posicao da variavel no vetor de memoria)
my %indices_das_labels; 	#hash de labels (label => posicao da label no vetor de programa)
my $conta_variaveis = 1;
my @indices_vetor_de_programa_com_jump; # temos de lembrar onde devemos traduzir as labels para seus valores correspondentes

my $arquivo = 'Programas.java'; #cria arquivo
open(my $fh, '>', $arquivo) or die "Não foi possível abrir o arquivo '$arquivo' $!";
	
	
# implementa o montador de nosso "assembly"
# recebe como argumentos um objeto da classe Programa e uma lista contendo
# as linhas do código fonte
sub monta {

	my $programa = shift @_; 
	my @linhas = @_;
	

 
	print $fh "public class Programas {
	Instrucao[][] prgs = {
		{\n";

	foreach my $linha (@linhas) 
   {
		chomp $linha;
		$linha =~ s/[#].*//;			# removemos comentarios
		$linha =~ s/\s*$//;				# removemos espacos no final da linha
		if ($linha =~ /^\s*$/) { 
			# a linha soh tem espacos, entao nos a ignoramos
		}
		elsif ($linha =~ /^
			((?<label>[A-Z][A-Z0-9_]*)[:])?				# 	label opcional
			\s* 										#	espacos opcionais antes do nome do opcode
			(?<nome_opcode>[A-Z]+)						#	nome do opcode (PUSH ou POP ou ...) 
			$/xi)
      {
			# a linha tem nome de opcode sem argumento (possivelmente tem label antes)
			my @instrucao;
			if (defined $+{label}) { 									# se a linha tem label
				$indices_das_labels{$+{label}} = @vetor_de_programa;	# marcamos seu indice correspondente
			}
			@instrucao = ($+{nome_opcode}, undef);			# instrucao eh um par (opcode, undef)			
			push @vetor_de_programa, \@instrucao; 						# colocamos o endereco da instrucao no vetor de programa
			if($+{nome_opcode} eq 'END' || $+{nome_opcode} eq 'EOP')	# Quando o END ou EOP for encontrado, os hashes de variáveis e labels serão zerados,
			{
				#ao fim de cada programa do arquivo de texto, cria o vetor (em java) e reseta os hashes para criar outros programas
              imprime_e_resseta();
		   }
      }
      elsif ($linha =~ /^
			((?<label>[A-Z][A-Z0-9_]*)[:])?				# label opcional
			\s* 										#	espacos opcionais antes do nome do opcode
			(?<nome_opcode>[A-Z]+)						# nome do opcode (PUSH ou POP ou ...)
			\s+											# pelo menos um espaco
			(?<numero>[-]?([.]\d+|\d+([.]\d*)?))		# numero (float)
			$/xi)
      {
			# a linha tem nome de opcode seguido de argumento numerico (possivelmente tem label antes)
			my @instrucao;
			if(defined $+{label}) {											# se a linha tem label
				$indices_das_labels{$+{label}} = @vetor_de_programa;		# marcamos seu indice correspondente
			}
			@instrucao = ($+{nome_opcode}, $+{numero});		# instrucao eh um par (opcode, numero)
			push @vetor_de_programa, \@instrucao;							# colocamos o endereco da instrucao no vetor de programa
		}
		elsif ($linha =~ /^
		((?<label>[A-Z][A-Z0-9_]*)[:])?					# label opcional
		\s*												# espacos opcionais antes do nome do opcode
		(?<nome_opcode>[A-Z]+)							# nome do opcode (PUSH ou POP ou ...)
		\s+												# pelo menos um espaco
		"(?<string>.*)"									# string (deve vir entre aspas)
		$/xi)
      {
			# a linha tem nome de opcode seguido de argumento string (possivelmente tem label antes)
			my @instrucao;
			if (defined $+{label}) {									# se a linha tem label
				$indices_das_labels{$+{label}} = @vetor_de_programa;	# marcamos seu indice correspondente
			}
			@instrucao = ($+{nome_opcode}, $+{string});	# instrucao eh um par (opcode, string)
			push @vetor_de_programa, \@instrucao;						# colocamos o endereco da instrucao no vetor de programa
		}
		elsif ($linha =~ /^
		((?<label>[A-Z][A-Z0-9_]*)[:])?					# label opcional
		\s*												# espacos opcionais antes do nome do opcode
		(?<nome_opcode>[A-Z]+)							# nome do opcode (PUSH ou POP ou ...)
		\s+												# pelo menos um espaco
		(?<label_argumento>[A-Za-z][A-Za-z0-9_]*)		# label argumento
		$/xi)
      {
			# a linha tem nome de opcode seguido de argumento label (possivelmente tem label antes)
			# logo o opcode eh JMP ou JIT ou JIF (pois sao as unicas instrucoes com argumento label)
			my @instrucao;
			if (defined $+{label}) {									# se a linha tem label
				$indices_das_labels{$+{label}} = @vetor_de_programa;	# marcamos seu indice correspondente
			}
			@instrucao = ($+{nome_opcode}, $+{label_argumento}); # IMPORTANTE! Colocamos
			# temporariamente o proprio nome da label (a string) junto com o opcode do jump
			# apos terminarmos de ler o arquivo "assembly", nos vamos substituir todos os nomes de
			# labels por seus valores numericos correspondentes
			push @indices_vetor_de_programa_com_jump, $#vetor_de_programa + 1; # guardamos o indice do vetor de programa onde deveremos, no final, trocar o nome da label por seu valor numerico correspondente
			push @vetor_de_programa, \@instrucao;
		}
		elsif ($linha =~ /^
		((?<label>[A-Z][A-Z0-9_]*)[:])?					# label opcional
		\s*												# espacos opcionais antes do nome do opcode
		(?<nome_opcode>[A-Z]+)							# nome do opcode (PUSH ou POP ou ...)
		\s+												# pelo menos um espaco
		(?<variavel>[\$][A-Za-z][A-Za-z0-9_]*)			# variável
		$/xi) {
			# a linha tem nome de opcode seguido de argumento variavel (possivelmente tem label antes)

			my @instrucao;
			if ($posicoes_das_variaveis{$+{variavel}} == undef) {			# se a variavel ainda nao foi declarada
				$posicoes_das_variaveis{$+{variavel}} = $conta_variaveis;	# separamos uma posicao de memoria para aloca-la
				print "$conta_variaveis\n";
				$conta_variaveis++;	#indica a posicao da variavel no vetor de memoria
				
			}
			if (defined $+{label}) {									# se a linha tem label
				$indices_das_labels{$+{label}} = @vetor_de_programa;	# marcamos seu indice correspondente
			}
			
			@instrucao = ($+{nome_opcode}, '$'."$posicoes_das_variaveis{$+{variavel}}");
			push @vetor_de_programa, \@instrucao;
		}
		elsif ($linha =~ /^
		((?<label>[A-Z][A-Z0-9_]*)[:])								# label obrigatoria
		$/xi) {
			# a linha tem apenas uma label
			$indices_das_labels{$+{label}} = @vetor_de_programa;	# marcamos seu indice correspondente
		}
		else {
			die "Erro de sintaxe: |$linha|\n";
		}
	}
}

sub imprime_e_resseta() {

	#substitui nomes de labels por seus respectivos enderecos(com desvio relativo)
	foreach my $indice (@indices_vetor_de_programa_com_jump) {
		my $nome_da_label = $vetor_de_programa[$indice]->[1];
		$vetor_de_programa[$indice]->[1] = $indices_das_labels{$nome_da_label} - $indice;
	}
	
	# para cada instrucao do vetor de programa, cria a instrucao no vetor em java
	foreach my $in (@vetor_de_programa)
	{	
		
		if($in->[0] eq "ADR") 
		{
			$in->[1] =~ s/\$([0-9]+)/$1/;# remove o caracter $ do inicio de uma variavel
			$in->[1]--;
			print $fh "			new ADR(new Endereco($in->[1])),\n";

		}
		elsif($in->[0] eq "PUSH")
      {
         if ($in->[1] == int($in->[1]))
   		{
   			print $fh "			new PUSH(new Inteiro($in->[1])),\n";
	   	}
         else {
            print $fh "			new PUSH(new Real($in->[1])),\n";
         }
      }
		elsif(  $in->[0] eq "ADD"  || #Instrucoes que nao pedem argumento
				$in->[0] eq "DUP"  || 
				$in->[0] eq "MUL"  ||
				$in->[0] eq "DIV"  ||
            $in->[0] eq "MOD"  ||
				$in->[0] eq "SUB"  ||
				$in->[0] eq "EQ"   ||
				$in->[0] eq "GT"   ||
				$in->[0] eq "GE"   ||
				$in->[0] eq "LT"   ||
				$in->[0] eq "LE"   ||
				$in->[0] eq "NE"   ||
				$in->[0] eq "STO"  ||
				$in->[0] eq "RCL"  ||
				$in->[0] eq "VCR"  ||
				$in->[0] eq "VRB"  ||
            $in->[0] eq "RAND"  ||
            $in->[0] eq "SQRT"  ||
            $in->[0] eq "INT"  ||
				$in->[0] eq "VTT"  ||
				$in->[0] eq "PTR"  ||
				$in->[0] eq "PSNM" ||
				$in->[0] eq "NMPS" ||
  				$in->[0] eq "PSNM" ||
				$in->[0] eq "SHIFT"||
				$in->[0] eq "VBA"  ||
				$in->[0] eq "PDVZ" ||
				$in->[0] eq "ATCK" ||
				$in->[0] eq "NCR"  ||
				$in->[0] eq "PGCR" ||
				$in->[0] eq "DPCR" ||
				$in->[0] eq "MVRB" ||
				$in->[0] eq "PRN"  ||
            $in->[0] eq "SWAP"  ||
				$in->[0] eq "POP"	)
		{
			print $fh "			new $in->[0](),\n";
		}
		elsif(  $in->[0] eq "JMP"  || #saltos com desvio relativo
				$in->[0] eq "JIT"  ||
				$in->[0] eq "JIF"	)
		{
			print $fh "			new $in->[0](new DesvioRelativo($in->[1])),\n";
		}
		elsif( $in->[0] eq "END") #indica o fim de um programa
		{
			print $fh "			new $in->[0]()\n";
			print $fh "		},\n		{\n";
			
		}
		elsif( $in->[0] eq "EOP") #indica o fim de todos os programas
		{
			print $fh "			new $in->[0]()\n";
			print $fh "\n		}};\n		int length() {\n		return prgs.length;\n		}\n\n	Instrucao[] programaDeIndice(int i) {
		return prgs[i];
	}
}
";
			
		}
	}

	undef %indices_das_labels;						# reseta o hash de labels
	undef %posicoes_das_variaveis;					# reseta o hash de variaveis
   @vetor_de_programa = ();							# reseta o vetor de programa
   @indices_vetor_de_programa_com_jump = ();
   $conta_variaveis = 1;							# zera o contador de variaveis

}

package main;

my $programa = novo Programa;
my $arquivo  =  shift @ARGV;         	  	# Nome do arquivo  

open (ARQ_ASM, "<", $arquivo);          	# Abre o arquivo (suposto escrito corretamente em "assembly") para leitura
my @linhas  =  <ARQ_ASM>; 					# Lê o arquivo e armazena cada linha como um elemento de @linhas

$programa->monta(@linhas);					# Chama o montador passando o código-fonte no vetor @linhas


