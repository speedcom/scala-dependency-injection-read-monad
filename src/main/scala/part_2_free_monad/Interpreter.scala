package part_2_free_monad

// INTERPRETER
class PureInterpreter {

  def runKVS[A](cache: Free[Cache, A],
                table: Map[String, String]): Map[String, String] = cache match {
    case More(Put(k, v, next)) => runKVS(next, table + (k -> v))
    case More(Get(k, f)) => runKVS(f(table(k)), table)
    case More(Delete(k, next)) => runKVS(next, table - k)
    case Done(a) => table
  }

}
