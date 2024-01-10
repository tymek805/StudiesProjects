type tstack = Scal of float | Vect of float * float
type eval = Scal of float | Vect of float * float | Neg | Add | Sub | Mul | Prod;;

exception InvalidExpression of string

let eval instructions =
  let rec eval_helper stack instr =
    match (stack, instr) with
    | (_, []) -> (
        match stack with
        | [Scal result] -> result
        | _ -> raise (InvalidExpression "Niewystrawczająca liczba argumentów")
      )
    | (s :: ss, Scal x :: is) -> eval_helper (Scal (x *. s) :: ss) is
    | (s :: ss, Vect (vx, vy) :: is) -> eval_helper (Vect (vx *. s, vy *. s) :: ss) is
    | (_, Neg :: _) -> raise (InvalidExpression "Niewystrawczająca liczba argumentów")
    | (s1 :: ss, Neg :: is) -> eval_helper ((Scal (-.s1)) :: ss) is
    | (s2 :: s1 :: ss, Add :: is) -> eval_helper (Scal (s1 +. s2) :: ss) is
    | (s2 :: s1 :: ss, Sub :: is) -> eval_helper (Scal (s1 -. s2) :: ss) is
    | (s2 :: s1 :: ss, Mul :: is) -> (
        match (s1, s2) with
        | (Scal x, Vect (vx, vy)) | (Vect (vx, vy), Scal x) ->
            eval_helper (Vect (vx *. x, vy *. x) :: ss) is
        | (Scal x1, Scal x2) -> eval_helper (Scal (x1 *. x2) :: ss) is
        | (_, _) -> raise (InvalidExpression "Typ argumentu Mul jest niepoprawny")
      ) 
  in
  eval_helper [] instructions;; 