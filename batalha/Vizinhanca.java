public class Vizinhanca implements Empilhavel {
   InfoDoTerreno[] terrenos;
   public Vizinhanca(InfoDoTerreno[] terrenos) {
      this.terrenos = terrenos;
   }
   public Empilhavel[] shift() {
      if (terrenos.length == 0) {
         Empilhavel[] retorno = {new Inteiro(0)};
         return retorno;
      }
      else {
         InfoDoTerreno terr_retorna = terrenos[0];
         InfoDoTerreno[] resto_retorna = new InfoDoTerreno[terrenos.length-1];
         int j;
         for (j = 1; j < terrenos.length; j++) {
            resto_retorna[j-1] = terrenos[j];
         }
         Vizinhanca viz_retorna = new Vizinhanca(resto_retorna);
         Empilhavel[] retorno = { viz_retorna, terr_retorna, new Inteiro(1)};
         return retorno;
      }
   }
}
