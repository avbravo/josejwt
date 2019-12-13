/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.josejwt;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import javax.ws.rs.core.Response;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

/**
 *
 * @author avbravo
 */
public class MyConsumer {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public void run() throws Exception {

        try {

            String token = getJWT();
         //   testJWT(token);
            testRolesMethod();
        } finally {
            close();
        }
    }

    private void close() throws IOException {
        httpClient.close();
    }

    private String getJWT() {
        String token = "";
        try {

            HttpPost post = new HttpPost("http://localhost:8080/jwt-provider/auth");

            // add request parameter, form parameters
            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("username", "user1"));
            urlParameters.add(new BasicNameValuePair("password", "user1"));
            urlParameters.add(new BasicNameValuePair("issuer", "http://apuntesdejava.com"));
            urlParameters.add(new BasicNameValuePair("public-key", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAprklfylWG4UCFvI4TIXsHB3dZlig1zlsOZWqEqrD3T9dV+PA5XKqL3sujpAiXRZM2fR7Qc8V9VcnuRvph+ihNs77imIKAH29+gPoB4Aq48iiUPWU5B7AzmJqLVgdYMuzYPy1emfXyk2oYXoHnc+6eGJSHidb5KqnM3e662ZTDTahXAS1cQKvYXqGxExaI+DSHEwTglGN+n4suUkW4Vt0KOYkN0gFPCf4wKbXZZfiosF59cjAQ/YVE2EwXQ8KCDGpTh3Uy4vkz+wX3cmEOAzPU0SddFXr3u5Zm3xf1BCC1EqLsGqbx2vOOeBNW4lOrRX2HpgBjM+ZYS0ZjtOwC+tc/QIDAQAB"));

            post.setEntity(new UrlEncodedFormEntity(urlParameters));

            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                    CloseableHttpResponse response = httpClient.execute(post)) {
                token = EntityUtils.toString(response.getEntity());
                System.out.println("---------------------------");
                System.out.println("EL JWT es");
                System.out.println(token);
                System.out.println("---------------------------");
                System.out.println(EntityUtils.toString(response.getEntity()));
            }
        } catch (Exception ex) {
            System.out.println("getJWT() " + ex.getLocalizedMessage());
        }
        return token;
    }

    public void conectarse() {
        try {
            HttpAuthenticationFeature autentificacion = HttpAuthenticationFeature.basic("myusername", "mypassword");

            Client client = ClientBuilder.newClient();
            client.register(autentificacion);

            WebTarget target = client.target("http://localhost:8080/resources/paises");

            GenericType<List<Paises>> paises = new GenericType<List<Paises>>() {
            };

            List<Paises> listaPaises = target.request(MediaType.APPLICATION_JSON).get(paises);
            for (Paises n : listaPaises) {
                System.out.println(n.getId());
                System.out.println(n.getPais());
            }

        } catch (Exception e) {
            System.out.println("main() " + e.getLocalizedMessage());
        }
    }

//    public void autentificationJWT() {
//        try {
//            
//            Client client=Client.create();          
//WebResource webresource=client.resource("http://localhost:8080/web-app/resources/ping/secure");
//ClientResponse clientResponse=webresource.header("authorization", accessToken)
//    .accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
//
//            ClientConfig clientConfig = new ClientConfig();
//            Client client = ClientBuilder.newClient(clientConfig);
//            WebTarget webTarget = client.target("http://localhost:8080/MyApp/customer/");
//            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "yoursecret key");
//response = invocationBuilder.get();
//            output = response.readEntity(String.class);
//            
////            Client client = ClientBuilder.newClient();
////WebTarget target = client.target("http:localhost:8080/web-app/resources/ping/secure ")
////    .request("...")     
////    .header(HttpHeaders.AUTHORIZATION, getJWT());
////
////Feature feature = OAuth2ClientSupport.feature("YOUR_BEARER_TOKEN");
////client.register(feature);
////
////ClientResponse clientResponse=target.header("authorization", getJWT())
////    .accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
//
//        } catch (Exception e) {
//            System.out.println(" " + e.getLocalizedMessage());
//        }
//    }
    public void testJWT(String token) {
        try {
            System.out.println("---------------------------------------------");
            System.out.println("    testJWT");

            Encoder BASE64_ENCODER = Base64.getEncoder();
            String REST_SERVICE_URL = "http://localhost:8080/web-app/resources/ping/secure";

            Client client = ClientBuilder.newClient();

            WebTarget target = client.target(REST_SERVICE_URL).path("{token}").resolveTemplate("token", token);
            Builder builder = target.request(MediaType.APPLICATION_JSON);
            Invocation invocation = builder.buildGet();

            // Invocar servicio
            String json = invocation.invoke(String.class);
            System.out.println("---> Json" + json);

            // Documentos en formato Base64
            //   Map<Long, String> documentosBase64 = parseJson(json);
            // Documentos a retornar
//            Map<Long, byte[]> documentos = new HashMap<>();
//
//            for (Long id : documentosBase64.keySet()) {
//                String base64 = documentosBase64.get(id);
//                documentos.put(id, BASE64_DECODER.decode(base64));
//            }
        } catch (Exception e) {
            System.out.println("testJWT " + e.getLocalizedMessage());
        }
    }

    private static Response makeConnection(
            String method, String urlString, String payload, String jwt)
            throws IOException, GeneralSecurityException {

        // Setup connection
        System.out.println("Creating connection - Method: " + method + ", URL: " + urlString);

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(urlString);

        Invocation.Builder invoBuild = target.request(MediaType.APPLICATION_JSON_TYPE);

        if (jwt != null) {
            invoBuild.header("Authorization", jwt);
        }
        if (payload != null) {
            System.out.println("Request Payload: " + payload);
            Entity<String> data = Entity.entity(payload, MediaType.APPLICATION_JSON_TYPE);
            return invoBuild.build(method, data).invoke();
        } else {
            return invoBuild.build(method).invoke();
        }
    }
    
    
//    public Response processRequest(String url, String method, String payload)
//    throws GeneralSecurityException, IOException {
//  Client client = ClientBuilder.newClient();
//  WebTarget target = client.target(url);
//  Builder builder = target.request();
//  builder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
//  builder.header(
//      HttpHeaders.AUTHORIZATION,
//      "Bearer "
//          + new JWTVerifier()
//              .createJWT("fred", new HashSet<String>(Arrays.asList("orchestrator"))));
//  return (payload != null)
//      ? builder.build(method, Entity.json(payload)).invoke()
//      : builder.build(method).invoke();
//}
// 
    
    private Response processRequest(String url, String method, String payload, String authHeader) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Builder builder = target.request();
        builder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        if (authHeader != null) {
            builder.header(HttpHeaders.AUTHORIZATION, authHeader);
        }
        return (payload != null)
            ? builder.build(method, Entity.json(payload)).invoke()
            : builder.build(method).invoke();
    }
    
    public void testRolesMethod() throws Exception {
    
        
           Client client = ClientBuilder.newClient();
        WebTarget echoEndpointTarget = ClientBuilder.newClient().target("http://localhost:8080/web-app/resources/ping/secure");
        
        Response response = echoEndpointTarget.request(TEXT_PLAIN).header(HttpHeaders.AUTHORIZATION, "Bearer " + getJWT()).get();
        
        if(response.getStatus() ==HttpURLConnection.HTTP_OK){
        String reply = response.readEntity(String.class);
            System.out.println("ok "+reply);
    }else{
            System.out.println("no se conecto");
        }
        
        // Must return hello, user={token upn claim}
       
    }
}
