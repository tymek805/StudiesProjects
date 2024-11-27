type 'a lazy_list = Empty | Cons of 'a * 'a lazy_list Lazy.t

let rec lazy_list_length ll =
  match ll with
  | Empty -> 0
  | Cons (_, tl) -> 1 + lazy_list_length (Lazy.force tl)

let rec filter_lazy_lists min_length ll =
  match ll with
  | Empty -> Empty
  | Cons (inner_ll, tl) ->
      if lazy_list_length inner_ll >= min_length then
        Cons (inner_ll, lazy (filter_lazy_lists min_length (Lazy.force tl)))
      else
        filter_lazy_lists min_length (Lazy.force tl)