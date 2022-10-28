package comparator

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Utils {
  def getCurrTimestamp: String = {
    DateTimeFormatter.ofPattern("YYYYMMdd_HHmmss").format(LocalDateTime.now)
  }
}
