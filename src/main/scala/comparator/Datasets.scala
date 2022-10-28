package comparator

trait Datasets {
  def path: String
  def name: String = path.split("/").last
  def colNames: Seq[String]
}
