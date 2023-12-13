let calkuj f a b n =
  let h = (b -. a) /. float_of_int n in
  let rec loop i sum =
    if i = n then sum
    else
      let xi = a +. float_of_int i *. h in
      let xi1 = xi +. h in
      let yi = f xi in
      let yi1 = f xi1 in
      let area = (yi +. yi1) *. h /. 2.0 in
      loop (i + 1) (sum +. area)
  in
  loop 0 0.0
;; 


let result = calkuj (fun x -> x *. x) 0.0 1.0 1;;

let result1 = calkuj (fun x -> x ** 2.0 +. 3.0 *. x +. 5.0) 0.0 2.0 1;;