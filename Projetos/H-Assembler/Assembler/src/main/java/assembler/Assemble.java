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
        int addressL = 0;
        Parser parserL = new Parser(inputFile);
        while (parserL.advance()){
            if (parserL.commandType(parserL.command()) == Parser.CommandType.L_COMMAND){
                String label = parserL.label(parserL.command());
                if (!table.contains(label)){
                    table.addEntry(label, addressL);
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
                    }
                }
            }
        } return table;
    }


    /**
     * Segundo passo para a geração do código de máquina
     * Varre o código em busca de instruções do tipo A, C
     * gerando a linguagem de máquina a partir do parse das instruções.
     *
     * Dependencias : Parser, Code
     */
    public void generateMachineCode() throws FileNotFoundException, IOException{
        Parser parser = new Parser(inputFile);  // abre o arquivo e aponta para o começo
        String instruction  = null;
        Code codes = new Code();
        /**
         * Aqui devemos varrer o código nasm linha a linha
         * e gerar a string 'instruction' para cada linha
         * de instrução válida do nasm
         */
        while (parser.advance()){
            switch (parser.commandType(parser.command())){
                case C_COMMAND:
                    String[] command = parser.instruction(parser.command());
//                    for (String string: command) {
//                        System.out.println(string);
//                    }
//                    System.out.println();
//                    System.out.println();
//                    System.out.println();
                    instruction = "10" + codes.comp(command) + codes.dest(command) + codes.jump(command);

                    break;
                case A_COMMAND:
                    String mSymbol = parser.symbol(parser.command());
                    String mInstruction;
                    if (mSymbol.matches("[0-9]+")) {
                        mInstruction = codes.toBinary(mSymbol);
                    } else {
                        Integer symbol = table.getAddress(mSymbol);
                        System.out.println(symbol);
                        mInstruction = codes.toBinary(symbol.toString());
                    }
                    instruction = "00" + mInstruction;
                    break;
                default:
                    instruction = "\n";
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
            outHACK.close();
        }
    }

    /**
     * Remove o arquivo de .hack se algum erro for encontrado
     */
    public void delete() {
        try{
            if(hackFile!=null) {
                hackFile.delete();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
