let rec count_occurrences lst count_map =
  match lst with
  | [] -> count_map
  | hd :: tl ->
      let updated_count_map = 
        match List.assoc_opt hd count_map with
        | Some(count) -> List.remove_assoc hd count_map
        | None -> count_map
      in
      let new_count = 
        match List.assoc_opt hd updated_count_map with
        | Some(count) -> count + 1
        | None -> 1
      in
      count_occurrences tl ((hd, new_count) :: updated_count_map)

let unique lst =
  let count_map = count_occurrences lst [] in
  List.fold_left (fun acc (elem, count) -> if count = 1 then elem :: acc else acc) [] count_map
