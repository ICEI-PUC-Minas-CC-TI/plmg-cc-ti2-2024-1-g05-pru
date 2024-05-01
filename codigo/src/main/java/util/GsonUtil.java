package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GsonUtil {
  private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
  private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  private static final JsonSerializer<LocalDate> dateSerializer =
    (src, typeOfSrc, context) -> new JsonPrimitive(src.format(dateFormatter));
  private static final JsonDeserializer<LocalDate> dateDeserializer =
    (json, typeOfT, context) -> LocalDate.parse(json.getAsString(), dateFormatter);

  private static final JsonSerializer<LocalDateTime> dateTimeSerializer =
    (src, typeOfSrc, context) -> new JsonPrimitive(src.format(dateTimeFormatter));
  private static final JsonDeserializer<LocalDateTime> dateTimeDeserializer =
    (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), dateTimeFormatter);


  public static final Gson GSON = new GsonBuilder()
    .registerTypeAdapter(LocalDate.class, dateSerializer)
    .registerTypeAdapter(LocalDate.class, dateDeserializer)
    .registerTypeAdapter(LocalDateTime.class, dateTimeSerializer)
    .registerTypeAdapter(LocalDateTime.class, dateTimeDeserializer)
    .registerTypeAdapter(Paciente.class, (JsonDeserializer<Paciente>) Paciente::deserialize)
    .registerTypeAdapter(Medico.class, (JsonDeserializer<Medico>) Medico::deserialize)
    .create();
}
