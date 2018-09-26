package com.example.user.afteryousiami.DAO;

import android.content.res.AssetManager;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Connection {

    /***
     * Sends a GET request to the api and returns the json result as a string
     * @param url url of the request including the parameters
     * @param keyValuePair null if the key is included in the parameter, else key in the key and value in this format <key>,<value>
     * @return string of the json response
     * @throws Exception if there is a connection error
     */
    public static String sendGet(String url, String keyValuePair) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //get the key and value (mainly used for SITA json requests)
        if (keyValuePair != null) {
            String key = keyValuePair.substring(0, keyValuePair.indexOf(','));
            String value = keyValuePair.substring(keyValuePair.indexOf(',') + 1);
            con.setRequestProperty(key, value);
        }
        con.setConnectTimeout(12000);
        con.setReadTimeout(12000);
        con.connect();
        int responseCode = con.getResponseCode();

        printDebug("\nSending 'GET' request to URL : " + url);
        printDebug("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    // HTTP POST request
    public void sendPost() throws Exception {

        String url = "https://apigw.singaporeair.com/appchallenge/api/krisflyer/profile";

        HttpClient client = createHttpClient();
        HttpPost post = new HttpPost(url);

        // add header
//        post.setHeader("User-Agent", "Mozilla/5.0");
        post.setHeader("Content-Type", "application/json");
        post.setHeader("apikey", "aghk73f4x5haxeby7z24d2rc");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
//        urlParameters.add(new BasicNameValuePair("flightNo", "SQ890"));
//        urlParameters.add(new BasicNameValuePair("flightDate", "2018-07-20"));
        urlParameters.add(new BasicNameValuePair("krisflyerNumber", "5918588234"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        printDebug("\nSending 'POST' request to URL : " + url);
        printDebug("Post parameters : " + post.getEntity());
        printDebug("Response Code : " + response.getStatusLine().getStatusCode());

        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.wire", "DEBUG");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.impl.conn", "DEBUG");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.impl.client", "DEBUG");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.client", "DEBUG");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "DEBUG");

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        printDebug(result.toString());
    }

    private HttpClient createHttpClient() {
        try {
            // Setup a custom SSL Factory object which simply ignore the certificates validation and accept all type of self signed certificates
            SSLSocketFactory sslFactory = new SimpleSSLSocketFactory(null);
            sslFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            // Enable HTTP parameters
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

//        HttpParams params = new BasicHttpParams();
//        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
//        HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
//        HttpProtocolParams.setUseExpectContinue(params, true);

            SchemeRegistry schReg = new SchemeRegistry();
            schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            schReg.register(new Scheme("https", sslFactory, 443));
            ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);


            return new DefaultHttpClient(conMgr, params);
        } catch (Exception e) {
            printError(e);
        }

        return null;
    }

    /***
     * temporary method used to read JSON file locally instead of thru the server due to connection problems
     * @param requestFileName name of the file
     * @param asset  AssetManager that is passed from the activity to this method so that the program can read the files in the /asset folder
     * @return json in string
     */
    public static String getPostResponseFromExternalFile(String requestFileName, AssetManager asset) throws IOException {
        String result = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(asset.open(requestFileName)));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                result += mLine;
            }
        } catch (IOException e) {
            printError(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    printError(e);
                }
            }
        }
        return result;

    }

    private static void printDebug(Object message) {
        Log.d("Connection.java", message.toString());
    }

    private static void printError(Exception e) {
        Log.e("Connection.java", "error", e);
    }

    public static void main(String[] args) throws Exception {
        String request = "https://tih-api.stb.gov.sg/content/v1/tag?apikey=t5XblPNbklytRwIx6yGIx1RE2cfiucBW";
        Connection.sendGet(request, null);
    }
}
