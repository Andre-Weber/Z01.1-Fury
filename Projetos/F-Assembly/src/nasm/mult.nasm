; Arquivo: Abs.nasm
; Curso: Elementos de Sistemas
; Criado por: Luciano Soares
; Data: 27/03/2017

; Multiplica o valor de RAM[1] com RAM[0] salvando em RAM[3]

leaw $0, %A
movw (%A), %D
subw %D, $1, %D

leaw $1, %A
movw (%A), %S
leaw $2, %A
movw %S, (%A)

LOOP:
	leaw $2, %A
	addw %S, (%A), %S
	leaw $3, %A
	movw %S, (%A)
	subw %D, $1, %D

leaw %LOOP, %A
jg %D
nop