.data
prompt: .asciiz "Podaj liczbe (0 - 255): "

.text
.globl main

main:
    # Wyświetlanie komunikatu
    li $v0, 4
    la $a0, prompt
    syscall
    
    # Wczytywanie liczby
    li $v0, 5
    syscall
    move $t0, $v0
    
    # Wyświetlanie komunikatu z wynikiem
    li $v0, 4
    la $a0, binary
    syscall
    
    # Konwersja na postać binarną i wyświetlanie wyniku
    li $t1, 8       # Liczba bitów
    li $t2, 0x80    # Maska bitowa: 1000 0000
    
    loop:
        and $t3, $t0, $t2   # Sprawdzenie stanu bitu
        beqz $t3, zero     # Jeśli bit jest wyzerowany, skocz do etykiety zero
        li $v0, 1
        li $a0, 49         # ASCII '1'
        syscall
        j next
        
    zero:
        li $v0, 1
        li $a0, 48         # ASCII '0'
        syscall
        
    next:
        sll $t2, $t2, 1    # Przesunięcie maski bitowej w lewo
        sub $t1, $t1, 1    # Zmniejszenie licznika bitów
        bnez $t1, loop     # Jeśli nie wszystkie bity zostały przetworzone, skocz do etykiety loop
    
    j exit
    
exit:
    li $v0, 1
    syscall