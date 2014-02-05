package urqa.action

import xitrum.annotation.{GET, POST}

@POST ("client/connect")
class ClientConnect extends Api {
  def execute() {
  	var apikey = param("apikey")
  	var appversion = param("appversion")

    respondJson(Map("idsession"->"123981239"))
  }
}

@POST ("client/send/exception")
class ClientException extends Api {
  def execute() {

  	var apikey = param("apikey")
  	var datetime = param("datetime")
  	var device = param("device")
  	var country = param("country")
   	var errorname = param("errorname")
  	var errorclassname = param("errorclassname")
  	var linenum = param("linenum")
  	var callstack = param("callstack")
  	var appversion = param("appversion")
  	var osversion = param("osversion")
  	var gpson = param("gpson")
   	var wifion = param("wifion")
  	var mobileon = param("mobileon")
  	var scrwidth = param("scrwidth")
  	var scrheight = param("scrheight")
  	var batterylevel = param("batterylevel")
  	var availsdcard = param("availsdcard")
  	var appmemtotal = param("appmemtotal")
  	var appmemfree = param("appmemfree")
   	var appmemmax = param("appmemmax")
  	var kernelversion = param("kernelversion")
  	var xdpi = param("xdpi")
  	var ydpi = param("ydpi")
   	var sysmemlow = param("sysmemlow")
  	var scrorientation = param("scrorientation")
  	var tag = param("tag")
  	var rank = param("rank")

  	respondJson(Map("idinstance"->"12312"))
  }
}

@POST ("client/send/native")
class ClientNative extends Api {
  def execute() {
  	val map = requestContentJson[Map[String, String]]

  	respondJson(Map("idinstance"->"12312"))
  }
}

@POST ("client/send/exception/log/:idinstance")
class ClientExceptionLog extends Api {
  def execute() {
  	val map = requestContentJson[Map[String, String]]

  	respondJson(map)
  }
}

@POST ("client/send/exception/dump/:idinstance")
class ClientExceptionDump extends Api {
  def execute() {
  	val map = requestContentJson[Map[String, String]]

  	  	respondJson(map)
  }
}

@POST ("client/send/eventpath/:idinstance")
class ClientEventpath extends Api {
  def execute() {
  	val map = requestContentJson[Map[String, String]]

  	  	respondJson(map)
  }
}