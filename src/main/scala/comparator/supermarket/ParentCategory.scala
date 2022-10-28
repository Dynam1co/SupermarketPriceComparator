package comparator.supermarket

import comparator.{Config, Constants, DatasetIO, Datasets, Loggin, SparkMgt, StorageConfig}
import org.apache.spark.sql.functions.lit

class ParentCategory(val id: Int, val name: String, val childCategories: Seq[ChildCategory]) {

}

object ParentCategory extends Datasets {
  override def path: String = "/parent_category"
  override def colNames: Seq[String] = Seq("id", "name")

  def storeParentCategory(categoriesList: List[ParentCategory], currTimestamp: String): Unit = {
    val storage_config: StorageConfig = Config.GetStorageConfig

    val spark = SparkMgt.CreateSparkSession(Config.GetSparkConfig, storage_config)

    Loggin.LogMessage(s"Creating spark dataframe for $name", this)

    val df = spark.createDataFrame(categoriesList.map(e => Tuple2(e.id, e.name))).toDF(colNames: _*)
      .withColumn("supermarket", lit(Constants.Supermarkets.MERCADONA))

    DatasetIO.storeDataFrame(df, currTimestamp, name)
  }
}