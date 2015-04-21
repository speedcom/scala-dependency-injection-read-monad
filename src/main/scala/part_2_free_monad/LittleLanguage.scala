package part_2_free_monad

// AST
sealed trait Cache[Next]
case class Put[Next](key: String, value: String, a: Next) extends Cache[Next]
case class Get[Next](key: String, a: String => Next) extends Cache[Next]
case class Delete[Next](key: String, a: Next) extends Cache[Next]

object Cache {

  implicit val kvsFunctor: Functor[Cache] =
    new Functor[Cache] {
      override def map[A, B](a: Cache[A])(f: (A) => B): Cache[B] = a match {
        case Put(k, v, a) => Put(k, v, f(a))
        case Get(k, h)    => Get(k, x => f(h(x)))
        case Delete(k, a) => Delete(k, f(a))
      }
    }

  def put(k: String, v: String): Free[Cache, Unit] = {
    More(Put(k, v, Done(())))
  }
  def get(k: String): Free[Cache, String] = {
    More(Get(k, v => Done(v)))
  }
  def delete(k: String): Free[Cache, String] = {
    More(Delete(k, Done(())))
  }
  def modify(k: String, f: String => String): Free[Cache, Unit] = {
    for {
      v <- get(k)
      _ <- put(k, f(v))
    } yield ()
  }


}