package project1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;  
import java.util.List;  
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;  

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
  
public class Main {  
   public int eval(String exp) throws IllegalExpressionException{  
       List<String> list = infixExpToPostExp(exp);//转化成后缀表达式  
       return doEval(list);//真正求值  
   }  
     
   //遇到操作符压栈，遇到表达式从后缀表达式中弹出两个数，计算出结果，压入堆栈  
   private int doEval(List<String> list) throws IllegalExpressionException {  
      Stack<String> stack =  new Stack<String>();  
      String element;  
      int n1,n2,result;  
      try{  
          for(int i = 0; i < list.size();i++){  
              element = list.get(i);  
              if(isOperator(element)){  
                  n1 = Integer.parseInt(stack.pop());  
                  n2 = Integer.parseInt(stack.pop());  
                  result = doOperate(n1,n2,element);  
                  stack.push(new Integer(result).toString());  
             }else{  
                 stack.push(element);  
             }  
          }  
          return Integer.parseInt(stack.pop());  
      }catch(RuntimeException e){  
          throw new IllegalExpressionException(e.getMessage());         
      }  
   }  
     
   private int doOperate(int n1, int n2, String operator) {  
      if(operator.equals("+"))  
          return n1 + n2;  
      else if(operator.equals("-"))  
          return n1 - n2;  
      else if(operator.equals("*"))  
          return n1 * n2;  
      else  
          return n1 / n2;  
   }  
  
   private boolean isOperator(String str){  
       return str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/");  
   }  
     
   private List<String> infixExpToPostExp(String exp) throws IllegalExpressionException{//将中缀表达式转化成为后缀表达式  
       List<String> postExp = new ArrayList<String>();//存放转化的后缀表达式的链表  
       StringBuffer numBuffer = new StringBuffer();//用来保存一个数的  
       Stack<Character> opStack = new Stack<Character>();//操作符栈  
       char ch,preChar;  
       opStack.push('#');  
       try{  
           for(int i = 0; i < exp.length();){  
               ch = exp.charAt(i);  
               switch(ch){  
                    case '+':  
                    case '-':  
                    case '*':  
                    case '/':  
                        preChar = opStack.peek();  
//              如果栈里面的操作符优先级比当前的大，则把栈中优先级大的都添加到后缀表达式列表中  
                        while(priority(preChar) >= priority(ch)){  
                            postExp.add(""+preChar);  
                            opStack.pop();  
                            preChar = opStack.peek();  
                        }  
                        opStack.push(ch);  
                        i++;  
                        break;  
                    case '(':  
//              左括号直接压栈  
                        opStack.push(ch);  
                        i++;  
                        break;  
                    case ')':  
//              右括号则直接把栈中左括号前面的弹出，并加入后缀表达式链表中  
                        char c = opStack.pop();  
                        while(c != '('){  
                            postExp.add("" + c);  
                            c = opStack.pop();  
                        }  
                        i++;  
                        break;  
//           #号，代表表达式结束，可以直接把操作符栈中剩余的操作符全部弹出，并加入后缀表达式链表中  
                 case '#':  
                     char c1;  
                     while(!opStack.isEmpty()){  
                         c1 = opStack.pop();  
                         if(c1 != '#')  
                           postExp.add("" + c1);  
                     }  
                     i++;  
                     break;  
                              //过滤空白符  
                 case ' ':  
                 case '\t':  
                     i++;  
                     break;  
//               数字则凑成一个整数，加入后缀表达式链表中  
                 default:  
                     if(Character.isDigit(ch)){  
                         while(Character.isDigit(ch)){  
                             numBuffer.append(ch);  
                             ch = exp.charAt(++i);  
                         }  
                         postExp.add(numBuffer.toString());  
                         numBuffer = new StringBuffer();  
                     }else{  
                       throw new IllegalExpressionException("illegal operator");  
                     }  
               }  
           }  
       }catch(RuntimeException e){  
           throw new IllegalExpressionException(e.getMessage());   
       }  
       return postExp;  
   }  
     
   private int priority(char op) throws IllegalExpressionException{//定义优先级  
        switch(op){  
        case'+':  
        case'-':  
            return 1;  
        case'*':  
        case'/':  
            return 2;  
        case'(':  
        case'#':  
            return 0;  
        }  
        throw new IllegalExpressionException("Illegal operator");  
  }  
     
   public static void main(String[] args) throws IllegalExpressionException 
   {  
	   Scanner scan = new Scanner(System.in);
	   System.out.println("请输入题目的数量：");
	   int s = scan.nextInt();
	   ArrayList<String> ex= new ArrayList<String>(); 
	   String[] operate=new String[]{"+","-","*","/"};
	   Random r=new Random();
	   ScriptEngineManager manager = new ScriptEngineManager();
       ScriptEngine se = manager.getEngineByName("js");
       for(int i=0;i<s;i++)
       {
    	   int a=(int)(Math.random()*100);
    	   int b=(int)(Math.random()*100);
    	   int c=(int)(Math.random()*100);
    	   int q=(int)(Math.random()*2);
    	   String cz=operate[r.nextInt(4)];
    	   String cz1=operate[r.nextInt(4)];
    	   //if(q==0){
    	   String AX=String.valueOf(a)+String.valueOf(cz)+String.valueOf(b);
    	   
    	   Main eval=new Main();
    	   int result = eval.eval(AX+"#");  
           System.out.println(a+cz+b+"="+result);
           ex.add(AX+"="+result);
    	   
       
       }
       try {
      		File f = new File("result.txt"); 
			FileWriter fw = new FileWriter(f);
			PrintWriter pw = new PrintWriter(fw);
			pw.println("201571030129");
			pw.println();
		    for(String con:ex)
			 {
	                pw.println(con);  
	                
	         }
			
			fw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       	        
   }
       
       
   }  
