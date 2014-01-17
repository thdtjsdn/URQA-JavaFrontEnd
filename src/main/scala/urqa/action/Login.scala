package urqa.action

import xitrum.annotation.GET

@GET("login")
class Login extends DefaultLayout {
  def execute() {
    respondView(Map("type" ->"mustache"))
  }
}
