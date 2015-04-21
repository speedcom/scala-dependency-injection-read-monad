package part_1_injection


case class Reader[C, A](g: C => A) {
  def apply(c: C): A = g(c)
  def map[B](f: A => B): Reader[C, B] = Reader(c => f(g(c)))
  def flatMap[B](f: A => Reader[C, B]): Reader[C, B] = Reader(c => f(g(c)).g(c))
}

object Reader {
  def pure[C, A](a: A) = Reader(c => a)

  implicit def reader[C,A](f: C => A) = Reader(f)
}

trait KeyValueStore {
  def put(key: String, value: String): Unit
  def get(key: String): String
  def delete(key: String): Unit
}

object KeyValueStore {
  def modify(key: String, f: String => String): Reader[KeyValueStore, Unit] = {
    for {
      v <- _.get(key)
      _ <- _.put(key, f(v))
    } yield ()
  }
}