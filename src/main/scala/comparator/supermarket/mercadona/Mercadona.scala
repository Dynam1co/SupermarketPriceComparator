package comparator.supermarket.mercadona

import comparator.{Config, Loggin, SupermarketConfig}
import comparator.supermarket.{ChildCategory, ParentCategory, Supermarket}
import sttp.client3.basicRequest
import sttp.client3._
import org.json4s._
import org.json4s.native.JsonMethods._
import sttp.model.Uri

class Mercadona extends Supermarket {
  override val supermarket_name = "mercadona"
  override val supermarket_config: SupermarketConfig = Config.getSuperparkedConfig(supermarket_name)
  override val base_api_url: String = supermarket_config.baseUrl
  override val categoies_endpoint: String = supermarket_config.categoriesEndpoint
  val url_params: String = supermarket_config.urlParams
  val url_get_categories: String = composeEndpoint(base_api_url, categoies_endpoint, url_params)

  override def composeEndpoint(baseUrl: String, params: String*): String = {
    var end = ""
    params.foreach(param => end += param)

    baseUrl + end
  }

  override def downloadAll(): Unit = {

  }

  override def getCategories(): List[ParentCategory] = {
    val categories = downloadCategories()

    catToStandarCategory(categories)
  }

  def catToStandarCategory(cats: List[MercadonaCategory.RootInterface]): List[ParentCategory] = {
    cats.map(mercadonaCat => {
      val standarChildCats = mercadonaCat.categories.map(mercaChildCat => new ChildCategory(mercaChildCat.id, mercaChildCat.name))

      new ParentCategory(mercadonaCat.id, mercadonaCat.name, standarChildCats)
    })
  }

  def downloadCategories(): List[MercadonaCategory.RootInterface] = {
    val request = basicRequest
      .get(uri"$url_get_categories")

    val backend = HttpClientSyncBackend()
    val response = request.send(backend)

    response.body match {
      case Left(error) =>
        Loggin.LogMessage(s"Error when executing request: $error", this)
        sys.error(s"Error when executing request: $error")
        null

      case Right(data) =>
        val parsedJson = parse(data, useBigDecimalForDouble = false, useBigIntForLong = false) \ "results"

        implicit val formats: Formats = DefaultFormats
        parsedJson.children.map(category => category.extract[MercadonaCategory.RootInterface])
    }
  }
}

object Mercadona extends App {
  val mercadona = new Mercadona()
  val categoriesList = mercadona.getCategories()
  println(mercadona.url_get_categories)
}
