package com.github.tvbox.osc.bbox.api;

import android.content.Context;
import android.widget.Toast;

import com.github.tvbox.osc.bbox.util.HawkConfig;
import com.github.tvbox.osc.bbox.util.LOG;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.HashMap;

public class StoreApiConfig {
    private static StoreApiConfig instance;

    public static StoreApiConfig get() {
        if (instance == null) {
            synchronized (StoreApiConfig.class) {
                if (instance == null) {
                    instance = new StoreApiConfig();
                }
            }
        }
        return instance;
    }

    public void MyRequest(String url, StoreApiConfigCallback callback) {
        OkGo.<String>get(url).headers("User-Agent", "okhttp/3.15").headers("Accept",
                "text/html," + "application/xhtml+xml,application/xml;q=0.9,image/avif," +
                        "image/webp,image/apng," + "*/*;q=0.8,application/signed-exchange;v=b3;" +
                        "q=0.9").execute(new AbsCallback<String>() {
            @Override
            public void onSuccess(Response<String> response) {
                callback.success(response.body());
            }

            @Override
            public String convertResponse(okhttp3.Response response) throws Throwable {
                assert response.body() != null;
                return response.body().string();
            }
        });
    }


    public void Subscribe(Context context) {

        Toast.makeText(context, "开始获取订阅", Toast.LENGTH_SHORT).show();

        // 获取多仓地址
        HashMap<String, String> storeMap = Hawk.get(HawkConfig.STORE_API_MAP, new HashMap<>());
        String storeName = Hawk.get(HawkConfig.STORE_API_NAME, "爬的别人的仓库");
        if (storeMap.isEmpty()) {
            Toast.makeText(context, "仓库为空，使用默认仓库", Toast.LENGTH_SHORT).show();
            storeMap.put("爬的别人的仓库", "https://raw.iqiq.io/mlabalabala/TVResource/main/boxCfg/storeHouse.json");
        }

        String storeUrl = storeMap.get(storeName);

        LOG.i("订阅仓库地址：" + storeUrl);

        // 处理多仓获取多节点
        StoreApiConfig.get().MyRequest(storeUrl, sourceJson -> {

            JsonObject json = new Gson().fromJson(sourceJson, JsonObject.class);

            if (null == json.get("urls")) {
                // 仓库链接，先获取多源，再获取多配置
                // 只获取仓库的第一个仓库

                JsonObject infoJson = new Gson().fromJson(sourceJson, JsonObject.class);
                JsonObject storeHouse = infoJson.get("storeHouse").getAsJsonArray().get(0).getAsJsonObject();

                String sourceName = storeHouse.get("sourceName").getAsString();
                String sourceUrl = storeHouse.get("sourceUrl").getAsString();

                Toast.makeText(context, sourceName, Toast.LENGTH_SHORT).show();

                // 获取单仓中的配置线路
                StoreApiConfig.get().MyRequest( sourceUrl, urlsJson -> {
                    String result = MutiUrl(urlsJson);
                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                });
            }
            else {
                // 单源链接，直接请求
                // 获取单源中的配置线路

                String result = MutiUrl(sourceJson);
                Toast.makeText(context, "单源链接，" + result, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String MutiUrl(String urlsJson) {

//        ArrayList<String> history = Hawk.get(HawkConfig.API_NAME_HISTORY, new ArrayList<>());
//        HashMap<String, String> map = Hawk.get(HawkConfig.API_MAP, new HashMap<>());

        ArrayList<String> history = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();

        JsonObject urlsObject = new Gson().fromJson(urlsJson, JsonObject.class);

        JsonArray urlsObjects = urlsObject.get("urls").getAsJsonArray();


        for (JsonElement element : urlsObjects) {
            JsonObject obj = element.getAsJsonObject();
            String name = obj.get("name").getAsString();
            String url = obj.get("url").getAsString();
            history.add(name);
            map.put(name, url);
        }
//        Hawk.put(HawkConfig.API_NAME, history.get(0));
//        Hawk.put(HawkConfig.API_URL, map.get(history.get(0)));
        Hawk.put(HawkConfig.API_NAME_HISTORY, history);
        Hawk.put(HawkConfig.API_MAP, map);
        return "订阅结束，点击线路可切换";
    }

    public interface StoreApiConfigCallback {
        void success(String json);
    }
}
