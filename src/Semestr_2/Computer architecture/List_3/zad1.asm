.data
prompt: .asciiz "Enter the value of N: "
result: .asciiz "The difference is: "

.text
.globl main

main:
    li $v0, 4
    la $a0, prompt
    syscall
    
    li $v0, 5
    syscall
    move $t0, $v0
    
    addi $t1, $t0, 1
    mul $t2, $t0, $t1
    div $t2, $t2, 2
    mul $t2, $t2, $t2
    
    li $t3, 1
    li $t4, 0
    
    loop:
        mul $t5, $t3, $t3
        add $t4, $t4, $t5
        
        move $t6, $t3
        addi $t3, $t3, 1
        blt $t6, $t0, loop
        
    sub $t6, $t2, $t4
    
    li $v0, 4
    la $a0, result
    syscall
    
    li $v0, 1
    move $a0, $t6
    syscall
  
    li $v0, 10
    syscall
