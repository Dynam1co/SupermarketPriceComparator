package comparator

import comparator.bucket.MinioOps
import org.apache.spark.sql.DataFrame

object DatasetIO {
  def storeDataFrame(dfIn: DataFrame, currTimestamp: String, tableName: String): Unit = {
    val storage_config: StorageConfig = Config.GetStorageConfig
    prepareBucket(storage_config, currTimestamp)

    dfIn.show(false)

    Loggin.LogMessage(s"Saving parquet file $tableName", this)
    dfIn.write.mode("overwrite").parquet(s"${Constants.Storage.STORAGE_URL}${storage_config.minioBucket}/$currTimestamp/$tableName")
    Loggin.LogMessage(s"Parquet file $tableName saved", this)
  }

  def prepareBucket(storage_config: StorageConfig, currTimestamp: String): Unit = {
    MinioOps.createBucketIfNotexists(storage_config.minioBucket)
    MinioOps.createFolderIntoBucketIfNotExists(storage_config.minioBucket, currTimestamp)
  }
}
