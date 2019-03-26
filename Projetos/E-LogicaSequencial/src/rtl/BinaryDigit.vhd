-- Elementos de Sistemas
-- by Luciano Soares
-- BinaryDigit.vhd

Library ieee;
use ieee.std_logic_1164.all;

entity BinaryDigit is
	port(
		clock:   in STD_LOGIC;
		input:   in STD_LOGIC;
		load:    in STD_LOGIC;
		output: out STD_LOGIC
	);
end entity;

architecture rtl of BinaryDigit is
  -- Aqui declaramos sinais (fios auxiliares)
  -- e componentes (outros módulos) que serao
  -- utilizados nesse modulo.
  component Mux2Way is
	port(
		a  : in  STD_LOGIC;
		b  : in  STD_LOGIC;
		sel: in  STD_LOGIC;
		q  : out STD_LOGIC 
	);
  end component;

  component FlipFlopD is
    port(
		clock : in STD_LOGIC;
		d     : in STD_LOGIC;
		clear : in STD_LOGIC;
		preset: in STD_LOGIC;
		q     : out STD_LOGIC
	);
  end component;	 

  signal out_mux, out_flip: STD_LOGIC;

begin

  process_1: Mux2Way port map(out_flip, input, load, out_mux);
  process_2: FlipFlopD port map(clock, out_mux, '0', '0', out_flip);

  output <= out_flip;

end architecture;
