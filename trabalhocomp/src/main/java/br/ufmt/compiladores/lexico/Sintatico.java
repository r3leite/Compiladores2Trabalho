
package br.ufmt.compiladores.lexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.Scanner;


public class Sintatico {
    
    private LexScanner scan;
    private String simbolo;
    private int tipo;
    private Map<String, Simbolo> tabelaSimbolos = new HashMap<>();
    private Map<String, Simbolo> tabelaSimbolosB = new HashMap<>();
    private int verificador = 0;
    
    private Stack<String> C = new Stack<String>();
    private ArrayList<Float> D = new ArrayList<Float>();
    private int i = -1;
    private int s;
    private int nulo = 0;
    private int var = -1;
    private int qnt_prc = 0;
    private String aux_a, aux_b = "start", prc_tempo;
    
    public Sintatico(String arq){
        scan = new LexScanner(arq);
    }
    
    public void analise(){
        obtemSimbolo();
        programa();
        
        if (simbolo.equals("")){
            System.out.println("Cadeia verificada com sucesso!!");
            System.out.println("Pilha executada: ");
            //System.out.println(C);
            //interpretador();
            System.out.println("Interpretador executado com exito!");
        } else {
             throw new RuntimeException ("Erro Sintático, esperado fim de cadeia");
        }
    
    }
    
    public void interpretador (){
        int s = 0;
        for(int x = 0; x < C.size(); x++){
            String instrucao = C.get(x);
            String nomes = instrucao;
            
            float int_real = 0;
            if (instrucao.contains(" ")){
                int finalnomes = instrucao.indexOf(" ");
                int startint_real = instrucao.indexOf(" ");
                
                nomes = instrucao.substring(0, finalnomes);
                int_real = Float.parseFloat(instrucao.substring(startint_real));
            
            }
            
            switch (nomes){
                case "CRCT":
                    s++;
                    D.add((float) int_real);
                    break;
                case "CRVL":
                    s++;
                    D.add(D.get((int) int_real));
                    break;
                case "SOMA":
                    D.set(s-1, D.get(s-1) + D.get(s));
                    D.remove(s);
                    s--;
                    break;
                case "SUBT":
                    D.set(s-1, D.get(s-1) - D.get(s));
                    D.remove(s);
                    s--;
                    break;
                case "MULT":
                    D.set(s-1, D.get(s-1) * D.get(s));
                    D.remove(s);
                    s--;
                    break;
                case "DIVI":
                    D.set(s-1, D.get(s-1) / D.get(s));
                    D.remove(s);
                    s--;
                    break;
                case "INVE":
                    D.set(s, - D.get(s));
                    break;
                case "CPME":
                    if(D.get(s-1) < D.get(s)){
                        D.set(s-1, (float) 1);
                    }else{
                        D.set(s-1, (float) 0);
                    }
                    D.remove(s);
                    s--;
                    break;
                case "CPMA":
                    if(D.get(s-1) > D.get(s)){
                        D.set(s-1, (float) 1);
                    }else{
                        D.set(s-1, (float) 0);
                    }
                    D.remove(s);
                    s--;
                    break;
                case "CPIG":
                    if(D.get(s-1).equals(D.get(s))){
                        D.set(s-1, (float) 1);
                    }else{
                        D.set(s-1, (float) 0);
                    }
                    D.remove(s);
                    s--;
                    break;
                    
                case "CDES":
                    if(!D.get(s-1).equals(D.get(s))){
                        D.set(s-1, (float) 1);
                    }else{
                        D.set(s-1, (float) 0);
                    }
                    D.remove(s);
                    s--;
                    break;
                case "CPMI":
                    if(D.get(s-1) <= D.get(s) ){
                        D.set(s-1, (float) 1);
                    }else{
                        D.set(s-1, (float) 0);
                    }
                    D.remove(s);
                    s--;
                    break;
                case "CMAI":
                    if(D.get(s-1) >= D.get(s) ){
                        D.set(s-1, (float) 1);
                    }else{
                        D.set(s-1, (float) 0);
                    }
                    D.remove(s);
                    s--;
                    break;
                case "ARMZ":
                    D.set((int) int_real, D.get(s));
                    D.remove(s);
                    s--;
                    break;
                case "DSVI":
                    x = (int) int_real - 1;
                    break;
                case "DSVF":
                    if(D.get(s) == 0){
                        x = (int) int_real - 1;
                    }
                    
                    D.remove(s);
                    s--;
                    break;
                case "LEIT":
                    s++;
                    Scanner read = new Scanner(System.in);
                    float numero = read.nextFloat();
                    D.add(numero);
                    break;
                case "IMPR":
                    System.out.println(D.get(s));
                    D.remove(s);
                    s--;
                    break;
                case "ALME":
                    for(int j = 0; j < int_real; j++){
                        D.add((float) 0);
                        
                    }
                    s += int_real;
                    break;
                case "PARAM":
                    s++;
                    D.add(D.get((int) int_real));
                    break;
                case "PUSHER":
                    s++;
                    D.add((float) int_real);
                    break;
                case "CHPR":
                    x = (int) int_real - 1;
                    break;
                case "DESM":
                    for(; int_real > 0; int_real--){
                        D.remove(s);
                        s--;
                    }
                    break;
                case "RTPR":
                    x = (int) (float) D.get(s) - 1;
                    
                    D.remove(s);
                    s--;
                    break;
                case "INPP":
                    s = -1;
                    break;
                case "PARA":
                    return;
                
                default: 
                    throw new RuntimeException("Simbolo nao localizado: " +simbolo);
                    
            
            
            
            
            
            
            }
            
        
        
        
        
        
        
        
        }
    
    
    
    
    }
    
    private void obtemSimbolo() {
        Token token = scan.nextToken();
        simbolo = "";
        if (token != null ) {
         simbolo = token.getTermo();
         tipo = token.getTipo();
         System.out.println(simbolo);
        }
       
    }
    
    private void programa(){
        if (simbolo.equals("program")){
            obtemSimbolo();
            i += 1;
            C.push("INPP");
            if (tipo == Token.IDENT){
                obtemSimbolo();
                
                
                
                corpo();
                if (simbolo.equals(".")){
                    obtemSimbolo();
                    
                   
                    
                } else {
                    throw new RuntimeException("Erro sintático, esperado '.'");
                }
            } else {
               throw new RuntimeException ("Erro Sintático, esperado identificador");
            }
           
        
        
        
        
        } else {
               throw new RuntimeException ("Erro Sintático, esperado program");
           } 
    }
    
    private void corpo(){
        dc();
        if (simbolo.equals("begin")){
            obtemSimbolo();
            comandos();
            if (simbolo.equals("end")){
                i += 1;
                C.push("PARA");
                System.out.println(C);
                obtemSimbolo();
            } else {
               throw new RuntimeException ("Erro Sintático, esperado end");
           }
        } else {
               throw new RuntimeException ("Erro Sintático, esperado begin");
           }
            
    
    }
    
    private void dc(){
        if(simbolo.equals("real") || simbolo.equals("integer")){
             dc_v();
             mais_dc();
             
          } else if (simbolo.equals("procedure")){
              i += 1;
              C.push("DSVI lineproc");
              dc_p();
              
              inserelinha("DSVI lineproc", "DSVI "+Integer.toString(i+1));
              
              
          }
    }    
    
    private void mais_dc(){
        if(simbolo.equals(";")){
            obtemSimbolo();
            dc();
        }  
    }
            
    
    
    private void dc_v(){
        tipo_var();
        if (simbolo.equals(":")){
            obtemSimbolo();
            variaveis();
        
        } else {
            throw new RuntimeException ("Erro Sintático, esperado ':' ");
          }
    }
    
    private void tipo_var(){
        
        if (simbolo.equals("real")){
            obtemSimbolo();
            
        } else if (simbolo.equals("integer")){
            obtemSimbolo();
        } 
       

    }
    
    private void variaveis(){
        if (tipo != Token.IDENT){
           throw new RuntimeException ("Erro Sintático, esperado identificador"); 
        } 
        if (verificador == 0){
           if(tabelaSimbolos.containsKey(simbolo)){
             throw new RuntimeException("Erro semantico, esperado "+simbolo);
           }else {
               var += 1;
               i += 1;
               C.push("ALME " +var);
               
               tabelaSimbolos.put(simbolo,new Simbolo(this.tipo, simbolo, var));
            }
        
        
        
        } else if (verificador == 1){
            if(tabelaSimbolosB.containsKey(simbolo)){
              throw new RuntimeException("Erro semantico, esperado "+simbolo );
            } else {
                qnt_prc++;
                var += 1;
                i += 1;
                C.push("ALME " +var);
               
                tabelaSimbolosB.put(simbolo, new Simbolo(this.tipo, simbolo, var));
              }
        }    
        obtemSimbolo();
        mais_var();
    }
    
    private void mais_var(){
        if (simbolo != null){
            if (simbolo.equals(",")){
                obtemSimbolo();
                variaveis();
            } 
        
        } 
    }
    
    private void dc_p(){
        if (simbolo.equals("procedure")){
            obtemSimbolo();
            verificador = 1;
            if (tipo == Token.IDENT) {
                tabelaSimbolosB.put(simbolo, new Simbolo(this.tipo, simbolo, -3, i+1));
                tabelaSimbolos.put(simbolo, new Simbolo(this.tipo, simbolo, -3, i+1));
             
                obtemSimbolo();
                parametros();
                corpo_p();
            } else {
                throw new RuntimeException("Erro sintático, era esperado um identificador");
            }
        } else {
            throw new RuntimeException("Erro sintático era esperado 'procedure'");
        }
    
    } 
    
    private void parametros(){
        if (simbolo.equals("(")){
            obtemSimbolo();
            lista_par();
            if (simbolo.equals(")")){
              obtemSimbolo();
            } else {
                throw new RuntimeException("Erro sintático era esperado ')'");
            }
        
        
        } 
    } 
    
    private void lista_par() {
        tipo_var();
        if (simbolo.equals(":")) {
            obtemSimbolo();
            variaveis();
            mais_par();
        } else {
            throw new RuntimeException("Erro sintático era esperado ':'");
        }
    
    
    } 
    
    private void mais_par(){
        if (simbolo.equals(";")){
            obtemSimbolo();
            lista_par();
        } 
    } 
    
    private void corpo_p(){
        dc_loc();
        if (simbolo.equals("begin")){
           obtemSimbolo();
           comandos();
           
           if (simbolo.equals("end")){
              i += 1;
              C.push("DESM "+Integer.toString(qnt_prc));
              qnt_prc = 0;
              i += 1;
              C.push("RTPR");
              obtemSimbolo();
              verificador = 0;
           } else {
               throw new RuntimeException("Erro sintático era esperado 'end'");
           }
        } else {
            throw new RuntimeException("Erro sintático era esperado 'begin'");
        }
    
    } 
    
    private void dc_loc() {
        if(simbolo.equals("real") || simbolo.equals("integer")){
            dc_v();
            mais_dcloc();
        }
    } 
    
    private void mais_dcloc(){
        if (simbolo.equals(";")){
            obtemSimbolo();
            dc_loc();
        } 
    
    } 
    
    private void lista_arg(){
        if (simbolo.equals("(")){
            obtemSimbolo();
            argumentos();
            if (simbolo.equals(")")) {
               obtemSimbolo();
            } else {
                throw new RuntimeException("Erro sintático era esperado ')'");
            }   
        } 
    } 
    
    private void argumentos(){
        if (tipo == Token.IDENT && verificador == 0){
          if(tabelaSimbolos.containsKey(simbolo)) {
            String keep_nam = simbolo;
            obtemSimbolo();
            i += 1;
            C.push("PARAM "+tabelaSimbolos.get(keep_nam).getEnd_rel());
            mais_ident();
          } 
        } else if (tipo == Token.IDENT && verificador == 0) {
            if (tabelaSimbolosB.containsKey(simbolo)){
                String keep_nam = simbolo;
                obtemSimbolo();
                i += 1;
                C.push("PARAM "+tabelaSimbolosB.get(keep_nam).getEnd_rel());
                mais_ident();
            }
        } else {
            throw new RuntimeException("Erro semantico, o identificador nao foi declarado");
          }
    
    
    } 
    
    private void mais_ident(){
        if (simbolo.equals(",")){
            obtemSimbolo();
            argumentos();
        }
    
    }
    
    
    private void comandos(){
         comando();
         mais_comandos();
        
     }
    
    private void mais_comandos(){
        if (simbolo.equals(";")){
            obtemSimbolo();
            comandos();
        } 
        
     }
        
    
    
    private void comando(){
        if (simbolo.equals("read")){
          i += 1;  
          C.push("LEIT");
          obtemSimbolo();
          if (simbolo.equals("(")){
            obtemSimbolo();
            if (tipo == Token.IDENT && verificador == 0){
              if(tabelaSimbolos.containsKey(simbolo)){
                i += 1;
                C.push("ARMZ " +tabelaSimbolos.get(simbolo).getEnd_rel());
               
                obtemSimbolo();
                if (simbolo.equals(")")){
                  obtemSimbolo();
                } else {
                    throw new RuntimeException ("Erro sintatico, esperado o ')' ");
                  } 
              } else {
                  throw new RuntimeException ("Erro semantico, o identificador nao foi declarado");
                }
            } else if (tipo == Token.IDENT && verificador == 1){
                 if(tabelaSimbolosB.containsKey(simbolo)){
                     i += 1;
                     C.push("ARMZ " +tabelaSimbolos.get(simbolo).getEnd_rel());
                     obtemSimbolo();
                   if (simbolo.equals(")")){
                     obtemSimbolo();
                   } else {
                       throw new RuntimeException ("Erro sintatico, esperado o ')'");
                     }
                 } else {
                     throw new RuntimeException ("Erro semantico, o identificador nao foi declarado");
                   }
            
            } else {
                throw new RuntimeException ("Erro sintatico, era esperado um identificador");
              }
         } else{
             throw new RuntimeException ("Erro sintatico, era esperado '('"); 
           }
        } else if (simbolo.equals("write")){
              obtemSimbolo();
              if (simbolo.equals("(")){
                obtemSimbolo();
                if (tipo == Token.IDENT && verificador == 0){
                  if(tabelaSimbolos.containsKey(simbolo)){
                      i += 1;
                      C.push("CRVL " +tabelaSimbolos.get(simbolo).getEnd_rel()); 
                      i += 1;
                      C.push("IMPR");
                      obtemSimbolo();
                   if (simbolo.equals(")")){
                     obtemSimbolo();
                   } else {
                       throw new RuntimeException ("Erro sintatico, era esperado o ')'");
                     }
                  } else {
                      throw new RuntimeException("Erro semantico, o identificador nao foi declarado");
                    }
                } else if (tipo == Token.IDENT && verificador == 1){
                    if (tabelaSimbolosB.containsKey(simbolo)){
                      i += 1;  
                      C.push("CRVL " +tabelaSimbolosB.get(simbolo).getEnd_rel()); 
                      i += 1;
                      C.push("IMPR");
                      obtemSimbolo();
                      if (simbolo.equals(")")){
                        obtemSimbolo();
                      } else {
                          throw new RuntimeException("Erro sintatico, esperado o ')'");
                        }
                    } else {
                        throw new RuntimeException("Erro semantico, o identificador nao foi declarado");
                      }
                  }
              } else {
                  throw new RuntimeException("Erro sintatico, esperado o '('");
                }
         } else if (tipo == Token.IDENT) {
            if (verificador == 0){
              if(tabelaSimbolos.containsKey(simbolo)){
                aux_a = simbolo;
                obtemSimbolo();
                restoIdent();
                
                int num = tabelaSimbolos.get(aux_a).getEnd_rel();
                if(num >= 0){
                    i += 1;
                    C.push("ARMZ "+tabelaSimbolos.get(aux_a).getEnd_rel());
                } else {
                    i +=1;
                    C.push("CHPR "+tabelaSimbolos.get(aux_a).getPrim_instr());
                
                }
                
               
              } else {
                  throw new RuntimeException("Erro semantico, o identificador nao foi declarado");
                }
            } else if (verificador == 1){
                if(tabelaSimbolosB.containsKey(simbolo)){
                  aux_a = simbolo;
                  obtemSimbolo();
                  restoIdent();
                  
                  int num = tabelaSimbolosB.get(aux_a).getEnd_rel();
                  if(num >= 0){
                    i += 1;
                    C.push("ARMZ "+tabelaSimbolosB.get(aux_a).getEnd_rel());
                  } else {
                    i +=1;
                    C.push("CHPR "+tabelaSimbolosB.get(aux_a).getPrim_instr());
                
                   }
                } else {
                    throw new RuntimeException("Erro semantico, o identificador nao foi declarado");
                  } 
              }
            } else if (simbolo.equals("if")){
               obtemSimbolo();
               condicao();
               if (simbolo.equals("then")){
                 i += 1;
                 C.push("DSVF numlinha");
                 obtemSimbolo();
                 
                 comandos();
                 
                 i += 1;
                 C.push("DSVI numlinha");
                 int if_fim = i + 1;
                 
                 pfalsa();
                 
                 int else_fim = i + 1;
                 
                 inserelinha("DSVF numlinha", "DSVF "+Integer.toString(if_fim));
                 inserelinha("DSVI numlinha", "DSVI "+Integer.toString(else_fim));
              
                 if (simbolo.equals("$")){
                     obtemSimbolo();
                 } else throw new RuntimeException("Erro sintático era esperado '$'");
               }
              } else if (simbolo.equals("while")){
                  obtemSimbolo();
                  int aux_des = i + 1;
                 
                  condicao();
                  if(simbolo.equals("do")){
                     C.push("DSVF desvio");
                     
                     obtemSimbolo();
                     comandos();
                     C.push("DSVI "+aux_des);
                     inserelinha("DSVF desvio", "DSVF "+Integer.toString(i+1));
                     
                     if(simbolo.equals("$")){
                       obtemSimbolo();
                     
                     } else throw new RuntimeException("Erro sintático era esperado '$'");
                  
                  } else throw new RuntimeException("Erro sintático era esperado 'do'");
                   
            } else throw new RuntimeException("Erro sintático era esperado 'read' ou 'write ou 'if' ou 'while'");
    }    
    
    private void restoIdent(){
        if (simbolo.equals(":=")){
            obtemSimbolo();
            expressao();
            
            if(aux_b.equals("-")){
                C.push("SUBT");
            }else if(aux_b.equals("+")){
                C.push("SOMA");
            }else if (aux_b.equals("*")){
                C.push("MULT");
            }else if (aux_b.equals("/")){
                C.push("DIVI");
            }else if (aux_b.equals("=")){
                C.push("CPIG");
            }else if (aux_b.equals("<>")){
                C.push("CDES");
            }else if (aux_b.equals(">=")){
                C.push("CMAI");
            }else if (aux_b.equals("<=")){
                C.push("CPMI");
            }else if (aux_b.equals(">")){
                C.push("CPMA");
            }else if (aux_b.equals("<")){
                C.push("CPME");
            } 
            aux_b = "";
        }   else{
                i += 1;
                C.push("PUSHER linepusher");
           // prc_tempo = simbolo;
            
                lista_arg();
                inserelinha("PUSHER linepusher", "PUSHER "+Integer.toString(i+1));
            }
        
    
      }
    
        
    
    private void condicao(){
        expressao();
        relacao();
        expressao();
    }
    
    private void relacao(){
        if (tipo == Token.RELACAO){
           if (simbolo.equals("=")){
                aux_b = simbolo;
                obtemSimbolo();
           } else if (simbolo.equals("<>")){
               aux_b = simbolo;
               obtemSimbolo();
           } else if (simbolo.equals(">=")){
               aux_b = simbolo;
               obtemSimbolo();
           } else if (simbolo.equals("<=")){
               aux_b = simbolo;
               obtemSimbolo();
           } else if (simbolo.equals(">")){
               aux_b = simbolo;
               obtemSimbolo();
           } else if (simbolo.equals("<")){
               aux_b = simbolo;
               obtemSimbolo();
           }
                   
        } 
    }
    
    private void expressao(){
        termo();
        outros_termos();
    }
    
    private void termo(){
        op_un();
        fator();
        mais_fatores();
        
    }
    
    private void op_un(){
        if (simbolo.equals("-")){
            i += 1;
            C.push("INVE");
            obtemSimbolo();
          } 
    }
    
    
    private void fator(){
        if ( tipo == Token.IDENT){
          if (verificador == 0){
             if(tabelaSimbolos.containsKey(simbolo)){
               i += 1;
               C.push("CRVL " +tabelaSimbolos.get(simbolo).getEnd_rel());
               obtemSimbolo();
             } else {
                throw new RuntimeException("Erro semantico, o identificador nao foi declarado");
               }
          } else if(verificador == 1){
              if(tabelaSimbolosB.containsKey(simbolo)){
                i += 1;  
                C.push("CRVL " +tabelaSimbolosB.get(simbolo).getEnd_rel());
                obtemSimbolo();
              } else {
                throw new RuntimeException("Erro semantico, o identificador nao foi declarado");
                }
            }
        } else if (tipo == Token.NUMERO_INTEIRO){
            i += 1;
            C.push("CRCT " +simbolo);
             if(aux_b.equals("-")){
                C.push("SUBT");
            }else if(aux_b.equals("+")){
                C.push("SOMA");
            }else if (aux_b.equals("*")){
                C.push("MULT");
            }else if (aux_b.equals("/")){
                C.push("DIVI");
            }else if (aux_b.equals("=")){
                C.push("CPIG");
            }else if (aux_b.equals("<>")){
                C.push("CDES");
            }else if (aux_b.equals(">=")){
                C.push("CMAI");
            }else if (aux_b.equals("<=")){
                C.push("CPMI");
            }else if (aux_b.equals(">")){
                C.push("CPMA");
            }else if (aux_b.equals("<")){
                C.push("CPME");
            } 
            aux_b = "";

            
            
            obtemSimbolo();
        } else if (tipo == Token.NUMERO_REAL){
            i += 1;
            C.push("CRCT " +simbolo);
             if(aux_b.equals("-")){
                C.push("SUBT");
            }else if(aux_b.equals("+")){
                C.push("SOMA");
            }else if (aux_b.equals("*")){
                C.push("MULT");
            }else if (aux_b.equals("/")){
                C.push("DIVI");
            }else if (aux_b.equals("=")){
                C.push("CPIG");
            }else if (aux_b.equals("<>")){
                C.push("CDES");
            }else if (aux_b.equals(">=")){
                C.push("CMAI");
            }else if (aux_b.equals("<=")){
                C.push("CPMI");
            }else if (aux_b.equals(">")){
                C.push("CPMA");
            }else if (aux_b.equals("<")){
                C.push("CPME");
            } 
            aux_b = "";

           
           
            
            obtemSimbolo();
        } else if (simbolo.equals("(")){
            obtemSimbolo();
            expressao();
            if (simbolo.equals(")")){
                obtemSimbolo();
            }
            
            
        } 
        
        
     }
       
        
       
    private void outros_termos(){
       if (simbolo.equals("+") || simbolo.equals("-")){
            op_ad();
            termo();
            outros_termos();
        
        }   
    }
    
    private void op_ad(){
        switch(simbolo){
            case "+":
                aux_b = simbolo;
                obtemSimbolo();
                break;
            case "-":
                aux_b = simbolo;
                obtemSimbolo();
                break;
            default:
                throw new RuntimeException("Erro sintatico: era esperado '+' ou '-'");
            
     
        }  
    }
    
    private void mais_fatores(){
      if (simbolo.equals("*") || simbolo.equals("/")){
          op_mul();
          fator();
          mais_fatores();
        }
        
     }   
      
    
    private void op_mul(){
            switch(simbolo){
            case "*":
                aux_b = simbolo;
                obtemSimbolo();
                break;
            case "/":
                aux_b = simbolo;
                obtemSimbolo();
                break;
            default:
                throw new RuntimeException("Erro sintatico: era esperado '*' ou '/'");
            
     
        }  
    }
    
    private void pfalsa(){
     
         if (simbolo.equals("else")){
             obtemSimbolo();
             comandos();
         
         } 
    } 
    
    private void inserelinha(String atual , String addlinha ){
        for(int a = C.size() -  1; a >= 0; a--){
            String conteudo = C.get(a);
                if (conteudo.contains(atual)){
                    C.set(a, conteudo.replace(atual, addlinha));
                
                }
        
        }
    
    
    
    }
    
        
}
    

