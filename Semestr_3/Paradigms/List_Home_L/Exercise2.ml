type point = float * float
             
let insertion_sort (compare : 'a -> 'a -> int) (arr : 'a list) : 'a list =
  (* Ta funkcja przechodzi przez posortowane już elementy i wstawia dany element w poprawne miejsce *)
  let rec insert_sorted x sorted_list =
    match sorted_list with
    (* Jeśli posortowana lista jest pusta to tworzymy listę zawierającą ten element *)
    | [] -> [x]
    | hd :: tl ->
        (* Jeśli element jest mniejszy lub równy wstawiamy go przed dany element *)
        if compare x hd <= 0 then
          x :: sorted_list
        else
          (* Jeśl jest większy to kontynuujemy przechodzenie przez posortowaną listę w poszukiwaniu poprawnego miejsca *)
          hd :: insert_sorted x tl
  in

  (* Ta funkcja rekurencyjne będzie przechodziła przez nieposotrowaną tablicę do momentu gdy dojdzie do jej końca *)
  let rec sort_rec unsorted sorted =
    match unsorted with
    (* Sprawdzamy czy już doszliśmy do końca nieposortowanej tablicy, jeśli tak to zwracamy posortowaną już tablicę *)
    | [] -> sorted 
    (* Jeśli nie to kontynuujemy sortowanie, za każdym razem usuwając pierwszy element z tablicy nieposortowanej i jest wstawiany w poprawne miejsce *)
    | hd :: tl -> sort_rec tl (insert_sorted hd sorted)
  in
  
  (* Rozpoczynamy sortowanie z daną nieposortowaną tablicą i pustą, do której będziemy wstawiali posortowane już elementy *)
  sort_rec arr []
;;

let distance_from_origin (x, y) = sqrt (x *. x +. y *. y);;
let compare_fn p1 p2 = compare (distance_from_origin p1) (distance_from_origin p2);;

insertion_sort compare_fn [(3., 6.); (1., 1.); (0., 2.); (1., 0.); (0., 0.)];;
insertion_sort compare_fn [(1., 2.); (5., 3.); (2., 4.); (2., 3.)];; 
insertion_sort compare_fn [(1.,2.); (2.,1.); (1.,1.); (0.,0.)];; 