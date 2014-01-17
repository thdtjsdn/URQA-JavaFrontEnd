package urqa.action

import xitrum.annotation.GET

@GET("")
class About extends Api {
  def execute() {
    respondView(Map("type" ->"mustache"))
  }
}
