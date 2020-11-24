public class Programas {
	Instrucao[][] prgs = {
		{
			new JMP(new DesvioRelativo(22)),		//	0
			new PUSHLV(new Endereco(0)),		//	1
			new PUSH(new Inteiro(0)),		//	2
			new GE(),		//	3
			new PUSHLV(new Endereco(0)),		//	4
			new PUSHLV(new Endereco(1)),		//	5
			new NE(),		//	6
			new MUL(),		//	7
			new JIF(new DesvioRelativo(11)),		//	8
			new NCR(),		//	9
			new PUSH(new Inteiro(0)),		//	10
			new GT(),		//	11
			new JIF(new DesvioRelativo(7)),		//	12
			new PUSHLV(new Endereco(1)),		//	13
			new PRN(),		//	14
			new PUSHLV(new Endereco(0)),		//	15
			new PRN(),		//	16
			new DPCR(),		//	17
			new JMP(new DesvioRelativo(-9)),		//	18
			new PUSH(new Inteiro(0)),		//	19
			new RET(),		//	20
			new RET(),		//	21
			new PDVZ(),		//	22
			new SHIFT(),		//	23
			new POP(),		//	24
			new VRB(),		//	25
			new SWAP(),		//	26
			new POP(),		//	27
			new SETV(new Endereco(0)),		//	28
			new PUSHV(new Endereco(0)),		//	29
			new PRN(),		//	30
			new PUSH(new Inteiro(0)),		//	31
			new SETV(new Endereco(1)),		//	32
			new PUSH(new Inteiro(1)),		//	33
			new JIF(new DesvioRelativo(85)),		//	34
			new PUSHV(new Endereco(1)),		//	35
			new PUSH(new Inteiro(0)),		//	36
			new EQ(),		//	37
			new JIF(new DesvioRelativo(43)),		//	38
			new PDVZ(),		//	39
			new SHIFT(),		//	40
			new POP(),		//	41
			new VCR(),		//	42
			new SWAP(),		//	43
			new POP(),		//	44
			new PUSH(new Inteiro(0)),		//	45
			new GT(),		//	46
			new NCR(),		//	47
			new PUSH(new Inteiro(9)),		//	48
			new LT(),		//	49
			new MUL(),		//	50
			new JIF(new DesvioRelativo(3)),		//	51
			new PGCR(),		//	52
			new JMP(new DesvioRelativo(-14)),		//	53
			new PDVZ(),		//	54
			new SHIFT(),		//	55
			new POP(),		//	56
			new VBA(),		//	57
			new SWAP(),		//	58
			new POP(),		//	59
			new PUSHV(new Endereco(0)),		//	60
			new ENTRA(),		//	61
			new SETLV(new Endereco(1)),		//	62
			new SETLV(new Endereco(0)),		//	63
			new CALL(new Endereco(1)),		//	64
			new PDVZ(),		//	65
			new SHIFT(),		//	66
			new POP(),		//	67
			new POP(),		//	68
			new SHIFT(),		//	69
			new POP(),		//	70
			new PTR(),		//	71
			new MVRB(),		//	72
			new POP(),		//	73
			new NCR(),		//	74
			new PUSH(new Inteiro(2)),		//	75
			new GT(),		//	76
			new JIF(new DesvioRelativo(3)),		//	77
			new PUSH(new Inteiro(1)),		//	78
			new SETV(new Endereco(1)),		//	79
			new JMP(new DesvioRelativo(-45)),		//	80
			new PUSHV(new Endereco(1)),		//	81
			new PUSH(new Inteiro(1)),		//	82
			new EQ(),		//	83
			new JIF(new DesvioRelativo(34)),		//	84
			new PDVZ(),		//	85
			new SHIFT(),		//	86
			new POP(),		//	87
			new VBA(),		//	88
			new SWAP(),		//	89
			new POP(),		//	90
			new PUSHV(new Endereco(0)),		//	91
			new ENTRA(),		//	92
			new SETLV(new Endereco(1)),		//	93
			new SETLV(new Endereco(0)),		//	94
			new CALL(new Endereco(1)),		//	95
			new PUSH(new Inteiro(0)),		//	96
			new DUP(),		//	97
			new SETV(new Endereco(2)),		//	98
			new PDVZ(),		//	99
			new SHIFT(),		//	100
			new POP(),		//	101
			new VBA(),		//	102
			new SWAP(),		//	103
			new POP(),		//	104
			new PUSHV(new Endereco(0)),		//	105
			new ENTRA(),		//	106
			new SETLV(new Endereco(1)),		//	107
			new SETLV(new Endereco(0)),		//	108
			new CALL(new Endereco(1)),		//	109
			new PUSHV(new Endereco(2)),		//	110
			new NCR(),		//	111
			new PUSH(new Inteiro(0)),		//	112
			new EQ(),		//	113
			new JIF(new DesvioRelativo(3)),		//	114
			new PUSH(new Inteiro(0)),		//	115
			new SETV(new Endereco(1)),		//	116
			new JMP(new DesvioRelativo(-36)),		//	117
			new JMP(new DesvioRelativo(-85)),		//	118
			new END()

		}
	};
	int length() {
		return prgs.length;
	}

	Instrucao[] programaDeIndice(int i) {
		return prgs[i];
	}
}
