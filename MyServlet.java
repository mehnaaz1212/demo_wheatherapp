package MyPackage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MyServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//API Setup
		String apiKey="c006d1c4ef1a28935890a3aa7a60d3aa";
		 
		// Get city from the input
        String city = request.getParameter("city");
        if (city == null || city.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "City parameter is missing or empty.");
            return;
        }

        // Encode the city parameter
        city = URLEncoder.encode(city, "UTF-8");
		
		//create the url openwhether app API request
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
		
		//API integration
		URL url= new URL(apiUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		
		//Reading data from network
		InputStream inputStream =connection.getInputStream();
		InputStreamReader reader = new InputStreamReader(inputStream);
		
		//Want to store in string 
		StringBuilder responseContent = new StringBuilder();
		
		//input from the reaer will create scanner class
		Scanner scanner = new Scanner(reader);
		
		while(scanner.hasNext()) {
			responseContent.append(scanner.nextLine());
		}
		
		scanner.close();
		//Typecasting or parsing data into json
		Gson gson= new Gson();
		JsonObject jsonObject= gson.fromJson(responseContent.toString(), JsonObject.class);
				//System.out.println(jsonObject);
				
				 //Date & Time
                long dateTimestamp = jsonObject.get("dt").getAsLong() * 1000;
                String date = new Date(dateTimestamp).toString();
                
                //Temperature
                double temperatureKelvin = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
                int temperatureCelsius = (int) (temperatureKelvin - 273.15);
               
                //Humidity
                int humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();
                
                //Wind Speed
                double windSpeed = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();
                
                //Weather Condition
                String weatherCondition = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
                
             // Send JSON response if it's an AJAX call
                if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                    JsonObject responseJson = new JsonObject();
                    responseJson.addProperty("date", date);
                    responseJson.addProperty("temperature", temperatureCelsius);
                    responseJson.addProperty("humidity", humidity);
                    responseJson.addProperty("windSpeed", windSpeed);
                    responseJson.addProperty("weatherCondition", weatherCondition);

                    response.setContentType("application/json");
                    PrintWriter out = response.getWriter();
                    out.print(responseJson.toString());
                    out.flush();
                } 
                else {
                	
    
                // Set the data as request attributes (for sending to the jsp page)
                request.setAttribute("date", date);
                request.setAttribute("city", city);
                request.setAttribute("temperature", temperatureCelsius);
                request.setAttribute("weatherCondition", weatherCondition); 
                request.setAttribute("humidity", humidity);    
                request.setAttribute("windSpeed", windSpeed);
                request.setAttribute("weatherData", responseContent.toString());
                
               
                
                connection.disconnect();
                // Forward the request to the weather.jsp page for rendering
                request.getRequestDispatcher("index.jsp").forward(request, response);
	}

 }
}