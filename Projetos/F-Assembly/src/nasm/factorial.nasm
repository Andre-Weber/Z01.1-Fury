; Arquivo: Factorial.nasm
; Curso: Elementos de Sistemas
; Criado por: Luciano Soares
; Data: 27/03/2017

; Calcula o fatorial do número em (R0) e armazena o valor em (R1).

leaw $0, %A 
movw (%A), %S 
leaw $1, %A 
movw %S, (%A)
decw %S 
decw %S 
leaw $2, %A 
movw %S, (%A) 
movw %S, %D 
leaw $LOOP1, %A
jg %D 
nop 
LOOP1:
    leaw $1, %A 
    movw (%A), %S 
    leaw %LOOP2, %A 
    jg %D 
    nop
LOOP2:
    leaw $1, %A 
    addw %S, (%A), %S
    leaw $LOOP2, %A 
    jg %D 
    nop
    leaw $1, %A 
    movw %S, (%A) 
    leaw $2, %A 
    movw (%A), %D 
    decw %D 
    movw %D, (%A)
    leaw $LOOP1, %A 
    jg %D 
    nop 
leaw $LOOP3, %A
jg %S 
nop 
incw %S
LOOP3:
    leaw $1, %A 
    movw %S, (%A)

