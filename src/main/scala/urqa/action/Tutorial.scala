package urqa.action

import xitrum.annotation.GET

@GET("tutorial")
class Tutorial extends Api {
  def execute() {
    respondView(Map("type" ->"mustache"))
  }
}
