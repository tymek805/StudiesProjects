.data
prompt: .asciiz "GCTAAGCTAA"
rnatext: .space 100
result: .asciiz "\nRNA strand is: "

.text
.globl main

main:
    la $a0, prompt
    
    move $t0, $a0
    li $t1, 0
    
    la $a0, rnatext
    j loop
  
loop:
    lb $t2 ($t0)
    beqz $t2, exit
     			
    beq $t2, 'G', G_C
    beq $t2, 'C', C_G
    beq $t2, 'T', T_A
    beq $t2, 'A', A_U

G_C:
    li $t3, 'C'
    j store_char
C_G:
    li $t3, 'G'
    j store_char
T_A:
    li $t3, 'A'
    j store_char
A_U:
    li $t3, 'U'
    j store_char

store_char:
    sb $t3, rnatext($t1)
    addi $t0, $t0, 1
    addi $t1, $t1, 1
    j loop

exit:
    la $a0, result
    li $v0, 4
    syscall
    
    la $a0, rnatext
    li $v0, 4
    syscall
    
    li $v0, 10
    syscall
