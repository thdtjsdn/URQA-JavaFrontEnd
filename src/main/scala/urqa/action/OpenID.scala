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
 
 object OpenId {
   val manager     = new ConsumerManager
   val SESSION_KEY = "openIdDiscovered"
 }
 
 @GET("openid/login")
 class OpenIdLogin extends Api {
   def execute() {
     respondView()
   }
 }
 
 @GET("openid/redirect")
 class OpenIdRedirect extends Api {
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
       redirectTo(destinationUrl)
     } catch {
       case NonFatal(e) =>
         log.warn("OpenID error", e)
         flash("Could not redirect to remote OpenID provider. Your OpenID may be wrong.")
         redirectTo("/")
     }
   }
 }
 
 @GET("openid/verify")
 class OpenIdVerify extends Api {
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
     respondJson(List(session("userId"),boolean))
   }
 }