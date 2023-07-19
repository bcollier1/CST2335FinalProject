package algonquin.cst2335.finalproject.util;

public interface Constants {
    //Stale data, used to obtain airport iata code because the free API plan does not allow search
    String DATA_AIRPORT = "data_airports_trimmed.json";

    //Test data
    String DATA_FLIGHTS = "data_flights_YOW.json";

    String URL_DEPARTURE_FLIGHT_FROM_CODE =
            "htt" +
                    "p://ap" +
                    "i.aviat" +
                    "ionsta" +
                    "ck.com/" +
                    "v1/flig" +
                    "hts?acc" +
                    "ess_key=" +
                    "56409b93" +
                    "6479bc7" +
                    "d32c48" +
                    "bc76cc6" +
                    "ba2e&de" +
                    "p_iata=";
}
