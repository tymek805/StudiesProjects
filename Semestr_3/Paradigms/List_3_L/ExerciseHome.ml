let group_repeated_elements lst =
  let rec group_helper acc current_group = function
    | [] -> acc
    | [x] -> (x :: current_group) :: acc
    | x :: (y :: _ as t) ->
        if x = y then
          group_helper acc (x :: current_group) t
        else
          group_helper ((x :: current_group) :: acc) [] t
  in
  List.rev (group_helper [] [] lst);;

    
let grouped_list = group_repeated_elements [1; 1; 2; 3; 3; 1; 4; 4; 4; 5];;