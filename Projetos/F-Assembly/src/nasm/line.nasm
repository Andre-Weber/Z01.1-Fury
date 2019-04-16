; ####################################################################
; Arquivo: Abs.nasm
; Curso: Elementos de Sistemas
; Criado por: Luciano Soares
; Data: 27/03/2017
;
; ####################################################################

leaw $0, %A 
movw %A, %S 
notw %S 
leaw $18794, %A 
movw %S, (%A)