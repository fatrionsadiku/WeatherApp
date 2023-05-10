<h1 align="center">Weather App</h1>

<p align="center">  
Weather App application built with Coroutines,Jetpack(ViewModel), and Material Design based on MVVM architecture.
</p>
</br>

<p align="center">
<img src="/mockups/weatherapp_mockup_photo.jpg"/>
</p>

<img src="/mockups/weather_app_mockup.gif" align="right" width="320"/>

## Tech stack & Open-source libraries
- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous.
- Jetpack
  - Lifecycle: Observe Android lifecycles and handle UI states upon the lifecycle changes.
  - ViewModel: Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
- Architecture
- MVVM Architecture (View - DataBinding - ViewModel - Model)
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit): Construct the REST APIs and paging network data.
## Architecture
**WeatherApp** is based on the MVVM architecture and the Repository pattern, which follows the [Google's official architecture guidance](https://developer.android.com/topic/architecture).
