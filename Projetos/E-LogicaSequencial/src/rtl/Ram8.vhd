-- Elementos de Sistemas
-- by Luciano Soares
-- Ram8.vhd

Library ieee;
use ieee.std_logic_1164.all;

entity Ram8 is
	port(
		clock:   in  STD_LOGIC;
		input:   in  STD_LOGIC_VECTOR(15 downto 0);
		load:    in  STD_LOGIC;
		address: in  STD_LOGIC_VECTOR( 2 downto 0);
		output:  out STD_LOGIC_VECTOR(15 downto 0)
	);
end entity;

architecture arch of ram8 is
  -- Aqui declaramos sinais (fios auxiliares)
  -- e componentes (outros módulos) que serao
  -- utilizados nesse modulo.

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

	component Register16 is
	port(
		clock:   in STD_LOGIC;
		input:   in STD_LOGIC_VECTOR(15 downto 0);
		load:    in STD_LOGIC;
		output: out STD_LOGIC_VECTOR(15 downto 0)
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
	
	signal o0, o1, o2, o3, o4, o5, o6, o7: STD_LOGIC;
	signal out_regis0, out_regis1, out_regis2, out_regis3, out_regis4, out_regis5, out_regis6, out_regis7: STD_LOGIC_VECTOR(15 downto 0);
	
begin

  dmux: DMux8Way port map(load, address, o0, o1, o2, o3, o4, o5, o6, o7);
  regsiter0: Register16 port map(clock, input(15 downto 0), o0, out_regis0(15 downto 0));
  regsiter1: Register16 port map(clock, input(15 downto 0), o1, out_regis1(15 downto 0));
  regsiter2: Register16 port map(clock, input(15 downto 0), o2, out_regis2(15 downto 0));
  regsiter3: Register16 port map(clock, input(15 downto 0), o3, out_regis3(15 downto 0));
  regsiter4: Register16 port map(clock, input(15 downto 0), o4, out_regis4(15 downto 0));
  regsiter5: Register16 port map(clock, input(15 downto 0), o5, out_regis5(15 downto 0));
  regsiter6: Register16 port map(clock, input(15 downto 0), o6, out_regis6(15 downto 0));
  regsiter7: Register16 port map(clock, input(15 downto 0), o7, out_regis7(15 downto 0));
  mux: Mux8Way16 port map(out_regis0, out_regis1, out_regis2, out_regis3, out_regis4, out_regis5, out_regis6, out_regis7,address,output);
	
end architecture;
