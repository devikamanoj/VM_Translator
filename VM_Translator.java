import java.util.*;
import java.io.*;
public class VM_Translator 
{
    public static void main(String[] args) 
    {
        try  
        {  
            // ASM file to write the codes
            File NewFile = new File("D:\\S2\\EoC\\Lab\\Lab Codes\\VM_Translator\\src\\FibinocciElement.asm");
            PrintWriter out = new PrintWriter(NewFile); 

            // Directory from where the files to be read
            File file1=new File("D:\\S2\\EoC\\Lab\\M_DEVIKA_AM.EN.U4AIE20144\\nand2tetris\\projects\\08\\FunctionCalls\\FibinocciElement");  //enter the directory
            String path = file1.getAbsolutePath(); // makes the directory to String
            ArrayList<String> Files = new ArrayList<String>(Arrays.asList(file1.list())); // file -->arraylist
            String line;

            // checks the directory if it contain any VM file
            ArrayList<String> VMFiles=new ArrayList<String>(); // for storing the the vm files in the array list
            if(Files!=null) // checks if the directory is empty or not
            {
                for(int i=0;i<Files.size();i++) 
                {
                    if(Files.get(i).endsWith(".vm"))// checks if the folder have ".vm" files
                    {
                        VMFiles.add(path+"\\"+Files.get(i)); // adds the path to the arraylist VMFiles
                    }
                }
            }
            if(VMFiles.size()==1)
            {
                if(VMFiles.get(0).endsWith("Sys.vm"))
                {
                    //for sys file
                    File sysFile = new File(VMFiles.get(0));
                    FileReader FRS = new FileReader(sysFile);
                    BufferedReader BRS=new BufferedReader(FRS);

                    if((line=BRS.readLine())!=null)  
                    {  
                        String FinalCode1=  VM_Codes.sysFilePresent(line);
                        if(!FinalCode1.isEmpty())
                        {
                            out.println(FinalCode1); //writes bootstrap code
                        }
                        VM_Codes.vmName="Sys.vm";
                        String FinalCode=  VM_Codes.SplitingCodes(line,VM_Codes.vmName);// goes to the Spliting codes method in class VM_Codes
                        if(!FinalCode.isEmpty())
                        {
                            out.println(FinalCode);// writes the code to the file
                        }
                    }  
                    while((line=BRS.readLine())!=null)
                    {
                        VM_Codes.vmName="Sys.vm"; // to mention @sys.main in the call function 
                        String FinalCode=  VM_Codes.SplitingCodes(line,VM_Codes.vmName);// goes to the Spliting codes method in class VM_Codes
                        if(!FinalCode.isEmpty())
                        {
                        out.println(FinalCode); //translates the sys file contents
                        }
                    }
                    out.close();   
                    FRS.close();
                }
                else
                {
                    // for the folders which does not contain sys.vm file
                    File mainFile = new File(VMFiles.get(0));
                    FileReader FRM = new FileReader(mainFile);
                    BufferedReader BRM=new BufferedReader(FRM);

                    while((line=BRM.readLine())!=null)
                    {
                        VM_Codes.vmName="Main.vm";// to mention @Main.main in the call function 
                        String FinalCode=  VM_Codes.SplitingCodes(line,VM_Codes.vmName);// goes to the Spliting codes method in class VM_Codes
                        if(!FinalCode.isEmpty())
                        {
                           out.println(FinalCode); //tanslates the main.vm
                        }
                    }
                    out.close();
                    FRM.close();  
                }
            }
            if(VMFiles.size()==2)
            {
                //main file
            	File mainFile = new File(VMFiles.get(0));
                FileReader FRM = new FileReader(mainFile);
                BufferedReader BRM=new BufferedReader(FRM);

                // sys file
                File sysFile = new File(VMFiles.get(1));
                FileReader FRS = new FileReader(sysFile);
                BufferedReader BRS=new BufferedReader(FRS);
               
                if((line=BRS.readLine())!=null)  
                {  
                    String FinalCode1=  VM_Codes.sysFilePresent(line);// goes to the sysFilePresent method in class VM_Codes
                    if(!FinalCode1.isEmpty())
                    {
                       out.println(FinalCode1); //writes bootstrap code
                    }
                }  
                while((line=BRS.readLine())!=null)
                {
                    VM_Codes.vmName="Sys.vm";
                    String FinalCode=  VM_Codes.SplitingCodes(line,VM_Codes.vmName);// goes to the Spliting codes method in class VM_Codes
                    if(!FinalCode.isEmpty())
                    {
                       out.println(FinalCode); //translates the sys file contents
                    }
                }
                while((line=BRM.readLine())!=null)
                {
                    VM_Codes.vmName="Main.vm";
                    String FinalCode=  VM_Codes.SplitingCodes(line,VM_Codes.vmName);// goes to the Spliting codes method in class VM_Codes
                    if(!FinalCode.isEmpty())
                    {
                       out.println(FinalCode); //tanslates the main.vm
                    }
                }
                out.close();
                FRM.close();   
                FRS.close();
            }
            if(VMFiles.size()==3)
            {
                 //class1 file
            	File class1File = new File(VMFiles.get(0));
                FileReader class1M = new FileReader(class1File);
                BufferedReader BR1=new BufferedReader(class1M);

                //class2 file
            	File class2File = new File(VMFiles.get(1));
                FileReader class2M = new FileReader(class2File);
                BufferedReader BR12=new BufferedReader(class2M);

                // sys file
                File sysFile = new File(VMFiles.get(2));
                FileReader FRS = new FileReader(sysFile);
                BufferedReader BRS=new BufferedReader(FRS);

                if((line=BRS.readLine())!=null)  
                {  
                    String FinalCode1=  VM_Codes.sysFilePresent(line);// goes to the sysFilePresent method in class VM_Codes
                    if(!FinalCode1.isEmpty())
                    {
                       out.println(FinalCode1); //writes bootstrap code
                    }
                }  
                 while((line=BRS.readLine())!=null)
                {
                    VM_Codes.vmName="Sys.vm";
                    String FinalCode=  VM_Codes.SplitingCodes(line,VM_Codes.vmName);// goes to the Spliting codes method in class VM_Codes
                    if(!FinalCode.isEmpty())
                    {
                       out.println(FinalCode); //translates the sys file contents
                    }
                }
                while((line=BR1.readLine())!=null)
                {
                    VM_Codes.vmName="Class1.vm";
                    String FinalCode=  VM_Codes.SplitingCodes(line,VM_Codes.vmName);// goes to the Spliting codes method in class VM_Codes
                    if(!FinalCode.isEmpty())
                    {
                       out.println(FinalCode); //tanslates the Class1.vm
                    }
                }
                while((line=BR12.readLine())!=null)
                {
                    VM_Codes.vmName="Class2.vm";
                    String FinalCode=  VM_Codes.SplitingCodes(line,VM_Codes.vmName);// goes to the Spliting codes method in class VM_Codes
                    if(!FinalCode.isEmpty())
                    {
                       out.println(FinalCode); //tanslates the Class2.vm
                    }
                }
                out.close();
                FRS.close();
                class1M.close();   
                class2M.close();
            }
        }          
        catch(IOException e)  
        {  
        	
        }  
    }
}
