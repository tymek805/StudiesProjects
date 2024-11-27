let przesun f (a, b) =
  fun x -> f (x +. a) +. b
;;

let wynik1 = przesun (fun x -> x) (4.0, 8.0) 9.0;;
print_float wynik1;;
print_newline ();;

let wynik2 = przesun (fun x -> x) (3.0, 1.0);;
print_float (wynik2 1.0);;
print_newline ();;