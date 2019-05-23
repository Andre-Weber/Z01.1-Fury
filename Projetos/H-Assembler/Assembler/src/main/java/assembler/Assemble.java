/**
 * Curso: Elementos de Sistemas
 * Arquivo: Assemble.java
 * Created by Luciano <lpsoares@insper.edu.br> 
 * Date: 04/02/2017
 *
 * 2018 @ Rafael Corsi
 */

package assembler;



import java.io.*;
import java.util.*;

/**
 * Faz a geração do código gerenciando os demais módulos
 */
public class Assemble {
    private String inputFile;              // arquivo de entrada nasm
    File hackFile = null;                  // arquivo de saída hack
    private PrintWriter outHACK = null;    // grava saida do código de máquina em Hack
    boolean debug;                         // flag que especifica se mensagens de debug são impressas
    private SymbolTable table;             // tabela de símbolos (variáveis e marcadores)

    private String[] jumpTypes = {
            "jmp",
            "je",
            "jne",
            "jg",
            "jge",
            "jl",
            "jle"
    };

    private String nopCommand = "100000000000000000";

    private HashMap<String, Macro> hashMap = new HashMap<>();

    public Assemble(String inFile, String outFileHack, boolean debug) throws IOException {
        this.debug = debug;
        if (debug) {
            System.out.println("Debug Mode is on.");
            System.out.println("Starting Assembler");
            System.out.println("-");
            System.out.println("File will be written to address " + outFileHack);
            System.out.println("-");
        }
        inputFile  = inFile;
        hackFile   = new File(outFileHack);                      // Cria arquivo de saída .hack
        outHACK    = new PrintWriter(new FileWriter(hackFile));  // Cria saída do print para
                                                                 // o arquivo hackfile
        table      = new SymbolTable();                          // Cria e inicializa a tabela de simbolos

    }

    /**
     * primeiro passo para a construção da tabela de símbolos de marcadores (labels)
     * varre o código em busca de Símbolos novos Labels e Endereços de memórias
     * e atualiza a tabela de símbolos com os endereços.
     *
     * Dependencia : Parser, SymbolTable
     */
    public SymbolTable fillSymbolTable() throws FileNotFoundException, IOException {
        if (debug) {
            System.out.println("Building Symbol Table.");
        }
        int addressL = 0;
        Parser parserL = new Parser(inputFile);
        String lastCommand = "";
        while (parserL.advance()){
            String command = parserL.command();
            if (!lastCommand.equals("")) {
                if (parserL.commandType(lastCommand) == Parser.CommandType.C_COMMAND) {
                    if (Arrays.asList(jumpTypes).contains(parserL.instruction(lastCommand)[0])) {
                        if (!command.trim().equals("nop")) {
                            addressL += 1;
                        }
                    }
                }
            }
            lastCommand = command;
            if (parserL.commandType(command) == Parser.CommandType.L_COMMAND){
                String label = parserL.label(command);
                if (!table.contains(label)){
                    table.addEntry(label, addressL);
                    if (debug) {
                        System.out.println("Added label " + label + " to memory address " + addressL + ".");
                    }
                }
            }
            else{
                addressL += 1;
            }
        }
        int addressA = 16;
        Parser parserA = new Parser(inputFile);
        while (parserA.advance()){
            if (parserA.commandType(parserA.command()) == Parser.CommandType.A_COMMAND) {
                String symbol = parserA.symbol(parserA.command());
                if (!symbol.matches("[0-9]+")) { //Checks it is not number
                    if (!table.contains(symbol)) {
                        if (!symbol.contains("par")) {
                            table.addEntry(symbol, addressA);
                            addressA += 1;
                            if (debug) {
                                System.out.println("Added symbol " + symbol + " to memory address " + addressA + ".");
                            }
                        }
                    }
                }
            }
        }
        if (debug) {
            System.out.println("-");
        }
        return table;
    }

    public void findMacros() throws FileNotFoundException, IOException {
        if (debug) {
            System.out.println("Looking for macros.");
        }
        Parser parser = new Parser(inputFile);
        while (parser.advance()) {
            String command = parser.command();
            if (parser.commandType(command) == Parser.CommandType.M_COMMAND) {
                String macroName = parser.macroName(command);
                int paramsNumber = parser.params(command);
                if (debug) {
                    System.out.println("-");
                    System.out.println("Found Macro.");
                    System.out.println("Name: " + macroName);
                    System.out.println("Number of Params: " + paramsNumber);
                    System.out.println("Saving Macro.");
                    System.out.println("-");
                }
                LinkedList<String> macroCommands = new LinkedList<>();
                boolean inMacro = true;
                while (inMacro) {
                    parser.advance();
                    String aCommand = parser.command();
                    if (parser.commandType(aCommand) == Parser.CommandType.M_COMMAND) {
                        inMacro = false;
                    } else {
                        macroCommands.add(aCommand);
                    }
                }
                Macro macro = new Macro(macroName, paramsNumber, macroCommands, inputFile, hashMap, table, debug);
                hashMap.put(macroName, macro);
            }
        }
    }


    /**
     * Segundo passo para a geração do código de máquina
     * Varre o código em busca de instruções do tipo A, C
     * gerando a linguagem de máquina a partir do parse das instruções.
     *
     * Dependencias : Parser, Code
     */
    public void generateMachineCode() throws FileNotFoundException, IOException{
        if (debug) {
            System.out.println("Generating Machine Code");
        }
        Parser parser = new Parser(inputFile);  // abre o arquivo e aponta para o começo
        String instruction  = null;
        String lastCommand = "";
        int currentLine = 0;
        /**
         * Aqui devemos varrer o código nasm linha a linha
         * e gerar a string 'instruction' para cada linha
         * de instrução válida do nasm
         */
        while (parser.advance()){
            String mCommand = parser.command();
            if (parser.commandType(mCommand) == Parser.CommandType.M_COMMAND) {
                boolean inMacro = true;
                while (inMacro) {
                    parser.advance();
                    String line = parser.command();
                    if (parser.commandType(line) == Parser.CommandType.M_COMMAND) {
                        inMacro = false;
                    }
                }
            } else {
                switch (parser.commandType(mCommand)) {
                    case C_COMMAND:
                        String[] command = parser.instruction(mCommand);
                        if (hashMap.containsKey(command[0])) {
                            if (debug) {
                                System.out.println("-");
                                System.out.println("Found Macro " + command[0]);
                                System.out.println("Inserting Commands on .Hack file");
                                System.out.println("-");
                            }
                            String[] params = new String[command.length - 1];
                            System.arraycopy(command, 1, params, 0, command.length - 1);
                            LinkedList<String> instructions = hashMap.get(command[0]).generateInstructions(params);
                            if (outHACK != null) {
                                for (String string : instructions) {
                                    outHACK.println(string);
                                    if (parser.fileClosed) {
                                        break;
                                    }
                                }
                            }
                        } else {
                            instruction = "10" + Code.comp(command) + Code.dest(command) + Code.jump(command);

                        }
                        break;
                    case A_COMMAND:
                        String mSymbol = parser.symbol(mCommand);
                        String mInstruction;
                        if (mSymbol.matches("[0-9]+")) {
                            mInstruction = Code.toBinary(mSymbol);
                            if (debug) {
                                System.out.println("Loading " + mSymbol + " in Register A");
                            }
                        } else {
                            Integer symbol = table.getAddress(mSymbol);
                            mInstruction = Code.toBinary(symbol.toString());
                            if (debug) {
                                System.out.println("Loading " + mSymbol + " (stored in memory address " + symbol + ") in Register A");
                            }
                        }
                        instruction = "00" + mInstruction;
                        break;
                    default:
                        continue;
                }
            }
            if (!lastCommand.equals("")) {
                if (parser.commandType(lastCommand) == Parser.CommandType.C_COMMAND) {
                    if (Arrays.asList(jumpTypes).contains(parser.instruction(lastCommand)[0])) {
                        if (!mCommand.trim().equals("nop")) {
                            if (debug) {
                                System.out.println("-");
                                System.out.println("[WARNING]");
                                System.out.println("Command " + lastCommand + " on line " + (currentLine - 1));
                                System.out.println("This would require a NOP command on line " + currentLine);
                                System.out.println("Nop command was automaticaly added for you!");
                                System.out.println("-");
                                }
                            if (outHACK!=null) {
                                outHACK.println(nopCommand);
                            }
                        }
                    }
                }
            }
            lastCommand = mCommand;
            // Escreve no arquivo .hack a instrução
            if(outHACK!=null) {
                if (instruction!= null) {
                    outHACK.println(instruction);
                }
            }
            instruction = null;
            currentLine += 1;
            if (parser.fileClosed) {
                break;
            }
        }

    }

    /**
     * Fecha arquivo de escrita
     */
    public void close() throws IOException {
        if(outHACK!=null) {
            if (debug) {
                System.out.println("Closing File.");
                System.out.println("-");
            }
            outHACK.close();
        }
    }

    /**
     * Remove o arquivo de .hack se algum erro for encontrado
     */
    public void delete() {
        try{
            if(hackFile!=null) {
                if (debug) {
                    System.out.println("Error encountered");
                    System.out.println("Deleting .hack File");
                    System.out.println("-");
                }
                hackFile.delete();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
