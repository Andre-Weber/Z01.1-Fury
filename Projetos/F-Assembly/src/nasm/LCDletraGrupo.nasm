; Arquivo: LCDletraGrupo.nasm
; Curso: Elementos de Sistemas
; Criado por: Rafael Corsi
; Data: 28/3/2018
;
; Escreva no LCD a letra do grupo de vocês
;  - Valide no hardawre
;  - Bata uma foto!





;Linha horizontal
leaw $3, %A
movw %A, %D
leaw $16385, %A
movw %D, (%A)

leaw $3, %A
movw %A, %D
leaw $16405, %A
movw %D, (%A)

leaw $3, %A
movw %A, %D
leaw $16425, %A
movw %D, (%A)

leaw $3, %A
movw %A, %D
leaw $16445, %A
movw %D, (%A)

leaw $3, %A
movw %A, %D
leaw $16465, %A
movw %D, (%A)

leaw $3, %A
movw %A, %D
leaw $16485, %A
movw %D, (%A)

leaw $3, %A
movw %A, %D
leaw $16505, %A
movw %D, (%A)

leaw $3, %A
movw %A, %D
leaw $16525, %A
movw %D, (%A)

leaw $3, %A
movw %A, %D
leaw $16545, %A
movw %D, (%A)

leaw $3, %A
movw %A, %D
leaw $16565, %A
movw %D, (%A)

leaw $3, %A
movw %A, %D
leaw $16585, %A
movw %D, (%A)

leaw $3, %A
movw %A, %D
leaw $16605, %A
movw %D, (%A)

leaw $3, %A
movw %A, %D
leaw $16625, %A
movw %D, (%A)

leaw $3, %A
movw %A, %D
leaw $16645, %A
movw %D, (%A)

leaw $3, %A
movw %A, %D
leaw $16665, %A
movw %D, (%A)

leaw $3, %A
movw %A, %D
leaw $16685, %A
movw %D, (%A)

leaw $3, %A
movw %A, %D
leaw $16705, %A
movw %D, (%A)

leaw $3, %A
movw %A, %D
leaw $16725, %A
movw %D, (%A)

leaw $3, %A
movw %A, %D
leaw $16745, %A
movw %D, (%A)

leaw $3, %A
movw %A, %D
leaw $16765, %A
movw %D, (%A)

;Linha F(superior)
leaw $65535, %A
movw %A, %D
leaw $16385, %A
movw %D, (%A)

leaw $65535, %A
movw %A, %D
leaw $16405, %A
movw %D, (%A)

leaw $65535, %A
movw %A, %D
leaw $16425, %A
movw %D, (%A)


;Linha F(inferior)
leaw $65535, %A
movw %A, %D
leaw $16545, %A
movw %D, (%A)

leaw $65535, %A
movw %A, %D
leaw $16565, %A
movw %D, (%A)

leaw $65535, %A
movw %A, %D
leaw $16585, %A
movw %D, (%A)