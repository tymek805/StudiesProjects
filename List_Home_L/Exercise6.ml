type 'a llist = Cons of 'a * 'a llist Lazy.t | Empty

let rec lpowiel ll =
  match ll with
  | Empty -> Empty
  | Cons (x, rest) ->
      let rec duplicate n value =
        if n = 0 then Empty
        else Cons (value, lazy (duplicate (n - 1) value))
      in
      append (duplicate x x) (lpowiel (Lazy.force rest))

and append ll_1 ll_2 =
  match ll_1 with
  | Empty -> ll_2
  | Cons (x, rest) -> Cons (x, lazy (append (Lazy.force rest) ll_2))
;;