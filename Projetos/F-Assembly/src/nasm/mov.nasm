; Elementos de Sistemas : 2018a
; Rafael Corsi
; Hardware : Z0.1
;
; Data :
;    - Dez 2018
; Descrição :
;    - Movimentação de dados da memoria RAM
;
; tmp    = RAM[0]
; RAM[0] = RAM[1]
; RAM[1] = tmp
; RAM[3] = 1


leaw $R1, %A
movw (%A), %D
leaw $R0, %A
movw (%A), %S
leaw $R1, %A
movw %S, (%A)
leaw $R0, %A
movw %D, (%A)
leaw $R3, %A
movw $1, (%A)

