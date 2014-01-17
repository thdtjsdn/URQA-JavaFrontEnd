package urqa.action

import xitrum.annotation.GET

@GET("tutorial")
class Tutorial extends DefaultLayout {
  def execute() {
    respondView(Map("type" ->"mustache"))
  }
}
