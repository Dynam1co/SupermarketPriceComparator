package comparator.supermarket

import comparator.bucket.MinioOps
import comparator.{Config, Datasets, Loggin, SparkMgt, StorageConfig}

class ParentCategory(val id: Int, val name: String, val childCategories: Seq[ChildCategory]) {

}

object ParentCategory extends Datasets {
  override def path: String = "parent_category"

  def storeParentCategory(categoriesList: List[ParentCategory], currTimestamp: String): Unit = {
    val storage_config: StorageConfig = Config.GetStorageConfig

    val spark = SparkMgt.CreateSparkSession(Config.GetSparkConfig, storage_config)

    MinioOps.createBucketIfNotexists(storage_config.minioBucket)
    MinioOps.createFolderIntoBucketIfNotExists(storage_config.minioBucket, currTimestamp)

    Loggin.LogMessage(s"Creating spark dataframe for $name", this)
    val df = spark.createDataFrame(categoriesList.map(e => Tuple2(e.id, e.name))).toDF("id", "name")

    df.show(false)

    Loggin.LogMessage(s"Saving parquet file $name", this)
    df.write.mode("overwrite").parquet(s"s3a://${storage_config.minioBucket}/$currTimestamp/$name")
    Loggin.LogMessage(s"Parquet file $name saved", this)
  }
}