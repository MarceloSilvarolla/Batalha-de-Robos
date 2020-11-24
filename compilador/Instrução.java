// Instrução genérica, não faz nada.
class Instrução {
	Empilhável val;

	Instrução(Empilhável v) {val = v;}
	Instrução() {val = null;}
	int Exec(Computador C) {return 1;} // retorna o incremento do ip
	String Show() {
		String res =  "\t\t\tnew " + this.toString().replaceFirst("@.+","");
		if (val != null) 
			res += "(new " + val.toString().replaceFirst("@.+","").replace("ç","c") + "("+val.Show() + ")),";
		else
			res += "(),";
		return res;
	}
}


// Empilha uma Cadeia com a descrição do Empilhável
class Mostra extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		p.push(new Cadeia(p.pop().Show()));
		return 1;
	} 
}

class PDVZ extends Instrução {
}
class PGCR extends Instrução {
}
class DPCR extends Instrução {
}
class MVRB extends Instrução {
}
class PTR extends Instrução {
}
class VBA extends Instrução {
}
class VCR extends Instrução {
}
class NCR extends Instrução {
}
class VRB extends Instrução {
}
class SHIFT extends Instrução {
}
////////////////////////////////////
// Instruções aritméticas

class ADD extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;

		Empilhável e1 = p.pop();
		Empilhável e2 = p.pop();
		Inteiro n1,n2;
		if (e1 instanceof Inteiro && e2 instanceof Inteiro) {
			n1 = (Inteiro)e1;
			n2 = (Inteiro)e2;
		}
		else n1 = n2 = new Inteiro(0);

		p.push(new Inteiro(n1.val() + n2.val()));
		return 1;
	}
}

class MUL extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável e1 = p.pop();
		Empilhável e2 = p.pop();
		Inteiro n1,n2;
		if (e1 instanceof Inteiro && e2 instanceof Inteiro) {
			n1 = (Inteiro)e1;
			n2 = (Inteiro)e2;
		}
		else n1 = n2 = new Inteiro(0);

		p.push(new Inteiro(n1.val() * n2.val()));
		return 1;
	}
}

class SUB extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável e1 = p.pop();
		Empilhável e2 = p.pop();
		Inteiro n1,n2;
		if (e1 instanceof Inteiro && e2 instanceof Inteiro) {
			n1 = (Inteiro)e1;
			n2 = (Inteiro)e2;
		}
		else n1 = n2 = new Inteiro(0);
		p.push(new Inteiro(n2.val() - n1.val()));
		return 1;
	}
}

class DIV extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável e1 = p.pop();
		Empilhável e2 = p.pop();
		Inteiro n1,n2;
		if (e1 instanceof Inteiro && e2 instanceof Inteiro) {
			n1 = (Inteiro)e1;
			n2 = (Inteiro)e2;
		}
		else n1 = n2 = new Inteiro(1);
		p.push(new Inteiro(n1.val()/n2.val()));
		return 1;
	}
}

////////////////////////////////////////////////////////////////////////
// Lógicas

class GT extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável e2 = p.pop();
		Empilhável e1 = p.pop();
		Inteiro n1,n2;
		if (e1 instanceof Inteiro && e2 instanceof Inteiro) {
			n1 = (Inteiro)e1;
			n2 = (Inteiro)e2;
		}
		else n1 = n2 = new Inteiro(0);
		p.push(new Inteiro(n1.val() > n2.val()? 1:0));
		return 1;
	}
}

class LT extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável e2 = p.pop();
		Empilhável e1 = p.pop();
		Inteiro n1,n2;
		if (e1 instanceof Inteiro && e2 instanceof Inteiro) {
			n1 = (Inteiro)e1;
			n2 = (Inteiro)e2;
		}
		else n1 = n2 = new Inteiro(0);
		p.push(new Inteiro(n1.val() < n2.val()?1:0));
		return 1;
	}
}

class EQ extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável e2 = p.pop();
		Empilhável e1 = p.pop();
		Inteiro n1,n2;
		if (e1 instanceof Inteiro && e2 instanceof Inteiro) {
			n1 = (Inteiro)e1;
			n2 = (Inteiro)e2;
		}
		else n1 = n2 = new Inteiro(0);
		p.push(new Inteiro(n1.val() == n2.val()? 1:0));
		return 1;
	}
}

class GE extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável e2 = p.pop();
		Empilhável e1 = p.pop();
		Inteiro n1,n2;
		if (e1 instanceof Inteiro && e2 instanceof Inteiro) {
			n1 = (Inteiro)e1;
			n2 = (Inteiro)e2;
		}
		else n1 = n2 = new Inteiro(0);
		p.push(new Inteiro(n1.val() >= n2.val()? 1:0));
		return 1;
	}
}

class LE extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável e2 = p.pop();
		Empilhável e1 = p.pop();
		Inteiro n1,n2;
		if (e1 instanceof Inteiro && e2 instanceof Inteiro) {
			n1 = (Inteiro)e1;
			n2 = (Inteiro)e2;
		}
		else n1 = n2 = new Inteiro(0);
		p.push(new Inteiro(n1.val() <= n2.val()? 1:0));
		return 1;
	}
}

class NE extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável e2 = p.pop();
		Empilhável e1 = p.pop();
		Inteiro n1,n2;
		if (e1 instanceof Inteiro && e2 instanceof Inteiro) {
			n1 = (Inteiro)e1;
			n2 = (Inteiro)e2;
		}
		else n1 = n2 = new Inteiro(0);
		p.push(new Inteiro(n1.val() != n2.val()? 1:0));
		return 1;
	}
}

///////////////////////////////////
// Manipulação da pilha

class PUSH extends Instrução {
	PUSH(Empilhável x) {super(x);}
	int Exec(Computador C) {
		Pilha  p = C.p;
		p.push(val);
		return 1;
	}
	String Show() {
		String res =  "\t\t\tnew " + this.toString().replaceFirst("@.+","");
		if (val != null)
			if (val instanceof Cadeia)
				res += "(new " + val.toString().replaceFirst("@.+","") + "("+val.Show().replace("\n","\\n") + ")),";
			else
				res += "(new " + val.toString().replaceFirst("@.+","").replace("ç","c") + "("+val.Show() + ")),";
		else
			res += "(),";
		return res;
	}
}

// Empilha uma variável
class PUSHV extends Instrução {
	PUSHV(Empilhável x) {super(x);}
	int Exec(Computador C) {
		Pilha  p = C.p;
		
		p.push(C.mem[((Endereço)val).val()]);
		return 1;
	}
//	String Show() {
//		String res =  "\t\t\tnew ADR";
//		if (val != null) 
//			res += "(new " + val.toString().replaceFirst("@.+","").replace("ç","c") + "("+val.Show() + ")),";
//		else
//			res += "(),";
//		res += "\n\t\t\tnew RCL(),";
//		return res;
//	}
}

// Atualiza uma variável
class SETV extends Instrução {
	SETV(Empilhável x) {super(x);}
	int Exec(Computador C) {
		Pilha  p = C.p;
		int v = ((Endereço)val).val();
		C.mem[v] = p.pop();
		return 1;
	}
//	String Show() {
//		String res =  "\t\t\tnew ADR";
//		if (val != null) 
//			res += "(new " + val.toString().replaceFirst("@.+","").replace("ç","c") + "("+val.Show() + ")),";
//		else
//			res += "(),";
//		res += "\n\t\t\tnew STO(),";
//		return res;
//	}
}

// Empilha uma variável local
class PUSHLV extends Instrução {
	PUSHLV(Empilhável x) {super(x);}
	int Exec(Computador C) {
		Pilha  p = C.p;
		int end = ((Endereço)val).val();

		Frame f = C.frame;
		Empilhável pp = f.get(end);

		p.push(pp);
		return 1;
	}
}

// Atualiza uma variável local
class SETLV extends Instrução {
	SETLV(Empilhável x) {super(x);}
	int Exec(Computador C) {
		Pilha  p = C.p;
		int v = ((Endereço)val).val();
		Frame f = C.frame;
		Empilhável pp = p.pop();
		f.set(pp,v);
		return 1;
	}
}

// troca os dois elementos do topo
class SWAP extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável e1 = p.pop();
		Empilhável e2 = p.pop();
		p.push(e1);
		p.push(e2);
		
		return 1;
	}
}

class POP extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		p.pop();
		return 1;
	}
}

class DUP extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável dd  = p.pop();
		p.push(dd);
		p.push(dd);
		return 1;
	}
}


////////////////////////////////////
// Controle de fluxo

class JIF extends Instrução {
	JIF(Empilhável x) {super(x);}
	JIF(int n) {super(new Endereço(n));}

	int Exec(Computador C) {
		Pilha  p = C.p;
		Endereço end = (Endereço) val;
		Empilhável e1 = p.pop();
		Inteiro n1;
		if (e1 instanceof Inteiro) {
			n1 = (Inteiro)e1;
		}
		else n1 = new Inteiro(0);
		if (n1.val() == 0)
			return  end.val();
		else return 1;
	}
	String Show() {
		String res =  "\t\t\tnew " + this.toString().replaceFirst("@.+","");
		if (val != null) 
			res += "(new DesvioRelativo("+val.Show() + ")),";
		else
			res += "(),";
		return res;
	}
}

class JMP extends Instrução {
	JMP (Empilhável x) {super(x);}
	JMP (int x) {super(new Endereço(x));}

	int Exec(Computador C) {
		return ((Endereço) val).val();
	}

	String Show() {
		String res =  "\t\t\tnew " + this.toString().replaceFirst("@.+","");
		if (val != null) 
			res += "(new DesvioRelativo("+val.Show() + ")),";
		else
			res += "(),";
		return res;
	}
}

class RET extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável r = p.pop();
		Endereço retorno = (Endereço) p.pop();
		p.push(r);
		return retorno.val();
	}
}

class PRN extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável x = p.pop();
		if (x instanceof Cadeia) 
			System.out.print(((Cadeia) x).v);
		else if (x instanceof Inteiro) 
			System.out.print(((Inteiro) x).val());
		else if (x instanceof Real) 
			System.out.print(((Real) x).val());
		else if (x instanceof Endereço) 
			System.out.print(((Endereço) x).val());
		return 1;
	}
}

class CALL extends Instrução {
	CALL (Empilhável x) {super(x);}
	int Exec(Computador C) {
		Pilha  p = C.p;
		int ip = C.ip;
		Instrução[] Prog = C.Prog;
		Endereço e = (Endereço)Prog[ip].val;
		p.push(new Endereço(ip+1));
		C.roda(e.val());
		return 1;
	}
//  	String Show() {
//		String res =  "\t\t\tnew " + this.toString().replaceFirst("@.+","");
//		if (val != null) 
//			res += "(new " + val.toString().replaceFirst("@.+","").replace("ç","c") + "("+ (Integer.parseInt(val.Show())+1) + ")),"; // adicionamos um por causa do PUSH na posição zero do programa
//		else
//			res += "(),";
//		return res;
//	}
}

class ENTRA extends Instrução {
	int Exec(Computador C) {
		C.Contexto.push(C.frame);
		C.frame=new Frame();
		return 1;
	}
}
