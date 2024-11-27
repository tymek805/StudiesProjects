let odwroc lt = (
  let rec reversed acc = function
    | [] -> acc
    | hd :: tl -> reversed (hd :: acc) tl
  in
  reversed[] lt
);;

let test_one = odwroc [3; 5; 1];;
let test_two = odwroc [];;
let test_three = odwroc [22; 1; 0; 34];; 