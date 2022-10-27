package comparator

import org.apache.log4j.Logger

object Loggin {
  def LogMessage(str: String, pObject: Object): Unit = {
    val LOG = Logger.getLogger(pObject.getClass)
    LOG.info(str)
  }
}
