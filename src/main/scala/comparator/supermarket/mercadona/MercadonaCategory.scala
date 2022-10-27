package comparator.supermarket.mercadona

object MercadonaCategory {
  case class Categories (
                          id: Int,
                          name: String,
                          order: Int,
                          layout: Int,
                          published: Boolean,
                          is_extended: Boolean
                        )

  case class RootInterface (
                             id: Int,
                             name: String,
                             order: Int,
                             layout: Int,
                             published: Boolean,
                             categories: Seq[Categories],
                             is_extended: Boolean
                           )
}
