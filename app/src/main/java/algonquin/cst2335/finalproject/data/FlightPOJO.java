package algonquin.cst2335.finalproject.data;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents flight information.
 */
public class FlightPOJO {
    public Pagination pagination;
    public ArrayList<Datum> data;

    /**
     * This class represents pagination information.
     */
    class Pagination {
        public int limit;
        public int offset;
        public int count;
        public int total;
    }

    /**
     * This class represents specific flight data.
     */
    public class Datum {
        public String flight_date;
        public String flight_status;
        public Departure departure;
        public Arrival arrival;
        public Airline airline;
        public FlightInfo flight;
        public Object aircraft;
        public Object live;

        /**
         * This class represents departure details.
         */
        public class Departure {
            public String airport;
            public String timezone;
            public String iata;
            public String icao;
            public String terminal;
            public String gate;
            public int delay;
            public Date scheduled;
            public Date estimated;
            public Date actual;
            public Date estimated_runway;
            public Date actual_runway;
        }

        /**
         * This class represents arrival details.
         */
        public class Arrival {
            public String airport;
            public String timezone;
            public String iata;
            public String icao;
            public String terminal;
            public String gate;
            public String baggage;
            public int delay;
            public Date scheduled;
            public Date estimated;
            public Date actual;
            public Date estimated_runway;
            public Date actual_runway;
        }

        /**
         * This class represents airline details.
         */
        public class Airline {
            public String name;
            public String iata;
            public String icao;
        }

        /**
         * This class represents flight information.
         */
        public class FlightInfo {
            public String number;
            public String iata;
            public String icao;
            public Codeshared codeshared;

            /**
             * This class represents details of code-shared flights.
             */
            public class Codeshared {
                public String airline_name;
                public String airline_iata;
                public String airline_icao;
                public String flight_number;
                public String flight_iata;
                public String flight_icao;
            }
        }
    }
}















