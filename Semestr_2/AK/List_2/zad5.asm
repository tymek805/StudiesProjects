.data
prompt: .asciiz "Podaj znak (0-9, A-z): "
digit_msg: .asciiz "\nTo jest cyfra!"
letter_msg: .asciiz "\nTo jest litera!"

.text
.globl main

main:
    la $a0, prompt
    li $v0, 4
    syscall
    
    li $v0, 12
    syscall
    move $t0, $v0
    
    li $t1, 48
    li $t2, 57
    blt $t0, $t1, check_letter
    bgt $t0, $t2, check_letter
    
    li $v0, 4
    la $a0, digit_msg
    syscall
    j exit
    
check_letter:
    li $t1, 65
    li $t2, 122
 
    li $v0, 4
    la $a0, letter_msg
    syscall
    j exit
    
exit:
    li $v0, 10
    syscall
