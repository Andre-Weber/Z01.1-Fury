/**
 * Curso: Elementos de Sistemas
 * Arquivo: Code.java
 */

package assembler;

/**
 * Traduz mnemônicos da linguagem assembly para códigos binários da arquitetura Z0.
 */
public class Code {

    /**
     * Retorna o código binário do(s) registrador(es) que vão receber o valor da instrução.
     *
     * @param mnemnonic vetor de mnemônicos "instrução" a ser analisada.
     * @return Opcode (String de 4 bits) com código em linguagem de máquina para a instrução.
     */
    public static String dest(String[] mnemnonic) {

        char d3= '0', d2= '0', d1= '0', d0 = '0';
        StringBuilder sb = new StringBuilder();
        if(mnemnonic.length == 2){
            if(mnemnonic[0].equals("jmp") || mnemnonic[0].equals("je") || mnemnonic[0].equals("jne") || mnemnonic[0].equals("jg") || mnemnonic[0].equals("jge") || mnemnonic[0].equals("jl") || mnemnonic[0].equals("jle")){
                d3 = '0';
                d2 = '0';
                d1 = '0';
                d0 = '0';
            } else {
                switch (mnemnonic[1]) {
                    case "%A":
                        d3 = '1';
                        break;
                    case "%D":
                        d1 = '1';
                        break;
                    case "%S":
                        d2 = '1';
                        break;
                    case "(%A)":
                        d0 = '1';
                        break;
                }
            }

        }
        if(mnemnonic.length == 3){
            switch (mnemnonic[2]){
                case "%A":
                    d3 = '1';
                    break;
                case "%D":
                    d1 = '1';
                    break;
                case "%S":
                    d2 = '1';
                    break;
                case "(%A)":
                    d0 = '1';
                    break;
            }
        }
        if(mnemnonic.length == 4){
            switch (mnemnonic[2]){
                case "%A":
                    d3 = '1';
                    break;
                case "%D":
                    d1 = '1';
                    break;
                case "%S":
                    d2 = '1';
                    break;
                case "(%A)":
                    d0 = '1';
                    break;
            }
            switch (mnemnonic[3]){
                case "%A":
                    d3 = '1';
                    break;
                case "%D":
                    d1 = '1';
                    break;
                case "%S":
                    d2 = '1';
                    break;
                case "(%A)":
                    d0 = '1';
                    break;

            }
            if(mnemnonic[0].equals("addw") || mnemnonic[0].equals("subw") || mnemnonic[0].equals("rsubw") || mnemnonic[0].equals("andw")){
                switch (mnemnonic[3]){
                    case "%A":
                        d3 = '1';
                        d2 = '0';
                        d1 = '0';
                        d0 = '0';
                        break;
                    case "%D":
                        d1 = '1';
                        d3 = '0';
                        d2 = '0';
                        d0 = '0';
                        break;
                    case "%S":
                        d2 = '1';
                        d3 = '0';
                        d1 = '0';
                        d0 = '0';
                        break;
                    case "(%A)":
                        d0 = '1';
                        d3 = '0';
                        d2 = '0';
                        d1 = '0';
                        break;

                }
            }

        }
        sb.append(d3);
        sb.append(d2);
        sb.append(d1);
        sb.append(d0);
        return sb.toString();

    }

    /**
     * Retorna o código binário do mnemônico para realizar uma operação de cálculo.
     * @param  mnemnonic vetor de mnemônicos "instrução" a ser analisada.
     * @return Opcode (String de 7 bits) com código em linguagem de máquina para a instrução.
     */
    public static String comp(String[] mnemnonic) {
        char r2 = '0', r1 = '0', r0 = '0', c5 = '0', c4 = '0', c3= '0', c2= '0', c1= '0', c0= '0';
        String r = "000";
        StringBuilder sb = new StringBuilder();
        if (mnemnonic[0].equals("jmp")) {
            return "000001100";
        }
        if(mnemnonic.length == 2){
            if(mnemnonic[0].equals("jmp") || mnemnonic[0].equals("je") || mnemnonic[0].equals("jne") || mnemnonic[0].equals("jg") || mnemnonic[0].equals("jge") || mnemnonic[0].equals("jl") || mnemnonic[0].equals("jle")){
                switch (mnemnonic[1]){
                    case "%D": case "%S":
                        c3 = '1';
                        c2 = '1';
                        break;
                    case "%A": case "(%A)":
                        c5 = '1';
                        c4 = '1';
                        break;
                }
            }
            if(mnemnonic[0].equals("incw")){
            switch (mnemnonic[1]){
                case "%A": case "(%A)":
                    c5 = '1';
                    c4 = '1';
                    c2 = '1';
                    c1 = '1';
                    c0 = '1';
                    break;
                case "%D": case "%S":
                    c4 = '1';
                    c3 = '1';
                    c2 = '1';
                    c1 = '1';
                    c0 = '1';
                    break;
            }


        }
            if(mnemnonic[0].equals("decw")){
                switch (mnemnonic[1]){
                    case "%D": case "%S":
                        c3 = '1';
                        c2 = '1';
                        c1 = '1';
                        break;
                    case "%A": case "(%A)":
                        c5 = '1';
                        c4 = '1';
                        c1 = '1';
                        break;

                }
            }
            if(mnemnonic[0].equals("notw")){
                switch (mnemnonic[1]){
                    case "%D": case "%S":
                        c3 = '1';
                        c2 = '1';
                        c0 = '1';
                        break;
                    case "%A": case "(%A)":
                        c5 = '1';
                        c4 = '1';
                        c0 = '1';
                        break;
                }
            }
            if(mnemnonic[0].equals("negw")){
                switch (mnemnonic[1]){
                    case "%D": case "%S":
                        c3 = '1';
                        c2 = '1';
                        c1 = '1';
                        c0 = '1';
                        break;
                    case "%A": case "(%A)":
                        c5 = '1';
                        c4 = '1';
                        c1 = '1';
                        c0 = '1';
                        break;

                }
            }
            r = mnemnonic[1];

        }
        if(mnemnonic.length == 3){
            switch (mnemnonic[1]){
                case "%A": case "(%A)":
                    c5 = '1';
                    c4 = '1';
                    break;
                case "%S": case "%D":
                    c3 = '1';
                    c2 = '1';
                    break;
                case "$1":
                    c5 = '1';
                    c4 = '1';
                    c3 = '1';
                    c2 = '1';
                    c1 = '1';
                    c0 = '1';
                    break;
                case "$0":
                    c5 = '1';
                    c4 = '0';
                    c3 = '1';
                    c2 = '0';
                    c1 = '1';
                    c0 = '0';
                    break;
                case "$-1":
                    c5 = '1';
                    c4 = '1';
                    c3 = '1';
                    c2 = '0';
                    c1 = '1';
                    c0 = '0';
                    break;
            }
            r = mnemnonic[1];

        }
        if(mnemnonic.length == 4){
            if(mnemnonic[0].equals("addw")){
                c1 = '1';
            }
            if(mnemnonic[0].equals("subw")){
                if(mnemnonic[1].equals("%S")){
                    switch (mnemnonic[2]){
                        case "$1":
                            c3 = '1';
                            c2 = '1';
                            c1 = '1';
                            break;
                        default:
                            c4 = '1';
                            c1 = '1';
                            c0 = '1';
                            break;
                    }

                }
                if(mnemnonic[1].equals("%D")){
                    switch (mnemnonic[2]){
                        case "%A": case "(%A)":
                            c4 = '1';
                            c1 = '1';
                            c0 = '1';
                            break;
                        case "%S":
                            c2 = '1';
                            c1 = '1';
                            c0 = '1';
                            break;
                        case "$1":
                            c3 = '1';
                            c2 = '1';
                            c1 = '1';
                            break;
                    }
                }
                if(mnemnonic[1].equals("(%A)")){
                    switch (mnemnonic[2]){
                        case "$1":
                            c5 = '1';
                            c4 = '1';
                            c1 = '1';
                            break;

                        default:
                            c2 = '1';
                            c1 = '1';
                            c0 = '1';
                            break;
                    }
                }
                if(mnemnonic[1].equals("%A")){
                    switch (mnemnonic[2]){
                        case "$1":
                            c5 = '1';
                            c4 = '1';
                            c1 = '1';
                            break;
                        default:
                            c2 = '1';
                            c1 = '1';
                            c0 = '1';
                            break;

                    }
                }

                }
            if(mnemnonic[0].equals("rsubw")){
                if(mnemnonic[2].equals("%D")){
                    switch (mnemnonic[2]){
                        case "%A":
                            c5 = '0';
                            c4 = '1';
                            c3 = '0';
                            c2 = '0';
                            c1 = '1';
                            c0 = '1';
                            break;
                        case "%S":
                            c5 = '0';
                            c4 = '0';
                            c3 = '0';
                            c2 = '1';
                            c1 = '1';
                            c0 = '1';
                            break;
                    }
                }
                if(mnemnonic[2].equals("%S")){
                    c4 = '1';
                    c1 = '1';
                    c0 = '1';
                }
                if(mnemnonic[2].equals("%D")){
                    switch (mnemnonic[2]){
                        case "%A": case "(%A)":
                            c4 = '1';
                            c1 = '1';
                            c0 = '1';
                            break;

                        case "%S":
                            c2 = '1';
                            c1 = '1';
                            c0 = '1';
                            break;
                    }
                }
                if(mnemnonic[2].equals("(%A)")){
                    c2 = '1';
                    c1 = '1';
                    c0 = '1';
                }
                if(mnemnonic[2].equals("%A")){
                    c2 = '1';
                    c1 = '1';
                    c0 = '1';

                }

            }
            if(mnemnonic[0].equals("orw")){
                c4 = '1';
                c2 = '1';
                c0 = '1';
            }

            r = mnemnonic[1] + mnemnonic[2];
            }





        if(r.contains("%D") || r.contains("%A") || (r.contains("%D") && r.contains("%A"))){
            r2 = '0';
            r1 = '0';
            r0 = '0';
        }
        if(r.contains("%S") || (r.contains("%S") && r.contains("%A"))){
            r2 = '0';
            r1 = '0';
            r0 = '1';
        }
        if(r.contains("(%A)") || (r.contains("%D") && r.contains("%(A)"))){
            r2 = '0';
            r1 = '1';
            r0 = '0';
        }

        if(r.contains("%S") && r.contains("(%A)")){
            r2 = '0';
            r1 = '1';
            r0 = '1';
        }

        if(r.contains("%S") && r.contains("%D")){
            r2 = '1';
            r1 = '0';
            r0 = '1';

        }
        sb.append(r2);
        sb.append(r1);
        sb.append(r0);
        sb.append(c5);
        sb.append(c4);
        sb.append(c3);
        sb.append(c2);
        sb.append(c1);
        sb.append(c0);
        return sb.toString();
    }

    /**
     * Retorna o código binário do mnemônico para realizar uma operação de jump (salto).
     * @param  mnemnonic vetor de mnemônicos "instrução" a ser analisada.
     * @return Opcode (String de 3 bits) com código em linguagem de máquina para a instrução.
     */
    public static String jump(String[] mnemnonic) {
        switch (mnemnonic[0]) {
            case "jmp":
                return "111";

            case "jle":
                return "110";

            case "jne":
                return "101";

            case "jl":
                return "100";

            case "jge":
                return "011";

            case "je":
                return "010";

            case "jg":
                return "001";
            default:
                return "000";


        }

    }

    /**
     * Retorna o código binário de um valor decimal armazenado numa String.
     * @param  symbol valor numérico decimal armazenado em uma String.
     * @return Valor em binário (String de 15 bits) representado com 0s e 1s.
     */
    public static String toBinary(String symbol) {
        int value = Integer.valueOf(symbol);
        String binary = Integer.toBinaryString(value);
        return String.format("%1$16s", binary).replace(" ", "0");
    }

}
