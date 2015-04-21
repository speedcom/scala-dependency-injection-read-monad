package part_1_injection


object ConnProviderInstances {
  lazy val sqliteTestDB: ConnProvider = ConnProvider.mkProvider(
    "org.sqlite.JDBC",
    "jdbc:sqlite:memory:")

  lazy val mysqlProdDB: ConnProvider = ConnProvider.mkProvider(
    "org.gjt.mm.mysql.Driver",
    "jdbc:mysql://prod:3306/?user=one&pass=two")
}
