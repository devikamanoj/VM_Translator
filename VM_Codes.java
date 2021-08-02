import java.util.*;
public class VM_Codes 
{
	static String[] codes ;
	static String St="";
	static int n;
	static String vmName;
    static String finalcode="";
    static int eqCount = 0;
    static int gtCount = 0;
	static int ltCount = 0;
	static int callcount=0;
	static String sysFilePresent(String Line)
	{
		List<String> ASM = new ArrayList<>();
		ASM.add("// Bootstrap code");
		ASM.add("@256");
		ASM.add("D=A");
		ASM.add("@SP");
		ASM.add("M=D");
		ASM.add(callsys("Sys.init", 0));
		// to convert array list to string
		StringBuffer sb = new StringBuffer();
        for (String s : ASM ) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
	}
    static String SplitingCodes(String line,String vmName)
    {
		String SPACE=" ";
        String s="" ;
        for (int i = 0; i < line.length(); i++) // whitespace handling
        {
           char CurrentChar = line.charAt(i);
           if(CurrentChar=='/')
            {
                break;
            }
            if (CurrentChar != ' ')
            {
               s = s + CurrentChar;  
            }
        }
		codes = line.split(SPACE); // spliting the line on the bases of space bars
		if(codes.length==1) // for return, sub, add,.........
		{
			finalcode=codelength1();// goes to method codelength1()
		}
		else if(codes.length==2) // for if-goto, goto,....
		{
			finalcode=codelength2();// goes to method codelength2()
		}
		else // for push,pop statements,..........
		{
			finalcode=codelength3();//goes to method codelength3()
		}
       return finalcode;
    }
	static String codelength3()
	{
		//In this method  it checks that if the first word and go to the respective methods

		if(codes.length==3)
		{
			String code="";
			if("call".equals(codes[0]))
			{
				String fn=codes[1];
				String arg = codes[2];
				n=Integer.parseInt(arg);// checks if the arg is integer and store it as n
				finalcode=Call(vmName,fn,n);
			}
			
			if("function".equals(codes[0]))
			{
				String fn=codes[1];
				String arg = codes[2];
				n=Integer.parseInt(arg);// checks if the arg is integer and store it as n
				finalcode=function(fn,n);
			}
			if("push".equals(codes[0]))// checks if the first statement is push
			{
				if ("argument".equals(codes[1]))
				{
					code=codes[1];
					St=codes[2];
					n=Integer.parseInt(St);// checks if the St is integer and store it as n
					finalcode=PushArg(code,n);
				}
				if ("local".equals(codes[1]))
				{
					code=codes[1];
					St=codes[2];
					n=Integer.parseInt(St);// checks if the St is integer and store it as n
					finalcode=PushLocal(code,n);
				}
				if ("this".equals(codes[1]))
				{
					code=codes[1];
					St=codes[2];
					n=Integer.parseInt(St);// checks if the St is integer and store it as n
					finalcode=PushThis(code,n);
				}
				if ("that".equals(codes[1]))
				{
					code=codes[1];
					St=codes[2];
					n=Integer.parseInt(St);// checks if the St is integer and store it as n
					finalcode=PushThat(code,n);
				}
				if ("static".equals(codes[1]))
				{
					code=codes[1];
					St=codes[2];
					n=Integer.parseInt(St);// checks if the St is integer and store it as n
					finalcode=PushStatic(vmName,code,n);
				}
				if ("constant".equals(codes[1]))
				{
					code=codes[1];
					St=codes[2];
					n=Integer.parseInt(St);// checks if the St is integer and store it as n
					finalcode=pushconstant(code,n);
				}
				if ("pointer".equals(codes[1]))
				{
					code=codes[1];
					St=codes[2];
					n=Integer.parseInt(St);// checks if the St is integer and store it as n
					finalcode=PushPointer(code,n);
				}
				if ("temp".equals(codes[1]))
				{
					code=codes[1];
					St=codes[2];
					n=Integer.parseInt(St);// checks if the St is integer and store it as n
					finalcode=PushTemp(code,n);
				}
			}
			if("pop".equals(codes[0]))// checks the first word is pop
			{
				if ("argument".equals(codes[1]))
				{
					code=codes[1];
					St=codes[2];
					n=Integer.parseInt(St);// checks if the St is integer and store it as n
					finalcode=VM_Codes.PopArgument(code,n);
				}
				if ("local".equals(codes[1]))
				{
					code=codes[1];
					St=codes[2];
					n=Integer.parseInt(St);// checks if the St is integer and store it as n
					finalcode=VM_Codes.PopLocal(code,n);
				}
				if ("this".equals(codes[1]))
				{
					code=codes[1];
					St=codes[2];
					n=Integer.parseInt(St);// checks if the St is integer and store it as n
					finalcode=VM_Codes.PopThis(code,n);
				}
				if ("that".equals(codes[1]))
				{
					code=codes[1];
					St=codes[2];
					n=Integer.parseInt(St);// checks if the St is integer and store it as n
					finalcode=VM_Codes.PopThat(code,n);
				}
				if ("static".equals(codes[1]))
				{
					code=codes[1];
					St=codes[2];
					n=Integer.parseInt(St);// checks if the St is integer and store it as n
					finalcode=VM_Codes.PopStatic(vmName,code,n);
				}
				if ("pointer".equals(codes[1]))
				{
					code=codes[1];
					St=codes[2];
					n=Integer.parseInt(St);// checks if the St is integer and store it as n
					finalcode=VM_Codes.PopPointer(code,n);
				}
				if ("temp".equals(codes[1]))
				{
					code=codes[1];
					St=codes[2];
					n=Integer.parseInt(St);// checks if the St is integer and store it as n
					finalcode=VM_Codes.PopTemp(code,n);
				}
			}
		}
		return finalcode;
	}
	static String codelength2()
	{
		// checks the 1st word and goes to the respective methods
		if("label".equals(codes[0]))
		{
			finalcode=VM_Codes.Label(codes[1]);
		}
		if("goto".equals(codes[0]))
		{
			finalcode=VM_Codes.Goto(codes[1]);
		}
		if("if-goto".equals(codes[0]))
		{
			finalcode=VM_Codes.IfGoto(codes[1]);	
		}
		return finalcode;
	}
	static String codelength1()
	{
		// checks the 1st word and goes to the respective methods
		if("return".equals(codes[0]))
		{
			finalcode=VM_Codes.Return();
		}
		if("eq".equals(codes[0]))
		{
			finalcode= VM_Codes.eq(vmName,codes[0]);
		}
		if("lt".equals(codes[0]))
		{
			finalcode=VM_Codes.lt(vmName,codes[0]);
		}
		if("gt".equals(codes[0]))
		{
			finalcode=VM_Codes.gt(vmName,codes[0]);
		}    
		if("add".equals(codes[0]))
		{
			finalcode=VM_Codes.add(codes[0]);
		}
		if("sub".equals(codes[0]))
		{
			finalcode=VM_Codes.sub(codes[0]);
		}    
		if("neg".equals(codes[0]))
		{
			finalcode=VM_Codes.neg(codes[0]);
		}
		if("and".equals(codes[0]))
		{
			finalcode= VM_Codes.and(codes[0]);
		}
		if("or".equals(codes[0]))
		{
			finalcode=VM_Codes.or(codes[0]);
		}
		if("not".equals(codes[0]))
		{
			finalcode=VM_Codes.not(codes[0]);
		}
		return finalcode;
	}
	static String callsys(String function, int nArgs)
	{
		List<String> ASM = new ArrayList<>();
		// add comment
		ASM.add("// call " + function + " " + nArgs);

		String returnAddress = "after-finishing-all-tasks";

		// 1. push return address
		ASM.add("@" + returnAddress);
		ASM.add("D=A");
		ASM.add("@SP");
		ASM.add("A=M");
		ASM.add("M=D");
		ASM.add("@SP");
		ASM.add("M=M+1");

		// 2. push LCL
		ASM.add("@LCL");
		ASM.add("D=M");
		ASM.add("@SP");
		ASM.add("A=M");
		ASM.add("M=D");
		ASM.add("@SP");
		ASM.add("M=M+1");

		// 3. push ARG
		ASM.add("@ARG");
		ASM.add("D=M");
		ASM.add("@SP");
		ASM.add("A=M");
		ASM.add("M=D");
		ASM.add("@SP");
		ASM.add("M=M+1");

		// 4. push THIS
		ASM.add("@THIS");
		ASM.add("D=M");
		ASM.add("@SP");
		ASM.add("A=M");
		ASM.add("M=D");
		ASM.add("@SP");
		ASM.add("M=M+1");

		// 5. push THAT
		ASM.add("@THAT");
		ASM.add("D=M");
		ASM.add("@SP");
		ASM.add("A=M");
		ASM.add("M=D");
		ASM.add("@SP");
		ASM.add("M=M+1");

		// 6. calculate ARG for called function
		ASM.add("@5");
		ASM.add("D=A");
		ASM.add("@" + nArgs);
		ASM.add("D=D+A");
		ASM.add("@SP");
		ASM.add("D=M-D");
		ASM.add("@ARG");
		ASM.add("M=D");

		// 7. calculate LCL for called function
		ASM.add("@SP");
		ASM.add("D=M");
		ASM.add("@LCL");
		ASM.add("M=D");

		// 8. go to function
		ASM.add("@" + function);
		ASM.add("0;JMP");

		// 9. mark return address
		ASM.add("(" + returnAddress + ")");

		// to convert the array list to String
		StringBuffer sb = new StringBuffer();
        for (String s : ASM ) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
	}
	static String function(String fn, int var)
    {
    	List<String> ASM = new ArrayList<>();
		// add comment
		ASM.add("// function " + fn + " " + var);

		ASM.add("(" + fn + ")");
		for (int i = 0; i < var; i++)
		{
			ASM.add("@SP");
			ASM.add("A=M");
			ASM.add("M=0");
			ASM.add("@SP");
			ASM.add("M=M+1");
		}

		// to convert the array list to String
		StringBuffer sb = new StringBuffer();
        for (String s : ASM ) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
    }
    static String Call(String vmName,String fn, int arg)
    {
    	List<String> ASM = new ArrayList<>();
		// add comment
		ASM.add("// call " + fn + " " + arg);
		String returnAddress;
		returnAddress = vmName + "-return-address-" + (callcount);
		callcount++;

		// 1. push return address
		ASM.add("@" + returnAddress);
		ASM.add("D=A");
		ASM.add("@SP");
		ASM.add("A=M");
		ASM.add("M=D");
		ASM.add("@SP");
		ASM.add("M=M+1");

		// 2. push LCL
		ASM.add("@LCL");
		ASM.add("D=M");
		ASM.add("@SP");
		ASM.add("A=M");
		ASM.add("M=D");
		ASM.add("@SP");
		ASM.add("M=M+1");

		// 3. push ARG
		ASM.add("@ARG");
		ASM.add("D=M");
		ASM.add("@SP");
		ASM.add("A=M");
		ASM.add("M=D");
		ASM.add("@SP");
		ASM.add("M=M+1");

		// 4. push THIS
		ASM.add("@THIS");
		ASM.add("D=M");
		ASM.add("@SP");
		ASM.add("A=M");
		ASM.add("M=D");
		ASM.add("@SP");
		ASM.add("M=M+1");

		// 5. push THAT
		ASM.add("@THAT");
		ASM.add("D=M");
		ASM.add("@SP");
		ASM.add("A=M");
		ASM.add("M=D");
		ASM.add("@SP");
		ASM.add("M=M+1");

		// 6. calculate ARG for called function
		ASM.add("@5");
		ASM.add("D=A");
		ASM.add("@" + arg);
		ASM.add("D=D+A");
		ASM.add("@SP");
		ASM.add("D=M-D");
		ASM.add("@ARG");
		ASM.add("M=D");

		// 7. calculate LCL for called function
		ASM.add("@SP");
		ASM.add("D=M");
		ASM.add("@LCL");
		ASM.add("M=D");

		// 8. go to function
		ASM.add("@" + fn);
		ASM.add("0;JMP");

		// 9. mark return address
		ASM.add("(" + returnAddress + ")");

		// to convert the array list to String
		StringBuffer sb = new StringBuffer();
        for (String s : ASM ) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
    }
    static String Return()
    {
    	List<String> ASM = new ArrayList<>();
		// add comment
		ASM.add("// return");

		//  R13 <--- FRAME = LCL
		ASM.add("@LCL");
		ASM.add("D=M");
		ASM.add("@R13");
		ASM.add("M=D");

		// R14 <--- RET = *(FRAME - 5)
		ASM.add("@R13");
		ASM.add("D=M");
		ASM.add("@5");
		ASM.add("A=D-A");
		ASM.add("D=M");
		ASM.add("@R14");
		ASM.add("M=D");

		//  *ARG = pop()
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");
		ASM.add("@ARG");
		ASM.add("A=M");
		ASM.add("M=D");

		//  SP = ARG + 1
		ASM.add("@ARG");
		ASM.add("D=M+1");
		ASM.add("@SP");
		ASM.add("M=D");

		// THAT = *(FRAME - 1)
		ASM.add("@R13");
		ASM.add("A=M-1");
		ASM.add("D=M");
		ASM.add("@THAT");
		ASM.add("M=D");

		// THIS = *(FRAME - 2)
		ASM.add("@R13");
		ASM.add("D=M");
		ASM.add("@2");
		ASM.add("A=D-A");
		ASM.add("D=M");
		ASM.add("@THIS");
		ASM.add("M=D");

		// ARG = *(FRAME - 3)
		ASM.add("@R13");
		ASM.add("D=M");
		ASM.add("@3");
		ASM.add("A=D-A");
		ASM.add("D=M");
		ASM.add("@ARG");
		ASM.add("M=D");

		//  LCL = *(FRAME - 4)
		ASM.add("@R13");
		ASM.add("D=M");
		ASM.add("@4");
		ASM.add("A=D-A");
		ASM.add("D=M");
		ASM.add("@LCL");
		ASM.add("M=D");

		// goto RET
		ASM.add("@R14");
		ASM.add("A=M");
		ASM.add("0;JMP");

		// to convert the array list to String
		StringBuffer sb = new StringBuffer();
		for (String s : ASM ) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
    }
	static String Goto(String line)
	{
        List<String> ASM = new ArrayList<>();
		ASM .add("// goto " + line);
		ASM .add("@" + line);
		ASM .add("0;JMP");

		// to convert the array list to String
		StringBuffer sb = new StringBuffer();
        for (String s : ASM ) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
		
    }
    static String IfGoto(String line)
	{
        List<String> ASM  = new ArrayList<>();
		ASM .add("// if-goto " + line);

		// pop the top most value
		ASM .add("@SP");
		ASM .add("AM=M-1");

		// check condition and jump
		ASM .add("D=M");
		ASM .add("@" + line);
		ASM .add("D;JNE");

		// to convert the array list to String
        StringBuffer sb = new StringBuffer();
        for (String s : ASM ) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
    }
	static String Label(String line)
	{
		List<String> ASM = new ArrayList<>();
        ASM.add("// label " + line);
		ASM.add("(" + line + ")");

		// to convert the array list to String
		StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
	}
    static String pushconstant(String line,int n)
    {
        List <String> ASM =new ArrayList<>();
        ASM.add("// push constant "+n);
        // Load (segment + n) content
		ASM.add("@" + n);
		ASM.add("D=A");

		// Push to stack
		ASM.add("@SP");
		ASM.add("A=M");
		ASM.add("M=D");

		// Update stack pointer
		ASM.add("@SP");
		ASM.add("M=M+1");
        
		// to convert the array list to String
        StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
    }
	static String PushArg(String line,int n)
	{
		List<String> ASM = new ArrayList<>();

		// Add comment to asm
		ASM.add("// push argument " + n);

		// Load (segment + n) content
		ASM.add("@ARG");
		ASM.add("D=M");
		ASM.add("@" + n);
		ASM.add("A=D+A");
		ASM.add("D=M");

		// Push to stack
		ASM.add("@SP");
		ASM.add("A=M");
		ASM.add("M=D");

		// Update stack pointer
		ASM.add("@SP");
		ASM.add("M=M+1");

		// to convert the array list to String
        StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
	}
	static String PushLocal(String line,int n) 
	{
		List<String> ASM = new ArrayList<>();

		// Add comment to asm
		ASM.add("// push local " + n);

		// Load (segment + n) content
		ASM.add("@LCL");
		ASM.add("D=M");
		ASM.add("@" + n);
		ASM.add("A=D+A");
		ASM.add("D=M");

		// Push to stack
		ASM.add("@SP");
		ASM.add("A=M");
		ASM.add("M=D");

		// Update stack pointer
		ASM.add("@SP");
		ASM.add("M=M+1");

		// to convert the array list to String
		StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
	}
	static String PushThis(String line, int n)
	{
		List<String> ASM = new ArrayList<>();

		// Add comment to asm
		ASM.add("// push this " + n);

		// Load (segment + n) content
		ASM.add("@THIS");
		ASM.add("D=M");
		ASM.add("@" + n);
		ASM.add("A=D+A");
		ASM.add("D=M");

		// Push to stack
		ASM.add("@SP");
		ASM.add("A=M");
		ASM.add("M=D");

		// Update stack pointer
		ASM.add("@SP");
		ASM.add("M=M+1");

		// to convert the array list to String
		StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
	}
	static String PushThat(String line, int n)
	{
		List<String> ASM = new ArrayList<>();

		// Add comment to asm
		ASM.add("// push that " + n);

		// Load (segment + n) content
		ASM.add("@THAT");
		ASM.add("D=M");
		ASM.add("@" + n);
		ASM.add("A=D+A");
		ASM.add("D=M");

		// Push to stack
		ASM.add("@SP");
		ASM.add("A=M");
		ASM.add("M=D");

		// Update stack pointer
		ASM.add("@SP");
		ASM.add("M=M+1");

		// to convert the array list to String
		StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
	}
	static String PushStatic(String vmName,String line, int n)
	{
		List<String> ASM = new ArrayList<>();

		// Add comment to asm
		ASM.add("// push static " + n);

		// Load (segment + n) content
		ASM.add("@" + vmName + ".static." + n);
		ASM.add("D=M");

		// Push to stack
		ASM.add("@SP");
		ASM.add("A=M");
		ASM.add("M=D");

		// Update stack pointer
		ASM.add("@SP");
		ASM.add("M=M+1");

		// to convert the array list to String
		StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
	}
	static String PushPointer(String line, int n)
	{
		List<String> ASM = new ArrayList<>();

		// Add comment to asm
		ASM.add("// push pointer " + n);

		// Load (segment + n) content
		ASM.add("@3");
		ASM.add("D=A");
		ASM.add("@" + n);
		ASM.add("A=D+A");
		ASM.add("D=M");

		// Push to stack
		ASM.add("@SP");
		ASM.add("A=M");
		ASM.add("M=D");

		// Update stack pointer
		ASM.add("@SP");
		ASM.add("M=M+1");

		// to convert the array list to String
		StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
	}
	static String PushTemp(String line, int n)
	{
		List<String> ASM = new ArrayList<>();

		// Add comment to asm
		ASM.add("// push temp " + n);

		// Load (segment + n) content
		ASM.add("@5");
		ASM.add("D=A");
		ASM.add("@" + n);
		ASM.add("A=D+A");
		ASM.add("D=M");

		// Push to stack
		ASM.add("@SP");
		ASM.add("A=M");
		ASM.add("M=D");

		// Update stack pointer
		ASM.add("@SP");
		ASM.add("M=M+1");

		// to convert the array list to String
		StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
	}
	static String PopArgument(String line, int n)
	{
		List<String> ASM = new ArrayList<>();
		// Add comment to asm
		ASM.add("// pop argument " + n);

		ASM.add("@ARG");
		ASM.add("D=M");
		ASM.add("@" + n);
		ASM.add("D=D+A");
		ASM.add("@R13");
		ASM.add("M=D");

		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");
		ASM.add("@R13");
		ASM.add("A=M");
		ASM.add("M=D");

		// Update stack pointer
		ASM.add("@SP");
		ASM.add("M=M-1");

		// to convert the array list to String
		StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
	}
	static String PopLocal(String line, int n)
	{
		List<String> ASM = new ArrayList<>();

		// Add comment to asm
		ASM.add("// pop local " + n);

		ASM.add("@LCL");
		ASM.add("D=M");
		ASM.add("@" + n);
		ASM.add("D=D+A");
		ASM.add("@R13");
		ASM.add("M=D");

		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");
		ASM.add("@R13");
		ASM.add("A=M");
		ASM.add("M=D");

		// Update stack pointer
		ASM.add("@SP");
		ASM.add("M=M-1");

		// to convert the array list to String
		StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;

	}
	static String PopThis(String line, int n)
	{
		List<String> ASM = new ArrayList<>();

		// Add comment to asm
		ASM.add("// pop this " + n);

		ASM.add("@THIS");
		ASM.add("D=M");
		ASM.add("@" + n);
		ASM.add("D=D+A");
		ASM.add("@R13");
		ASM.add("M=D");

		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");
		ASM.add("@R13");
		ASM.add("A=M");
		ASM.add("M=D");

		// Update stack pointer
		ASM.add("@SP");
		ASM.add("M=M-1");

		// to convert the array list to String
		StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
	}
	static String PopThat(String line, int n)
	{
		List<String> ASM = new ArrayList<>();

		// Add comment to asm
		ASM.add("// pop that " + n);

		ASM.add("@THAT");
		ASM.add("D=M");
		ASM.add("@" + n);
		ASM.add("D=D+A");
		ASM.add("@R13");
		ASM.add("M=D");

		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");
		ASM.add("@R13");
		ASM.add("A=M");
		ASM.add("M=D");

		// Update stack pointer
		ASM.add("@SP");
		ASM.add("M=M-1");

		// to convert the array list to String
		StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
	}
	static String PopStatic(String vmName,String line, int n)
	{
		List<String> ASM = new ArrayList<>();

		// Add comment to asm
		ASM.add("// pop static " + n);

		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");

		ASM.add("@" + vmName + ".static." + n);
		ASM.add("M=D");

		// Update stack pointer
		ASM.add("@SP");
		ASM.add("M=M-1");

		// to convert the array list to String
		StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
	}
	static String PopPointer(String line, int n)
	{
		List<String> ASM = new ArrayList<>();

		// Add comment to asm
		ASM.add("// pop pointer " + n);

		ASM.add("@3");
		ASM.add("D=A");
		ASM.add("@" + n);
		ASM.add("D=D+A");
		ASM.add("@R13");
		ASM.add("M=D");

		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");
		ASM.add("@R13");
		ASM.add("A=M");
		ASM.add("M=D");

		// Update stack pointer
		ASM.add("@SP");
		ASM.add("M=M-1");

		// to convert the array list to String
		StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
	}
	static String PopTemp(String line, int n)
	{
		List<String> ASM = new ArrayList<>();

		// Add comment to asm
		ASM.add("// pop temp " + n);

		ASM.add("@5");
		ASM.add("D=A");
		ASM.add("@" + n);
		ASM.add("D=D+A");
		ASM.add("@R13");
		ASM.add("M=D");

		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");
		ASM.add("@R13");
		ASM.add("A=M");
		ASM.add("M=D");

		// Update stack pointer
		ASM.add("@SP");
		ASM.add("M=M-1");

		// to convert the array list to String
		StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
	}
   	static String eq(String vmName,String line)
    {
        List <String> ASM =new ArrayList<>();
        String labelEQTrue = vmName + ".EQ.true." + eqCount;
		String labelEQEnd = vmName + ".EQ.end." + eqCount;
        eqCount++;

		// Add comment to asm
		ASM.add("// eq");

		// Load second operand
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");
		ASM.add("@R13");
		ASM.add("M=D");

		// Update stack pointer
		ASM.add("@SP");
		ASM.add("M=M-1");

		// Load first operand
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");

		// Sub
		ASM.add("@R13");
		ASM.add("D=D-M");

		// Check to jump
		ASM.add("@" + labelEQTrue);
		ASM.add("D;JEQ");

		// No jump
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("M=0");
		ASM.add("@" + labelEQEnd);
		ASM.add("0;JMP");

		// Jump to here if true/equal
		ASM.add("(" + labelEQTrue + ")");
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("M=-1");

		// End
		ASM.add("(" + labelEQEnd + ")");

		// to convert the array list to String
        StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
    }
     static String lt(String vmName,String line)
    {
        List <String> ASM =new ArrayList<>();

        String labelLTTrue = vmName + ".LT.true." + ltCount;
		String labelLTEnd = vmName + ".LT.end." + ltCount;
		ltCount++;

		// Add comment to asm
		ASM.add("// lt");

		// Load second operand
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");
		ASM.add("@R13");
		ASM.add("M=D");

		// Update stack pointer
		ASM.add("@SP");
		ASM.add("M=M-1");

		// Load first operand
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");

		// Sub
		ASM.add("@R13");
		ASM.add("D=D-M");

		// Check to jump
		ASM.add("@" + labelLTTrue);
		ASM.add("D;JLT");

		// No jump
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("M=0");
		ASM.add("@" + labelLTEnd);
		ASM.add("0;JMP");

		// Jump to here if true/lesser than
		ASM.add("(" + labelLTTrue + ")");
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("M=-1");

		// End
		ASM.add("(" + labelLTEnd + ")");

		// to convert the array list to String
        StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
    }
    static String gt(String vmName,String line)
    {
        List <String> ASM =new ArrayList<>();
        String labelGTTrue = vmName + ".GT.true." + gtCount;
		String labelGTEnd = vmName + ".GT.end." + gtCount;
		gtCount++;

		// Add comment to asm
		ASM.add("// gt");

		// Load second operand
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");
		ASM.add("@R13");
		ASM.add("M=D");

		// Update stack pointer
		ASM.add("@SP");
		ASM.add("M=M-1");

		// Load first operand
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");

		// Sub
		ASM.add("@R13");
		ASM.add("D=D-M");

		// Check to jump
		ASM.add("@" + labelGTTrue);
		ASM.add("D;JGT");

		// No jump
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("M=0");
		ASM.add("@" + labelGTEnd);
		ASM.add("0;JMP");

		// Jump to here if true/greater than
		ASM.add("(" + labelGTTrue + ")");
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("M=-1");

		// End
		ASM.add("(" + labelGTEnd + ")");

		// to convert the array list to String
        StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
    }
    static String add(String line)
    {
        List <String> ASM =new ArrayList<>();
        ASM.add("// add");

		// Load second operand
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");
		ASM.add("@R13");
		ASM.add("M=D");

		// Update stack pointer
		ASM.add("@SP");
		ASM.add("M=M-1");

		// Load first operand
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");

		// Add
		ASM.add("@R13");
		ASM.add("D=D+M");

		// Store the result
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("M=D");

		// to convert the array list to String
        StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
    }
    static String sub(String line)
    {
        List <String> ASM =new ArrayList<>();
        ASM.add("// sub");

		// Load second operand
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");
		ASM.add("@R13");
		ASM.add("M=D");
        
		// Update stack pointer
		ASM.add("@SP");
        
		ASM.add("M=M-1");
        
		// Load first operand
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");
        
		// Sub
		ASM.add("@R13");
		ASM.add("D=D-M");

		// Store the result
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("M=D");

		// to convert the array list to String
        StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
    }
    static String neg(String line)
    {
        List <String> ASM =new ArrayList<>();
        ASM.add("// neg");

		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("M=-M");

		// to convert the array list to String
        StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
    }
    static String and(String line)
    {
        List <String> ASM =new ArrayList<>();
		// Add comment to asm
		ASM.add("// and");

		// Load second operand
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");
		ASM.add("@R13");
		ASM.add("M=D");

		// Update stack pointer
		ASM.add("@SP");
		ASM.add("M=M-1");

		// Load first operand
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");

		// And
		ASM.add("@R13");
		ASM.add("D=D&M");

		// Store the result
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("M=D");

		// to convert the array list to String
        StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
    }
    static String or(String line)
    {
        List <String> ASM =new ArrayList<>();
        // Add comment to asm
		ASM.add("// or");

		// Load second operand
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");
		ASM.add("@R13");
		ASM.add("M=D");

		// Update stack pointer
		ASM.add("@SP");
		ASM.add("M=M-1");

		// Load first operand
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("D=M");

		// Or
		ASM.add("@R13");
		ASM.add("D=D|M");

		// Store the result
		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("M=D");

		// to convert the array list to String
        StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
    }
    static String not(String line)
    {
        List <String> ASM =new ArrayList<>();
        ASM.add("// not");

		ASM.add("@SP");
		ASM.add("A=M-1");
		ASM.add("M=!M");

		// to convert the array list to String
        StringBuffer sb = new StringBuffer();
        for (String s : ASM) 
        {
         sb.append(s);
         sb.append("\n");
        }
        String asm = sb.toString();
		return asm;
    }
}
