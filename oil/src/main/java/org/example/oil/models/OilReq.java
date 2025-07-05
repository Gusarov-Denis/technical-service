
package org.example.oil.models;

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
 * Oil object schema
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "oilTypeId",
    "oilViscosityId"
})
@Generated("jsonschema2pojo")
public class OilReq {

    /**
     * Наименование
     * (Required)
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("\u041d\u0430\u0438\u043c\u0435\u043d\u043e\u0432\u0430\u043d\u0438\u0435")
    private String name;
    /**
     * Ид типа масла.
     * (Required)
     * 
     */
    @JsonProperty("oilTypeId")
    @JsonPropertyDescription("\u0418\u0434 \u0442\u0438\u043f\u0430 \u043c\u0430\u0441\u043b\u0430.")
    private UUID oilTypeId;
    /**
     * Ид вязкости масла.
     * (Required)
     * 
     */
    @JsonProperty("oilViscosityId")
    @JsonPropertyDescription("\u0418\u0434 \u0432\u044f\u0437\u043a\u043e\u0441\u0442\u0438 \u043c\u0430\u0441\u043b\u0430.")
    private UUID oilViscosityId;
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
     * Ид типа масла.
     * (Required)
     * 
     */
    @JsonProperty("oilTypeId")
    public UUID getOilTypeId() {
        return oilTypeId;
    }

    /**
     * Ид типа масла.
     * (Required)
     * 
     */
    @JsonProperty("oilTypeId")
    public void setOilTypeId(UUID oilTypeId) {
        this.oilTypeId = oilTypeId;
    }

    /**
     * Ид вязкости масла.
     * (Required)
     * 
     */
    @JsonProperty("oilViscosityId")
    public UUID getOilViscosityId() {
        return oilViscosityId;
    }

    /**
     * Ид вязкости масла.
     * (Required)
     * 
     */
    @JsonProperty("oilViscosityId")
    public void setOilViscosityId(UUID oilViscosityId) {
        this.oilViscosityId = oilViscosityId;
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
        sb.append(OilReq.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("oilTypeId");
        sb.append('=');
        sb.append(((this.oilTypeId == null)?"<null>":this.oilTypeId));
        sb.append(',');
        sb.append("oilViscosityId");
        sb.append('=');
        sb.append(((this.oilViscosityId == null)?"<null>":this.oilViscosityId));
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
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.oilTypeId == null)? 0 :this.oilTypeId.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.oilViscosityId == null)? 0 :this.oilViscosityId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OilReq) == false) {
            return false;
        }
        OilReq rhs = ((OilReq) other);
        return (((((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.oilTypeId == rhs.oilTypeId)||((this.oilTypeId!= null)&&this.oilTypeId.equals(rhs.oilTypeId))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.oilViscosityId == rhs.oilViscosityId)||((this.oilViscosityId!= null)&&this.oilViscosityId.equals(rhs.oilViscosityId))));
    }

}
