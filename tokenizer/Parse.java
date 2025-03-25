// Predictive Parsing Table
// |            | ++                  | --                  | +                       | -                       | (                  | )   | 0-9                                              | F                  | $ (end) |
// | ---------- | ------------------- | ------------------- | ----------------------- | ----------------------- | ------------------ | --- | ------------------------------------------------ | ------------------ | ------- |
// | expr       | `term expr_prime`   | `term expr_prime`   |                         |                         | `term expr_prime`  |     | `term expr_prime`                                | `term expr_prime`  |         |
// | expr_prime | `ε`                 | `ε`                 | `binop term expr_prime` | `binop term expr_prime` |                    | `ε` |                                                  |                    | `ε`     |
// | term       | `prime term_prime`  | `prime term_prime`  |                         |                         | `prime term_prime` |     | `prime term_prime`                               | `prime term_prime` |         |
// | term_prime | `incrop term_prime` | `incrop term_prime` | `ε`                     | `ε`                     |                    | `ε` |                                                  |                    | `ε`     |
// | prime      | `incrop prime`      | `incrop prime`      |                         |                         | `(expr)`           |     | `num`                                            | `lvalue`           |         |
// | lvalue     |                     |                     |                         |                         |                    |     |                                                  | `F expr`           |         |
// | incrop     | `++`                | `--`                |                         |                         |                    |     |                                                  |                    |         |
// | binop      |                     |                     | `+`                     | `-`                     |                    |     |                                                  |                    |         |
// | num        |                     |                     |                         |                         |                    |     | `0`, `1`, `2`, `3`, `4`, `5`, `6`, `7`, `8`, `9` |                    |         |

import java.util.Stack;

public class Parse {
    public tring input = "";
    private int indexOfInput = -1;
    Stack <String> strack = new Stack<String>();
    String [][] table = {
        // ++ | -- | + | - | ( | ) | 0-9 | F | $ (end)
        
        // expr 
       {"term expr_prime", "term expr_prime", null, null, "term expr_prime", null,
       "term expr_prime", "term expr_prime", "term expr_prime", "term expr_prime", "term expr_prime", "term expr_prime", "term expr_prime", "term expr_prime", "term expr_prime", "term expr_prime", // 0-9
       "term expr_prime", null},

       // expr_prime 
       {"", "", "binop term expr_prime", "binop term expr_prime", null, "",
       null, null, null, null, null, null, null, null, null, null, // 0-9
       null, ""},

       // term
       {"prime term_prime", "prime term_prime", null, null, "prime term_prime", null,
       "prime term_prime", "prime term_prime", "prime term_prime", "prime term_prime", "prime term_prime", "prime term_prime", "prime term_prime", "prime term_prime", "prime term_prime", "prime term_prime", // 0-9
       "prime term_prime", null},

       // term_prime
       {"incrop term_prime", "incrop term_prime", "", "", null, "",
       null, null, null, null, null, null, null, null, null, null, // 0-9
       null, ""},

       // prime
       {"incrop prime", "incrop prime", null, null, "(expr)", null,
       "num", "num", "num", "num", "num", "num", "num", "num", "num", "num",
       "lvalue", null},

       // lvalue
       {null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, // 0-9
        "F expr", null},

       // incrop
       {"++", "--", null, null, null, null,
       null, null, null, null, null, null, null, null, null, null, // 0-9
       null, null},

       // binop
       {null, null, "+", "-", null, null,
       null, null, null, null, null, null, null, null, null, null, // 0-9
       null, null},

       // num
       {null, null, null, null, null, null,
       "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", // 0-9
        null, null},
    };

    public static void main(String[] args){
        for(String each : args){
            System.out.println(each);
        }
    }
}
