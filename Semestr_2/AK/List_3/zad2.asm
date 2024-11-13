.data
plaintext: .asciiz "TEST"
ciphertext: .space 100

.text
.globl main

main:
    la $a0, plaintext
   
    move $t0, $a0
    li $t1, 0
    
    count_length:
        lb $t2, ($t0)
        beqz $t2, encrypt
        addi $t0, $t0, 1
        addi $t1, $t1, 1
        j count_length
        
    encrypt:
        move $t0, $a0
        
        li $t2, 0
        
        encrypt_loop:
            lb $t3, ($t0)
            beqz $t3, exit
            
            li $t4, 65   # 'A' as the starting value for uppercase letters
            li $t5, 90   # 'Z' as the ending value for uppercase letters
            blt $t3, $t4, lowercase_check  # Check if the character is lowercase
            bgt $t3, $t5, symbol_check     # Check if the character is a symbol
            
            # Encryption for uppercase letters
            sub $t3, $t3, $t4   # Get the relative position of the letter
            negu $t3, $t3      # Perform Atbash encryption
            addu $t3, $t3, $t5   # Get the encrypted letter
            j store_char
            
        lowercase_check:
            li $t4, 97
            li $t5, 122
            blt $t3, $t4, symbol_check
            bgt $t3, $t5, symbol_check
            
            sub $t3, $t3, $t4
            negu $t3, $t3
            addu $t3, $t3, $t5
            j store_char
            
        symbol_check:
            j store_char
            
        store_char:
            sb $t3, ciphertext($t2)
            addi $t0, $t0, 1
            addi $t2, $t2, 1
            j encrypt_loop
            
    exit:
        la $a0, ciphertext
        li $v0, 4
        syscall
       
        li $v0, 10
        syscall
