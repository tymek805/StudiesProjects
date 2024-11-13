.data
prompt: .asciiz "Podaj liczbe (0-99): "
result: .asciiz "Reszta z dzielenia przez 4: "

.text
.globl main

main:
    li $v0, 4
    la $a0, prompt
    syscall
    
    li $v0, 5
    syscall
    
    move $t0, $v0
    
    andi $t1, $t0, 0x3
    
    li $v0, 4
    la $a0, result
    syscall
    
    move $a0, $t1
    li $v0, 1
    syscall