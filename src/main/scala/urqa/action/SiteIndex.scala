package urqa.action

import xitrum.annotation.GET

@GET ("projectpage")
class Projectpage extends Api {
  def execute() {
respondView(Map("type" ->"mustache"))
  }
}

@GET ("clientpage")
class Clientpage extends Api {
  def execute() {
respondView(Map("type" ->"mustache"))
  }
}
@GET ("entitypage")
class Entitypage extends Api {
  def execute() {
respondView(Map("type" ->"mustache"))
  }
}
@GET ("memberpage")
class Memberpage extends Api {
  def execute() {
respondView(Map("type" ->"mustache"))
  }
}