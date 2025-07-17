<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Weather App - CodingWallahSir</title>
    
    <link rel="stylesheet" href="style.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" /> 
</head>
<style>
@keyframes glowPulse {
  0%, 100% {
    box-shadow: 0 0 15px rgba(0, 255, 255, 0.8), 0 0 25px rgba(0, 255, 255, 0.6), 0 0 35px rgba(0, 255, 255, 0.4);
  }
  50% {
    box-shadow: 0 0 25px rgba(255, 0, 255, 1), 0 0 40px rgba(255, 0, 255, 0.8), 0 0 60px rgba(255, 0, 255, 0.6);
  }
}

@keyframes shimmer {
  0% {
    background-position: -200% 0;
  }
  100% {
    background-position: 200% 0;
  }
}

#digitalClock {
  position: absolute;
  top: 250px;
  left: 200px; /* 👈 moved to the left side */
  width: 140px;
  height: 140px;
  font-size: 22px;
  font-weight: bold;
  color: white;
  background: linear-gradient(145deg, rgba(0,0,0,0.6), rgba(0,0,0,0.9));
  padding: 30px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  border: 3px solid transparent;
  background-clip: padding-box;
  animation: glowPulse 3s ease-in-out infinite;
  z-index: 1;
}

#digitalClock::before {
  content: "";
  position: absolute;
  top: -3px;
  left: -3px;
  right: -3px;
  bottom: -3px;
  border-radius: 50%;
  background: linear-gradient(90deg, #0ff, #f0f, #0ff);
  z-index: -1;
  background-size: 300% 300%;
  animation: shimmer 6s linear infinite;
  filter: blur(4px);
}
#city{
  font-size: 36px;       /* Increase font size */
  font-weight: bold;     /* Make it bold */
  text-transform: capitalize;  /* Makes 'delhi' → 'Delhi' */
}
</style>

<body>

    <!-- Digital Clock -->
    <div id="digitalClock"></div>

    <div class="mainContainer">
        <form action="MyServlet" method="post" class="searchInput">
            <input type="text" placeholder="Enter City Name" id="searchInput" name="city"/>
            <button type="button" id="searchButton"><i class="fa-solid fa-magnifying-glass"></i></button>
        </form>
        <div class="cityDetails">        
                <div class="desc"><strong id="city">${city}</strong></div>
                <div class="date" id="serverClock">${date}</div>
            </div>
        <div class="weatherDetails">
            <div class="weatherIcon">
                <img src="https://cdn-icons-png.flaticon.com/512/1163/1163624.png" alt="Cloud" width="40" height="40" alt="Clouds" id="weather-icon">
                <h2 id="temperature">${temperature} °C</h2>
                <input type="hidden" id="wc" value="${weatherCondition}">
            </div>
            
            

            <div class="windDetails">
                <div class="humidityBox">
                    <img src="https://cdn-icons-png.flaticon.com/512/1684/1684375.png" alt="Humidity" width="40" height="40">
                    <div class="humidity">
                        <span>Humidity </span>
                        <h2 id="humidity">${humidity}% </h2>
                    </div>
                </div> 
               
                <div class="windSpeed">
                    <img src="https://cdn-icons-png.flaticon.com/512/4005/4005901.png" alt="Wind Speed" width="40" height="40">
                    <div class="wind">
                        <span>Wind Speed</span>
                        <h2 id="windSpeed">${windSpeed} km/h</h2>
                    </div>
                </div>
            </div>
        </div>
    </div>
<script src="myScript.js"></script>


</body>
</html>
