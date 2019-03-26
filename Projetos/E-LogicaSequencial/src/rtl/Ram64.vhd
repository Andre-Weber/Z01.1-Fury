-- Elementos de Sistemas
-- by Luciano Soares
-- Ram64.vhd

Library ieee;
use ieee.std_logic_1164.all;

entity Ram64 is
	port(
		clock:   in  STD_LOGIC;
		input:   in  STD_LOGIC_VECTOR(15 downto 0);
		load:    in  STD_LOGIC;
		address: in  STD_LOGIC_VECTOR( 5 downto 0);
		output:  out STD_LOGIC_VECTOR(15 downto 0)
	);
end entity;

architecture arch of Ram64 is

component Ram8 is
    port(
  		clock:   in  STD_LOGIC;
  		input:   in  STD_LOGIC_VECTOR(15 downto 0);
  		load:    in  STD_LOGIC;
  		address: in  STD_LOGIC_VECTOR( 2 downto 0);
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
  			q:   out STD_LOGIC_VECTOR(15 downto 0));
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
			q7:  out STD_LOGIC);
  end component;


signal v1,v2,v3,v4,v5,v6,v7,v8: STD_LOGIC;
signal vout1, vout2, vout3, vout4, vout5, vout6, vout7, vout8 : STD_LOGIC_VECTOR(15 downto 0);


begin

In: DMux8Way port map(load,address,v1,v2,v3,v4,v5,v6,v7,v8);

R1: Ram8 port map(clock, input, load, address, vout1);
R2: Ram8 port map(clock, input, load, address, vout2);
R3: Ram8 port map(clock, input, load, address, vout3);
R4: Ram8 port map(clock, input, load, address, vout4);
R5: Ram8 port map(clock, input, load, address, vout5);
R6: Ram8 port map(clock, input, load, address, vout6);
R7: Ram8 port map(clock, input, load, address, vout7);
R8: Ram8 port map(clock, input, load, address, vout8);

Out: Mux8Way16 port map(vout1, vout2, vout3, vout4, vout5, vout6, vout7, vout8, address, output);


end architecture;
