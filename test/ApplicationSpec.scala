import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._
import play.api.Logger
import org.slf4j.LoggerFactory
import ch.qos.logback.classic.Level;
//import ch.qos.logback.classic.Logger

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {
 //  not working
//  val logroot = LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME)
 //                            .asInstanceOf[ch.qos.logback.classic.Logger]
//  logroot.setLevel(Level.OFF)
  
// Logger.warn ("i logged !!!")
  

  "Root Application" should {

    "send 404 on a bad request" in new WithApplication {
      route(FakeRequest(GET, "/boum")) must beSome.which (status(_) == NOT_FOUND)
    }

    "render the index page" in new WithApplication {
      val home = route(FakeRequest(GET, "/")).get

      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "text/html")
    }

    
    "get all journals" in new WithApplication {
      val journals = route(FakeRequest(GET, "/journals")).get
      status(journals) must equalTo(OK)
      contentType(journals) must beSome.which(_ == "application/json")
    }
  
  }
}
