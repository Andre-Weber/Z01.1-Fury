-- Elementos de Sistemas
-- by Luciano Soares
-- Ram4K.vhd

Library ieee;
use ieee.std_logic_1164.all;

entity Ram4K is
	port(
		clock:   in  STD_LOGIC;
		input:   in  STD_LOGIC_VECTOR(15 downto 0);
		load:    in  STD_LOGIC;
		address: in  STD_LOGIC_VECTOR(11 downto 0);
		output:  out STD_LOGIC_VECTOR(15 downto 0)
	);
end entity;

architecture arch of Ram4k is
  -- Aqui declaramos sinais (fios auxiliares)
  -- e componentes (outros módulos) que serao
  -- utilizados nesse modulo.
  component Ram512 is
  	port (
  		clock:   in  STD_LOGIC;
		input:   in  STD_LOGIC_VECTOR(15 downto 0);
		load:    in  STD_LOGIC;
		address: in  STD_LOGIC_VECTOR(8 downto 0);
		output:  out STD_LOGIC_VECTOR(15 downto 0)
  	);
  end component;

  component Mux8Way16 is
  	port (
  			a:   in  STD_LOGIC_VECTOR(15 downto 0);
			b:   in  STD_LOGIC_VECTOR(15 downto 0);
			c:   in  STD_LOGIC_VECTOR(15 downto 0);
			d:   in  STD_LOGIC_VECTOR(15 downto 0);
			e:   in  STD_LOGIC_VECTOR(15 downto 0);
			f:   in  STD_LOGIC_VECTOR(15 downto 0);
			g:   in  STD_LOGIC_VECTOR(15 downto 0);
			h:   in  STD_LOGIC_VECTOR(15 downto 0);
			sel: in  STD_LOGIC_VECTOR(2 downto 0);
			q:   out STD_LOGIC_VECTOR(15 downto 0)
  	);
  end component;

  component DMux8Way is
  	port (
  			a:   in  STD_LOGIC;
			sel: in  STD_LOGIC_VECTOR(2 downto 0);
			q0:  out STD_LOGIC;
			q1:  out STD_LOGIC;
			q2:  out STD_LOGIC;
			q3:  out STD_LOGIC;
			q4:  out STD_LOGIC;
			q5:  out STD_LOGIC;
			q6:  out STD_LOGIC;
			q7:  out STD_LOGIC
  	);
  end component;

  signal out_ram0, out_ram1, out_ram2, out_ram3, out_ram4, out_ram5, out_ram6, out_ram7, out_ram8: STD_LOGIC_VECTOR(15 downto 0);
  signal d0, d1, d2, d3, d4, d5, d6, d7, d8: STD_LOGIC;

begin
	dmux: DMux8Way port map (load, address(11 downto 9), d0, d1, d2, d3, d4, d5, d6, d7);

	ram1: Ram512 port map (clock, input, d0, address(8 downto 0), out_ram0);
	ram2: Ram512 port map (clock, input, d1, address(8 downto 0), out_ram1);
	ram3: Ram512 port map (clock, input, d2, address(8 downto 0), out_ram2);
	ram4: Ram512 port map (clock, input, d3, address(8 downto 0), out_ram3);
	ram5: Ram512 port map (clock, input, d4, address(8 downto 0), out_ram4);
	ram6: Ram512 port map (clock, input, d5, address(8 downto 0), out_ram5);
	ram7: Ram512 port map (clock, input, d6, address(8 downto 0), out_ram6);
	ram8: Ram512 port map (clock, input, d7,  address(8 downto 0), out_ram7);
	ram9: Ram512 port map (clock, input, d7,  address(8 downto 0), out_ram8);
	
	mux: Mux8way16 port map (out_ram0, out_ram1, out_ram2, out_ram3, out_ram4, out_ram5, out_ram6, out_ram7, address(11 downto 9), output);
	

end architecture;
