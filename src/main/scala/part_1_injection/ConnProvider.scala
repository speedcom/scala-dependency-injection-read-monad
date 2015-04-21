package part_1_injection



trait ConnProvider {
  def apply[A](dbAction: DB[A]): A
}

object ConnProvider {

  def mkProvider(driver: String, url: String) = new ConnProvider {
    override def apply[A](dbAction: DB[A]): A = {
      Class.forName(driver)
      val conn = new Connection {}
      try {
        dbAction(conn)
      } finally {
        conn.close
      }
    }
  }
}