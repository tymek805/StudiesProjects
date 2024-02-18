.data

a: .word 3
b: .word 4
c: .word 8
d: .word 5

.text
main:
lw $t0, a
lw $t1, b
lw $t2, c
lw $t3, d

add $t0, $t0, $t1
sub $t2, $t2, $t3
sub $a0, $t0, $t2

li $v0, 1
syscall