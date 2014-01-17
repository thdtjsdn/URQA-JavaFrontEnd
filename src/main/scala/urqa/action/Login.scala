package urqa.action

import scala.collection.JavaConverters._
 import scala.util.control.NonFatal
 
 import org.openid4java.consumer.ConsumerManager
 import org.openid4java.discovery.DiscoveryInformation
 import org.openid4java.message.ParameterList
 import org.openid4java.message.ax.FetchRequest
 import org.openid4java.message.ax.FetchResponse
 import org.openid4java.message.ax.AxMessage
 import org.openid4java.discovery.Identifier
import org.openid4java.message._
import org.openid4java.OpenIDException
 import xitrum.annotation.{GET, POST}
@GET ("login")
class Login extends Api {
	def execute() {
		respondView(Map("type" ->"mustache"))
	}
}
@GET ("logout")
class Logout extends Api {
  def execute() {
    session.clear()
    redirectTo("/")
  }
}
@GET("login/redirect")
class LoginCheck extends Api {
  def execute() {
     try {
      // var openId            = param("openId")
       var openId = "https://www.google.com/accounts/o8/id"
       val discoveries       = OpenId.manager.discover(openId)
       val discovered        = OpenId.manager.associate(discoveries)
       val returnUrl         = absUrl[OpenIdVerify]  // Must be http(s)://...
       val authReq           = OpenId.manager.authenticate(discovered, returnUrl)

     val fetch = FetchRequest.createFetchRequest
      fetch.addAttribute("email", "http://axschema.org/contact/email", true)

       authReq.addExtension(fetch);

       val destinationUrl    = authReq.getDestinationUrl(true)
 
       session(OpenId.SESSION_KEY) = discovered
       session("8000Test") = "true"
       redirectTo(destinationUrl)
     } catch {
       case NonFatal(e) =>
         log.warn("OpenID error", e)
         flash("Could not redirect to remote OpenID provider. Your OpenID may be wrong.")
         redirectTo("/login")
     }

  }
}

 @GET("login/verify")
 class LoginVerify extends Api {
   def execute() {
var boolean = "false"
     try {
       val queryString  = request.getUri.substring(request.getUri.indexOf("?") + 1)
       val openIdResp   = ParameterList.createFromQueryString(queryString)
       val discovered   = session(OpenId.SESSION_KEY).asInstanceOf[DiscoveryInformation]
       val receivingUrl = absUrl[OpenIdVerify] + "?" + queryString
       val verification = OpenId.manager.verify(receivingUrl, openIdResp, discovered)
       val verified     = verification.getVerifiedId


       
       if (verified != null){
       session.clear()
       session("userId") = param("openid.ext1.value.email")
       boolean = "true"
    }
       else
         flash("OpenID login failed")
     } catch {
       case NonFatal(e) =>
         log.warn("OpenID error", e)
         flash("OpenID login failed")
     }  
    redirectTo("/afterlogin")
   }
 }

  @GET ("afterlogin")
 class Loggedin extends Api{
 	def execute(){
 		val verifiedID = session("userId").toString
 		if (verifiedID == "andy@favoritemedium.com" ||
 			verifiedID == "breakit100@gmail.com" ||
 			verifiedID == "pegasuskil@gmail.com" ||
 			verifiedID == "indigoguru@gmail.com" ||
 			verifiedID == "hcyang06@gmail.com") {
 		session("isAdmin") = "admin"
 	}
 		// DBSession.getSession().withSession{
	 	// 	 var q1= Query(Member).filter(t => t.name === verifiedID).list
	  //        if (q1.isEmpty) {
	  //       //timesheet is empty
	  //       //create timesheet
	  //       Member.noId insert(verifiedID, "false")
	  //        }
	  //        q1= Query(Member).filter(t => t.name === verifiedID).list

	  //        session("isAdmin") = q1(0).productIterator.toList(2).toString
	  //        println(session("isAdmin"))
	  //   }
 		redirectTo("/")
	}
}
