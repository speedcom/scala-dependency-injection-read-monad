package part_1_injection


case class DB[A](g: Connection => A) {
  def apply     (c: Connection): A     = g(c)
  def map    [B](f: A => B)    : DB[B] = DB(c => f(g(c)))
  def flatMap[B](f: A => DB[B]): DB[B] = DB(c => f(g(c))(c))
}

object DB {
  def pure[A](a: A): DB[A] = DB(c => a)
}