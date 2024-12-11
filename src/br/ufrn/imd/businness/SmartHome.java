package br.ufrn.imd.businness;

import br.ufrn.imd.annotations.*;
import org.json.JSONObject;

@RequestMap(router = "/smart")
public class SmartHome {
	private String lightStat;
	private int thermostatTemp;
	
	public SmartHome() {
		this.lightStat = "OFF";
		this.thermostatTemp = 22;
	}
	
	public String getLightStat() {
		return lightStat;
	}
	public int getThermostatTemp() {
		return thermostatTemp;
	}
	
	@Get(router = "/state")
	public JSONObject stateString() {
		JSONObject result = new JSONObject();
		result.put("Light", lightStat);
		result.put("Temperature", thermostatTemp);
		return result;
	}
	
	@Post(router = "/regulate")
	public JSONObject setThermostatTemp(JSONObject jsonObject) {
		this.thermostatTemp = jsonObject.getInt("var1");
		JSONObject result = new JSONObject();
		result.put("status", "success");
		result.put("Temperature", thermostatTemp);
		return result;
	}
	
	@Post(router = "/switch")
	public JSONObject lightSwitch() {
		switch(this.lightStat) {
		case "ON":
			this.lightStat = "OFF";
			break;
		case "OFF":
			this.lightStat = "ON";
			break;
		}
		JSONObject result = new JSONObject();
		result.put("status", "success");
		result.put("Light", lightStat);
		return result;
	}
	
	@Put(router = "/update")
    public JSONObject updateSettings(JSONObject jsonObject) {
        if (jsonObject.has("Light")) {
            this.lightStat = jsonObject.getString("Light");
        }
        if (jsonObject.has("Temperature")) {
            this.thermostatTemp = jsonObject.getInt("Temperature");
        }
        JSONObject result = new JSONObject();
        result.put("status", "success");
        result.put("Light", lightStat);
        result.put("Temperature", thermostatTemp);
        return result;
    }

    @Delete(router = "/reset")
    public JSONObject resetSettings() {
        this.lightStat = "OFF";
        this.thermostatTemp = 22;
        JSONObject result = new JSONObject();
        result.put("status", "success");
        result.put("message", "Settings have been reset to default");
        return result;
    }
}
