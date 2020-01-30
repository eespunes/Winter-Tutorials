package cat.tecnocampus.omega.webControllers;

import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JapisExample {

    private String owmApiKey = "742bf5820f2530a3a241e7fde16148fe";

    @GetMapping("/weather")
    public String usingJapis(Model model) throws APIException {
        OWM owm = new OWM(owmApiKey);
        CurrentWeather cwd = owm.currentWeatherByCityName("Madrid");
        // checking data retrieval was successful or not
        if (cwd.hasRespCode() && cwd.getRespCode() == 200) {
            // checking if city name is available
            if (cwd.hasCityName()) {
                //printing city name from the retrieved data
                System.out.println("City: " + cwd.getCityName());
            }
            // checking if max. temp. and min. temp. is available
            if (cwd.hasMainData() && cwd.getMainData().hasTempMax() && cwd.getMainData().hasTempMin()) {
                // printing the max./min. temperature
                double maxTemp = cwd.getMainData().getTempMax().shortValue() - 273;
                double minTemp = cwd.getMainData().getTempMin().shortValue() - 273;
                System.out.println("Temperature: " + maxTemp
                        + "/" + minTemp + "\'C");

                model.addAttribute("time", cwd);
                return "Weather";
            }
        }
        return null;
    }

    @GetMapping("/time")
    public String time() {
        return "weatherForcast";
    }
}
