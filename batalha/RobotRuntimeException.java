public class RobotRuntimeException extends Exception { /* apenas para separar os erros
 que ocorrem durante a execução do programa em assembly. Idealmente, deveriam
acontecer apenas porque o usuário montou um programa em assembly com erros */
   RobotRuntimeException (String str) {
      super(str);   /* usa o construtor da superclasse */
   }
}


