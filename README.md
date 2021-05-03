# esport-analytics

This repository contains simple dota esport app based on MVVM architecture.

# Screenshots

<p align="center">
  <img src="https://github.com/pavlinz/esport-analytics/blob/master/screenshots/1.jpg" width="200" title="Match Details" alt="Match Details">
  <img src="https://github.com/pavlinz/esport-analytics/blob/master/screenshots/2.jpg" width="200" title="Favored Matches" alt="Favored Matches">
  <img src="https://github.com/pavlinz/esport-analytics/blob/master/screenshots/3.jpg" width="200" title="Matches" alt="Matches">
  <img src="https://github.com/pavlinz/esport-analytics/blob/master/screenshots/4.jpg" width="200" title="Matches" alt="Matches">
</p>

# Tech stack & Open-source libraries

* Kotlin
* Architecture
  * MVVM
  * Repository pattern
* Coroutines - asynchronous programming
* Architecture Components
  * LiveData - observable data holder
  * ViewModel - lifecycle aware class for storing and managing UI-related data
  * Room Database - provides an abstraction layer over SQLite
  * Navigation Component - handle everything needed for in-app navigation
* Hilt - compile-time framework for dependency injection
* Retrofit2 & Moshi - constructing the REST API
* Glide - load and cache images by URL
* Jsoup - parsing html pages
* Firebase - Tools to develop high-quality apps
  * Realtime Database
  * Crashlytics
  * Cloud Messaging
