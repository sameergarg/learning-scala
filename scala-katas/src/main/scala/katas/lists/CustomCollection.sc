
def map[T,V](t: T)(f:T => V) = {
  f(t)
}

map(2){x => 3*x}
