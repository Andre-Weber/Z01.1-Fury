library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity CvtToDisplay is 
    port (
        sw : in  STD_LOGIC_VECTOR(8 downto 0);
        dp0: out STD_LOGIC_VECTOR(6 downto 0);
        dp1: out STD_LOGIC_VECTOR(6 downto 0);
        dp2: out STD_LOGIC_VECTOR(6 downto 0)
    );
end entity CvtToDisplay;

architecture arch of CvtToDisplay is
    signal display: STD_LOGIC_VECTOR(20 downto 0);
    signal v_input : integer;

    function seven_segment(
        bcd_digit: STD_LOGIC_VECTOR(3 downto 0)
    ) return STD_LOGIC_VECTOR
    is begin
        case bcd_digit is
            when x"0" => return "0111111";
            when x"1" => return "0000110";
            when x"2" => return "1011011";
            when x"3" => return "1001111";
            when x"4" => return "1100110";
            when x"5" => return "1101101";
            when x"6" => return "1111101";
            when x"7" => return "0000111";
            when x"8" => return "1111111";
            when x"9" => return "1101111";
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
            displays(i*7+6 downto i*7) <= seven_segment(
                STD_LOGIC_VECTOR(remainder)
            );
        end loop;
        return displays
    end;

begin

    v_input <= to_integer(unsigned(sw))

    display <= to_seven_segment(
        value <= unsigned(sw),
        digits <= 3
    );

    dp2 <= display(20 downto 14);
    dp1 <= display(13 downto 7);
    dp0 <= display(6 downto 0);

end architecture;