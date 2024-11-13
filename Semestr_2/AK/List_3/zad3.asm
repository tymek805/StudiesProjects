.data
word: .asciiz "CABBAGE"
score: .word 100

.text
.globl main

main:
    la $a0, word
    li $t0, 0
    
    loop:
        lb $t1, ($a0)
        li $t2, 65
        sub $t1, $t1, $t2
        
        # 1 points
        beq $t1, 0, add_1	# A
        beq $t1, 4, add_1	# E
        beq $t1, 8, add_1	# I
        beq $t1, 11, add_1	# L
        beq $t1, 13, add_1	# N
        beq $t1, 14, add_1	# O
        beq $t1, 17, add_1	# R
        beq $t1, 18, add_1	# S
        beq $t1, 19, add_1	# T
        beq $t1, 20, add_1	# U
        # 2 points
        beq $t1, 3, add_2	# D
        beq $t1, 6, add_2	# G
        # 3 points
        beq $t1, 1, add_3	# B
        beq $t1, 2, add_3	# C
        beq $t1, 12, add_3	# M
        beq $t1, 15, add_3	# P
        # 4 points
        beq $t1, 5, add_4	# F
        beq $t1, 7, add_4	# H
        beq $t1, 21, add_4	# V
        beq $t1, 22, add_4	# W
        beq $t1, 24, add_4	# Y
        # 5 points
        beq $t1, 10, add_5	# K
        # 8 points
        beq $t1, 9, add_8	# J
        beq $t1, 23, add_8	# X
        # 10 points
        beq $t1, 16, add_10	# Q
        beq $t1, 25, add_10	# Z
        j exit
        
    add_1:
        addi $t0, $t0, 1
        j next_char
        
    add_2:
        addi $t0, $t0, 2
        j next_char
        
    add_3:
        addi $t0, $t0, 3
        j next_char
        
    add_4:
        addi $t0, $t0, 4
        j next_char
        
    add_5:
        addi $t0, $t0, 5
        j next_char
        
    add_8:
        addi $t0, $t0, 8
        j next_char
        
    add_10:
        addi $t0, $t0, 10
        j next_char
        
    next_char:
        addi $a0, $a0, 1
        j loop
 
exit:   
    sw $t0, score
   
    li $v0, 1
    lw $a0, score
    syscall
   
    li $v0, 10
    syscall
