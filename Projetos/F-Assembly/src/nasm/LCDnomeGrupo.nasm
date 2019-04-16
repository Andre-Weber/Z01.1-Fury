; Arquivo: LCDnomeGrupo.nasm
; Curso: Elementos de Sistemas
; Criado por: Rafael Corsi
; Data: 28/3/2018
;
; Escreva no LCD o nome do grupo

;; F horizontal cima
leaw $65535, %A
movw %A, %D
leaw $16384, %A
movw %D, (%A)

leaw $65535, %A
movw %A, %D
leaw $16404, %A
movw %D, (%A)

leaw $65535, %A
movw %A, %D
leaw $16424, %A
movw %D, (%A)

leaw $65535, %A
movw %A, %D
leaw $16444, %A
movw %D, (%A)
;;
;; um bloco abaixo
leaw $7, %A
movw %A, %D
leaw $16464, %A
movw %D, (%A)

leaw $7, %A
movw %A, %D
leaw $16484, %A
movw %D, (%A)

leaw $7, %A
movw %A, %D
leaw $16504, %A
movw %D, (%A)

leaw $7, %A
movw %A, %D
leaw $16524, %A
movw %D, (%A)
;;
;; segunda horizontal
leaw $255, %A
movw %A, %D
leaw $16544, %A
movw %D, (%A)

leaw $255, %A
movw %A, %D
leaw $16564, %A
movw %D, (%A)

leaw $255, %A
movw %A, %D
leaw $16584, %A
movw %D, (%A)

leaw $255, %A
movw %A, %D
leaw $16604, %A
movw %D, (%A)
;;
;; bloco abaixo

leaw $7, %A
movw %A, %D
leaw $16624, %A
movw %D, (%A)

leaw $7, %A
movw %A, %D
leaw $16644, %A
movw %D, (%A)

leaw $7, %A
movw %A, %D
leaw $16664, %A
movw %D, (%A)

leaw $7, %A
movw %A, %D
leaw $16684, %A
movw %D, (%A)
;; bloco abaixo
leaw $7, %A
movw %A, %D
leaw $16704, %A
movw %D, (%A)

leaw $7, %A
movw %A, %D
leaw $16724, %A
movw %D, (%A)

leaw $7, %A
movw %A, %D
leaw $16744, %A
movw %D, (%A)

leaw $7, %A
movw %A, %D
leaw $16764, %A
movw %D, (%A)
;; F terminado


;;U 
leaw $57372, %A
movw %A, %D
leaw $16385, %A
movw %D, (%A)

leaw $57372, %A
movw %A, %D
leaw $16405, %A
movw %D, (%A)

leaw $57372, %A
movw %A, %D
leaw $16425, %A
movw %D, (%A)

leaw $57372, %A
movw %A, %D
leaw $16445, %A
movw %D, (%A)
;;
;;bloco abaixo
leaw $57372, %A
movw %A, %D
leaw $16465, %A
movw %D, (%A)

leaw $57372, %A
movw %A, %D
leaw $16485, %A
movw %D, (%A)

leaw $57372, %A
movw %A, %D
leaw $16505, %A
movw %D, (%A)

leaw $57372, %A
movw %A, %D
leaw $16525, %A
movw %D, (%A)
;;
;;bloco abaixo
leaw $57372, %A
movw %A, %D
leaw $16545, %A
movw %D, (%A)

leaw $57372, %A
movw %A, %D
leaw $16565, %A
movw %D, (%A)

leaw $57372, %A
movw %A, %D
leaw $16585, %A
movw %D, (%A)

leaw $57372, %A
movw %A, %D
leaw $16605, %A
movw %D, (%A)
;;
;;bloco abaixo
leaw $57372, %A
movw %A, %D
leaw $16625, %A
movw %D, (%A)

leaw $57372, %A
movw %A, %D
leaw $16645, %A
movw %D, (%A)

leaw $57372, %A
movw %A, %D
leaw $16665, %A
movw %D, (%A)

leaw $57372, %A
movw %A, %D
leaw $16685, %A
movw %D, (%A)
;;
;;diagonal abaixo

leaw $8160, %A
movw %A, %D
leaw $16705, %A
movw %D, (%A)

leaw $8160, %A
movw %A, %D
leaw $16725, %A
movw %D, (%A)

leaw $8160, %A
movw %A, %D
leaw $16745, %A
movw %D, (%A)

leaw $8160, %A
movw %A, %D
leaw $16765, %A
movw %D, (%A)
;;U terminado
;;R 
leaw $4092, %A
movw %A, %D
leaw $16386, %A
movw %D, (%A)

leaw $4092, %A
movw %A, %D
leaw $16406, %A
movw %D, (%A)

leaw $4092, %A
movw %A, %D
leaw $16426, %A
movw %D, (%A)

leaw $4092, %A
movw %A, %D
leaw $16446, %A
movw %D, (%A)
;;
;;bloco abaixo
leaw $28700, %A
movw %A, %D
leaw $16466, %A
movw %D, (%A)

leaw $28700, %A
movw %A, %D
leaw $16486, %A
movw %D, (%A)

leaw $28700, %A
movw %A, %D
leaw $16506, %A
movw %D, (%A)

leaw $28700, %A
movw %A, %D
leaw $16526, %A
movw %D, (%A)
;;
;;Diagonal abaixo

leaw $4092, %A
movw %A, %D
leaw $16546, %A
movw %D, (%A)

leaw $4092, %A
movw %A, %D
leaw $16566, %A
movw %D, (%A)

leaw $4092, %A
movw %A, %D
leaw $16586, %A
movw %D, (%A)

leaw $4092, %A
movw %A, %D
leaw $16606, %A
movw %D, (%A)
;;
;;Bloco abaixo


leaw $28700, %A
movw %A, %D
leaw $16626, %A
movw %D, (%A)

leaw $28700, %A
movw %A, %D
leaw $16646, %A
movw %D, (%A)

leaw $28700, %A
movw %A, %D
leaw $16666, %A
movw %D, (%A)

leaw $28700, %A
movw %A, %D
leaw $16686, %A
movw %D, (%A)
;;
;;bloco abaixo

leaw $28700, %A
movw %A, %D
leaw $16706, %A
movw %D, (%A)

leaw $28700, %A
movw %A, %D
leaw $16726, %A
movw %D, (%A)

leaw $28700, %A
movw %A, %D
leaw $16746, %A
movw %D, (%A)

leaw $28700, %A
movw %A, %D
leaw $16766, %A
movw %D, (%A)
;;R terminado
;;
;;Y
leaw $28700, %A
movw %A, %D
leaw $16387, %A
movw %D, (%A)

leaw $28700, %A
movw %A, %D
leaw $16407, %A
movw %D, (%A)

leaw $28700, %A
movw %A, %D
leaw $16427, %A
movw %D, (%A)

leaw $28700, %A
movw %A, %D
leaw $16447, %A
movw %D, (%A)
;;
;; bloco diagonal
leaw $3808, %A
movw %A, %D
leaw $16467, %A
movw %D, (%A)

leaw $3808, %A
movw %A, %D
leaw $16487, %A
movw %D, (%A)

leaw $3808, %A
movw %A, %D
leaw $16507, %A
movw %D, (%A)

leaw $3808, %A
movw %A, %D
leaw $16527, %A
movw %D, (%A)
;;
;;bloco diagonal
leaw $896, %A
movw %A, %D
leaw $16547, %A
movw %D, (%A)

leaw $896, %A
movw %A, %D
leaw $16567, %A
movw %D, (%A)

leaw $896, %A
movw %A, %D
leaw $16587, %A
movw %D, (%A)

leaw $896, %A
movw %A, %D
leaw $16607, %A
movw %D, (%A)
;;
;;bloco abaixo
leaw $896, %A
movw %A, %D
leaw $16627, %A
movw %D, (%A)

leaw $896, %A
movw %A, %D
leaw $16647, %A
movw %D, (%A)

leaw $896, %A
movw %A, %D
leaw $16667, %A
movw %D, (%A)

leaw $896, %A
movw %A, %D
leaw $16687, %A
movw %D, (%A)
;;
;;bloco abaixo
leaw $896, %A
movw %A, %D
leaw $16707, %A
movw %D, (%A)

leaw $896, %A
movw %A, %D
leaw $16727, %A
movw %D, (%A)

leaw $896, %A
movw %A, %D
leaw $16747, %A
movw %D, (%A)

leaw $896, %A
movw %A, %D
leaw $16767, %A
movw %D, (%A)

