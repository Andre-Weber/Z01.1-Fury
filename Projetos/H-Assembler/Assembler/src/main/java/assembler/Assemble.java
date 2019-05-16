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
        int AddressL = 0;
        Parser parser = new Parser(inputFile);
        while (parser.advance()){
            if (parser.commandType(parser.command()) == Parser.CommandType.L_COMMAND) {
                if (!table.contains(parser.label(parser.command()))) {
                    table.addEntry(parser.label(parser.command()), parser.lineNumber);
                    AddressL ++;
                }
            }

        }

        int AddressA = 0;
        Parser parser2 = new Parser(inputFile);
        while(parser2.advance()){
            if (parser2.commandType(parser2.command()) == Parser.CommandType.A_COMMAND) {
                if (!table.contains(parser2.symbol(parser2.command()))) {
                    table.addEntry(parser2.symbol(parser2.command()), AddressA);
                    AddressA ++;
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

        /**
         * Aqui devemos varrer o código nasm linha a linha
         * e gerar a string 'instruction' para cada linha
         * de instrução válida do nasm
         */
        while (parser.advance()){
            switch (parser.commandType(parser.command())){
                case C_COMMAND:
                    Code.comp(parser.instruction(parser.command()));
                    break;
                case A_COMMAND:
                    String mSymbol = parser.symbol(parser.command());
                    String mInstruction;
                    if (mSymbol.matches("[0-9]+")) {
                        mInstruction = Code.toBinary(mSymbol);
                    } else {
                        Integer symbol = table.getAddress(mSymbol);
                        mInstruction = Code.toBinary(symbol.toString());
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
