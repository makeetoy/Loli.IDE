/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl;

import java.util.ArrayList;
import static pl.ByteInstructions.*;

public class MainP {
    
 
    static ArrayList<Integer> clist = new ArrayList();
    static String code;
    String output="", o, error;
    String[] errors={"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""};
    int[] line=new int[50];
    int numberOferr=0,errorCount=0,errline, errctr=1,y=1;
    
    
    void codetext(String s, String wholecode){
       
        boolean run=true;
        code=s;
        int x=0,counter=0;
        while(x<code.length()){
            if(code.charAt(x)==' '){
                counter++;
            }
            x++;
        }
        
        String[] codearr=new String[counter+1];             //Declare an array of string with size according to number of spaces
        int[] syn=new int[codearr.length];                  //Declare an array of integer with size according to codearr[]'s size 
        codearr=code.split(" ");                            //Store code into an array of strings

        String tempo=wholecode;
        x=0;
        int check=0;
        String symbols="! @ # $ % ^ & * ( ) | { } [ ] : ' , . / ? _ =";
        String[] symbolss=symbols.split(" ");
        // ERROR CHECKING
        while(x<codearr.length){
            switch(codearr[x]){
           
                case "INTEGER":
                    try{
                        check=BYTECODES.get(codearr[x+1]);
                        
                        run=false;
                        errline=x+1;
                        lineNumberErr(tempo);
                        System.out.println(errctr);
                    
                        errorDetected("ERROR: Please declare variable identifier.",errctr);
                        errctr=1;y=1;
                    }
                    catch(NullPointerException npe){
                        //Variable identifier error checker for symbols
                        for(int i=0;i<symbolss.length;i++){
                            if(codearr[x+1].contains(symbolss[i])){
                                errline=x+1;
                                lineNumberErr(tempo);
                                errorDetected("VARIABLE IDENTIFIER ERROR: Variable name/identifier must not contain symbols",errctr);
                                run=false;
                                break;
                            }
                        }
                     
                        if(!isNumeric(codearr[x+2])){
                                errline=x+2;
                                lineNumberErr(tempo);
                                errorDetected("VARIABLE VALUE ERROR: 'INTEGER' variable requires specified integer value.",errctr);
                                errctr=1;y=1;
                                run=false;                              
                        }
                        
                        
                    }     
            }
            x++;
        }
        System.out.println("mali: "+errorCount);
        if(run==true){
            for(int i=0;i<codearr.length;i++){
                
                if(codearr[i].contains("0")||codearr[i].contains("1")||codearr[i].contains("2")||codearr[i].contains("3")||codearr[i].contains("4")||codearr[i].contains("5")||codearr[i].contains("6")||codearr[i].contains("7")||codearr[i].contains("8")||codearr[i].contains("9")){
                    try{
                        syn[i]=Integer.parseInt(codearr[i]);
                    }
                    catch(NumberFormatException nfe){
                        lineNumberErr(tempo);
                        errorDetected("Syntax Error: WRONG NUMBER(CONSTANT VALUE) FORMAT",errctr);
                        errctr=1;y=1;
                    }
                }
                else{
                    try{                                            //Check each string if it has an equal in the bytecode instructions
                        syn[i]=BYTECODES.get(codearr[i]);           //and store it into an array of integers.
                    }
                    catch(NullPointerException e){
                        System.out.println("Syntax error at string index "+i);
                    }
                }
            }
            
            for (int i=0;i<syn.length;i++){
                clist.add(syn[i]);
            }
        }
        
        else{
            System.out.println("pasok");
            
            for(int i=0;i<errorCount;i++){
                output+="Line "+line[i]+" -- "+errors[i];
                System.out.println(errors[i]);
            }
        }
    
}
    
    public void exe(String s,String wholecode){
        MainP mp=new MainP();
        String obj="";
        s = s.trim().replaceAll("\n"," ").replaceAll(" +", " ");          //Replace all linebreaks in code with whitespace and excess whitespace remover        
        codetext(s,wholecode);                                                      //Process code input
        
        System.out.println("FINAL: "+output);
        if(errorCount==0){
            //PRINT CODE'S NO. OF STRINGS
             System.out.println(clist.size());
            int[] syntax = new int[clist.size()];
            for (int i=0; i < syntax.length; i++)
            {
                syntax[i] = clist.get(i).intValue();
                obj=obj+syntax[i]+" ";
            }
            System.out.println();
            VM vm=new VM(syntax,0,200);
            vm.trace = false;
            output=vm.cpu();
            o=obj;
        }
    }

    void errorDetected(String err, int errline){
        line[errorCount]=errline;
        errors[errorCount]=err+"\n";
        errorCount++;
    }
    
    public static boolean isNumeric(String str)  
    {  
      try  
      {  
        int i = Integer.parseInt(str);  
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      return true;  
    }
    
    public void lineNumberErr(String temp){
        int  z=0, k=0, j=0;
        for(k=0;k<temp.length();k++){
                        if(temp.charAt(k)==' '||temp.charAt(k)=='\n'){
                            y++;
                            if(y==errline){
                                z=k;
                                k=temp.length();
                            }
                        }
                        else if(Character.isDigit(temp.charAt(k))){
                            y--;
                        }
                    }
                    System.out.println(errline+" "+z);
                    for(j=0;j<z;j++){
                        if(temp.charAt(j)=='\n'){
                            errctr++;
                        }
                    }
        
    }
}
        


