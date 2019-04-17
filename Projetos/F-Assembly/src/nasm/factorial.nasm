; Arquivo: Factorial.nasm
; Curso: Elementos de Sistemas
; Criado por: Luciano Soares
; Data: 27/03/2017

; Calcula o fatorial do número em (R0) e armazena o valor em (R1).

leaw $0, %A 
movw (%A), %D 
; REDUZ O VALOR INICIAL PARA INICIAR AS MULTIPLICAÇÕES
decw %D
leaw %LOOP0, %A 
jge %D 
nop 
; IF PARA O CASO DE SER FATORIAL DE 0
leaw $1, %A
movw %A, %S
movw %S, (%A)
leaw $LOOP_END, %A
jmp
nop

; LOOP PARA DECRESCER MULTIPLICAÇÃO
LOOP0:
    incw %D
    leaw $0, %A 
    movw %A, %S 
    leaw $2, %A 
    movw %D, (%A)
    decw %D 
; LOOP DA MULTIPLICAÇÃO
LOOP1:
    movw %D, (%A)
    leaw $0, %A 
    addw %S, (%A), %S 
    decw %D
    leaw $LOOP1, %A 
    jg %D 
    nop 

leaw $2, %A 
movw (%A), %D 
decw %D 
movw %D, (%A) 
leaw $0, %A 
movw %S, (%A)
movw %A, %S 
leaw $LOOP1, %A 
jg %D 
nop 

; CARREGANDO O VALOR FINAL
leaw $0, %A 
movw (%A), %S
leaw $1, %A 
movw %S, (%A)

; LOOP USADO PARA 0!
LOOP_END:
