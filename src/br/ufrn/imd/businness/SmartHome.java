package br.ufrn.imd.businness;

import br.ufrn.imd.annotations.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.json.JSONObject;

@RequestMap(router = "/smart")
public class SmartHome {
	private final AtomicReference<String> lightStat;
	private final AtomicInteger thermostatTemp;
	
	public SmartHome() {
		this.lightStat = new AtomicReference<>("OFF");
        this.thermostatTemp = new AtomicInteger(22);
	}
	
	private String getLightStat() {
		return lightStat.get();
	}
	private int getThermostatTemp() {
		return thermostatTemp.get();
	}
	private void setLightStat(String stat) {
		lightStat.set(stat);
	}
	private void setThermostatTemp(int temp) {
		thermostatTemp.set(temp);
	}
	
	@Get(router = "/state")
	public JSONObject stateString() {
		return createJsonResponse("Light", getLightStat(), "Temperature", getThermostatTemp());
	}
	
	@Post(router = "/regulate")
	public JSONObject setThermostatTemp(JSONObject jsonObject) {
		setThermostatTemp(jsonObject.getInt("var1"));
		return createJsonResponse("status", "success","Temperature", getThermostatTemp());
	}
	
	@Post(router = "/switch")
	public  JSONObject lightSwitch() {
		setLightStat(getLightStat().equals("ON")? "OFF" : "ON");
		return createJsonResponse("status", "success", "Light", getLightStat());
	}
	
	@Put(router = "/update")
    public JSONObject updateSettings(JSONObject jsonObject) {
        if (jsonObject.has("Light")) {
        	setLightStat(jsonObject.getString("Light"));
        }
        if (jsonObject.has("Temperature")) {
        	setThermostatTemp(jsonObject.getInt("Temperature"));
        }
        return createJsonResponse("status", "success","Light", getLightStat(),"Temperature", getThermostatTemp());
    }

    @Delete(router = "/reset")
    public JSONObject resetSettings() {
    	setLightStat("OFF");
    	setThermostatTemp(22);
        return createJsonResponse("status", "success","message", "Settings have been reset to default");
    }
    
    private JSONObject createJsonResponse(Object... keyValuePairs) {
    	JSONObject result = new JSONObject();
    	for(int i = 0; i < keyValuePairs.length; i += 2) {
    		if (keyValuePairs[i] instanceof String) {
                result.put((String) keyValuePairs[i], keyValuePairs[i + 1]);
            }
    	}
    	return result;
    }
}
