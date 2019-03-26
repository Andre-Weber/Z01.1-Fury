-- Elementos de Sistemas
-- by Luciano Soares
-- Register16.vhd

Library ieee;
use ieee.std_logic_1164.all;

entity Register16 is
	port(
		clock:   in STD_LOGIC;
		input:   in STD_LOGIC_VECTOR(15 downto 0);
		load:    in STD_LOGIC;
		output: out STD_LOGIC_VECTOR(15 downto 0)
	);
end entity;

architecture arch of Register16 is
  -- Aqui declaramos sinais (fios auxiliares)
  -- e componentes (outros módulos) que serao
  -- utilizados nesse modulo.
  component Register8 is
    port(
      clock : in STD_LOGIC;
	  input : in STD_LOGIC_VECTOR(7 downto 0);
	  load  : in STD_LOGIC;
	  output: out STD_LOGIC_VECTOR(7 downto 0) 
	);
  end component;

begin

  process_0: Register8 port map(clock, input(7 downto 0), load, output(7 downto 0));
  process_1: Register8 port map(clock, input(15 downto 8), load, output(15 downto 8));

end architecture;
