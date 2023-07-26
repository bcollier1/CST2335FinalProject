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


    // Trivia Constants
    String TRIVIA_BASE_URL_START = "https://opentdb.com/api.php?";

    String TRIVIA_BASE_URL_END = "&type=multiple";

    String[] triviaCategoryArray = {"Any Category", "General Knowledge", "Entertainment: Books",
            "Entertainment: Film", "Entertainment: Music", "Entertainment: Musicals &amp; Theatres",
            "Entertainment: Television", "Entertainment: Video Games", "Entertainment: Board Games",
            "Science &amp; Nature", "Science: Computers", "Science: Mathematics", "Mythology",
            "Sports", "Geography", "History", "Politics", "Art", "Celebrities", "Animals",
            "Vehicles", "Entertainment: Comics", "Science: Gadgets",
            "Entertainment: Japanese Anime &amp; Manga", "Entertainment: Cartoon &amp; Animations"};
}
