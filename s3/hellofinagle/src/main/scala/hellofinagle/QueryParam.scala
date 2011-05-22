package hellofinagle

import org.jboss.netty.handler.codec.http._

class QueryParam(request:HttpRequest) {
    val decoder = new QueryStringDecoder(request.getUri())
    val params  = decoder.getParameters()

    def apply[T](name:String,default:T=null)(implicit mf:ClassManifest[T])  = {
        if(params.containsKey(name)) {
            val value = params.get(name)
            if(value.size > 0) {
                val clazz = mf.erasure
                val ret = if(classOf[String] isAssignableFrom clazz) { 
                    value.get(0)
                } else if(classOf[Long] isAssignableFrom clazz) {
                    value.get(0).toLong
                }
                ret
            }
            else default
        }
        else {
            default
        }
    }
}



// vim: set ts=4 sw=4 et:
