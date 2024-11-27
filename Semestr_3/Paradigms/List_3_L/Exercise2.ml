let zamien lt idx = (
  let rec divide acc n = function
    | [] -> (List.rev acc, [])
    | h :: t as l ->
        if n = 0 then (List.rev acc, l)
        else divide (h :: acc) (n - 1) t
  in
  let lt1, lt2 = divide [] idx lt in
  lt2 @ lt1
);;

let test1 = zamien [1;7;5;6] 2;; 
let test2 = zamien [1;7;5;6] 3;; 
let test3 = zamien [3; 4; 2; 1] 0;; 
let test4 = zamien [3; 4; 1; 2] 2;; 