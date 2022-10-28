package comparator.supermarket

import comparator.SupermarketConfig

trait Supermarket {
  val base_api_url: String
  val categoies_endpoint: String
  val supermarket_name: String
  val supermarket_config: SupermarketConfig

  def downloadAll(): Unit
  def getCategories: List[ParentCategory]
  def composeEndpoint(baseUrl: String, params: String*): String
}
