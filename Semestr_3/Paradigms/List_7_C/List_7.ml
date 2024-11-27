(* Zad 1 *)
module type QUEUE_FUN =
sig
 type 'a t
 exception Empty of string
 val empty: unit -> 'a t
 val enqueue: 'a * 'a t -> 'a t
 val dequeue: 'a t -> 'a t
 val first: 'a t -> 'a
 val isEmpty: 'a t -> bool
end;;

module QueueList : QUEUE_FUN =
struct
    type 'a t = 'a list
    exception Empty of string

    let empty() = []
    let enqueue(e, queue) = queue @ [e]
    let dequeue queue =
        match queue with
        | h::t -> t
        | _ -> []
    let first queue =
        match queue with
        | h::t -> h
        | _ -> raise (Empty "module QueueList: first")
    let isEmpty queue =
        match queue with
        | [] -> true
        | _ -> false
end;;

module QueueListPair : QUEUE_FUN =
struct
    type 'a t = 'a list * 'a list
    exception Empty of string

    let empty() = ([], [])
    let enqueue(e, queue) = 
        match queue with
        | ([], yl) -> (List.rev yl, [e])
        | (xl, yl) -> (xl, e::yl)
    let dequeue queue =
        match queue with
        | ([], []) -> ([], [])
        | ([], yl) -> (List.tl (List.rev yl), [])
        | (xl::xlt, yl) -> (xlt, yl)
    let first queue =
        match queue with
        | ([], []) -> raise (Empty "module QueueListPair: first")
        | ([], yl) -> List.hd (List.rev yl)
        | (xlh::xl, _) -> xlh
    let isEmpty queue =
        match queue with
        | ([], []) -> true
        | _ -> false
end;;

(* Zad 2*)
module type QUEUE_MUT =
sig
 type 'a t
 exception Empty of string
 exception Full of string 
 val empty: int -> 'a t
 val enqueue: 'a * 'a t -> unit
 val dequeue: 'a t -> unit
 val first: 'a t -> 'a
 val isEmpty: 'a t -> bool
 val isFull: 'a t -> bool
end;;

module QueueCyclicArrayMutable : QUEUE_MUT =
struct
    type 'a t = {mutable f : int; mutable r : int; mutable size : int; mutable arr : 'a option array }
    exception Empty of string
    exception Full of string

    let empty(s) = {f=0; r=0; size = s; arr = Array.make s None}

    let enqueue(elem, q) =
        if q.f = q.r && q.arr.(q.f) != None then 
            raise (Full "module QueueCyclicArrayMutable: enqueue")
        else
            q.arr.(q.r) <- Some elem;
            q.r <- ((q.r + 1) mod q.size)
     
    let dequeue(q) =
        q.arr.(q.f) <- None;
        q.f <- ((q.f + 1) mod q.size)
        
    let first(q) = 
        match q.arr.(q.f) with
        | Some e -> e
        | None -> raise (Empty "module QueueCyclicArrayMutable: first")

    let isEmpty(q) = q.arr.(q.f) = None
    let isFull(q) = q.f = q.r && q.arr.(q.f) != None
end;;

