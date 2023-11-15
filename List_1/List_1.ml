let rec flatten(llt) = (
    if llt = [] then [] 
    else List.hd llt @ flatten(List.tl llt)
);;

let rec count(x, lt) = (
    let oozf = (fun (y, z) -> if y = z then 1 else 0) in
    if lt = [] then 0
    else oozf(x, List.hd lt) + count(x, List.tl lt)
);;

let rec replicate(x, number) = (
    if number <= 0 then []
    else [x] @ replicate(x, number-1)
);;

let rec sqrList(li) = (
    if li = [] then []
    else [List.hd li * List.hd li] @ sqrList(List.tl li)
);;

let rec palindrome(lt) = (
    lt = List.rev lt
);;

let rec listLength(lt) = (
    if lt = [] then 0
    else 1 + listLength(List.tl lt)
);;
