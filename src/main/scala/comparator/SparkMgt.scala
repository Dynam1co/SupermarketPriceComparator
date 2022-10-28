package comparator

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object SparkMgt {
  def CreateSparkSession(sparkConf: SparkConfig, sotrageConf: StorageConfig): SparkSession = {
    Logger.getLogger("org").setLevel(Level.OFF)

    val spark = SparkSession
      .builder()
      .appName(s"${sparkConf.appName}")
      .master(s"${sparkConf.master}")
      .config("spark.hadoop.fs.s3a.endpoint", s"${sotrageConf.endpoint}")
      .config("spark.hadoop.fs.s3a.access.key", s"${sotrageConf.accessKey}")
      .config("spark.hadoop.fs.s3a.secret.key", s"${sotrageConf.secretKey}")
      .config("spark.hadoop.fs.s3a.path.style.access", value = true)
      .config("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
      .config("spark.hadoop.fs.s3a.aws.credentials.provider", "org.apache.hadoop.fs.s3a.SimpleAWSCredentialsProvider")
      .getOrCreate()

    spark
  }
}
