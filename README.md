
# Star Wars Planets

A modern Android application developed using Kotlin and Jetpack Compose, designed to explore planets from the Star Wars universe. This app leverages the SWAPI API to fetch data and incorporates architectural components like MVVM, Room for local data storage, and Hilt for dependency injection. Key features include paginated planet listings, offline caching, and detailed planet information accessible through Compose Navigation.

## Features

- **Paginated Planet List:** Browse through a list of Star Wars planets with seamless pagination.
- **Planet Details:** Access comprehensive information about each planet, including name, orbitalPeriod, gravity.
- **Offline Caching:** Enjoy uninterrupted access to previously viewed planets without an internet connection.
- **Modern Android Practices:** Built with the latest Android development tools and libraries to ensure optimal performance and maintainability.
- **Unit Test Coverage for Business Logic:** The app includes comprehensive unit tests to validate and ensure the correctness of the business logic.

## Screenshots

<table>
  <tr>
    <th align="center"><b>Splash Screen</b></th>
    <th align="center"><b>Planet List Screen</b></th>
    <th align="center"><b>Planet Detail Screen</b></th>
  </tr>
  <tr>
    <td><img src="https://github.com/chamodlakmal/Star-Wars-Planets/blob/main/screenshots/Splash%20Screen.png" width="400"></td>
    <td><img src="https://github.com/chamodlakmal/Star-Wars-Planets/blob/main/screenshots/Planet%20List.jpg" width="400"></td>
    <td><img src="https://github.com/chamodlakmal/Star-Wars-Planets/blob/main/screenshots/Planet%20Detail.jpg" width="400"></td>
  </tr>
</table>

## Installation

To run this application locally:

1. **Clone the Repository:** ```git clone https://github.com/chamodlakmal/Star-Wars-Planets.git```
2. **Navigate to the Project Directory:**
3. **Open with Android Studio:**
- Launch Android Studio.
- Select 'Open an existing project' and choose the cloned directory.
4. **Build the Project:**
- Allow Android Studio to synchronize and build the project. Ensure all dependencies are resolved.
5. **Run the App:**
- Connect an Android device or start an emulator.
- Click on the 'Run' button.

## Dependencies

- **Kotlin:** Programming language used for Android development.
- **Jetpack Compose:** Modern toolkit for building native UI.
- **SWAPI API:** The Star Wars API providing data about planets.
- **Room:** Persistence library for local data storage.
- **Hilt:** Dependency injection library for Android.
- **Retrofit:** Type-safe HTTP client for API requests.
- **Coroutines:** For asynchronous programming.

## Folder Structure
```
Star-Wars-Planets/
│-- app/
│   │-- src/
│   │   │-- main/
│   │   │   │-- java/lk/chamiviews/starwarsplanets/
│   │   │   │   │-- data/       # Handles API calls and database interactions
│   │   │   │   │-- di/         # Hilt dependency injection setup
│   │   │   │   │-- domain/     # Business logic and use cases
│   │   │   │   │-- presentation/ # UI-related components, ViewModels, and navigation
│   │   │   │   │-- utils/      # Utility classes and helpers
│   │   │   │-- res/            # Resources such as layouts, drawables, etc.
│   │   │-- AndroidManifest.xml # Application configuration
│-- build.gradle.kts            # Project-level Gradle configuration
│-- settings.gradle.kts         # Gradle settings
│-- README.md                   # Project documentation
```

## Architecture

This application follows the Model-View-ViewModel (MVVM) architecture:

- **Model:** Handles data operations, including API calls and database interactions.
- **View:** UI components built using Jetpack Compose.
- **ViewModel:** Manages UI-related data and handles user interactions.

## Contact

For any inquiries or feedback, please contact [Chamod Lakmal](https://github.com/chamodlakmal).

