package comparator

import com.typesafe.config.{Config, ConfigException, ConfigFactory}

import java.io.File
import java.nio.file.{Files, Paths}

case class SparkConfig(appName: String, master: String)
case class StorageConfig(endpoint: String, accessKey: String, secretKey: String, minioBucket: String)
case class SupermarketConfig(baseUrl: String, categoriesEndpoint: String, urlParams: String)

object Config {
  val configPath: String = "src/conf/application.conf"
  val config: Config = ConfigFactory.parseFile(new File(configPath))
  TestFileConfExists()

  def GetExistsConfigFile: Boolean = {
    Files.exists(Paths.get(configPath))
  }

  def TestFileConfExists(): Unit = {
    if (!GetExistsConfigFile)
      sys.error(s"Configuraton file don't exists. File: $configPath")
  }

  def getSuperparkedConfig(supermarketName: String): SupermarketConfig = {
    try {
      val superConf = config.getConfig(s"conf.supermarket.$supermarketName")
      Loggin.LogMessage("Supermarket config is loaded", this)

      SupermarketConfig(superConf.getString("base_url"), superConf.getString("categories_endpoint"), superConf.getString("url_params"))
    }
    catch {
      case _: ConfigException => sys.error(s"Supermarket $supermarketName conf not found")
      case _: UnknownError => sys.error(s"Uwnown error at load supermarket $supermarketName conf")
    }
  }

  def GetSparkConfig: SparkConfig = {
    try {
      val sparkConf = config.getConfig("conf.spark")
      Loggin.LogMessage("Spark config is loaded", this)

      SparkConfig(sparkConf.getString("appName"), sparkConf.getString("master"))
    }
    catch {
      case _: ConfigException => sys.error("Spark conf not found")
      case _: UnknownError => sys.error("Uwnown error at load spark conf")
    }
  }

  def GetStorageConfig: StorageConfig = {
    try {
      val storageConf = config.getConfig("conf.storage")
      Loggin.LogMessage("Storage config is loaded", this)

      val endpoint = storageConf.getString("minio_endpoint")
      val accessKey = storageConf.getString("minio_access_key")
      val secretKey = storageConf.getString("minio_secret_key")
      val minioBucket = storageConf.getString("minio_bucket")

      StorageConfig(endpoint, accessKey, secretKey, minioBucket)
    }
    catch {
      case _: ConfigException => sys.error("Storage conf not found")
      case _: UnknownError => sys.error("Uwnown error at load storage conf")
    }
  }
}
