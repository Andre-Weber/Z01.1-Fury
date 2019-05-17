package assembler;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class Macro {

    private String name;
    private String inputFile;
    private int paramNumbers;
    private LinkedList<String> instructions;
    private HashMap<String, Macro> macros;
    private SymbolTable table;
    private boolean debug;

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

    public Macro(String name, int paramNumbers, LinkedList<String > instructions, String inputFile, HashMap<String, Macro> macros, SymbolTable table, boolean debug) {
        this.name = name;
        this.inputFile = inputFile;
        this.paramNumbers = paramNumbers;
        this.instructions = instructions;
        this.macros = macros;
        this.table = table;
        this.debug = debug;
    }

    public LinkedList<String> generateInstructions(String[] mParams) throws FileNotFoundException {
        LinkedList<String> linkedList = new LinkedList<>();
        Parser parser = new Parser(inputFile);
        String instruction = null;
        String lastCommand = "";
        int currentLine = 0;
        for (String mCommand : this.instructions) {
            switch (parser.commandType(mCommand)) {
                case C_COMMAND:
                    String[] command = parser.instruction(mCommand);
                    if (macros.containsKey(command[0])) {
                        if (debug) {
                            System.out.println("-");
                            System.out.println("Found Macro " + command[0] + " inside of macro " + name);
                            System.out.println("Inserting Commands on .Hack file");
                            System.out.println("-");
                        }
                        String[] params = new String[command.length - 1];
                        System.arraycopy(command, 1, params, 0, command.length - 1);
                        LinkedList<String> instructions = macros.get(command[0]).generateInstructions(params);
                        linkedList.addAll(instructions);
                    } else {
                        for (int i = 0; i < command.length; i++) {
                            if (command[i].matches("par[0-9]")) {
                                int paramNumber = Integer.valueOf(command[i].substring(command[i].length() - 1));
                                command[i] = mParams[paramNumber];
                            }
                        }
                        instruction = "10" + Code.comp(command) + Code.dest(command) + Code.jump(command);

                    }
                    break;
                case A_COMMAND:
                    String mSymbol = parser.symbol(mCommand);
                    String mInstruction;
                    if (mSymbol.trim().matches("par[0-9]")) {
                        int paramNumber = Integer.valueOf(mSymbol.substring(mSymbol.length() - 1));
                        mSymbol = mParams[paramNumber].substring(1);
                    }
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
            if (!lastCommand.equals("")) {
                if (parser.commandType(lastCommand) == Parser.CommandType.C_COMMAND) {
                    if (Arrays.asList(jumpTypes).contains(parser.instruction(lastCommand)[0])) {
                        if (!mCommand.trim().equals("nop")) {
                            if (debug) {
                                System.out.println("-");
                                System.out.println("[WARNING]");
                                System.out.println("Command " + lastCommand + " on line " + (currentLine - 1) + " of macro.");
                                System.out.println("This would require a NOP command on line " + currentLine + " of macro.");
                                System.out.println("Nop command was automaticaly added for you!");
                                System.out.println("-");
                            }
                            linkedList.add(nopCommand);
                        }
                    }
                }
            }
            lastCommand = mCommand;
            if (instruction != null) {
                linkedList.add(instruction);
            }
            currentLine++;
            instruction = null;
        }
        return linkedList;
    }

}
