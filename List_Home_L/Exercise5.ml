type 'a llist = LNil | LCons of 'a * (unit -> 'a llist)

let ltake n lxs = 
  let rec ltake_acc n acc lxs =
    match (n, lxs) with
    | (0, _) -> List.rev acc
    | (_, LNil) -> List.rev acc
    | (n, LCons(x, xf)) -> ltake_acc (n-1) (x::acc) (xf ())
  in ltake_acc n [] lxs