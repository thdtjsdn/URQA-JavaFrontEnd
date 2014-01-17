package urqa.action

import xitrum.annotation.GET

@GET("")
class About extends DefaultLayout {
  def execute() {
    respondView(Map("type" ->"mustache"))
  }
}
