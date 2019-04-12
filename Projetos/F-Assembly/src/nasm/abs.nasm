; ####################################################################
; Arquivo: Abs.nasm
; Curso: Elementos de Sistemas
; Criado por: Luciano Soares
; Data: 27/03/2017
;
; Copia o valor de RAM[1] para RAM[0] deixando o valor sempre positivo.
; ####################################################################
leaw $R1, %A
movw (%A), %D

fazer if oq tem em R1 é menor q zero, se for, nega ele pra transformar em postivio

leaw $R0, %A
movw %D, (%A) 