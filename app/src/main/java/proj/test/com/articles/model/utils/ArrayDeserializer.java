package proj.test.com.articles.model.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.attr.data;
import static android.R.id.list;

/**
 * Created by Kolot Liza on 2/8/18.
 */

public class ArrayDeserializer implements JsonDeserializer<List<String>>
    {
        @Override
        public List<String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            List<String> list = new ArrayList<>();
                if (json.isJsonArray()){
                    JsonArray data = json.getAsJsonArray();
                    for (int i = 1; i < data.size(); i++) {
                        list.add(data.get(i).getAsString());
                    }
            }
            else
             {
                String element = json.getAsString();
                list.add(element);
            }

            return list;
        }
    }

