package hellofinagle

import com.twitter.finagle.Service
import com.twitter.util.Future
import com.twitter.logging.Logger

import org.jboss.netty.handler.codec.http._
import org.jboss.netty.handler.codec.http._
import org.jboss.netty.handler.codec.http.HttpResponseStatus._
import org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1
import org.jboss.netty.buffer.ChannelBuffers.copiedBuffer
import org.jboss.netty.util.CharsetUtil.UTF_8


class HelloService extends Service[HttpRequest,HttpResponse] {

    def apply(request: HttpRequest) : Future[HttpResponse] = {
        val queryParam = new QueryParam(request)
        val userId  = queryParam[Long]("userId",0L).asInstanceOf[Long]
        val username = queryParam[String]("username",0L).asInstanceOf[String]
 
        val msg = "userId:%s username:%s".format(userId,username))
        print(msg)

        val response = new DefaultHttpResponse(HTTP_1_1,OK)
        response.setContent(copiedBuffer(msg,UTF_8))
        Future.value(response)
    }
}

// vim: set ts=4 sw=4 et:
