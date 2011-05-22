package hellofinagle

import com.twitter.finagle.{Service,SimpleFilter}
import org.jboss.netty.handler.codec.http._
import org.jboss.netty.handler.codec.http.HttpResponseStatus._
import org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1
import org.jboss.netty.buffer.ChannelBuffers.copiedBuffer
import org.jboss.netty.util.CharsetUtil.UTF_8

import java.net.InetSocketAddress
import com.twitter.finagle.builder.{Server, Http, ServerBuilder}

object HttpServer {

    class HandleExceptions extends SimpleFilter[HttpRequest,HttpResponse] {
        def apply(request: HttpRequest,service: Service[HttpRequest,HttpResponse]) = {
            service(request) handle { case error  =>
                val statusCode = error match {
                    case _ : IllegalArgumentException  => FORBIDDEN
                    case _ => INTERNAL_SERVER_ERROR
                }
                val errorResponse = new DefaultHttpResponse(HTTP_1_1,statusCode)
                errorResponse.setContent(copiedBuffer(error.getStackTraceString,UTF_8))
                errorResponse
            }
        }
    }

    class Respond extends Service[HttpRequest,HttpResponse] {

        val routeMap = Map(
            "/hello" -> new HelloService
        )

        def apply(request: HttpRequest) = {
            try {
                val decoder = new QueryStringDecoder(request.getUri());

                val path = decoder.getPath()
                val service = routeMap.getOrElse(path,null)
                val response : Future[HttpResponse] = if(service != null) {
                    service(request)
                }
                else {
                    val response = new DefaultHttpResponse(HTTP_1_1,NOT_FOUND)
                    response.setContent(copiedBuffer("Not found endpoint",UTF_8))
                    Future.value(response)
                }
                response
            } catch {
                case e:Exception => Future.exception(e)
            }
        }
    }


    def main(args: Array[String]) {

        val handleExceptions = new HandleExceptions
        val respond = new Respond

        val service : Service[HttpRequest,HttpResponse] = handleExceptions andThen respond

        val server : Server = ServerBuilder()
            .codec(Http)
            .bindTo(new InetSocketAddress(7171))
            .build(service)
    }
}


// vim: set ts=4 sw=4 et:
