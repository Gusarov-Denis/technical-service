
package org.example.serv.models;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.processing.Generated;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;


/**
 * TO object schema
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "login",
    "oilId",
    "carId",
    "dateTo",
    "mileage",
    "toStatusId"
})
@Generated("jsonschema2pojo")
public class ToReq {

    /**
     * Наименование
     * (Required)
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("\u041d\u0430\u0438\u043c\u0435\u043d\u043e\u0432\u0430\u043d\u0438\u0435")
    private String name;
    /**
     * Логин клиента
     * (Required)
     * 
     */
    @JsonProperty("login")
    @JsonPropertyDescription("\u041b\u043e\u0433\u0438\u043d \u043a\u043b\u0438\u0435\u043d\u0442\u0430")
    private String login;
    /**
     * Ид масла.
     * (Required)
     * 
     */
    @JsonProperty("oilId")
    @JsonPropertyDescription("\u0418\u0434 \u043c\u0430\u0441\u043b\u0430.")
    private UUID oilId;
    /**
     * Ид машины.
     * (Required)
     * 
     */
    @JsonProperty("carId")
    @JsonPropertyDescription("\u0418\u0434 \u043c\u0430\u0448\u0438\u043d\u044b.")
    private UUID carId;
    /**
     * format yyyy-MM-dd'T'HH:mm:ss.SSSZ
     * 
     */
    @JsonProperty("dateTo")
    @JsonPropertyDescription("format yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime dateTo;
    /**
     * Пробег
     * (Required)
     * 
     */
    @JsonProperty("mileage")
    @JsonPropertyDescription("\u041f\u0440\u043e\u0431\u0435\u0433")
    private Integer mileage = 0;
    /**
     * Ид статуса.
     * (Required)
     * 
     */
    @JsonProperty("toStatusId")
    @JsonPropertyDescription("\u0418\u0434 \u0441\u0442\u0430\u0442\u0443\u0441\u0430.")
    private UUID toStatusId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * Наименование
     * (Required)
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Наименование
     * (Required)
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Логин клиента
     * (Required)
     * 
     */
    @JsonProperty("login")
    public String getLogin() {
        return login;
    }

    /**
     * Логин клиента
     * (Required)
     * 
     */
    @JsonProperty("login")
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Ид масла.
     * (Required)
     * 
     */
    @JsonProperty("oilId")
    public UUID getOilId() {
        return oilId;
    }

    /**
     * Ид масла.
     * (Required)
     * 
     */
    @JsonProperty("oilId")
    public void setOilId(UUID oilId) {
        this.oilId = oilId;
    }

    /**
     * Ид машины.
     * (Required)
     * 
     */
    @JsonProperty("carId")
    public UUID getCarId() {
        return carId;
    }

    /**
     * Ид машины.
     * (Required)
     * 
     */
    @JsonProperty("carId")
    public void setCarId(UUID carId) {
        this.carId = carId;
    }

    /**
     * format yyyy-MM-dd'T'HH:mm:ss.SSSZ
     * 
     */
    @JsonProperty("dateTo")
    public ZonedDateTime getDateTo() {
        return dateTo;
    }

    /**
     * format yyyy-MM-dd'T'HH:mm:ss.SSSZ
     * 
     */
    @JsonProperty("dateTo")
    public void setDateTo(ZonedDateTime dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * Пробег
     * (Required)
     * 
     */
    @JsonProperty("mileage")
    public Integer getMileage() {
        return mileage;
    }

    /**
     * Пробег
     * (Required)
     * 
     */
    @JsonProperty("mileage")
    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    /**
     * Ид статуса.
     * (Required)
     * 
     */
    @JsonProperty("toStatusId")
    public UUID getToStatusId() {
        return toStatusId;
    }

    /**
     * Ид статуса.
     * (Required)
     * 
     */
    @JsonProperty("toStatusId")
    public void setToStatusId(UUID toStatusId) {
        this.toStatusId = toStatusId;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ToReq.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("login");
        sb.append('=');
        sb.append(((this.login == null)?"<null>":this.login));
        sb.append(',');
        sb.append("oilId");
        sb.append('=');
        sb.append(((this.oilId == null)?"<null>":this.oilId));
        sb.append(',');
        sb.append("carId");
        sb.append('=');
        sb.append(((this.carId == null)?"<null>":this.carId));
        sb.append(',');
        sb.append("dateTo");
        sb.append('=');
        sb.append(((this.dateTo == null)?"<null>":this.dateTo));
        sb.append(',');
        sb.append("mileage");
        sb.append('=');
        sb.append(((this.mileage == null)?"<null>":this.mileage));
        sb.append(',');
        sb.append("toStatusId");
        sb.append('=');
        sb.append(((this.toStatusId == null)?"<null>":this.toStatusId));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.oilId == null)? 0 :this.oilId.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.dateTo == null)? 0 :this.dateTo.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.login == null)? 0 :this.login.hashCode()));
        result = ((result* 31)+((this.carId == null)? 0 :this.carId.hashCode()));
        result = ((result* 31)+((this.mileage == null)? 0 :this.mileage.hashCode()));
        result = ((result* 31)+((this.toStatusId == null)? 0 :this.toStatusId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ToReq) == false) {
            return false;
        }
        ToReq rhs = ((ToReq) other);
        return (((((((((this.oilId == rhs.oilId)||((this.oilId!= null)&&this.oilId.equals(rhs.oilId)))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.dateTo == rhs.dateTo)||((this.dateTo!= null)&&this.dateTo.equals(rhs.dateTo))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.login == rhs.login)||((this.login!= null)&&this.login.equals(rhs.login))))&&((this.carId == rhs.carId)||((this.carId!= null)&&this.carId.equals(rhs.carId))))&&((this.mileage == rhs.mileage)||((this.mileage!= null)&&this.mileage.equals(rhs.mileage))))&&((this.toStatusId == rhs.toStatusId)||((this.toStatusId!= null)&&this.toStatusId.equals(rhs.toStatusId))));
    }

}
