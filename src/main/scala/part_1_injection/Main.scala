package part_1_injection

import scala.io.StdIn

object Injection {
  def runInTest[A](f: ConnProvider => A): A = f(ConnProviderInstances.sqliteTestDB)

  def runInProduction[A](f: ConnProvider => A): A = f(ConnProviderInstances.mysqlProdDB)
}

object Main {

  def myProgram(userId: String): ConnProvider => Unit =
    cp => {
      println("Enter old pass")
      val oldPass = StdIn.toString
      println("Enter new pass")
      val newPass = StdIn.toString
      cp.apply(changePass(userId, oldPass, newPass))
    }

  def changePass(userId: String, oldPwd: String, newPwd: String): DB[Unit] = DB.pure(())

  import part_1_injection.Injection._

  def main(args: Array[String]): Unit = {
    runInTest(myProgram(args(0)))
  }

}

