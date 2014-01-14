package urqa.action

import scala.slick.session.Database.threadLocalSession
import scala.slick.driver.MySQLDriver.simple._
import xitrum.{Action, SkipCsrfCheck}
import xitrum.annotation.GET
import xitrum.annotation.POST
import org.joda.time.{DateTime, Period}
import org.joda.time.Chronology
import org.joda.time.chrono
import util.Properties
import java.io.File
import com.typesafe.config.ConfigFactory
import scala.collection.JavaConversions._
import scala.io.Source

trait Api extends Action with SkipCsrfCheck {
	override def layout = renderViewNoLayout[DefaultLayout]()
}


object DBSession {
  def getSession(): Database = {

  	    val cfg = ConfigFactory.load();

// xitrum.local.db.driver
// com.mysql.jdbc.Driver
// xitrum.local.db.url
// jdbc:mysql://localhost:3306/newMariaDB
// xitrum.local.db.password
// 1234
// xitrum.local.db.user
// root
var productiondriver: String = ""
var productionurl: String = ""
        for (e <- cfg.entrySet() if e.getKey().contains("xitrum.local.db")) {
      if (e.getKey().contains("xitrum.local.db.driver")) {
      	productiondriver = e.getValue().unwrapped().toString
      }
      if (e.getKey().contains("xitrum.local.db.url")) {
      	productionurl = e.getValue().unwrapped().toString
      }
    }
    var dbsession =  Database.forURL(productionurl, driver = productiondriver, user="root", password="1234")
    dbsession.asInstanceOf[scala.slick.session.Database]

  }
}

object DivdeDash {
	def getFirst(inputValue:String): Int = {
		val dashindex = inputValue.indexOf('-')
		val fvalue = inputValue.substring(0,dashindex)
		return fvalue.toInt
	}
	def getSecond(inputValue:String): Int = {
		val dashindex = inputValue.indexOf('-')
		val fvalue = inputValue.substring(dashindex+1)
		return fvalue.toInt
	}
}

object TransDate {
	def weektoDate(): String = {

	return "aa"
	}
	def datetoWeek(): String = {

		return "bb"
	}
	def getdatebyday(inputyear:Int, inputweek:Int, inputday:Int): String = {
		val from = new DateTime(inputyear,1,1,0,0)
		val startdate = from.withWeekyear(from.getYear()).withWeekOfWeekyear(inputweek).withDayOfWeek(inputday)
		return startdate.toLocalDate().toString
	}
	def getbeforeweek(inputyear:Int, inputweek:Int): String = {
		var weekvalue = inputweek - 1
		var yearvalue = inputyear
		if (weekvalue == 0){
			yearvalue = inputyear - 1
			weekvalue = 52
		}
		return yearvalue + "-" + weekvalue
	}
	def getafterweek(inputyear:Int, inputweek:Int): String = {
		var weekvalue = inputweek + 1
		var yearvalue = inputyear
		if (weekvalue == 53){
			yearvalue = inputyear + 1
			weekvalue = 1
		}
		return yearvalue + "-" + weekvalue
	}
	def getCurrentTime(): DateTime = {
		val ctime = new DateTime()
		return ctime
	}
	def getCurrentWeek():  Int = {
		val ctime = new DateTime()
		 val week = ctime.weekOfWeekyear().get();
		 return week
	}
	def getCurrentYear():  Int = {
		val ctime = new DateTime()
		 val year = ctime.year().get();
		 return year
	}
}