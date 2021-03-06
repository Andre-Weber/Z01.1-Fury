/**
 * Curso: Elementos de Sistemas
 * Arquivo: Parser.java
 */

package assembler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Encapsula o código de leitura. Carrega as instruções na linguagem assembly,
 * analisa, e oferece acesso as partes da instrução  (campos e símbolos).
 * Além disso, remove todos os espaços em branco e comentários.
 */
public class Parser {

    public String currentCommand = "";  // comando atual
    public String inputFile;		    // arquivo de leitura
    public int lineNumber = 0;			// linha atual do arquivo (nao do codigo gerado)
    public String currentLine;			// linha de codigo atual
    public Scanner scanner;
    private BufferedReader fileReader;  // leitor de arquivo
    public boolean fileClosed;

    /** Enumerator para os tipos de comandos do Assembler. */
    public enum CommandType {
        A_COMMAND,      // comandos LEA, que armazenam no registrador A
        C_COMMAND,      // comandos de calculos
        L_COMMAND,       // comandos de Label (símbolos)
        M_COMMAND
    }

    /**
     * Abre o arquivo de entrada NASM e se prepara para analisá-lo.
     * @param file arquivo NASM que será feito o parser.
     */
    public Parser(String file) throws FileNotFoundException {
        this.lineNumber = 0;
        this.inputFile = file;
        this.fileReader = new BufferedReader(new FileReader(file));
        this.fileClosed = false;
    }

    /**
     * Carrega uma instrução e avança seu apontador interno para o próxima
     * linha do arquivo de entrada. Caso não haja mais linhas no arquivo de
     * entrada o método retorna "Falso", senão retorna "Verdadeiro".
     * @return Verdadeiro se ainda há instruções, Falso se as instruções terminaram.
     */
    public Boolean advance() throws IOException {
        // usar o fileReader.readLine();
        String line;
        do {
            line = fileReader.readLine();
            if (line != null && line.length() > 0 && line.charAt(0) != ';') {
                String[] splitLine = line.split(";"); // Removendo comentario de meio de linha

                lineNumber++;
                currentLine = splitLine[0];
                currentLine = currentLine.trim();
                return true;
            }
        } while (line != null);
        fileReader.close();
        this.fileClosed = true;
        return false;
    }

    /**
     * Retorna o comando "intrução" atual (sem o avanço)
     * @return a instrução atual para ser analilisada
     */
    public String command() {
    	return currentLine;
    }

    /**
     * Retorna o tipo da instrução passada no argumento:
     *  A_COMMAND para leaw, por exemplo leaw $1,%A
     *  L_COMMAND para labels, por exemplo Xyz: , onde Xyz é um símbolo.
     *  C_COMMAND para todos os outros comandos
     * @param  command instrução a ser analisada.
     * @return o tipo da instrução.
     */
    public CommandType commandType(String command) {
    	if (command.toLowerCase().contains("leaw")) {
    	    return CommandType.A_COMMAND;
        } else if (command.toLowerCase().contains(":")) {
    	    return CommandType.L_COMMAND;
        } else if (command.toLowerCase().contains("%macro") || command.toLowerCase().contains("%endmacro")){
    	    return CommandType.M_COMMAND;
        } else {
    	    return CommandType.C_COMMAND;
        }
    }

    /**
     * Retorna o símbolo ou valor numérico da instrução passada no argumento.
     * Deve ser chamado somente quando commandType() é A_COMMAND.
     * @param  command instrução a ser analisada.
     * @return somente o símbolo ou o valor número da instrução.
     */
    public String symbol(String command) {
        if (command.contains("$")) {
            return command.substring(command.indexOf("$") + 1, command.indexOf(","));
        } else if (command.contains("par")) {
            return command.substring(command.indexOf(" ") + 1, command.indexOf(","));
        } else {
            return command.substring(command.indexOf("%") + 1, command.indexOf(","));
        }
    }

    /**
     * Retorna o símbolo da instrução passada no argumento.
     * Deve ser chamado somente quando commandType() é L_COMMAND.
     * @param  command instrução a ser analisada.
     * @return o símbolo da instrução (sem os dois pontos).
     */
    public String label(String command) {
    	return command.substring(0, command.length()-1);
    }

    /**
     * Separa os mnemônicos da instrução fornecida em tokens em um vetor de Strings.
     * Deve ser chamado somente quando CommandType () é C_COMMAND.
     * @param  command instrução a ser analisada.
     * @return um vetor de string contento os tokens da instrução (as partes do comando).
     */
    public String[] instruction(String command) {
        String[] aCommand = command.split(" |,");
        String newWord = "";
        for (String string : aCommand) {
            if (!(string.equals(" ") || string.equals(""))) {
                newWord += string;
                newWord += ",";
            }
        }
        String[] mReturn = newWord.split(",");
        return mReturn;
    }

    public int params(String command) {
        String[] aCommand = command.split(" ");
        String paramsNumber = aCommand[aCommand.length - 1];
        return Integer.valueOf(paramsNumber);
    }

    public String macroName(String command) {
        return command.split(" ")[1];
    }

    // fecha o arquivo de leitura
    public void close() throws IOException {
        fileReader.close();
    }


}
