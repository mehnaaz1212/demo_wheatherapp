document.addEventListener("DOMContentLoaded", function () {
    // ⏰ First digital clock (client-side)
    function updateClock() {
        const now = new Date();
        let hours = now.getHours();
        let minutes = now.getMinutes();
        let seconds = now.getSeconds();
        let ampm = hours >= 12 ? 'PM' : 'AM';

        hours = hours % 12 || 12;
        minutes = minutes < 10 ? '0' + minutes : minutes;
        seconds = seconds < 10 ? '0' + seconds : seconds;

        const timeString = hours + ':' + minutes + ':' + seconds + ' ' + ampm;
        document.getElementById("digitalClock").textContent = timeString;
    }

    // ⏱️ Second digital clock (server-side time via servlet)
    function updateServerClock() {
        fetch("TimeServlet")
            .then(response => response.text())
            .then(serverTime => {
                document.getElementById("serverClock").textContent = serverTime;
            })
            .catch(error => {
                console.error("Failed to fetch server time:", error);
                document.getElementById("serverClock").textContent = "Server time unavailable";
            });
    }

    // 📡 Fetch weather data when search is clicked
    function fetchWeatherData() {
        const city = document.getElementById("searchInput").value;

        if (!city) {
            alert("Please enter a city name.");
            return;
        }

        fetch(`MyServlet`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'X-Requested-With': 'XMLHttpRequest'
            },
            body: `city=${encodeURIComponent(city)}`
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById("temperature").textContent = data["temperature"] + " °C";
                document.getElementById("city").textContent = city;
                document.getElementById("serverClock").textContent = data["date"]; // ✅ FIXED
                document.getElementById("humidity").textContent = data["humidity"] + "%";
                document.getElementById("windSpeed").textContent = data["windSpeed"] + " km/h";

                const weatherIcon = document.getElementById("weather-icon");
                const weatherCondition = data.weatherCondition;

                const weatherIcons = {
                    'Clouds': "https://blogger.googleusercontent.com/img/.../Clouds.png",
                    'Clear': "https://blogger.googleusercontent.com/img/.../sun.png",
                    'Rain': "https://blogger.googleusercontent.com/img/.../rainy.png",
                    'Mist': "https://blogger.googleusercontent.com/img/.../mist.png",
                    'Snow': "https://blogger.googleusercontent.com/img/.../snow.png",
                    'Haze': "https://blogger.googleusercontent.com/img/.../haze.png"
                };

                weatherIcon.src = weatherIcons[weatherCondition] || "https://blogger.googleusercontent.com/img/.../default.png";
            })
            .catch(error => console.error('Error fetching weather data:', error));
    }

    // ⏱️ Start both clocks
    setInterval(updateClock, 1000);
    updateClock();

    setInterval(updateServerClock, 1000);
    updateServerClock();

    // 🔍 Bind search button
    document.getElementById("searchButton").addEventListener("click", fetchWeatherData);
});
