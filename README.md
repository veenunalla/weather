Weather Application
Overview
This Weather Application provides real-time weather updates using modern Android development tools and libraries. The app is built with Retrofit for network requests, Hilt for dependency injection, Jetpack Compose for UI, Navigation Component for navigation, and DataStore for data storage.

Features
Weather Updates: Fetches current weather data from a weather API when city is entered.
Weather Forecase : Shows forecast for next 5days.

Architecture
The app follows the MVVM (Model-View-ViewModel) architecture pattern, ensuring a clear separation of concerns and making the codebase more maintainable.

Libraries and Tools
Retrofit: For making network requests to the weather API.
Hilt: For dependency injection, simplifying the management of dependencies.
Jetpack Compose: For building the UI declaratively.
Navigation Component: For handling navigation between different screens.
DataStore: For storing user preferences and app data.
Setup and Installation
Clone the repository:

git clone https://github.com/veenunalla/weather.git

Open the project in Android Studio.

Build the project:

Ensure you have the latest version of Android Studio.
Sync the project with Gradle files.
Run the app on an emulator or a physical device.

Usage
Home Screen: Displays the current weather based on the user’s city.
Detail Screen: Allows users to search for weather updates for forecase.

Code Structure
repository/: Contains repository implementations for fetching weather info.
di/: Contains Hilt modules for dependency injection.
ui/: Contains Composable functions and ViewModels.
navigation/: Contains navigation graph and related components.
datastore/: Contains classes for managing DataStore operations.


Implementation Demo

![weather](https://github.com/user-attachments/assets/abe300bd-06a8-40a3-9666-deb5a461ebac)


