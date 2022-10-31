package utils;

import com.google.gson.*;

import java.io.FileReader;

public class ReadJsonFile {
   // public JSONParser jsonParser=new JSONParser();
    static JsonElement root;
    public static JsonObject object;

    public static Object[][] getData(String jsonFilePath) {
        //complete API tests data in a JsonObject

        object = getApiJson(jsonFilePath);
        JsonArray cases = object.get("test_cases").getAsJsonArray();
        Object[][] data = new Object[cases.size()][1];

        JsonObject baseObjectValue = object.get("test_case").getAsJsonObject();
        for (int i = 0; i < cases.size(); i++) {
            JsonObject o = null;
            JsonObject expectedResponse = cases.get(i).getAsJsonObject().get("expectedResponse").getAsJsonObject();
            int expectedResponseCode = cases.get(i).getAsJsonObject().get("expectedResponseCode").getAsInt();
            String testCaseDesc = cases.get(i).getAsJsonObject().get("testCaseDesc").getAsString();
            if (cases.get(i).getAsJsonObject().get("value") != null) {
                o = cases.get(i).getAsJsonObject().get("value").getAsJsonObject();
            }

            JsonObject caseObject = baseObjectValue.deepCopy();
            if (o != null) {
                for (String key : o.keySet()) {
                    String value = o.get(key).getAsString();
                    caseObject.addProperty(key, value);
                }
                caseObject.add("expectedResponse", expectedResponse);
                caseObject.addProperty("expectedResponseCode", expectedResponseCode);
                caseObject.addProperty("testCaseDesc", testCaseDesc);
            }

            data[i][0] = caseObject;
        }
        return data;
    }

    public static JsonObject getApiJson(String jsonFilePath) {
        try {
            root = new JsonParser().parse(new FileReader(jsonFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //complete API tests data in a JsonObject
        JsonObject object = root.getAsJsonObject();
        return object;
    }
    public static JsonObject parseStringToJsonObject(String jsonString) {
        Gson gson = new Gson();
        JsonElement element = gson.fromJson(jsonString, JsonElement.class);
        JsonObject jsonObject = element.getAsJsonObject();
        return jsonObject;
    }
    public static void main(String args[]){
        JsonObject json=getApiJson(System.getProperty("user.dir")+"/src/commonServices/resources/TestApi1.json");
        System.out.println("JsonPath="+json.toString());
        String fName=json.get("phoneNumbers").getAsJsonArray().get(0).getAsJsonObject().get("number").toString();
        System.out.println(fName);
    }
}
