let sumProdd xs = 
    List.fold_left ((fun accM n -> (n + fst(accM), n * snd(accM)) )) (0,1) xs;;
    
let insertSort xs f = 
    let rec insert xs x =
      match xs  with 
      [] -> [x]
      | h :: t when f x h -> x :: h :: t
      | h :: t -> h :: insert t x in
    List.fold_left(insert)([]) xs;;
  
let mergeSort xs f =
    let splitted = List.map(fun x -> [x]) xs in
    let rec putInList(a, b, acc) =
      match (a, b) with
        ([], x) -> List.rev acc @ x
        | (x, []) -> List.rev acc @ x
        | (x, y) -> if not (f (List.hd x) (List.hd y)) then  putInList(x, List.tl y, List.hd y::acc)  else  putInList(List.tl x, y, List.hd x::acc)
        | _ -> failwith("putInList match error") in
    let rec merge(llx) =
      match llx with
        a::b::[] -> putInList(a, b, [])
        | a::b::t -> merge([putInList(a, b, []); merge(t)])
        | a::[] -> a
        | _ -> List.map (fun x -> List.map (fun y -> print_int y;) x ) llx; failwith("Problem occured") in
        merge(splitted);;
