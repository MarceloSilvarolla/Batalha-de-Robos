public class Operacao{
   String codigo;
   Empilhavel operando;
   Robo chamador;
   Operacao(String codigo, Empilhavel operando, Robo chamador) {
      this.codigo = codigo;
      this.operando = operando;
      this.chamador = chamador;
   }
   
   /* gets */
   public String codigo() {
      return this.codigo;
   }
   
   public Empilhavel operando() {
      return this.operando;
   }
   
   public Robo chamador() {
      return this.chamador;
   }
}
