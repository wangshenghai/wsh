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
       List<String> list = infixExpToPostExp(exp);//ת���ɺ�׺���ʽ  
       return doEval(list);//������ֵ  
   }  
     
   //����������ѹջ���������ʽ�Ӻ�׺���ʽ�е���������������������ѹ���ջ  
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
     
   private List<String> infixExpToPostExp(String exp) throws IllegalExpressionException{//����׺���ʽת����Ϊ��׺���ʽ  
       List<String> postExp = new ArrayList<String>();//���ת���ĺ�׺���ʽ������  
       StringBuffer numBuffer = new StringBuffer();//��������һ������  
       Stack<Character> opStack = new Stack<Character>();//������ջ  
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
//              ���ջ����Ĳ��������ȼ��ȵ�ǰ�Ĵ����ջ�����ȼ���Ķ���ӵ���׺���ʽ�б���  
                        while(priority(preChar) >= priority(ch)){  
                            postExp.add(""+preChar);  
                            opStack.pop();  
                            preChar = opStack.peek();  
                        }  
                        opStack.push(ch);  
                        i++;  
                        break;  
                    case '(':  
//              ������ֱ��ѹջ  
                        opStack.push(ch);  
                        i++;  
                        break;  
                    case ')':  
//              ��������ֱ�Ӱ�ջ��������ǰ��ĵ������������׺���ʽ������  
                        char c = opStack.pop();  
                        while(c != '('){  
                            postExp.add("" + c);  
                            c = opStack.pop();  
                        }  
                        i++;  
                        break;  
//           #�ţ�������ʽ����������ֱ�ӰѲ�����ջ��ʣ��Ĳ�����ȫ���������������׺���ʽ������  
                 case '#':  
                     char c1;  
                     while(!opStack.isEmpty()){  
                         c1 = opStack.pop();  
                         if(c1 != '#')  
                           postExp.add("" + c1);  
                     }  
                     i++;  
                     break;  
                              //���˿հ׷�  
                 case ' ':  
                 case '\t':  
                     i++;  
                     break;  
//               ������ճ�һ�������������׺���ʽ������  
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
     
   private int priority(char op) throws IllegalExpressionException{//�������ȼ�  
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
	   System.out.println("��������Ŀ��������");
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
