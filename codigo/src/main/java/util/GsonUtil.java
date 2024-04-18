package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GsonUtil {
  private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

  private static final JsonSerializer<LocalDate> serializer =
    (src, typeOfSrc, context) -> new JsonPrimitive(src.format(formatter));
  private static final JsonDeserializer<LocalDate> deserializer =
    (json, typeOfT, context) -> LocalDate.parse(json.getAsString(), formatter);

  public static final Gson GSON = new GsonBuilder()
    .registerTypeAdapter(LocalDate.class, serializer)
    .registerTypeAdapter(LocalDate.class, deserializer)
    .create();
}