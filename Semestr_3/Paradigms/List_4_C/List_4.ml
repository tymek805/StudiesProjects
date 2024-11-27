type 'a bt = Empty | Node of 'a * 'a bt * 'a bt;;
  
let tt = Node(1, Node(2, Node(4, Empty, Empty), Empty ), Node(3,
  Node(5, Empty, Node(6, Empty, Empty)), Empty));;
 
 let breadthBT bt =
    let rec breadthIN acc res =
        if acc = [] then  res
        else 
            match List.hd acc with
            Empty -> breadthIN (List.tl acc) res
            | Node(v, l, r) -> breadthIN ((List.tl acc) @ [l; r]) (v::res) in
    List.rev (breadthIN [bt] []);;
    
let internalPath bt =
    let rec internal n res lvl =
    match n with
        Empty -> res
        | Node(v, l, r) -> internal l (internal r (res+lvl) (lvl+1)) (lvl+1) in
    internal bt 0 0;;

let externalPath bt =
    let rec extern n res lvl =
    match n with
        Empty -> res + lvl
        | Node(v, l, r) -> extern l (extern r res (lvl+1)) (lvl+1) in
    extern bt 0 0;;

type 'a graph = Graph of ('a -> 'a list);;
 let g = Graph
 (function
 0 -> [3]
 | 1 -> [0;2;4]
 | 2 -> [1]
 | 3 -> []
 | 4 -> [0;2]
 | n -> failwith ("Graph g: node "^string_of_int n^" doesn't exist")
);;

let depthSearch (Graph gr) st =
    let rec dfs acc res =
        if acc = [] then res
        else 
            if  List.mem (List.hd acc) res then
                dfs (List.tl acc) res
            else 
                dfs ((gr (List.hd acc)) @ (List.tl acc)) ((List.hd acc) :: res)  in
    List.rev (dfs [st] []);;
