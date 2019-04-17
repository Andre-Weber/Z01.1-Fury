; Arquivo: LCDQuadrado.nasm
; Curso: Elementos de Sistemas
; Criado por: Rafael Corsi
; Data: 28/3/2018
;
; Desenhe um quadro no LCD
;  - Valide no hardawre
;  - Bata uma foto!

leaw $65535, %A
movw %A, %D
leaw $16447, %A
movw %D, (%A)

leaw $65535, %A
movw %A, %D
leaw $16467, %A
movw %D, (%A)

leaw $65535, %A
movw %A, %D
leaw $16487, %A
movw %D, (%A)

leaw $65535, %A
movw %A, %D
leaw $16507, %A
movw %D, (%A)

leaw $65535, %A
movw %A, %D
leaw $16527, %A
movw %D, (%A)

leaw $65535, %A
movw %A, %D
leaw $16547, %A
movw %D, (%A)

leaw $65535, %A
movw %A, %D
leaw $16567, %A
movw %D, (%A)

leaw $65535, %A
movw %A, %D
leaw $16587, %A
movw %D, (%A)

leaw $65535, %A
movw %A, %D
leaw $16607, %A
movw %D, (%A)

leaw $65535, %A
movw %A, %D
leaw $16627, %A
movw %D, (%A)

leaw $65535, %A
movw %A, %D
leaw $16647, %A
movw %D, (%A)

leaw $65535, %A
movw %A, %D
leaw $16667, %A
movw %D, (%A)

leaw $65535, %A
movw %A, %D
leaw $16687, %A
movw %D, (%A)

leaw $65535, %A
movw %A, %D
leaw $16707, %A
movw %D, (%A)

leaw $65535, %A
movw %A, %D
leaw $16727, %A
movw %D, (%A)