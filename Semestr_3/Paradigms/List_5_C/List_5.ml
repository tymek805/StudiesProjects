(* Zad 1 *)
type 'a llist = LNil | LCons of 'a * (unit -> 'a llist);;

let rec lrepeat (n, llx)=
    let rec lrep(n, x, lazyRest) =
        if n > 1 then LCons(x, function () -> (lrep(n-1, x, lazyRest)))
        else LCons(x, lazyRest) in
        
    match llx with
    | LNil -> LNil
    | LCons(x, xf) -> lrep(n, x, function () -> (lrepeat(n, xf())));;
    
let rec lfrom k = LCons (k, function () -> lfrom (k+1));;
let rec ltake = function
| (0, _) -> []
| (_, LNil) -> []
| (n, LCons(x,xf)) -> x::ltake(n-1, xf());;

ltake(15,(lrepeat(3,(lfrom 3))));;


(*Zad 2*)
let lfib = 
    let rec lfibIn(p, n) =
        LCons(p+n, function() -> (lfibIn(n, p+n))) in
    LCons(1, function() -> (LCons(1, function() -> (lfibIn(1, 1)))));;
    
ltake(15, lfib);;


let rec lfib = 
    let rec lfibIn(p, n) =
        LCons(p+n, function () -> (lfibIn(n, p+n))) in
    LCons(0, function () ->(LCons(1, function () -> (lfibIn(0, 1)))));;
    
ltake(15, lfib);;

(* Zad 3 *)
type 'a lBT = LEmpty | LNode of  'a * (unit ->'a lBT) * (unit -> 'a lBT);;

let rec lTree n =
    LNode(n, (function () -> lTree(2*n)), (function () -> lTree(2*n+1)));;

let bfs ltree =
    let rec bfsIn queue =
        match queue with
        | [] -> LNil
        | LEmpty::t -> bfsIn t
        | LNode(v, l, r)::t -> LCons(v, (function () -> bfsIn (t @ l() :: r() :: []))) in
        bfsIn [ltree];;
    
    
ltake(7, (bfs (lTree 5)));;