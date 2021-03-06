package org.ligi.plughub

import com.squareup.okhttp.*
import java.net.Proxy

public class EdiMaxCommunicator (cfg: EdiMaxConfig) {

    val cfg: EdiMaxConfig = cfg

    public fun executeCommand(cmd: String, function: (param: String?) -> Unit) {
        Thread(Runnable {
            val body = RequestBody.create(null, cmd);

            val client = OkHttpClient();

            client.setAuthenticator(auth())

            val request = Request.Builder()

                    .url("http://${cfg.host}:${cfg.port}/smartplug.cgi")
                    .post(body)
                    .build();

            try {
                val response = client.newCall(request).execute()
                function(response.body().string())
            } catch(e: Exception) {
                function(e.toString())
            }

        }).start()
    }

    inner class auth : Authenticator {
        override fun authenticateProxy(proxy: Proxy?, response: Response?): Request? {
            return null
        }

        override fun authenticate(proxy: Proxy?, response: Response?): Request? {
            val credential = Credentials.basic("admin", cfg.pass);
            return response?.request()?.newBuilder()?.header("Authorization", credential)?.build();
        }

    }

}
