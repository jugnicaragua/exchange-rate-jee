package ni.jug.exchangerate.restful;

import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import ni.jug.cb.exchangerate.Cookie;
import ni.jug.cb.exchangerate.ExecutionContext;

/**
 *
 * @author aalaniz
 */
@Path("bdfCookies")
public class BdfCookieResource {

    @GET
    public JsonArray findCookies() {
        JsonArrayBuilder cookiesBuilder = Json.createArrayBuilder();
        for (Map.Entry<String, String> cookieEntry : ExecutionContext.getInstance().bdfCookies().entrySet()) {
            JsonObjectBuilder cookieBuilder = Json.createObjectBuilder();
            cookieBuilder.add("name", cookieEntry.getKey())
                    .add("value", cookieEntry.getValue());
            cookiesBuilder.add(cookieBuilder);
        }
        return cookiesBuilder.build();
    }

    @PUT
    public void updateCookie(JsonObject cookie) {
        Cookie cookieWithDefaults = new Cookie(cookie.getString("name", "no-name"), cookie.getString("value", "no-value"));
        ExecutionContext.getInstance().addOrReplaceBdfCookie(cookieWithDefaults);
    }

}
