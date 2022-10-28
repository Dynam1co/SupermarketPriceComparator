package comparator.bucket

import comparator.{Config, Loggin, StorageConfig}
import java.io.ByteArrayInputStream
import io.minio.{BucketExistsArgs, MakeBucketArgs, MinioClient, PutObjectArgs}
import io.minio.StatObjectArgs
import io.minio.errors.ErrorResponseException

object MinioOps {
  val storageConfig: StorageConfig = Config.GetStorageConfig

  val minioClient: MinioClient =
    MinioClient.builder()
      .endpoint(storageConfig.endpoint)
      .credentials(storageConfig.accessKey, storageConfig.secretKey)
      .build()

  def createBucketIfNotexists(bucket: String): Unit = {
    val found: Boolean = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())

    if (found) {
      Loggin.LogMessage(s"Bucket: $bucket exists", this)
    } else {
      minioClient.makeBucket(
        MakeBucketArgs.builder()
          .bucket(bucket)
          .build())

      Loggin.LogMessage(s"Bucket: $bucket created", this)
    }
  }

  def createFolderIntoBucketIfNotExists(bucketName: String, folderName: String): Unit = {
    val objectResultName = folderName + "/"

    if (!isObjectExist(bucketName, folderName)) {
      minioClient.putObject(PutObjectArgs.builder.bucket(bucketName).`object`(objectResultName).stream(new ByteArrayInputStream(new Array[Byte](0)), 0, -1).build)
      Loggin.LogMessage(s"Created folder $folderName into bucket $bucketName", this)
    }
  }

  def isObjectExist(bucketName: String, objectName: String): Boolean = try {
    val objectResultName = objectName + "/"

    minioClient.statObject(StatObjectArgs.builder.bucket(bucketName).`object`(objectResultName).build)
    true
  } catch {
    case _: ErrorResponseException =>
      false
    case e: Exception =>
      e.printStackTrace()
      throw new RuntimeException(e.getMessage)
  }
}
