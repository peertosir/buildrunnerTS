package dev.peertosir.difftoolui.service;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiffService {
    public Optional<String> MakeDiff(String rawJson1, String rawJson2, String ignoreFields) {

        String[] jsons = getFilteredJsons(rawJson1, rawJson2, ignoreFields);
        // Как же хочется деструктуризацию массивов в Java :(
        String json1 = jsons[0];
        String json2 = jsons[1];

        Optional<String> returnValue = Optional.empty();
        try {
            JSONCompareResult diffResult = JSONCompare.compareJSON(json1, json2, JSONCompareMode.LENIENT);
            returnValue  = Optional.ofNullable(diffResult.toString());
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        }
        return returnValue;
    }

    private String[] getFilteredJsons(String rawJson1, String rawJson2, String ignoreFieldsString) {
        DocumentContext context1 = JsonPath.parse(rawJson1);
        DocumentContext context2 = JsonPath.parse(rawJson2);
        if (ignoreFieldsString.trim().length() > 0) {
            for (String ignoreField : ignoreFieldsString.split(",")) {
                context1.delete(ignoreField);
                context2.delete(ignoreField);
            }
        }
        return new String[] {context1.jsonString(), context2.jsonString()};
    }
}
