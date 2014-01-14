package urqa.action

import scala.slick.session.Database.threadLocalSession
import scala.slick.driver.MySQLDriver.simple._
import xitrum.{Action, SkipCsrfCheck}
import xitrum.annotation.GET
import xitrum.annotation.POST

trait DefaultLayout extends Action {
  override def layout = renderViewNoLayout[DefaultLayout]()
}
