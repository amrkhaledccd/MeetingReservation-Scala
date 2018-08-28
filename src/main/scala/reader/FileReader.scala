package reader
import scala.io.Source

object FileReader  {

  def fromClassPath(name: String) = {
    val source = Source.fromInputStream(getClass().getResourceAsStream(name))
    try {source.getLines().toList} finally {source.close()}
  }
}
