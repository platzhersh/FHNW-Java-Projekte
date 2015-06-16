package spin

object Models {
  val peterson = """
/* Peterson's solution to the mutual exclusion problem - 1981 */

bool turn, flag[2];
byte ncrit;

active [2] proctype user() {
  assert(_pid == 0 || _pid == 1);
  int other = 1 - _pid;
again:
  flag[_pid] = 1;
  turn = _pid;
  (flag[other] == 0 || turn == other);

  ncrit++;
  assert(ncrit == 1); /* critical section */
  ncrit--;

  flag[_pid] = 0;
  goto again
}    
"""

  val inc = 
"""
int cnt = 0;

active proctype A() {
  int regA;
  regA = cnt;
  cnt = regA + 1;
}

active proctype B() {
  int regB;
  regB = cnt;
  cnt = regB + 1;
}

ltl endState {
  <>[](cnt >= 3)
}
"""
  
  val counter = 
"""
int cnt = 0;
#define N 10

active [2] proctype Thread() {
  int i; 
  do
  :: (i < N) ->
     int reg = cnt;
     cnt = reg + 1;
     i = i + 1;
     
  :: else -> 
     break;
  od 
}

// Acceptance
ltl minCntValue {
  <>([](cnt >= 3))
}
"""   
  
}