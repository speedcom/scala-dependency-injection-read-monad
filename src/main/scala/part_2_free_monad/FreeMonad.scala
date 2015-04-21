package part_2_free_monad


trait Functor[F[_]] {
  def map[A, B](a: F[A])(f: A => B): F[B]
}

case class Done[F[_] : Functor, A](a: A) extends Free[F, A]

case class More[F[_] : Functor, A](k: F[Free[F, A]]) extends Free[F, A]

class Free[F[_], A](implicit F: Functor[F]) {

  def flatMap[B](f: A => Free[F, B]): Free[F, B] = this match {
    case Done(a) => f(a)
    case More(k) => More(F.map(k)(_ flatMap f))
  }

  def map[B](f: A => B): Free[F, B] = flatMap(a => Done(f(a)))
}