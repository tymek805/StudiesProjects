.data
prompt: .asciiz "Enter number: "
pling: .asciiz "Pling "
plang: .asciiz "Plang "
plong: .asciiz "Plong "

.text
.globl main

main:
    li $v0, 4
    la $a0, prompt
    syscall
    
    li $v0, 5
    syscall
    move $t0, $v0

    # Divisible by 3
    li $t1, 3
    div $t0, $t1
    mfhi $t2    
    beqz $t2, divisible_by_3
    
    # Divisible by 5
    li $t1, 5
    div $t0, $t1
    mfhi $t2
    beqz $t2, divisible_by_5
    
    # Divisible by 7
    li $t1, 7
    div $t0, $t1
    mfhi $t2
    beqz $t2, divisible_by_7
    
    # Not divisible by 3, 5 or 7
    li $v0, 1
    move $a0, $t0
    syscall
    j exit
    
    
divisible_by_3:
    li $v0, 4
    la $a0, pling
    syscall
    
    li $t1, 5
    div $t0, $t1
    mfhi $t2
    
    beqz $t2, divisible_by_5
    
    li $t1, 7
    div $t0, $t1
    mfhi $t2
    
    beqz $t2, divisible_by_7
    
    j exit

divisible_by_5:
    li $v0, 4
    la $a0, plang
    syscall
    
    li $t1, 7
    div $t0, $t1
    mfhi $t2
    
    beqz $t2, divisible_by_7
    
    j exit

divisible_by_7:
    li $v0, 4
    la $a0, plong
    syscall
    
    j exit
       
exit:
    li $v0, 10
    syscall