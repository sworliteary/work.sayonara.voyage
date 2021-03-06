package workbuilder.pages

import java.nio.file.Path
import java.nio.file.Paths

import workbuilder.PageGenerator
import workbuilder.Database
import workbuilder.Template

sealed trait AllTagPage

object AllTagPageObject extends AllTagPage

object AllTagPage {
  implicit object AllTagPageGenerator extends PageGenerator[AllTagPage] {
    def generate(source: AllTagPage, database: Database): Map[Path, String] = {
      val path = Paths.get("tags/index.html")
      val novels = database.getNovels
      val html = Template.htmlPage(
        "タグ一覧",
        s"""
        |<h1>Tags</h1>
        |${database.getTags
            .sortBy(_.name)
            .map(t => t.htmlTag(Some(novels.filter(_.hasTag(t)).length)))
            .mkString}""".stripMargin
      )
      Map(path -> html)
    }
  }
}
