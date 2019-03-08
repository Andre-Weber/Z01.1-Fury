library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity TopLevel is 
    port (
        sw : in  STD_LOGIC_VECTOR(8 downto 0);
        HEX0: out STD_LOGIC_VECTOR(6 downto 0);
        HEX1: out STD_LOGIC_VECTOR(6 downto 0);
        HEX2: out STD_LOGIC_VECTOR(6 downto 0)
    );
end entity TopLevel;

architecture arch of TopLevel is
    signal display  : STD_LOGIC_VECTOR(20 downto 0);
    signal v_input  : integer;

    function seven_segment(
        bcd_digit: STD_LOGIC_VECTOR(3 downto 0)
    ) return STD_LOGIC_VECTOR
    is begin
        case bcd_digit is
            when x"0" => return "1000000";
            when x"1" => return "1111001";
            when x"2" => return "0100100";
            when x"3" => return "0110000";
            when x"4" => return "0011001";
            when x"5" => return "0010010";
            when x"6" => return "0000010";
            when x"7" => return "1111000";
            when x"8" => return "0000000";
            when x"9" => return "0010000";
				when others => return "1111111";
        end case;
    end;

    function to_seven_segment(
        value : unsigned;
        digits: integer
    ) return STD_LOGIC_VECTOR
    is 
        variable displays : STD_LOGIC_VECTOR(digits*7-1 downto 0);
        variable quotient : unsigned(value'range);
        variable remainder: unsigned(3 downto 0);
    begin
        quotient := value;
        for i in 0 to digits-1 loop
            remainder := resize(quotient mod 10, 4);
            quotient  := quotient/10;
            displays(i*7+6 downto i*7) := seven_segment(
                STD_LOGIC_VECTOR(remainder)
            );
        end loop;
        return displays;
    end;

begin

    v_input <= to_integer(unsigned(sw));

    display <= to_seven_segment(
        unsigned(sw),
        3
    );

    HEX2 <= display(20 downto 14);
    HEX1 <= display(13 downto 7);
    HEX0 <= display(6 downto 0);

end architecture;