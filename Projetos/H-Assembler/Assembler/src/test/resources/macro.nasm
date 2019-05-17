%macro caioga 2
leaw par0, %A
movw %A, par1
vitor $8, %D
%endmacro

%macro vitor 2
leaw par0, %A
movw par1, (%A)
%endmacro

caioga $5, %D