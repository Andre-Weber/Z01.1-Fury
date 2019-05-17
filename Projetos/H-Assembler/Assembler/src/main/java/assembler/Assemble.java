/**
 * Curso: Elementos de Sistemas
 * Arquivo: Assemble.java
 * Created by Luciano <lpsoares@insper.edu.br> 
 * Date: 04/02/2017
 *
 * 2018 @ Rafael Corsi
 */

package assembler;

import com.sun.deploy.util.StringUtils;

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
        while (parserL.advance()){
            if (parserL.commandType(parserL.command()) == Parser.CommandType.L_COMMAND){
                String label = parserL.label(parserL.command());
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
                        table.addEntry(symbol, addressA);
                        addressA += 1;
                        if (debug) {
                            System.out.println("Added symbol " + symbol + " to memory address " + addressA + ".");
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
        /**
         * Aqui devemos varrer o código nasm linha a linha
         * e gerar a string 'instruction' para cada linha
         * de instrução válida do nasm
         */
        while (parser.advance()){
            switch (parser.commandType(parser.command())){
                case C_COMMAND:
                    String[] command = parser.instruction(parser.command());
                    instruction = "10" + Code.comp(command) + Code.dest(command) + Code.jump(command);

                    break;
                case A_COMMAND:
                    String mSymbol = parser.symbol(parser.command());
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

            // Escreve no arquivo .hack a instrução
            if(outHACK!=null) {
                outHACK.println(instruction);
            }
            instruction = null;
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
