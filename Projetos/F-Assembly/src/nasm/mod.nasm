; Arquivo: Mod.nasm
; Curso: Elementos de Sistemas
; Criado por: Luciano Soares
; Data: 27/03/2017
;
;  Encontra o resto da divisão entre RAM[2] e RAM[1] e o salva em RAM[0].


leaw %1, %A
movw (%A), %S
leaw %2, %A
movw (%A), %D
LOOP:
leaw %LOOP, %A
subw %S, %D, %S
jge %S
nop
leaw %0, %A
addw %S, %D, (%A)

