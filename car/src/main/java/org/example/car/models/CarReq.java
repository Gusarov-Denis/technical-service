
package org.example.car.models;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Car object schema
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "regNumber",
    "login",
    "motorTypeId",
    "markModelId"
})
@Generated("jsonschema2pojo")
public class CarReq {

    /**
     * Регистрационный номер
     * (Required)
     * 
     */
    @JsonProperty("regNumber")
    @JsonPropertyDescription("\u0420\u0435\u0433\u0438\u0441\u0442\u0440\u0430\u0446\u0438\u043e\u043d\u043d\u044b\u0439 \u043d\u043e\u043c\u0435\u0440")
    private String regNumber;
    /**
     * Логин клиента
     * (Required)
     * 
     */
    @JsonProperty("login")
    @JsonPropertyDescription("\u041b\u043e\u0433\u0438\u043d \u043a\u043b\u0438\u0435\u043d\u0442\u0430")
    private String login;
    /**
     * Ид типа мотора.
     * (Required)
     * 
     */
    @JsonProperty("motorTypeId")
    @JsonPropertyDescription("\u0418\u0434 \u0442\u0438\u043f\u0430 \u043c\u043e\u0442\u043e\u0440\u0430.")
    private UUID motorTypeId;
    /**
     * Ид марки модели.
     * (Required)
     * 
     */
    @JsonProperty("markModelId")
    @JsonPropertyDescription("\u0418\u0434 \u043c\u0430\u0440\u043a\u0438 \u043c\u043e\u0434\u0435\u043b\u0438.")
    private UUID markModelId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * Регистрационный номер
     * (Required)
     * 
     */
    @JsonProperty("regNumber")
    public String getRegNumber() {
        return regNumber;
    }

    /**
     * Регистрационный номер
     * (Required)
     * 
     */
    @JsonProperty("regNumber")
    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
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
     * Ид типа мотора.
     * (Required)
     * 
     */
    @JsonProperty("motorTypeId")
    public UUID getMotorTypeId() {
        return motorTypeId;
    }

    /**
     * Ид типа мотора.
     * (Required)
     * 
     */
    @JsonProperty("motorTypeId")
    public void setMotorTypeId(UUID motorTypeId) {
        this.motorTypeId = motorTypeId;
    }

    /**
     * Ид марки модели.
     * (Required)
     * 
     */
    @JsonProperty("markModelId")
    public UUID getMarkModelId() {
        return markModelId;
    }

    /**
     * Ид марки модели.
     * (Required)
     * 
     */
    @JsonProperty("markModelId")
    public void setMarkModelId(UUID markModelId) {
        this.markModelId = markModelId;
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
        sb.append(CarReq.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("regNumber");
        sb.append('=');
        sb.append(((this.regNumber == null)?"<null>":this.regNumber));
        sb.append(',');
        sb.append("login");
        sb.append('=');
        sb.append(((this.login == null)?"<null>":this.login));
        sb.append(',');
        sb.append("motorTypeId");
        sb.append('=');
        sb.append(((this.motorTypeId == null)?"<null>":this.motorTypeId));
        sb.append(',');
        sb.append("markModelId");
        sb.append('=');
        sb.append(((this.markModelId == null)?"<null>":this.markModelId));
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
        result = ((result* 31)+((this.regNumber == null)? 0 :this.regNumber.hashCode()));
        result = ((result* 31)+((this.markModelId == null)? 0 :this.markModelId.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.login == null)? 0 :this.login.hashCode()));
        result = ((result* 31)+((this.motorTypeId == null)? 0 :this.motorTypeId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CarReq) == false) {
            return false;
        }
        CarReq rhs = ((CarReq) other);
        return ((((((this.regNumber == rhs.regNumber)||((this.regNumber!= null)&&this.regNumber.equals(rhs.regNumber)))&&((this.markModelId == rhs.markModelId)||((this.markModelId!= null)&&this.markModelId.equals(rhs.markModelId))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.login == rhs.login)||((this.login!= null)&&this.login.equals(rhs.login))))&&((this.motorTypeId == rhs.motorTypeId)||((this.motorTypeId!= null)&&this.motorTypeId.equals(rhs.motorTypeId))));
    }

}
