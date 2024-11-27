type 'a bt = Empty | Node of 'a * 'a bt * 'a bt;;

let tt = Node(1,
              Node(2,
                   Node(4,
                        Empty,
                        Empty
                       ),
                   Empty
                  ),
              Node(3,
                   Node(5,
                        Empty,
                        Node(6,
                             Empty,
                             Empty
                            )
                       ),
                   Empty
                  )
             );; 

let rec mirror_tree = function
  | Empty -> Empty
  | Node (value, left, right) -> Node (value, mirror_tree right, mirror_tree left);; 

mirror_tree tt;; 