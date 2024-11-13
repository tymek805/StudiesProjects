.data
prompt: .asciiz "Podaj liczbe (0 - 99): "
is_less: .asciiz "a < b"
is_greater: .asciiz "a > b"
is_equal: .asciiz "a = b"

.text
.globl main

main:
    li $v0, 4
    la $a0, prompt
    syscall
    
    li $v0, 5
    syscall
    move $t0, $v0
    
    li $v0, 4
    la $a0, prompt
    syscall
    
    li $v0, 5
    syscall
    move $t1, $v0
    
    bgt $t0, $t1, greater
    blt $t0, $t1, less
    j equal    

less:
    li $v0, 4
    la $a0, is_less
    syscall
    j exit
    
greater:
    li $v0, 4
    la $a0, is_greater
    syscall
    j exit
    
equal:
    li $v0, 4
    la $a0, is_equal
    syscall
    
exit:
    li $v0, 10
    syscall
