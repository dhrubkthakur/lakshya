package com.lakshya.clients

import com.lakshya.Config.Config
import com.lakshya.utils.Loggable
import org.apache.http.{HttpHeaders, HttpStatus}
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.{CloseableHttpResponse, HttpGet, HttpPost}
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils

/**
 * HTTP Client refers to Rest client and will hold all purpose of Rest operation needed for Lakshya
 */
class httpClient extends Loggable with Config{

  val httpBuilder = RequestConfig
    .custom()
    .setConnectTimeout(connectionTimeout)
    .setConnectionRequestTimeout(connectionRequestTimeout)
    .setSocketTimeout(socketTimeout)
    .build()

  def getRestContent(url: String): String = {

    val httpClient =
      HttpClientBuilder.create().setDefaultRequestConfig(httpBuilder).build()
    val get = new HttpGet(url)
    get.addHeader("Content-Type", "application/json")

    val response: CloseableHttpResponse = httpClient.execute(get)
    val entity = response.getEntity
    var content: String = null
    if (response.getStatusLine.getStatusCode != HttpStatus.SC_OK) {
      error(
        throw new IllegalStateException(
          s"status code is not OK: ${response.getStatusLine.getStatusCode}"))
    }
    if (entity != null) {
      content = EntityUtils.toString(entity, "UTF-8")
    }
    content
  }

  def postRestContent(url: String,
                      payload: String): (String, CloseableHttpResponse) = {

    val httpClient = HttpClientBuilder
      .create()
      .setDefaultRequestConfig(httpBuilder)
      .build()
    val post = new HttpPost(url)
    post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
    //post.setHeader(HttpHeaders.AUTHORIZATION, asapToken)
    post.setEntity(new StringEntity(payload))

    val response: CloseableHttpResponse = httpClient.execute(post)

    val entity = response.getEntity
    var content: String = null

    if (entity != null) {
      content = EntityUtils.toString(entity, "UTF-8")
    }
    (content, response)
  }

}
