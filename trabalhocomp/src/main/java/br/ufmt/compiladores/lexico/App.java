package br.ufmt.compiladores.lexico;

/**
 * Hello world!
 *
 */
public class App {
  public static void main(String[] args) {

    
    Sintatico sintatico = new Sintatico("input.txt");
    sintatico.analise();
      
  }    
}
