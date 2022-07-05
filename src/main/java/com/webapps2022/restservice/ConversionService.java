package com.webapps2022.restservice;  

import java.util.HashMap;
import java.util.Map;
import javax.ejb.Singleton;
import javax.ws.rs.GET; 
import javax.ws.rs.Path; 
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("/conversion")
public class ConversionService {
    private final Map<String, Double> currencyRates = new HashMap<>();
    private double convertedAmount;

    public ConversionService() {
        currencyRates.put("GBPUSD", 1.24);
        currencyRates.put("GBPEUR", 1.18);
        currencyRates.put("USDGBP", 0.80);
        currencyRates.put("USDEUR", 0.95);
        currencyRates.put("EURUSD", 1.05);
        currencyRates.put("EURGBP", 0.84);
    }

    @Path("/{currency1}/{currency2}/{currencyAmount}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public double ReturnConversion( @PathParam("currency1") String currency1,
    @PathParam("currency2") String currency2, @PathParam("currencyAmount") double currencyAmount) {
        String pair = currency1 + currency2;
        for (String key : currencyRates.keySet()) {
            if (key.equals(pair)) {
                convertedAmount = currencyAmount * currencyRates.get(key);
            }
        }
        return convertedAmount;
    }
}