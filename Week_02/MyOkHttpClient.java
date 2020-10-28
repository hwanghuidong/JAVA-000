import okhttp3.*;

import java.io.IOException;

public class MyOkHttpClient {
    public static void main(String[] args) {

        String strUrl = "http://127.0.0.1";

        Request request = new Request.Builder().url(strUrl).build();

        OkHttpClient myOkClient = new OkHttpClient();

        myOkClient.newCall(request)
                .enqueue( new Callback() {
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);}
                        else {
                            System.out.println(response.toString());}
                    }
                });
    }
}
